package com.adm.backend.context;


import java.io.File;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyOverrideConfigurer;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

/**
 * spring PropertyOverrideConfigurer 사용
 */
public class AdminPropertyOverrideConfigurer extends PropertyOverrideConfigurer {
    private static final Logger log = LoggerFactory.getLogger(AdminPropertyOverrideConfigurer.class);
    private Resource[] locations;

    /**
     * vm 구동 시 변수로 선언되는 admin.home property 가 존재하지 않으면 null 반환
     * 존재하면 admin.home 을 / 로 끝나지 않으면 / 붙이고 파일로 반환
     */
    private File getAdminPropertiesPath() {
        // -Dadmin.home = ... 으로 구동 시키지 않는 이상 home 은 null 이다.
        String home = System.getProperty("admin.home");
        File path = null;
        if (StringUtils.isEmpty((CharSequence)home)) {
            log.info("System property 'kona.home' is not set. Skipping property override.");
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(home); 
            if(!home.endsWith(File.separator)) {  // File.separator = / 로 끝나지 않으면 끝에 / 추가
                buffer.append(File.separator);
            }
            buffer.append("ADMIN.properties");
            path = new File(buffer.toString());
            log.info("Loading user configuration file from {}", (Object)path.getPath());
        }
        return path;
    }
    /**
     * override 
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException{
        File adminPropertiesPath = this.getAdminPropertiesPath();
        if(adminPropertiesPath != null) {
            this.setLocations(
                (Resource[]) ArrayUtils.add((Object[])this.locations,(Object) new FileSystemResource(adminPropertiesPath))
            );
        }
        super.postProcessBeanFactory(beanFactory);
    }

    /**
     * override
     */
    protected void processProperties(ConfigurableListableBeanFactory benaFactoryToProcess, Properties props) throws BeansException {
        String[] beanNames;
        Enumeration<?> names = props.propertyNames();
        while (names.hasMoreElements()) {
            String[] key = (String[]) names.nextElement();
            try {
                this.processKey(benaFactoryToProcess, (String) key.toString(), props.getProperty((String) key.toString()));
            }
            catch (BeansException ex) {
                log.warn("Could not process key '{}' in PropertyOverrideConfiigurer", (Object)key);
            }
        }

        beanNames = benaFactoryToProcess.getBeanDefinitionNames();
        for (String beanName : beanNames) {
            PropertyValue[] pvArray;
            BeanDefinition bd = benaFactoryToProcess.getBeanDefinition(beanName);
            pvArray = bd.getPropertyValues().getPropertyValues();
            for (PropertyValue pv : pvArray) {
                String stringValue = this.getStringValue(pv);
                if(!StringUtils.isNotBlank((CharSequence)stringValue)) continue;
                bd.getPropertyValues().addPropertyValue(pv.getName(), (Object)this.resolveStringValue(stringValue));
            }
        }
    }
    /**
     * PropertyValue 에 값을 문자열로 return
     */
    private String getStringValue(PropertyValue pv) {
        String stringValue = null;
        if (pv.getValue() instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue)pv.getValue();
            stringValue = typedStringValue.getValue();
        } else if (pv.getValue() instanceof String) {
            stringValue = (String)pv.getValue();
        }
        return stringValue;
    }

    protected String resolveStringValue(String strVal) {
        String result = strVal;
        if(this.hasPropertyExpress(result)) {
            String prefix = result.replaceAll("(\\$\\{).+(\\}).*","");  // ${} 제거
            String suffix = result.replaceAll(".*(\\$\\{).+(\\})","");
            String placeholder = result.replaceAll(".*(\\$\\{).+(\\}).*","");
            String convertedValue = System.getProperty(placeholder);
            if(StringUtils.isNotBlank((CharSequence)convertedValue)) {
                result = prefix + convertedValue + suffix;
            }
        }
        return result;
    }

    /**
     *  정규식 필터 
     *  프로퍼티 표현식인지 체크하고 resolveStringValue 에서 한번 더 체크 함
     */
    private boolean hasPropertyExpress(String strVal) {
        Pattern pattern = Pattern.compile("(\\$\\{).+(\\}).*");  // ${한글자} 많은글자일수도 없을수도
        Matcher matcher = pattern.matcher(strVal);
        return matcher.find();
    }

    public AdminPropertyOverrideConfigurer() {}

    public AdminPropertyOverrideConfigurer(Resource[] locations) {
        this.locations = locations;
    }
}

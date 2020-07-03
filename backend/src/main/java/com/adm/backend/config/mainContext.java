package com.adm.backend.config;

import com.adm.backend.context.AdminPropertyEditorRegistrar;
import com.adm.backend.context.AdminPropertyOverrideConfigurer;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


/**
 * 빈 등록은 했는데 
 * customEditorConfigurer 이건 뭔고하니
 * 스프링 프로퍼티 에디터
 */
/* 
<bean id="customEditorConfigurer"
class="org.springframework.beans.factory.config.CustomEditorConfigurer">
<property name="propertyEditorRegistrars">
  <list>
      <bean class="...AdminPropertyEditorRegistrar"/>
  </list>
</property>
</bean> */

@Configuration
public class mainContext {
    private static final Logger log = LoggerFactory.getLogger(mainContext.class);
    
    /**
     * 스프링 에디터
     * property를 넘길 떄는 이렇게
     * @return
     */
    @Bean(name = "customEditorConfigurer")
    public CustomEditorConfigurer customEditorConfigurer() {

        log.debug("%s", "CustomEditor context");
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        
        customEditorConfigurer.setPropertyEditorRegistrars(new PropertyEditorRegistrar[]{new AdminPropertyEditorRegistrar()});

        return customEditorConfigurer;
        //customEditorConfigurer.setPropertyEditorRegistrars((AdminPropertyEditorRegistrar[]) ArrayUtils.add( new AdminPropertyEditorRegistrar[],new AdminPropertyEditorRegistrar()));
        //customEditorConfigurer.setPropertyEditorRegistrars(propEditorRegList);

        /**
         * 리턴클래스를 인스턴스화 하고 CustomEditorConfigurer
         * 받을 배열을 앞에 파라미터에 넣고
         * PropertyEditorRegistrar 는 인터페이스고 구현체를 가져와야 한다.
         */
        
        //ArrayUtils.add((Object[]) new PropertyEditorRegistrar(), (Object) new AdminPropertyEditorRegistrar());

        // return customEditorConfigurer;

        // log.debug("%s", "CustomEditor context");
        // CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        // PropertyEditorRegistrar[] propEditorRegList = new PropertyEditorRegistrar[1];
        // propEditorRegList[0] = new AdminPropertyEditorRegistrar();
        
        // customEditorConfigurer.setPropertyEditorRegistrars(propEditorRegList);
        // return new CustomEditorConfigurer();
    }

    //프로퍼티 파일 찾기
    /**
     * 해당 프로퍼티 컨피거에 admin.properties ( classpath 에 있는 것 )
     * 정보를 생성자로 던진다 생성자는 Resourcep[] 형태이다.
     * 생성자에 파라미터로 넣을 때는 이렇게
     */
    @Bean(name = "propertyConfigurer")
    public AdminPropertyOverrideConfigurer propertyConfigurer(Resource[] resource) {
        
        return new AdminPropertyOverrideConfigurer((Resource[]) ArrayUtils.add((Object[]) resource, (Object) new ClassPathResource("admin.properties")));
    }
} 

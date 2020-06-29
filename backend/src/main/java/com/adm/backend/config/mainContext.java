package com.adm.backend.config;

import com.adm.backend.context.AdminPropertyEditorRegistrar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyEditorRegistrar;
import org.springframework.beans.factory.config.CustomEditorConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
    
    // 스프링 프로퍼티 에디터
    @Bean(name = "customEditorConfigurer")
    public CustomEditorConfigurer customEditorConfigurer() {
        log.debug("%s", "CustomEditor context");
        CustomEditorConfigurer customEditorConfigurer = new CustomEditorConfigurer();
        PropertyEditorRegistrar[] propEditorRegList = new PropertyEditorRegistrar[1];
        propEditorRegList[0] = new AdminPropertyEditorRegistrar();
        
        customEditorConfigurer.setPropertyEditorRegistrars(propEditorRegList);
        return new CustomEditorConfigurer();
    }

    // 프로퍼티 파일 찾기
    // @Bean(name = "propertyConfigurer")
    // public  propertyConfigurer() {

    // }
}


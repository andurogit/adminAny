package com.adm.backend.listener;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.fusesource.jansi.AnsiConsole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * 디렉토리 설정
 * ADMIN_HOME 이 있을경우 설정값에 따르고
 * 아닐 경우 .admin 으로 생성 함 
 * 해당 메소드는 폴더명칭을 설정 함. 
 * admin.home -> property
 */
@Component
public class adminApplicationListener implements ApplicationListener<ApplicationStartingEvent>{
    private static final Logger log = LoggerFactory.getLogger(adminApplicationListener.class);

    private void setupAdminHomeProperty() {
        String adminHome = System.getenv("ADMIN_HOME");
        if (StringUtils.isBlank((CharSequence)adminHome)) {
            adminHome = System.getProperty("user.home") + File.separator + ".admin";
        }
        System.setProperty("admin.home", adminHome);
    }

    /**
     *  admin.property 와 같은 위치 에
     *  캐쉬에 관한 프로퍼티 
     */
    private void setupOsCacheProperty() {
        String propertiesFilePath = System.getProperty("admin.home") + File.separator + "admin-oscache.properties";
        System.setProperty("cachePropertiesFilePath", propertiesFilePath);
        String cachePath = System.getProperty("admin.home") + File.separator + "temp" + File.separator + "admin-cache";
        System.setProperty("togaCachePath", cachePath);
    }

	@Override
	public void onApplicationEvent(ApplicationStartingEvent event) {
        AnsiConsole.systemInstall();
        System.setProperty("jcl.isolateLogging", "false");
        System.setProperty("adminSystem.name", "any-admin");
        this.setupAdminHomeProperty();
        log.info("AdminAny home directory : {}", (Object)System.getProperty("admin.home"));
        this.setupOsCacheProperty();
        log.debug("Servlet context listener has been initialized.");
	}

}
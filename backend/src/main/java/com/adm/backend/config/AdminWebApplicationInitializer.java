package com.adm.backend.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.adm.backend.listener.adminApplicationListener;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;

/**
 * Wrong Way
 * AdminWebApplicationInitializer
 * litener 는 이렇게 등록하면 안되고 SpringBootApplication 어노테이션이 있는 곳에서 지정해야 한다.
 */
@Configuration
public class AdminWebApplicationInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		servletContext.addListener(adminApplicationListener.class);
	}

    
}
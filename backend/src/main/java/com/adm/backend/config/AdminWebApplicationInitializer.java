package com.adm.backend.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;

/**
 * Wrong Way
 * AdminWebApplicationInitializer
 * litener 는 이렇게 등록하면 안되고 SpringBootApplication 어노테이션이 있는 곳에서 지정해야 한다.
 */
@Configuration
public class AdminWebApplicationInitializer implements WebApplicationInitializer{
	private static final Logger log = LoggerFactory.getLogger(AdminWebApplicationInitializer.class);

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		log.info("Listener Add");
		servletContext.addListener(ContextLoaderListener.class);
		servletContext.addListener(RequestContextListener.class);
	}

}
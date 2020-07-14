package com.adm.backend;

import java.util.Arrays;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.adm.backend.listener.adminApplicationListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CharacterEncodingFilter;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
// @EnableAutoConfiguration
// @ComponentScan
public class AnyAdminApplication extends SpringBootServletInitializer { // WebApplicationInitializer 포함 됨
	
	private static final Logger log = LoggerFactory.getLogger(AnyAdminApplication.class);

	public static void main(String[] args) {
		SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(AnyAdminApplication.class);

		SpringApplication springApplication = springApplicationBuilder.build();

		springApplication.addListeners(new adminApplicationListener());

		springApplication.run(args);

		//SpringApplication.run(DemoApplication.class, args);
		// SpringApplication app = new SpringApplication(AnyAdminApplication.class);
		// app.addListeners(new adminApplicationListener());
		// app.run(args);
		// AnyAdminApplication.servletBuilder(new SpringApplicationBuilder()).run(args);
	}
	
	// public static SpringApplicationBuilder servletBuilder(SpringApplicationBuilder builder) {
	// 	return builder.sources(AnyAdminApplication.class);
	// }
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		log.info("System Init ");
		//return super.configure(builder);
		return builder.sources(AnyAdminApplication.class); //endpoint class
		//return AnyAdminApplication.servletBuilder(builder);
	}
	
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
		log.info("System StartUP ");
		System.out.println("TESTESTETSETET");
		// FilterRegistration charEncodingFilterReg = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
		// charEncodingFilterReg.setInitParameter("encoding", "UTF-8");
		// charEncodingFilterReg.setInitParameter("forceEncoding", "true");
		// charEncodingFilterReg.addMappingForUrlPatterns(null, true, "/*");

		// FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);

		// springSecurityFilterChain.addMappingForUrlPatterns( EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),  true, "/*");
	}

	/**
	 *  Creation Beans Check
	 */
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			
			System.out.println("Let's inspect the beans provided by Spring Boot:");
			
			System.out.println("--- args start ---");
			for( String param : args) {
				System.out.println(param);
			}
			System.out.println("--- args end ---");
			
			String[] beanNames = ctx.getBeanDefinitionNames();
			
			Arrays.sort(beanNames);
			System.out.println("--- beans start ---");
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
			System.out.println("--- beans end ---");

			//WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		};
	}

	public AnyAdminApplication(){
		log.info("Init Application");
	}
}

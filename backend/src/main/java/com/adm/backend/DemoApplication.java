package com.adm.backend;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(exclude={DataSourceAutoConfiguration.class})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
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
}

package com.adm.backend;

import com.adm.backend.listener.adminApplicationListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @EnableAutoConfiguration, @ComponentScan -> @ SpringBootApplication 안에 포함되어 있음
 * 자동설정을 제외하고 싶다면
 * @SpringBootApplication(exclude={DataSourceAutoConfiguration.class, WebMvcAutoConfiguration.class, SecurityAutoConfiguration.class})
 * 
 * SpringBootServletInitializer 추상클래스는  WebApplicationInitializer 가 포함되어 있음
 * 해당 추상클래스를 확장하여 사용하면 dispatcher Sevlet 등 서블릿 관련 설정들이 기본사항으로 설정되어 있음
 * 해당 추상클래스에 onStartup configure 매소드는 런타임 때 진입하지 않으므로 주의하기 바람
 * Spring boot 가이드에는 configure 만 Override 해 놓는 걸로 사용가능하다고 하고 있음
 * 
 * 하기 소스는 onStartup , configure 메소드에서 디버깅해보기 위한 시도들임
 * main() ...
 * 		SpringApplication.run(AnyAdminApplication.class, args);

		SpringApplication.run(AnyAdminApplication.class, args);
		SpringApplication app = new SpringApplication(AnyAdminApplication.class);
		app.addListeners(new adminApplicationListener());
		app.run(args);
		AnyAdminApplication.servletBuilder(new SpringApplicationBuilder()).run(args);
 * 		
 * Start Application Sequence
 * 1. context/adminApplicationListener.java 구성 상 해당 클래스 가장먼저 진입
 * 	1. 현재 login debug 설정이 되어 있지 않아 정보가 제한적임 application.properties 에서 로깅처리 정보입력
 * 	1. 프로퍼티 및 기본경로 설정 캐쉬 설정 등
 * 1. 그 다음은 어플리케이션 로딩
 */

@SpringBootApplication 
public class AnyAdminApplication extends SpringBootServletInitializer { 
	
	private static final Logger log = LoggerFactory.getLogger(AnyAdminApplication.class);

	public static void main(final String[] args) {
		final SpringApplicationBuilder springApplicationBuilder = new SpringApplicationBuilder(AnyAdminApplication.class);

		final SpringApplication springApplication = springApplicationBuilder.build();

		springApplication.addListeners(new adminApplicationListener());

		springApplication.run(args);


	}
	
	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder builder) {
		log.info("System Init "); // 진입되지 않음.
		return builder.sources(AnyAdminApplication.class); //endpoint class
		//return super.configure(builder);
		//return AnyAdminApplication.servletBuilder(builder);
	}
	
	/* public static SpringApplicationBuilder servletBuilder(SpringApplicationBuilder builder) {
		return builder.sources(AnyAdminApplication.class);
	} */
	
	/**
	 *  Creation Beans Check
	 */
	@Bean
	public CommandLineRunner commandLineRunner(final ApplicationContext ctx) {
		return args -> {

			// System.out.println("Let's inspect the beans provided by Spring Boot:");
			
			// System.out.println("--- args start ---");
			// for( String param : args) {
			// 	System.out.println(param);
			// }
			// System.out.println("--- args end ---");
			
			// String[] beanNames = ctx.getBeanDefinitionNames();
			
			// Arrays.sort(beanNames);
			// System.out.println("--- beans start ---");
			// for (String beanName : beanNames) {
			// 	System.out.println(beanName);
			// }
			// System.out.println("--- beans end ---");

			//WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		};
	}

	public AnyAdminApplication(){
		log.info("Init Application");
	}
}

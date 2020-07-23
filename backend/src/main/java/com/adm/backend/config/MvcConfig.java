// package com.adm.backend.config;

// import java.util.EnumSet;

// import javax.servlet.DispatcherType;
// import javax.servlet.FilterRegistration;
// import javax.servlet.ServletContext;
// import javax.servlet.ServletException;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.context.annotation.Configuration;
// import org.springframework.web.WebApplicationInitializer;
// import org.springframework.web.filter.DelegatingFilterProxy;

// @Configuration
// public class MvcConfig implements WebApplicationInitializer {

//     private static final Logger log = LoggerFactory.getLogger(MvcConfig.class);

//     /**
//      * autoConfig
//      * @return
//      */

//     // @Bean(name = "RequestContextListener")
//     // public RequestContextListener requestContextListener(){
//     //     return new RequestContextListener();
//     // }

//     /**
//      * 이녀석은 이미 등록이 되어 있다. 
//      * @return
//      */
//     // @Bean(name = "ContextLoaderListener")
//     // public ContextLoaderListener contextLoaderListener(){
//     //     return new ContextLoaderListener();
//     // }
        
//     /**
//      * filter
//      */
//     // @Bean(name = "CharacterEncodingFilter")
//     // public CharacterEncodingFilter encodingFilter() {
//     //     CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//     //     characterEncodingFilter.setEncoding("UTF-8");
//     //     characterEncodingFilter.setForceEncoding(true);
//     //     return characterEncodingFilter;
//     // }

//     /**
//      * filter mapping 필요
//     //  */
//     // FilterRegistration charEncodingFilterReg = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
//     // charEncodingFilterReg.setInitParameter("encoding", "UTF-8");
//     // charEncodingFilterReg.setInitParameter("forceEncoding", "true");
//     // // charEncodingFilterReg.addMappingForUrlPatterns(null, true, "/*");
//     // @Bean(name = "DelegatingFilterProxy")
//     // public DelegatingFilterProxy springSecurityFilterChain() {

//     //     return new DelegatingFilterProxy();
//     // }


// 	@Override
// 	public void onStartup(ServletContext servletContext) throws ServletException {
//         log.debug("SpringSecurityFilterChain on Startup");
//         FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);
//         springSecurityFilterChain.addMappingForUrlPatterns( EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD, DispatcherType.INCLUDE),  true, "/*");
// 	}

    
// }
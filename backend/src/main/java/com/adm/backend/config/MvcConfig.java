// package com.adm.backend.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.web.context.request.RequestContextListener;
// import org.springframework.web.filter.DelegatingFilterProxy;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// //@Configuration
// public class MvcConfig implements WebMvcConfigurer {
    
//     @Bean(name = "RequestContextListener")
//     public RequestContextListener requestContextListener(){
//         return new RequestContextListener();
//     }

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

//     @Bean(name = "DelegatingFilterProxy")
//     public DelegatingFilterProxy springSecurityFilterChain() {

//         return new DelegatingFilterProxy();
//     }

//     /**
//      * filter mapping 필요
//     //  */
//     // FilterRegistration charEncodingFilterReg = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
//     // charEncodingFilterReg.setInitParameter("encoding", "UTF-8");
//     // charEncodingFilterReg.setInitParameter("forceEncoding", "true");
//     // charEncodingFilterReg.addMappingForUrlPatterns(null, true, "/*");

//     // FilterRegistration.Dynamic springSecurityFilterChain = servletContext.addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);

//     // springSecurityFilterChain.addMappingForUrlPatterns( EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD),  true, "/*");

// }
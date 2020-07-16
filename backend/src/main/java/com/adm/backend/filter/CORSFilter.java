// package com.adm.backend.filter;

// import java.io.IOException;

// import javax.servlet.Filter;
// import javax.servlet.FilterChain;
// import javax.servlet.FilterConfig;
// import javax.servlet.ServletException;
// import javax.servlet.ServletRequest;
// import javax.servlet.ServletResponse;
// import javax.servlet.annotation.WebFilter;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
// import org.springframework.stereotype.Component;

// import lombok.extern.slf4j.Slf4j;

// /**
//  * CORSFilter
//  */

//  @Slf4j
//  @Component
//  @WebFilter(urlPatterns = "/*" )
// public class CORSFilter implements Filter{
//     private static final Logger log = LoggerFactory.getLogger(CORSFilter.class);

// 	@Override
// 	public void init(FilterConfig filterConfig) throws ServletException {
// 		log.info("init CORSFilter");
// 	}

// 	@Override
// 	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
// 			throws IOException, ServletException {
		
//             HttpServletRequest req = (HttpServletRequest) request;
//             HttpServletResponse res = (HttpServletResponse) response;
//             log.info("##### filter - before #####");
//             chain.doFilter(req, res);
//             log.info("##### filter - after #####");

//     }

// 	@Override
// 	public void destroy() {
// 		log.info("destroy CORSFilter");
// 	}   
// }
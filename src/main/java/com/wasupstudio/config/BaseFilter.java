//package com.wasupstudio.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@WebFilter(filterName = "BaseFilter")
//@Configuration
//public class BaseFilter implements Filter {
//
//    @Autowired
//    private JwtTokenUtils jwtTokenUtil;
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        String authorizationHeader = httpRequest.getHeader("Authorization");
//
//        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
//            throw new ServletException("Missing or invalid Authorization header");
//        }
//
//        String jwtToken = authorizationHeader.substring(7);
//        String username = JwtTokenUtils.getJwtData(jwtToken);
//
////        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
////            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
////
////            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
////                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
////                        userDetails, null, userDetails.getAuthorities());
////                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
////                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
////            }
////        }
//
//        chain.doFilter(request, response);
//    }
//}

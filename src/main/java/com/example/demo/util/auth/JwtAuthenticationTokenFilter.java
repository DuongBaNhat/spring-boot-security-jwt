package com.example.demo.util.auth;

import com.example.demo.service.JwtService;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JwtAuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
    private final static String TOKEN_HEADER = "authorization";


    @Autowired
    private  JwtService jwtService;
    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String bearer = httpServletRequest.getHeader(TOKEN_HEADER);
        String authToken = bearer.substring(7);
         System.out.println("authToken " + authToken);

        if(jwtService.validateTokenLogin(authToken)) {
            String username = jwtService.getUsernameFromToken(authToken);

            com.example.demo.entities.User user = userService.getUserByName(username);
            if(user != null) {
                boolean enabled = true;
                boolean accountNonExpired = true;
                boolean credentialsNonExpired = true;
                boolean accountNonBlocked = true;

                UserDetails userDetail = new User(username, user.getPassword(), enabled, accountNonExpired,
                        credentialsNonExpired, accountNonBlocked, user.getAuthorities());

                UsernamePasswordAuthenticationToken authenticationFilter =
                        new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                authenticationFilter.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                SecurityContextHolder.getContext().setAuthentication(authenticationFilter);
            }
        }

        try {
            chain.doFilter(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        }
    }
}

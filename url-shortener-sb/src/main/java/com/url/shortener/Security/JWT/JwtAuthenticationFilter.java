package com.url.shortener.Security.JWT;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// for every request this code will execute. Use for validates the jwt token in the request header and loads the user info.
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    @Autowired
    private JWTUtils jwtTokenprovider;

    @Autowired
    private UserDetailsService userDetailsService;  // this interface define by spring security. It has logic to load the user
    // specific data.

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try{
            // to authenticate every request we have to follow this rules :
            // get jwt from header : by the help of jwt token we know user is valid or not.
            String jwt = jwtTokenprovider.getJwtFromHeader(request);

            if(jwt != null && jwtTokenprovider.validateToken(jwt)){
                String username = jwtTokenprovider.getUsernameFromJwtToken(jwt);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if(userDetails != null){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            // validate token
            // If valid get user details.
            // get username -> load user -> set the auth context.

        } catch(Exception e){
            e.printStackTrace();
        }

        filterChain.doFilter(request, response);    // it continuous the filter chain.
    }
}

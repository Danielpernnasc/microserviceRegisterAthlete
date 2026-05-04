package com.trainday.bodybuilder.infra.security;

import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
   private final UserDetailsService userDetailsService;

   public JwtAuthFilter(JwtService jwtService, UserDetailsService userDetailsService){
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
   }

   @Override
   protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();

        return path.equals("/v3/api-docs")
            || path.startsWith("/v3/api-docs/")
            || path.startsWith("/swagger-ui/")
            || path.equals("/swagger-ui.html")
            || path.startsWith("/swagger-resources/")
            || path.startsWith("/webjars/");
   }

   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);

            if(jwtService.isTokenValid(token)){
                String email = jwtService.extractEmail(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userDetails, null, List.of());

                SecurityContextHolder.getContext().setAuthentication((@Nullable Authentication) auth);
            }

            filterChain.doFilter(request, response);

    }
   


}
   

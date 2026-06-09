package com.trainday.bodybuilder.infra.security;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.trainday.bodybuilder.infra.Service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

@ExtendWith(MockitoExtension.class)
public class JwtAuthFilterTest {

    @Mock
    JwtService jwtService;

    @Mock
    UserDetailsService userDetailsService;

    @Mock
    FilterChain filterChain;

    @InjectMocks
    JwtAuthFilter jwtAuthFilter;
    

  

    @AfterEach
    void clearContext() {
        SecurityContextHolder.clearContext();
    }

       @Test
    void shouldNotFilter_swaggerPaths() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getServletPath()).thenReturn("/swagger-ui/index.html");

        boolean result = jwtAuthFilter.shouldNotFilter(request);

        assertTrue(result);
    }

    @Test
    void shouldNotFilter_apiDocs() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getServletPath()).thenReturn("/v3/api-docs");

        boolean result = jwtAuthFilter.shouldNotFilter(request);

        assertTrue(result);
    }

    @Test
    void shouldNotFilter_otherPath() {
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getServletPath()).thenReturn("/api/users");

        boolean result = jwtAuthFilter.shouldNotFilter(request);

        assertFalse(result);
    }

    @Test
    void shouldContinueFilterAuthenticateHeader() throws ServletException, IOException{
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(filterChain).doFilter(request, response);
        verify(jwtService, never()).isTokenValid(anyString());
        verify(userDetailsService, never()).loadUserByUsername(anyString());
    }

    @Test
    void shouldNotAuthenticateHeader() throws ServletException, IOException{
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader("Authorization", "Bearer token-invalid");
        
        when(jwtService.isTokenValid("token-invalid")).thenReturn(false);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertNull(SecurityContextHolder.getContext().getAuthentication());
        verify(jwtService).isTokenValid("token-invalid");
        verify(userDetailsService, never()).loadUserByUsername(anyString());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    void shouldAuthenticateTokenValid() throws ServletException, IOException{
         MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        UserDetails userDetails = mock(UserDetails.class);
        request.addHeader("Authorization", "Bearer token-valid");

        when(jwtService.isTokenValid("token-valid")).thenReturn(true);
        when(jwtService.extractEmail("token-valid")).thenReturn("user@host.com");
        when(userDetailsService.loadUserByUsername("user@host.com")).thenReturn(userDetails);

        jwtAuthFilter.doFilterInternal(request, response, filterChain);

        assertSame(userDetails, SecurityContextHolder.getContext().getAuthentication().getPrincipal());

       verify(jwtService).isTokenValid("token-valid");
       verify(jwtService).extractEmail("token-valid");
       verify(userDetailsService).loadUserByUsername("user@host.com");
       verify(filterChain).doFilter(request, response);
    }

   @Test
    void shouldSkipJwtValidationForAthletePath() throws Exception {

        MockHttpServletRequest request =
                new MockHttpServletRequest();

            request.setMethod("GET");
            request.setServletPath("/athlete/123");

        MockHttpServletResponse response =
                new MockHttpServletResponse();

        FilterChain filterChain = mock(FilterChain.class);

        jwtAuthFilter.doFilterInternal(
                request,
                response,
                filterChain
        );

        verify(filterChain).doFilter(request, response);

        verifyNoInteractions(jwtService);
    }

    @Test
    void shouldValidateJwtForPutAthletePath()
        throws Exception {

    MockHttpServletRequest request =
            new MockHttpServletRequest();

    request.setMethod("PUT");
    request.setServletPath("/athlete/123");

    request.addHeader(
            "Authorization",
            "Bearer token-valid"
    );

    MockHttpServletResponse response =
            new MockHttpServletResponse();

    UserDetails userDetails =
            mock(UserDetails.class);

    when(jwtService.isTokenValid("token-valid"))
            .thenReturn(true);

    when(jwtService.extractEmail("token-valid"))
            .thenReturn("user@host.com");

    when(userDetailsService.loadUserByUsername(
            "user@host.com"))
            .thenReturn(userDetails);

    jwtAuthFilter.doFilterInternal(
            request,
            response,
            filterChain
    );

    verify(jwtService)
            .isTokenValid("token-valid");

    verify(jwtService)
            .extractEmail("token-valid");

    verify(userDetailsService)
            .loadUserByUsername("user@host.com");

    verify(filterChain)
            .doFilter(request, response);

    assertNotNull(
            SecurityContextHolder
                    .getContext()
                    .getAuthentication()
    );
}




    
}

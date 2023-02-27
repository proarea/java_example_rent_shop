package com.snowboard_rental_crm.gateway.auth.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowboard_rental_crm.gateway.auth.filter.JwtRequestFilter;
import com.snowboard_rental_crm.gateway.auth.filter.LoginRequestFilter;
import com.snowboard_rental_crm.gateway.auth.filter.RefreshTokenRequestFilter;
import com.snowboard_rental_crm.gateway.auth.handler.LoginAuthSuccessHandler;
import com.snowboard_rental_crm.gateway.auth.handler.RefreshTokenAuthSuccessHandler;
import com.snowboard_rental_crm.gateway.auth.provider.JwtAuthenticationProvider;
import com.snowboard_rental_crm.gateway.auth.provider.LoginAuthenticationProvider;
import com.snowboard_rental_crm.gateway.auth.provider.RefreshTokenAuthenticationProvider;
import com.snowboard_rental_crm.gateway.auth.util.SkipPathRequestMatcher;
import com.snowboard_rental_crm.gateway.auth.util.TokenExtractor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
@ComponentScan("com.snowboard_rental_crm.*")
@EnableAspectJAutoProxy
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_ENTRY_POINT = "/**/auth";
    private static final String REFRESH_TOKEN_ENTRY_POINT = "/**/auth/refresh-token";

    private static final String TOKEN_AUTH_ENTRY_POINT = "/**";

    @Autowired
    @Qualifier("authWhiteList")
    private String[] authWhiteList;
    @Autowired
    private LoginAuthenticationProvider loginAuthenticationProvider;
    @Autowired
    private JwtAuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private RefreshTokenAuthenticationProvider refreshTokenAuthenticationProvider;
    @Autowired
    private AuthenticationFailureHandler failureHandler;
    @Autowired
    private LoginAuthSuccessHandler successHandler;
    @Autowired
    private RefreshTokenAuthSuccessHandler refreshTokenAuthenticationSuccessHandler;
    @Autowired
    private TokenExtractor tokenExtractor;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        log.debug("Enabling Login Authentication Provider");
        auth.authenticationProvider(loginAuthenticationProvider);

        log.debug("Enabling JWT Authentication Provider");
        auth.authenticationProvider(jwtAuthenticationProvider);

        log.debug("Enabling JWT Refresh Token Provider");
        auth.authenticationProvider(refreshTokenAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("Configuring security for web applications.");

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(authWhiteList).permitAll()
                .and()
                .addFilterBefore(buildLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildRefreshTokenRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtRequestFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addExposedHeader("Location");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private JwtRequestFilter buildJwtRequestFilter() {
        List<String> pathsToSkip = Arrays.asList(authWhiteList);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_AUTH_ENTRY_POINT);

        JwtRequestFilter filter = new JwtRequestFilter(
                failureHandler,
                tokenExtractor,
                matcher
        );
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    private RefreshTokenRequestFilter buildRefreshTokenRequestFilter() {
        RefreshTokenRequestFilter refreshTokenRequestFilter = new RefreshTokenRequestFilter(
                REFRESH_TOKEN_ENTRY_POINT,
                refreshTokenAuthenticationSuccessHandler,
                failureHandler,
                objectMapper
        );
        refreshTokenRequestFilter.setAuthenticationManager(this.authenticationManager);
        return refreshTokenRequestFilter;
    }

    private LoginRequestFilter buildLoginProcessingFilter() {
        LoginRequestFilter loginRequestFilter = new LoginRequestFilter(
                LOGIN_ENTRY_POINT,
                successHandler,
                failureHandler,
                objectMapper
        );
        loginRequestFilter.setAuthenticationManager(this.authenticationManager);
        return loginRequestFilter;
    }
}

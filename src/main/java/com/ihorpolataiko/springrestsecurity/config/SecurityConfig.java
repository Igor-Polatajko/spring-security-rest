package com.ihorpolataiko.springrestsecurity.config;

import com.ihorpolataiko.springrestsecurity.security.TokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private AuthenticationProvider authenticationProvider;

    private TokenFilter tokenAuthFilter;

    private AuthenticationEntryPoint authenticationEntryPoint;

    private AccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(AuthenticationProvider authenticationProvider,
                          TokenFilter tokenAuthFilter,
                          AuthenticationEntryPoint authenticationEntryPoint,
                          AccessDeniedHandler accessDeniedHandler) {

        this.authenticationProvider = authenticationProvider;
        this.tokenAuthFilter = tokenAuthFilter;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout().disable()
                .addFilterBefore(tokenAuthFilter, BasicAuthenticationFilter.class)
                .antMatcher("/**")
                .authenticationProvider(authenticationProvider)
                .authorizeRequests()
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .antMatchers("/users/**", "/logout/**").authenticated()
                .antMatchers("/login").permitAll()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler);
    }

}

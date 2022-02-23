package com.lnu.RESTfulCafe.security;

import com.lnu.RESTfulCafe.security.filter.CustomAuthenticationFilter;
import com.lnu.RESTfulCafe.security.filter.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;


@Configuration @EnableWebSecurity @RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/login");
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);

        // Restricted GET methods (for Employees and Admin)
        http.authorizeRequests().antMatchers(HttpMethod.GET, "/users/**", "/roles/**", "/subscriptions/**", "/employees/**", "/customers/**").hasAnyAuthority("ROLE_EMPLOYEE","ROLE_MANAGER","ROLE_ADMIN");

        // Restricted access for Subscription URIs
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/subscriptions/**", "/subscribe/**").hasAnyAuthority("ROLE_CUSTOMER","ROLE_EMPLOYEE","ROLE_MANAGER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/subscriptions/**").hasAnyAuthority("ROLE_EMPLOYEE","ROLE_MANAGER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/subscriptions/**").hasAnyAuthority("ROLE_EMPLOYEE","ROLE_MANAGER","ROLE_ADMIN");

        // Restricted POST, PUT, DELETE routes
        http.authorizeRequests().antMatchers(HttpMethod.POST, "/beans/**", "/drinks/**", "/users/**", "/roles/**").hasAnyAuthority("ROLE_EMPLOYEE","ROLE_MANAGER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.PUT, "/beans/**", "/drinks/**", "/users/**", "/roles/**", "/orders/**").hasAnyAuthority("ROLE_EMPLOYEE","ROLE_MANAGER","ROLE_ADMIN");
        http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/beans/**", "/drinks/**", "/users/**", "/roles/**", "/orders/**").hasAnyAuthority("ROLE_EMPLOYEE","ROLE_MANAGER","ROLE_ADMIN");

        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}

package ru.voskhod.springdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class DemoSecurityConfig extends WebSecurityConfigurerAdapter {

    // add a reference to security DataSource
    private DataSource securityDataSource;

    @Autowired
    public void setSecurityDataSource(DataSource securityDataSource) {
        this.securityDataSource = securityDataSource;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // add users for in-memory authentication

        // use jdbc authentication
        auth.jdbcAuthentication().dataSource(securityDataSource);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // restrict access based on HttpServletRequest
        http.authorizeRequests()
                // private resources
                .antMatchers(HttpMethod.GET, "/api/customers").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.GET, "/api/customers/**").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                .antMatchers(HttpMethod.POST, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.PUT, "/api/customers").hasAnyRole("MANAGER", "ADMIN")
                .antMatchers(HttpMethod.DELETE, "/api/customers/**").hasRole("ADMIN")
                .and()
                .httpBasic()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
}

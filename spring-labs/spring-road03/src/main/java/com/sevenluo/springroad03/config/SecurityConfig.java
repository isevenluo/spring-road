package com.sevenluo.springroad03.config;

import com.sevenluo.springroad03.dao.ViewerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启Spring方法级安全
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ViewerRepository viewerRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(
                new UserDetailsService() {
                    @Override
                    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                        return null;
                    }
                }
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
        .and().formLogin().failureUrl("/login?error=true");
    }


}

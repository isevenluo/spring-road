package com.sevenluo.springroad03.config;

import com.sevenluo.springroad03.dao.ViewerRepository;
import com.sevenluo.springroad03.entity.Viewer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Resource
    private ViewerRepository viewerRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(username -> {
            viewerRepository.save(new Viewer(username, "{noop}123","ADMIN"));
            logger.info(viewerRepository.findViewerByUsername(username).toString());
            return viewerRepository.findViewerByUsername(username);
        });
    }

}

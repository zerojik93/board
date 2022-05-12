package com.project.board.config;

import org.springframework.boot.autoconfigure.security.reactive.PathRequest;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/files/**").permitAll()
//                .antMatchers("/**").permitAll()
                .anyRequest().authenticated();
        http
                .oauth2Login();

        http.cors().and();
        http.csrf().disable();  //csrf 보안 설정을 비활성화, 해당 기능을 사용하기 위해서는 프론트단에서 csrf토큰값 보내줘야함


    }
}

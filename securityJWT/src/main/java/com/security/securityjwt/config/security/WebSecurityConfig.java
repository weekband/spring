package com.security.securityjwt.config.security;

import com.security.securityjwt.config.exception.CustomAccessDeniedHandler;
import com.security.securityjwt.config.exception.UserException;
import com.security.securityjwt.config.exception.UserExceptionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.ldap.LdapBindAuthenticationManagerFactory;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;


@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    //암호화에 필요한 PasswordEncoder 를 Bean으로 등록합니다.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }



    @Bean
    public WebSecurityCustomizer securityCustomizer() {
        return web -> web.ignoring().antMatchers("/resources/**", "/h2-console/**");


    }

    private static final RequestMatcher ROLE_USER_REQUIRED = new OrRequestMatcher(
            new AntPathRequestMatcher("/api/v1/user/{email}/search")

    );

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests() //요청에 대한 사용권한 체크
                .antMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(ROLE_USER_REQUIRED).hasRole("USER")
                .antMatchers("/**").permitAll().and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)// JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
                .exceptionHandling()
                .accessDeniedHandler(new CustomAccessDeniedHandler()).and()
                .build();
    }


}

package com.example.shoppro.config;

import jakarta.servlet.annotation.WebListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@WebListener
public class SecurityConfig {

    @Bean
    SecurityFilterChain filterChain (HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                // 권한 페이지 접속권한
                .authorizeHttpRequests(
                        authorization -> authorization
                                .requestMatchers("/members/login/**").permitAll()       // 로그인 페이지는 누구나 접속이 가능한 권한을 부여
                                .requestMatchers("/board/register").authenticated()  // 로그인 한 사람만 접속 가능
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/user/list").hasRole("ADMIN")
                                .anyRequest().permitAll()         // 그 외 다 열어
//                                .anyRequest().authenticated()   // 그 이외에는 다 로그인해서 접속해

                )

                // 위변조 방지 웹에서 form 태그 변경 등의 변조를 방지
                .csrf(csrf -> csrf.disable())
                // 로그인
                .formLogin(
                        formLogin -> formLogin.loginPage("/members/login") // 기본 로그인 페이지 지정
                                .defaultSuccessUrl("/")                 // 로그인이 성공했다면
                                .usernameParameter("email")             // 로그인 <input name="email">
                        // 컨트롤러로 보낼 때 ~~~
                )
                // 로그아웃
                .logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout"))   // 로그아웃, a 태그라고 생각
                                // <a href="/user/logout">잘가~</a>
                                .invalidateHttpSession(true)            // 세션 초기화
                                .logoutSuccessUrl("/")                  // localhost:8090 으로 간다.
                        // dns 주소일 경우 www.naver.com 까지 로 간다.
                        // 컨트롤러에서 만들어줄껄?
                )

        // 예외처리 // 로그인이 되지 않은 사용자, 권한이 없는 사용자 접속시 취할 행동들
                .exceptionHandling(
                        a -> a.authenticationEntryPoint(new CustomAuthenticationEntryPoint()).accessDeniedHandler(new CustomAccessDeniedHandler())
                )

        ;
        return httpSecurity.build();
    }

    @Bean
    PasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();

    }

}

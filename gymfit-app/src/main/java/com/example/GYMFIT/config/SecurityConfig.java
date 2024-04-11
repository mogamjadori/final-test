package com.example.GYMFIT.config;

import com.example.GYMFIT.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final UserService userService;

    public SecurityConfig(UserService userService) {
        this.userService = userService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity hs) throws Exception {

        return hs.csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .requestMatchers("/img/**").permitAll()
                        .requestMatchers("/favicon.ico").permitAll()
//                        .requestMatchers("/GYMFIT/").permitAll()
                        .requestMatchers("/WelcomeGymFit/login").permitAll()
                        .requestMatchers("/WelcomeGymFit/new-user").permitAll()
                        .requestMatchers("/WelcomeGymFit/new-page").permitAll()
//                        .requestMatchers("/members/**").hasRole("USER")
                        .anyRequest().authenticated()
                )

                .formLogin(formLogin -> formLogin
                        .loginPage("/WelcomeGymFit/login")    //커스텀 로그인 페이지 주소
                        .loginProcessingUrl("/login-process")    //로그인 페이지를 login으로 하면 이 항목을 별도 지정해야 무한루프가 없음
                        .usernameParameter("mem_lgn_id")    //아이디 값
                        .passwordParameter("mem_lgn_pw")    //비밀번호 값
                        .defaultSuccessUrl("/GYMFIT/", true)
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/GYMFIT/")
                        .invalidateHttpSession(true)
                        .permitAll()
                )

                .userDetailsService(userService)
//                .httpBasic(Customizer.withDefaults())
//                .passwordEncoder(passwordEncoder())
//                .exceptionHandling(handling -> handling
//                                .accessDeniedHandler(accessDeniedHandler)
//                        .authenticationEntryPoint()   //401
//                        .authenticationFailureHandler()
//                        .authenticationSuccessHandler()
//                        .logoutSuccessHandler()
//                        .sessionInformationExpiredStrategy()
//                )
                .build();

    }
}
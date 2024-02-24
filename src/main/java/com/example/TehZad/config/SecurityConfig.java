package com.example.TehZad.config;

import com.example.TehZad.user.model.Permission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("userService") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests(aut -> aut
                .requestMatchers(HttpMethod.POST, "/api/welcome").authenticated()
                .requestMatchers(HttpMethod.GET, "/api/welcome").permitAll()
                //получение своего профиля
                .requestMatchers(HttpMethod.GET, "/api/v1/user/myprofile")
                .authenticated()
                //подучение всех пользователей
                .requestMatchers(HttpMethod.GET, "/api/v1/user**")
                .hasAuthority(Permission.READ.getPermission())
                //получение пользователя по id
                .requestMatchers(HttpMethod.GET, "/api/v1/user/{id}**")
                .hasAuthority(Permission.READ.getPermission())
                //поиск пользователя по имени
                .requestMatchers(HttpMethod.GET, "/api/v1/user/search?text")
                .hasAuthority(Permission.READ.getPermission())
                //добавление нового пользователя
                .requestMatchers(HttpMethod.POST, "/api/v1/user")
                .hasAuthority(Permission.CHANGE.getPermission())
                //удаление пользователя по id
                .requestMatchers(HttpMethod.DELETE, "/api/v1/user/{id}")
                .hasAuthority(Permission.CHANGE.getPermission())
                //проекты
                //получение проектов
                .requestMatchers(HttpMethod.GET, "/api/v1/project").authenticated()
                //получение одного проекта
                .requestMatchers(HttpMethod.GET, "/api/v1/project/{id}").authenticated()
                //создание проекта
                .requestMatchers(HttpMethod.POST, "/api/v1/project")
                .hasAuthority(Permission.CHANGE.getPermission())
                // удаление проекта
                .requestMatchers(HttpMethod.DELETE, "/api/v1/project/{id}")
                .hasAuthority(Permission.CHANGE.getPermission())
                // изменение проекта
                .requestMatchers(HttpMethod.PATCH, "/api/v1/project/{id}")
                .hasAuthority(Permission.CHANGE.getPermission())
                // изменение статуса проекта
                .requestMatchers(HttpMethod.POST, "/api/v1/project/{id}/status**")
                .hasAuthority(Permission.CHANGE.getPermission())
                // по таскам
                .requestMatchers("/api/v1/task").authenticated()
                .requestMatchers("/api/v1/task/{id}").authenticated()
                .requestMatchers("/api/v1/task/{id}/status**").authenticated()

        )
                .formLogin(withDefaults())
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }


}



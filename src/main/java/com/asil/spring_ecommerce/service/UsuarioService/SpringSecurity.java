package com.asil.spring_ecommerce.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurity {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/css/**", "/js/**", "/images/**", "/vendor/**").permitAll() // Permitir acceso a archivos estáticos
                .requestMatchers("/","/usuario/acceder","/usuario/login", "/usuario/form/registro","/usuario/save"
                ,"/productohome/{id}","/cart","/delete/cart/{id}","/carrito","/orden","/saveOrder","/buscar"
                 ,"/usuario/compras","/usuario/salir","/usuario/detalle/{id}").permitAll() // Permitir acceso a las rutas públicas
                .requestMatchers("/administrador/**").hasRole("admin")
                .requestMatchers("/productos").hasRole("ADMIN")
                
            )
             .formLogin(form -> form
                .loginPage("/")
                .permitAll()
                .defaultSuccessUrl("/usuario/acceder")
            ) 
            .logout(logout -> logout.permitAll())
            .exceptionHandling(exception -> exception.accessDeniedPage("/403"));
    
        return http.build();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authenticationManagerBuilder.build();
    }
}

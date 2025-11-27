package nl.miwnn.ch17.pixeldae.goudvinkje.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author Simon van der Kooij
 * configure the security for the GoudVinkje application
 */

@Configuration
@EnableWebSecurity
public class GoudVinkjeSecurityConfig {

    private static final int TOKEN_VALIDITY_SECONDS_DEFAULT = 60 * 60 * 24 * 30;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((request) -> request
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/css/**", "/webjars/**", "/images/**", "/fonts/**").permitAll()
                        .requestMatchers("/gebruiker/**").hasRole("ADMIN")
                        .requestMatchers("/recept/").hasRole("USER")
                        .requestMatchers("/ingredient/aanvullen/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/ingredient/cal-opslaan/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/ingredient/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/recept/overzicht", true)
                        .failureUrl("/?error=true")
                        .permitAll()
                )
                .rememberMe(remember -> remember
                        .key("uniekeSleutelVoorHashing123")
                        .tokenValiditySeconds(TOKEN_VALIDITY_SECONDS_DEFAULT)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

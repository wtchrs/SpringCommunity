package wtchrs.SpringCommunity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import wtchrs.SpringCommunity.auth.JpaAuthProvider;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JpaAuthProvider jpaAuthProvider)
            throws Exception {

        http.authenticationProvider(jpaAuthProvider);
        http.formLogin(form -> form
                .loginPage("/sign-in")
                .loginProcessingUrl("/sign-in")
                .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                .permitAll()
        );
        http.logout(logout -> logout.logoutUrl("/sign-out"));
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/boards/*/articles/write").authenticated()
                .requestMatchers("/boards/*/articles/*/delete").authenticated()
                .anyRequest().permitAll()
        );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}

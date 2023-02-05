package wtchrs.SpringCommunity.view;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests().requestMatchers("/**").permitAll()
            .and()
            .formLogin().loginPage("/sign-in").usernameParameter("username").passwordParameter("password")
            .defaultSuccessUrl("/").permitAll();

        return http.build();
    }
}

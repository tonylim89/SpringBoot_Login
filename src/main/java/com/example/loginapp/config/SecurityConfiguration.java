package com.example.loginapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// @formatter:off
		http
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/login","/register","/registermanager","/lang").permitAll()
						.requestMatchers("/css/**","/js/**","/images/**").permitAll()
						.requestMatchers("/restricted").hasRole("MANAGER")
						.anyRequest().authenticated()
				)
				.httpBasic(withDefaults())
				.formLogin(form -> form
					.loginPage("/login")
					.defaultSuccessUrl("/welcome", true)
					.failureUrl("/login?error=true")
					.permitAll()
				)
				.logout(logout -> logout
					.logoutUrl("/logout")
					.logoutSuccessUrl("/login")
					.invalidateHttpSession(true)
					.deleteCookies("JSESSIONID")
			);
		// @formatter:on
		return http.build();
	}
}
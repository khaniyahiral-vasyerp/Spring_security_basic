package com.spring.security.springsecuritybasic.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Autowired
	private CustomSuccessAuthHandler customSuccessAuthHandler;

    @Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(Customizer.withDefaults())
		
				.authorizeHttpRequests(
						authorizeRequests -> authorizeRequests
								.requestMatchers("/login").permitAll()
								.requestMatchers("/adminPage/**").hasRole("ADMIN")
								.requestMatchers("/userPage/**").hasAnyRole("USER","ADMIN")
								.anyRequest().authenticated())
								
				.formLogin(formLogin -> formLogin
										.successHandler(customSuccessAuthHandler))

				.logout(logout -> logout
                						.permitAll());
										
		return http.build();
	}


    @Bean
	public InMemoryUserDetailsManager userDetailManager(
			@Value("${security.users.admin.name}") String adminUsername,
			@Value("${security.users.admin.password}") String adminPassword,
			@Value("${security.users.admin.roles}") String adminRoles,
			@Value("${security.users.user.name}") String userUsername,
			@Value("${security.users.user.password}") String userPassword,
			@Value("${security.users.user.roles}") String userRoles) {
                
        UserDetails user = User.withUsername(userUsername)
                        .password(passwordEncoder().encode(userPassword))
                        .roles(userRoles)
                        .build();

                        
		UserDetails admin = User.withUsername(adminUsername)
				.password(passwordEncoder().encode(adminPassword))
				.roles(adminRoles.split(","))
				.build();
	
		return new InMemoryUserDetailsManager(admin, user);
	}

}

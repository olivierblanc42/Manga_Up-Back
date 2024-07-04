package fr.projet.manga_up.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import fr.projet.manga_up.controller.MangaController;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	private static final Logger LOGGER=LoggerFactory.getLogger(MangaController.class);

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
    	
    	LOGGER.info("Méthode securityFilterChain");
    	
		return httpSecurity
				.authorizeHttpRequests(auth->auth.requestMatchers(
				"/api/comments/**",
				"/api/pictures/**",
	        	"/api/mangas/**",
	        	"/api/genres/**",
				"/api/categories/**",
				"/api/users/**",
	        	"/swagger-ui/**", 
	        	"/v3/api-docs/**", 
	        	"/swagger-ui.html", 
	        	"/war/**, /jar/**").permitAll())
				.csrf(AbstractHttpConfigurer::disable)
				.build();
	    }
}

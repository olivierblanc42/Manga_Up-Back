package fr.projet.manga_up.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import fr.projet.manga_up.controller.MangaController;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	// Déclaration d'un Logger pour cette classe, utilisant SLF4J
	private static final Logger LOGGER = LoggerFactory.getLogger(MangaController.class);

	// Configuration du filtre de sécurité HTTP
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		//Permet de logger l'info concernant la sécurité
		LOGGER.info("Méthode securityFilterChain");

		// Configuration de la sécurité HTTP
		//Demande à ce que toute requête soit authentifiée
		//Seules les personnes ayant le rôle ROLE_USER sont autorisées
		//L'authentification Basic est utilisée

		http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/api/login").permitAll()
				.requestMatchers("/api/user").hasAnyRole("USER", "ADMIN")
				.requestMatchers("/api/admin").hasRole("ADMIN")
				.anyRequest().permitAll());
		//http.formLogin(Customizer.withDefaults());
		//http.httpBasic(Customizer.withDefaults());
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.headers(headers -> headers
				.contentSecurityPolicy(csp -> csp
						.policyDirectives("script-src 'self' https://trustedscripts.example.com; object-src https://trustedplugins.example.com; report-uri /csp-report-endpoint/")
				)

				.xssProtection(xss -> xss
						.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)
				)

				.frameOptions(frameOptions -> frameOptions
						.sameOrigin()
				)
		);
		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
				/*.requestMatchers(
								"/api/comments/**",
								"/api/pictures/**",
								"/api/mangas/**",
								"/api/genres/**",
								"/api/categories/**",
								"/api/addresses/**")
						.permitAll()
						.anyRequest().authenticated());*/
		//.csrf(csrfCustomizer -> csrfCustomizer.ignoringRequestMatchers("/api/**"));

		//.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));



		//http.csrf(AbstractHttpConfigurer::disable);
		// Retourne le filtre de sécurité configuré


	/*
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}*/


	// Configuration d'un utilisateur en mémoire pour les tests
    /*@Bean
    public UserDetailsService userDetailsService() {
       UserDetails user = User.withUsername("user")
             .password(passwordEncoder().encode("password"))// Mot de passe
             .roles("USER") // Rôle de l'utilisateur
             .build();

       UserDetails admin = User.withUsername("admin")
             .password(passwordEncoder().encode("adminPass")) // Mot de passe
             .roles("ADMIN") // Rôle de l'utilisateur
             .build();

       JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);

       userDetailsManager.createUser(user);
       userDetailsManager.createUser(admin);

       return userDetailsManager;

       // Retourne un gestionnaire de détails utilisateur en mémoire avec cet utilisateur
       //return new InMemoryUserDetailsManager(user, admin);
    }*/

	// Bean pour encoder les mots de passe


}


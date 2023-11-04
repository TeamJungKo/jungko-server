package com.jungko.jungko_server.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
		return httpSecurity
//				.cors()
//				.configurationSource(corsConfigurationSource())
//				.and()
				.csrf().disable()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
//				.oauth2ResourceServer()
//				.bearerTokenResolver(new DefaultBearerTokenResolver())
//				.jwt()
//				.authenticationManager(jwtAuthenticationManager)
//				.and()
//				.accessDeniedHandler(jwtAccessDeniedHandler)
//				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
//				.and()
				.formLogin().disable()
				.build();
	}
}

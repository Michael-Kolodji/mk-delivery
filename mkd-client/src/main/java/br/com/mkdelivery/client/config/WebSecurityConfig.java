package br.com.mkdelivery.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.csrf().disable().authorizeRequests()
			.antMatchers("/api/clients/**", "/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.csrf().disable()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.headers().frameOptions().sameOrigin();
//			.and()
//			.addFilterBefore(new AuthenticationFilter(tokenService,autenticacaoService), UsernamePasswordAuthenticationFilter.class)
//			.addFilterBefore(new JWTLoginFilter("/login", authenticationManager()),
//	                UsernamePasswordAuthenticationFilter.class)
//			
//			.addFilterBefore(new JWTAuthenticationFilter(),
//	                UsernamePasswordAuthenticationFilter.class)
//			
//			.headers((headers) ->
//						//disabledAllHeadersSecurity(headers)
//						configureHeaders(headers)
//					);
			
	}
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}

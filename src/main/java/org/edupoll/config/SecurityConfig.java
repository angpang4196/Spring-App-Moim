package org.edupoll.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(t -> t.disable());
		http.authorizeHttpRequests(	t -> t
				.requestMatchers("/", "/user/**", "/moims","/status").permitAll()
				.anyRequest().authenticated());
		http.formLogin(t -> t.usernameParameter("loginId").passwordParameter("loginPass")
				.loginPage("/user/login").permitAll()
				.defaultSuccessUrl("/", true)
			);
		
		// loginPage 설정해둔 건 controller 로 만들면 되고, 
		// loginProcessingURL 은 만드는게 아님.(Spring에서 인증을 처리할 경로를 바꾸는 - action 경로
		
		http.logout(t->t
				.logoutUrl("/logout")
				.logoutSuccessUrl("/"));		
		http.exceptionHandling(t -> t
				.accessDeniedPage("/access/denied"));
		
		return http.build();
	}

}

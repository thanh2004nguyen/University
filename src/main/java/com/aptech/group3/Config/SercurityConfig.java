package com.aptech.group3.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.aptech.group3.serviceImpl.CustomAccessDeniedHandler;
import com.aptech.group3.serviceImpl.CustomerUserDetailService;
import com.aptech.group3.serviceImpl.JwtAuthenticationFilter;
import com.aptech.group3.serviceImpl.UserServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@ComponentScan("com.aptech.group3")
public class SercurityConfig {

	@Autowired
	UserServiceImpl userService;

	@Autowired
	CustomerUserDetailService customerUserDetailService;

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	@Bean
	@Order(3)
	 SecurityFilterChain api(HttpSecurity http) throws Exception {
		return http.securityMatcher("/api/**").authorizeHttpRequests(au -> {
			au.requestMatchers(HttpMethod.GET, "/api/login", "/api/logout","/api/ws","/api/public/**").permitAll();
			au.requestMatchers(HttpMethod.POST, "/api/login","/api/ws", "api/refreshtoken","/api/public/**").permitAll();
			au.requestMatchers("/api//admin/**").hasAuthority("ADMIN");
			au.requestMatchers("/api/teacher/**").hasAuthority("TEACHER");
			au.requestMatchers("/api/student/**").hasAuthority("STUDENT");
			au.anyRequest().authenticated();
		})
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.csrf(csrf -> csrf.disable()) // Often, CSRF is disabled for APIs
				.build();
	}
	
	@Bean
	@Order(4)
	 SecurityFilterChain app(HttpSecurity http) throws Exception {
		return http.securityMatcher("/**").authorizeHttpRequests(au -> {
			au.requestMatchers(HttpMethod.GET, "/login", "/logout", "/css/**", "/fonts/**", "/image/**", "/images/**", "/js/**,/vendor/**").permitAll();
			au.requestMatchers(HttpMethod.POST, "/login").permitAll();
			au.anyRequest().permitAll();
		}).formLogin(frm -> {
			frm.loginPage("/login").usernameParameter("email").passwordParameter("password").permitAll()
					.successHandler(new CustomAuthenticationSuccessHandler())
					.failureUrl("/login?sucess=false");
		}).logout(lo -> {
			lo.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		}).exceptionHandling(handling -> handling.accessDeniedHandler(new CustomAccessDeniedHandler()))
				.csrf(csrf -> csrf.disable())
				.build();
	}
	
	@Bean
	@Order(1)
	 SecurityFilterChain web(HttpSecurity http) throws Exception {
		return http.securityMatcher("/web/**").authorizeHttpRequests(au -> {
			au.requestMatchers(HttpMethod.GET, "/login", "/web/news/**", "/css/**", "/fonts/**", "/image/**", "/images/**", "/js/**,/vendor/**").permitAll();
			au.requestMatchers(HttpMethod.POST, "/login").permitAll();
			au.anyRequest().authenticated();
		}).formLogin(frm -> {
			frm.loginPage("/login").usernameParameter("email").passwordParameter("password").permitAll()
					.defaultSuccessUrl("/index").failureUrl("/login?sucess=false");
		}).logout(lo -> {
			lo.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		}).exceptionHandling(handling -> handling.accessDeniedHandler(new CustomAccessDeniedHandler()))
				.csrf(csrf -> csrf.disable())
				.build();
	}
	 
	
	@Bean
	@Order(2)
	 SecurityFilterChain admin(HttpSecurity http) throws Exception {
		return http.securityMatcher("/admin/**").authorizeHttpRequests(au -> {
			au.anyRequest().authenticated();
		}).formLogin(frm -> {
			frm.loginPage("/login").usernameParameter("email").passwordParameter("password").permitAll()
					.defaultSuccessUrl("/index").failureUrl("/login?sucess=false");
		}).logout(lo -> {
			lo.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login");
		}).exceptionHandling(handling -> handling.accessDeniedHandler(new CustomAccessDeniedHandler()))
				.csrf(csrf -> csrf.disable())
				.build();
	}
	
	
	

	@Autowired
	void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
		builder.userDetailsService(customerUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
	}

}
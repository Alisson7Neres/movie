package com.movie.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.movie.model.PessoaModel;

@Configuration
@EnableWebSecurity
public class WebConfiSecurity extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	@Override // Configura as solicitações de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers("/").permitAll()
		.and()
		.authorizeRequests().antMatchers("/h2-console/**").permitAll()
		.and()
		.headers().frameOptions().disable()
		.and()
		.csrf().ignoringAntMatchers("/h2-console/**")
		.and()
		.cors().disable();
		
		http.csrf()
		.disable() // Desativa as configurações padrão de memória.
		.authorizeRequests() // permitir restringir acessos.
		.antMatchers(HttpMethod.GET, "/" , "/index", "/minhaconta", "/random", "/pesquisar", "/filmes", "/login", "/").permitAll() // Qualquer usuário acessa a página
		.antMatchers(HttpMethod.GET, "/login").hasAnyRole("") // Qualquer usuário acessa a página
		.antMatchers("/pesquisar", "/filmes", "/random/movie", "/conta/create","/h2/**", "/h2-console/**").permitAll()
		.antMatchers(HttpMethod.GET, "/logado").hasAnyRole("")
		.anyRequest().authenticated()
		.and().formLogin() // Permite quaquer usuárioa
		.loginPage("/login")
		.defaultSuccessUrl("/conta/logado", true)
		.permitAll()
		.and().logout().logoutSuccessUrl("/login") // Mapeia URL de Logout e inválida usuárioa autenticado
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
		
		
	}
	
	
	@Override // Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(implementacaoUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
	}

	/*
	@Bean
	@Override
	protected UserDetailsService userDetailsService() {
		PessoaModel pessoaModel = new PessoaModel();
		UserDetails user = User
				.withDefaultPasswordEncoder()
				.username(pessoaModel.getEmail())
				.password(pessoaModel.getSenha())
				.build();
		return new InMemoryUserDetailsManager(user);
	}
	*/
	
	
}
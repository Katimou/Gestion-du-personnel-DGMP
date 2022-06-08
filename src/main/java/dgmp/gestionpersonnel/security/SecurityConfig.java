package dgmp.gestionpersonnel.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter 
{
	@Autowired private UserDetailsService userDetailsService;
	//@Autowired LoginSuccessListener loginSuccessListener;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring()
				.antMatchers("/resources/**", "/static/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http .authorizeRequests() .antMatchers("/agents/login", "/login").permitAll();
		  http.formLogin().loginPage("/agents/login").defaultSuccessUrl("/agents/accueil");
			//http.formLogin().defaultSuccessUrl("/agents/accueil");
			http .authorizeRequests() .antMatchers("/todo/**").hasRole("USER");
		  	http.authorizeRequests().anyRequest().authenticated();*/

		http.authorizeRequests()
				.antMatchers("/agents/login")
				.permitAll()
				.antMatchers("/todo/**").access("hasRole('ROLE_ADMIN')")
				.and()
				.formLogin(form -> form
						.loginPage("/agents/login")
						.defaultSuccessUrl("/agents/accueil")
						.failureUrl("/agents/login?error=true")
				);


		 /*.antMatchers("/resources/**", "/css/**", "/js/**", "/img/**").permitAll()
		 .anyRequest().authenticated() .and() .formLogin() .loginPage("/agents/login")
		 .defaultSuccessUrl("/agents/accueil") .permitAll() .and()
		 .logout() .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		 .logoutSuccessUrl("/home") .permitAll();*/
		 //.exceptionHandling().accessDeniedPage("/403");
}
	@Bean
	PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}

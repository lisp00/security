package org.zerock.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.zerock.model.CustomUser;

import lombok.Setter;
import lombok.extern.log4j.Log4j;



// CustomAuthenticationProvider 완성본
@Log4j
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
	@Setter(onMethod_ = @Autowired)
	private CustomUserDetailsService customUserDetailsService;
	
	@Autowired
	private PasswordEncoder pwencoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("MyAuthenticationProvider :::: " + authentication.toString());
		String username = authentication.getName().toString();
		String password = authentication.getCredentials().toString();
		log.info("CustomAuthenticationProvider :::: " + username + ", " + password);
		
		CustomUser customUser = (CustomUser) customUserDetailsService.loadUserByUsername(username);
		if(!customUser.getMember().getEnabled().equals("1")) {
			log.info("This Account is disabled");
		}
		log.info("Password : " + password + ", " + customUser.getPassword());
		boolean state = pwencoder.matches(password, customUser.getPassword());
		log.info("Password Matches :::: + " + state);
		
		UsernamePasswordAuthenticationToken authUser = null;
		if(state) {
			log.info("Login Success");
			authUser = new UsernamePasswordAuthenticationToken(customUser, customUser.getPassword(),customUser.getAuthorities());
			log.info("provider :::::::::: " + authUser.toString());
		} else {
			throw new BadCredentialsException("Password does not match stored value : " + username);
		}
		return authUser;
	}
	@Override
	public boolean supports(Class<?> authentication) {
		return true;
	}
	
}

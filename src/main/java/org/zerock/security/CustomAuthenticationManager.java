package org.zerock.security;

import java.util.Collection;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.zerock.model.MemberVO;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomAuthenticationManager implements AuthenticationManager {
	private UserDetailsService userDetailsService;
    private PasswordEncoder encoder;
    
    public CustomAuthenticationManager(UserDetailsService userDetailsService,PasswordEncoder encoder) {
        this.userDetailsService = userDetailsService;
        this.encoder = encoder;
    }

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.debug("CustomUserAuthenticationProvider.authenticate :::: {}" + authentication.toString());
        
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken)authentication;
        
        String userId = token.getName();
        
        MemberVO memberVO = null;
        
        if(!StringUtils.isEmpty(userId)) {
        	memberVO = (MemberVO) userDetailsService.loadUserByUsername(userId);
        }
        
        if(ObjectUtils.isEmpty(memberVO)) {
            throw new UsernameNotFoundException("Invalid username");
        }
        
        String password = memberVO.getPassword();
        
        if(password.equals(encoder.encode(String.valueOf(token.getCredentials())))) {
            throw new BadCredentialsException("Invalid password");
        }
        
        return new UsernamePasswordAuthenticationToken(memberVO, password, (Collection<? extends GrantedAuthority>) memberVO.getAuthList());
	}
	
}

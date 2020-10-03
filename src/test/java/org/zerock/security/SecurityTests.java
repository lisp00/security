package org.zerock.security;

import java.util.Collection;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml", "file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class SecurityTests {
	@Autowired
	private CustomAuthenticationProvider authenticationProvider;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Ignore
	@Test
	public void authenticationProviderTest() {
		Authentication authentication = new UsernamePasswordAuthenticationToken("admin", "1234");
		authenticationProvider.authenticate(authentication);
	}
	
	@Test
	public void tokenTest() {
		log.info("TokenValue : " +jwtTokenProvider.createToken("admin", null));
	}
	
}

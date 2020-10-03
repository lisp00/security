package org.zerock.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import lombok.extern.log4j.Log4j;

@Log4j
public class WebLoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {
	public WebLoginFailureHandler() {
		this.setDefaultFailureUrl("/admin/customLogin");
	}

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		log.info("Login Failed... - {" + request.getParameter("username") + "}");
		super.onAuthenticationFailure(request, response, exception);
	}
}
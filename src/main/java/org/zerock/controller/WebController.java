package org.zerock.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

//샘플 컨트롤러
@Log4j
@RequestMapping("/web")
@Controller
public class WebController {
	@GetMapping("/all")
	public void doAll() {
		log.info("do allcan access everybody");
	}
	
	@GetMapping("/manager")
	public void doMember() {
		log.info("logined manager");
	}
	
	@GetMapping("/admin")
	public void doAdmin() {
		log.info("admin only");
	}
	
	@GetMapping("/accessError")
	public void accessDenied(Authentication auth, Model model) {
		log.info("access Denied : " + auth);
		
		model.addAttribute("msg", "Access Denied");
	}
	@GetMapping("/customLogin")
	public void loginInput(String error, String logout, Model model) {
		log.info("error: " + error);
		log.info("logout: " + logout);
		
		if(error != null) {
			model.addAttribute("error", "Login Error Check your Account");
		}
		
		if(logout != null) {
			model.addAttribute("logout", "Logout!");
		}
	}
	
	@GetMapping("/customLogout")
	public void logoutGet() {
		log.info("custom logout");
	}
}

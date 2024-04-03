package com.narasimham.springbootsecuritydemo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

	@GetMapping("/welcome")
	public String getWelcome() {
		System.out.println("welcome controller , welome page rendered...");
		return "welcome";
	}
}

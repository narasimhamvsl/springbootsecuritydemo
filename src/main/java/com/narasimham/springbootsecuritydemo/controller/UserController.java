package com.narasimham.springbootsecuritydemo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.narasimham.springbootsecuritydemo.entity.User;
import com.narasimham.springbootsecuritydemo.repository.UserRepository;
import com.narasimham.springbootsecuritydemo.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/tenant")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	UserService userService;
	
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("logged in user is : "+userName);
		
		User user = userRepository.getUserByUserName(userName);
		System.out.println("USER : "+user);
		model.addAttribute("user",user);

	}

	@RequestMapping("/")
	public String dashBoard(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","User - dashboard");
		return "tenant/tenant_dashboard";
	}
	
	@GetMapping("/user_home")
	public String userHome(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","User - home");

		return "tenant/user_home";
	}
	
	@GetMapping("/user_profile")
	public String userProfile(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","User - home");

		return "tenant/user_profile";
	}
	
	@GetMapping("/user_search")
	public String userSearch(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","User - home");

		return "tenant/user_search";
	}
	
	@GetMapping("/user_pay")
	public String userPay(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","User - home");

		return "tenant/user_pay";
	}
	
	@GetMapping("/user_reports")
	public String userReports(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","User - home");

		return "tenant/user_reports";
	}
}
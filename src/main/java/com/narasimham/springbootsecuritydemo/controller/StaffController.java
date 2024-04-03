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
@RequestMapping("/staff")
public class StaffController {
	
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
		model.addAttribute("title","Staff - dashboard");
		System.out.println("in staff /");
		return "staff/staff_dashboard";
	}
	
//	@GetMapping("/user_home")
//	public String userHome(Model model, HttpSession session, Principal principal) {
//		model.addAttribute("title","Sraff - home");
//
//		return "staff/user_home";
//	}
	
	@GetMapping("/staff_profile")
	public String userProfile(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","Staff - profile");

		return "staff/staff_profile";
	}
	
	@GetMapping("/staff_search")
	public String userSearch(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","Staff - search");

		return "staff/staff_search";
	}
	
	@GetMapping("/staff_pay")
	public String userPay(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","Staff - pay");

		return "staff/staff_pay";
	}
	
	@GetMapping("/staff_reports")
	public String userReports(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","Staff - reports");

		return "staff/staff_reports";
	}
}

package com.narasimham.springbootsecuritydemo.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.narasimham.springbootsecuritydemo.entity.User;
import com.narasimham.springbootsecuritydemo.exception.EmailIdAlreadyRegisteredException;
import com.narasimham.springbootsecuritydemo.exception.MobileNumberAlreadyRegisteredException;
import com.narasimham.springbootsecuritydemo.helper.Message;
import com.narasimham.springbootsecuritydemo.repository.UserRepository;
import com.narasimham.springbootsecuritydemo.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private BCryptPasswordEncoder passwordEncoder;

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
	public String adminDashboard(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","Admin - dashboard");

		return "admin/admin_dashboard";
	}

	@RequestMapping("/admin_search")
	public String searchUsers(Model model, HttpSession session) {
		model.addAttribute("title","Admin - User Search");
		List<User> listUsers = userRepository.findAll();
		System.out.println("list users : "+listUsers);
		model.addAttribute("user",listUsers);
		model.addAttribute("noOfUsers",listUsers.size());
		return "admin/admin_search";
	}

	@RequestMapping("/admin_reports")
	public String adminReports(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","Admin - Reports");

		return "admin/admin_reports";
	}
	
	@RequestMapping("/add_staff")
	public String addStaff(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","Admin - addStaff");

		return "admin/add_staff";
	}

	@RequestMapping("/do_register")
	public String adminCreateStaff(@Valid @ModelAttribute("user") User user, 
			BindingResult result1,
			Model model,			
			HttpSession session) {
		
		model.addAttribute("title","Admin - CREATE STAFF");
		System.out.println("staff"+user);
//		if(staff.getRole().equals("ROLE_ADMIN")) {
//			staff = new User();
//		}

		try {

			User existingDbUser =  userRepository.getUserByUserName(user.getEmail());
			System.out.println("flag user : "+existingDbUser);

			if(existingDbUser != null) {

				if((existingDbUser.getEmail().equals(user.getEmail()))) {

					System.out.println("Staff Email id already exists");
					throw new EmailIdAlreadyRegisteredException();

				}
			}

			existingDbUser =  userRepository.getUserByMobile(user.getMobile());
			System.out.println("flag user2 : "+existingDbUser);

			if(existingDbUser != null) {
				if(existingDbUser.getMobile().equals(user.getMobile())) {

					System.out.println("Staff Mobile number already exists");
					throw new MobileNumberAlreadyRegisteredException();

				}
			}

			if(result1.hasErrors()) {
				System.out.println("ERROR : "+result1.toString());
				model.addAttribute("user", user);
				return "admin/add_staff";
			}

			user.setRole("ROLE_STAFF");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			User result = this.userRepository.save(user);

			model.addAttribute("user", result);
			model.addAttribute("user", new User());

			System.out.println("You have AGREED to user terms and aggrements!!");
			session.setAttribute("message", new Message("Successfully Registered !!! Please check your e-mail for account activation.", "alert-success"));
			return "redirect:admin/add_staff";

		}catch (EmailIdAlreadyRegisteredException e) {
			// Duplicate entry email
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("OOPS !!! ["+user.getEmail()+"] Email already registered ", "alert-danger"));
		}
		catch (MobileNumberAlreadyRegisteredException e) {
			// Duplicate entry mobile number
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("OOPS !!! ["+user.getMobile()+"] Mobile Number already registered ", "alert-danger"));
		}
		catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("OOPS !!  " + e.getMessage(), "alert-danger"));
		}
		return "redirect:admin/add_staff";
	}

	@RequestMapping("/admin_profile")
	public String adminProfile(Model model, HttpSession session, Principal principal) {
		model.addAttribute("title","Admin - MyProfile");

		return "admin/admin_my_profile";
	}
}

package com.narasimham.springbootsecuritydemo.controller;

import java.sql.SQLIntegrityConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
public class HomeController {

	static final String ACCEPT_AGGREMENT = "You have NOT aggreed to user terms and aggrements!!";
	
	private BCryptPasswordEncoder passwordEncoder;

	private UserRepository userRepository;

	UserService userService;

	@Autowired
	public HomeController(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository,
			UserService userService) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	@RequestMapping("/")
	public String home(Model model, HttpSession session) {
		model.addAttribute("title","HOME - Smart WAQF land online Registrations");
		return "home";
	}

	@RequestMapping("/updates")
	public String updates(Model model, HttpSession session) {
		model.addAttribute("title","Updates - Smart WAQF land online Registrations");
		return "updates";
	}

	@RequestMapping("/about")
	public String about(Model model, HttpSession session) {
		model.addAttribute("title","About - Smart WAQF land online Registrations");
		return "about";
	}

	@RequestMapping("/signup")
	public String signUp(Model model, HttpSession session) {
		model.addAttribute("title","SignUp - Smart WAQF land online Registrations");
		model.addAttribute("user",new User());
		return "signup";
	}

	@RequestMapping("/feedback")
	public String feedback(Model model, HttpSession session) {
		model.addAttribute("title","Feebback - Smart WAQF land online Registrations");
		return "feedback";
	}

	@RequestMapping("/login")
	public String login(Model model, HttpSession session) {
		model.addAttribute("title","Login - Smart WAQF land online Registrations");
		return "login";
	}

	@SuppressWarnings("null")
	@RequestMapping(value = "/do_register", method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("user") User user, 
			BindingResult result1,
			@RequestParam(value = "aggrement", defaultValue = "false") boolean aggrement,
			Model model,			
			HttpSession session) {

		try {

			if(!aggrement) {
				System.out.println(ACCEPT_AGGREMENT);
				throw new Exception(ACCEPT_AGGREMENT);
			}

			User existingDbUser =  userRepository.getUserByUserName(user.getEmail());
			System.out.println("flag user : "+existingDbUser);

			if(existingDbUser != null) {

				if((existingDbUser.getEmail().equals(user.getEmail()))) {

					System.out.println("Email id already exists");
					throw new EmailIdAlreadyRegisteredException();

				}
			}
			
			existingDbUser =  userRepository.getUserByMobile(user.getMobile());
			System.out.println("flag user2 : "+existingDbUser);
			
			if(existingDbUser != null) {
				if(existingDbUser.getMobile().equals(user.getMobile())) {

					System.out.println("Mobile number already exists");
					throw new MobileNumberAlreadyRegisteredException();

				}
			}

			if(result1.hasErrors()) {
				System.out.println("ERROR : "+result1.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			User result = this.userRepository.save(user);

			model.addAttribute("user", result);
			model.addAttribute("user", new User());

			System.out.println("redirect:signup");
			session.setAttribute("message", new Message("Successfully Registered !!! Please check your e-mail for account activation.", "alert-success"));
			return "redirect:signup";

		}catch (EmailIdAlreadyRegisteredException e) {
			// Duplicate entry
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("OOPS !!! ["+user.getEmail()+"] Email already registered ", "alert-danger"));
		}
		catch (MobileNumberAlreadyRegisteredException e) {
			// Duplicate entry
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("OOPS !!! ["+user.getMobile()+"] Mobile Number already registered ", "alert-danger"));
		}
		catch (SQLIntegrityConstraintViolationException e) {
			// Duplicate entry
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("OOPS Record already exists  " + e.getMessage(), "alert-danger"));

		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("OOPS !!  " + e.getMessage(), "alert-danger"));
		}
		return "signup";
	}

}

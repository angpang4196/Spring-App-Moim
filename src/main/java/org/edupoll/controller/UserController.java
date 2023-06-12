package org.edupoll.controller;

import org.edupoll.model.dto.request.UserDTO;
import org.edupoll.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	UserService userService;

	@GetMapping("/user/join")
	public String showUserJoinForm() {
		return "user/join";
	}

	@PostMapping("/user/join")
	public String userJoinHandle(UserDTO userDTO, Model model) {
		boolean rst = userService.createNewUser(userDTO);
		logger.debug("userJoinHandle's result : {} ", rst);
		if (rst) {
			return "redirect:/user/login?loginId=" + userDTO.getId();
		} else {
			model.addAttribute("error", true);
			return "/user/join";
		}
	}

	@GetMapping("/user/login")
	public String showUserLoginForm() {
		return "/user/login";
	}

}

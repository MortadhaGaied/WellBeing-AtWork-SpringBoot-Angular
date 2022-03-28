package com.wellbeignatwork.backend.Controller;

import com.wellbeignatwork.backend.ServiceImp.UserService;
import com.wellbeignatwork.backend.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;


@RestController
public class LoginController {
	
	@Autowired
	private UserService userService;
	public static User user;
	
	@PostMapping("/login")
	public User login(@RequestBody User user) {
		user = this.userService.getUser(user.getName(), user.getPassword());
		return user;
	}
	
	@PostMapping("/register")
	public User register(@RequestBody User user) {
		return this.userService.addUser(user);
	}
	
	@GetMapping("/users")
	public ArrayList<User> getUsers(){
		return this.userService.getUsers();
	}
}

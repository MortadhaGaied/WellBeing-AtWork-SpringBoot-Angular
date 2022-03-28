package com.wellbeignatwork.backend.Controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wellbeignatwork.backend.ServiceImp.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
	@Autowired
	private UserService userService;

}
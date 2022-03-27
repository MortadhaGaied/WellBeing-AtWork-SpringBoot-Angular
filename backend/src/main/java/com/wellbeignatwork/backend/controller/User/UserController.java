package com.wellbeignatwork.backend.controller.User;

import com.wellbeignatwork.backend.config.UserConfig.CurrentUser;
import com.wellbeignatwork.backend.dto.LocalUser;
import com.wellbeignatwork.backend.entity.User.User;
import com.wellbeignatwork.backend.repository.User.UserRepository;
import com.wellbeignatwork.backend.service.UserService.UserService;
import com.wellbeignatwork.backend.util.GeneralUtils;
import com.wellbeignatwork.backend.util.TestResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCurrentUser(@CurrentUser LocalUser user) {
        return ResponseEntity.ok(GeneralUtils.buildUserInfo(user));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getContent() {
        return ResponseEntity.ok("Public content goes here");
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getUserContent() {
        return ResponseEntity.ok("User content goes here");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAdminContent() {
        return ResponseEntity.ok("Admin content goes here");
    }

    @GetMapping("/mod")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<?> getModeratorContent() {
        return ResponseEntity.ok("Moderator content goes here");
    }


    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteuser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public User findUserById(@PathVariable Long id) {
        return userService.findUserById(id);
    }



    // from  Amine .. just for testing front

    @PostMapping("/login/{userName}/{password}")
    @ResponseBody
    public ResponseEntity<?> login(@PathVariable String userName,@PathVariable String password){
        User found = userRepository.findUserByDisplayNameAndPassword(userName,password);
        return  ResponseEntity.ok(new TestResponse(found.getId(), found.getDisplayName()));
    }

    @GetMapping("/get-all-users")
    @ResponseBody
    public List<User> getAllUSers(){
        return userRepository.findAll();
    }
}
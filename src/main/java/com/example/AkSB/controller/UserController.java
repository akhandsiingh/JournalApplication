package com.example.AkSB.controller;

import com.example.AkSB.JournalEntryRepository.UserRepository;
import com.example.AkSB.entity.User;
import com.example.AkSB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
   @Autowired
    private UserService userService;
   @Autowired
    private UserRepository userRepository;

   @GetMapping
   public List<User> getAllUsers(){
       return userService.getAll();
   }
   @PostMapping
    public void createUser(@RequestBody User user){
       userService.saveNewUser(user);
   }
   @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName= authentication.getName();
       User userInDb=userService.findByUserName(userName);
       userInDb.setUserName(user.getUserName());
       userInDb.setPassword(user.getPassword());
       userService.saveUser(userInDb);
       return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }

    @DeleteMapping("/user")
    public ResponseEntity<?>deleteUserById(@RequestBody User user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}

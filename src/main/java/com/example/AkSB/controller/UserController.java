package com.example.AkSB.controller;

import com.example.AkSB.JournalEntryRepository.UserRepository;
import com.example.AkSB.api.response.WeatherResponse;
import com.example.AkSB.entity.User;
import com.example.AkSB.service.UserService;
import com.example.AkSB.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "User APIs", description = "Read, Update and Delete User")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WeatherService weatherService;

    @GetMapping
    @Operation(summary = "Get all users", description = "Fetch list of all registered users")
    public List<User> getAllUsers() {
        return userService.getAll();
    }

    @PostMapping
    @Operation(summary = "Create new user", description = "Register a new user",
            requestBody = @RequestBody(required = true,
                    content = @Content(schema = @Schema(
                            example = "{ \"userName\": \"exampleUser\", \"email\": \"example@gmail.com\", \"password\": \"password123\" }"))))
    public void createUser(@RequestBody User user) {
        userService.saveNewUser(user);
    }

    @PutMapping
    @Operation(summary = "Update user", description = "Update the current logged-in user's details",
            requestBody = @RequestBody(required = true,
                    content = @Content(schema = @Schema(
                            example = "{ \"userName\": \"updatedUser\", \"password\": \"newPassword\" }"))))
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDb = userService.findByUserName(userName);
        userInDb.setUserName(user.getUserName());
        userInDb.setPassword(user.getPassword());
        userService.saveUser(userInDb);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/user")
    @Operation(summary = "Delete user", description = "Delete the current logged-in user",
            requestBody = @RequestBody(required = true,
                    content = @Content(schema = @Schema(
                            example = "{ \"userName\": \"exampleUser\" }"))))
    public ResponseEntity<?> deleteUserById(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepository.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("get-Weather")
    @Operation(summary = "Get current weather", description = "Fetch current weather info for Mumbai for the logged-in user")
    public ResponseEntity<?> getWeather() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WeatherResponse weatherResponse = weatherService.getWeather("Mumbai");
        String greeting = "";
        if (weatherResponse != null) {
            greeting = ", Weather feels like " + weatherResponse.getCurrent().getFeelslike();
        }
        return new ResponseEntity<>("Hi " + authentication.getName() + greeting, HttpStatus.OK);
    }
}

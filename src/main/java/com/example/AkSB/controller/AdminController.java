package com.example.AkSB.controller;

import com.example.AkSB.entity.User;
import com.example.AkSB.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin APIs")
public class AdminController {

    @Autowired
    public UserService userService;

    @GetMapping("all-users")
    @Operation(summary = "Get all users", description = "Fetch list of all users in the system")
    public ResponseEntity<?> getAllUsers() {
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create-admin-user")
    @Operation(summary = "Create admin user", description = "Create a new user with ADMIN role",
            requestBody = @RequestBody(required = true,
                    content = @Content(schema = @Schema(
                            example = "{ \"userName\": \"adminUser\", \"email\": \"admin@example.com\", \"password\": \"admin123\" }"))))
    public void createUser(@RequestBody User user) {
        userService.saveAdmin(user);
    }
}

package com.awda.controller;

import com.awda.model.User;
import com.awda.model.request.LoginRequest;
import com.awda.model.response.LoginResponse;
import com.awda.services.UserServiceImpl;
import com.awda.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserServiceImpl userService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            loginRequest.getEmail(), loginRequest.getPassword()
                    ));
            User user = new User(loginRequest.getEmail(), loginRequest.getPassword());
            String token = jwtUtil.createToken(user);
            LoginResponse loginResponse = new LoginResponse(loginRequest.getEmail(), token);
            return ResponseEntity.ok(loginResponse);
        } catch (BadCredentialsException ex) {
            response.put("status", "error");
            response.put("messages", "Invalid username or password");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception ex) {
            response.put("status", "error");
            response.put("messages", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/getAllUser")
    @ResponseBody
    public ResponseEntity<?> getAllUser() {
        List<User> userList = userService.getAllUser();
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("messages", "Success get all data user");
        response.put("data", userList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getUser/{id}")
    @ResponseBody
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        Map<String, Object> response = new HashMap<>();

        response.put("status", "success");
        response.put("messages", "Success get data user id : " + id);
        response.put("data", user);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<?> storeUser(@RequestBody User user) {
        Map<String, Object> response = new HashMap<>();
        try {
            User newUser = userService.storeUser(user);
            response.put("status", "success");
            response.put("messages", "Success create new user");
            response.put("data", newUser);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException ex) {
            response.put("status", "error");
            response.put("messages", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/updateUser/{id}")
    @ResponseBody
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();
        try {
            User existingUser = userService.getUser(id);
            if (existingUser == null) {
                throw new IllegalArgumentException("User id " + id + " not found");
            }
            user.setId(id);
            userService.updateUser(user, id);
            response.put("status", "success");
            response.put("messages", "Success update user id : " + id);
            response.put("data", userService.getUser(id));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException ex) {
            response.put("status", "error");
            response.put("messages", ex.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    @ResponseBody
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        Boolean isUserDeleted = userService.deleteUser(id);
        Map<String, Object> response = new HashMap<>();
        if (!isUserDeleted) {
            response.put("status", "error");
            response.put("messages", "User id " + id + " not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
        response.put("status", "success");
        response.put("messages", "Success delete user id : " + id);
        return ResponseEntity.ok(response);
    }
}

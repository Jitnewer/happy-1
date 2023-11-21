package com.example.backend.rest;

import com.example.backend.models.User;
import com.example.backend.repositories.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:8080"})
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody User user) {
        userRepository.register(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userRepository.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/users/fullname/{mail}")
    public ResponseEntity<?> getUserByMail(@PathVariable String mail) {
        try {
            User user = userRepository.getUserByMail(mail);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with email: " + mail);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUser(@PathVariable long id) {
        try {
            User user = userRepository.getUser(id);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with id: " + id);
        }
    }

    @PostMapping("/users")
    public ResponseEntity<?> addUser(@RequestBody User user) {
        try {
            User updatedUser = User.copyConstructor(user);

            userRepository.addUser(updatedUser);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(updatedUser.getId()).toUri();
            return ResponseEntity.created(location)
                    .header("Response-message", "User is saved successfully.")
                    .body(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

    @PostMapping("/login/{email}/{password}")
    public ResponseEntity<?> login(@PathVariable String email, @PathVariable String password) {
        try {
            User user = userRepository.login(email, password);
            return ResponseEntity.ok(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        try {
            User updatedUser = User.copyConstructor(user);

            userRepository.updateUser(updatedUser);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}").buildAndExpand(updatedUser.getId()).toUri();
            return ResponseEntity.created(location)
                    .header("Response-message", "User updated successfully.")
                    .body(updatedUser);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        try {
            userRepository.deleteUser(id);
            return ResponseEntity.ok("User deleted successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}

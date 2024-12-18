package dev.jingyi.TransactFlow.controller;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import dev.jingyi.TransactFlow.dto.UserDTO;
import dev.jingyi.TransactFlow.entity.User;
import dev.jingyi.TransactFlow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.aop.ClassFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // POST - Create new user
    @PostMapping("/register")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody User user) {
        System.out.println("Register endpoint hit");
        User savedUser = userService.saveUser(user);  // save user and hash password
        UserDTO userDTO = new UserDTO(savedUser.getId(), savedUser.getUsername());  // create UserDTO
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);  // return 201 Created
    }

    // POST - Login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        User user = userService.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }

    // GET - Get all users
    @GetMapping("/all")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsersAsDTO();  // Get all users --> UserDTO
        return ResponseEntity.ok(users);  // return 200 OK
    }

    // GET - Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        User user = userService.findById(id);  // read user
        if (user == null) {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }
        UserDTO userDTO = new UserDTO(user.getId(), user.getUsername());  // create UserDTO
        return ResponseEntity.ok(userDTO);  // return 200 OK
    }

    // PUT - Update User
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody User userDetails) {
        User existingUser = userService.findById(id);  //find user by ID

        if (existingUser == null) {
            return ResponseEntity.notFound().build();  // 404 Not Found
        }

        // Update user Info
        existingUser.setUsername(userDetails.getUsername());
        existingUser.setPassword(userService.encodePassword(userDetails.getPassword()));  // Update Password in UserService (Logic)
        User updatedUser = userService.saveUser(existingUser);  // save updates

        UserDTO updatedUserDTO = new UserDTO(updatedUser.getId(), updatedUser.getUsername());  // create UserDTO
        return ResponseEntity.ok(updatedUserDTO);  // return 200 OK
    }

    // DELETE - Delete User
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);  // find user by ID
        if (user == null) {
            return ResponseEntity.notFound().build();  // if not found --> 404 Not Found
        }

        userService.deleteUser(id);  // delete user
        return ResponseEntity.noContent().build();  // return 204 No Content
    }
}

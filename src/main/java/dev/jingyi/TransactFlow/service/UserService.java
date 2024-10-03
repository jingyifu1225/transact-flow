package dev.jingyi.TransactFlow.service;

import dev.jingyi.TransactFlow.dto.UserDTO;
import dev.jingyi.TransactFlow.entity.User;
import dev.jingyi.TransactFlow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /* get all users */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /* find user by username */
    public User loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        // if not found
        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found.");
        }
        return user;
    }


    /* register user and hash password */
    public User saveUser(User user) {
        if (user.getId() != null) { // if exists, update
            Optional<User> existingUser = userRepository.findById(user.getId());
            if (existingUser.isPresent() && !existingUser.get().getPassword().equals(user.getPassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword())); // hash password when update password
            }
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword())); // new user --> auto hash
        }
        return userRepository.save(user);
    }


    /* user data --> user DTO */
    public List<UserDTO> getAllUsersAsDTO() {
        return userRepository.findAll().stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    /* find user by ID */
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null); // no user --> null
    }

    /* hash password logic */
    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    /* delete user by ID */
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

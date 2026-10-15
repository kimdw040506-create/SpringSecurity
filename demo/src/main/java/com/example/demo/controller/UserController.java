package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class UserController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserController(
            UserService userService,
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userService = userService;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String getUsers(Model model) {

        model.addAttribute(
                "users",
                userService.findAll()
        );

        return "users";
    }


    @GetMapping("/new")
    public String newUser(Model model) {

        model.addAttribute(
                "user",
                new User()
        );
        model.addAttribute("roles",
                roleRepository.findAll());

        return "edit";
    }

    @PostMapping
    public String addUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "edit";
        }

        if(userRepository.findByUsername(user.getUsername()).isPresent()) {
            bindingResult.rejectValue(
                    "username",
                    "error.user",
                    "Username alraedy exists"
            );

            model.addAttribute("roles", roleRepository.findAll());
            return "edit";
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userService.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(
            @PathVariable("id") long id,
            Model model) {

        model.addAttribute(
                "user",
                userService.findById(id)
        );
        model.addAttribute("roles",
                roleRepository.findAll());

        return "edit";
    }

    @PostMapping("/update")
    public String updateUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            @RequestParam(required = false) String newPassword,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleRepository.findAll());
            return "edit";
        }

        User userWithSameUsername = userRepository
                .findByUsername(user.getUsername())
                .orElse(null);

        if (userWithSameUsername != null
                && !userWithSameUsername.getId().equals(user.getId())) {

            bindingResult.rejectValue(
                    "username",
                    "error.user",
                    "Username already exists"
            );

            model.addAttribute("roles", roleRepository.findAll());
            return "edit";
        }
        User existingUser = userService.findById(user.getId());

        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());
        existingUser.setRoles(user.getRoles());

        if (newPassword != null && !newPassword.isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(newPassword));
        }

        userService.update(existingUser);

        return "redirect:/admin";
    }   

    @PostMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable("id") long id) {

        userService.delete(id);

        return "redirect:/admin";
    }
}

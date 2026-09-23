package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
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

        return "edit";
    }

    @PostMapping
    public String addUser(
           @Valid @ModelAttribute("user") User user,
           BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit";
        }

        userService.save(user);

        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(
            @PathVariable("id") long id,
            Model model) {

        model.addAttribute(
                "user",
                userService.findById(id)
        );

        return "edit";
    }

    @PostMapping("/update")
    public String updateUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "edit";
        }

        userService.update(user);

        return "redirect:/users";
    }

    @PostMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable("id") long id) {

        userService.delete(id);

        return "redirect:/users";
    }
}
package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
public class UsersController {


    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }



    @GetMapping("/userpage")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user_page", userService.getUserByName(principal.getName()));
        return "user_page";
    }




}

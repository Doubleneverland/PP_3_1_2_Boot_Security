package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;


@Controller
public class UsersController {


    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public String create(@ModelAttribute("create_user") User user,
                         @RequestParam("roles") Collection<Long> selectById) {
        if(user != null) {
            Collection<Role> list = new ArrayList<>();
            for (Long newRole : selectById) {
                list.addAll(userService.getRoleById(newRole));
            }
            user.setRoles(list);
            userService.save(user);
        }
        return "redirect:/admin/allusers";
    }

    @GetMapping("/admin/user/new")
    public String newUser(@ModelAttribute("newUser") User user, Model model) {
        Collection<Role> rolesList = userService.getAllRoles();
        model.addAttribute("list_role", rolesList);
        return "new_user";
    }

    @GetMapping("/admin/allusers")
    public String read(Model model) {
        model.addAttribute("get_user", userService.read());
        return "user_show";
    }

    @GetMapping("/admin/user/delete/YES")
    public String deleteUser(@RequestParam("id") long id) {
        userService.delete(id);
        return "redirect:/admin/allusers";
    }

    @GetMapping("/admin/user/delete")
    public String pageDelete(@RequestParam("id") long id, Model model) {
        model.addAttribute("que", userService.upPage(id));
        return "delete_user";
    }

    @GetMapping("/admin/user/update")
    public String pageUpdate(@RequestParam("id") long id, Model model) {
        model.addAttribute("up_user", userService.upPage(id));
        model.addAttribute("update_role", userService.getAllRoles());
        return "update_user";
    }
    @PostMapping("/admin/user/edit")
    public String update(@ModelAttribute("User") User user,
                         @RequestParam("id") long id,
                         @RequestParam("name") String name,
                         @RequestParam("lastName") String lastname,
                         @RequestParam("password") String password,
                         @RequestParam("role") Collection<Long> roleById) {
        Collection<Role> list = new ArrayList<>();
        for (Long upRole : roleById) {
            list.addAll(userService.getRoleById(upRole));
        }
        user.setRoles(list);
        userService.update(id, name, lastname, password, list);
        return "redirect:/admin/allusers";
    }

    @GetMapping("/userpage")
    public String userPage(Principal principal, Model model) {
        model.addAttribute("user_page", userService.getUserByName(principal.getName()));
        return "user_page";
    }

    @GetMapping("/admin/userpage")
    public String userPageAdmin(@RequestParam("id") long id, Model model) {
        model.addAttribute("user_page", userService.upPage(id));
        return "user_page";
    }


}

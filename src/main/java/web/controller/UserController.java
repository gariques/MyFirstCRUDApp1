package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.entity.User;
import web.service.UserService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    String sayHello(Model model){
        model.addAttribute("message", "Hello, World!");
        return "hello";
    }

    @GetMapping("/users")
    String getUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("users/new")
    String newUser(Model model) {
        model.addAttribute("user", new User());
        return "new";
    }

    @PostMapping("users/new")
    public String create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        userService.add(user);
        session.setAttribute("msg", "User added successfully");
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {
        User user = userService.getOne(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         HttpSession session) {
        if (bindingResult.hasErrors()) {
            return "edit";
        }
        userService.update(user);
        session.setAttribute("msg", "User edited successfully");

        return "redirect:/users";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
        userService.deleteById(id);
        session.setAttribute("msg", "User deleted successfully");

        return "redirect:/users";
    }
}

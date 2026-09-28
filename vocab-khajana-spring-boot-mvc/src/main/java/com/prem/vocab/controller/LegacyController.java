package com.prem.vocab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LegacyController {
    @GetMapping("/login")
    public String login(Model m) {
        m.addAttribute("allUsers", java.util.List.of());
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(@RequestParam String name, @RequestParam String password, Model m) {
        if ("jbk".equals(name) && "jbk".equals(password)) {
            m.addAttribute("name", name);
            return "welcome";
        }
        m.addAttribute("errorMessage", "Invalid username/password");
        return "login";
    }

    @GetMapping("/welcome")
    public String welcome(@RequestParam(defaultValue = "Guest") String name, Model m) {
        m.addAttribute("name", name);
        return "welcome";
    }

    @GetMapping("/list-todos")
    public String todos(@RequestParam(defaultValue = "Guest") String name, Model m) {
        m.addAttribute("name", name);
        m.addAttribute("todos", java.util.List.of("Study vocabulary", "Review Java", "Practice Spring MVC"));
        return "list-todos";
    }
}
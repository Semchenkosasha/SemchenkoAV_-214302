package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Users;
import com.ordermanagement.model.enums.Role;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reg")
public class RegCont extends Main {
    @GetMapping
    public String reg(Model model) {
        getCurrentUserAndRole(model);
        return "reg";
    }

    @PostMapping
    public String reg(Model model, @RequestParam String fio, @RequestParam String username, @RequestParam String password) {
        if (usersRepo.findAll().isEmpty()) {
            usersRepo.save(new Users(fio, username, password, Role.ADMIN));
            return "redirect:/login";
        }

        if (usersRepo.findByUsername(username) != null) {
            model.addAttribute("message", "Пользователь с таким логином уже существует!");
            getCurrentUserAndRole(model);
            return "reg";
        }

        usersRepo.save(new Users(fio, username, password, Role.MANAGER));

        return "redirect:/login";
    }
}

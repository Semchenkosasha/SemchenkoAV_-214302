package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Sups;
import com.ordermanagement.model.Users;
import com.ordermanagement.model.enums.Role;
import com.ordermanagement.repo.SupsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profiles")
public class ProfilesCont extends Main {
    @Autowired
    private SupsRepo supsRepo;

    @GetMapping
    public String profiles(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("roles", Role.values());
        model.addAttribute("users", usersRepo.findAll());
        return "profiles";
    }

    @PostMapping("/edit/{id}")
    public String profileRoleEdit(@PathVariable Long id, @RequestParam Role role) {
        Users user = usersRepo.getReferenceById(id);
        if (user.getRole() != role) {
            if (user.getRole() == Role.SUP) {
                Sups sup = user.getSup();
                user.setSup(null);
                supsRepo.delete(sup);
            } else if (role == Role.SUP) {
                user.setSup(new Sups(user));
            }
            user.setRole(role);
            usersRepo.save(user);
        }
        return "redirect:/profiles";
    }
}

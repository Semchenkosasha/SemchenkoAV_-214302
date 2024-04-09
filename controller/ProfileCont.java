package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class ProfileCont extends Main {
    @GetMapping
    public String profile(Model model) {
        getCurrentUserAndRole(model);
        return "profile";
    }

    @PostMapping("/edit")
    public String profileEdit(@RequestParam String fio, @RequestParam String email, @RequestParam String tel, @RequestParam String address) {
        Users user = getUser();

        user.setFio(fio);
        user.setEmail(email);
        user.setTel(tel);
        user.setAddress(address);

        usersRepo.save(user);
        return "redirect:/profile";
    }
}

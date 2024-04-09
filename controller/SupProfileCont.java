package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Sups;
import com.ordermanagement.repo.SupsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/supProfile")
public class SupProfileCont extends Main {

    @Autowired
    private SupsRepo supsRepo;

    @GetMapping
    public String supProfile(Model model) {
        getCurrentUserAndRole(model);
        return "sup_profile";
    }

    @PostMapping("/photo")
    public String supProfilePhoto(Model model, @RequestParam MultipartFile photo) {
        Sups sup = getUser().getSup();

        try {
            if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result = "sups/" + uuidFile + "_" + photo.getOriginalFilename();
                photo.transferTo(new File(uploadImg + "/" + result));
                sup.setPhoto(result);
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            return "sup_profile";
        }

        supsRepo.save(sup);

        return "redirect:/supProfile";
    }

    @PostMapping("/edit")
    public String supProfileEdit(@RequestParam String name, @RequestParam String company, @RequestParam String contact, @RequestParam String country, @RequestParam String email) {
        Sups sup = getUser().getSup();

        sup.setName(name);
        sup.setCompany(company);
        sup.setContact(contact);
        sup.setCountry(country);
        sup.setEmail(email);

        supsRepo.save(sup);

        return "redirect:/supProfile";
    }
}

package com.ordermanagement.controller.main;

import com.ordermanagement.model.Users;
import com.ordermanagement.repo.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;

import java.time.LocalDateTime;

public class Main {
    @Autowired
    protected UsersRepo usersRepo;
    @Value("${upload.img}")
    protected String uploadImg;

    protected final int TAX = 13;

    protected Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if ((!(auth instanceof AnonymousAuthenticationToken)) && auth != null) {
            UserDetails userDetail = (UserDetails) auth.getPrincipal();
            return usersRepo.findByUsername(userDetail.getUsername());
        }
        return null;
    }

    protected String getRole() {
        Users users = getUser();
        if (users == null) return "NOT";
        return users.getRole().toString();
    }

    protected String getDate() {
        return LocalDateTime.now().toString().substring(0, 10);
    }


    protected void getCurrentUserAndRole(Model model) {
        model.addAttribute("role", getRole());
        model.addAttribute("user", getUser());
    }
}

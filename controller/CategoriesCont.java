package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Categories;
import com.ordermanagement.repo.CategoriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/categories")
public class CategoriesCont extends Main {
    @Autowired
    private CategoriesRepo categoriesRepo;

    @GetMapping
    public String categories(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("categories", getUser().getSup().getCategories());
        return "categories";
    }

    @GetMapping("/delete/{id}")
    public String categoryDelete(@PathVariable Long id) {
        categoriesRepo.deleteById(id);
        return "redirect:/categories";
    }

    @PostMapping("/add")
    public String categoryAdd(@RequestParam String name) {
        categoriesRepo.save(new Categories(name, getUser().getSup()));
        return "redirect:/categories";
    }
}

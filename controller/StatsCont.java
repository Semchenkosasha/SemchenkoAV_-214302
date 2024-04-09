package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Categories;
import com.ordermanagement.model.Products;
import com.ordermanagement.model.enums.Role;
import com.ordermanagement.repo.CategoriesRepo;
import com.ordermanagement.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/stats")
public class StatsCont extends Main {
    @Autowired
    private ProductsRepo productsRepo;
    @Autowired
    private CategoriesRepo categoriesRepo;

    @GetMapping
    public String stats(Model model) {
        getCurrentUserAndRole(model);

        List<Products> products = new ArrayList<>();

        if (getUser().getRole() == Role.SUP) {
            products.addAll(getUser().getSup().getProducts());
        } else {
            products.addAll(productsRepo.findAll());
        }

        model.addAttribute("products", products);

        products.sort(Comparator.comparing(Products::getTotalQuantity));
        Collections.reverse(products);

        String[] stringQuantity = new String[5];
        int[] intQuantity = new int[5];

        for (int i = 0; i < products.size(); i++) {
            if (i == 5) break;
            stringQuantity[i] = products.get(i).getName();
            intQuantity[i] = products.get(i).getTotalQuantity();
        }

        model.addAttribute("stringQuantity", stringQuantity);
        model.addAttribute("intQuantity", intQuantity);

        products.sort(Comparator.comparing(Products::getTotalPrice));
        Collections.reverse(products);

        String[] stringTotalPrice = new String[5];
        float[] floatTotalPrice = new float[5];

        for (int i = 0; i < products.size(); i++) {
            if (i == 5) break;
            stringTotalPrice[i] = products.get(i).getName();
            floatTotalPrice[i] = products.get(i).getTotalPrice();
        }

        model.addAttribute("stringTotalPrice", stringTotalPrice);
        model.addAttribute("floatTotalPrice", floatTotalPrice);

        products.sort(Comparator.comparing(Products::getPrice));
        Collections.reverse(products);

        String[] stringPrice = new String[5];
        float[] floatPrice = new float[5];

        for (int i = 0; i < products.size(); i++) {
            if (i == 5) break;
            stringPrice[i] = products.get(i).getName();
            floatPrice[i] = products.get(i).getPrice();
        }

        model.addAttribute("stringPrice", stringPrice);
        model.addAttribute("floatPrice", floatPrice);

        List<Categories> categories;

        if (getUser().getRole() == Role.SUP) {
            categories = getUser().getSup().getCategories();
        } else {
            categories = categoriesRepo.findAll();
        }

        String[] categoryString = new String[categories.size()];
        float[] categoryFloat = new float[categories.size()];

        if (getUser().getRole() == Role.SUP) {
            for (int i = 0; i < categories.size(); i++) {
                categoryString[i] = categories.get(i).getName();
                categoryFloat[i] = categories.get(i).getTotalPrice();
            }
        } else {
            for (int i = 0; i < categories.size(); i++) {
                categoryString[i] = categories.get(i).getName() + " | " + categories.get(i).getSup().getName();
                categoryFloat[i] = categories.get(i).getTotalPrice();
            }
        }

        model.addAttribute("categoryString", categoryString);
        model.addAttribute("categoryFloat", categoryFloat);

        return "stats";
    }
}

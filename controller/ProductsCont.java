package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Contracts;
import com.ordermanagement.model.Products;
import com.ordermanagement.model.enums.ContractStatus;
import com.ordermanagement.repo.CategoriesRepo;
import com.ordermanagement.repo.ContractsRepo;
import com.ordermanagement.repo.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/products")
public class ProductsCont extends Main {
    @Autowired
    private ProductsRepo productsRepo;
    @Autowired
    private CategoriesRepo categoriesRepo;
    @Autowired
    private ContractsRepo contractsRepo;

    @GetMapping
    public String products(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("products", productsRepo.findAll());
        model.addAttribute("categories", categoriesRepo.findAll());
        return "products";
    }

    @GetMapping("/filter")
    public String supsFilter(Model model, @RequestParam String name, @RequestParam long categoryId, @RequestParam String filter) {
        getCurrentUserAndRole(model);
        List<Products> products = productsRepo.findAllByNameContainingAndCategory_Id(name, categoryId);

        products.sort(Comparator.comparing(Products::getPrice));

        if (filter.equals("expensive")) {
            Collections.reverse(products);
        }

        model.addAttribute("products", products);
        model.addAttribute("name", name);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categoriesRepo.findAll());
        model.addAttribute("filter", filter);
        return "products";
    }

    @GetMapping("/{id}")
    public String product(Model model, @PathVariable Long id) {
        getCurrentUserAndRole(model);
        model.addAttribute("product", productsRepo.getReferenceById(id));
        return "product";
    }

    @GetMapping("/add")
    public String productAdd(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("categories", categoriesRepo.findAll());
        return "product_add";
    }

    @GetMapping("/edit/{id}")
    public String productEdit(Model model, @PathVariable Long id) {
        getCurrentUserAndRole(model);
        model.addAttribute("categories", categoriesRepo.findAll());
        model.addAttribute("product", productsRepo.getReferenceById(id));
        return "product_edit";
    }

    @GetMapping("/delete/{id}")
    public String productDelete(@PathVariable Long id) {
        productsRepo.deleteById(id);
        return "redirect:/products";
    }

    @PostMapping("/contract/{id}")
    public String productContract(@RequestParam int quantity, @RequestParam String dateEnd, @RequestParam String description, @PathVariable Long id) {
        System.out.println(getDate());
        System.out.println(dateEnd);
        Products product = productsRepo.getReferenceById(id);
        contractsRepo.save(new Contracts(getUser(), product.getSup(), product, description, getDate(), dateEnd, ContractStatus.WAITING, quantity));
        return "redirect:/products/{id}";
    }

    @PostMapping("/add")
    public String productAdd(Model model, @RequestParam String name, @RequestParam int count, @RequestParam String warehouse, @RequestParam int year, @RequestParam int term, @RequestParam String description, @RequestParam MultipartFile photo, @RequestParam float price, @RequestParam Long categoryId) {
        String result = "";
        try {
            if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) uploadDir.mkdir();
                result = "products/" + uuidFile + "_" + photo.getOriginalFilename();
                photo.transferTo(new File(uploadImg + "/" + result));
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            model.addAttribute("categories", categoriesRepo.findAll());
            return "product_add";
        }

        productsRepo.save(new Products(name, warehouse, description, result, price, getUser().getSup(), categoriesRepo.getReferenceById(categoryId), year, term, count));

        return "redirect:/products";
    }

    @PostMapping("/edit/{id}")
    public String productEdit(Model model, @RequestParam String name, @RequestParam int count, @RequestParam String warehouse, @RequestParam int year, @RequestParam int term, @RequestParam MultipartFile photo, @RequestParam float price, @RequestParam Long categoryId, @PathVariable Long id) {
        Products product = productsRepo.getReferenceById(id);

        try {
            if (photo != null && !Objects.requireNonNull(photo.getOriginalFilename()).isEmpty()) {
                String uuidFile = UUID.randomUUID().toString();
                File uploadDir = new File(uploadImg);
                if (!uploadDir.exists()) uploadDir.mkdir();
                String result = "products/" + uuidFile + "_" + photo.getOriginalFilename();
                photo.transferTo(new File(uploadImg + "/" + result));
                product.setPhoto(result);
            }
        } catch (IOException e) {
            model.addAttribute("message", "Некорректные данные!");
            getCurrentUserAndRole(model);
            model.addAttribute("categories", categoriesRepo.findAll());
            model.addAttribute("product", productsRepo.getReferenceById(id));
            return "product_edit";
        }

        product.setName(name);
        product.setWarehouse(warehouse);
        product.setPrice(price);
        product.setYear(year);
        product.setCount(count);
        product.setTerm(term);
        product.setCategory(categoriesRepo.getReferenceById(categoryId));

        productsRepo.save(product);

        return "redirect:/products";
    }
}

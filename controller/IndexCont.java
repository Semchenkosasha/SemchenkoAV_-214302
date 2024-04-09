package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexCont extends Main {
    @GetMapping
    public String index1() {
        return "redirect:/products";
    }

    @GetMapping("/")
    public String index2() {
        return "redirect:/products";
    }

    @GetMapping("/index")
    public String index3() {
        return "redirect:/products";
    }
}

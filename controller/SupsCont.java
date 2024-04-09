package com.ordermanagement.controller;

import com.ordermanagement.controller.main.Main;
import com.ordermanagement.model.Reviews;
import com.ordermanagement.model.Sups;
import com.ordermanagement.repo.ReviewsRepo;
import com.ordermanagement.repo.SupsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/sups")
public class SupsCont extends Main {

    @Autowired
    private SupsRepo supsRepo;
    @Autowired
    private ReviewsRepo reviewsRepo;

    @GetMapping
    public String sups(Model model) {
        getCurrentUserAndRole(model);
        model.addAttribute("sups", supsRepo.findAll());
        return "sups";
    }

    @GetMapping("/filter")
    public String supsFilter(Model model, @RequestParam String name, @RequestParam int score) {
        getCurrentUserAndRole(model);
        List<Sups> sups = supsRepo.findAllByNameContaining(name);
        sups = sups.stream().filter(sup -> sup.getScore() == score).toList();
        model.addAttribute("sups", sups);
        model.addAttribute("name", name);
        model.addAttribute("score", score);
        return "sups";
    }

    @GetMapping("/{id}/reviews")
    public String supReviews(Model model, @PathVariable Long id) {
        getCurrentUserAndRole(model);
        model.addAttribute("sup", supsRepo.getReferenceById(id));
        return "reviews";
    }

    @PostMapping("/{id}/reviews/add")
    public String supReviewAdd(@PathVariable Long id, @RequestParam String review, @RequestParam int score) {
        reviewsRepo.save(new Reviews(review, getDate(), score, supsRepo.getReferenceById(id), getUser()));
        return "redirect:/sups/{id}/reviews";
    }
}

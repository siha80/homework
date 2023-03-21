package io.siha.homework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchViewController {
    @GetMapping("/view/search")
    public String search(Model model) {
        return "search";
    }
}

package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import ru.yandex.practicum.catsgram.service.HomeService;

@RestController
@RequestMapping("/home")
public class HomeController {
    HomeService homeService;

    @Autowired
    public HomeController(HomeService service) {
        homeService = service;
    }

    @GetMapping
    public String homePage() {
        return homeService.homePage();
    }
}

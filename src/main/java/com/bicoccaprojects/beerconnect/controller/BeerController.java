package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @GetMapping("/lista")
    public ModelAndView  getAllBeers() {
        ModelAndView model = new ModelAndView();
        Iterable<Beer> lebirre = beerService.getAllBeers();
        model.addObject("birre", lebirre);
        model.setViewName("beers.html");
        return model;
    }


}

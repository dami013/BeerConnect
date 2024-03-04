package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class BeerController {

    @Autowired
    private BeerService beerService;

    @GetMapping(value = "/lista")
    public ModelAndView  getAllBeers() {
        ModelAndView model = new ModelAndView();
        Iterable<Beer> beers = beerService.getAllBeers();
        model.addObject("beers", beers);
        model.setViewName("beers");
        System.out.println("Percorso della view: " + model.getViewName());
        return model;
    }


}

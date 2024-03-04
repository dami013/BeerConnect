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

    @GetMapping(value = "/beerlist")
    public ModelAndView  getAllBeers() {
        ModelAndView model = new ModelAndView();
        Iterable<Beer> beers = beerService.getAllBeers();
        model.addObject("beers", beers);
        model.setViewName("beer_views/beers");
        System.out.println("Percorso della view: " + model.getViewName());
        return model;
    }

    @GetMapping("/add_beer")
    public ModelAndView addBeer() {
        ModelAndView modelAndView = new ModelAndView();
        Beer beer = new Beer();
        modelAndView.addObject("beer", beer);
        modelAndView.setViewName("beer_views/add_beer");
        return modelAndView;
    }
    @PostMapping(value = "/save_beer")
    public String saveBeer(@ModelAttribute Beer beer) {
        beerService.addBeer(beer);
        return "redirect:/beerlist";
    }
}

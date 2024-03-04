package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.entity.Pub;
import com.bicoccaprojects.beerconnect.service.BeerService;
import com.bicoccaprojects.beerconnect.service.PubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class PubController {

    @Autowired
    private PubService pubService;

    @GetMapping(value = "/publist")
    public ModelAndView getAllPubs() {
        ModelAndView model = new ModelAndView();
        Iterable<Pub> pubs = pubService.getAllPubs();
        model.addObject("allPubs", pubs);
        model.setViewName("pub_views/pubs");
        System.out.println("Percorso della view: " + model.getViewName());
        return model;
    }

    @GetMapping(value = "/publist/{id}")
    public ModelAndView getPub(@PathVariable("id") Long id) {
        ModelAndView model = new ModelAndView();
        Pub pub = pubService.getPub(id);
        model.addObject("pub", pub);
        model.setViewName("pub_views/pub");
        System.out.println("Percorso della view: " + model.getViewName());
        return model;
    }

    @GetMapping("/add_pub")
    public ModelAndView addPub() {
        ModelAndView modelAndView = new ModelAndView();
        Pub pub = new Pub();
        modelAndView.addObject("pub", pub);
        modelAndView.setViewName("pub_views/add_pub");
        return modelAndView;
    }

    @PostMapping(value = "/save_pub")
    public String saveBeer(@ModelAttribute Pub pub) {
        pubService.addPub(pub);
        return "redirect:/publist";
    }
}

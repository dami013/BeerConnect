package com.bicoccaprojects.beerconnect.controller;

import com.bicoccaprojects.beerconnect.entity.Beer;
import com.bicoccaprojects.beerconnect.exception.beer.NoBeersFoundException;
import com.bicoccaprojects.beerconnect.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BeerController {

    @Autowired
    private BeerService beerService;

    @GetMapping("/beers")
    public String getAllBeers(Model model) {
        Iterable<Beer> beers = beerService.getAllBeers();
        model.addAttribute("beers", beers);
        return "beer";
    }

}

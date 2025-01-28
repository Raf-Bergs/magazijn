package com.prularia.magazijn.inkomendeLevering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("leveringen")
public class InkomendeLeveringController {
    private final InkomendeLeveringService inkomendeLeveringService;

    public InkomendeLeveringController(InkomendeLeveringService inkomendeLeveringService) {
        this.inkomendeLeveringService = inkomendeLeveringService;
    }

    @GetMapping("{id}")
    public List<InkomendeLeveringslijnDTO> leveringen(@PathVariable long id) {
        var result = inkomendeLeveringService.verwerkInkomendeLevering(id);
        System.out.println(result);
        return result;
    }
}

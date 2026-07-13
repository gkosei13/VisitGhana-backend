package com.visitghana.api.controller;

import com.visitghana.api.model.Festival;
import com.visitghana.api.repository.FestivalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/festivals")
@CrossOrigin(origins = "*")
public class FestivalController {

    @Autowired
    private FestivalRepository festivalRepository;

    @GetMapping
    public List<Festival> getAllFestivals() {
        return festivalRepository.findAll();
    }

    @GetMapping("/{festivalId}")
    public Festival getFestival(@PathVariable Long festivalId) {
        return festivalRepository.findById(festivalId).orElse(null);
    }
}
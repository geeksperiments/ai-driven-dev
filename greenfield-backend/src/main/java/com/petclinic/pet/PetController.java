package com.petclinic.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {
    
    @Autowired
    private PetService petService;
    
    @GetMapping
    public List<Pet> getAllPets() {
        return petService.findAll();
    }
    
    @GetMapping("/name/{name}")
    public Pet getPetByName(@PathVariable String name) {
        return petService.findByName(name);
    }
    
    @PostMapping
    public Pet createPet(@RequestBody Pet pet) {
        return petService.save(pet);
    }
}
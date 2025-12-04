package com.petclinic.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {
    
    @Autowired
    private PetRepository petRepository;
    
    public List<Pet> findAll() {
        return petRepository.findAll();
    }
    
    public Pet findByName(String name) {
        return petRepository.findByName(name);
    }
    
    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }
}
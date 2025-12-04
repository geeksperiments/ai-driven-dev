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
        Pet existingPet = petRepository.findByNameAndOwnerName(pet.getName(), pet.getOwnerName());
        if (existingPet != null) {
            throw new IllegalArgumentException("Pet with name '" + pet.getName() + "' already exists for owner '" + pet.getOwnerName() + "'");
        }
        return petRepository.save(pet);
    }
}
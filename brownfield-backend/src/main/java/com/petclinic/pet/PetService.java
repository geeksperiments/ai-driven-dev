package com.petclinic.pet;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Long id) {
        return petRepository.findById(id).orElse(null);
    }

    public Pet findByName(String name) {
        return petRepository.findByName(name);
    }

    @Transactional
    public Pet save(Pet pet) {
        validatePetUniquenessPerOwner(pet);
        return petRepository.save(pet);
    }

    @Transactional
    public void delete(Long id) {
        petRepository.deleteById(id);
    }

    private void validatePetUniquenessPerOwner(Pet pet) {
        if (pet.getOwner() != null) {
            Pet existingPet = null;
            Long ownerId = pet.getOwner().getId();
            if (ownerId != null) {
                existingPet = petRepository.findByNameAndOwnerId(pet.getName(), ownerId);
            } else {
                existingPet = petRepository.findByNameAndOwner(pet.getName(), pet.getOwner());
            }
            // Defensive check: ensure the existing pet actually belongs to the same owner
            if (existingPet != null) {
                boolean sameOwner = false;
                if (ownerId != null && existingPet.getOwner() != null && existingPet.getOwner().getId() != null) {
                    sameOwner = existingPet.getOwner().getId().equals(ownerId);
                } else if (existingPet.getOwner() != null && pet.getOwner().getName() != null) {
                    sameOwner = pet.getOwner().getName().equals(existingPet.getOwner().getName());
                }

                if (sameOwner && (pet.getId() == null || !existingPet.getId().equals(pet.getId()))) {
                    throw new IllegalArgumentException("Owner " + pet.getOwner().getName() + " already has a pet named " + pet.getName());
                }
            }
        }
    }
}

package com.petclinic.pet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class PetServiceTest {

    @Autowired
    private PetService petService;

    @Test
    public void testSavePet() {
        Pet pet = new Pet("Fluffy", "Alice");
        Pet savedPet = petService.save(pet);
        assertNotNull(savedPet.getId());
        assertEquals("Fluffy", savedPet.getName());
        assertEquals("Alice", savedPet.getOwnerName());
    }

    @Test
    public void testPetUniquenessPerOwner() {
        Pet pet1 = new Pet("Buddy", "John");
        petService.save(pet1);
        
        Pet pet2 = new Pet("Buddy", "John");
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            petService.save(pet2);
        });
        
        assertTrue(exception.getMessage().contains("Pet with name 'Buddy' already exists for owner 'John'"));
    }

    @Test
    public void testSamePetNameDifferentOwners() {
        Pet pet1 = new Pet("Max", "John");
        Pet pet2 = new Pet("Max", "Jane");
        
        assertDoesNotThrow(() -> {
            petService.save(pet1);
            petService.save(pet2);
        });
    }
}
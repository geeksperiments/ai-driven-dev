package com.petclinic.pet;

import com.petclinic.owner.Owner;
import com.petclinic.owner.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class PetServiceTest {

    @Autowired
    private PetService petService;
    
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void shouldSaveNewPet() {
        // given
        Owner owner = new Owner("Alice", "123 Test St");
        ownerRepository.save(owner);
        Pet pet = new Pet("Buddy", owner);

        // when
        Pet savedPet = petService.save(pet);

        // then
        assertThat(savedPet.getId()).isNotNull();
        assertThat(savedPet.getName()).isEqualTo("Buddy");
        assertThat(savedPet.getOwner().getName()).isEqualTo("Alice");
    }

    @Test
    void shouldThrowExceptionWhenOwnerHasDuplicatePetName() {
        // given
        Owner owner = new Owner("Bob", "456 Test Ave");
        ownerRepository.save(owner);
        Pet firstPet = new Pet("Max", owner);
        petService.save(firstPet);
        Pet duplicatePet = new Pet("Max", owner);

        // when & then
        assertThatThrownBy(() -> petService.save(duplicatePet))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Owner Bob already has a pet named Max");
    }

    @Test
    void shouldAllowDifferentOwnersToHavePetsWithSameName() {
        // given
        Owner owner1 = new Owner("Carol", "789 Test Rd");
        Owner owner2 = new Owner("Dave", "321 Test Blvd");
        ownerRepository.save(owner1);
        ownerRepository.save(owner2);
        Pet pet1 = new Pet("Charlie", owner1);
        Pet pet2 = new Pet("Charlie", owner2);

        // when
        Pet savedPet1 = petService.save(pet1);
        Pet savedPet2 = petService.save(pet2);

        // then
        assertThat(savedPet1.getId()).isNotNull();
        assertThat(savedPet2.getId()).isNotNull();
        assertThat(savedPet1.getId()).isNotEqualTo(savedPet2.getId());
    }

    @Test
    void shouldAllowSameOwnerToHavePetsWithDifferentNames() {
        // given
        Owner owner = new Owner("Eve", "654 Test Way");
        ownerRepository.save(owner);
        Pet pet1 = new Pet("Rex", owner);
        Pet pet2 = new Pet("Luna", owner);

        // when
        Pet savedPet1 = petService.save(pet1);
        Pet savedPet2 = petService.save(pet2);

        // then
        assertThat(savedPet1.getId()).isNotNull();
        assertThat(savedPet2.getId()).isNotNull();
        assertThat(savedPet1.getName()).isEqualTo("Rex");
        assertThat(savedPet2.getName()).isEqualTo("Luna");
    }

    @Test
    void shouldFindAllPets() {
        // given
        Owner owner1 = new Owner("Frank", "987 Test Ln");
        Owner owner2 = new Owner("Grace", "147 Test Ct");
        ownerRepository.save(owner1);
        ownerRepository.save(owner2);
        Pet pet1 = new Pet("Milo", owner1);
        Pet pet2 = new Pet("Bella", owner2);
        petService.save(pet1);
        petService.save(pet2);

        // when
        var pets = petService.findAll();

        // then
        assertThat(pets).hasSizeGreaterThanOrEqualTo(2);
    }

    @Test
    void shouldFindPetById() {
        // given
        Owner owner = new Owner("Helen", "258 Test Dr");
        ownerRepository.save(owner);
        Pet pet = new Pet("Oscar", owner);
        Pet savedPet = petService.save(pet);

        // when
        Pet foundPet = petService.findById(savedPet.getId());

        // then
        assertThat(foundPet).isNotNull();
        assertThat(foundPet.getName()).isEqualTo("Oscar");
        assertThat(foundPet.getOwner().getName()).isEqualTo("Helen");
    }

    @Test
    void shouldFindPetByName() {
        // given
        Owner owner = new Owner("Ivan", "369 Test Pl");
        ownerRepository.save(owner);
        Pet pet = new Pet("Daisy", owner);
        petService.save(pet);

        // when
        Pet foundPet = petService.findByName("Daisy");

        // then
        assertThat(foundPet).isNotNull();
        assertThat(foundPet.getOwner().getName()).isEqualTo("Ivan");
    }

    @Test
    void shouldDeletePet() {
        // given
        Owner owner = new Owner("Jack", "741 Test Ave");
        ownerRepository.save(owner);
        Pet pet = new Pet("Rocky", owner);
        Pet savedPet = petService.save(pet);

        // when
        petService.delete(savedPet.getId());

        // then
        Pet deletedPet = petService.findById(savedPet.getId());
        assertThat(deletedPet).isNull();
    }
}

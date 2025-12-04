package com.petclinic.visit;

import com.petclinic.owner.Owner;
import com.petclinic.owner.OwnerRepository;
import com.petclinic.pet.Pet;
import com.petclinic.pet.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Autowired
    private PetService petService;
    
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void shouldFindAllVisits() {
        // when
        List<Visit> visits = visitService.findAll();

        // then
        assertThat(visits).isNotEmpty();
        assertThat(visits.size()).isGreaterThanOrEqualTo(8);
    }

    @Test
    void shouldFindVisitById() {
        // given
        List<Visit> allVisits = visitService.findAll();
        Visit existingVisit = allVisits.get(0);

        // when
        Visit foundVisit = visitService.findById(existingVisit.getId());

        // then
        assertThat(foundVisit).isNotNull();
        assertThat(foundVisit.getId()).isEqualTo(existingVisit.getId());
        assertThat(foundVisit.getClinic()).isEqualTo(existingVisit.getClinic());
    }

    @Test
    void shouldReturnNullWhenVisitNotFound() {
        // when
        Visit foundVisit = visitService.findById(99999L);

        // then
        assertThat(foundVisit).isNull();
    }

    @Test
    void shouldFindVisitsByPetId() {
        // given
        Pet pet = petService.findById(1L);
        assertThat(pet).isNotNull();

        // when
        List<Visit> visits = visitService.findByPetId(1L);

        // then
        assertThat(visits).isNotEmpty();
        assertThat(visits).hasSize(2);
        assertThat(visits).allMatch(visit -> visit.getPet().getId().equals(1L));
    }

    @Test
    void shouldReturnEmptyListWhenNoVisitsForPet() {
        // given
        Owner owner = new Owner("TestOwner", "123 Test St");
        ownerRepository.save(owner);
        Pet newPet = new Pet("TestPet", owner);
        Pet savedPet = petService.save(newPet);

        // when
        List<Visit> visits = visitService.findByPetId(savedPet.getId());

        // then
        assertThat(visits).isEmpty();
    }

    @Test
    void shouldVerifyVisitHasCorrectFields() {
        // given
        List<Visit> visits = visitService.findByPetId(1L);
        assertThat(visits).isNotEmpty();

        // when
        Visit visit = visits.get(0);

        // then
        assertThat(visit.getId()).isNotNull();
        assertThat(visit.getDateTime()).isNotNull();
        assertThat(visit.getClinic()).isNotEmpty();
        assertThat(visit.getSummary()).isNotEmpty();
        assertThat(visit.getPet().getId()).isEqualTo(1L);
        assertThat(visit.getVet()).isNotNull();
        assertThat(visit.getVet().getName()).isNotEmpty();
        assertThat(visit.getVet().getSpecialty()).isNotEmpty();
    }

    @Test
    void shouldFindMultipleVisitsForDifferentPets() {
        // when
        List<Visit> visitsForPet1 = visitService.findByPetId(1L);
        List<Visit> visitsForPet2 = visitService.findByPetId(2L);
        // test-data was adjusted so the third pet with multiple visits is id 3
        List<Visit> visitsForPet3 = visitService.findByPetId(3L);

        // then
        assertThat(visitsForPet1).hasSize(2);
        assertThat(visitsForPet2).hasSize(2);
        assertThat(visitsForPet3).hasSize(4);
    }
}

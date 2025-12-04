package com.petclinic.pet;

import com.petclinic.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long> {
    
    Pet findByName(String name);
    
    Pet findByNameAndOwner(String name, Owner owner);
    
    List<Pet> findByOwner(Owner owner);
}

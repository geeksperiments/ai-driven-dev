package com.petclinic.visit;

import com.petclinic.owner.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {
    
    List<Visit> findByPetId(Long petId);
    
    List<Visit> findByOwner(Owner owner);
}

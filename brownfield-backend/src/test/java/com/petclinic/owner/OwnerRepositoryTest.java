package com.petclinic.owner;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OwnerRepositoryTest {

    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    void shouldSaveOwner() {
        // given
        Owner owner = new Owner("John Doe", "123 Main St");

        // when
        Owner savedOwner = ownerRepository.save(owner);

        // then
        assertThat(savedOwner).isNotNull();
        assertThat(savedOwner.getId()).isNotNull();
        assertThat(savedOwner.getName()).isEqualTo("John Doe");
        assertThat(savedOwner.getAddress()).isEqualTo("123 Main St");
    }

    @Test
    void shouldFindById() {
        // given
        Owner owner = new Owner("Jane Smith", "456 Oak Ave");
        Owner savedOwner = ownerRepository.save(owner);

        // when
        Optional<Owner> foundOwner = ownerRepository.findById(savedOwner.getId());

        // then
        assertThat(foundOwner).isPresent();
        assertThat(foundOwner.get().getName()).isEqualTo("Jane Smith");
        assertThat(foundOwner.get().getAddress()).isEqualTo("456 Oak Ave");
    }

    @Test
    void shouldFindByName() {
        // given
        Owner owner = new Owner("Bob Johnson", "789 Pine Rd");
        ownerRepository.save(owner);

        // when
        Owner foundOwner = ownerRepository.findByName("Bob Johnson");

        // then
        assertThat(foundOwner).isNotNull();
        assertThat(foundOwner.getName()).isEqualTo("Bob Johnson");
        assertThat(foundOwner.getAddress()).isEqualTo("789 Pine Rd");
    }

    @Test
    void shouldReturnNullWhenOwnerNameNotFound() {
        // when
        Owner foundOwner = ownerRepository.findByName("NonExistent Owner");

        // then
        assertThat(foundOwner).isNull();
    }

    @Test
    void shouldReturnEmptyWhenOwnerIdNotFound() {
        // when
        Optional<Owner> foundOwner = ownerRepository.findById(99999L);

        // then
        assertThat(foundOwner).isEmpty();
    }
}
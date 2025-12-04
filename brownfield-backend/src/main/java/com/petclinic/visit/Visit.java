package com.petclinic.visit;

import com.petclinic.owner.Owner;
import com.petclinic.pet.Pet;
import com.petclinic.vet.Vet;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String clinic;

    @Column(nullable = false, length = 1000)
    private String summary;

    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "vet_id")
    private Vet vet;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Visit() {
    }

    public Visit(Long id, LocalDateTime dateTime, String clinic, String summary, Pet pet, Vet vet, Owner owner) {
        this.id = id;
        this.dateTime = dateTime;
        this.clinic = clinic;
        this.summary = summary;
        this.pet = pet;
        this.vet = vet;
        this.owner = owner;
    }

    public Visit(LocalDateTime dateTime, String clinic, String summary, Pet pet, Vet vet, Owner owner) {
        this.dateTime = dateTime;
        this.clinic = clinic;
        this.summary = summary;
        this.pet = pet;
        this.vet = vet;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getClinic() {
        return clinic;
    }

    public String getSummary() {
        return summary;
    }

    public Pet getPet() {
        return pet;
    }

    public Vet getVet() {
        return vet;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}

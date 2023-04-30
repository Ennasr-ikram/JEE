package com.example.patientsecurity.repositories;

import com.example.patientsecurity.entities.Patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Page<Patient> findByNomContains(String kw, Pageable pageable);
}


//on utilison interface repository pour simplifie
// la gestion des données dans l'application,
// tout en améliorant la lisibilité et la maintenabilité du code.
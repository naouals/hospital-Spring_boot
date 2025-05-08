package org.example.hospital.repositories;

import org.example.hospital.entities.Medcein;
import org.example.hospital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedceinRepository extends JpaRepository<Medcein, Long> {
    Medcein findByNom(String nom);

}

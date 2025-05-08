package org.example.hospital.repositories;

import org.example.hospital.entities.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
     Patient findByNom(String name);

}

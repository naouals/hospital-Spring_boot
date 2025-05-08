package org.example.hospital.repositories;

import org.example.hospital.entities.Consultation;
import org.example.hospital.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

}

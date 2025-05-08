package org.example.hospital.repositories;

import org.example.hospital.entities.Medcein;
import org.example.hospital.entities.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RendezVousRepository extends JpaRepository<RendezVous, Long> {

}

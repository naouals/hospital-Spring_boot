package org.example.hospital.service;

import org.example.hospital.entities.Consultation;
import org.example.hospital.entities.Medcein;
import org.example.hospital.entities.Patient;
import org.example.hospital.entities.RendezVous;

public interface IHospitalService {
    Patient savePatient(Patient patient);
    Medcein saveMedcein(Medcein medcein);
    RendezVous saveRendezVous(RendezVous rendezVous);
    Consultation saveConsultation(Consultation consultation);
}

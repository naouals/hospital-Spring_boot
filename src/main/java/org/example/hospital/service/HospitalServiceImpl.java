package org.example.hospital.service;

import jakarta.transaction.Transactional;
import org.example.hospital.entities.Consultation;
import org.example.hospital.entities.Medcein;
import org.example.hospital.entities.Patient;
import org.example.hospital.entities.RendezVous;
import org.example.hospital.repositories.ConsultationRepository;
import org.example.hospital.repositories.MedceinRepository;
import org.example.hospital.repositories.PatientRepository;
import org.example.hospital.repositories.RendezVousRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class HospitalServiceImpl implements IHospitalService {
    private PatientRepository patientRepository;
    private MedceinRepository medceinRepository;
    private ConsultationRepository consultationRepository;
    private RendezVousRepository rendezVousRepository;

    public HospitalServiceImpl(PatientRepository patientRepository, MedceinRepository medceinRepository, ConsultationRepository consultationRepository, RendezVousRepository rendezVousRepository) {
        this.patientRepository = patientRepository;
        this.medceinRepository = medceinRepository;
        this.consultationRepository = consultationRepository;
        this.rendezVousRepository = rendezVousRepository;
    }

    @Override
    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Medcein saveMedcein(Medcein medcein) {
        return medceinRepository.save(medcein);
    }

    @Override
    public RendezVous saveRendezVous(RendezVous rendezVous) {
        return rendezVousRepository.save(rendezVous);
    }

    @Override
    public Consultation saveConsultation(Consultation consultation) {
        return consultationRepository.save(consultation);
    }
}

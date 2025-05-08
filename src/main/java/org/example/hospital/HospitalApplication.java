package org.example.hospital;

import org.example.hospital.entities.*;
import org.example.hospital.repositories.ConsultationRepository;
import org.example.hospital.repositories.MedceinRepository;
import org.example.hospital.repositories.PatientRepository;
import org.example.hospital.repositories.RendezVousRepository;
import org.example.hospital.service.IHospitalService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class HospitalApplication{

	public static void main(String[] args) {
		SpringApplication.run(HospitalApplication.class, args);
	}

	@Bean
	CommandLineRunner start(IHospitalService iHospitalService, RendezVousRepository rendezVousRepository, PatientRepository patientRepository, MedceinRepository medceinRepository, ConsultationRepository consultationRepository) {
		return args -> {
			//patientRepository.save(new Patient(null, "ahmed",new Date(), false, null));
			Stream.of("Mohammed", "Hassan", "Najat").forEach(name -> {
				Patient patient = new Patient();
				patient.setNom(name);
				patient.setDateNaissance(new Date());
				patient.setMalade(false);

				iHospitalService.savePatient(patient);
			});
			Stream.of("Aymane", "Adil", "Yassmine").forEach(name -> {
				Medcein medcein = new Medcein();
				medcein.setNom(name);
				medcein.setSpecialite(Math.random()>0.5?"cardio":"Dentaire");
				medcein.setEmail(name + "@gmail.com");

				iHospitalService.saveMedcein(medcein);
			});
			Patient patient = patientRepository.findById(1L).orElse(null);
			Patient patient2 = patientRepository.findByNom("Mohammed");

			Medcein medcein= medceinRepository.findByNom("Adil");

			RendezVous rendezVous = new RendezVous();
			rendezVous.setDate(new Date());
			rendezVous.setStatus(StatusRDV.PENDING);
			rendezVous.setMedcein(medcein);
			rendezVous.setPatient(patient);
			rendezVousRepository.save(rendezVous);

			RendezVous rendezVous2 = rendezVousRepository.findById(1L).orElse(null);
			Consultation consultation = new Consultation();
			consultation.setDateConsultation(new Date());
			consultation.setRendezVous(rendezVous2);
			consultation.setRapport("Rapport de la consultation ...");
			iHospitalService.saveConsultation(consultation);



		};
	}
}

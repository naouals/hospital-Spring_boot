# **Projet Hospital Management System - Documentation**

## **📌 Table des matières**
1. [Introduction](#-introduction)
2. [Technologies utilisées](#-technologies-utilisées)
3. [Structure du projet](#-structure-du-projet)
4. [Entités et relations JPA](#-entités-et-relations-jpa)
5. [API Endpoints](#-api-endpoints)
6. [Tests et démonstration](#-tests-et-démonstration)
7. [Configuration](#-configuration)
8. [Captures d'écran](#-captures-décran)

---

## **Introduction**
Ce projet est une **application Spring Boot** de gestion hospitalière permettant de :
- Gérer les **patients**, **médecins**, **rendez-vous** et **consultations**.
- Utiliser une base de données **H2 en mémoire** pour le développement.
- Offrir une **API REST** pour interagir avec les données.

---

## **Technologies utilisées**
- **Backend** :  
    *Spring Boot*  
    *Spring Data JPA*  
    *H2 Database*  
    *Lombok*  

- **Autres** :  
    *Maven*  
    *Java*  

---

## **Structure du projet**
```
hospital/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org.example.hospital
│   │   │        ├── entities/           # Entités JPA
│   │   │        │   ├── Patient.java
│   │   │        │   ├── Medcein.java
│   │   │        │   ├── RendezVous.java
│   │   │        │   └── Consultation.java
│   │   │        ├── repositories/       # Couche d'accès aux données
│   │   │        │   ├── PatientRepository.java
│   │   │        │   ├── MedceinRepository.java
│   │   │        │   └── ...
│   │   │        ├── service/            # Logique métier
│   │   │        │   ├── IHospitalService.java
│   │   │        │   └── HospitalServiceImpl.java
│   │   │        └── web/                # Couche API REST
│   │   │            └── PatientRestController.java
│   │   └── resources/
│   │       └── application.properties # Configuration
│   └── test/                          # Tests (optionnel)
├── pom.xml                            # Dépendances Maven
└── README.md
```

---

## **Entités et relations JPA**
### **1. Patient (`Patient.java`)**
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Patient {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Date dateNaissance;
    private boolean malade;
    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}
```
- **Relations** : Un patient peut avoir **plusieurs rendez-vous**.

### **2.Médecin (`Medcein.java`)**
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Medcein {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String email;
    private String specialite;
    @OneToMany(mappedBy = "medcein", fetch = FetchType.LAZY)
    private Collection<RendezVous> rendezVous;
}
```
- **Relations** : Un médecin peut avoir **plusieurs rendez-vous**.

### **3.Rendez-vous (`RendezVous.java`)**
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class RendezVous {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date date;
    @Enumerated(EnumType.STRING)
    private StatusRDV status;
    @ManyToOne
    private Patient patient;
    @ManyToOne
    private Medcein medcein;
    @OneToOne(mappedBy = "rendezVous")
    private Consultation consultation;
}
```
- **Relations** :  
  - **ManyToOne** → Patient et Médecin.  
  - **OneToOne** → Consultation.  

### **4.Consultation (`Consultation.java`)**
```java
@Entity
@Data @NoArgsConstructor @AllArgsConstructor
public class Consultation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateConsultation;
    private String rapport;
    @OneToOne
    private RendezVous rendezVous;
}
```

### **5.Enumération (`StatusRDV.java`)**
```java
public enum StatusRDV {
    PENDING, CANCELLED, DONE
}
```
*pour remplacer des valeurs numériques (comme des codes) par des chaînes de caractères plus lisibles*
---

## ** API **
| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/patients` | Liste tous les patients |

**Exemple de requête :**
```bash
http://localhost:8086/patients
```
**Réponse :**
```json
[
  {
    "id": 1,
    "nom": "Mohammed",
    "dateNaissance": "2023-10-25",
    "malade": false
  }
]
```

---

## **Tests et démonstration**
### **Données initiales chargées au démarrage (`HospitalApplication.java`)**
```java
@Bean
CommandLineRunner start(IHospitalService service) {
    return args -> {
        Stream.of("Mohammed", "Hassan", "Najat").forEach(name -> {
            Patient patient = new Patient();
            patient.setNom(name);
            patient.setDateNaissance(new Date());
            patient.setMalade(false);
            service.savePatient(patient);
        });

        Stream.of("Aymane", "Adil", "Yassmine").forEach(name -> {
            Medcein medcein = new Medcein();
            medcein.setNom(name);
            medcein.setSpecialite(Math.random() > 0.5 ? "Cardio" : "Dentaire");
            medcein.setEmail(name + "@gmail.com");
            service.saveMedcein(medcein);
        });

        // Création d'un rendez-vous et d'une consultation
        RendezVous rdv = new RendezVous();
        rdv.setDate(new Date());
        rdv.setStatus(StatusRDV.PENDING);
        rdv.setPatient(patientRepository.findById(1L).orElse(null));
        rdv.setMedcein(medceinRepository.findByNom("Adil"));
        service.saveRendezVous(rdv);

        Consultation consultation = new Consultation();
        consultation.setDateConsultation(new Date());
        consultation.setRendezVous(rdv);
        consultation.setRapport("Rapport de consultation...");
        service.saveConsultation(consultation);
    };
}
```

---

## **Configuration**
### **Fichier `application.properties`**
```properties
spring.application.name=hospital
spring.datasource.url=jdbc:h2:mem:hospital
spring.h2.console.enabled=true
server.port=8086
```
- **Accès à la console H2** : `http://localhost:8086/h2-console`  
  (JDBC URL: `jdbc:h2:mem:hospital`, User: `sa`, Password: vide)

---

## **Captures d'écran**
### **1. Console H2**
![image](https://github.com/user-attachments/assets/adffd675-8754-4c96-b8b2-a6acceaea9c3) ![image](https://github.com/user-attachments/assets/c362f9ae-a3a6-4334-a5be-eeba67bc2f18)
  
*Base de données en mémoire avec les tables générées.*

### **2. Liste des patients via API**
![image](https://github.com/user-attachments/assets/5a862b5d-8210-4f15-8859-af4436675c7a)


*Réponse JSON des patients via `GET /patients`.*

### **3. Les tables**
![image](https://github.com/user-attachments/assets/60485ce2-901d-4fa9-9f4f-a1dae4dfad46)

*La table consultation*

![image](https://github.com/user-attachments/assets/f75e5228-f918-44c3-933f-3cd7a0939a50)

*La table medcein*

![image](https://github.com/user-attachments/assets/db507c13-ce42-4096-8728-953b73b4b872)

*La table rendez_vous*

![image](https://github.com/user-attachments/assets/042d2f84-e767-437d-b569-2f40f45d9511)

*La table patient*


---


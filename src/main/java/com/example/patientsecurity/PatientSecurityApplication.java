package com.example.patientsecurity;

import com.example.patientsecurity.entities.Patient;
import com.example.patientsecurity.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@SpringBootApplication
public class PatientSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(PatientSecurityApplication.class, args);
    }
    //   @Bean //Pour que ça s'execute au démarrage
    CommandLineRunner commandLineRunner(PatientRepository patientRepository) {
        return args -> {
            patientRepository.save(new Patient(null, "Karim", new Date(), false, 198));
            patientRepository.save(new Patient(null, "Ahmed", new Date(), true, 571));
            patientRepository.save(new Patient(null, "Zakaria", new Date(), false, 965));
            patientRepository.save(new Patient(null, "Soukaina", new Date(), true, 120));
            patientRepository.findAll().forEach(
                    p -> {
                        System.out.println(p.getNom());
                    }
            );

        };
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

package com.medicalclinic.controller;

import java.util.List;

import com.medicalclinic.model.Password;
import com.medicalclinic.model.Patient;
import com.medicalclinic.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public Patient getPatientByEmail(@PathVariable("email") String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPatient(@RequestBody Patient patient) {
        patientService.addPatient(patient);
    }

    @DeleteMapping("/{email}")
    public boolean deletePatientByEmail(@PathVariable("email") String email) {
        return patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public boolean editPatient(@PathVariable("email") String email, @RequestBody Patient newPatient) {
        return patientService.updatePatientByEmail(newPatient, email);
    }

    @PatchMapping("/{email}")
    public boolean changePassword(@PathVariable("email") String email, @RequestBody Password password){
        return patientService.changePasswordByEmail(email, password.password());
    }
}
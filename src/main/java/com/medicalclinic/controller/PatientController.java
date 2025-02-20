package com.medicalclinic.controller;

import com.medicalclinic.model.Patient;
import com.medicalclinic.service.PatientService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public List<Patient> getPatients() {
        return patientService.getPatients();
    }

    @GetMapping("/{email}")
    public Patient getPatientByEmail(@PathVariable("email") String email) {
        return patientService.getPatientByEmail(email);
    }

    @PostMapping
    public void addNewPatient(@RequestBody Patient patient) {
        patientService.addNewPatient(patient);
    }

    @DeleteMapping("/{email}")
    public boolean deletePatientByEmail(@PathVariable("email") String email) {
        return patientService.deletePatientByEmail(email);
    }

    @PutMapping("/{email}")
    public boolean editPatient(@PathVariable("email") String email, @RequestBody Patient newPatient) {
        return patientService.updatePatientByEmail(newPatient, email);
    }
}
package com.medicalclinic.controller;

import com.medicalclinic.model.Password;
import com.medicalclinic.model.dto.PatientDTO;
import com.medicalclinic.model.entity.Patient;
import com.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
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

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public List<PatientDTO> getPatients() {
        return patientService.getAll();
    }

    @GetMapping("/{email}")
    public PatientDTO getPatientByEmail(@PathVariable("email") String email) {
        return patientService.getByEmail(email);
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPatient(@RequestBody Patient data) {
        patientService.add(data);
    }

    @DeleteMapping("/{email}")
    public boolean deletePatientByEmail(@PathVariable("email") String email) {
        return patientService.deleteByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDTO editPatient(@PathVariable("email") String email, @RequestBody Patient data) {
        return patientService.updateByEmail(data, email);
    }

    @PatchMapping("/{email}")
    public boolean changePassword(@PathVariable("email") String email, @RequestBody Password changePassword) {
        return patientService.changePasswordByEmail(email, changePassword.password());
    }
}
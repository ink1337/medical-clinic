package com.medicalclinic.controller;

import com.medicalclinic.model.Password;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.patient.PatientDTO;
import com.medicalclinic.model.dto.patient.PatientCommandDTO;
import com.medicalclinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/patients")
public class PatientController {
    private final PatientService patientService;

    @GetMapping
    public PageableDataDTO<PatientDTO> getPatients(Pageable pageable) {
        return patientService.getAll(pageable);
    }

    @GetMapping("/{email}")
    public PatientDTO getPatientByEmail(@PathVariable("email") String email) {
        return patientService.getByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPatient(@RequestBody PatientCommandDTO data) {
        patientService.add(data);
    }

    @DeleteMapping("/{email}")
    public void deletePatientByEmail(@PathVariable("email") String email) {
        patientService.deleteByEmail(email);
    }

    @PutMapping("/{email}")
    public PatientDTO editPatient(@PathVariable("email") String email, @RequestBody PatientCommandDTO commandDTO) {
        return patientService.updateByEmail(commandDTO, email);
    }

    @PatchMapping("/{email}")
    public boolean changePassword(@PathVariable("email") String email, @RequestBody Password changePassword) {
        return patientService.changePasswordByEmail(email, changePassword.password());
    }
}
package com.medicalclinic.controller;

import com.medicalclinic.model.Password;
import com.medicalclinic.model.dto.DoctorDTO;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.service.DoctorService;
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

import java.util.List;


@AllArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDTO> getAll() {
        return doctorService.getAll();
    }

    @GetMapping("/{email}")
    public DoctorDTO getByName(@PathVariable("email") String email) {
        return doctorService.getByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Doctor data) {
        doctorService.add(data);
    }

    @DeleteMapping("/{email}")
    public boolean deleteByEmail(@PathVariable("email") String email) {
        return doctorService.deleteByEmail(email);
    }

    @PutMapping("/{email}")
    public DoctorDTO update(@PathVariable("email") String email, @RequestBody Doctor data) {
        return doctorService.updateByEmail(data, email);
    }

    @PatchMapping("/{email}/facilities/{facilityName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFacility(@PathVariable("email") String email, @PathVariable("facilityName") String facilityName) {
        doctorService.addFacility(email, facilityName);
    }

    @PatchMapping("/{email}")
    public boolean changePassword(@PathVariable("email") String email, @RequestBody Password changePassword) {
        return doctorService.changePasswordByEmail(email, changePassword.password());
    }
}
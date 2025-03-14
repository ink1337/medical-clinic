package com.medicalclinic.controller;

import com.medicalclinic.model.Password;
import com.medicalclinic.model.dto.PageDataDTO;
import com.medicalclinic.model.dto.doctor.DoctorInDTO;
import com.medicalclinic.model.dto.doctor.DoctorOutDTO;
import com.medicalclinic.service.DoctorService;
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
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public PageDataDTO<DoctorOutDTO> getAll(Pageable pageable) {
        return doctorService.getAll(pageable);
    }

    @GetMapping("/{email}")
    public DoctorOutDTO getByName(@PathVariable("email") String email) {
        return doctorService.getByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody DoctorInDTO data) {
        doctorService.add(data);
    }

    @DeleteMapping("/{email}")
    public boolean deleteByEmail(@PathVariable("email") String email) {
        return doctorService.deleteByEmail(email);
    }

    @PutMapping("/{email}")
    public DoctorOutDTO update(@PathVariable("email") String email, @RequestBody DoctorInDTO data) {
        return doctorService.updateByEmail(data, email);
    }

    @PatchMapping("/{email}/facilities/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFacility(@PathVariable("email") String email, @PathVariable("id") Long id) {
        doctorService.addFacility(email, id);
    }

    @PatchMapping("/{email}")
    public boolean changePassword(@PathVariable("email") String email, @RequestBody Password changePassword) {
        return doctorService.changePasswordByEmail(email, changePassword.password());
    }
}
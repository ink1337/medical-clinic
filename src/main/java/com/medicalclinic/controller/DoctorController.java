package com.medicalclinic.controller;

import com.medicalclinic.model.Password;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.doctor.DoctorCreateCommand;
import com.medicalclinic.model.dto.doctor.DoctorDTO;
import com.medicalclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/doctors")
public class DoctorController {
    private final DoctorService doctorService;

    @GetMapping
    public PageableDataDTO<DoctorDTO> getAll(Pageable pageable) {
        return doctorService.getAll(pageable);
    }

    @GetMapping("/{email}")
    public DoctorDTO getByName(@PathVariable("email") String email) {
        return doctorService.getByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody DoctorCreateCommand data) {
        doctorService.add(data);
    }

    @DeleteMapping("/{email}")
    public void deleteByEmail(@PathVariable("email") String email) {
        doctorService.deleteByEmail(email);
    }

    @PutMapping("/{email}")
    public DoctorDTO update(@PathVariable("email") String email, @RequestBody DoctorCreateCommand commandDTO) {
        return doctorService.updateByEmail(commandDTO, email);
    }

    @PatchMapping("/{email}/facilities/{facilityId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addFacility(@PathVariable("email") String email, @PathVariable("facilityId") Long facilityId) {
        doctorService.addFacility(email, facilityId);
    }

    @PatchMapping("/{email}")
    public boolean changePassword(@PathVariable("email") String email, @RequestBody Password changePassword) {
        return doctorService.changePasswordByEmail(email, changePassword.password());
    }
}
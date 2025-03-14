package com.medicalclinic.controller;

import com.medicalclinic.model.dto.PageDataDTO;
import com.medicalclinic.model.dto.facility.FacilityInDTO;
import com.medicalclinic.model.dto.facility.FacilityOutDTO;
import com.medicalclinic.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/facilities")
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping
    public PageDataDTO<FacilityOutDTO> getAll(Pageable pageable) {
        return facilityService.getAll(pageable);
    }

    @GetMapping("/{name}")
    public FacilityOutDTO getByName(@PathVariable("name") String name) {
        return facilityService.getByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody FacilityInDTO data) {
        facilityService.add(data);
    }

    @DeleteMapping("/{name}")
    public boolean deleteByEmail(@PathVariable("name") String name) {
        return facilityService.deleteByName(name);
    }

    @PutMapping("/{name}")
    public FacilityOutDTO update(@PathVariable("name") String name, @RequestBody FacilityInDTO data) {
        return facilityService.updateByName(data, name);
    }
}
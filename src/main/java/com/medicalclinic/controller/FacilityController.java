package com.medicalclinic.controller;

import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.facility.FacilityCreateCommand;
import com.medicalclinic.model.dto.facility.FacilityDTO;
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
    public PageableDataDTO<FacilityDTO> getAll(Pageable pageable) {
        return facilityService.getAll(pageable);
    }

    @GetMapping("/{name}")
    public FacilityDTO getByName(@PathVariable("name") String name) {
        return facilityService.getByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long add(@RequestBody FacilityCreateCommand data) {
        return facilityService.add(data);
    }

    @DeleteMapping("/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByName(@PathVariable("name") String name) {
        facilityService.deleteByName(name);
    }

    @PutMapping("/{name}")
    public FacilityDTO update(@PathVariable("name") String name, @RequestBody FacilityCreateCommand commandDTO) {
        return facilityService.updateByName(commandDTO, name);
    }
}
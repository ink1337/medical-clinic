package com.medicalclinic.controller;

import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.facility.FacilityCreateCommand;
import com.medicalclinic.model.dto.facility.FacilityDTO;
import com.medicalclinic.service.FacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public void add(@RequestBody FacilityCreateCommand data) {
        facilityService.add(data);
    }

    @DeleteMapping("/{name}")
    public void deleteByName(@PathVariable("name") String name) {
        facilityService.deleteByName(name);
    }

    @PutMapping("/{name}")
    public FacilityDTO update(@PathVariable("name") String name, @RequestBody FacilityCreateCommand commandDTO) {
        return facilityService.updateByName(commandDTO, name);
    }
}
package com.medicalclinic.controller;

import com.medicalclinic.model.dto.FacilityDTO;
import com.medicalclinic.model.entity.Facility;
import com.medicalclinic.service.FacilityService;
import lombok.RequiredArgsConstructor;
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

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/facilities")
public class FacilityController {
    private final FacilityService facilityService;

    @GetMapping
    public List<FacilityDTO> getAll() {
        return facilityService.getAll();
    }

    @GetMapping("/{name}")
    public FacilityDTO getByName(@PathVariable("name") String name) {
        return facilityService.getByName(name);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Facility data) {
        facilityService.add(data);
    }

    @DeleteMapping("/{name}")
    public boolean deleteByEmail(@PathVariable("name") String name) {
        return facilityService.deleteByName(name);
    }

    @PutMapping("/{name}")
    public FacilityDTO update(@PathVariable("name") String name, @RequestBody Facility data) {
        return facilityService.updateByName(data, name);
    }
}
package com.medicalclinic.controller;

import com.medicalclinic.model.VisitFilter;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.visit.VisitCreateCommand;
import com.medicalclinic.model.dto.visit.VisitDTO;
import com.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Long createVisit(@RequestBody VisitCreateCommand visitData) {
        return visitService.add(visitData);
    }

    @GetMapping
    public PageableDataDTO<VisitDTO> getVisits(VisitFilter visitFilter, Pageable pageable) {
        return visitService.getVisits(visitFilter, pageable);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/registerPatientToVisit/{visitId}/{patientId}")
    public void registerPatientToVisit(@PathVariable("visitId") Long visitId, @PathVariable("patientId") Long patientId) {
        visitService.registerPatientToVisit(visitId, patientId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVisit(@PathVariable("id") Long visitId) {
        visitService.deleteVisit(visitId);
    }
}
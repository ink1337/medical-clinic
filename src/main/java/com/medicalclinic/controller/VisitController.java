package com.medicalclinic.controller;

import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.visit.VisitCreateCommand;
import com.medicalclinic.model.dto.visit.VisitDTO;
import com.medicalclinic.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final VisitService visitService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public VisitDTO createVisit(@RequestBody VisitCreateCommand visitData) {
        return visitService.add(visitData);
    }

    @GetMapping
    public PageableDataDTO<VisitDTO> getAllVisits(Pageable pageable) {
        return visitService.getAll(pageable);
    }

    @GetMapping("/doctorId/{doctorId}")
    public Set<VisitDTO> getDoctorsVisits(@PathVariable("doctorId") Long doctorId) {
        return visitService.getDoctorsVisits(doctorId);
    }

    @GetMapping("/patientId/{patientId}")
    public Set<VisitDTO> getPatientsVisits(@PathVariable("patientId") Long patientId) {
        return visitService.getPatientsVisits(patientId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{id}/patientId/{patientId}")
    public void registerPatientToVisit(@PathVariable("id") Long visitId, @PathVariable("patientId") Long patientId) {
        visitService.registerPatientToVisit(visitId, patientId);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteVisit(@PathVariable("id") Long visitId) {
        visitService.deleteVisit(visitId);
    }
}
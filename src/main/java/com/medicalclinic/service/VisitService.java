package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingDoctorException;
import com.medicalclinic.mapper.VisitMapper;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.visit.VisitCreateCommand;
import com.medicalclinic.model.dto.visit.VisitDTO;
import com.medicalclinic.model.entity.Doctor;
import com.medicalclinic.model.entity.Patient;
import com.medicalclinic.model.entity.Visit;
import com.medicalclinic.repository.DoctorRepository;
import com.medicalclinic.repository.PatientRepository;
import com.medicalclinic.repository.VisitRepository;
import com.medicalclinic.validator.VisitValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@RequiredArgsConstructor
@Service
public class VisitService {
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;
    private final VisitValidator visitValidator;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    public PageableDataDTO<VisitDTO> getAll(Pageable pageable) {
        var result = visitRepository.findAll(pageable);
        return PageableDataDTO.from(visitMapper.toDTOs(result.getContent()), result, pageable);
    }

    @Transactional
    public VisitDTO add(VisitCreateCommand visit) {
        visitValidator.validateForPersist(visit);
        var entity = visitMapper.toEntity(visit);
        Doctor doctor = getDoctorById(visit.getDoctorId());
        entity.setDoctor(doctor);
        visitRepository.save(entity);
        return visitMapper.toDTO(entity);
    }

    @Transactional
    public void registerPatientToVisit(Long visitId, Long patientId) {
        Visit visit = getVisitWithId(visitId);
        Patient patient = getPatientById(patientId);
        visitValidator.validateVisitAvailability(visit);
        visit.setPatient(patient);
        visitRepository.save(visit);
    }

    @Transactional
    public void deleteVisit(Long visitId) {
        Visit visit = getVisitWithId(visitId);
        visitRepository.delete(visit);
    }

    public Set<VisitDTO> getDoctorsVisits(Long doctorId) {
        var doctor = getDoctorById(doctorId);
        return visitMapper.toDTOs(doctor.getVisits());
    }

    public Set<VisitDTO> getPatientsVisits(Long patientId) {
        var patient = getPatientById(patientId);
        return visitMapper.toDTOs(patient.getVisits());
    }

    private Visit getVisitWithId(Long visitId) {
        return visitRepository.findById(visitId)
                .orElseThrow(() -> new ProcessingDoctorException(getMessage("visit.not_found", visitId)));
    }

    private Doctor getDoctorById(Long doctorId) {
        return doctorRepository.findByIdWithVisits(doctorId)
                .orElseThrow(() -> new ProcessingDoctorException(getMessage("patient.id_not_found", doctorId)));
    }

    private Patient getPatientById(Long patientId) {
        return patientRepository.findByIdWithVisits(patientId)
                .orElseThrow(() -> new ProcessingDoctorException(getMessage("patient.id_not_found", patientId)));
    }
}

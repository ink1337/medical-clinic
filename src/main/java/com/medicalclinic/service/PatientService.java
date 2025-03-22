package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.PatientMapper;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.patient.PatientCreateCommand;
import com.medicalclinic.model.dto.patient.PatientDTO;
import com.medicalclinic.repository.PatientRepository;
import com.medicalclinic.repository.VisitRepository;
import com.medicalclinic.validator.PatientValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.medicalclinic.exception.DictionaryHandler.getMessage;

@RequiredArgsConstructor
@Service
public class PatientService {
    private final PatientValidator patientValidator;
    private final PatientRepository patientRepository;
    private final PatientMapper patientMapper;
    private final VisitRepository visitRepository;

    public PageableDataDTO<PatientDTO> getAll(Pageable pageable) {
        var result = patientRepository.findAll(pageable);
        return PageableDataDTO.from(patientMapper.toDTOs(result.getContent()), result, pageable);

    }

    public PatientDTO getByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }

    @Transactional
    public void add(PatientCreateCommand patient) {
        patientValidator.validatePatientForPersist(patient);
        patientRepository.save(patientMapper.toEntity(patient));
    }

    @Transactional
    public void deleteByEmail(String email) {
        var patient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
        visitRepository.detachPatientFromVisits(patient);
        patientRepository.delete(patient);
    }

    @Transactional
    public PatientDTO updateByEmail(PatientCreateCommand newPatient, String referencedEmail) {
        var entity = patientValidator.validateAndGetPatientToUpdate(newPatient, referencedEmail);
        entity.update(newPatient);
        patientRepository.save(entity);
        return patientMapper.toDTO(entity);
    }

    @Transactional
    public boolean changePasswordByEmail(String email, String password) {
        var existingPatient = patientRepository.findByEmail(email)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
        existingPatient.setPassword(password);
        return true;
    }
}

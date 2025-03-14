package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.PatientMapper;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.patient.PatientInDTO;
import com.medicalclinic.model.dto.patient.PatientOutDTO;
import com.medicalclinic.repository.PatientRepository;
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

    public PageableDataDTO<PatientOutDTO> getAll(Pageable pageable) {
        var result = patientRepository.findAll(pageable);
        return PageableDataDTO.<PatientOutDTO>builder()
                .data(patientMapper.toDTOs(result.getContent()))
                .totalPages(result.getTotalPages())
                .totalElements(result.getTotalElements())
                .currentPage(pageable.getPageNumber())
                .build();
    }

    public PatientOutDTO getByEmail(String email) {
        return patientRepository.findByEmail(email)
                .map(patientMapper::toDTO)
                .orElseThrow(() -> new ProcessingPatientException(getMessage("patient.not_found", email)));
    }

    @Transactional
    public void add(PatientInDTO patient) {
        patientValidator.validatePatientForPersist(patient);
        patientRepository.save(patientMapper.toEnity(patient));
    }

    @Transactional
    public boolean deleteByEmail(String email) {
        var patientOptional = patientRepository.findByEmail(email);
        if (patientOptional.isPresent()) {
            patientRepository.delete(patientOptional.get());
            return true;
        }
        return false;
    }

    @Transactional
    public PatientOutDTO updateByEmail(PatientInDTO newPatient, String referencedEmail) {
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

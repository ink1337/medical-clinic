package com.medicalclinic.service;

import com.medicalclinic.exception.ProcessingPatientException;
import com.medicalclinic.mapper.PatientMapper;
import com.medicalclinic.model.dto.patient.PatientCreateCommand;
import com.medicalclinic.model.dto.patient.PatientDTO;
import com.medicalclinic.model.entity.Patient;
import com.medicalclinic.repository.PatientRepository;
import com.medicalclinic.repository.VisitRepository;
import com.medicalclinic.validator.PatientValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class PatientServiceTest {

    private PatientService patientService;
    private PatientRepository patientRepository;
    private PatientValidator patientValidator;
    private VisitRepository visitRepository;

    @BeforeEach
    void setUp() {
        patientRepository = mock(PatientRepository.class);
        patientValidator = mock(PatientValidator.class);
        PatientMapper patientMapper = Mappers.getMapper(PatientMapper.class);
        visitRepository = mock(VisitRepository.class);

        patientService = new PatientService(
                patientValidator,
                patientRepository,
                patientMapper,
                visitRepository
        );
    }

    @Test
    void getAll_PatientsExist_ReturnsPageableDataDTO() {
        Pageable pageable = PageRequest.of(0, 10);
        Patient patient = new Patient();
        Page<Patient> page = new PageImpl<>(List.of(patient));

        when(patientRepository.findAll(pageable)).thenReturn(page);

        var result = patientService.getAll(pageable);

        assertEquals(1, result.getData().size());
        verify(patientRepository).findAll(pageable);
    }

    @Test
    void getByEmail_PatientExists_ReturnsPatientDTO() {
        String email = "john@example.com";
        Patient patient = new Patient();
        PatientDTO dto = PatientDTO.builder().build();

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));

        var result = patientService.getByEmail(email);

        assertEquals(dto, result);
        verify(patientRepository).findByEmail(email);
    }

    @Test
    void getByEmail_PatientDoesNotExist_ThrowsException() {
        String email = "john@example.com";

        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ProcessingPatientException.class, () -> patientService.getByEmail(any()));
    }

    @Test
    void add_ValidPatient_SavesToRepository() {
        PatientCreateCommand command = PatientCreateCommand.builder().build();
        Patient entity = Patient.builder().id(1L).build();
        when(patientRepository.save(any())).thenReturn(entity);

        var result = patientService.add(command);

        verify(patientValidator).validatePatientForPersist(command);
        assertEquals(1L, result);
    }

    @Test
    void deleteByEmail_PatientExists_DeletesPatientAndDetachesVisits() {
        String email = "john@example.com";
        Patient patient = new Patient();

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));

        patientService.deleteByEmail(email);

        verify(visitRepository).detachPatientFromVisits(patient);
        verify(patientRepository).delete(patient);
    }

    @Test
    void deleteByEmail_PatientDoesNotExist_ThrowsException() {
        String email = "john@example.com";

        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ProcessingPatientException.class, () -> patientService.deleteByEmail(any()));
    }

    @Test
    void updateByEmail_ValidData_ReturnsUpdatedDTO() {
        // given
        String referencedEmail = "old@example.com";

        PatientCreateCommand command = PatientCreateCommand.builder()
                .firstName("Anna")
                .lastName("Kowalska")
                .email("new@example.com")
                .build();

        Patient patientEntity = Patient.builder()
                .id(1L)
                .firstName("Old")
                .lastName("Name")
                .email(referencedEmail)
                .build();

        when(patientValidator.validateAndGetPatientToUpdate(command, referencedEmail)).thenReturn(patientEntity);
        // when
        PatientDTO result = patientService.updateByEmail(command, referencedEmail);

        // then
        verify(patientValidator).validateAndGetPatientToUpdate(command, referencedEmail);
        verify(patientRepository).save(patientEntity);

        assertEquals("Anna", result.getFirstName());
        assertEquals("Kowalska", result.getLastName());
        assertEquals("new@example.com", result.getEmail());
    }

    @Test
    void changePasswordByEmail_PatientExists_UpdatesPasswordAndReturnsTrue() {
        String email = "john@example.com";
        String newPassword = "newPass";
        Patient patient = new Patient();

        when(patientRepository.findByEmail(email)).thenReturn(Optional.of(patient));

        boolean result = patientService.changePasswordByEmail(email, newPassword);

        assertTrue(result);
        assertEquals(newPassword, patient.getPassword());
    }

    @Test
    void changePasswordByEmail_PatientDoesNotExist_ThrowsException() {
        String email = "john@example.com";

        when(patientRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(ProcessingPatientException.class, () -> patientService.changePasswordByEmail(email, "any"));
    }
}

package com.medicalclinic.service;

import com.medicalclinic.model.Patient;
import com.medicalclinic.repository.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    private Patient patient1;
    private Patient patient2;

    @BeforeEach
    void setUp() {
        patient1 = Patient.builder()
                .email("john.doe@example.com")
                .password("securepass123")
                .firstName("John")
                .lastName("Doe")
                .birthday(new Date())
                .idCardNo(123456789L)
                .phoneNumber("123-456-789")
                .build();

        patient2 = Patient.builder()
                .email("jane.doe@example.com")
                .password("strongpass456")
                .firstName("Jane")
                .lastName("Doe")
                .birthday(new Date())
                .idCardNo(987654321L)
                .phoneNumber("987-654-321")
                .build();
        patientService.addNewPatient(patient1);
        patientService.addNewPatient(patient2);

        verify(patientRepository, times(1)).persist(patient1);
        verify(patientRepository, times(1)).persist(patient2);
    }


    @Test
    void testGetPatients() {
        when(patientRepository.listAll()).thenReturn(Arrays.asList(patient1, patient2));

        List<Patient> patients = patientService.getPatients();

        assertEquals(2, patients.size());
        assertEquals("John", patients.get(0).getFirstName());
        assertEquals("Jane", patients.get(1).getFirstName());

        verify(patientRepository, times(1)).listAll();
    }

    @Test
    void testGetPatientByEmail() {
        when(patientRepository.findPatientByEmail("john.doe@example.com")).thenReturn(Optional.of(patient1));

        Patient found = patientService.getPatientByEmail("john.doe@example.com");

        assertNotNull(found);
        assertEquals("John", found.getFirstName());
        assertEquals("Doe", found.getLastName());

        verify(patientRepository, times(1)).findPatientByEmail("john.doe@example.com");
    }


    @Test
    void testDeletePatientByEmail_Success() {
        when(patientRepository.findPatientByEmail("john.doe@example.com")).thenReturn(Optional.of(patient1));
        when(patientRepository.deletePatient(patient1)).thenReturn(true);

        boolean result = patientService.deletePatientByEmail("john.doe@example.com");

        assertTrue(result);
        verify(patientRepository, times(1)).findPatientByEmail("john.doe@example.com");
        verify(patientRepository, times(1)).deletePatient(patient1);
    }

    @Test
    void testDeletePatientByEmail_NotFound() {
        when(patientRepository.findPatientByEmail("john.doe@example.com")).thenReturn(Optional.empty());

        boolean result = patientService.deletePatientByEmail("john.doe@example.com");

        assertFalse(result);
        verify(patientRepository, times(1)).findPatientByEmail("john.doe@example.com");
        verify(patientRepository, never()).deletePatient(any());
    }

    @Test
    void testUpdatePatientByEmail_Success() {
        when(patientRepository.updateByEmail(patient1, "john.doe@example.com")).thenReturn(true);

        boolean result = patientService.updatePatientByEmail(patient1, "john.doe@example.com");

        assertTrue(result);
        verify(patientRepository, times(1)).updateByEmail(patient1, "john.doe@example.com");
    }

    @Test
    void testUpdatePatientByEmail_NullPatient() {
        boolean result = patientService.updatePatientByEmail(null, "john.doe@example.com");

        assertFalse(result);
        verify(patientRepository, never()).updateByEmail(any(), any());
    }

    @Test
    void testUpdatePatientByEmail_NullEmail() {
        patient1 = patient1.toBuilder().email(null).build();

        boolean result = patientService.updatePatientByEmail(patient1, "john.doe@example.com");

        assertFalse(result);
        verify(patientRepository, never()).updateByEmail(any(), any());
    }
}

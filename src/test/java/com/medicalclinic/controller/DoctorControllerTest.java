package com.medicalclinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medicalclinic.model.Password;
import com.medicalclinic.model.dto.PageableDataDTO;
import com.medicalclinic.model.dto.doctor.DoctorCreateCommand;
import com.medicalclinic.model.dto.doctor.DoctorDTO;
import com.medicalclinic.service.DoctorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class DoctorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DoctorService doctorService;

    @Test
    public void addDoctor_ReturnsIdWithStatus201() throws Exception {
        DoctorCreateCommand command = DoctorCreateCommand.builder()
                .email("email")
                .password("pass")
                .firstName("Jan")
                .lastName("Kowalski")
                .specialization("Ortopeda")
                .build();

        when(doctorService.add(command)).thenReturn(1L);

        mockMvc.perform(post("/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @Test
    public void getDoctorByEmail_ReturnsDoctorDto() throws Exception {
        DoctorDTO dto = DoctorDTO.builder()
                .id(1L)
                .email("email")
                .firstName("Jan")
                .lastName("Kowalski")
                .specialization("Ortopeda")
                .facilities(Set.of())
                .build();

        when(doctorService.getByEmail("email")).thenReturn(dto);

        mockMvc.perform(get("/doctors/email"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("email"))
                .andExpect(jsonPath("$.firstName").value("Jan"))
                .andExpect(jsonPath("$.lastName").value("Kowalski"))
                .andExpect(jsonPath("$.specialization").value("Ortopeda"))
                .andExpect(jsonPath("$.facilities").isArray());
    }

    @Test
    public void deleteDoctorByEmail_Returns204() throws Exception {
        mockMvc.perform(delete("/doctors/email"))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(doctorService).deleteByEmail("email");
    }

    @Test
    public void updateDoctor_ReturnsUpdatedDoctorDto() throws Exception {
        DoctorCreateCommand command = DoctorCreateCommand.builder()
                .email("email")
                .password("pass")
                .firstName("Jan")
                .lastName("Nowak")
                .specialization("Chirurg")
                .build();

        DoctorDTO updated = DoctorDTO.builder()
                .id(1L)
                .email("email")
                .firstName("Jan")
                .lastName("Nowak")
                .specialization("Chirurg")
                .facilities(Set.of())
                .build();

        when(doctorService.updateByEmail(command, "email")).thenReturn(updated);

        mockMvc.perform(put("/doctors/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.lastName").value("Nowak"))
                .andExpect(jsonPath("$.specialization").value("Chirurg"));
    }

    @Test
    public void addFacilityToDoctor_Returns204() throws Exception {
        mockMvc.perform(patch("/doctors/email/facilities/5"))
                .andExpect(status().isNoContent());

        verify(doctorService).addFacility("email", 5L);
    }

    @Test
    public void changeDoctorPassword_ReturnsTrueWithStatus200() throws Exception {
        Password password = Password.builder()
                .password("newSecret123")
                .build();

        when(doctorService.changePasswordByEmail("email", "newSecret123")).thenReturn(true);

        mockMvc.perform(patch("/doctors/email")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(password)))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void getAllDoctors_ReturnsPageableDataDTOWithFullVerification() throws Exception {
        DoctorDTO doc1 = DoctorDTO.builder()
                .id(1L)
                .email("doc1@example.com")
                .firstName("Anna")
                .lastName("Nowak")
                .specialization("Ortopeda")
                .facilities(Set.of(1L))
                .build();

        DoctorDTO doc2 = DoctorDTO.builder()
                .id(2L)
                .email("doc2@example.com")
                .firstName("Marek")
                .lastName("Kowalski")
                .specialization("Kardiolog")
                .facilities(Set.of(1L))
                .build();

        PageableDataDTO<DoctorDTO> response = PageableDataDTO.<DoctorDTO>builder()
                .totalElements(2)
                .totalPages(1)
                .currentPage(0)
                .data(Set.of(doc1, doc2))
                .build();

        when(doctorService.getAll(any())).thenReturn(response);

        mockMvc.perform(get("/doctors")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.currentPage").value(0))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].email").value("doc1@example.com"))
                .andExpect(jsonPath("$.data[0].firstName").value("Anna"))
                .andExpect(jsonPath("$.data[0].lastName").value("Nowak"))
                .andExpect(jsonPath("$.data[0].specialization").value("Ortopeda"))
                .andExpect(jsonPath("$.data[0].facilities[0]").value("1"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].email").value("doc2@example.com"))
                .andExpect(jsonPath("$.data[1].firstName").value("Marek"))
                .andExpect(jsonPath("$.data[1].lastName").value("Kowalski"))
                .andExpect(jsonPath("$.data[1].specialization").value("Kardiolog"))
                .andExpect(jsonPath("$.data[1].facilities[0]").value("1"));
    }

    @Test
    public void getAllDoctors_ReturnsEmptyContent() throws Exception {
        PageableDataDTO<DoctorDTO> response = PageableDataDTO.<DoctorDTO>builder()
                .totalElements(0)
                .totalPages(1)
                .currentPage(0)
                .data(Set.of())
                .build();

        when(doctorService.getAll(any())).thenReturn(response);

        mockMvc.perform(get("/doctors")
                        .param("page", "0")
                        .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(0))
                .andExpect(jsonPath("$.data").isEmpty());
    }
}


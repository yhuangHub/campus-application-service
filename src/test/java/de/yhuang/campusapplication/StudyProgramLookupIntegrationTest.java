package de.yhuang.campusapplication;

import de.yhuang.campusapplication.studyprogram.exception.StudyProgramNotFoundException;
import de.yhuang.campusapplication.studyprogram.repository.StudyProgramRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class StudyProgramLookupIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudyProgramRepository studyProgramRepository;

    @Test
    public void lookUpStudyProgramByIdReturnsCorrectResponse() throws Exception {
        Long id = 1L;
        var studyProgramEntity = studyProgramRepository.findById(id).orElseThrow(() -> new StudyProgramNotFoundException(id));

        mockMvc.perform(get("/api/v1/study-programs/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(studyProgramEntity.getCode()))
                .andExpect(jsonPath("$.name").value(studyProgramEntity.getName()))
                .andExpect(jsonPath("$.university").value(studyProgramEntity.getUniversity()))
                .andExpect(jsonPath("$.capacity").value(studyProgramEntity.getCapacity()))
                .andExpect(jsonPath("$.active").value(studyProgramEntity.isActive()));
    }

    @Test
    public void lookUpStudyProgramByIdReturnsNotFoundException() throws Exception {
        Long id = 999L;

        mockMvc.perform(get("/api/v1/study-programs/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value("STUDY_PROGRAM_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Study program not found: " + id));
    }
}

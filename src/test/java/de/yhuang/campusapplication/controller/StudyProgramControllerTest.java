package de.yhuang.campusapplication.controller;

import de.yhuang.campusapplication.studyprogram.LookUpStudyProgramResponse;
import de.yhuang.campusapplication.studyprogram.controller.StudyProgramController;
import de.yhuang.campusapplication.studyprogram.exception.StudyProgramNotFoundException;
import de.yhuang.campusapplication.studyprogram.service.StudyProgramService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudyProgramController.class)
public class StudyProgramControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudyProgramService studyProgramService;

    @Test
    void getStudyProgramByIdReturnsCorrectResponseBody() throws Exception {
        Long id = 1L;

        var LookUpStudyProgramResponse = new LookUpStudyProgramResponse(
                id,
                "CS-BER-01",
                "Computer Science",
                "Campus University Berlin",
                120,
                true
        );

        when(studyProgramService.getStudyProgramResponse(id)).thenReturn(LookUpStudyProgramResponse);

        mockMvc.perform(get("/api/v1/study-programs/{id}", id))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.code").value("CS-BER-01"))
                .andExpect(jsonPath("$.name").value("Computer Science"))
                .andExpect(jsonPath("$.university").value("Campus University Berlin"))
                .andExpect(jsonPath("$.capacity").value(120)    )
                .andExpect(jsonPath("$.active").value(true));

        verify(studyProgramService).getStudyProgramResponse(id);
    }

    @Test
    void getStudyProgramByIdReturnsNotFound() throws Exception {
        Long id = 999L;
        when(studyProgramService.getStudyProgramResponse(id)).thenThrow(new StudyProgramNotFoundException(id));

        mockMvc.perform(get("/api/v1/study-programs/{id}", id))
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("STUDY_PROGRAM_NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Study program not found: " + id));

        verify(studyProgramService).getStudyProgramResponse(id);
    }
}

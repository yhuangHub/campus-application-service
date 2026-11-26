package de.yhuang.campusapplication.service;

import de.yhuang.campusapplication.studyprogram.entity.StudyProgram;
import de.yhuang.campusapplication.studyprogram.exception.StudyProgramNotFoundException;
import de.yhuang.campusapplication.studyprogram.repository.StudyProgramRepository;
import de.yhuang.campusapplication.studyprogram.service.StudyProgramService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudyProgramServiceTest {

    @Mock
    private StudyProgramRepository studyProgramRepository;

    @InjectMocks
    private StudyProgramService studyProgramService;

    @Test
    void getStudyProgramByIdReturnsCorrectStudyProgram() {
        Long id = 1L;
        var studyProgramEntity = new StudyProgram(
                "CS-BER-01",
                "Computer Science",
                "Campus University Berlin",
                120,
                true
        );
        ReflectionTestUtils.setField(studyProgramEntity, "id", id);

        when(studyProgramRepository.findById(id)).thenReturn(Optional.of(studyProgramEntity));

        var studyProgramResponse = studyProgramService.getStudyProgramResponse(id);

        assertThat(studyProgramResponse.id()).isEqualTo(id);
        assertThat(studyProgramResponse.code()).isEqualTo("CS-BER-01");
        assertThat(studyProgramResponse.name()).isEqualTo("Computer Science");
        assertThat(studyProgramResponse.university()).isEqualTo("Campus University Berlin");
        assertThat(studyProgramResponse.capacity()).isEqualTo(120);
        assertThat(studyProgramResponse.active()).isEqualTo(true);

        verify(studyProgramRepository).findById(id);
    }

    @Test
    void getStudyProgramByIdButNoStudyProgramWasFound() {
        Long id = 999L;

        when(studyProgramRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> studyProgramService.getStudyProgramResponse(id))
                .isInstanceOf(StudyProgramNotFoundException.class)
                .hasMessageContaining("Study program not found: " + id);

        verify(studyProgramRepository).findById(id);
    }
}

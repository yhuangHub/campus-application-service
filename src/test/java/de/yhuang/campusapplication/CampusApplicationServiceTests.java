package de.yhuang.campusapplication;

import de.yhuang.campusapplication.studyprogram.entity.StudyProgram;
import de.yhuang.campusapplication.studyprogram.repository.StudyProgramRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CampusApplicationServiceTests {

    @Autowired
    private StudyProgramRepository repository;

    @Test
    void contextLoadsWithThreeSeededStudyPrograms() {
        assertThat(repository.count()).isEqualTo(3);

        StudyProgram firstProgram = repository.findById(1L).orElseThrow();
        assertThat(firstProgram.getCode()).isEqualTo("CS-BER-01");
        assertThat(firstProgram.getName()).isEqualTo("Computer Science");
        assertThat(firstProgram.getUniversity()).isEqualTo("Campus University Berlin");
        assertThat(firstProgram.getCapacity()).isEqualTo(120);
        assertThat(firstProgram.isActive()).isTrue();
    }
}


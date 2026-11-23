package de.yhuang.campusapplication.studyprogram.repository;

import de.yhuang.campusapplication.studyprogram.entity.StudyProgram;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudyProgramRepository extends JpaRepository<StudyProgram, Long> {
}


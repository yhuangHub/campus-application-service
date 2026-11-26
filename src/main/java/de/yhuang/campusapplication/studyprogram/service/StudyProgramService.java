package de.yhuang.campusapplication.studyprogram.service;

import de.yhuang.campusapplication.studyprogram.LookUpStudyProgramResponse;
import de.yhuang.campusapplication.studyprogram.entity.StudyProgram;
import de.yhuang.campusapplication.studyprogram.exception.StudyProgramNotFoundException;
import de.yhuang.campusapplication.studyprogram.repository.StudyProgramRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudyProgramService {

    private StudyProgramRepository studyProgramRepository;

    public StudyProgramService(StudyProgramRepository studyProgramRepository) {
        this.studyProgramRepository = studyProgramRepository;
    }

    @Transactional
    public LookUpStudyProgramResponse getStudyProgramResponse(Long id) {

        return studyProgramRepository.findById(id)
                .map(this::convertEntityToResponse)
                .orElseThrow(() -> new StudyProgramNotFoundException(id));
    }

    private LookUpStudyProgramResponse convertEntityToResponse(final StudyProgram studyProgramEntity) {

        return new LookUpStudyProgramResponse(
                studyProgramEntity.getId(),
                studyProgramEntity.getCode(),
                studyProgramEntity.getName(),
                studyProgramEntity.getUniversity(),
                studyProgramEntity.getCapacity(),
                studyProgramEntity.isActive()
        );
    }
}

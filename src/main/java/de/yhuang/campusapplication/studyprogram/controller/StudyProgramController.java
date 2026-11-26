package de.yhuang.campusapplication.studyprogram.controller;

import de.yhuang.campusapplication.studyprogram.LookUpStudyProgramResponse;
import de.yhuang.campusapplication.studyprogram.service.StudyProgramService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/study-programs")
public class StudyProgramController {

    private StudyProgramService studyProgramService;

    public StudyProgramController(StudyProgramService studyProgramService) {
        this.studyProgramService = studyProgramService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LookUpStudyProgramResponse> getStudyProgramResponse(@PathVariable @Positive long id) {
        var response = studyProgramService.getStudyProgramResponse(id);

        return ResponseEntity.ok(response);
    }
}

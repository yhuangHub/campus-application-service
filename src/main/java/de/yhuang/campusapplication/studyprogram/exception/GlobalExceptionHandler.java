package de.yhuang.campusapplication.studyprogram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(StudyProgramNotFoundException.class)
    public ResponseEntity<ApiError> handleStudyProgramNotFoundException(StudyProgramNotFoundException ex) {
        var apiError = new ApiError(
                HttpStatus.NOT_FOUND.value(),
                "STUDY_PROGRAM_NOT_FOUND",
                ex.getMessage()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}

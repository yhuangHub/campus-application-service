package de.yhuang.campusapplication.studyprogram.exception;

public class StudyProgramNotFoundException extends RuntimeException {

    public StudyProgramNotFoundException(Long id) {
        super("Study program not found: " + id);
    }
}

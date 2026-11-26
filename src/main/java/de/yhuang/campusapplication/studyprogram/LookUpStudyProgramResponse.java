package de.yhuang.campusapplication.studyprogram;

public record LookUpStudyProgramResponse(
        Long id,
        String code,
        String name,
        String university,
        int capacity,
        boolean active
) {
}

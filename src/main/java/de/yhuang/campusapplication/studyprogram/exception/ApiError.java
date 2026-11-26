package de.yhuang.campusapplication.studyprogram.exception;

public record ApiError(
        int status,
        String code,
        String message
) {
}

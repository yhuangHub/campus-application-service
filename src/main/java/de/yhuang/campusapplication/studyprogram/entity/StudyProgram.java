package de.yhuang.campusapplication.studyprogram.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "study_programs")
public class StudyProgram {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String code;

    @Column(nullable = false, length = 120)
    private String name;

    @Column(nullable = false, length = 160)
    private String university;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false)
    private boolean active;

    protected StudyProgram() {
    }

    public StudyProgram(String code, String name, String university, Integer capacity, boolean active) {
        this.code = code;
        this.name = name;
        this.university = university;
        this.capacity = capacity;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public String getUniversity() {
        return university;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public boolean isActive() {
        return active;
    }
}


package com.example.StationMisyullaeng.entity;

public enum UserGrade {
    GENERAL("일반회원"),
    OWNER("점주회원"),
    ADMIN("관리자");

    private final String label;

    UserGrade(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}

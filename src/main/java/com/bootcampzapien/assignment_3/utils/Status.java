package com.bootcampzapien.assignment_3.utils;

public enum Status {
    CREATED("Created"),
    ALREADY_EXISTS("Already exists");

    public final String label;

    private Status(String label) {
        this.label = label;
    }
}

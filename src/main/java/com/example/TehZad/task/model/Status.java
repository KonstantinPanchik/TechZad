package com.example.TehZad.task.model;

import java.util.Optional;

public enum Status {

    NEW,
    IN_PROGRESS,
    DONE;

    public static Optional<Status> from(String state) {
        for (Status value : Status.values()) {
            if (value.name().equals(state)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }
}

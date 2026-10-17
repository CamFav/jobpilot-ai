package com.jobpilotai.searchprofile.domain;

public record ProfileName(String value) {
    public ProfileName {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(
                    "Profile name must not be blank");
        }

        value = value.trim();
    }
}
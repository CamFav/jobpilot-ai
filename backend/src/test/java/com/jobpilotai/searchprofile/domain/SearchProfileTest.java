package com.jobpilotai.searchprofile.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SearchProfileTest {

    @Test
    void newProfileShouldBeActive() {
        SearchProfile profile = new SearchProfile("Développeur Java");

        assertEquals(SearchProfileStatus.ACTIVE, profile.getStatus());
    }

    @Test
    void pauseShouldChangeStatusToPaused() {

        // Arrange : Create a profile and ensure it is active
        SearchProfile profile = new SearchProfile("Développeur Java");

        // Act : Execute the pause action to test
        profile.pause();

        // Assert : Check the result of the action to ensure it behaves as expected
        assertEquals(SearchProfileStatus.PAUSED, profile.getStatus());
    }

    @Test
    void resumeShouldChangeStatusToActive() {
        SearchProfile profile = new SearchProfile("Développeur Java");

        profile.pause();

        profile.resume();

        assertEquals(SearchProfileStatus.ACTIVE, profile.getStatus());
    }

    @Test
    void emptyNameShouldBeRejected() {
        assertThrows(
                IllegalArgumentException.class,
                () -> new SearchProfile(""));
    }
}
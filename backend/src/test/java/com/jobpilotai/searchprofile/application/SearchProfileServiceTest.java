package com.jobpilotai.searchprofile.application;

import com.jobpilotai.searchprofile.domain.SearchProfile;
import com.jobpilotai.searchprofile.domain.SearchProfileStatus;
import com.jobpilotai.searchprofile.support.InMemorySearchProfileRepository;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SearchProfileServiceTest {

    @Test
    void createShouldSaveAnActiveProfile() {

        // Arrange : Set up the necessary objects and state for the test
        InMemorySearchProfileRepository repository = new InMemorySearchProfileRepository();

        SearchProfileService service = new SearchProfileService(repository);

        // Act : Execute the action to test
        SearchProfile profile = service.create("Développeur Java");

        // Assert : Check the result of the action to ensure it behaves as expected
        assertEquals("Développeur Java", profile.getName());

        assertEquals(SearchProfileStatus.ACTIVE, profile.getStatus());

        assertSame(profile, repository.getStoredProfile());
    }
}
package com.jobpilotai.searchprofile.support;

import com.jobpilotai.searchprofile.application.SearchProfileRepository;
import com.jobpilotai.searchprofile.domain.SearchProfile;

public class InMemorySearchProfileRepository
        implements SearchProfileRepository {

    private SearchProfile storedProfile;

    @Override
    public SearchProfile save(SearchProfile profile) {
        this.storedProfile = profile;
        return profile;
    }

    public SearchProfile getStoredProfile() {
        return storedProfile;
    }
}
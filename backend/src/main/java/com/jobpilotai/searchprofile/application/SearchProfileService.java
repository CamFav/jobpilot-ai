package com.jobpilotai.searchprofile.application;

import com.jobpilotai.searchprofile.domain.SearchProfile;

public class SearchProfileService {

    private final SearchProfileRepository repository;

    public SearchProfileService(SearchProfileRepository repository) {
        this.repository = repository;
    }

    public SearchProfile create(String name) {
        SearchProfile profile = new SearchProfile(name);

        return repository.save(profile);
    }
}
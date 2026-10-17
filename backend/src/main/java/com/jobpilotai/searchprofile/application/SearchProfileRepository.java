package com.jobpilotai.searchprofile.application;

import com.jobpilotai.searchprofile.domain.SearchProfile;

public interface SearchProfileRepository {

    SearchProfile save(SearchProfile profile);

}
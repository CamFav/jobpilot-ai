package com.jobpilotai.searchprofile.domain;

public class SearchProfile {

    private final ProfileName name;
    private SearchProfileStatus status;

    public SearchProfile(String name) {
        this.name = new ProfileName(name);
        this.status = SearchProfileStatus.ACTIVE;
    }

    public void pause() {
        this.status = SearchProfileStatus.PAUSED;
    }

    public void resume() {
        this.status = SearchProfileStatus.ACTIVE;
    }

    public String getName() {
        return name.value();
    }

    public SearchProfileStatus getStatus() {
        return status;
    }
}
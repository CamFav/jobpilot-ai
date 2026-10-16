package com.jobpilotai.system.api;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class PingResponseTest {

    @Test
    void exposesTheStableAvailableStatus() {
        PingResponse response = new PingResponse("ok");

        assertThat(response.status()).isEqualTo("ok");
    }
}

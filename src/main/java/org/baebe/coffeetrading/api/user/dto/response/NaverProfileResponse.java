package org.baebe.coffeetrading.api.user.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class NaverProfileResponse {

    @JsonProperty("resultcode")
    private String resultCode;
    private String message;
    private NaverProfile response;

    @Getter
    public static class NaverProfile {
        private String nickname;
        private String email;
        private String name;
        @JsonProperty("phone")
        private String mobile;
        private String gender;
        private String birthday;
        @JsonProperty("birthyear")
        private String birthYear;
    }
}

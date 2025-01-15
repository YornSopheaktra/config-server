package com.trazzz.configserver.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trazzz.configserver.model.entity.ApplicationProperties;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationPropertiesResponse implements Serializable {
    private Profile profile;
    Map<String, Object> properties = new HashMap<>();

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static class Profile {
        String env;
        @JsonProperty("app_name")
        String appName;
        String label;
    }

    public static ApplicationPropertiesResponse fromEntities(List<ApplicationProperties> properties) {
        if (properties.isEmpty()) return null;
        ApplicationPropertiesResponse response = new ApplicationPropertiesResponse();
        response.setProfile(Profile.builder()
                .env(properties.get(0).getProfile())
                .label(properties.get(0).getLabel())
                .appName(properties.get(0).getAppName())
                .build());
        properties.forEach(prop -> response.getProperties().put(prop.getKey(), prop.getValue()));
        return response;
    }
}

package com.trazzz.configserver.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trazzz.configserver.model.entity.ApplicationProperties;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class AppPropertiesRequest implements Serializable {
    @NotEmpty(message = "app_name is required.")
    @JsonProperty("app_name")
    private String appName;
    @NotEmpty(message = "label is required.")
    private String label;
    @NotEmpty(message = "profile is required.")
    private String profile;
    @NotEmpty(message = "properties is required.")
    Map<String, Object> properties;

    public List<ApplicationProperties> toEntities(){
        return properties.keySet().stream()
                .map(prop -> ApplicationProperties.builder()
                        .appName(this.appName)
                        .profile(this.profile)
                        .label(this.label)
                        .key(prop)
                        .value(this.properties.get(prop).toString())
                        .build()
                )
                .collect(Collectors.toList());
    }
}

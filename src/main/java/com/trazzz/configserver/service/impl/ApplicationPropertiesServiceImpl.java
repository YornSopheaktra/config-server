package com.trazzz.configserver.service.impl;

import com.trazzz.configserver.model.entity.ApplicationProperties;
import com.trazzz.configserver.model.enumeration.Status;
import com.trazzz.configserver.model.response.ApplicationPropertiesResponse;
import com.trazzz.configserver.repository.ApplicationPropertiesRepo;
import com.trazzz.configserver.service.ApplicationPropertiesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ApplicationPropertiesServiceImpl implements ApplicationPropertiesService {

    private final ApplicationPropertiesRepo propertiesRepo;

    @Override
    public ApplicationPropertiesResponse save(Map<String, Object> properties, String appName, String profile, String label) {
        List<ApplicationProperties> propertiesList = propertiesRepo.findAllByAppNameAndProfileAndLabel(appName, profile, label);
        if (propertiesList.isEmpty()) {
            List<ApplicationProperties> collect = properties.keySet().stream().map(prop -> ApplicationProperties.builder()
                    .key(prop)
                    .value(properties.get(prop).toString())
                    .appName(appName)
                    .profile(profile)
                    .label(label)
                    .status(Status.ACTIVE)
                    .build()
            ).collect(Collectors.toList());
            List<ApplicationProperties> propertiesListSaved = propertiesRepo.saveAll(collect);
            return ApplicationPropertiesResponse.fromEntities(propertiesListSaved);
        }
        List<ApplicationProperties> existingProps = propertiesList.stream()
                .filter(prop -> properties.containsKey(prop.getKey()))
                .map(newPro -> ApplicationProperties.builder()
                        .id(newPro.getId())
                        .key(newPro.getKey())
                        .value(properties.get(newPro.getKey()).toString())
                        .profile(newPro.getProfile())
                        .label(newPro.getLabel())
                        .appName(newPro.getAppName())
                        .status(Status.ACTIVE)
                        .build())
                .collect(Collectors.toList());
        List<ApplicationProperties> newProps = properties.keySet().stream().filter(key -> !propertiesList.stream().map(ApplicationProperties::getKey).toList().contains(key))
                .map(newKey -> {
                    return ApplicationProperties.builder()
                            .key(newKey)
                            .value(properties.get(newKey).toString())
                            .profile(profile)
                            .appName(appName)
                            .label(label)
                            .status(Status.ACTIVE)
                            .build();
                }).collect(Collectors.toList());
        newProps = propertiesRepo.saveAll(newProps);
        propertiesRepo.saveAll(existingProps);
        newProps.addAll(existingProps);
        return ApplicationPropertiesResponse.fromEntities(newProps);
    }

    @Override
    public ApplicationPropertiesResponse getProperties(Status status, String appName, String profile, String label) {
        return ApplicationPropertiesResponse.fromEntities(propertiesRepo.findAllByAppNameAndProfileAndLabelAndStatus(appName, profile, label, status));
    }
}

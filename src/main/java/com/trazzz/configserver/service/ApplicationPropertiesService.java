package com.trazzz.configserver.service;

import com.trazzz.configserver.model.enumeration.Status;
import com.trazzz.configserver.model.response.ApplicationPropertiesResponse;

import java.util.Map;

public interface ApplicationPropertiesService {
    ApplicationPropertiesResponse save(Map<String, Object> properties, String appName, String profile, String label);
    ApplicationPropertiesResponse getProperties(Status status, String appName, String profile, String label);
}

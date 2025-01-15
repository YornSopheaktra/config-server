package com.trazzz.configserver.repository;

import com.trazzz.configserver.model.entity.ApplicationProperties;
import com.trazzz.configserver.model.enumeration.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationPropertiesRepo extends JpaRepository<ApplicationProperties, Long> {

    List<ApplicationProperties> findAllByAppNameAndProfileAndLabel(String appName, String profile, String label);
    List<ApplicationProperties> findAllByStatus(Status status);
    List<ApplicationProperties> findAllByAppNameAndProfileAndLabelAndStatus(String appName, String profile, String label, Status status);
}

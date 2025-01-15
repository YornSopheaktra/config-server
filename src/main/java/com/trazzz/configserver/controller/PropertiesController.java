package com.trazzz.configserver.controller;

import com.trazzz.configserver.model.enumeration.Status;
import com.trazzz.configserver.service.ApplicationPropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/properties")
public class PropertiesController {

    @Autowired
    private ApplicationPropertiesService propertiesService;

    @GetMapping
    public String welcome(){
        return "Welcome to Config Server";
    }

    @PostMapping("/{app_name}/{profile}/{label}")
    ResponseEntity<?> save(@RequestBody Map<String, Object> request,
                           @PathVariable("app_name") String appName,
                           @PathVariable("profile") String profile,
                           @PathVariable("label") String label) {
        return ResponseEntity.ok(propertiesService.save(request, appName, profile, label));
    }

    @GetMapping("/{app_name}/{profile}/{label}/{status}")
    ResponseEntity<?> save(@PathVariable("app_name") String appName,
                           @PathVariable("profile") String profile,
                           @PathVariable("label") String label,
                           @PathVariable("status") Status status) {
        return ResponseEntity.ok(propertiesService.getProperties(status, appName, profile, label));
    }
}

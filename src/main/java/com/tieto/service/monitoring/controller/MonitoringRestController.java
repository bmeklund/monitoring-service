package com.tieto.service.monitoring.controller;

import com.tieto.service.monitoring.domain.StatusCheck;
import com.tieto.service.monitoring.domain.dto.Response;
import com.tieto.service.monitoring.domain.dto.ServiceStatus;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MonitoringRestController {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringRestController.class);
    private StatusCheck statusCheck;

    @Autowired
    public MonitoringRestController(StatusCheck statusCheck) {
        this.statusCheck = statusCheck;
    }

    @ApiOperation(value = "Get health status")
    @GetMapping("/health")
    public ResponseEntity<Response> getStatus() {
        logger.info("Got status call from Tieto watch");
        Response response = statusCheck.checkStatus();
        if (ServiceStatus.OK.equals(response.getStatus())) {
            logger.info("StatusCheck: serviceStatus={}", response);
        } else {
            logger.error("Statuscheck failed: serviceStatus={}", response);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}

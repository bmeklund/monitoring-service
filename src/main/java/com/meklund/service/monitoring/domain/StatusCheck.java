package com.meklund.service.monitoring.domain;

import com.meklund.service.monitoring.domain.dto.Response;
import com.meklund.service.monitoring.domain.dto.ServiceStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class StatusCheck {

    private static final Logger logger = LoggerFactory.getLogger(StatusCheck.class);
    private static final String BODY_OK = "-Monitoring ok";
    private static final String BODY_NOK = "Project not available, status unknown";

    public Response checkStatus() {
        Response response = new Response();
        try {
            String currentProject = System.getenv().getOrDefault("OPENSHIFT_PROJECT", "Projectname-missing");
            logger.info("Checking status on project: {}", currentProject);
            response.setStatus(ServiceStatus.OK);
            response.setBody(currentProject + BODY_OK);
        } catch(Exception exception) {
            logger.error("Statuschecking on project failed");
            response.setStatus(ServiceStatus.CRITICAL);
            response.setBody(BODY_NOK);
        }
        return response;
    }
}

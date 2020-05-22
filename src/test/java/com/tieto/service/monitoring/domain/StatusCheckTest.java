package com.tieto.service.monitoring.domain;

import com.tieto.service.monitoring.domain.dto.Response;
import com.tieto.service.monitoring.domain.dto.ServiceStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class StatusCheckTest {

    @Autowired
    private StatusCheck statusCheck;

    @Test
    public void shouldReturnOk() {
        String expectedBody = "Projectname-missing-Monitoring ok";
        Response result = statusCheck.checkStatus();
        assertEquals(expectedBody, result.getBody());
        assertEquals(ServiceStatus.OK, result.getStatus());
    }

}

package com.tieto.service.monitoring.controller;

import com.google.gson.Gson;
import com.tieto.service.monitoring.domain.StatusCheck;
import com.tieto.service.monitoring.domain.dto.Response;
import com.tieto.service.monitoring.domain.dto.ServiceStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class MonitoringRestControllerTest {

    private static final Logger logger = LoggerFactory.getLogger(MonitoringRestControllerTest.class);

    private MockMvc mockMvc;

    @Autowired
    private MonitoringRestController controller;

    @MockBean
    private StatusCheck statusCheck;

    @Before
    public void init() {
        mockMvc = standaloneSetup(controller).build();
    }

    @Test
    public void testPingSuccessfully() throws Exception {
        Response response = new Response(ServiceStatus.OK, "Monitoring Ok");

        Mockito.when(statusCheck.checkStatus()).thenReturn(response);

        String result = mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        logger.info("RESPONSE: {}", result);
        assertEquals(responseToJson(response), result);
    }

    @Test
    public void testPingWhenEntityServicesNotAvailable() throws Exception {
        Response response = new Response(ServiceStatus.CRITICAL, "Something amiss");

        Mockito.when(statusCheck.checkStatus()).thenReturn(response);

        String result = mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertEquals(responseToJson(response), result);
    }

    private String responseToJson(Response resp) {
        Gson gson = new Gson();
        return gson.toJson(resp);
    }
}
package com.viktorkuts.apigateway.domainclientlayer.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class EventClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String EVENTS_BASE_URL;

    public EventClient(RestTemplate restTemplate, ObjectMapper mapper, @Value("${app.events-service.host}") String host, @Value("${app.events-service.port}") String port) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.EVENTS_BASE_URL = "http://" + host + ":" + port + "/api/v1/events";
    }


}

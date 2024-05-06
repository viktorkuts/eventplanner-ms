package com.viktorkuts.apigateway.domainclientlayer.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktorkuts.apigateway.presentationlayer.events.EventRequestModel;
import com.viktorkuts.apigateway.presentationlayer.events.EventResponseModel;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;
import com.viktorkuts.apigateway.utils.HttpErrorInfo;
import com.viktorkuts.apigateway.utils.exceptions.InvalidPostalCodeException;
import com.viktorkuts.apigateway.utils.exceptions.NotFoundException;
import jdk.jfr.Event;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
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

    public List<EventResponseModel> getAllEventsForPerformer(String customerId){
        final String url = EVENTS_BASE_URL + "/performer/" + customerId;
        List<EventResponseModel> events = null;
        try{
            EventResponseModel[] models = restTemplate.getForObject(url, EventResponseModel[].class);
            events = Arrays.asList(models);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return events;
    }

    public List<TicketResponseModel> getAllTicketsForCustomer(String customerId){
        final String url = EVENTS_BASE_URL + "/tickets/" + customerId;
        List<TicketResponseModel> tickets = null;
        try{
            TicketResponseModel[] models = restTemplate.getForObject(url, TicketResponseModel[].class);
            tickets = Arrays.asList(models);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return tickets;
    }

    public List<EventResponseModel> getEventsForVenue(String venueId){
        final String url = EVENTS_BASE_URL + "/venue/" + venueId;
        List<EventResponseModel> events = null;
        try{
            EventResponseModel[] models = restTemplate.getForObject(url, EventResponseModel[].class);
            events = Arrays.asList(models);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return events;
    }

    public List<EventResponseModel> getAllEvents(){
        final String url = EVENTS_BASE_URL;
        List<EventResponseModel> events = null;
        try {
            EventResponseModel[] models = restTemplate.getForObject(url, EventResponseModel[].class);
            events = Arrays.asList(models);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return events;
    }

    public EventResponseModel getEvent(String eventId){
        final String url = EVENTS_BASE_URL + "/" + eventId;
        EventResponseModel event = null;
        try{
            event = restTemplate.getForObject(url, EventResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return event;
    }

    public EventResponseModel createEvent(EventRequestModel eventRequestModel){
        final String url = EVENTS_BASE_URL;
        EventResponseModel event = null;
        try{
            event = restTemplate.postForObject(url, eventRequestModel, EventResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return event;
    }

    public EventResponseModel updateEvent(EventRequestModel eventRequestModel, String eventId){
        final String url = EVENTS_BASE_URL + "/" + eventId;
        EventResponseModel event = null;
        try{
            restTemplate.put(url, eventRequestModel);
            event = restTemplate.getForObject(url, EventResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return event;
    }

    public void deleteEvent(String eventId){
        final String url = EVENTS_BASE_URL + "/" + eventId;
        try{
            restTemplate.delete(url);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
    }


    private String getErrorMessage(HttpClientErrorException ex){
        try{
            return mapper.readValue(ex.getResponseBodyAsString(), HttpErrorInfo.class).getMessage();
        }catch (IOException ioex){
            return ioex.getMessage();
        }
    }

    private RuntimeException handleHttpClientException(HttpClientErrorException ex){
        if(ex.getStatusCode() == HttpStatus.NOT_FOUND){
            return new NotFoundException(getErrorMessage(ex));
        }
        if(ex.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){
            return new InvalidPostalCodeException(getErrorMessage(ex));
        }
        log.warn("HTTP Error: {}", ex.getStatusCode());
        log.warn("Error Body: {}", ex.getResponseBodyAsString());
        return ex;
    }

    private void handleRestClientException(RestClientException ex){
        log.warn("Got RestClientException: " + ex.getMessage());
    }

}

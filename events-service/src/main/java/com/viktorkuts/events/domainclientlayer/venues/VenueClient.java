package com.viktorkuts.events.domainclientlayer.venues;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktorkuts.events.domainclientlayer.venues.VenueModel;
import com.viktorkuts.events.utils.HttpErrorInfo;
import com.viktorkuts.events.utils.exceptions.InvalidPostalCodeException;
import com.viktorkuts.events.utils.exceptions.InvalidTimeAllocationException;
import com.viktorkuts.events.utils.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class VenueClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String VENUE_BASE_URL;

    private VenueClient(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.venues-service.host}") String host,
            @Value("${app.venues-service.port}") String port) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.VENUE_BASE_URL = "http://" + host + ":" + port + "/api/v1/venues";
    }

    public List<VenueModel> getAllVenues(){
        final String url = VENUE_BASE_URL;
        List<VenueModel> responseModels = new ArrayList<>();
        try{
            VenueModel[] res = restTemplate.getForObject(url, VenueModel[].class);
            if(res != null) responseModels = Arrays.asList(res);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModels;
    }

    public VenueModel getVenue(String venueId){
        final String url = VENUE_BASE_URL + "/" + venueId;
        VenueModel responseModel = null;
        try{
            responseModel = restTemplate.getForObject(url, VenueModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
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
        if(ex.getStatusCode() == HttpStatus.BAD_REQUEST){
            return new InvalidTimeAllocationException(getErrorMessage(ex));
        }
        log.warn("HTTP Error: {}", ex.getStatusCode());
        log.warn("Error Body: {}", ex.getResponseBodyAsString());
        return ex;
    }

    private void handleRestClientException(RestClientException ex){
        log.warn("Got RestClientException: " + ex.getMessage());
    }


}

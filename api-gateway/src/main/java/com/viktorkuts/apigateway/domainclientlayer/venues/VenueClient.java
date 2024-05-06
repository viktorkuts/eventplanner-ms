package com.viktorkuts.apigateway.domainclientlayer.venues;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktorkuts.apigateway.presentationlayer.venues.VenueRequestModel;
import com.viktorkuts.apigateway.presentationlayer.venues.VenueResponseModel;
import com.viktorkuts.apigateway.utils.HttpErrorInfo;
import com.viktorkuts.apigateway.utils.exceptions.InvalidPostalCodeException;
import com.viktorkuts.apigateway.utils.exceptions.InvalidTimeAllocationException;
import com.viktorkuts.apigateway.utils.exceptions.NotFoundException;
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

    public List<VenueResponseModel> getAllVenues(){
        final String url = VENUE_BASE_URL;
        List<VenueResponseModel> responseModels = new ArrayList<>();
        try{
            VenueResponseModel[] res = restTemplate.getForObject(url, VenueResponseModel[].class);
            if(res != null) responseModels = Arrays.asList(res);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModels;
    }

    public VenueResponseModel getVenue(String venueId){
        final String url = VENUE_BASE_URL + "/" + venueId;
        VenueResponseModel responseModel = null;
        try{
            responseModel = restTemplate.getForObject(url, VenueResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public VenueResponseModel addVenue(VenueRequestModel requestModel){
        final String url = VENUE_BASE_URL;
        VenueResponseModel responseModel = null;
        try{
            responseModel = restTemplate.postForObject(url, requestModel, VenueResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public VenueResponseModel editVenue(VenueRequestModel requestModel, String venueId){
        final String url = VENUE_BASE_URL + "/" + venueId;
        VenueResponseModel responseModel = null;
        try{
            restTemplate.put(url, requestModel);
            responseModel = getVenue(venueId);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public void deleteVenue(String venueId){
        final String url = VENUE_BASE_URL + "/" + venueId;
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

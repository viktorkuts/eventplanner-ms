package com.viktorkuts.apigateway.domainclientlayer.tickets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktorkuts.apigateway.presentationlayer.customers.CustomerResponseModel;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketRequestModel;
import com.viktorkuts.apigateway.presentationlayer.tickets.TicketResponseModel;
import com.viktorkuts.apigateway.utils.HttpErrorInfo;
import com.viktorkuts.apigateway.utils.exceptions.InvalidPostalCodeException;
import com.viktorkuts.apigateway.utils.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
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
public class TicketClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String TICKETS_BASE_URL;

    private TicketClient(
            RestTemplate restTemplate,
            ObjectMapper mapper,
            @Value("${app.tickets-service.host}") String host,
            @Value("${app.tickets-service.port}") String port
    ) {
        this.restTemplate = restTemplate;
        this.mapper = mapper;
        this.TICKETS_BASE_URL = "http://" + host + ":" + port + "/api/v1/tickets";
    }

    public List<TicketResponseModel> getAllTickets(){
        final String url = TICKETS_BASE_URL;
        List<TicketResponseModel> responseModels = new ArrayList<>();
        try{
            TicketResponseModel[] res = restTemplate.getForObject(url, TicketResponseModel[].class);
            if(res != null) responseModels = Arrays.asList(res);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModels;
    }

    public TicketResponseModel getTicket(String ticketId){
        final String url = TICKETS_BASE_URL + "/" + ticketId;
        TicketResponseModel responseModel = null;
        try{
            responseModel = restTemplate.getForObject(url, TicketResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public TicketResponseModel addTicket(TicketRequestModel requestModel){
        final String url = TICKETS_BASE_URL;
        TicketResponseModel responseModel = null;
        try{
            responseModel = restTemplate.postForObject(url, requestModel, TicketResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public TicketResponseModel editTicket(TicketRequestModel requestModel, String ticketId){
        final String url = TICKETS_BASE_URL + "/" + ticketId;
        TicketResponseModel responseModel = null;
        try{
            restTemplate.put(url, requestModel);
            responseModel = getTicket(ticketId);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public void deleteTicket(String ticketId){
        final String url = TICKETS_BASE_URL + "/" + ticketId;
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

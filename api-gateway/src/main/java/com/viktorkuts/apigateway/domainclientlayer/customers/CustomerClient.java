package com.viktorkuts.apigateway.domainclientlayer.customers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viktorkuts.apigateway.presentationlayer.customers.CustomerRequestModel;
import com.viktorkuts.apigateway.presentationlayer.customers.CustomerResponseModel;
import com.viktorkuts.apigateway.utils.HttpErrorInfo;
import com.viktorkuts.apigateway.utils.exceptions.InvalidPostalCodeException;
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
public class CustomerClient {
    private final RestTemplate restTemplate;
    private final ObjectMapper mapper;
    private final String CUSTOMER_BASE_URL;

    private CustomerClient(
            RestTemplate restTemplate,
            ObjectMapper objectMapper,
            @Value("${app.customers-service.host}") String host,
            @Value("${app.customers-service.port}") String port
    ){
        this.restTemplate = restTemplate;
        this.mapper = objectMapper;
        CUSTOMER_BASE_URL = "http://" + host + ":" + port + "/api/v1/customers";
    }

    public List<CustomerResponseModel> getAllCustomers(){
        final String url = CUSTOMER_BASE_URL;
        List<CustomerResponseModel> responseModels = new ArrayList<>();
        try{
            CustomerResponseModel[] res = restTemplate.getForObject(url, CustomerResponseModel[].class);
            if (res != null) responseModels = Arrays.asList(res);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModels;
    }

    public CustomerResponseModel getCustomer(String customerId){
        final String url = CUSTOMER_BASE_URL + "/" + customerId;
        CustomerResponseModel responseModel = null;
        try{
            responseModel = restTemplate.getForObject(url, CustomerResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public CustomerResponseModel postCustomer(CustomerRequestModel requestModel){
        final String url = CUSTOMER_BASE_URL;
        CustomerResponseModel responseModel = null;
        try{
            responseModel = restTemplate.postForObject(url, requestModel, CustomerResponseModel.class);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public CustomerResponseModel editCustomer(CustomerRequestModel requestModel, String customerId){
        final String url = CUSTOMER_BASE_URL + "/" + customerId;
        CustomerResponseModel responseModel = null;
        try{
            restTemplate.put(url, requestModel);
            responseModel = getCustomer(customerId);
        }catch (HttpClientErrorException ex){
            throw handleHttpClientException(ex);
        }catch (RestClientException ex){
            handleRestClientException(ex);
        }
        return responseModel;
    }

    public void deleteCustomer(String customerId){
        final String url = CUSTOMER_BASE_URL + "/" + customerId;
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

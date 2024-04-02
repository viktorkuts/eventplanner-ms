package com.viktorkuts.customers.presentationlayer;

import com.viktorkuts.customers.logiclayer.CustomerServiceImpl;
import com.viktorkuts.customers.presentationlayer.models.CustomerRequestModel;
import com.viktorkuts.customers.presentationlayer.models.CustomerResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {
    private CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CustomerResponseModel>> getAll(){
        return ResponseEntity.ok(customerService.getCustomers());
    }

    @GetMapping(value = "{customerId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseModel> getCustomer(@PathVariable String customerId){
        return ResponseEntity.ok(customerService.getCustomer(customerId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseModel> addCustomer(@RequestBody CustomerRequestModel requestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.addCustomer(requestModel));
    }

    @PutMapping(value = "{customerId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CustomerResponseModel> editCustomer(@PathVariable String customerId, @RequestBody CustomerRequestModel requestModel){
        return ResponseEntity.ok(customerService.editCustomer(customerId, requestModel));
    }

    @DeleteMapping(value = "{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable String customerId){
        customerService.deleteCustomer(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

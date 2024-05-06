package com.viktorkuts.apigateway.presentationlayer.venues;

import com.viktorkuts.apigateway.logiclayer.venues.VenueService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venues")
public class VenueController {
    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VenueResponseModel>> getAllVenues(){
        return ResponseEntity.ok(venueService.getAllVenues());
    }

    @GetMapping(value = "{venueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponseModel> getVenueById(@PathVariable String venueId){
        return ResponseEntity.ok(venueService.getVenue(venueId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponseModel> addVenue(@RequestBody VenueRequestModel requestModel){
        return ResponseEntity.status(HttpStatus.CREATED).body(venueService.addVenue(requestModel));
    }

    @PutMapping(value = "{venueId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponseModel> editVenue(@RequestBody VenueRequestModel requestModel, @PathVariable String venueId){
        return ResponseEntity.ok(venueService.editVenue(requestModel, venueId));
    }

    @DeleteMapping("{venueId}")
    public ResponseEntity<Void> deleteVenue(@PathVariable String venueId){
        venueService.deleteVenue(venueId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}

package com.viktorkuts.venues.presentationlayer;

import com.viktorkuts.venues.logiclayer.VenueService;
import com.viktorkuts.venues.presentationlayer.models.VenueRequestModel;
import com.viktorkuts.venues.presentationlayer.models.VenueResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/venues")
public class VenueController {
    private VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<VenueResponseModel>> getVenues() {
        return ResponseEntity.ok(venueService.getVenues());
    }

    @GetMapping(value = "{venueId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponseModel> getVenue(@PathVariable String venueId) {
        return ResponseEntity.ok(venueService.getVenue(venueId));
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponseModel> createVenue(@RequestBody VenueRequestModel model) {
        return ResponseEntity.status(HttpStatus.CREATED).body(venueService.createVenue(model));
    }

    @PutMapping(value = "{venueId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VenueResponseModel> createVenue(@PathVariable String venueId, @RequestBody VenueRequestModel model) {
        return ResponseEntity.ok(venueService.updateVenue(venueId, model));
    }

    @DeleteMapping(value = "{venueId}")
    public ResponseEntity<Void> deleteVenue(@PathVariable String venueId) {
        venueService.deleteVenue(venueId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }
}

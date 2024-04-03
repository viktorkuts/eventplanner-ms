package com.viktorkuts.venues.logiclayer;

import com.viktorkuts.venues.datalayer.Venue;
import com.viktorkuts.venues.datalayer.VenueIdentifier;
import com.viktorkuts.venues.datalayer.VenueRepository;
import com.viktorkuts.venues.mapperlayer.VenueRequestMapper;
import com.viktorkuts.venues.mapperlayer.VenueResponseMapper;
import com.viktorkuts.venues.presentationlayer.models.VenueRequestModel;
import com.viktorkuts.venues.presentationlayer.models.VenueResponseModel;
import com.viktorkuts.venues.utils.exceptions.InUseException;
import com.viktorkuts.venues.utils.exceptions.InvalidPostalCodeException;
import com.viktorkuts.venues.utils.exceptions.InvalidTimeAllocationException;
import com.viktorkuts.venues.utils.exceptions.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class VenueServiceImpl implements VenueService {
    private VenueRepository venueRepository;
    private VenueResponseMapper venueResponseMapper;
    private VenueRequestMapper venueRequestMapper;

    public VenueServiceImpl(VenueRepository venueRepository, VenueResponseMapper venueResponseMapper, VenueRequestMapper venueRequestMapper) {
        this.venueRepository = venueRepository;
        this.venueResponseMapper = venueResponseMapper;
        this.venueRequestMapper = venueRequestMapper;
    }

    private void validatePostalCode(String postal){
        if(postal != null){
            boolean valid = false;
            valid = postal.matches("\\d{5}") || postal.matches("^[A-Za-z]\\d[A-Za-z] ?\\d[A-Za-z]\\d$");
            if(!valid){
                throw new InvalidPostalCodeException("Invalid postal code, can only be in format 12345 or A1A 1A1 (US and Canada format)");
            }
        }
    }

    private void validateTimeAlloc(LocalDateTime start, LocalDateTime end, Integer allocBlock){
        if(start != null && end != null && allocBlock != null){
            Duration diff = Duration.between(start, end);
            if((long)allocBlock > diff.toHours()){
                throw new InvalidTimeAllocationException("Invalid time allocation, max block allocation cannot be greater than the difference between available start and end schedule!");
            }
        }
    }

    @Override
    public List<VenueResponseModel> getVenues() {
        return venueResponseMapper.entitiesToModels(venueRepository.findAll());
    }

    @Override
    public VenueResponseModel getVenue(String venueId) {
        Venue foundVenue = venueRepository.findVenueByVenueIdentifier_VenueId(venueId);
        if(foundVenue == null) {
            throw new NotFoundException("Unknown venueId:" + venueId);
        }
        return venueResponseMapper.entityToModel(foundVenue);
    }

    @Override
    public VenueResponseModel createVenue(VenueRequestModel venueRequestModel) {
        Venue newVenue = venueRequestMapper.modelToEntity(venueRequestModel, new VenueIdentifier());
        String postal = newVenue.getAddress().getPostalcode();
        validatePostalCode(postal);
        validateTimeAlloc(newVenue.getAvailableStart(), newVenue.getAvailableEnd(), newVenue.getMaxBlockAllocation());
        venueRepository.save(newVenue);
        return venueResponseMapper.entityToModel(newVenue);
    }

    @Override
    public VenueResponseModel updateVenue(String venueId, VenueRequestModel venueRequestModel) {
        Venue foundVenue = venueRepository.findVenueByVenueIdentifier_VenueId(venueId);
        if(foundVenue == null) {
            throw new NotFoundException("Unknown venueId:" + venueId);
        }
        Venue updatedVenue = venueRequestMapper.modelToEntity(venueRequestModel, foundVenue.getVenueIdentifier());
        String postal = updatedVenue.getAddress().getPostalcode();
        validatePostalCode(postal);
        validateTimeAlloc(updatedVenue.getAvailableStart(), updatedVenue.getAvailableEnd(), updatedVenue.getMaxBlockAllocation());
        venueRepository.save(updatedVenue);
        return venueResponseMapper.entityToModel(updatedVenue);
    }

    @Override
    public void deleteVenue(String venueId) {
        Venue foundVenue = venueRepository.findVenueByVenueIdentifier_VenueId(venueId);
        if(foundVenue == null) {
            throw new NotFoundException("Unknown venueId:" + venueId);
        }
        try{
            venueRepository.delete(foundVenue);
        }catch (DataIntegrityViolationException e){
            throw new InUseException("Venue is in use by other resource!");
        }
    }
}

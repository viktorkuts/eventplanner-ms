package com.viktorkuts.events.utils;

import com.viktorkuts.events.datalayer.Event;
import com.viktorkuts.events.datalayer.EventIdentifier;
import com.viktorkuts.events.datalayer.EventRepository;
import com.viktorkuts.events.domainclientlayer.customers.CustomerModel;
import com.viktorkuts.events.domainclientlayer.customers.CustomerType;
import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.domainclientlayer.venues.VenueModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class DatabaseLoaderService implements CommandLineRunner {
    @Autowired
    EventRepository eventRepository;

    public void run(String... args){
        var eventIdentifier1 = new EventIdentifier();
        var customerModel1 = CustomerModel.builder()
                .customerId("6d7e2586-d3e1-4153-9666-bb17ed4ca50e")
                .firstName("Charity")
                .lastName("Anthoin")
                .customerType(CustomerType.PERFORMER)
                .build();
        var ticketModelList = new ArrayList<TicketModel>();
        var ticketModel1 = TicketModel.builder()
                .ticketId("b145e3f9-738d-4fda-a60c-c6a4c91a183c")
                .build();
        ticketModelList.add(ticketModel1);

        var venueModel1 = VenueModel.builder()
                .venueId("bcec20dd-939f-4873-8053-7102621f4344")
                .venueName("Da Venue")
                .build();

        var event1 = Event.builder()
                .eventIdentifier(eventIdentifier1)
                .performer(customerModel1)
                .tickets(ticketModelList)
                .venue(venueModel1)
                .build();

        eventRepository.save(event1);
    }
}

package com.viktorkuts.events.datalayer;


import com.viktorkuts.events.domainclientlayer.customers.CustomerModel;
import com.viktorkuts.events.domainclientlayer.tickets.TicketModel;
import com.viktorkuts.events.domainclientlayer.venues.VenueModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "events")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    private String id;

    @Indexed(unique = true)
    public EventIdentifier eventIdentifier;

    public LocalDateTime startsAt;
    public LocalDateTime endsAt;

    public CustomerModel performer;
    public List<TicketModel> tickets;
    public VenueModel venue;

    public Event(
            CustomerModel customerModel,
            List<TicketModel> ticketModels,
            VenueModel venueModel,
            LocalDateTime startsAt,
            LocalDateTime endsAt
    ){
        this.eventIdentifier = new EventIdentifier();
        this.performer = customerModel;
        this.venue = venueModel;
        this.tickets = ticketModels;
        this.startsAt = startsAt;
        this.endsAt = endsAt;
    }
}

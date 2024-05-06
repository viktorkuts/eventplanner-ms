package com.viktorkuts.events.datalayer;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findEventsByPerformer_CustomerId (String customerId);
    Event findEventByPerformer_CustomerId (String customerId);
}

package com.viktorkuts.events.datalayer;


import lombok.Getter;

import java.util.UUID;

@Getter
public class EventIdentifier {
    public String eventId;

    public EventIdentifier() {
        this.eventId = UUID.randomUUID().toString();
    }
}

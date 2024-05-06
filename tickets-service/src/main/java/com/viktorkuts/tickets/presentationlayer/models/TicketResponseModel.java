package com.viktorkuts.tickets.presentationlayer.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketResponseModel {
    private String ticketId;
    private Date purchaseTime;
    private String user;
    private Boolean validated;
}

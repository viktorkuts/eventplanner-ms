package com.viktorkuts.tickets.presentationlayer.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketRequestModel {
    private Date purchaseTime;
    private Boolean validated;
    private String user;
}

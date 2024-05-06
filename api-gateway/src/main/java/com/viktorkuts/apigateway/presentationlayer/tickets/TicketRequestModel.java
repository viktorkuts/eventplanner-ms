package com.viktorkuts.apigateway.presentationlayer.tickets;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class TicketRequestModel {
    Date purchaseTime;
    Boolean validated;
    String user;
}

package com.viktorkuts.apigateway.presentationlayer.tickets;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.util.Date;

@Data
@Value
@Builder
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class TicketResponseModel extends RepresentationModel<TicketResponseModel> {
    String ticketId;
    Date purchaseTime;
    String user;
    Boolean validated;
}

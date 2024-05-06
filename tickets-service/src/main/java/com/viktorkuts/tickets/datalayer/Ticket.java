package com.viktorkuts.tickets.datalayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Entity
@Data
@Table(name = "tickets")
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private TicketIdentifier ticketIdentifier;

    @Column(name = "purchasetime")
    private Date purchaseTime;

    @Column(name = "userid")
    private String user;

    @Column(name = "validated")
    private Boolean validated;

    public Ticket(
            @NotNull TicketIdentifier ticketIdentifier,
            @NotNull Date purchaseTime,
            @NotNull String user,
            @NotNull Boolean validated
    ){
        Objects.requireNonNull(this.ticketIdentifier = ticketIdentifier);
        Objects.requireNonNull(this.purchaseTime = purchaseTime);
        Objects.requireNonNull(this.user = user);
        Objects.requireNonNull(this.validated = validated);
    }
}

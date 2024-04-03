package com.viktorkuts.venues.datalayer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Data
@Table(name = "venues")
@NoArgsConstructor
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Embedded
    private VenueIdentifier venueIdentifier;

    @Column(name = "venuename")
    private String venueName;

    @Embedded
    private Address address;

    @Column(name = "availablestart")
    private LocalDateTime availableStart;

    @Column(name = "availableend")
    private LocalDateTime availableEnd;

    @Column(name = "maxblockallocation")
    private Integer maxBlockAllocation;

    @Column(name = "capacity")
    private Integer capacity;

    public Venue(
            @NotNull VenueIdentifier venueIdentifier,
            @NotNull String venueName,
            @NotNull Address address,
            @NotNull LocalDateTime availableStart,
            @NotNull LocalDateTime availableEnd,
            @NotNull Integer maxBlockAllocation,
            @NotNull Integer capacity
    ){
        Objects.requireNonNull(this.venueIdentifier = venueIdentifier);
        Objects.requireNonNull(this.venueName = venueName);
        Objects.requireNonNull(this.address = address);
        Objects.requireNonNull(this.availableStart = availableStart);
        Objects.requireNonNull(this.availableEnd = availableEnd);
        Objects.requireNonNull(this.maxBlockAllocation = maxBlockAllocation);
        Objects.requireNonNull(this.capacity = capacity);
    }
}

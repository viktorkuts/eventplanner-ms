package com.viktorkuts.venues.presentationlayer;

import com.viktorkuts.venues.datalayer.Address;
import com.viktorkuts.venues.datalayer.VenueRepository;
import com.viktorkuts.venues.presentationlayer.models.VenueRequestModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:venues-db"})
@Sql({"/schema-h2.sql", "/data-h2.sql"})
public class VenueControllerIntegrationTest {
    private final String BASE_URL = "/api/v1/venues";

    @Autowired
    VenueRepository venueRepository;

    @Autowired
    WebTestClient webTestClient;

    @AfterEach
    void tearDown() {
        venueRepository.deleteAll();
    }

    @Test
    public void whenVenuesExists_ReturnVenues(){
        webTestClient.get()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.length()").isEqualTo(1);
    }

    @Test
    public void whenVenueNotExist_ReturnEmptyVenue(){
        webTestClient.get()
                .uri(BASE_URL + "/thisdoesnotexist")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    public void whenVenueInvalidTime_ReturnInvalidTimeException(){
        VenueRequestModel venueRequestModel = new VenueRequestModel("test", new Address("123 Main St", "Anytown", "NY", "US", "12345"), LocalDateTime.now(), LocalDateTime.now(), 5, 1000);
        webTestClient.post()
                .uri(BASE_URL)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isBadRequest();
    }
}

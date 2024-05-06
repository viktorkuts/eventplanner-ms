package com.viktorkuts.tickets.presentationlayer;

import com.viktorkuts.tickets.datalayer.TicketRepository;
import com.viktorkuts.tickets.presentationlayer.models.TicketRequestModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Clock;
import java.time.Instant;
import java.util.Date;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {"spring.datasource.url=jdbc:h2:mem:tickets-db"})
@Sql({"/schema-h2.sql", "/data-h2.sql"})
public class TicketControllerIntegrationTest {
    private final String BASE_URL = "/api/v1/tickets";
    private final String VALID_TICKET_ID = "b145e3f9-738d-4fda-a60c-c6a4c91a183c";

    @Autowired
    TicketRepository ticketRepository;

    @Autowired
    WebTestClient webTestClient;

    @AfterEach
    void tearDown() {
        ticketRepository.deleteAll();
    }

    @Test
    public void whenTicketExists_ThenGetAllTickets(){
        webTestClient.get()
                .uri(BASE_URL + "/" + VALID_TICKET_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    public void whenTicketExists_ThenGetTicketById(){
        webTestClient.get()
                .uri(BASE_URL + "/" + VALID_TICKET_ID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.ticketId").isEqualTo(VALID_TICKET_ID);
    }

    @Test
    public void whenUpdateTicket_ThenReturnUpdatedTicket(){
        TicketRequestModel ticketRequestModel = new TicketRequestModel(Date.from(Instant.now()), true, "cus01");
        webTestClient.put()
                .uri(BASE_URL + "/" + VALID_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ticketRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.ticketId").isEqualTo(VALID_TICKET_ID);
    }

    @Test
    public void whenTicketValidatedAndUpdated_ThenReturnValidatedException(){
        TicketRequestModel ticketRequestModel = new TicketRequestModel(Date.from(Instant.now()), false, "cus01");
        webTestClient.put()
                .uri(BASE_URL + "/" + VALID_TICKET_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(ticketRequestModel)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @Test
    public void whenCreateTicket_ThenReturnCreatedTicket(){
        TicketRequestModel ticketRequestModel = new TicketRequestModel(Date.from(Instant.now()), false, "cus01");
        webTestClient.post()
                .uri(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(ticketRequestModel)
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }
}

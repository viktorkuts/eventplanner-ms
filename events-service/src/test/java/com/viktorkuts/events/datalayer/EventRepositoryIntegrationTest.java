package com.viktorkuts.events.datalayer;

import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;

@DataMongoTest()
@ActiveProfiles("test")
public class EventRepositoryIntegrationTest {

}

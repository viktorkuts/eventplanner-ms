# eventplanner-ms

There is a total of 5 microservices in this project, 2 (api-gateway and events-service) which are not yet implemented since
it was not a requirement of milestone 1.

The 3 microservices that are implemented are:
1. Customer Service
2. Venues Service
3. Tickets Service

The aggregate service is the event service which is a combination of the 3 services above. (Cannot implement since they need to communicate over network, which we have not yet done)

Here is the list of running containers:
1. customers-service -> PostgreSQL
2. venues-service -> mySQL
3. tickets-service -> mySQL
4. event-service -> PostgreSQL
5. api-gateway
6. pg_cs
7. pg_es
8. sql_ts (for customers-service)
9. sql_vs (for venues-service)
10. pgadmin 9000
11. phpadmin 5013
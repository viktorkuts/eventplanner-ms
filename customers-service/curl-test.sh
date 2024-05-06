#!/bin/bash

#GET ALL
curl -v http://localhost:8080/customer | jq

#GET
curl -v http://localhost:8080/customer/eb3fd435-7811-4880-8c0e-3e65af54e6ce | jq

#POST
curl -v -X POST http://localhost:8080/customer -H 'Content-Type: application/json' -d '{"firstName": "Viktor", "lastName": "Kuts", "customerType": "CUSTOMER", "emailAddress": "vik.kuts@hotmail.com", "address": { "streetAddress": "123 main street", "city": "Canadaland", "province": "Quebec", "country": "Canada", "postalcode": "12345" }, "phoneNumbers": [ { "type": "HOME", "number": "123" } ]
}' | jq

#PUT
curl -v -X PUT http://localhost:8080/customer/6d7e2586-d3e1-4153-9666-bb17ed4ca50e -H 'Content-Type: application/json' -d '{"firstName": "Viktor", "lastName": "Kuts", "customerType": "CUSTOMER", "emailAddress": "vik.kuts@hotmail.com", "address": { "streetAddress": "123 main street", "city": "Canadaland", "province": "Quebec", "country": "Canada", "postalcode": "12345" }, "phoneNumbers": [ { "type": "HOME", "number": "123" } ]
}' | jq

#DELETE
curl -v -X DELETE http://localhost:8080/customer/48bb3fd8-fbaf-4581-a389-01583265efbe

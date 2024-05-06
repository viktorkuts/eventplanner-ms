#!/usr/bin/env bash
#
# Sample usage:
#   ./test_all.bash start stop
#   start and stop are optional
#
#   HOST=localhost PORT=7000 ./test-em-all.bash
#
# When not in Docker
#: ${HOST=localhost}
#: ${PORT=7000}

# When in Docker
: ${HOST=localhost}
: ${PORT=8080}

#array to hold all our test data ids
allTestCustomerIds=("6d7e2586-d3e1-4153-9666-bb17ed4ca50e")
allTestSaleId=()

function assertCurl() {

  local expectedHttpCode=$1
  local curlCmd="$2 -w \"%{http_code}\""
  local result=$(eval $curlCmd)
  local httpCode="${result:(-3)}"
  RESPONSE='' && (( ${#result} > 3 )) && RESPONSE="${result%???}"

  if [ "$httpCode" = "$expectedHttpCode" ]
  then
    if [ "$httpCode" = "200" ]
    then
      echo "Test OK (HTTP Code: $httpCode)"
    else
      echo "Test OK (HTTP Code: $httpCode, $RESPONSE)"
    fi
  else
      echo  "Test FAILED, EXPECTED HTTP Code: $expectedHttpCode, GOT: $httpCode, WILL ABORT!"
      echo  "- Failing command: $curlCmd"
      echo  "- Response Body: $RESPONSE"
      exit 1
  fi
}

function assertEqual() {

  local expected=$1
  local actual=$2

  if [ "$actual" = "$expected" ]
  then
    echo "Test OK (actual value: $actual)"
  else
    echo "Test FAILED, EXPECTED VALUE: $expected, ACTUAL VALUE: $actual, WILL ABORT"
    exit 1
  fi
}

#have all the microservices come up yet?
function testUrl() {
    url=$@
    if curl $url -ks -f -o /dev/null
    then
          echo "Ok"
          return 0
    else
          echo -n "not yet"
          return 1
    fi;
}

#prepare the test data that will be passed in the curl commands for posts and puts
function setupTestdata() {

##CREATE SOME CUSTOMER TEST DATA - THIS WILL BE USED FOR THE POST REQUEST
#
body=\
'{
  "firstName":"Viktor",
  "lastName":"Kuts",
  "customerType": "CUSTOMER",
  "emailAddress":"vikkuts@gmail.com",
  "streetAddress": "99 Main Street",
  "city":"Montreal",
  "province": "Quebec",
  "country": "Canada",
  "postalCode": "H3A 1A1",
  "phoneNumbers": [
    {
      "type": "HOME",
      "number": "514-555-5555"
    },
    {
      "type": "WORK",
      "number": "514-555-5556"
    }
  ]
}'
    recreateCustomer 1 "$body"
#
#Create some sale test data

body=\
  '{
    "performerId": "cus01",
    "startsAt": "2020-01-01",
    "endsAt": "2021-01-01",
    "totalAmount": 100.00,
    "venueId": "bcec20dd-939f-4873-8053-7102621f4344",
    "totalDue": 110.00,
    "tickets": [
      {
        "ticketId": "b145e3f9-738d-4fda-a60c-c6a4c91a183c",
        "user": "cus01"
      }
    ]
  }'
    recreateSale 1 "$body" "cus01"

} #end of setupTestdata


#USING CUSTOMER TEST DATA - EXECUTE POST REQUEST
function recreateCustomer() {
    local testId=$1
    local aggregate=$2

    #create the customer and record the generated customerId
    customerId=$(curl -X POST http://$HOST:$PORT/api/v1/customers -H "Content-Type:
    application/json" --data "$aggregate" | jq '.customerId')
    allTestCustomerIds[$testId]=$customerId
    echo "Added Customer with customerId: ${allTestCustomerIds[$testId]}"
}

#USING Sale TEST DATA - EXECUTE POST REQUEST
function recreateSale() {
    local testId=$1
    local aggregate=$2
    local customerId=$3

    #create the sale and record the generated saleId
    saleId=$(curl -X POST http://$HOST:$PORT/api/v1/events -H "Content-Type:
    application/json" --data "$aggregate" | jq '.eventId')
    allTestSaleId[$testId]=$saleId
    echo "Added Event with eventId: ${allTestSaleId[$testId]}"
}

#don't start testing until all the microservices are up and running
function waitForService() {
    url=$@
    echo -n "Wait for: $url... "
    n=0
    until testUrl $url
    do
        n=$((n + 1))
        if [[ $n == 100 ]]
        then
            echo " Give up"
            exit 1
        else
            sleep 6
            echo -n ", retry #$n "
        fi
    done
}

#start of test script
set -e

echo "HOST=${HOST}"
echo "PORT=${PORT}"

if [[ $@ == *"start"* ]]
then
    echo "Restarting the test environment..."
    echo "$ docker-compose down"
    docker-compose down
    echo "$ docker-compose up -d"
    docker-compose up -d
fi
#
##try to delete an entity/aggregate that you've set up but that you don't need. This will confirm that things are working
waitForService curl -X DELETE http://$HOST:$PORT/api/v1/customers/cf834976-7ddc-40e2-9c49-84df1c83bb17

setupTestdata

#EXECUTE EXPLICIT TESTS AND VALIDATE RESPONSE
#
##verify that a get all customers works
echo -e "\nTest 1: Verify that a get all customers works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers -s"
assertEqual 10 $(echo $RESPONSE | jq ". | length")
#
#
## Verify that a normal get by id of earlier posted customer works
echo -e "\nTest 2: Verify that a normal get by id of earlier posted customer works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} '${body}' -s"
assertEqual ${allTestCustomerIds[1]} $(echo $RESPONSE | jq .customerId)
assertEqual "\"Christine\"" $(echo $RESPONSE | jq ".firstName")
#
#
## Verify that an update of an earlier posted customer works - put at api-gateway has no response body
echo -e "\nTest 3: Verify that an update of an earlier posted customer works"
body=\
'{
  "firstName":"Christine",
  "lastName":"Gerard",
  "phoneType": "CUSTOMER",
  "emailAddress":"christine@gmail.com",
  "streetAddress": "99 Main Street",
  "city":"Montreal",
  "province": "Quebec",
  "country": "Canada",
  "postalCode": "H3A 1A1",
  "phoneNumbers": [
    {
      "type": "HOME",
      "number": "514-555-5555"
    },
    {
      "type": "WORK",
      "number": "514-555-5556"
    }
  ]
}'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
#
#
## Verify that a delete of an earlier posted customer works
echo -e "\nTest 4: Verify that a delete of an earlier posted customer works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/customers/${allTestCustomerIds[1]} -s"
#
#
## Verify that a 404 (Not Found) status is returned for a non existing customerId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 5: Verify that a 404 (Not Found) error is returned for a get customer request with a non existing customerId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d7 -s"
#
#
## Verify that a 422 (Unprocessable Entity) status is returned for an invalid customerId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 6: Verify that a 422 (Unprocessable Entity) status is returned for a get customer request with an invalid customerId"
assertCurl 422 "curl http://$HOST:$PORT/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d -s"

#verify that a get all sales works
echo -e "\nTest 7: Verify that a get all sales works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/customers/cus01/purchases -s"
assertEqual 2 $(echo $RESPONSE | jq ". | length")

#verify that a get sale by id saleid and customerid works
echo -e "\nTest 8: Verify that a get sale by id saleid and customerid works"
assertCurl 200 "curl http://$HOST:$PORT/api/v1/events/performer/cus01${allTestSaleId[1]} -s"
assertEqual ${allTestSaleId[1]} $(echo $RESPONSE)


#Verify that an update of an earlier posted sale works - put at api-gateway has no response body
echo -e "\nTest 9: Verify that an update of an earlier posted sale works"

body=\
'{
    "customerId": "cus01",
    "saleDate": "2020-01-01",
    "totalAmount": 200.00,
    "totalTax": 15.00,
    "totalDiscount": 5.00,
    "totalDue": 110.00,
    "lineItems": [
      {
        "productId": "1",
        "quantity": 1,
        "unitPrice": 100.00,
        "tax": 15.00,
        "discount": 5.00,
        "total": 110.00
      }
    ]
  }'
assertCurl 200 "curl -X PUT http://$HOST:$PORT/api/v1/customers/cus01/purchases/${allTestSaleId[1]} -H \"Content-Type: application/json\" -d '${body}' -s"
assertEqual ${allTestSaleId[1]} $(echo $RESPONSE | jq .saleId)
assertEqual 200.00 $(echo $RESPONSE | jq ".totalAmount")
assertEqual 110.00 $(echo $RESPONSE | jq ".totalDue")
#
#
#TODO: Add verification of vehicle status change to sold

# Verify that a delete of an earlier posted sale works
echo -e "\nTest 10: Verify that a delete of an earlier posted sale works"
assertCurl 204 "curl -X DELETE http://$HOST:$PORT/api/v1/customers/cus01/purchases/${allTestSaleId[1]} -s"

# Verify that a 404 (Not Found) status is returned for a non existing saleId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 11: Verify that a 404 (Not Found) error is returned for a get sale request with a non existing saleId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customers/cus01/purchases/c3540a89-cb47-4c96-888e-ff96708db4d7 -s"

# Verify that a 422 (Unprocessable Entity) status is returned for an invalid saleId (c3540a89-cb47-4c96-888e-ff96708db4d)
echo -e "\nTest 12: Verify that a 422 (Unprocessable Entity) status is returned for a get sale request with an invalid saleId"
assertCurl 422 "curl http://$HOST:$PORT/api/v1/customers/cus01/purchases/c3540a89-cb47-4c96-888e-ff96708db4d -s"

# Verify that a 404 (Not Found) status is returned for a non existing customerId (c3540a89-cb47-4c96-888e-ff96708db4d7)
echo -e "\nTest 13: Verify that a 404 (Not Found) error is returned for a get sale request with a non existing customerId"
assertCurl 404 "curl http://$HOST:$PORT/api/v1/customers/c3540a89-cb47-4c96-888e-ff96708db4d7/purchases -s"


if [[ $@ == *"stop"* ]]
then
    echo "We are done, stopping the test environment..."
    echo "$ docker-compose down"
    docker-compose down
fi
# Setup

## MongoDB

docker run -d --name=mongo --net=host mongo:latest

# Test

## Create Order

~~~~
curl -X POST \
  http://localhost:8080/v1/orders \
  -H 'Content-Type: application/json' \
  -d '{
	"customerId" : "6a7dc9bd-b837-45c9-aa8d-246e71ad98df",
	"status" : "ACCEPTED",
	"value" : 782
}'
~~~~

## List Orders

~~~~
curl -X GET \
  http://localhost:8080/v1/orders \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
~~~~
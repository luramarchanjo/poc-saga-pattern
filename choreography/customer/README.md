# Setup

## Postgres

docker run -d --name=postgres --net=host postgres:latest

# Test

## Create Customer

~~~~
curl -X POST \
  http://localhost:8081/v1/customers \
  -H 'Content-Type: application/json' \
  -d '{
	"name" : "Luram Archanjo",
	"creditLimit" : 1000
}'
~~~~

## List Customers

~~~~
curl -X GET \
  http://localhost:8081/v1/customers \
  -H 'Content-Type: application/json' \
  -H 'cache-control: no-cache'
~~~~
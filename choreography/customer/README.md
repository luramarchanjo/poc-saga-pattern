# Setup

## Postgres

docker run -d --name=postgres --net=host postgres:latest

## Nats

Run the command below
~~~
docker run -d --name=nats --net=host nats:latest
~~~

# Test

### Create Customer

~~~
POST http://localhost:8081/v1/customers
Accept: application/json
Content-Type: application/json

{
  "name" : "Luram Archanjo",
  "creditLimit" : 1000
}
~~~

### List Customers

~~~
GET http://localhost:8081/v1/customers
Accept: application/json
Content-Type: application/json
~~~
# Setup

## Nats

Run the command below
~~~
docker run -d --name=nats --net=host nats:latest
~~~

# Test

### Create Order

~~~
POST http://localhost:8080/v1/orders
Accept: application/json
Content-Type: application/json

{
  "customerId" : "eecfe3c9-30e8-474e-bd20-28d99d203f7c",
  "products" : [
    {
      "id" : "3732093a-c299-4cba-a752-c24bd4024ea8",
      "value" : 20,
      "quantity" : 50
    }
  ]
}
~~~

### List Orders

~~~
GET http://localhost:8080/v1/orders
Accept: application/json
Content-Type: application/json
~~~
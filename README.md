# Exemplar Service

A fictional payment service acting as my idea of an exemplar SpringBoot microservice.

Example service is running at `http://tf-ecs-chat-188386310.eu-west-2.elb.amazonaws.com`.

e.g
```bash
$ curl -X POST -H "Content-Type: application/json" --data '{"organisationId":"MyOrg","amount":"100.00"}' tf-ecs-chat-188386310.eu-west-2.elb.amazonaws.com/payments
{"id":"381e5424-b68a-4970-9596-1c31aea3752d","organisationId":"MyOrg","amount":"100.00"}

$ curl -X POST -H "Content-Type: application/jsonelb.amazonaws.com/payments":"MyOrg","amount":"100.00"}' tf-ecs-chat-188386310.eu-west-2.e
{"id":"de5ad819-7073-45de-8bc4-0df2e4402a14","organisationId":"MyOrg","amount":"100.00"}

$ curl -X POST -H "Content-Type: application/jsonelb.amazonaws.com/payments":"MyOrg","amount":"100.00"}' tf-ecs-chat-188386310.eu-west-2.e
{"id":"53de8435-d542-4b14-9ff1-f89751f745ea","organisationId":"MyOrg","amount":"100.00"}

$ curl -X GET -H "Content-Type: application/json" tf-ecs-chat-188386310.eu-west-2.elb.amazonaws.com/payments
[
  {"id":"381e5424-b68a-4970-9596-1c31aea3752d","organisationId":"MyOrg","amount":"100.00"},
  {"id":"de5ad819-7073-45de-8bc4-0df2e4402a14","organisationId":"MyOrg","amount":"100.00"},
  {"id":"53de8435-d542-4b14-9ff1-f89751f745ea","organisationId":"MyOrg","amount":"100.00"}
]
```

# Deployment

Prerequisites:
- Install Terraform 

1. Build local image and tag with repository. e.g

    `docker build --tag janakerman/exemplar-service`

2. Push docker image to repository. This would be part of a CI pipeline after tests pass.

    `docker push janakerman/exemplar-service`
    
3. Initialize AWS Fargate cluster.

    ```
    cd deployment
    terraform init
    terraform plan -out plan.tf
    terraform apply plan.tf
    ```



# Service Template

Standard SpringBoot Project Template

# Applied technology

* [Spring Boot](https://spring.io/projects/spring-boot) – as the basic framework
* [PostgreSQL](https://www.postgresql.org/) – as a basic relational database
* [Redis](https://redis.io/) – how to cache and queue messages via pub/sub
* [testcontainers](https://testcontainers.com/) – for isolated testing with a database
* [Liquibase](https://www.liquibase.org/) – to conduct database schema migration
* [Gradle](https://gradle.org/) – as an application build system
* [Lombok](https://projectlombok.org/) – for convenient work with POJO classes
* [MapStruct](https://mapstruct.org/) – for convenient mapping between POJO classes


# Task

analytics_service should collect information about comments of a user's post/project by other users in post_service

# Code

* Usual three - layer
  architecture – Controller, [Service](https://github.com/schonpink/post_services/blob/master/src/main/java/post/service/CommentEventService.java), [Repository](src/main/java/analytics/repository)
* The Repository layer is implemented on both JdbcTemplate and JPA (Hibernate)
* Implemented simple Messaging via [Redis pub/sub](https://redis.io/docs/manual/pubsub/)
    * [Configuration](src/main/java/analytics/config/RedisConfig.java) –
      setting up [RedisTemplate](https://docs.spring.io/spring-data/redis/docs/current/api/org/springframework/data/redis/core/RedisTemplate.html) –
      a class for convenient work with Redis and Spring
    * A message about a new profile view is sent to the new Redis topic from [post_service](https://github.com/schonpink/post_services)
    * [CommentEventListener](src/main/java/analytics/listener/CommentEventListener.java) –
      listens events and saves information from these events to the database an entity. I used [AnalyticsEventMapper](src/main/java/analytics/mapper/AnalyticsEventMapper.java) for this transformation.
      
# Tests

* SpringBootTest
* MockMvc
* Testcontainers
* AssertJ
* JUnit5
* Parameterized tests
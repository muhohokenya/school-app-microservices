package org.app.users

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableKafka
class UsersApplication
fun main(args: Array<String>) {
    runApplication<UsersApplication>(*args)
}

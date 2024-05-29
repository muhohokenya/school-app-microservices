package org.app.library

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.kafka.annotation.EnableKafka

@SpringBootApplication
@EnableFeignClients
@EnableKafka
class LibraryApplication

fun main(args: Array<String>) {
    runApplication<LibraryApplication>(*args)
}

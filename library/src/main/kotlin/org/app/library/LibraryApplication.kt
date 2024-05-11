package org.app.library

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class LibraryApplication

fun main(args: Array<String>) {
    runApplication<LibraryApplication>(*args)
}

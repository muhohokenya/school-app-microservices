package org.app.courses

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CoursesApplication

fun main(args: Array<String>) {
    runApplication<CoursesApplication>(*args)
}

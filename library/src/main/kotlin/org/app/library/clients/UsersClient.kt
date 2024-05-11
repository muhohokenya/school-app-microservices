package org.app.library.clients

import org.app.library.dto.UserDto
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable


@FeignClient(
    name = "user-client",
    url =  "http://localhost:8081",
    value =  "",
    path = "api/v1/students"
)


interface UsersClient {
    @GetMapping("")
    fun  getAllUsers():MutableList<UserDto>

    @GetMapping("{admNo}")
    fun  getUserByAdmNo(@PathVariable admNo:String):UserDto

    @GetMapping("exists/{admNo}")
    fun studentExists(@PathVariable admNo: String):Boolean
}
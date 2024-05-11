package org.app.users.controllers

import org.app.users.dto.UserDto
import org.app.users.models.Students
import org.app.users.services.UserService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/students")
@CrossOrigin(origins = ["http://localhost"])
class StudentsController(
    private val userService: UserService
) {
    @GetMapping("")
    fun getUsers():MutableList<Students>{
        println("Called")
        return userService.getUsers()
    }

    @GetMapping("exists/{admNo}")
    fun studentExists(
        @PathVariable admNo: String
    ):Boolean{
        return userService.studentExistsByAdmNo(admNo)
    }

    @PostMapping("save")
    fun saveUser(@RequestBody userDto: UserDto){
        userService.saveUser(userDto)
    }

    @GetMapping("{admNo}")
    fun getUserByAdmNo(
        @PathVariable admNo:String
    ):Students{
        return userService.getUserByAdmNo(admNo)
    }




}
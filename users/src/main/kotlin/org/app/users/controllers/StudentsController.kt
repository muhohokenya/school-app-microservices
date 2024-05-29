package org.app.users.controllers

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import jakarta.validation.Valid
import org.app.users.dto.ResponseDto
import org.app.users.dto.UserDto
import org.app.users.models.Students
import org.app.users.services.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("api/v1/students")
@CrossOrigin(origins = ["http://localhost:3000"])
class StudentsController(
    private val userService: UserService
) {
    @GetMapping("")
    @CircuitBreaker(name = "userService", fallbackMethod = "getAllUsers")
    fun getUsers():MutableList<Students>{
        println("Called")
        return userService.getUsers()
    }


    fun getAllUsers(t:Throwable):MutableList<Students>{
        val students = mutableListOf<Students>()

        return students
    }

    @GetMapping("/ids/{ids}")
    fun getUsersWhereInIds(@PathVariable ids: String): MutableList<Students> {
        val idList = ids.split(",").map { it.toLong() } // Convert to list of Long
        return userService.getUsersByIds(idList)
    }

    @GetMapping("exists/{admNo}")
    fun studentExists(
        @PathVariable admNo: String
    ):Boolean{
        return userService.studentExistsByAdmNo(admNo)
    }

    @PostMapping("save")
    fun saveUser(@RequestBody @Valid userDto: UserDto): ResponseEntity<ResponseDto> {
        return try {
            userService.saveUser(userDto)
            ResponseEntity.ok(ResponseDto("Student has been saved successfully"))
        }catch (ex: MethodArgumentNotValidException){
            ResponseEntity.ok(ResponseDto(ex.message))
        }
    }

    @GetMapping("{admNo}")
    fun getUserByAdmNo(
        @PathVariable admNo:String
    ):Students{
        return userService.getUserByAdmNo(admNo)
    }

    @GetMapping("/id/{id}")
    fun getUserById(
        @PathVariable id:String
    ): Optional<Students> {
        return userService.getUserById(id)
    }




}
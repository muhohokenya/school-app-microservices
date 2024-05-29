package org.app.courses.controllers

import jakarta.validation.Valid
import org.app.courses.dto.DepartmentDto
import org.app.courses.dto.ResponseDto
import org.app.courses.models.Department
import org.app.courses.services.DepartmentService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("api/v1/departments")
@CrossOrigin("http://localhost:3000")
class DepartmentsController(
    private val departmentService: DepartmentService
){

    @GetMapping("")
    fun getAllDepartments():MutableList<Department>{
        return departmentService.getAllDepartments()
    }

    @PostMapping("save")
    fun saveDepartment(@RequestBody @Valid departmentDto: DepartmentDto): ResponseEntity<ResponseDto> {
        return try {
            departmentService.saveDepartment(departmentDto)
            ResponseEntity.ok(ResponseDto(message = "Department has been saved"))
        }catch (ex: MethodArgumentNotValidException){
            ResponseEntity.ok(ResponseDto(message = ex.message))
        }
    }

}
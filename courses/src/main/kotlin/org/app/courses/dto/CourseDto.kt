package org.app.courses.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class CourseDto(

    @field:NotEmpty(message = "Name cannot be empty")
    val name:String,


    @field:NotEmpty(message = "Department cannot be empty")
    val departmentId:String
)

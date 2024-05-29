package org.app.courses.dto

import jakarta.validation.constraints.NotEmpty

data class DepartmentDto(
    val id:Long,

    @field:NotEmpty(message = "The name cannot be empty")
    val name:String,
)
package org.app.library.dto
import jakarta.validation.constraints.NotEmpty

class BookIssueDto(
    @field:NotEmpty(message = "The admNo cannot be empty")
    val admNo:String,

    @field:NotEmpty(message = "The bookIsbn cannot sbe empty")
    val bookIsbn:String,
)
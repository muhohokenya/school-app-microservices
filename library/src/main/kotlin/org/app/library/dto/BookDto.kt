package org.app.library.dto

data class BookDto(
    val id:Long,
    val bookAuthor:String,
    val bookTitle: String,
    val bookIsbn: String,
)
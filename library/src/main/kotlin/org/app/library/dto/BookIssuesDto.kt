package org.app.library.dto

data class BookIssuesDto(
    val id: Long,
    val title: String,
    val isbn: String,
    val name: String,
    val adm_no: String,
    val returnDate: String?,
    val dateIssued: String,
)
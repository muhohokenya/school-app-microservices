package org.app.library.models

import jakarta.persistence.*
import java.text.SimpleDateFormat
import java.util.Date

private val currentDate = Date()
@Table(name = "book_issues")

@Entity
open class BookIssue(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id:Long?,

    @Column(name = "bookId", nullable = false)
    open var bookId:String,

    @Column(name = "studentId", nullable = false, unique = true)
    open var studentId:String,

    @Column(name = "dateIssued")
    open var dateIssued: Date?,

    @Column(name = "returnDate")
    open var returnDate: Date?,

    ) {

    constructor() : this(1,"","",currentDate, null)
}
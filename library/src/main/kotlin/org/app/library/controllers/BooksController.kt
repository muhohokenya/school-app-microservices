package org.app.library.controllers

import org.app.library.dto.BookDto
import org.app.library.dto.BookIssueDto
import org.app.library.dto.UserDto
import org.app.library.models.Book
import org.app.library.models.BookIssue
import org.app.library.services.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("api/v1")
class BooksController(
    private val bookService: BookService
) {

    @GetMapping("books")
    fun books():MutableList<Book>{
        return bookService.getBooks()
    }

    @PostMapping("books/save")
    fun saveBook(@RequestBody bookDto: BookDto){
         bookService.saveBook(bookDto)
    }

    @GetMapping("students")
    fun getStudents(): MutableList<UserDto> {
        return bookService.getStudents()
    }

    @GetMapping("students/{admNo}")
    fun getUserByAdmNo(
        @PathVariable admNo:String
    ):UserDto {
        return bookService.getUserByAdmNo(admNo)
    }

    private fun bookExists(bookIsbn:String):Boolean{
        return bookService.bookExistsByIsbn(bookIsbn)
    }

    private fun studentExists(admNo:String):Boolean{
        return bookService.studentExists(admNo)
    }

    @PostMapping("return")
    fun returnBook(@RequestBody bookIssueDto: BookIssueDto):ResponseEntity<String>{
        val bookExists: Boolean = bookExists(bookIssueDto.bookIsbn) // internal
        val studentExists:Boolean = studentExists(bookIssueDto.admNo) // external
        /*Check if book exists*/
        if (!bookExists) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Book with ISBN ${bookIssueDto.bookIsbn} does not exist")

            //Check if student exists
        }else if (!studentExists) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Student with ID ${bookIssueDto.admNo} does not exist")
            //Check if book is already issued to the student
        }

        bookService.returnBook(bookIssueDto)
        return ResponseEntity.ok("Book returned successfully")
    }

    @PostMapping("issue")
    fun issueBook(@RequestBody bookIssueDto: BookIssueDto): ResponseEntity<String> {

        val bookExists = bookExists(bookIssueDto.bookIsbn)
        val studentExists = studentExists(bookIssueDto.admNo)

        /*Check if book exists*/
        if (!bookExists) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Book with ISBN ${bookIssueDto.bookIsbn} does not exist")

        //Check if student exists
        }else if (!studentExists) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Student with ID ${bookIssueDto.admNo} does not exist")

        //Check if book is already issued to the student
        }

        val book = bookService.getBookByIsbn(bookIssueDto.bookIsbn)
        val student = bookService.getStudentByAdmNo(bookIssueDto.admNo)

        if (bookService
                .bookIsAllocatedTheStudent(book.id.toString(),student.id.toString())
            ){
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body("Student with ID ${bookIssueDto.admNo} has already been allocated the book ${bookIssueDto.bookIsbn}")
        }else{


            val bookIssue = BookIssue(
                id = null,
                bookId = book.id.toString(),
                studentId = student.id.toString(),
                dateIssued = Date(),
                null
            )
            bookService.issueBook(bookIssue)
            return ResponseEntity.ok("Book issued successfully")
        }
    }

    @GetMapping("issues")
    fun issues():List<Any> {
        return bookService.getBooksAndStudents()
    }
}
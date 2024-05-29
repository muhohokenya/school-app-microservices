package org.app.library.controllers

import jakarta.validation.Valid
import org.app.library.dto.*
import org.app.library.models.Book
import org.app.library.models.BookIssue
import org.app.library.services.BookService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("api/v1")
@CrossOrigin("http://localhost:3000")
@PreAuthorize("hasAnyAuthority('library_role')")
class BooksController(
    private val bookService: BookService
) {

    @GetMapping("books")
    fun books():MutableList<Book>{
        return bookService.getBooks()
    }

    @GetMapping("books/borrowed")
    fun getBooksTransactionsHistoryNew():MutableList<BookIssuesDto>{
        return bookService.getBorrowedBooks()
    }

    @GetMapping("books/transactions")
    fun getBooksTransactionsHistory():MutableList<BookTransactionHistory>{
        return bookService.getBooksTransactionsHistory()
    }

    @PostMapping("books/save")
    fun saveBook(@RequestBody @Valid bookDto: BookDto):ResponseEntity<MessageDto>{
        return try {
            bookService.saveBook(bookDto)
            ResponseEntity.ok(MessageDto("Book has been saved successfully"))
        }catch (ex: MethodArgumentNotValidException){
            ResponseEntity.ok(MessageDto("Error saving the book"))
        }
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
        val bookExists: Boolean = bookExists(bookIssueDto.bookIsbn) // internal service
        val studentExists:Boolean = studentExists(bookIssueDto.admNo) // external service
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
    fun issueBook(@RequestBody @Valid bookIssueDto: BookIssueDto): ResponseEntity<String> {

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
        val bookIsNotAvailable:Boolean = bookService.bookIsAvailable(book.id.toString())

//        println(bookService.findByBookIdAndReturnDateIsNull("2").bookId)

       if(bookIsNotAvailable){
           return ResponseEntity
               .status(HttpStatus.BAD_REQUEST)
               .body("${book.title} is not available")
       }
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
package org.app.library.services

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.app.library.clients.UsersClient
import org.app.library.dto.BookDto
import org.app.library.dto.BookIssueDto
import org.app.library.dto.BookIssuesDto
import org.app.library.dto.UserDto
import org.app.library.models.Book
import org.app.library.models.BookIssue
import org.app.library.repositories.BookIssueRepository
import org.app.library.repositories.BookRepository
import org.springframework.stereotype.Service
import java.net.ConnectException
import java.util.*
import org.app.library.dto.BookTransactionHistory
import org.springframework.kafka.annotation.KafkaListener
import com.fasterxml.jackson.module.kotlin.readValue

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val bookIssueRepository: BookIssueRepository,
    private val usersClient: UsersClient
) {
//    private val logger = LoggerFactory.getLogger(KafkaConsumerService::class.java)

    @KafkaListener(topics = ["students-topic"], groupId = "myGroup")
    fun receiveData(userJson:String){
        try {
            val objectMapper = jacksonObjectMapper()
            val student:UserDto = objectMapper.readValue(userJson)
            println(userJson)
//            println("Consumed student: ${student.name} with admNo: ${student.admNo} and course is ${student.course}")
        } catch (e: Exception) {

            println(" Error => ${e.message}")
            println("******************************************")
            println("Error deserializing message: $userJson Error => ${e.message}")
        }
    }

    fun saveBook(bookDto: BookDto) {
        val book = Book(
            id = null,
            title = bookDto.bookTitle,
            author = bookDto.bookAuthor,
            isbn = bookDto.bookIsbn,
            borrowed = false
        )
        bookRepository.save(book)
    }


    fun getBooks(): MutableList<Book> {
        return bookRepository.findAll()
    }

    fun getStudents(): MutableList<UserDto> {
        try {
            return usersClient.getAllUsers()
        } catch (e: ConnectException) {
            println("Caught Exception: ${e.message}")
            return mutableListOf()
        }
    }

    fun getUserByAdmNo(admNo: String): UserDto {
        return usersClient.getUserByAdmNo(admNo)
    }

    fun issueBook(bookIssue: BookIssue) {
        bookIssueRepository.save(bookIssue)
    }

    fun studentExists(admNo: String): Boolean {
        return usersClient.studentExists(admNo) //Client
    }

    fun bookIsAllocatedTheStudent(
        bookId: String,
        studentId: String
    ): Boolean {
        return bookIssueRepository
            .existsByBookIdAndStudentIdAndReturnDateIsNull(bookId, studentId)
    }

    fun getBookByIsbn(bookIsbn: String): Book {
        return bookRepository.getBookByIsbn(bookIsbn)
    }

    fun getStudentByAdmNo(admNo: String): UserDto {
        return usersClient.getUserByAdmNo(admNo)
    }

    fun bookExistsByIsbn(isbn: String): Boolean {
        return bookRepository.existsByIsbn(isbn)
    }

    fun bookIsAvailable(bookId: String): Boolean {
        return bookIssueRepository.existsByBookIdAndReturnDateIsNull(bookId)

    }

    fun findByBookIdAndReturnDateIsNull(string: String): BookIssue {
        return bookIssueRepository.findByBookIdAndReturnDateIsNull(string)
    }

    fun returnBook(bookIssueDto: BookIssueDto) {
        val now = Date()
        val book = bookRepository.getBookByIsbn(bookIssueDto.bookIsbn)
        val bookIssue = bookIssueRepository.findByBookIdAndReturnDateIsNull(book.id.toString())
        bookIssueRepository.updateReturnDateById(bookIssue.id.toString().toLong(), now)
    }

    fun getBooksAndStudents(): MutableList<BookIssuesDto> {
        val bookIssueDto = mutableListOf<BookIssuesDto>()
        bookIssueRepository.getBooksAndStudents().forEach { dto ->
            if (dto is Array<*>) {
                val id = dto[0].toString().toLong()
                val title = dto[1].toString()
                val isbn = dto[2].toString()
                val name = dto[3].toString()
                val adm_no = dto[4].toString()
                val returnDate = dto[5]?.toString() // Handle null return date
                val dateIssued = dto[6].toString()
                val bookIssue = BookIssuesDto(
                    id, title, isbn, name, adm_no, returnDate, dateIssued
                )
                bookIssueDto.add(bookIssue)
            }
        }

        return bookIssueDto

    }


    fun getBorrowedBooks(): MutableList<BookIssuesDto> {
        val issuedBooks = mutableListOf<BookIssuesDto>()

        val borrowedBooks = bookRepository.findBorrowedBooks()
        borrowedBooks.forEachIndexed { index, it ->
            if (it is Array<*>) {
                val studentId =  it[1].toString()
                val bookTitle =  it[2].toString()
                val isbn =       it[3].toString()
                val returnDate = it[4].toString()
                val dateIssued = it[5].toString()
                val student = usersClient.getUserById(studentId)

                val bookIssueDto = BookIssuesDto(
                    id = index.toLong() + 1,
                    title = bookTitle,
                    isbn = isbn,
                    name = student.name,
                    admNno = student.admNo,
                    returnDate = returnDate,
                    dateIssued = dateIssued
                )
                issuedBooks.add(bookIssueDto)
            }
        }
        return issuedBooks
    }

    //    @Deprecated("This has been deprecated")
    fun getBooksTransactionsHistory(): MutableList<BookTransactionHistory> {
        val transactionHistory = mutableListOf<BookTransactionHistory>()
        bookIssueRepository.getBooksTransactionsHistory().forEach { dto ->
            if (dto is Array<*>) {
                val name = dto[0].toString()
                val transaction = dto[1].toString().toInt()

                val transactionDto = BookTransactionHistory(
                    name, transaction
                )

                transactionHistory.add(transactionDto)
            }
        }
        return transactionHistory
    }

//    fun getBooksTransactionsHistoryNew(): MutableList<BookTransactionHistory> {
//        val transactionHistory = mutableListOf<BookTransactionHistory>()
//        bookIssueRepository.getBooksTransactionsHistory().forEach { dto->
//            if (dto is Array<*>){
//                val name = dto[0].toString()
//                val transaction = dto[1].toString().toInt()
//
//                val transactionDto = BookTransactionHistory(
//                    name, transaction
//                )
//
//                transactionHistory.add(transactionDto)
//            }
//        }
//        return transactionHistory
//    }


}
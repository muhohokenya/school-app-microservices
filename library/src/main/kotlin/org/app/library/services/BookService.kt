package org.app.library.services

import org.app.library.clients.UsersClient
import org.app.library.dto.BookDto
import org.app.library.dto.BookIssueDto
import org.app.library.dto.BookIssuesDto
import org.app.library.dto.UserDto
import org.app.library.models.Book
import org.app.library.models.BookIssue
import org.app.library.repositories.BookIssueRepository
import org.app.library.repositories.BookRepository
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import java.net.ConnectException
import java.util.*

@Service
class BookService(
    private val bookRepository: BookRepository,
    private val bookIssueRepository: BookIssueRepository,
    private val usersClient: UsersClient
) {

    fun saveBook(bookDto: BookDto){
        val book  =Book(
            id = null,
            title = bookDto.bookTitle,
            author = bookDto.bookAuthor,
            isbn = bookDto.bookIsbn,
        )
        bookRepository.save(book)
    }


    fun getBooks():MutableList<Book>{
        return  bookRepository.findAll()
    }

    fun getStudents(): MutableList<UserDto> {
       try {
           return usersClient.getAllUsers()
       }catch (e:ConnectException){
           println("Caught Exception: ${e.message}")
           return mutableListOf()
       }
    }

    fun getUserByAdmNo(admNo:String):UserDto {
     return usersClient.getUserByAdmNo(admNo)
    }

    fun issueBook(bookIssue: BookIssue){
        bookIssueRepository.save(bookIssue)
    }

    fun studentExists(admNo: String):Boolean{
        return usersClient.studentExists(admNo) //Client
    }

    fun bookIsAllocatedTheStudent(
        bookId:String,
        studentId: String):Boolean{
        return bookIssueRepository
            .existsByBookIdAndStudentIdAndReturnDateIsNull(bookId,studentId)
    }

    fun getBookByIsbn(bookIsbn: String): Book {
        return bookRepository.getBookByIsbn(bookIsbn)
    }

    fun getStudentByAdmNo(admNo: String): UserDto {
        return usersClient.getUserByAdmNo(admNo)
    }

    fun bookExistsByIsbn(isbn:String):Boolean{
        return bookRepository.existsByIsbn(isbn)
    }


    fun returnBook(bookIssueDto: BookIssueDto){
        val now = Date()
        val book = bookRepository.getBookByIsbn(bookIssueDto.bookIsbn)
        val bookIssue = bookIssueRepository.findByBookIdAndReturnDateIsNull(book.id.toString())
        bookIssueRepository.updateReturnDateById(bookIssue.id.toString().toLong(),now)
    }

    fun getBooksAndStudents():MutableList<BookIssuesDto> {
        val bookIssueDto = mutableListOf<BookIssuesDto>()
        bookIssueRepository.getBooksAndStudents().forEach{ dto->
            if (dto is Array<*>) {
                val id = dto[0].toString().toLong()
                val title = dto[1].toString()
                val isbn = dto[2].toString()
                val name = dto[3].toString()
                val adm_no = dto[4].toString()
                val returnDate = dto[5]?.toString() // Handle null return date
                val dateIssued = dto[6].toString()
                val bookIssue = BookIssuesDto(
                    id, title,isbn, name,adm_no, returnDate, dateIssued)
                bookIssueDto.add(bookIssue)
            }
        }

        return bookIssueDto

    }


}
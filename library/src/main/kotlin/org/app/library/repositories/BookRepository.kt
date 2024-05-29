package org.app.library.repositories
import org.app.library.models.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface BookRepository:JpaRepository<Book,Long>{

    fun getBookByIsbn(isbn:String):Book

    fun existsByIsbn(isbn: String):Boolean

    @Query("""
        SELECT
            book_issues.book_id,student_id,
            books.title,
            books.isbn,
            book_issues.return_date,
            book_issues.date_issued
        FROM
            book_issues
        JOIN books on CAST(books.id AS BIGINT) = CAST(book_issues.book_id AS BIGINT)
        WHERE return_date IS NULL;
        """
        , nativeQuery = true)
    fun findBorrowedBooks(): MutableList<Any>
}
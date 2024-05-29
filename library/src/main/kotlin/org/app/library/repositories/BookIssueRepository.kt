package org.app.library.repositories

import org.app.library.dto.BookTransactionHistory
import org.app.library.models.BookIssue
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional
import java.util.Date

interface BookIssueRepository : JpaRepository<BookIssue, Long> {

    fun existsByBookIdAndStudentIdAndReturnDateIsNull(bookId: String, studentId: String): Boolean


    @Modifying
    @Transactional
    @Query(value = "UPDATE book_issues SET return_date = :returnDate WHERE id = :issueId", nativeQuery = true)
    fun updateReturnDateById(issueId: Long, returnDate: Date)

    @Query(
        """
        SELECT
            book_issues.id,
            books.title,
            books.isbn,
            students.name,
            students.adm_no,
            book_issues.return_date,
            book_issues.date_issued
        FROM
            book_issues
        JOIN
            students ON CAST(book_issues.student_id AS BIGINT) = students.id
        JOIN
            books ON books.id = CAST(book_issues.book_id AS BIGINT)
    """, nativeQuery = true
    )
    fun getBooksAndStudents(): MutableList<Any>

    fun findByBookIdAndReturnDateIsNull(bookId: String): BookIssue

    fun existsByBookIdAndReturnDateIsNull(bookId: String): Boolean


    @Query(
        """
        SELECT
            books.title,
            COUNT(book_issues.id) AS issue_count
        FROM 
        book_issues
                 JOIN books 
                 ON  
                 CAST(book_issues.book_id AS BIGINT) = CAST(books.id AS BIGINT)
        GROUP BY books.id, books.title;
    """, nativeQuery = true
    )
    fun getBooksTransactionsHistory(): MutableList<Any>

}
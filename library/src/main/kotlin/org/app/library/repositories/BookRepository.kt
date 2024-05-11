package org.app.library.repositories
import org.app.library.models.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.transaction.annotation.Transactional

interface BookRepository:JpaRepository<Book,Long>{

    fun getBookByIsbn(isbn:String):Book

    fun existsByIsbn(isbn: String):Boolean
}
package com.library.cruddemo.dao;

import com.library.cruddemo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT b FROM book b WHERE b.title = :title")
    Optional<Book> findByTitle(@Param("title") String title);


    @Query("SELECT b FROM book b JOIN b.authors a WHERE b.id = :bookId AND a.id = :authorId")
    Optional<Book> findBookByAuthorId(@Param("bookId") int bookId, @Param("authorId") int authorId);
}

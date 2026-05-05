package com.library.cruddemo.dao;

import com.library.cruddemo.entity.Lib;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LibRepository extends JpaRepository<Lib, Integer> {

    @Query("SELECT l FROM lib l WHERE l.name = :name")
    Optional<Lib> findByName(@Param("name") String name);

    @Query("SELECT l FROM lib l JOIN l.books b WHERE b.id = :bookId AND l.id = :libId")
    Optional<Lib> findLibByBookId(@Param("bookId") int bookId, @Param("libId") int libId);
}

package com.alura.literalura.repository;

import com.alura.literalura.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    Book findByTitleIgnoreCase(String title);

    @Query("SELECT b FROM Book b JOIN FETCH b.authors a WHERE a.name = :name")
    List<Book> buscarPorAutor(String name);

    @Query("SELECT b FROM Book b JOIN FETCH b.authors")
    List<Book> todoConAutores();

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.authors WHERE b.languages = :idioma")
    List<Book> findByLenguajes(String idioma);
}

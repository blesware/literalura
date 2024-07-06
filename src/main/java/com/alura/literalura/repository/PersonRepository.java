package com.alura.literalura.repository;

import com.alura.literalura.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByName(String name);

    @Query("SELECT a FROM Person a LEFT JOIN FETCH a.books")
    List<Person> bookAuthor();

    @Query("SELECT a FROM Person a LEFT JOIN FETCH a.books WHERE a.birth_year <= :year AND (a.death_year = 0 OR a.death_year >= :year)")
    List<Person> yearAuthors(@Param("year") int year);
}

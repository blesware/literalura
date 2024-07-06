package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "authors")
public class Person {

    //Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(name = "birth_year")
    private Integer birth_year;

    @Column(name = "death_year")
    private Integer death_year;

    @ManyToMany(mappedBy = "authors", fetch = FetchType.EAGER)
    private List<Book> books;

    //Constructores
    public Person() { }

    public Person(DataPerson dataAuthor) {

        this.name = dataAuthor.name();
        this.birth_year = dataAuthor.birth_year().get();
        this.death_year = dataAuthor.death_year().get();
    }

    //Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirth_year() {
        return birth_year;
    }

    public void setBirth_year(Integer birth_year) {
        this.birth_year = birth_year;
    }

    public Integer getDeath_year() {
        return death_year;
    }

    public void setDeath_year(Integer death_year) {
        this.death_year = death_year;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    //Metodo toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Autor: ").append(name).append("\n");
        sb.append("Fecha de nacimiento: ").append(birth_year).append("\n");
        sb.append("Fecha de fallecimiento: ").append(death_year).append("\n");
        sb.append("Libros:\n");

        if (books != null) {
            for (Book book : books) {
                sb.append(" - ").append(book.getTitle()).append("\n");
            }
        }

        return sb.toString();
    }
}

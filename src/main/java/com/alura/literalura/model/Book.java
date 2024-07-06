package com.alura.literalura.model;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "books")
public class Book {

    //Atributos

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "autor_id")
    )
    private List<Person> authors;

    @Column(name = "lenguajes")
    private String languages;

    @Column(name = "download_count")
    private Double download_count;

    //Constructores
    public Book() { }

    public Book(DataBook dataBook, List<Person> authors) {

        this.title = dataBook.title();
        this.authors = authors;
        this.languages = String.join(",", dataBook.languages());
        this.download_count = dataBook.download_count();
    }

    //Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Person> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Person> authors) {
        this.authors = authors;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Double getDownload_count() {
        return download_count;
    }

    public void setDownload_count(Double download_count) {
        this.download_count = download_count;
    }

    //Metodo toString
    @Override
    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("----- LIBRO -----\n");
        sb.append("Titulo: ").append(title).append("\n");

        if (authors != null) {
            sb.append("Autores:\n");
            for (Person author : authors) {
                sb.append(" - ").append(author.getName()).append("\n");
            }
        }

        sb.append("Lenguajes: ").append(languages).append("\n");
        sb.append("Numero de descargas: ").append(download_count).append("\n");
        sb.append("-----------------");
        return sb.toString();
    }
}

package com.alura.literalura.view;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.BookRepository;
import com.alura.literalura.repository.PersonRepository;
import com.alura.literalura.service.ConsumeAPI;
import com.alura.literalura.service.ConvertData;
import jakarta.transaction.Transactional;
import jdk.swing.interop.SwingInterOpUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {

    private final String URL_BASE = "https://gutendex.com/books/";

    //Metodo para desplegar el menu de opciones
    public void deployMenu(BookRepository bookRepository, PersonRepository personRepository) {

        String option = "";

        while(!option.trim().equals("0")) {

            System.out.println("---------------------------");
            System.out.println("Elija la opcion a traves de su numero: ");
            System.out.println("1- Buscar libro por titulo");
            System.out.println("2- Listar libros registrados");
            System.out.println("3- Listar autores registrados");
            System.out.println("4- Listar autores vivos en un determinado año");
            System.out.println("5- Listar libros por idioma");
            System.out.println("0- Salir");
            System.out.print("Digite la opcion: ");
            option = input();
            System.out.println("");

            switch (option.trim()) {

                case "1":
                    searchBookByTitle(bookRepository, personRepository);
                    break;

                case "2":
                    listRegisteredBooks(bookRepository);
                    break;

                case "3":
                    listRegisteredAuthors(personRepository);
                    break;

                case "4":
                    listLivingAuthors(personRepository);
                    break;

                case "5":
                    listBooksByLanguage(bookRepository);
                    break;

                case "0":
                    System.out.println("");
                    System.out.println("Cerrando Aplicacion...");
                    System.out.println("");
                    break;

                default:
                    System.out.println("");
                    System.out.println("Opcion no disponible, vuelva a interntarlo");
                    System.out.println("");
            }
        }
    }

    //Metodo para buscar libros por el titulo
    private void searchBookByTitle(BookRepository bookRepository, PersonRepository personRepository) {

        System.out.println("");
        System.out.print("Escribe el nombre del libro que deseas buscar: ");
        String nombreLibro = input();
        System.out.println("");

        System.out.println("");

        String json = ConsumeAPI.obtenerDatos(URL_BASE + "?search=" + nombreLibro.replace(" ", "+"));
        Data datos = ConvertData.getData(json, Data.class);

        Optional<DataBook> libroBuscado = datos.books().stream()
                .filter(b -> b.title().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {

            DataBook book = libroBuscado.get();
            saveBookDB(bookRepository, personRepository, book);
            System.out.println("Libro encontrado");
            System.out.println(book);

        } else {

            System.out.println("Libro no encontrado");
        }

        System.out.println("");
    }

    //Metodo para listar los libros registrados
    private void listRegisteredBooks(BookRepository bookRepository) {

        System.out.println("");

        List<Book> books = bookRepository.todoConAutores();

        if(books.isEmpty()) {

            System.out.println("No hay libros registrados");

        } else {

            System.out.println("----- LIBROS REGISTRADOS ------------");

            for (Book book : books) {
                System.out.println(book);
            }

            System.out.println("----- FIN DE LIBROS REGISTRADOS -----");
        }

        System.out.println("");
    }

    //Metodo para listar los autores registrados
    @Transactional
    private void listRegisteredAuthors(PersonRepository personRepository) {

        System.out.println("");

        List<Person> authors = personRepository.bookAuthor();

        if(authors.isEmpty()) {

            System.out.println("No hay autores registrados");

        } else {

            System.out.println("----- AUTORES REGISTRADOS -----");

            for (Person author : authors) {
                System.out.println(author);
            }

            System.out.println("----- FIN DE AUTORES ----------");
        }

        System.out.println("");
    }

    //Metodo para listar los autores vivos
    private void listLivingAuthors(PersonRepository personRepository) {

        System.out.println("");
        System.out.print("Escriba el año por el cual desea filtrar los autores registrados: ");
        String ano = input();
        System.out.println("");

        try {

            int year = Integer.valueOf(ano);

            List<Person> authors = personRepository.yearAuthors(year);

            if(authors.isEmpty()) {

                System.out.println("No hay autores vivos registrados del año: " + ano);

            } else {

                System.out.println("----- AUTORES VIVOS DEL " + ano + " -----");

                for (Person author : authors) {
                    System.out.println(author);
                }

                System.out.println("----- FIN DE AUTORES VIVOS ----------");
            }

        } catch (Exception e){

            System.out.println("El campo del año, no es correcto, ERROR: " + e);
        }

        System.out.println("");
    }

    //Metodo para listar libros por el idioma
    private void listBooksByLanguage(BookRepository bookRepository) {

        String opt = "";

        while(!opt.trim().equals("0")) {

            System.out.println("");
            System.out.println("Idiomas disponibles");
            System.out.println("1- es | Español");
            System.out.println("2- en | Ingles");
            System.out.println("3- fr | Frances");
            System.out.println("4- pt | Portugues");
            System.out.println("0- Volver atras");
            System.out.print("Escriba el idioma que desea filtrar: ");
            opt = input();
            System.out.println("");

            switch (opt) {

                case "1":
                    searchBookLanguage(bookRepository, Languages.ES);
                    break;

                case "2":
                    searchBookLanguage(bookRepository, Languages.EN);
                    break;

                case "3":
                    searchBookLanguage(bookRepository, Languages.FR);
                    break;

                case "4":
                    searchBookLanguage(bookRepository, Languages.PT);
                    break;

                case "0":
                    //Saliendo del menu
                    break;

                default:
                    System.out.println("Opcion invalida, vuelva a intentar");
            }
        }

        System.out.println("");
    }

    //Metodo para buscar libros segun el idioma
    @Transactional
    private void searchBookLanguage(BookRepository bookRepository, Languages languages) {

        String codeLanguage = languages.getCode();

        List<Book> books = bookRepository.findByLenguajes(codeLanguage);

        if(books.isEmpty()) {

            System.out.println("No se encontraron libros registrados en el idioma seleccionado");

        } else {

            System.out.println("----- LIBROS '" + codeLanguage + "' ---------");
            for (Book book : books) {
                Hibernate.initialize(book.getAuthors());
                System.out.println(book);
            }
            System.out.println("---- FIN LIBROS '" + codeLanguage + "' -----");
        }

    }

    //Metodo para guardar libros en la base de datos
    private void saveBookDB(BookRepository bookRepository, PersonRepository personRepository, DataBook dataBook) {

        Book book = bookRepository.findByTitleIgnoreCase(dataBook.title());

        if(book != null) {

            System.out.println("No se puede registrar un libro, ya existe en la BD");

        } else {

            List<Person> authors = dataBook.authors().stream()
                    .map(dataAuthor -> {
                        Optional<Person> author = personRepository.findByName(dataAuthor.name());
                        return author.orElseGet(() -> personRepository.save(new Person(dataAuthor)));
                    }).collect(Collectors.toList());

            Book newBook = new Book(dataBook, authors);
            bookRepository.save(newBook);

            System.out.println("Libro guardado exitosamente");
        }
    }

    //Metodo para leer por teclado
    private String input() {

        Scanner input = new Scanner(System.in);
        return  input.nextLine();
    }
}

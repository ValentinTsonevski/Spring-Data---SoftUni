package com.example.bookshopsystem;


import com.example.bookshopsystem.model.entity.AgeRestriction;
import com.example.bookshopsystem.model.entity.Book;
import com.example.bookshopsystem.model.entity.EditionType;
import com.example.bookshopsystem.service.AuthorService;
import com.example.bookshopsystem.service.BookService;
import com.example.bookshopsystem.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    private final Scanner scanner = new Scanner(System.in);

    public CommandLineRunnerImpl(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {

    }




    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

//    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
//        bookService
//                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
//                .forEach(System.out::println);
//    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}

package com.example.bookshopsystem.service.impl;

import com.example.bookshopsystem.model.entity.*;
import com.example.bookshopsystem.repository.BookRepository;
import com.example.bookshopsystem.service.AuthorService;
import com.example.bookshopsystem.service.BookService;

import com.example.bookshopsystem.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }


//
//    @Override
//    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
//        return bookRepository
//                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
//                .stream()
//                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
//                        book.getAuthor().getLastName()))
//                .distinct()
//                .collect(Collectors.toList());
//    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }


    @Override
    public List<Book> findAllByAgeRestriction(String restriction) {
        final AgeRestriction ageRestriction = AgeRestriction.valueOf(restriction.toUpperCase());

        List<Book> booksList = this.bookRepository.findAllByAgeRestriction(ageRestriction).orElseThrow(NoSuchElementException::new);

        booksList.stream().map(Book::getTitle).forEach(System.out::println);

        return booksList;
    }

    @Override
    public List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies) {

        List<Book> booksList = this.bookRepository.findAllByEditionTypeAndCopiesLessThan(editionType,copies).orElseThrow(NoSuchElementException::new);

        booksList.stream().map(Book::getTitle).forEach(System.out::println);

        return booksList;
    }

    @Override
    public List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high) {

        List<Book> bookList = this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(low,high).orElseThrow(NoSuchElementException::new);

        bookList.stream().map(Book::toString).forEach(System.out::println);

        return bookList;
    }

    @Override
    public List<Book> findAllByReleaseDateNot(LocalDate year) {
        List<Book> bookList = this.bookRepository.findAllByReleaseDateNot(year).orElseThrow(NoSuchElementException::new);

        bookList.stream().map(Book::getTitle).forEach(System.out::println);
        return bookList;
    }

    @Override
    public List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore) {

        List<Book> booksList = this.bookRepository.findAllByReleaseDateBefore(releaseDateBefore);

        booksList.stream().map(Book::toString).forEach(System.out::println);

        return booksList;
    }

    @Override
    public List<Book> findAllByTitleContaining(String sequence) {

        List<Book> booksList = this.bookRepository.findAllByTitleContaining(sequence).orElseThrow(NoSuchElementException::new);

        booksList.stream().map(Book::getTitle).forEach(System.out::println);

        return booksList;
    }

    @Override
    public List<Book> findAllByAuthorLastNameStartsWith(String sequence) {

        List<Book> booksList = this.bookRepository.findAllByAuthorLastNameStartsWith(sequence).orElseThrow(NoSuchElementException::new);

        booksList.stream().map(Book::printTitleAndAuthorNames).forEach(System.out::println);

        return booksList;
    }

    @Override
    public Integer findBooksCountByTitleLongerThan(Integer length) {
        Integer count = this.bookRepository.findBooksCountByTitleLongerThan(length).orElseThrow(NoSuchElementException::new);
        System.out.println(count);
        return count;
    }

    @Override
    public Integer findAllCountByAuthorOrderByCopiesDesc(String firstName, String lastName) {
        Integer copiesCount = bookRepository.findAllCountByAuthorOrderByCopiesDesc(firstName,lastName).orElseThrow(NoSuchElementException::new);
        System.out.println(copiesCount);
        return copiesCount;
    }

    @Override
    public String findAllByTitle(String title) {
        String theBook = bookRepository.findAllByTitle(title).map(Book::printTitleEditionTypeAgeRestrictionPrice).orElseThrow(NoSuchElementException::new);
        System.out.println(theBook);
        return theBook;
    }


    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}

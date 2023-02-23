package com.example.bookshopsystem.service;

import com.example.bookshopsystem.model.entity.AgeRestriction;
import com.example.bookshopsystem.model.entity.Book;
import com.example.bookshopsystem.model.entity.EditionType;

import javax.print.attribute.standard.Copies;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    //Optional<List<String>> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<Book> findAllByAgeRestriction(String restriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType editionType,Integer copies);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low, BigDecimal high);

    List<Book> findAllByReleaseDateNot(LocalDate year);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByTitleContaining(String sequence);

    List<Book> findAllByAuthorLastNameStartsWith(String sequence);

    Integer findBooksCountByTitleLongerThan(Integer length);

    Integer findAllCountByAuthorOrderByCopiesDesc(String firstName,String lastName);

    String findAllByTitle(String title);
}

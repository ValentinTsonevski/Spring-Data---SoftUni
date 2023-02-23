package com.example.bookshopsystem.repository;

import com.example.bookshopsystem.model.entity.AgeRestriction;
import com.example.bookshopsystem.model.entity.Book;
import com.example.bookshopsystem.model.entity.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.print.attribute.standard.Copies;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    Optional<List<Book>> findAllByAgeRestriction(AgeRestriction ageRestriction);

    Optional<List<Book>> findAllByEditionTypeAndCopiesLessThan(EditionType editionType, Integer copies);

    Optional<List<Book>> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal low,BigDecimal high);

    Optional<List<Book>> findAllByReleaseDateNot(LocalDate year);

    Optional<List<Book>> findAllByTitleContaining(String sequence);

    Optional<List<Book>> findAllByAuthorLastNameStartsWith(String sequence);

    @Query("Select count(b.id) from Book as b where length( b.title) > :length")
    Optional<Integer> findBooksCountByTitleLongerThan(Integer length);

    @Query("select sum(b.copies)  from Book as b where b.author.firstName = :firstName AND  b.author.lastName = :lastName order by count(b.copies)desc ")
    Optional<Integer> findAllCountByAuthorOrderByCopiesDesc(String firstName,String lastName);

    Optional<Book> findAllByTitle(String title);

}

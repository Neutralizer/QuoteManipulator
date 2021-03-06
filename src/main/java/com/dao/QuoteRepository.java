package com.dao;

import com.model.Quote;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface QuoteRepository extends PagingAndSortingRepository<Quote,Integer> {

    List<Quote> findByAuthor(String author);

    List<Quote> findBySource(String source);

    List<Quote> findByRating(int rating);

    List<Quote> findByAuthorAndRating(String author, int rating);

    List<Quote> findByAuthorAndSourceAndRating(String author, String source, Integer rating);

    List<Quote> findByAuthorAndSource(String author, String source);

    List<Quote> findBySourceAndRating(String source, Integer rating);
}

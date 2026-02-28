package com.example.day1.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.day1.entity.Books;

@Repository
public interface BooksRepository extends JpaRepository<Books, Integer> {
    List<Books> findByTitleContainingIgnoreCase(String title);
    Optional<Books> findByIsbn(String isbn);
}
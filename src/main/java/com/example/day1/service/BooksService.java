package com.example.day1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.day1.entity.Books;
import com.example.day1.repository.BooksRepository;

@Service
public class BooksService {

    @Autowired
    BooksRepository booksRepo;

    public List<Books> getAllBooks(){
        return booksRepo.findAll();
    }



    public List<Books> searchBooks(String query){
        if(query != null && !query.trim().isEmpty()){
            return booksRepo.findByTitleContainingIgnoreCase(query);
        }return booksRepo.findAll();
    }

    public String addBook(Books book) {
    Optional<Books> existingBook = booksRepo.findByIsbn(book.getIsbn());
    
    // Check: If ISBN exists AND belongs to a DIFFERENT ID, it's a real duplicate error
    if (existingBook.isPresent() && !existingBook.get().getId().equals(book.getId())) {
        return "Error: ISBN " + book.getIsbn() + " is already taken!";
    }

    booksRepo.save(book);
    return (book.getId() == null) ? "Book added successfully!" : "Book updated successfully!";
}

    public String deleteBook(Integer id){

        booksRepo.deleteById(id);
        return "book deleted";

    }


}
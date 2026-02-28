package com.example.day1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.day1.entity.Books;
import com.example.day1.service.BooksService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@Controller
public class BooksController {

    @Autowired
    BooksService booksService;
    
   @GetMapping("/allBooks")
   @ResponseBody
   public List<Books> getAllBooks() {
       return booksService.getAllBooks();
   }

   @GetMapping("/viewAllBooks")
   public String booksPage(Model model) {
       List<Books> list = booksService.getAllBooks();
       model.addAttribute("books", list);
       return "book-list";
       
   }

   @GetMapping("/library")
   public String viewLibraryPage(@RequestParam(name = "keyword", required = false) String keyword, Model model) {
       List<Books> list = booksService.searchBooks(keyword);
       model.addAttribute("books", list);
       model.addAttribute("keyword", keyword);
       return "book-list";
   }

   @PostMapping("/addBook")
public String addBook(@ModelAttribute Books book, RedirectAttributes redirectAttributes) {
    // 1. Capture the message from the service
    String message = booksService.addBook(book);
    
    // 2. Pass it to the UI safely
    if (message.contains("Error")) {
        redirectAttributes.addFlashAttribute("error", message);
    } else {
        redirectAttributes.addFlashAttribute("success", message);
    }
    
    // 3. ALWAYS redirect back to the library page
    return "redirect:/library"; 
}


   @GetMapping("/deleteBook/{id}")
   public String deleteBook(@PathVariable Integer id, RedirectAttributes redirectAttributes){
        booksService.deleteBook(id);
        redirectAttributes.addFlashAttribute("success", "book deleted successfully");
        return "redirect:/library";
    
   }

   @PostMapping("/updateBook")
public String updateBook(@ModelAttribute Books book, RedirectAttributes redirectAttributes) {
    booksService.addBook(book); // save() works for both adding and updating in JPA
    redirectAttributes.addFlashAttribute("success", "Book updated successfully!");
    return "redirect:/library";
}


   
   
   
   
   
    
    
}

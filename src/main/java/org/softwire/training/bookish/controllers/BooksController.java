package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.Books;
import org.softwire.training.bookish.models.page.BooksPageModel;
import org.softwire.training.bookish.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping ("/books")
public class BooksController {
    private final BookService bookService;

    @Autowired
    public BooksController (BookService bookService) {this.bookService = bookService;}

    @RequestMapping("")
    ModelAndView books (){

        List<Books> allBooks = bookService.getAllBooks();

        BooksPageModel booksPageModel = new BooksPageModel();
        booksPageModel.setBooks(allBooks);

        return new ModelAndView("books", "model", booksPageModel);

    }

}

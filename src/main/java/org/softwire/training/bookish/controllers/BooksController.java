package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.Books;
import org.softwire.training.bookish.models.database.Members;
import org.softwire.training.bookish.models.page.books.BooksPageModel;
import org.softwire.training.bookish.models.page.books.EditBookPageModel;
import org.softwire.training.bookish.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

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

        return new ModelAndView("books/books", "model", booksPageModel);

    }

    @RequestMapping("/books-edit/{id}")
    ModelAndView editBooks(@PathVariable("id") Integer bookId) {

        Optional<Books> book = bookService.getSingleBook(bookId);

        if (book.isPresent()) {
            EditBookPageModel editBookPageModel = new EditBookPageModel();
            editBookPageModel.setBook(book.get());
            return new ModelAndView("books/books-edit", "model", editBookPageModel);
        } else {
            return books();
        }
    }

    @RequestMapping ("/edit-book/edited")
    RedirectView handleEditForm(@ModelAttribute Books books)   {
        bookService.updateBook(books);
        return new RedirectView("/books");
    }
    @RequestMapping("/books-delete/{id}")
    RedirectView deleteBook(@PathVariable("id") Integer bookId) {

        bookService.deleteBook(bookId);

        return new RedirectView("/books");
    }
    @RequestMapping("/books-add")
    RedirectView addBook(@ModelAttribute Books book) {

        bookService.addBook(book);

        return new RedirectView("/books");
    }
}

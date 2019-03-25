package org.softwire.training.bookish.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping ("/books")
public class BooksController {

    @RequestMapping("")
    ModelAndView books (){
        return new ModelAndView("books");
    }

}

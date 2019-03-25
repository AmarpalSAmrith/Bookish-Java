package org.softwire.training.bookish.models.page;

import org.softwire.training.bookish.models.database.Books;

public class EditBookPageModel {

    Books book;

    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }
}

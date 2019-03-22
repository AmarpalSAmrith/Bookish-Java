package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Books;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService extends DatabaseService {
    public List<Books> getAllBooks() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM books")
                        .mapToBean(Books.class)
                        .list()
        );
    }

    public void addBook(Books books) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO books (title, author, isbn, genre, age_rating, number_of_copies) " +
                        "VALUES (:title, :author, :isbn, :genre, :ageRating, :numberOfCopies)")
                        .bind("title", books.getTitle())
                        .bind("author", books.getAuthor())
                        .bind("isbn", books.getIsbn())
                        .bind("genre", books.getGenre())
                        .bind("ageRating", books.getAgeRating())
                        .bind("numberOfCopies", books.getNumberOfCopies())
                        .execute()
        );
    }

    public void deleteBook(int bookId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("DELETE FROM books WHERE id = :id")
                        .bind("id", bookId)
                        .execute()
        );
    }

    public void updateBook(Books books, int bookId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE books SET title = :title, author = :author, isbn = :isbn, genre = :genre, age_rating = :ageRating, number_of_copies = :numberOfCopies " +
                                         "WHERE id = :id")
                        .bind("id", bookId)
                        .bind("title", books.getTitle())
                        .bind("author", books.getAuthor())
                        .bind("isbn", books.getIsbn())
                        .bind("genre", books.getGenre())
                        .bind("ageRating", books.getAgeRating())
                        .bind("numberOfCopies", books.getNumberOfCopies())
                        .execute()
        );
    }
}
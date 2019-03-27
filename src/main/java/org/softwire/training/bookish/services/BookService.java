package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.Books;
import org.softwire.training.bookish.models.database.Members;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService extends DatabaseService {
    public List<Books> getAllBooks() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT books.*, books.number_of_copies - SUM(IF(check_in_out_history.returned = 0, 1, 0)) AS available_copies " +
                        "FROM books " +
                        "LEFT JOIN check_in_out_history ON books.id = check_in_out_history.book_id " +
                        "WHERE check_in_out_history.applicable = 1 AND books.applicable = 1 " +
                        "GROUP BY books.id")
                        .mapToBean(Books.class)
                        .list()
        );
    }

    public Optional<Books> getSingleBook(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM books WHERE id = :id AND applicable = 1")
                        .bind("id",id)
                        .mapToBean(Books.class)
                        .findFirst()
        );
    }

    public void addBook(Books books) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO books (title, author, isbn, genre, age_rating, number_of_copies, applicable) " +
                        "VALUES (:title, :author, :isbn, :genre, :ageRating, :numberOfCopies, 1)")
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
                handle.createUpdate("UPDATE books SET applicable = 0 WHERE id=:id")
                        .bind("id", bookId)
                        .execute()
        );
    }

    public void updateBook(Books books) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE books SET title = :title, author = :author, isbn = :isbn, genre = :genre, age_rating = :ageRating, number_of_copies = :numberOfCopies " +
                                         "WHERE id = :id")
                        .bind("id", books.getId())
                        .bind("title", books.getTitle())
                        .bind("author", books.getAuthor())
                        .bind("isbn", books.getIsbn())
                        .bind("genre", books.getGenre())
                        .bind("ageRating", books.getAgeRating())
                        .bind("numberOfCopies", books.getNumberOfCopies())
                        .execute()
        );
    }
    public List<Books> getBooksThatMatch(String search) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM books WHERE " +
                        "title LIKE CONCAT('%', :search '%') OR " +
                        "author LIKE CONCAT('%', :search, '%') OR " +
                        "isbn LIKE CONCAT('%', :search, '%') OR " +
                        "genre LIKE CONCAT('%', :search, '%') OR " +
                        "age_rating LIKE CONCAT('%', :search, '%')")

                        .bind("search", search)
                        .mapToBean(Books.class)
                        .list()
        );
    }
}
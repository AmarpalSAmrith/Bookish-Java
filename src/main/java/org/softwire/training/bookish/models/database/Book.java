package org.softwire.training.bookish.models.database;

public class Book {

    String id;
    String title;
    String author;
    String isbn;
    String genre;
    int ageRating;
    int numberOfCopies;

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getGenre() {
        return genre;
    }

    public int getAgeRating() {
        return ageRating;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setAgeRating(int ageRating) {
        this.ageRating = ageRating;
    }

    public void setNumberOfCopies(int numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }
}

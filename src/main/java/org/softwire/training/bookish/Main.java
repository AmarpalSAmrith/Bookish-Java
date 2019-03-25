package org.softwire.training.bookish;

import org.jdbi.v3.core.Jdbi;
import org.softwire.training.bookish.models.database.Books;

import java.sql.*;
import java.util.List;


public class Main {

    public static void main(String[] args) throws SQLException {
        String hostname = "localhost";
        String database = "bookish";
        String user = "root";
        String password = "password";
        String connectionString = "jdbc:mysql://" + hostname + "/" + database + "?user=" + user + "&password=" + password + "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=GMT&useSSL=false";

        jdbcMethod(connectionString);
        jdbiMethod(connectionString);
    }

    private static void jdbcMethod(String connectionString) throws SQLException {
        System.out.println("JDBC method...");

        // TODO: print out the details of all the books (using JDBC)
        // See this page for details: https://docs.oracle.com/javase/tutorial/jdbc/basics/processingsqlstatements.html


        Connection connection = DriverManager.getConnection(connectionString);


        String query = "SELECT * " +
                "FROM bookish.books " +
                "ORDER BY books.title ASC";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            String title = rs.getString("title");
            String author = rs.getString("author");
            String isbn = rs.getString("isbn");
            String genre = rs.getString("genre");
            int ageRating = rs.getInt("age_rating");
            int numberOfCopies = rs.getInt("number_of_copies");
            System.out.println(title + "\t" + author +
                    "\t" + isbn + "\t" + genre +
                    "\t" + ageRating +
                    "\t" + numberOfCopies);
        }


    }

    private static void jdbiMethod(String connectionString) {
        System.out.println("\nJDBI method...");

        // TODO: print out the details of all the books (using JDBI)
        // See this page for details: http://jdbi.org
        // Use the "Book" class that we've created for you (in the models.database folder)

        Jdbi jdbi = Jdbi.create(connectionString);

        String query = "SELECT * FROM bookish.books ORDER BY books.title ASC";
        List<Books> books = jdbi.withHandle(handle ->
                handle.createQuery(query)
                        .mapToBean(Books.class)
                        .list()
        );

        for (Books book : books) {
            System.out.println(book.getTitle());
        }
    }
}

package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.CheckInOutHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckInOutHistoryService extends DatabaseService {

    public List<CheckInOutHistory> getAllCheckInOutHistory() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT check_in_out_history.*, " +
                        "books.title AS book_title, books.author AS book_author, books.genre AS book_genre,  " +
                        "members.first_name AS members_first_name, members.middle_name AS members_middle_name, " +
                        "members.surname AS members_surname, members.birth_date AS members_birth_date, members.post_code AS members_post_code " +
                        "FROM check_in_out_history " +
                        "JOIN books ON check_in_out_history.book_id = books.id " +
                        "JOIN members ON check_in_out_history.member_id = members.id " +
                        "WHERE check_in_out_history.applicable = 1")
                        .mapToBean(CheckInOutHistory.class)
                        .list()
        );
    }

    public Optional<CheckInOutHistory> getSingleRecord(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT check_in_out_history.*, " +
                        "books.title AS book_title, books.author AS book_author, books.genre AS book_genre,  " +
                        "members.first_name AS members_first_name, members.middle_name AS members_middle_name, " +
                        "members.surname AS members_surname, members.birth_date AS members_birth_date, members.post_code AS members_post_code " +
                        "FROM check_in_out_history " +
                        "JOIN books ON check_in_out_history.book_id = books.id " +
                        "JOIN members ON check_in_out_history.member_id = members.id " +
                        "WHERE check_in_out_history.applicable = 1 AND check_in_out_history.id = :id")
                        .bind("id", id)
                        .mapToBean(CheckInOutHistory.class)
                        .findFirst()
        );
    }

    public void addRecord(CheckInOutHistory record) {
        jdbi.useHandle(handle ->
                handle.createUpdate("INSERT INTO check_in_out_history (memberId, bookId, checkOutDate, daysUntilDueBack, " +
                                         "returned, returnCondition, applicable) " +
                                         "VALUES (:memberId, :bookId, :checkOutDate, :daysUntilDueBack, :returned, :returnCondition, 1)")
                        .bind("memberId", record.getMember().getId())
                        .bind("bookId", record.getBook().getId())
                        .bind("checkOutDate", record.getCheckOutDate())
                        .bind("daysUntilDueBack", record.getDaysUntilDueBack())
                        .bind("returned", record.isReturned())
                        .bind("returnCondition", record.getReturnCondition())
                        .execute()
        );
    }

    public void deleteRecord(int recordId) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE check_in_out_history SET applicable = 0 WHERE id = :id")
                        .bind("id", recordId)
                        .execute()
        );
    }

    public void updateRecord(CheckInOutHistory record) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE check_in_out_history SET memberId = :memberId, bookId = :bookId, checkOutDate = :checkOutDate, " +
                                         "daysUntilDueBack = :daysUntilDueBack, returned = :returned, returnCondition = :returnCondition " +
                                         "WHERE id = :id")
                        .bind("memberId", record.getMember().getId())
                        .bind("bookId", record.getBook().getId())
                        .bind("checkOutDate", record.getCheckOutDate())
                        .bind("daysUntilDueBack", record.getDaysUntilDueBack())
                        .bind("returned", record.isReturned())
                        .bind("returnCondition", record.getReturnCondition())
                        .execute()
        );
    }
    public List<CheckInOutHistory> getRecordsForMember(int memberid) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT check_in_out_history.id AS 'Check out ID', " +
                        "CONCAT(members.first_name, ' ', middle_name, ' ', surname) AS 'Name', " +
                        "floor(datediff(CURDATE(),birth_date) / 365) AS 'Age', " +
                        "title AS 'Book Title', DATE_FORMAT(check_out_date,'%d/%m/%Y') AS 'Date Checked out', " +
                        "DATE_FORMAT(DATE_ADD(check_out_date, INTERVAL days_until_due_back DAY),'%d/%m/%Y') AS 'Date Due Back', " +
                            "IF(returned = 1, 'Returned', " +
                            "IF(DATE_ADD(check_out_date, INTERVAL days_until_due_back DAY) = CURDATE(),'Due Today'," +
                            "IF(returned = 0 AND DATE_ADD(check_out_date, INTERVAL days_until_due_back DAY) > CURDATE(), 'Not Due Yet', 'OverDue')))  AS 'Book Status' " +
                        "FROM check_in_out_history " +
                            "LEFT JOIN books ON books.id = check_in_out_history.book_id " +
                            "LEFT JOIN members ON members.id = check_in_out_history.member_id " +
                        "WHERE check_in_out_history.applicable = 1 AND member_id = :id")
                        .bind("id", memberid)
                        .mapToBean(CheckInOutHistory.class)
                        .list()
        );
    }
}
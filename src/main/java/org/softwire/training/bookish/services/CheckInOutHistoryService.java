package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.CheckInOutHistory;
import org.softwire.training.bookish.models.database.Members;
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
                handle.createQuery("SELECT check_in_out_history.*, " +
                        "books.title AS book_title, books.author AS book_author, books.genre AS book_genre,  " +
                        "members.first_name AS members_first_name, members.middle_name AS members_middle_name, " +
                        "members.surname AS members_surname, members.birth_date AS members_birth_date, members.post_code AS members_post_code " +
                        "FROM check_in_out_history " +
                        "JOIN books ON check_in_out_history.book_id = books.id " +
                        "JOIN members ON check_in_out_history.member_id = members.id " +
                        "WHERE check_in_out_history.applicable = 1 AND members.id = :id AND check_in_out_history.returned = 0")
                        .bind("id", memberid)
                        .mapToBean(CheckInOutHistory.class)
                        .list()
        );
    }
    public List<CheckInOutHistory> getRecordsThatMatch(String search) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM check_in_out_history " +
                        "JOIN books ON check_in_out_history.book_id = books.id " +
                        "JOIN members ON check_in_out_history.member_id = members.id " +
                        "WHERE members.first_name LIKE CONCAT('%', :search '%') OR " +
                        "members.middle_name LIKE CONCAT('%', :search, '%') OR " +
                        "members.surname LIKE CONCAT('%', :search, '%') OR " +
                        "books.title LIKE CONCAT('%', :search, '%') OR " +
                        "books.author LIKE CONCAT('%', :search, '%')")
                        .bind("search", search)
                        .mapToBean(CheckInOutHistory.class)
                        .list()
        );
    }
    public void checkInRecord(CheckInOutHistory record) {
        jdbi.useHandle(handle ->
                handle.createUpdate("UPDATE check_in_out_history SET returned = 1, return_condition = :returnCondition " +
                        "WHERE id = :id")
                        .bind("id", record.getId())
                        .bind("returnCondition", record.getReturnCondition())
                        .execute()
        );
    }
}
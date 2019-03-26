package org.softwire.training.bookish.services;

import org.softwire.training.bookish.models.database.CheckInOutHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheckInOutHistoryService extends DatabaseService {

    public List<CheckInOutHistory> getAllCheckInOutHistory() {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM check_in_out_history WHERE applicable = 1")
                        .mapToBean(CheckInOutHistory.class)
                        .list()
        );
    }

    public Optional<CheckInOutHistory> getSingleRecord(int id) {
        return jdbi.withHandle(handle ->
                handle.createQuery("SELECT * FROM check_in_out_history WHERE id = :id AND applicable = 1")
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
                        .bind("memberId", record.getMemberId())
                        .bind("bookId", record.getBookId())
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
                        .bind("memberId", record.getMemberId())
                        .bind("bookId", record.getBookId())
                        .bind("checkOutDate", record.getCheckOutDate())
                        .bind("daysUntilDueBack", record.getDaysUntilDueBack())
                        .bind("returned", record.isReturned())
                        .bind("returnCondition", record.getReturnCondition())
                        .execute()
        );
    }
}
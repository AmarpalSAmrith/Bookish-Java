package org.softwire.training.bookish.models.database;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CheckInOutHistory {
    int id;
    int memberId;
    int bookId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate checkOutDate;
    int daysUntilDueBack;
    boolean returned;
    String returnCondition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getDaysUntilDueBack() {
        return daysUntilDueBack;
    }

    public void setDaysUntilDueBack(int daysUntilDueBack) {
        this.daysUntilDueBack = daysUntilDueBack;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public String getReturnCondition() {
        return returnCondition;
    }

    public void setReturnCondition(String returnCondition) {
        this.returnCondition = returnCondition;
    }
}

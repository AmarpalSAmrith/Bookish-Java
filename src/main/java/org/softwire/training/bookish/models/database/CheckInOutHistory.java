package org.softwire.training.bookish.models.database;

import org.jdbi.v3.core.mapper.Nested;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class CheckInOutHistory {

    int id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate checkOutDate;
    int daysUntilDueBack;
    boolean returned;
    String returnCondition;
    boolean applicable;
    Books book;
    Members member;

    public boolean isApplicable() {
        return applicable;
    }

    public void setApplicable(boolean applicable) {
        this.applicable = applicable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Nested("book")
    public Books getBook() {
        return book;
    }

    public void setBook(Books book) {
        this.book = book;
    }

    @Nested("members")
    public Members getMember() {
        return member;
    }

    public void setMember(Members member) {
        this.member = member;
    }
}

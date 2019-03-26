package org.softwire.training.bookish.models.page;

import org.softwire.training.bookish.models.database.CheckInOutHistory;

import java.util.List;

public class CheckInOutHistoryPageModel {

    private List <CheckInOutHistory> records;

    public List<CheckInOutHistory> getRecords() { return records; }

    public void setRecords(List<CheckInOutHistory> records) { this.records = records; }
}

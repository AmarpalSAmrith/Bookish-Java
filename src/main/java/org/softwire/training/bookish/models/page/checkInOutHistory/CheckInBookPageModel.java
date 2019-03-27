package org.softwire.training.bookish.models.page.checkInOutHistory;

import org.softwire.training.bookish.models.database.CheckInOutHistory;

public class CheckInBookPageModel {
    CheckInOutHistory record;

    public CheckInOutHistory getRecord() {
        return record;
    }

    public void setRecord(CheckInOutHistory record) {
        this.record = record;
    }
}

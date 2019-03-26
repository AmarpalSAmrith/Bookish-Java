package org.softwire.training.bookish.models.page.checkInOutHistory;

import org.softwire.training.bookish.models.database.CheckInOutHistory;

import java.util.List;

public class MemberCheckInOutHistoryPageModel {

    private List<CheckInOutHistory> memberRecords;

    public List<CheckInOutHistory> getMemberRecords() {
        return memberRecords;
    }

    public void setMemberRecords(List<CheckInOutHistory> memberRecords) {
        this.memberRecords = memberRecords;
    }
}

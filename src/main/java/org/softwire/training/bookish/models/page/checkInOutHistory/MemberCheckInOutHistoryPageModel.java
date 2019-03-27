package org.softwire.training.bookish.models.page.checkInOutHistory;

import org.softwire.training.bookish.models.database.CheckInOutHistory;
import org.softwire.training.bookish.models.database.Members;

import java.util.List;

public class MemberCheckInOutHistoryPageModel {

    private Members member;
    private List<CheckInOutHistory> memberRecords;

    public List<CheckInOutHistory> getMemberRecords() {
        return memberRecords;
    }
    public void setMemberRecords(List<CheckInOutHistory> memberRecords) {
        this.memberRecords = memberRecords;
    }

    public Members getMember() {
        return member;
    }
    public void setMember(Members member) {
        this.member = member;
    }
}

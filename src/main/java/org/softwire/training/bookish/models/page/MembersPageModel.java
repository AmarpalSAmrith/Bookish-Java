package org.softwire.training.bookish.models.page;

import org.softwire.training.bookish.models.database.Members;

import java.util.List;

public class MembersPageModel {

    private List <Members> members;

    public List<Members> getMembers() { return members; }

    public void setMembers(List<Members> members) { this.members = members; }
}

package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.CheckInOutHistory;
import org.softwire.training.bookish.models.database.Members;
import org.softwire.training.bookish.models.page.checkInOutHistory.CheckInBookPageModel;
import org.softwire.training.bookish.models.page.checkInOutHistory.CheckInOutHistoryPageModel;
import org.softwire.training.bookish.models.page.checkInOutHistory.EditCheckInOutHistoryPageModel;
import org.softwire.training.bookish.models.page.checkInOutHistory.MemberCheckInOutHistoryPageModel;
import org.softwire.training.bookish.services.CheckInOutHistoryService;
import org.softwire.training.bookish.services.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/check-in-out-history")
public class CheckInOutHistoryController {

    private final CheckInOutHistoryService checkInOutHistoryService;
    private final MembersService membersService;

    @Autowired
    public CheckInOutHistoryController(CheckInOutHistoryService checkInOutHistoryService, MembersService membersService) {
        this.checkInOutHistoryService = checkInOutHistoryService;
        this.membersService = membersService;
    }

    @RequestMapping("")
    ModelAndView records (){

        List<CheckInOutHistory> allRecords = checkInOutHistoryService.getAllCheckInOutHistory();

        CheckInOutHistoryPageModel checkInOutHistoryPageModel = new CheckInOutHistoryPageModel();
        checkInOutHistoryPageModel.setRecords(allRecords);

        return new ModelAndView("checkInOutHistory/records","model",checkInOutHistoryPageModel);
    }

    @RequestMapping("/records-edit/{id}")
    ModelAndView editRecords(@PathVariable("id") Integer recordId){

        Optional<CheckInOutHistory> record = checkInOutHistoryService.getSingleRecord(recordId);

        if (record.isPresent()) {
            EditCheckInOutHistoryPageModel editCheckInOutHistoryPageModel = new EditCheckInOutHistoryPageModel();
            editCheckInOutHistoryPageModel.setRecord(record.get());
            return new ModelAndView("records-edit", "model", editCheckInOutHistoryPageModel);
        } else {
            return records();
        }
    }
    @RequestMapping ("/record-edit/edited")
    RedirectView handleEditForm(@ModelAttribute CheckInOutHistory records)   {
        checkInOutHistoryService.updateRecord(records);
        return new RedirectView("/check-in-out-history");
    }
    @RequestMapping("/record-delete/{id}")
    RedirectView deleteTechnology(@PathVariable("id") Integer recordId) {

        checkInOutHistoryService.deleteRecord(recordId);

        return new RedirectView("/check-in-out-history");
    }
    @RequestMapping("/record-add")
    ModelAndView addRecord(){
        return new ModelAndView("checkInOutHistory/records");
    }
    @RequestMapping("/record-add/added")
    RedirectView addRecord(@ModelAttribute CheckInOutHistory record) {

        checkInOutHistoryService.addRecord(record);

        return new RedirectView("/check-in-out-history");
    }
    @RequestMapping("/record-view/{id}")
    ModelAndView viewRecords(@PathVariable("id") Integer memberId){

        Optional<Members> member = membersService.getSingleMembers(memberId);
        if (member.isPresent()) {
            List<CheckInOutHistory> allRecordsForMember = checkInOutHistoryService.getRecordsForMember(memberId);

            MemberCheckInOutHistoryPageModel memberCheckInOutHistoryPageModel = new MemberCheckInOutHistoryPageModel();
            memberCheckInOutHistoryPageModel.setMemberRecords(allRecordsForMember);
            memberCheckInOutHistoryPageModel.setMember(member.get());

            return new ModelAndView("checkInOutHistory/checkInOut", "model", memberCheckInOutHistoryPageModel);
        } else {
            return records();
        }
    }

    @RequestMapping("/check-in/{id}")
    ModelAndView returnRecord(@PathVariable("id") Integer id) {
        CheckInOutHistory record = new CheckInOutHistory();
        record.setId(id);
        CheckInBookPageModel model = new CheckInBookPageModel();
        model.setRecord(record);
        return new ModelAndView("checkInOutHistory/records-check-in", "model", model);
    }

    @RequestMapping("/check-in/checkedin")
    RedirectView checkInRecords (@ModelAttribute CheckInOutHistory record){
        checkInOutHistoryService.checkInRecord(record);
        return new RedirectView("/check-in-out-history");
    }
}

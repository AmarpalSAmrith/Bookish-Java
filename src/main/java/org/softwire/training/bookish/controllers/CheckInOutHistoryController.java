package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.CheckInOutHistory;
import org.softwire.training.bookish.services.CheckInOutHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/check-in-out-history")
public class CheckInOutHistoryController {
    private final CheckInOutHistoryService checkInOutHistoryService;

    @Autowired
    public CheckInOutHistoryController (CheckInOutHistoryService checkInOutHistoryService) {this.checkInOutHistoryService = checkInOutHistoryService;}

    @RequestMapping("")
    ModelAndView records (){

        List<CheckInOutHistory> allRecords = checkInOutHistoryService.getAllCheckInOutHistory();

        
    }
}

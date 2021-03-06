package org.softwire.training.bookish.controllers;

import org.softwire.training.bookish.models.database.Members;
import org.softwire.training.bookish.models.database.Technology;
import org.softwire.training.bookish.models.page.members.EditMemberPageModel;
import org.softwire.training.bookish.models.page.members.MembersPageModel;
import org.softwire.training.bookish.services.MembersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/members")
public class MembersController {

    private final MembersService membersService;

    @Autowired
    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

    @RequestMapping("")
    ModelAndView members(@RequestParam(value = "search", required = false) String search) {

        List<Members> allMembers;
        if (search == null) {
            allMembers = membersService.getAllMembers();
        } else {
            allMembers = membersService.getMembersThatMatch(search);
        }

        MembersPageModel membersPageModel = new MembersPageModel();
        membersPageModel.setMembers(allMembers);

        return new ModelAndView("members/members", "model", membersPageModel);
    }
    @RequestMapping("/members-edit/{id}")
    ModelAndView editMembers(@PathVariable("id") Integer memberId) {

        Optional<Members> member = membersService.getSingleMembers(memberId);

        if (member.isPresent()) {
            EditMemberPageModel editMemberPageModel = new EditMemberPageModel();
            editMemberPageModel.setMember(member.get());
            return new ModelAndView("members/members-edit", "model", editMemberPageModel);
        } else {
            return members(null);
        }
    }

    @RequestMapping ("/edit-member/edited")
    RedirectView handleEditForm(@ModelAttribute Members members)   {
        membersService.updateMember(members);
        return new RedirectView("/members");
    }
    @RequestMapping("/members-delete/{id}")
    RedirectView deleteMember(@PathVariable("id") Integer memberId) {

        membersService.deleteMember(memberId);

        return new RedirectView("/members");
    }
    @RequestMapping("/members-add")
    ModelAndView addMember() {
        return new ModelAndView("members/members-add");

    }
    @RequestMapping("/members-add/added")
    RedirectView addMember(@ModelAttribute Members member) {

        membersService.addMember(member);

        return new RedirectView("/members");
    }
}

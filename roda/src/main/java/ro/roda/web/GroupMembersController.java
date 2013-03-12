package ro.roda.web;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ro.roda.domain.GroupMembers;

@RequestMapping("/groupmemberses")
@Controller
@RooWebScaffold(path = "groupmemberses", formBackingObject = GroupMembers.class)
public class GroupMembersController {
}

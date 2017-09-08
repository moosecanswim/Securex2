package byAJ.Securex.controllers;

import byAJ.Securex.models.Uzer;
import byAJ.Securex.models.Role;
import byAJ.Securex.repositories.RoleRepository;
import byAJ.Securex.repositories.UzerRepository;
import byAJ.Securex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UzerRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    private UserService userService;


    @RequestMapping("/listusers")
    public String listAllUsers(Model toSend) {
        toSend.addAttribute("listUsers", userRepository.findAll());
        return "user/listusers";
    }

    @RequestMapping("/toggleadmin/{id}")
    public String toggleAdminStatus(@PathVariable("id") long id) {
        Uzer tempUser = userRepository.findOne(id);
        Role adminRole = roleRepository.findByRole("ADMIN");

        if (!tempUser.getRoles().contains(adminRole)) {
            userService.upgradeUserToAdmin(tempUser);
        } else {
            userService.downgradeAdminToUser(tempUser);
        }
        return "redirect:/user/listusers";
    }

    @RequestMapping("/togglestatus/{id}")
    public String toggleEnableStatus(@PathVariable("id") long id) {
        Uzer tempUser = userRepository.findOne(id);
        if (tempUser.getEnabled()) {  //if it is enabled set it fo disabled
            userService.archiveUser(tempUser);
        } else {
            userService.reinstateUser(tempUser);
        }
        return "redirect:/user/listusers";
    }

    @GetMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, Model toSend) {
        Uzer tempUser = userRepository.findOne(id);
        toSend.addAttribute("aUser", tempUser);
        return "user/updateuserform";
    }

    @PostMapping("/processupdate")
    public String processUpdatedUser(@Valid Uzer user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/updateuserform";
        } else {
            userService.updateUser(user);
            return "redirect:/user/listusers";
        }
    }
}

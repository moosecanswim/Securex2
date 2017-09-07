package byAJ.Securex.controllers;

import byAJ.Securex.models.Uzer;
import byAJ.Securex.models.Role;
import byAJ.Securex.repositories.RoleRepository;
import byAJ.Securex.repositories.UzerRepository;
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

    @GetMapping("/register")
    public String registerNewUser(Model toSend){
        toSend.addAttribute("aUser", new Uzer());
        return "registrationform";
    }
    @PostMapping("/register")
    public String processNewUser(@Valid Uzer user, BindingResult result){
        if(result.hasErrors()){
            return "registrationform";
        }else {
            System.out.println("adding new user " + user.toString());
            user.addRole(roleRepository.findByRole("USER"));
            userRepository.save(user);
            return "redirect:/";
        }
    }
    @RequestMapping("/listusers")
    public String listAllUsers(Model toSend){
        toSend.addAttribute("listUsers",userRepository.findAll());
        return "listusers";
    }
    @RequestMapping("/toggleadmin/{id}")
    public String toggleAdminStatus(@PathVariable("id") long id){
        Uzer tempUser= userRepository.findOne(id);
        Role adminRole=roleRepository.findByRole("ADMIN");
        if(!tempUser.getRoles().contains(adminRole)){
            tempUser.addRole(adminRole);
            userRepository.save(tempUser);
        }else{
            tempUser.removeRole(adminRole);
            userRepository.save(tempUser);
        }
        return "redirect:/user/listusers";
    }
    @RequestMapping("/togglestatus/{id}")
    public String toggleEnableStatus(@PathVariable("id") long id) {
        Uzer tempUser= userRepository.findOne(id);
        if(tempUser.getEnabled()){  //if it is enabled set it fo disabled
            tempUser.setEnabled(false);
            userRepository.save(tempUser);
        }else{
            tempUser.setEnabled(true);
            userRepository.save(tempUser);
        }
        return "redirect:/user/listusers";
    }
}

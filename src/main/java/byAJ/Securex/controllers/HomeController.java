package byAJ.Securex.controllers;

import byAJ.Securex.models.Role;
import byAJ.Securex.models.Uzer;
import byAJ.Securex.repositories.RoleRepository;
import byAJ.Securex.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserService userService;

    @RequestMapping("/")
    public String index(){
        if(roleRepository.count()<1){
            addDefaults();
        }
        return "index";
    }

    @RequestMapping("/login")
    public String login(){
        if(roleRepository.count()<1){
            addDefaults();
        }
        return "login";
    }

    @GetMapping("/register")
    public String registerNewUser(Model toSend) {
        toSend.addAttribute("aUser", new Uzer());
        return "user/registrationform";
    }

    @PostMapping("/register")
    public String processNewUser(@Valid Uzer user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/registrationform";
        } else {
            userService.create(user);
            return "redirect:/";
        }
    }


    public void addDefaults(){
        Role role1 = new Role();
        role1.setId(1);
        role1.setRole("USER");

        Role role2 = new Role();
        role2.setId(2);
        role2.setRole("ADMIN");

        roleRepository.save(role1);
        roleRepository.save(role2);

    }

}

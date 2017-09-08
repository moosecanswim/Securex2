package byAJ.Securex.controllers;

import byAJ.Securex.models.Role;
import byAJ.Securex.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class HomeController {

    @Autowired
    RoleRepository roleRepository;

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

package byAJ.Securex.service;

import byAJ.Securex.models.Uzer;
import byAJ.Securex.repositories.RoleRepository;
import byAJ.Securex.repositories.UzerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {
    @Autowired
    UzerRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    public UserService(UzerRepository userRepository){
        this.userRepository=userRepository;
    }
    public Uzer create(Uzer user) {
        Uzer existingUser = userRepository.findByUsername(user.getUsername());

        if (existingUser != null) {
            throw new RuntimeException("Record already exists!");
        }
        System.out.println("UserService: adding new user " + user.toString());
        user.addRole(roleRepository.findByRole("USER"));
        return userRepository.save(user);
    }

    public void saveUser(Uzer user){
        user.setRoles(Arrays.asList(roleRepository.findByRole("USER")));
        user.setEnabled(true);
        userRepository.save(user);
    }
    public void saveAdmin(Uzer user){
        user.setRoles(Arrays.asList(roleRepository.findByRole("ADMIN")));
        user.setEnabled(true);
        userRepository.save(user);
    }
    public void upgradeUserToAdmin(Uzer user){
        if(!user.getRoles().contains(roleRepository.findByRole("ADMIN"))){
            user.addRole(roleRepository.findByRole("ADMIN"));
            System.out.println("added Admin role to user");
            userRepository.save(user);
        }else {
            System.out.println("did not add ADMIN role to user (already asigned)");
        }

    }
    public void downgradeAdminToUser(Uzer user){
        if(user.getRoles().contains(roleRepository.findByRole("ADMIN"))){
            user.removeRole(roleRepository.findByRole("ADMIN"));
            System.out.println("removed ADMIN role from user (1/2)");
            if(!user.getRoles().contains(roleRepository.findByRole("USER"))){
                user.addRole(roleRepository.findByRole("USER"));
                System.out.println("added USER role from user (2/2)");
            }else{
                System.out.println("USER role is already asigned to user (2/2)");
            }
            userRepository.save(user);
        }
    }
    public void archiveUser(Uzer user){
        user.setEnabled(false);
        userRepository.save(user);
    }
    public void reinstateUser(Uzer user){
        user.setEnabled(true);
        userRepository.save(user);
    }
    public void updateUser(Uzer user){
        Uzer existingUser= userRepository.findOne(user.getId());

        if(existingUser==null){
            System.out.println("User does not exist to modify");
        }

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setUsername(user.getUsername());

        System.out.println("updated user ("+existingUser.getId()+" ) "+existingUser.getUsername());
        userRepository.save(existingUser);
    }
}

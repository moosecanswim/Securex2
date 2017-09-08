package byAJ.Securex.service;

import byAJ.Securex.models.Role;
import byAJ.Securex.models.Uzer;
import byAJ.Securex.repositories.UzerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Transactional
@Service
public class SSUserDetailsService implements UserDetailsService{

    private UzerRepository userRepository;

    public SSUserDetailsService(UzerRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username){
        try{
            Uzer tempUser=userRepository.findByUsername(username);
            if(tempUser==null){
                System.out.println("SSUserDetailService: user [" + username + "] not found with provided username");
                return null;
            }
            return new org.springframework.security.core.userdetails.User(tempUser.getUsername(),tempUser.getPassword(),getAuthorities(tempUser));
        }
        catch(Exception e){
            System.out.println("exception! " +e.toString());

            throw new UsernameNotFoundException("SSUserDetailService:User not found");
        }
    }
    private Set<GrantedAuthority> getAuthorities(Uzer user){
        Set<GrantedAuthority> authorities=new HashSet<GrantedAuthority>();

        for(Role role:user.getRoles()){
            GrantedAuthority grantedAuthority= new SimpleGrantedAuthority(role.getRole());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }
}

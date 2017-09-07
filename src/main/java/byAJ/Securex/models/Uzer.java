package byAJ.Securex.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Uzer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String name;
    private String username;
    private Boolean enabled;
    private String password;
    @ManyToMany(fetch=FetchType.EAGER)
    private Collection<Role> roles;

    public Uzer(){
        setRoles(new HashSet<Role>());
        setEnabled(true);
    }
    public void addRole(Role role){
        this.roles.add(role);
    }
    public void removeRole(Role r){
        roles.remove(r);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String toString() {
        return "Uzer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}

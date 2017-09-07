package byAJ.Securex.models;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String role;
    @ManyToMany(mappedBy="roles",fetch= FetchType.EAGER)
    private Collection<Uzer> users;

    public Role(){
        setUsers(new HashSet<Uzer>());
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Collection<Uzer> getUsers() {
        return users;
    }

    public void setUsers(Collection<Uzer> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role:" + role+"("+id+")";
    }
}

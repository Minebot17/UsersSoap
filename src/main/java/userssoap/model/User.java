package userssoap.model;

import javax.persistence.*;
import javax.xml.bind.annotation.*;
import java.util.Collection;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "user", propOrder = {
        "login",
        "name",
        "password"
})
@Entity(name = "users")
public class User {

    @Id
    @XmlElement(required = true)
    private String login;

    @Column
    @XmlElement(required = true)
    private String name;

    @Column
    @XmlElement(required = true)
    private String password;

    @XmlTransient
    @ManyToMany
    @JoinTable(
            name = "users_to_roles",
            joinColumns = @JoinColumn(name = "user_login", referencedColumnName = "login"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Collection<Role> roles;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }
}

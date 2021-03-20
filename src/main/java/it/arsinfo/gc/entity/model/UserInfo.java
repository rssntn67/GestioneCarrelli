package it.arsinfo.gc.entity.model;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;


@Entity
@Table(uniqueConstraints={
        @UniqueConstraint(columnNames = {"username"})
})
public class UserInfo extends EntityBase {

    public static String[] getRoleNames() {
        return Arrays.stream(Role.values()).map(Enum::name).toArray(String[]::new);
    }

    public enum Role {
        ADMIN,
        USER,
        LOCKED,
    }

    @Column(nullable=false)
    private String passwordHash;

    @Column(unique=true, nullable=false)
    private String username;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private Role role;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable=false)
    private Date data = new Date();

    public UserInfo() {
    }

    public UserInfo(String name, String password, Role role) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(password);
        Objects.requireNonNull(role);

        this.username = name;
        this.passwordHash = password;
        this.role = role;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordHash = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "username='" + username + '\'' +
                ", role=" + role +
                '}';
    }
}

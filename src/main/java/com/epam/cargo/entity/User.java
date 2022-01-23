package com.epam.cargo.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @Column(name="name", length = 32)
    @Getter
    @Setter
    private String name;

    @Column(name="surname", length = 32)
    @Getter
    @Setter
    private String surname;

    @Column(name="login", unique = true, nullable = false, length = 32)
    @Getter
    @Setter
    private String login;

    @Column(name="password", nullable = false, length = 256)
    @Setter
    private String password;

    @Column(name="phone", length = 13)
    @Getter
    @Setter
    private String phone;

    @Column(name="email")
    @Getter
    @Setter
    private String email;

    @Column(name="cash")
    @Getter
    @Setter
    private BigDecimal cash;

    @OneToOne
    @JoinColumn(name="addressId")
    @Getter
    @Setter
    private Address address;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name="user_id"))
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Set<Role> roles;

    public User() { }

    public User(String name, String login, String password) {
        this.name = name;
        this.login = login;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean isManager(){
        return getRoles().contains(Role.MANAGER);
    }
}

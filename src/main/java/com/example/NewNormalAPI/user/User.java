package com.example.NewNormalAPI.user;

import java.util.Arrays;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.NewNormalAPI.verification.Verification;

import lombok.Data;

@Entity
@Data
public class User implements UserDetails {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    // TODO validation for username,password,email,authorities,verified
    private @Id @GeneratedValue Long id;
    private String username;
    private String password;
    private String email;
    private String authorities; // admin, faculty, student
    private Boolean verified; // yes, no

    @OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
    private Verification verification;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(authorities));
    }

    @Override
    public boolean isAccountNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled() {
        // TODO Auto-generated method stub
        return false;
    }
}

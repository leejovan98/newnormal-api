package com.example.NewNormalAPI.user;

import java.util.Collection;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.NewNormalAPI.verification.Verification;

import lombok.Data;

@Entity
@Data
public class User implements UserDetails {
    private @Id @GeneratedValue Long id;
    private String username;
    private String password;
    private String email;
    private String role; // admin, faculty, student
    private Boolean verified; // yes, no
    
    @OneToOne(mappedBy="user", orphanRemoval=true, cascade=CascadeType.ALL)
	private Verification verification;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getPassword() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getUsername() {
        // TODO Auto-generated method stub
        return null;
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

    public String getEmail() {
        return null;
    }
}

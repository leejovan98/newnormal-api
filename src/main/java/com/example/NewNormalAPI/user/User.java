package com.example.NewNormalAPI.user;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.NewNormalAPI.event.Event;
import com.example.NewNormalAPI.verification.Verification;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity
@Data
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class User implements UserDetails {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String username;
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private String email;
	
	private String authorities = "student"; // admin, faculty, student
	
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String verified = "N"; // yes, no

	@OneToOne(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonIgnore
	private Verification verification;


	@OneToMany(mappedBy = "organizer", orphanRemoval = true, cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Event> events;

	@ManyToMany(mappedBy = "subscribers")
	@JsonIgnore
	private List<Event> subscriptions;

	private String vaccinated = "N";
	
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Singapore")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date vaccinationDate = null;


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Arrays.asList(new SimpleGrantedAuthority(authorities));
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return false;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		if (Objects.nonNull(verified) && verified.equals("Y"))
			return true;
		return false;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

}

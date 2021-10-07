package com.example.NewNormalAPI.event;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.NewNormalAPI.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // used to prevent infinite references from occurring with lombok
public class Event {

	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY) // used to prevent infinite references from occurring with
														// lombok
	private Long id;

	@ManyToOne
	@JoinColumn(name = "organizer_id", nullable = false)
	private User organizer;

	@ManyToMany
	@JoinTable(name = "subscription", joinColumns = @JoinColumn(name = "event_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private Set<User> subscribers;

	@NotNull
	@Size(min=1, max=50)
	private String title;

	@NotNull
	@Size(min=1, max=200)
	private String description;

	@NotNull
	private String visibility; // public, private

	@NotNull
	private Integer maxSubscribers;

	@NotNull
	@Size(min=1, max=50)
	private String location;

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Singapore")
	@NotNull
	private Date datetime;

	// invite link will be <host>:<port>/events/join/<invite_code>
	private String inviteCode;

	private int numSubscribers;

	@NotNull
	@Size(min=1, max=1) // Y / N value only
	private String vaccinationRequired;

	private Timestamp insertTs;

	@Override
	public String toString() {
		return "Event [id=" + id + ", user=" + "[" + organizer.getEmail() + "," + organizer.getUsername() + "]"
				+ ", title=" + title + ", description=" + description + ", visibility=" + visibility
				+ ", maxSubscribers=" + maxSubscribers + ", datetime=" + datetime + ", inviteCode=" + inviteCode
				+ ", location=" + location + ", numSubscribers=" + numSubscribers + ", isVaccinationRequired="
				+ vaccinationRequired + "]";
	}
}
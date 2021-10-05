package com.example.NewNormalAPI.event;

import javax.persistence.*;
import lombok.Data;

@Entity
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long event_id;

    @JoinColumn(name="user_id")
	private User user;

    @Column(length = 45)
    private String title = null;

    @Column(length = 200)
    private String description = null;

    @Column(nullable = false)
    private String visibility = "private"; // public, private

    private int maxsize;

    @Column(nullable = false, length = 32)
    private String datetime;

    private String invitelink;

    @Column(nullable = false, length = 45)
    private String location;

    private int numsubscribers;

    @Column(length = 32)
    private String vaxrequirement;
}
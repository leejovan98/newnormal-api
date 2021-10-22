package com.example.NewNormalAPI.adminconfig;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

import com.example.NewNormalAPI.user.User;

import io.micrometer.core.lang.NonNull;
import lombok.Data;

@Entity
@Data
public class AdminConfig {
    @Id @GeneratedValue
    private Long id;

    @NonNull
    @Size(min = 1, max = 100)
    private int capactiy; // percentage of venue capacity available for booking

    @OneToOne
	@JoinColumn(name = "admin_id", nullable = false)
	private User admin;
}
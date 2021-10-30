package com.example.NewNormalAPI.adminconfig;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AdminConfig {
    @Id
    @GeneratedValue
    private Long id;

    private String property;
    private String value;
S
    private Timestamp insertTs;

}
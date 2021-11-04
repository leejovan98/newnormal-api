package com.example.NewNormalAPI.adminconfig;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminConfigRepo extends JpaRepository<AdminConfig, Long> {
    AdminConfig findByProperty(String property);

    List<AdminConfig> findAll();
}
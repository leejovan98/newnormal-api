package com.example.NewNormalAPI.verification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationRepository extends CrudRepository<Verification, String> {
	
}

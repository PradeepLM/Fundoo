package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.Profile;
@Repository
public interface ProfilePicsRepository extends JpaRepository<Profile, Long>{

	@Query(value =  " select * from Profile where userId = ?" , nativeQuery =  true)
	Profile findByUserId(Long userId);
}

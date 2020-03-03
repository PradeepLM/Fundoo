package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.Profile;
@Repository
public interface ProfilePicsRepository extends JpaRepository<Profile, Long>{

	@Query("from Profile where user_id=:userId")
	Profile findByUserId(Long userId);
	@Modifying
	@Query("update Profile set profile_pic_name=:profilePicName where user_id=:id")
	void updateByUserId(String profilePicName, Long id);
}

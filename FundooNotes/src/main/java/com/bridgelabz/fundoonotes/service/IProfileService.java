package com.bridgelabz.fundoonotes.service;

import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;

/**
 * 
 * @author pradeep
 *
 */

public interface IProfileService {

	Profile storePicsInS3(MultipartFile file, String originalFilename, String contentType, String token);

	Profile updateProfilePic(MultipartFile file, String originalFilename, String contentType, String token);

	S3Object getProfilePic(String token);
	
	
	

}

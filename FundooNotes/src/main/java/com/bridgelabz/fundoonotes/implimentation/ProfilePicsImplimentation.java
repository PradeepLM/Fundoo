package com.bridgelabz.fundoonotes.implimentation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.ProfilePicsRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.IProfileService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author pradeep
 *
 */
@Slf4j
@Service
public class ProfilePicsImplimentation implements IProfileService {
	@Autowired
	private ProfilePicsRepository profilePicsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private AmazonS3 amazonS3Client;

	@Value("${BucketName}")
	private String bucketName;

	@Override
	@Transactional
	public Profile storePicsInS3(MultipartFile file, String filename, String contentType, String token) {
		try {
			Long id = generate.parseJwt(token);
			UserInformation user = userRepository.getUserById(id);
			if (user != null) {
				Profile profile = new Profile(filename, user);
				ObjectMetadata objectMetadata = new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.getSize());
				
				amazonS3Client.putObject(bucketName, filename, file.getInputStream(), objectMetadata);
				profilePicsRepository.save(profile);
				return profile;
			}
		} catch (Exception e) {
			throw new RuntimeException("Error occuring uploading file");
		}
		return null;
	}
	
	@Transactional
	@Override
	public Profile updateProfilePic(MultipartFile file, String originalFilename, String contentType, String token) {
		try {
			Long id = generate.parseJwt(token);
			UserInformation user = userRepository.getUserById(id);
			Profile profile=profilePicsRepository.findByUserId(id);
			 
			if(user!=null&&profile!=null) {
				delteObject(profile.getProfilePicName());
				//profilePicsRepository.delete(profile);
				profilePicsRepository.updateByUserId(originalFilename,id);
				ObjectMetadata objectMetadata=new ObjectMetadata();
				objectMetadata.setContentType(contentType);
				objectMetadata.setContentLength(file.getSize());
				
				amazonS3Client.putObject(bucketName, originalFilename, file.getInputStream(), objectMetadata);
				//profilePicsRepository.save(profile);
				return profile;
				
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		return null;
	}

	private void delteObject(String profilePicName) {
		try {
			amazonS3Client.deleteObject(bucketName, profilePicName);
		} catch (AmazonServiceException serviceException) {
			log.error(serviceException.getErrorMessage());
		} catch (AmazonClientException exception) {
			log.error("Error while deleting File.");
		}
	}
	@Transactional
	@Override
	public S3Object getProfilePic(String token) {
		try {
			Long id=generate.parseJwt(token);
			UserInformation user = userRepository.getUserById(id);
			if(user!=null) {
				Profile profile=profilePicsRepository.findByUserId(id);
				if(profile!=null) {
					return fetchObject(profile.getProfilePicName());
				}else {
					return null;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public S3Object fetchObject(String awsFileName) {
		S3Object s3Object;
		try {
			s3Object = amazonS3Client.getObject(new GetObjectRequest(bucketName,awsFileName));
		} catch (AmazonServiceException serviceException) {
			serviceException.printStackTrace();

			throw new RuntimeException("Error while streaming File.");
		} catch (AmazonClientException exception) {
			exception.printStackTrace();
			throw new RuntimeException("Error while streaming File.");
		}
		return s3Object;
	}

}

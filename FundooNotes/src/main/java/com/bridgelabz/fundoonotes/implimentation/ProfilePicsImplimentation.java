package com.bridgelabz.fundoonotes.implimentation;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.entity.UserInformation;
import com.bridgelabz.fundoonotes.repository.ProfilePicsRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ProfileService;
import com.bridgelabz.fundoonotes.utility.JwtGenerator;

@Service
public class ProfilePicsImplimentation implements ProfileService{
	@Autowired
	private ProfilePicsRepository profilePicsRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtGenerator generate;
	@Autowired
	private AmazonS3 amazonS3Client;
	
	@Value ("${BucketName}")
	private String bucketName;
	
	@Override
	@Transactional
	public Profile storePicsInS3(MultipartFile file, String filename, String contentType, String token) {
		try {
			Long id=generate.parseJwt(token);
			UserInformation user=userRepository.getUserById(id);
			System.out.println("hello");
			if(user!=null) {
				Profile profile=new Profile(filename,user);
				
				ObjectMetadata objectMetadata=new ObjectMetadata();
				
				objectMetadata.setContentType(contentType);
				
				objectMetadata.setContentLength(file.getSize());

				
			amazonS3Client.putObject(bucketName,filename,file.getInputStream(),objectMetadata);
				System.out.println("hello");
				profilePicsRepository.save(profile);
				System.out.println("ello");
				return profile;
			}
		} catch (Exception e) {
				//throw new RuntimeException("Error occuring uploading file");
			System.out.println(e.getMessage());
		}
		return null;
	}

}

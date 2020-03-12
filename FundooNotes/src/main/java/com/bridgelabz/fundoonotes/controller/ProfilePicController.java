package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.S3Object;
import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.IProfileService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author pradeep
 *
 */
@CrossOrigin("*")
@RestController
//its api for adding profile pics to aws3 and db
public class ProfilePicController {
	@Autowired
	private IProfileService profileService;
	@ApiOperation(value = "its api for  adding profile pic", response = Response.class)
	@PostMapping("/uploadProfilePic")
	public ResponseEntity<Response> addProfilePic(@ModelAttribute MultipartFile file,@RequestHeader("token") String token){
		Profile profile=profileService.storePicsInS3(file,file.getOriginalFilename(),file.getContentType(),token);
		return profile.getUserLabel()!=null?ResponseEntity.status(HttpStatus.OK).body(new Response("added pics sucessfully", profile)):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something wrong in adding"));		
	}
	
	@ApiOperation(value = "Api to update profile pic ", response = Response.class)
	@PostMapping("/updateProfilePics")
	public ResponseEntity<Response> updateProfile(@ModelAttribute MultipartFile file,@RequestHeader("token") String token){
			Profile profile=profileService.updateProfilePic(file,file.getOriginalFilename(),file.getContentType(),token);
			return profile.getUserLabel()!=null?ResponseEntity.status(HttpStatus.OK).body(new Response("updated sucessfully", profile)):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something wrong in updating"));		
	}
	
	@ApiOperation(value = "Api to getting profile pic info", response = Response.class)
	@GetMapping("/getProfilePic")
	public ResponseEntity<Response> getprofilePic(@RequestHeader ("token") String token){
		S3Object s3=profileService.getProfilePic(token);
		return s3!=null ? ResponseEntity.status(HttpStatus.OK).body(new Response("profile pics are", 200, s3)):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("somthing wrong",400, s3));		 
		
	}
	
}

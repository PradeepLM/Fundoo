package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.entity.Profile;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.ProfileService;

import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author pradeep
 *
 */
@RestController
//its api for adding profile pics to aws3 and db
public class ProfilePicController {
	@Autowired
	private ProfileService profileService;
	@ApiOperation(value = "its api for  adding profile pic", response = Response.class)
	@PostMapping("/uploadProfilePic")
	public ResponseEntity<Response> addProfilePic(@ModelAttribute MultipartFile file,@RequestHeader("token") String token){
		Profile profile=profileService.storePicsInS3(file,file.getOriginalFilename(),file.getContentType(),token);
		return profile.getUserLabel()!=null?ResponseEntity.status(HttpStatus.OK).body(new Response("added pics sucessfully", 200, profile)):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something wrong in adding",400, profile));		
	}
	
	@PostMapping("/updateProfilePics")
	public ResponseEntity<Response> updateProfile(@ModelAttribute MultipartFile file,@RequestHeader("token") String token){
			Profile profile=profileService.updateProfilePic(file,file.getOriginalFilename(),file.getContentType(),token);
			return profile.getUserLabel()!=null?ResponseEntity.status(HttpStatus.OK).body(new Response("updated sucessfully", 200, profile)):ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("something wrong in updating",400, profile));		
	}
	
}

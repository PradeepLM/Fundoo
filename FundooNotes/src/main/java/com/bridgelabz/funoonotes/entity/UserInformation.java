package com.bridgelabz.funoonotes.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
@Entity
@Data
@Table(name="userDetails")
public class UserInformation {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userId;
	private String name;
	private String email;
	private String password;
	private long mobileNumber;
	private boolean isVerified;
	private LocalDateTime createDate;

}

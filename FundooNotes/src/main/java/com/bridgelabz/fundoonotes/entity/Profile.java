package com.bridgelabz.fundoonotes.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor

public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private String profilePicName;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "userId")
	private UserInformation userLabel;

	public Profile(String profilePicName, UserInformation userLabel) {
		this.profilePicName = profilePicName;
		this.userLabel = userLabel;
	}
}

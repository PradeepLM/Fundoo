package com.bridgelabz.fundoonotes.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
/**
 * 
 * @author pradeep
 *
 */
@Data
@Entity
@Table(name = "NotesInfo")
public class NoteInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String description;

	private boolean isArchieved;

	private boolean isPinned;

	private boolean isTrashed;

	private LocalDateTime createdDateAndTime;

	private LocalDateTime upDateAndTime;

	private String colour;

	private LocalDateTime reminder;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="LabelNote",joinColumns= {@JoinColumn(name="noteId")},inverseJoinColumns= {@JoinColumn(name="labelId")})
	
	 @JsonBackReference
	 @JsonIgnore
	private List<LabelInformation> list;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "collaborator_note", joinColumns = { @JoinColumn(name = "noteId") }, inverseJoinColumns = {
			@JoinColumn(name = "userId") })
	@JsonBackReference
     private List<UserInformation> colabratorUser;
}

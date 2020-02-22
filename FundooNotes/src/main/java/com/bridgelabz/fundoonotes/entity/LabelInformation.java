package com.bridgelabz.fundoonotes.entity;

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

import lombok.Data;

@Data
@Entity
@Table(name = "label_info")
public class LabelInformation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long LabelId;
	private String name;
	private Long UserId;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "labelNote", joinColumns = { @JoinColumn(name = "labelId") }, inverseJoinColumns = {
			@JoinColumn(name = "id") })

	private List<NoteInformation> list;
}

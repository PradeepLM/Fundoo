package com.bridgelabz.fundoonotes.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	//private List<NoteInformation> list;

	public Long getLabelId() {
		return LabelId;
	}

	public void setLabelId(Long labelId) {
		LabelId = labelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getUserId() {
		return UserId;
	}

	public void setUserId(Long userId) {
		UserId = userId;
	}

	/*
	 * public List<NoteInformation> getList() { return list; }
	 * 
	 * public void setList(List<NoteInformation> list) { this.list = list; }
	 */

}

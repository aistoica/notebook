package com.ansr.dto;

import org.springframework.data.annotation.Id;

public class Disability {
	
/*	@Id
    private String id;*/
	
	private String type;
	
	private int grade;
	
	public Disability() {}
	
	public Disability(String type, int grade) {
		this.type = type;
		this.grade = grade;
	}

	public int getGrade() {
		return grade;
	}

	public String getType() {
		return type;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Disability [type=" + type + ", grade=" + grade
				+ "]";
	}
	
	
	
}
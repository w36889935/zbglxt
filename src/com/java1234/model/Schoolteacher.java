package com.java1234.model;

public class Schoolteacher {
	
	private int ID;
	private String schoolName;//ѧУ
	private String workroom;//ְ��
	private String teacherName;//����
	private String sex;//�Ա�
	private String telephone;//�ֻ�
	private String wenpin;//��ƾ
	
	public Schoolteacher() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Schoolteacher(String schoolName, String workroom, String teacherName, String sex, String telephone,
			String wenpin) {
		super();
		this.schoolName = schoolName;
		this.workroom = workroom;
		this.teacherName = teacherName;
		this.sex = sex;
		this.telephone = telephone;
		this.wenpin = wenpin;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getWorkroom() {
		return workroom;
	}

	public void setWorkroom(String workroom) {
		this.workroom = workroom;
	}


	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getWenpin() {
		return wenpin;
	}

	public void setWenpin(String wenpin) {
		this.wenpin = wenpin;
	}


	
}

package com.java1234.model;

import java.util.Date;

public class roomUseRecord {
	private int id;
	private String schoolName;//ѧУ����
	private String roomName;//����������
	private Date useDate;//ʹ������
	private String classNo;//�ڼ��ڿ�
	private String classInfo;//�����
	private String teacher;//�Ͽν�ʦ
	
	public roomUseRecord() {
		super();
		// TODO Auto-generated constructor stub
	}

	public roomUseRecord(String roomName, Date useDate, String classNo, String classInfo, String teacher) {
		super();
		this.roomName = roomName;
		this.useDate = useDate;
		this.classNo = classNo;
		this.classInfo = classInfo;
		this.teacher = teacher;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public Date getUseDate() {
		return useDate;
	}

	public void setUseDate(Date useDate) {
		this.useDate = useDate;
	}

	public String getClassNo() {
		return classNo;
	}

	public void setClassNo(String classNo) {
		this.classNo = classNo;
	}

	public String getClassInfo() {
		return classInfo;
	}

	public void setClassInfo(String classInfo) {
		this.classInfo = classInfo;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	
	
	
}

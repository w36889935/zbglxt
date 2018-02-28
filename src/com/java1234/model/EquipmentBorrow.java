package com.java1234.model;

import java.util.Date;

public class EquipmentBorrow {
	private int id;
	private String schoolName;//ѧУ����
	private String EquipmentRoom;//����������
	private String EquipmentName;//��������
	private String unit;//��λ
	private String num;//����
	private String applyName;//ʹ����
	private Date applyDate;//��������
	private Date returnDate;//�黹����
	private String returnInfo;//�黹���
	private String memo;//��ע
	
	public EquipmentBorrow() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EquipmentBorrow(String schoolName, String equipmentRoom, String equipmentName, String unit,
			String num, String applyName, Date applyDate, String returnInfo, String memo) {
		super();
		this.schoolName = schoolName;
		EquipmentRoom = equipmentRoom;
		EquipmentName = equipmentName;
		this.unit = unit;
		this.num = num;
		this.applyName = applyName;
		this.applyDate = applyDate;
		this.returnInfo = returnInfo;
		this.memo = memo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getEquipmentRoom() {
		return EquipmentRoom;
	}

	public void setEquipmentRoom(String equipmentRoom) {
		EquipmentRoom = equipmentRoom;
	}

	public String getEquipmentName() {
		return EquipmentName;
	}

	public void setEquipmentName(String equipmentName) {
		EquipmentName = equipmentName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getApplyName() {
		return applyName;
	}

	public void setApplyName(String applyName) {
		this.applyName = applyName;
	}

	public Date getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getReturnInfo() {
		return returnInfo;
	}

	public void setReturnInfo(String returnInfo) {
		this.returnInfo = returnInfo;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	
	
}

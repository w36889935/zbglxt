package com.java1234.model;

/**
 * 用户Model类
 * @author www.java1234.com
 *
 */
public class User {

	private int id;
	private String userName;
	private String password;
	private String schoolCode;
	private String userType;
	private String schoolName;
	private String realName;//真实姓名
	private String passQues;//密保问题
	private String quesAnswer;//密保答案
	private String phoneNumber;//手机号码
	private String passEmail;//邮箱
	private Boolean syyqgl;//实验仪器管理
	private Boolean syjxgl;//实验教学管理
	private Boolean ytmgl;//音体美权限
	private Boolean xxjbxx;//学校基本信息管理
	private Boolean canUse;//状态
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public User(String userName, String password, String schoolName, String realName, String passQues,
			String quesAnswer, String phoneNumber, String passEmail) {
		super();
		this.userName = userName;
		this.password = password;
		this.schoolName = schoolName;
		this.realName = realName;
		this.passQues = passQues;
		this.quesAnswer = quesAnswer;
		this.phoneNumber = phoneNumber;
		this.passEmail = passEmail;
	}



	public User(String userName, String password) {
		super();
		this.userName = userName;
		this.password = password;
	}



	public String getSchoolName() {
		return schoolName;
	}



	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getSchoolCode() {
		return schoolCode;
	}



	public void setSchoolCode(String schoolCode) {
		this.schoolCode = schoolCode;
	}



	public String getUserType() {
		return userType;
	}



	public void setUserType(String userType) {
		this.userType = userType;
	}



	public String getRealName() {
		return realName;
	}



	public void setRealName(String realName) {
		this.realName = realName;
	}



	public String getPassQues() {
		return passQues;
	}



	public void setPassQues(String passQues) {
		this.passQues = passQues;
	}



	public String getQuesAnswer() {
		return quesAnswer;
	}



	public void setQuesAnswer(String quesAnswer) {
		this.quesAnswer = quesAnswer;
	}



	public String getPhoneNumber() {
		return phoneNumber;
	}



	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}



	public String getPassEmail() {
		return passEmail;
	}



	public void setPassEmail(String passEmail) {
		this.passEmail = passEmail;
	}



	public Boolean getSyyqgl() {
		return syyqgl;
	}



	public void setSyyqgl(Boolean syyqgl) {
		this.syyqgl = syyqgl;
	}



	public Boolean getSyjxgl() {
		return syjxgl;
	}



	public void setSyjxgl(Boolean syjxgl) {
		this.syjxgl = syjxgl;
	}



	public Boolean getYtmgl() {
		return ytmgl;
	}



	public void setYtmgl(Boolean ytmgl) {
		this.ytmgl = ytmgl;
	}



	public Boolean getXxjbxx() {
		return xxjbxx;
	}



	public void setXxjbxx(Boolean xxjbxx) {
		this.xxjbxx = xxjbxx;
	}



	public Boolean getCanUse() {
		return canUse;
	}



	public void setCanUse(Boolean canUse) {
		this.canUse = canUse;
	}

	

	

	
}

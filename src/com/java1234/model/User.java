package com.java1234.model;

/**
 * �û�Model��
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
	private String realName;//��ʵ����
	private String passQues;//�ܱ�����
	private String quesAnswer;//�ܱ���
	private String phoneNumber;//�ֻ�����
	private String passEmail;//����
	private Boolean syyqgl;//ʵ����������
	private Boolean syjxgl;//ʵ���ѧ����
	private Boolean ytmgl;//������Ȩ��
	private Boolean xxjbxx;//ѧУ������Ϣ����
	private Boolean canUse;//״̬
	
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

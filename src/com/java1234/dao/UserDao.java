package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java1234.model.User;
import com.java1234.model.roomUseRecord;
import com.java1234.util.DateUtil;


public class UserDao {

	/**
	 * 登录验证
	 * @param con
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User login(Connection con,User user) throws Exception{
		User resultUser=null;
		String sql="  SELECT userList.userName,userList.password,schoolCode,userType  FROM  userList where userName=? and  password=?   union all  SELECT userName,password,xianCode,inXian  FROM  xianUserList where  userName=? and  password= ? ";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getPassword());
		pstmt.setString(3, user.getUserName());
		pstmt.setString(4, user.getPassword());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			resultUser=new User();
			resultUser.setUserName(rs.getString("userName"));
			resultUser.setPassword(rs.getString("password"));
			resultUser.setSchoolCode(rs.getString("schoolCode"));
			resultUser.setUserType(rs.getString("userType"));
		}
		return resultUser;
	}
	
	/*学校权限判断*/
	public User shorroot(Connection con,User user) throws Exception{
		String sql=" SELECT syyqgl,syjxgl,ytmgl,xxjbxx,canUse FROM userList where userName= ? ";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
		user.setSyyqgl(rs.getBoolean("syyqgl"));
		user.setSyjxgl(rs.getBoolean("syjxgl"));
		user.setYtmgl(rs.getBoolean("ytmgl"));
		user.setXxjbxx(rs.getBoolean("xxjbxx"));
		user.setCanUse(rs.getBoolean("canUse"));
		}
		return user;
	}
	/*学校用户*/
	public User shorcod(Connection con,User user) throws Exception{
		String sql=" SELECT schoolName FROM schoolList where schoolCode=?";
		System.out.println("==="+user.getSchoolCode());
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getSchoolCode());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
		user.setSchoolName(rs.getString("schoolName"));
		}
		return user;
	}
	/*添加用户*/
	public  int userAdd(Connection con,User user)throws Exception{
		String sql="insert into userList(userName,realName,schoolCode,userType,password,passQues,quesAnswer,phoneNumber,passEmail,syyqgl,syjxgl,ytmgl,xxjbxx,canUse) values(?,?,(SELECT schoolCode FROM schoolList   where schoolName='"+user.getSchoolName()+"'),'普通用户',?,?,?,?,?,1,1,1,1,0)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getUserName());
		pstmt.setString(2, user.getRealName());
		pstmt.setString(3, user.getPassword());
		pstmt.setString(4, user.getPassQues());
		pstmt.setString(5, user.getQuesAnswer());
		pstmt.setString(6, user.getPhoneNumber());
		pstmt.setString(7, user.getPassEmail());
		return pstmt.executeUpdate();
	}

}

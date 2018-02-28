package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java1234.model.PageBean;
import com.java1234.model.User;
import com.java1234.model.roomUseRecord;
import com.java1234.util.DateUtil;
import com.java1234.util.StringUtil;

/**
 * @ClassName: StudentDao
 * @Description: 对学校用户进行更改
 * @author: wrwang
 * @date: 2018年2月8日 下午1:04:59
 */
public class UserListDao {

	/**
	 * @Title: studentList
	 * @Description: 账号信息管理
	 * @param con	（ 接数据库连接对象）
	 * @param pageBean	（分页查询对象）
	 * @param schoolCode (学校编号)
	 * @return
	 * @throws Exception
	 * @return: ResultSet
	 */
	public ResultSet userList(Connection con,PageBean pageBean,String schoolCode)throws Exception{
		String sb="SELECT Id,userName,password,realName,phoneNumber,syyqgl,syjxgl,ytmgl,xxjbxx,canUse FROM userList where userType='普通用户' and schoolCode='"+schoolCode+"' order by Id asc ";
		String sd="";
		if(pageBean!=null){
			System.out.println("getRows"+pageBean.getRows()+"pageBean.getStart()"+pageBean.getStart());
			 sd="select top "+pageBean.getRows()+"  Id,userName,password,realName,phoneNumber,syyqgl,syjxgl,ytmgl,xxjbxx,canUse FROM userList  where Id not in (select top "+pageBean.getStart()+" Id from userList where schoolCode = '"+schoolCode+"' and  userType='普通用户' order by Id asc) and  schoolCode = '"+schoolCode+"' and  userType='普通用户' order by Id asc ";
		}else {
			 sd=sb;
		}
		if(pageBean!=null){
			sb=sd;
		}
		PreparedStatement pstmt=con.prepareStatement(sb);
		return pstmt.executeQuery();
	}
	
	/**
	 * @Title: studentCount
	 * @Description: 查询记录总数
	 * @param con （数据库连接对象）
	 * @param student （功能室使用对象）
	 * @param bbirthday （开始时间）
	 * @param ebirthday （结束时间）
	 * @param schoolCode （学校编号）
	 * @return
	 * @throws Exception
	 * @return: int 查询记录总数
	 */
	public int studentCount(Connection con,String schoolCode)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total FROM userList where userType='普通用户' and schoolCode='"+schoolCode+"'");
		PreparedStatement pstmt=con.prepareStatement(sb.toString());
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	/**
	 * @Title: studentDelete
	 * @Description: 删除人员记录
	 * @param con （数据库连接对象）
	 * @param delIds （要删除的记录ID）
	 * @return
	 * @throws Exception
	 * @return: int (删除记录的总数)
	 */
	public int studentDelete(Connection con,String delIds)throws Exception{
		String sql="delete from userList where Id in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentModify
	 * @Description: 修改账号状态记录
	 * @param con （数据库连接对象）
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int studentModify(Connection con,User user)throws Exception{
		String sql="update userList set syyqgl=?,syjxgl=?,ytmgl=?,xxjbxx=?,canUse=? where Id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setBoolean(1, user.getSyyqgl());
		pstmt.setBoolean(2, user.getSyjxgl());
		pstmt.setBoolean(3, user.getYtmgl());
		pstmt.setBoolean(4, user.getXxjbxx());
		pstmt.setBoolean(5, user.getCanUse());
		pstmt.setInt(6, user.getId());
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentModify
	 * @Description: 修改账号密码
	 * @param con （数据库连接对象）
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int alterUser(Connection con,User user,Boolean a)throws Exception{
		String sql=null;
		if(a) {
			sql="update userList set password=? where userName=?";
		}else {
			sql="update xianUserList set password=? where userName=?";
		}
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, user.getPassword());
		pstmt.setString(2, user.getUserName());
		return pstmt.executeUpdate();
	}
	

}

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
 * @Description: ��ѧУ�û����и���
 * @author: wrwang
 * @date: 2018��2��8�� ����1:04:59
 */
public class UserListDao {

	/**
	 * @Title: studentList
	 * @Description: �˺���Ϣ����
	 * @param con	�� �����ݿ����Ӷ���
	 * @param pageBean	����ҳ��ѯ����
	 * @param schoolCode (ѧУ���)
	 * @return
	 * @throws Exception
	 * @return: ResultSet
	 */
	public ResultSet userList(Connection con,PageBean pageBean,String schoolCode)throws Exception{
		String sb="SELECT Id,userName,password,realName,phoneNumber,syyqgl,syjxgl,ytmgl,xxjbxx,canUse FROM userList where userType='��ͨ�û�' and schoolCode='"+schoolCode+"' order by Id asc ";
		String sd="";
		if(pageBean!=null){
			System.out.println("getRows"+pageBean.getRows()+"pageBean.getStart()"+pageBean.getStart());
			 sd="select top "+pageBean.getRows()+"  Id,userName,password,realName,phoneNumber,syyqgl,syjxgl,ytmgl,xxjbxx,canUse FROM userList  where Id not in (select top "+pageBean.getStart()+" Id from userList where schoolCode = '"+schoolCode+"' and  userType='��ͨ�û�' order by Id asc) and  schoolCode = '"+schoolCode+"' and  userType='��ͨ�û�' order by Id asc ";
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
	 * @Description: ��ѯ��¼����
	 * @param con �����ݿ����Ӷ���
	 * @param student ��������ʹ�ö���
	 * @param bbirthday ����ʼʱ�䣩
	 * @param ebirthday ������ʱ�䣩
	 * @param schoolCode ��ѧУ��ţ�
	 * @return
	 * @throws Exception
	 * @return: int ��ѯ��¼����
	 */
	public int studentCount(Connection con,String schoolCode)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total FROM userList where userType='��ͨ�û�' and schoolCode='"+schoolCode+"'");
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
	 * @Description: ɾ����Ա��¼
	 * @param con �����ݿ����Ӷ���
	 * @param delIds ��Ҫɾ���ļ�¼ID��
	 * @return
	 * @throws Exception
	 * @return: int (ɾ����¼������)
	 */
	public int studentDelete(Connection con,String delIds)throws Exception{
		String sql="delete from userList where Id in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentModify
	 * @Description: �޸��˺�״̬��¼
	 * @param con �����ݿ����Ӷ���
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
	 * @Description: �޸��˺�����
	 * @param con �����ݿ����Ӷ���
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

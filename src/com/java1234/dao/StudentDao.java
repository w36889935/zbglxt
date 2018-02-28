package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.java1234.model.PageBean;
import com.java1234.model.roomUseRecord;
import com.java1234.util.DateUtil;
import com.java1234.util.StringUtil;

/**
 * @ClassName: StudentDao
 * @Description: ������ʹ�ü�¼�������ɾ�Ĳ�
 * @author: wrwang
 * @date: 2018��2��8�� ����1:04:59
 */
public class StudentDao {

	/**
	 * @Title: studentList
	 * @Description: ��ѯ������ʹ�ü�¼
	 * @param con	�� �����ݿ����Ӷ���
	 * @param pageBean	����ҳ��ѯ����
	 * @param student	��������ʹ�ö���
	 * @param bbirthday ����ʼʱ�䣩
	 * @param ebirthday	������ʱ�䣩
	 * @param schoolCode (ѧУ���)
	 * @return
	 * @throws Exception
	 * @return: ResultSet
	 */
	public ResultSet studentList(Connection con,PageBean pageBean,roomUseRecord student,String bbirthday,String ebirthday,String schoolCode,String xianCode)throws Exception{
		StringBuffer sb=new StringBuffer("SELECT id,schoolName,roomName,useDate,classNo,classInfo,teacher FROM roomUseRecord");
		StringBuffer sd=new StringBuffer(" ");
		if(pageBean!=null){
			 sd.append("select top "+pageBean.getRows()+"  id,schoolName,roomName,useDate,classNo,classInfo,teacher FROM roomUseRecord  where id not in (select top "+pageBean.getStart()+" id from roomUseRecord where schoolCode = '"+schoolCode+"' order by ID asc) ");
		}else {
			 sd=sb;
		}
		if(StringUtil.isNotEmpty(student.getSchoolName())){
			sd.append(" and schoolName like '%"+student.getSchoolName()+"%'");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(student.getRoomName())){
			sd.append(" and roomName like '%"+student.getRoomName()+"%'");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(student.getTeacher())){
			System.out.println("=getTeacher=" + student.getTeacher());
			sd.append(" and teacher like '%"+student.getTeacher()+"%'");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			sd.append("  and convert(timestamp,convert(datetime,useDate))>=convert(timestamp,convert(datetime,'"+bbirthday+"'))");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sd.append(" and convert(timestamp,convert(datetime,useDate))<=convert(timestamp,convert(datetime,'"+ebirthday+"'))");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(schoolCode)){
			sd.append(" and schoolCode = '"+schoolCode+"' ");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(xianCode)) {
			sd.append(" and schoolCode like '"+xianCode+"%' ");
			sb=sd;
		}
		if(pageBean!=null){
			sb=sd;
		}
		sd.append(" order by ID asc ");
		/*�ж�sql������Ƿ���where ���û�аѵ�һ��and����where*/
		int sta=sb.indexOf("where");
		System.out.println("=="+sb);
		PreparedStatement pstmt=null;
		if(sta==-1) {
			 pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		}else {
			 pstmt=con.prepareStatement(sb.toString());
		}
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
	public int studentCount(Connection con,roomUseRecord student,String bbirthday,String ebirthday,String schoolCode,String xianCode)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from roomUseRecord");
		if(StringUtil.isNotEmpty(student.getSchoolName())){
			sb.append(" and schoolName like '%"+student.getSchoolName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getRoomName())){
			sb.append(" and roomName like '%"+student.getRoomName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getTeacher())){
			sb.append(" and teacher like '%"+student.getTeacher()+"%'");
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			sb.append("  and convert(timestamp,convert(datetime,useDate))>=convert(timestamp,convert(datetime,'"+bbirthday+"'))");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and convert(timestamp,convert(datetime,useDate))<=convert(timestamp,convert(datetime,'"+ebirthday+"'))");
		}
		if(StringUtil.isNotEmpty(schoolCode)){
			sb.append(" and schoolCode = '"+schoolCode+"'");
		}
		if(StringUtil.isNotEmpty(xianCode)) {
			sb.append(" and schoolCode like '"+xianCode+"%' ");
		}
		PreparedStatement pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));

		ResultSet rs=pstmt.executeQuery();
		if(rs.next()){
			return rs.getInt("total");
		}else{
			return 0;
		}
	}
	
	/**
	 * @Title: studentDelete
	 * @Description: ɾ��������ʹ�ü�¼
	 * @param con �����ݿ����Ӷ���
	 * @param delIds ��Ҫɾ���ļ�¼ID��
	 * @return
	 * @throws Exception
	 * @return: int (ɾ����¼������)
	 */
	public int studentDelete(Connection con,String delIds)throws Exception{
		String sql="delete from roomUseRecord where id in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentAdd
	 * @Description: ���ӹ�����ʹ�ü�¼
	 * @param con �����ݿ����Ӷ���
	 * @param student ��������ʹ�ö���
	 * @param schoolCode ��ѧУ��ţ�
	 * @param schoolName ��ѧУ���֣�
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int studentAdd(Connection con,roomUseRecord student,String schoolCode,String schoolName)throws Exception{
		String sql="insert into roomUseRecord(schoolCode,schoolName,roomName,useDate,classNo,classInfo,teacher) values(?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, schoolCode);
		pstmt.setString(2, schoolName);
		pstmt.setString(3, student.getRoomName());
		pstmt.setString(4, DateUtil.formatDate(student.getUseDate(), "yyyy-MM-dd"));
		pstmt.setString(5, student.getClassNo());
		pstmt.setString(6, student.getClassInfo());
		pstmt.setString(7, student.getTeacher());
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentModify
	 * @Description: �޸Ĺ�����ʹ�ü�¼
	 * @param con �����ݿ����Ӷ���
	 * @param student ��������ʹ�ü�¼��
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int studentModify(Connection con,roomUseRecord student)throws Exception{
		String sql="update roomUseRecord set roomName=?,useDate=?,classNo=?,classInfo=?,teacher=? where id=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getRoomName());
		pstmt.setString(2, DateUtil.formatDate(student.getUseDate(), "yyyy-MM-dd"));
		pstmt.setString(3, student.getClassNo());
		pstmt.setString(4, student.getClassInfo());
		pstmt.setString(5, student.getTeacher());
		pstmt.setInt(6, student.getId());
		return pstmt.executeUpdate();
	}
	

}

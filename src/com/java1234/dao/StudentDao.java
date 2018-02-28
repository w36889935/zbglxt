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
 * @Description: 功能室使用记录表进行增删改查
 * @author: wrwang
 * @date: 2018年2月8日 下午1:04:59
 */
public class StudentDao {

	/**
	 * @Title: studentList
	 * @Description: 查询功能室使用记录
	 * @param con	（ 接数据库连接对象）
	 * @param pageBean	（分页查询对象）
	 * @param student	（功能室使用对象）
	 * @param bbirthday （开始时间）
	 * @param ebirthday	（结束时间）
	 * @param schoolCode (学校编号)
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
		/*判断sql语句中是否含有where 如果没有把第一个and换成where*/
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
	 * @Description: 删除功能室使用记录
	 * @param con （数据库连接对象）
	 * @param delIds （要删除的记录ID）
	 * @return
	 * @throws Exception
	 * @return: int (删除记录的总数)
	 */
	public int studentDelete(Connection con,String delIds)throws Exception{
		String sql="delete from roomUseRecord where id in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentAdd
	 * @Description: 增加功能室使用记录
	 * @param con （数据库连接对象）
	 * @param student （功能室使用对象）
	 * @param schoolCode （学校编号）
	 * @param schoolName （学校名字）
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
	 * @Description: 修改功能室使用记录
	 * @param con （数据库连接对象）
	 * @param student （功能室使用记录）
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

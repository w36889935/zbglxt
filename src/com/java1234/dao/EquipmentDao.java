package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.java1234.model.EquipmentBorrow;
import com.java1234.model.PageBean;
import com.java1234.util.DateUtil;
import com.java1234.util.StringUtil;

/**
 * @ClassName: EquipmentDao
 * @Description: ���Ľ��ü�¼������ɾ�Ĳ�
 * @author: wrwang
 * @date: 2018��2��7�� ����10:59:29
 */
public class EquipmentDao {

	/**
	 * @Title: studentList
	 * @Description: ���Ľ��ü�¼�Ĳ�ѯ����
	 * @param con �������ݿ����Ӷ���
	 * @param pageBean ����ҳ��ѯ����
	 * @param student  �����Ľ��ü�¼����
	 * @param bbirthday ����ʼʱ�䣩
	 * @param ebirthday ������ʱ�䣩
	 * @param schoolCode ���û���ѧУ��ţ�
	 * @return
	 * @throws Exception
	 * @return: ResultSet
	 */
	public ResultSet studentList(Connection con,PageBean pageBean,EquipmentBorrow student,String bbirthday,String ebirthday,String schoolCode,String xianCode)throws Exception{
		StringBuffer sb=new StringBuffer("SELECT id,schoolName,EquipmentRoom,EquipmentName,unit,num,applyName,applyDate,returnDate,returnInfo,memo FROM EquipmentBorrow");
		StringBuffer sd=new StringBuffer(" ");
		if(pageBean!=null){
			sd.append("select top "+pageBean.getRows()+" id,schoolName,EquipmentRoom,EquipmentName,unit,num,applyName,applyDate,returnDate,returnInfo,memo FROM EquipmentBorrow  where id not in (select top "+pageBean.getStart()+" id from EquipmentBorrow where schoolCode = '"+schoolCode+"' order by ID asc) " );
			
			 }else {
			 sd=sb;
		}
		
		if(StringUtil.isNotEmpty(student.getSchoolName())){
			sd.append(" and schoolName like '%"+student.getSchoolName()+"%'");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(student.getEquipmentRoom())){
			sd.append(" and EquipmentRoom like '%"+student.getEquipmentRoom()+"%'");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(student.getEquipmentName())){
			sd.append(" and EquipmentName like '%"+student.getEquipmentName()+"%'");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(student.getApplyName())){
			System.out.println("=appname=="+student.getApplyName());
			sd.append(" and applyName  like '%"+student.getApplyName()+"%'");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			sd.append("  and convert(timestamp,convert(datetime,applyDate))>=convert(timestamp,convert(datetime,'"+bbirthday+"'))");
			sb=sd;
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sd.append(" and convert(timestamp,convert(datetime,applyDate))<=convert(timestamp,convert(datetime,'"+ebirthday+"'))");
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
		System.out.println("=s1d="+sb);
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
	 * @Description: ��ѯ���Ľ��ü�¼����
	 * @param con ��Connection�����������ݿ⣩
	 * @param student �� EquipmentBorrow��ʵ�����
	 * @param bbirthday ����ʼʱ�䣩
	 * @param ebirthday ������ʱ�䣩
	 * @param schoolCode ��ѧУ��ţ�
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int studentCount(Connection con,EquipmentBorrow student,String bbirthday,String ebirthday,String schoolCode,String xianCode)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from EquipmentBorrow");
		if(StringUtil.isNotEmpty(student.getSchoolName())){
			sb.append(" and schoolName like '%"+student.getSchoolName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getEquipmentRoom())){
			sb.append(" and EquipmentRoom like '%"+student.getEquipmentRoom()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getEquipmentName())){
			sb.append(" and EquipmentName like '%"+student.getEquipmentName()+"%'");
		}
		if(StringUtil.isNotEmpty(student.getApplyName())){
			sb.append(" and applyName like '%"+student.getApplyName()+"%'");
		}
		if(StringUtil.isNotEmpty(bbirthday)){
			sb.append("  and convert(timestamp,convert(datetime,applyDate))>=convert(timestamp,convert(datetime,'"+bbirthday+"'))");
		}
		if(StringUtil.isNotEmpty(ebirthday)){
			sb.append(" and convert(timestamp,convert(datetime,applyDate))<=convert(timestamp,convert(datetime,'"+ebirthday+"'))");
		}
		if(StringUtil.isNotEmpty(schoolCode)){
			sb.append(" and schoolCode = '"+schoolCode+"' ");
		}
		if(StringUtil.isNotEmpty(xianCode)) {
			sb.append(" and schoolCode like '"+xianCode+"%' ");
		}
		System.out.println("=sd="+sb);
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
	 * @Description: ɾ��ָ�����Ľ��ü�¼��¼
	 * @param con
	 * @param delIds ��Ҫɾ����¼��ID��
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int studentDelete(Connection con,String delIds)throws Exception{
		String sql="delete from EquipmentBorrow where id in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentreturn
	 * @Description: ���Ľ��ü�¼  �黹
	 * @param con 
	 * @param delIds Ҫ�黹�ļ�¼��ID
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int studentreturn(Connection con,String delIds,String date)throws Exception{

		String sql="update EquipmentBorrow set returnInfo='�黹',returnDate='"+date+"'  where id in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentAdd
	 * @Description: ������Ľ��ü�¼
	 * @param con
	 * @param student  EquipmentBorrow��ʵ�����
	 * @param schoolCode ѧУ���
	 * @param schoolName ѧУ����
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int studentAdd(Connection con,EquipmentBorrow student,String schoolCode,String schoolName)throws Exception{
		String sql=null;
		if(student.getReturnDate()==null) {
			sql= "insert into EquipmentBorrow(id,schoolCode,schoolName,opUser,EquipmentRoom,EquipmentName,unit,num,applyName,applyDate,returnInfo,memo) values((select  max(ID)+1 from EquipmentBorrow),?,?,?,?,?,?,?,?,?,?,?)";
		}else {
			sql= "insert into EquipmentBorrow values((select  max(ID)+1 from EquipmentBorrow),?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getSchoolName());
		pstmt.setString(2,schoolName);
		pstmt.setString(3,schoolCode);
		pstmt.setString(4, student.getEquipmentRoom());
		pstmt.setString(5, student.getEquipmentName());
		pstmt.setString(6, student.getUnit());
		pstmt.setString(7, student.getNum());
		pstmt.setString(8, student.getApplyName());
		pstmt.setString(9, DateUtil.formatDate(student.getApplyDate(), "yyyy-MM-dd"));
		if(student.getReturnDate()==null) {
			pstmt.setString(10, student.getReturnInfo());
			pstmt.setString(11, student.getMemo());
		}else {
			pstmt.setString(10, DateUtil.formatDate(student.getReturnDate(), "yyyy-MM-dd"));
			pstmt.setString(11, student.getReturnInfo());
			pstmt.setString(12, student.getMemo());
		}
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: studentModify
	 * @Description: �޸����Ľ��ü�¼
	 * @param con
	 * @param student EquipmentBorrow��ʵ�����
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int studentModify(Connection con,EquipmentBorrow student)throws Exception{
		String sql=null;
		if(student.getReturnDate()==null) {
			sql="update EquipmentBorrow set schoolName=?,EquipmentRoom=?,EquipmentName=?,unit=?,num=?,applyName=?,applyDate=?,returnInfo=?,memo=?   where Id=?";
		}else {
			sql="update EquipmentBorrow set schoolName=?,EquipmentRoom=?,EquipmentName=?,unit=?,num=?,applyName=?,applyDate=?,returnDate=?,returnInfo=?,memo=?   where Id=?";
		}
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, student.getSchoolName());
		pstmt.setString(2, student.getEquipmentRoom());
		pstmt.setString(3, student.getEquipmentName());
		pstmt.setString(4, student.getUnit());
		pstmt.setString(5, student.getNum());
		pstmt.setString(6, student.getApplyName());
		pstmt.setString(7, DateUtil.formatDate(student.getApplyDate(), "yyyy-MM-dd"));
		if(student.getReturnDate()==null) {
			pstmt.setString(8, student.getReturnInfo());
			pstmt.setString(9, student.getMemo());
			pstmt.setInt(10, student.getId());
		}else {
			pstmt.setString(8, DateUtil.formatDate(student.getReturnDate(), "yyyy-MM-dd"));
			pstmt.setString(9, student.getReturnInfo());
			pstmt.setString(10, student.getMemo());
			pstmt.setInt(11, student.getId());
		}
		
		return pstmt.executeUpdate();
	}
}

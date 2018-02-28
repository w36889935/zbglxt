package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.java1234.model.PageBean;
import com.java1234.model.Schoolteacher;
import com.java1234.util.StringUtil;

/**
 * @ClassName: SchoolteacherDao
 * @Description: ��ѧУ������Ա�������ɾ��
 * @author: wrwang
 * @date: 2018��2��8�� ����12:46:59
 */
public class SchoolteacherDao {

	/**
	 * @Title: gradeList
	 * @Description: ѧУ������Ա����в�ѯ
	 * @param con	�� �����ݿ����Ӷ���
	 * @param pageBean	����ҳ����
	 * @param grade (ѧУ������Ա����)
	 * @param schoolCode {ѧУ��ţ�
	 * @return
	 * @throws Exception
	 * @return: ResultSet
	 */
	public ResultSet gradeList(Connection con,PageBean pageBean,Schoolteacher grade,String schoolCode,String xianCode)throws Exception{
		StringBuffer sb=new StringBuffer("select ID,schoolName,workroom,teacherName,sex,telephone,wenpin from schoolteacher");
		StringBuffer sd=new StringBuffer(" ");
		if(pageBean!=null){
			 sd.append("select top "+pageBean.getRows()+" ID,schoolName,workroom,teacherName,sex,telephone,wenpin from schoolteacher where ID not in (select top "+pageBean.getStart()+" ID from schoolteacher where schoolCode = '"+schoolCode+"' order by ID asc)");
		}else {
			 sd=sb;
		}
		if(grade!=null && StringUtil.isNotEmpty(grade.getTeacherName())){
			sd.append(" and teacherName like '%"+grade.getTeacherName()+"%' ");
			sb=sd;
		}
		if(grade!=null && StringUtil.isNotEmpty(grade.getSchoolName())){
			sd.append(" and schoolName like '%"+grade.getSchoolName()+"%' ");
			sb=sd;
		}
		if(grade!=null && StringUtil.isNotEmpty(grade.getWorkroom())){
			sd.append(" and workroom = '"+grade.getWorkroom()+"' ");
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
		PreparedStatement pstmt=null;
		if(sta==-1) {
			 pstmt=con.prepareStatement(sb.toString().replaceFirst("and", "where"));
		}else {
			 pstmt=con.prepareStatement(sb.toString());
		}
		return pstmt.executeQuery();
	}
	
	/**
	 * @Title: gradeCount
	 * @Description: ��ѯѧУ������Ա����
	 * @param con �����ݿ����Ӷ���
	 * @param grade (ѧУ������Աʵ������)
	 * @param schoolCode ���û���ѧУ��ţ�
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int gradeCount(Connection con,Schoolteacher grade,String schoolCode,String xianCode)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from schoolteacher");
		if(StringUtil.isNotEmpty(grade.getTeacherName())){
			sb.append(" and teacherName like '%"+grade.getTeacherName()+"%'");
		}
		if(grade!=null && StringUtil.isNotEmpty(grade.getSchoolName())){
			sb.append(" and schoolName like '%"+grade.getSchoolName()+"%' ");
		}
		if(grade!=null && StringUtil.isNotEmpty(grade.getWorkroom())){
			sb.append(" and workroom = '"+grade.getWorkroom()+"' ");
		}
		if(StringUtil.isNotEmpty(schoolCode)){
			sb.append(" and schoolCode = '"+schoolCode+"' ");
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
	 * @Title: gradeDelete
	 * @Description: ɾ��ѧУ������Ա
	 * @param con
	 * @param delIds ��Ҫɾ����¼��ID��
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int gradeDelete(Connection con,String delIds)throws Exception{
		String sql="delete from schoolteacher where id in("+delIds+")";
		PreparedStatement pstmt=con.prepareStatement(sql);
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: gradeAdd
	 * @Description: ����ѧУ������Ա
	 * @param con	
	 * @param grade	(ѧУ������Աʵ������)
	 * @param schoolCode ��ѧУ��ţ�
	 * @return
	 * @throws Exception
	 * @return: int
	 */
	public int gradeAdd(Connection con,Schoolteacher grade,String schoolCode)throws Exception{
		String sql="insert into schoolteacher(schoolCode,schoolName,workroom,teacherName,sex,telephone,wenpin) values (?,?,?,?,?,?,?)";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, schoolCode);
		pstmt.setString(2, grade.getSchoolName());
		pstmt.setString(3, grade.getWorkroom());
		pstmt.setString(4, grade.getTeacherName());
		pstmt.setString(5, grade.getSex());
		pstmt.setString(6, grade.getTelephone());
		pstmt.setString(7, grade.getWenpin());
		return pstmt.executeUpdate();
	}
	
	/**
	 * @Title: gradeModify
	 * @Description: �༭ѧУ������Ա��Ϣ
	 * @param con
	 * @param grade	(ѧУ������Աʵ������)
	 * @return
	 * @throws Exception
	 * @return: int �����ļ�¼����
	 */
	public int gradeModify(Connection con,Schoolteacher grade)throws Exception{
		String sql="update schoolteacher set schoolName=?,workroom=?,teacherName=?,sex=?,telephone=?,wenpin=? where ID=?";
		PreparedStatement pstmt=con.prepareStatement(sql);
		pstmt.setString(1, grade.getSchoolName());
		pstmt.setString(2, grade.getWorkroom());
		pstmt.setString(3, grade.getTeacherName());
		pstmt.setString(4, grade.getSex());
		pstmt.setString(5, grade.getTelephone());
		pstmt.setString(6, grade.getWenpin());
		pstmt.setInt(7, grade.getID());
		return pstmt.executeUpdate();
	}

	/**
	 * @Title: gradeworkroomList
	 * @Description: ��ѯ����ְ��
	 * @param con
	 * @return
	 * @return: ResultSet
	 * @throws SQLException 
	 */
	public ResultSet gradeworkroomList(Connection con) throws SQLException {
		String sql="select distinct workroom from schoolteacher";
		// TODO Auto-generated method stub
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pstmt.executeQuery();
	}
	
}

package com.java1234.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SchoolListDao {
	public ResultSet studentList(Connection con,String parentiD,String MyColums) throws SQLException {
		String sb=null;
		if("Province".equals(MyColums)) {
			sb ="SELECT distinct inSheng FROM schoolList";
		}
		if("City".equals(MyColums)) {
			sb ="SELECT distinct inShi FROM schoolList where inSheng='"+parentiD+"'";
		}
		if("Village".equals(MyColums)) {
			sb ="SELECT distinct inXian FROM schoolList where inShi='"+parentiD+"'";
		}
		if("school".equals(MyColums)) {
			sb ="SELECT distinct schoolName FROM schoolList where inXian='"+parentiD+"'";
		}
		PreparedStatement pstmt=null;
		pstmt=con.prepareStatement(sb.toString());
		return pstmt.executeQuery();
	}
}

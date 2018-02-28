package com.java1234.web;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.java1234.dao.EquipmentDao;
import com.java1234.model.EquipmentBorrow;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;

/**
 * Servlet implementation class EqupemtExcelSevlet
 */
/**
 * @ClassName: EqupemtExcelSevlet
 * @Description: 导出 器材借用记录Excel
 * @author: wrwang
 * @date: 2018年2月8日 下午7:54:49
 */
public class EqupemtExcelSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	EquipmentDao equipmentDao=new EquipmentDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String schoolName=request.getParameter("schoolName");
		String equipmentRoom=request.getParameter("EquipmentRoom");
		String equipmentName=request.getParameter("EquipmentName");
		String applyName=request.getParameter("applyName");
		String bbirthday=request.getParameter("bbirthday");
		String ebirthday=request.getParameter("ebirthday");
		String schoolCode=null;
		String xianCode=null;
		/*判断是否为学校用户，则设置学校编号*/
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		if(ExistStaticfinal.ADMINUSER.equals(user.getUserType())||ExistStaticfinal.DOMESTIC.equals(user.getUserType())) {
			schoolCode=user.getSchoolCode();
		}else {
			xianCode=user.getSchoolCode();
		}
		EquipmentBorrow student=new EquipmentBorrow();
		if(schoolName!=null){
			student.setSchoolName(schoolName);
		}
		if(equipmentRoom!=null){
			student.setEquipmentRoom(equipmentRoom);
		}
		if(equipmentName!=null){
			student.setEquipmentName(equipmentName);
		}
		if(applyName!=null){
			student.setApplyName(applyName);
		}
		
		// TODO Auto-generated method stub
		 HSSFWorkbook workbook = new HSSFWorkbook(); 
	     HSSFSheet spreadsheet = workbook.createSheet("employe db");
	     HSSFRow row=spreadsheet.createRow(1);
	     Connection con=null;
	     HSSFCell cell;
	      cell=row.createCell(1);
	      cell.setCellValue("编号");
	      cell=row.createCell(2);
	      cell.setCellValue("学校名称");
	      cell=row.createCell(3);
	      cell.setCellValue("器材室名称");
	      cell=row.createCell(4);
	      cell.setCellValue("器材名称");
	      cell=row.createCell(5);
	      cell.setCellValue("单位");
	      cell=row.createCell(6);
	      cell.setCellValue("数量");
	      cell=row.createCell(7);
	      cell.setCellValue("使用人");
	      cell=row.createCell(8);
	      cell.setCellValue("借用日期");
	      cell=row.createCell(9);
	      cell.setCellValue("归还日期");
	      cell=row.createCell(10);
	      cell.setCellValue("归还情况");
	      cell=row.createCell(11);
	      cell.setCellValue("备注");
	      int i=2;
	      con = null;
	      try {
			con=dbUtil.getCon();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      ResultSet resultSet = null;
		try {
			resultSet = equipmentDao.studentList(con, null,student,bbirthday,ebirthday,schoolCode,xianCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
			while(resultSet.next())
			  {
			     row=spreadsheet.createRow(i);
			     cell=row.createCell(1);
			     cell.setCellValue(resultSet.getInt("id"));
			     cell=row.createCell(2);
			     cell.setCellValue(resultSet.getString("schoolName"));
			     cell=row.createCell(3);
			     cell.setCellValue(resultSet.getString("EquipmentRoom"));
			     cell=row.createCell(4);
			     cell.setCellValue(resultSet.getString("EquipmentName"));
			     cell=row.createCell(5);
			     cell.setCellValue(resultSet.getString("unit"));
			     cell=row.createCell(6);
			     cell.setCellValue(resultSet.getString("num"));
			     cell=row.createCell(7);
			     cell.setCellValue(resultSet.getString("applyName"));
			     cell=row.createCell(8);
			     if(resultSet.getDate("applyDate")!=null) {
			    	 cell.setCellValue(DateFormat.getDateInstance(DateFormat.FULL).format(resultSet.getDate("applyDate")));
			     }else {
			    	 cell.setCellValue("");
			     }
			    
			     cell=row.createCell(9);
			     if(resultSet.getDate("returnDate")!=null) {
			    	 cell.setCellValue(DateFormat.getDateInstance(DateFormat.FULL).format(resultSet.getDate("returnDate")));
			     }else {
			    	 cell.setCellValue("");
			     }
			     cell=row.createCell(10);
			     cell.setCellValue(resultSet.getString("returnInfo"));
			     cell=row.createCell(11);
			     cell.setCellValue(resultSet.getString("memo"));
			     i++;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      response.reset();
	      response.setCharacterEncoding("UTF-8");
	      response.setHeader("Content-disposition","attachment; filename=InspectionExcel.xls");
	      response.setContentType("application/vnd.ms-excel");
	      OutputStream outs=response.getOutputStream();

	      workbook.write(outs);
	      outs.close();
	      workbook.close();  
	      outs.flush(); 
	}

}

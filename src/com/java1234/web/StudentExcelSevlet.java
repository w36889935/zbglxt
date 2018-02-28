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
import com.java1234.dao.StudentDao;
import com.java1234.model.User;
import com.java1234.model.roomUseRecord;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;

/**
 * Servlet implementation class EqupemtExcelSevlet
 */
/**
 * @ClassName: StudentExcelSevlet
 * @Description: 导出 功能室使用记录 Excel
 * @author: wrwang
 * @date: 2018年2月8日 下午8:03:48
 */
public class StudentExcelSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	StudentDao studentDao=new StudentDao();
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
		
		String roomName=request.getParameter("roomName");
		String bbirthday=request.getParameter("bbirthday");
		String ebirthday=request.getParameter("ebirthday");
		String teacher=request.getParameter("teacher");
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
		roomUseRecord student=new roomUseRecord();
		if(teacher!=null){
			student.setTeacher(teacher);
		}
		if(roomName!=null){
			student.setRoomName(roomName);
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
	      cell.setCellValue("功能室名称");
	      cell=row.createCell(3);
	      cell.setCellValue("使用日期");
	      cell=row.createCell(4);
	      cell.setCellValue("第几节课");
	      cell=row.createCell(5);
	      cell.setCellValue("活动主题");
	      cell=row.createCell(6);
	      cell.setCellValue("上课教师");
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
			resultSet = studentDao.studentList(con, null,student,bbirthday,ebirthday,schoolCode,xianCode);
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
			     cell.setCellValue(resultSet.getString("roomName"));
			     cell=row.createCell(3);
			     if(resultSet.getDate("useDate")!=null) {
			    	 cell.setCellValue(DateFormat.getDateInstance(DateFormat.FULL).format(resultSet.getDate("useDate")));
			     }else {
			    	 cell.setCellValue("");
			     }
			     cell=row.createCell(4);
			     cell.setCellValue(resultSet.getString("classNo"));
			     cell=row.createCell(5);
			     cell.setCellValue(resultSet.getString("classInfo"));
			     cell=row.createCell(6);
			     cell.setCellValue(resultSet.getString("teacher"));
			     i++;
			  }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      response.reset();
	      response.setCharacterEncoding("UTF-8");
	      response.setHeader("Content-disposition","attachment; filename=InspectionExcel.xls");
	      response.setContentType("application/msexcel;charset=UTF-8");
	      OutputStream outs=response.getOutputStream();

	      workbook.write(outs);
	      outs.close();
	      workbook.close();  
	      outs.flush(); 
	}

}

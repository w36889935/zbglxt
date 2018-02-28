package com.java1234.web;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.java1234.dao.SchoolteacherDao;
import com.java1234.model.Schoolteacher;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;

/**
 * Servlet implementation class EqupemtExcelSevlet
 */
/**
 * @ClassName: GradeExcelSevlet
 * @Description: 导出学校工作人员 Excel
 * @author: wrwang
 * @date: 2018年2月8日 下午5:43:24
 */
public class GradeExcelSevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	SchoolteacherDao gradeDao=new SchoolteacherDao();
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
		String gradeName=request.getParameter("gradeName");
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

		if(gradeName==null){
			gradeName="";
		}
		Schoolteacher grade=new Schoolteacher();
		grade.setTeacherName(gradeName);
		
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
	      cell.setCellValue("职务");
	      cell=row.createCell(4);
	      cell.setCellValue("姓名");
	      cell=row.createCell(5);
	      cell.setCellValue("性别");
	      cell=row.createCell(6);
	      cell.setCellValue("手机");
	      cell=row.createCell(7);
	      cell.setCellValue("文凭");
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
			resultSet = gradeDao.gradeList(con, null,grade,schoolCode,xianCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	      try {
			while(resultSet.next())
			  {
			     row=spreadsheet.createRow(i);
			     cell=row.createCell(1);
			     cell.setCellValue(resultSet.getInt("ID"));
			     cell=row.createCell(2);
			     cell.setCellValue(resultSet.getString("schoolName"));
			     cell=row.createCell(3);
			     cell.setCellValue(resultSet.getString("workroom"));
			     cell=row.createCell(4);
			     cell.setCellValue(resultSet.getString("teacherName"));
			     cell=row.createCell(5);
			     cell.setCellValue(resultSet.getString("sex"));
			     cell=row.createCell(6);
			     cell.setCellValue(resultSet.getString("telephone"));
			     cell=row.createCell(7);
			     cell.setCellValue(resultSet.getString("wenpin"));
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

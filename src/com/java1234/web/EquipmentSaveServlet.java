package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import com.java1234.dao.EquipmentDao;
import com.java1234.model.EquipmentBorrow;
import com.java1234.model.User;
import com.java1234.util.DateUtil;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

/**
 * @ClassName: EquipmentSaveServlet
 * @Description: 对器材借用记录进行增加和修改
 * @author: wrwang
 * @date: 2018年2月8日 下午3:07:49
 */
public class EquipmentSaveServlet extends HttpServlet{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	EquipmentDao studentDao=new EquipmentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		/*获取学校名称*/
		String schoolName=request.getParameter("schoolName");
		/*获取器材室名称*/
		String equipmentRoom=request.getParameter("EquipmentRoom");
		/*获取器材名称*/
		String equipmentName=request.getParameter("EquipmentName");
		/*获取单位*/
		String unit=request.getParameter("unit");
		/*获取数量*/
		String num=request.getParameter("num");
		/*获取使用人*/
		String applyName=request.getParameter("applyName");
		/*获取使用日期*/
		String applyDate=request.getParameter("applyDate");
		/*归还日期*/
		String returnDate=request.getParameter("returnDate");
		/*获取归还情况*/
		String returnInfo=request.getParameter("returnInfo");
		/*获取备注*/
		String memo=request.getParameter("memo");
		/*获取ID*/
		String stuId=request.getParameter("stuId");
		
		EquipmentBorrow student=null;
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		/*权限验证。只有学校用户才可以对功能室使用记录进行删除操作*/
		if(!ExistStaticfinal.DOMESTIC.equals(user.getUserType())&&!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		try {
			student = new EquipmentBorrow(schoolName,equipmentRoom,equipmentName,unit,num,applyName,DateUtil.formatString(applyDate,"yyyy-MM-dd"),returnInfo, memo);
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(StringUtil.isNotEmpty(stuId)){
			student.setId(Integer.parseInt(stuId));
		}
		if(StringUtil.isNotEmpty(returnDate)) {
			try {
				student.setReturnDate(DateUtil.formatString(returnDate,"yyyy-MM-dd"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			/*判断是ID是否为空，如果是则进行增加记录，否则为修改记录*/
			if(StringUtil.isNotEmpty(stuId)){
				saveNums=studentDao.studentModify(con, student);
			}else{
				saveNums=studentDao.studentAdd(con, student,user.getSchoolCode(),user.getSchoolName());
			}
			/*判断是否修改成功*/
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(response, result);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	
	
}

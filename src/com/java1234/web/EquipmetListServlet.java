package com.java1234.web;


import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.java1234.dao.EquipmentDao;
import com.java1234.model.EquipmentBorrow;
import com.java1234.model.PageBean;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;

/**
 * @ClassName: EquipmetListServlet
 * @Description: 查询器材借用记录
 * @author: wrwang
 * @date: 2018年2月8日 下午3:22:12
 */
public class EquipmetListServlet extends HttpServlet{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	EquipmentDao equipmentDao=new EquipmentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*获取学校名称*/
		String schoolName=request.getParameter("schoolName");
		/*获取器材室名称*/
		String equipmentRoom=request.getParameter("EquipmentRoom");
		/*获取器材名称*/
		String equipmentName=request.getParameter("EquipmentName");
		/*获取使用人*/
		String applyName=request.getParameter("applyName");
		/*归还日期*/
		String bbirthday=request.getParameter("bbirthday");
		/*获取使用人*/
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
		/*获取第几页，每页记录数*/
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			int total=equipmentDao.studentCount(con,student,bbirthday,ebirthday,schoolCode,xianCode);
			/*判断是否为最后一页*/
			if((Integer.parseInt(page)*Integer.parseInt(rows))>total) {
				pageBean.setRows(total%10);
				pageBean.setStart((Integer.parseInt(page)-1)*Integer.parseInt(rows));
			}else {
				pageBean.setStart((pageBean.getPage()-1)*pageBean.getRows());
			}
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(equipmentDao.studentList(con, pageBean,student,bbirthday,ebirthday,schoolCode,xianCode));
			result.put("rows", jsonArray);
			result.put("total", total);
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

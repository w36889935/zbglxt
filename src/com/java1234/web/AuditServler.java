package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.java1234.dao.UserListDao;
import com.java1234.model.PageBean;
import com.java1234.model.User;
import com.java1234.model.roomUseRecord;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class AuditServler
 * 管理部下账号
 */
public class AuditServler extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	UserListDao userdao = new UserListDao();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		System.out.println("12");
		/*判断是否为学校用户，则设置学校编号*/
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		if(!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		/*获取编号*/
		user.getSchoolCode();
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			//总记录数
			int total=userdao.studentCount(con,user.getSchoolCode());
			/*判断是否为最后一页*/
			if((Integer.parseInt(page)*Integer.parseInt(rows))>total) {
				pageBean.setRows(total%10);
				pageBean.setStart((Integer.parseInt(page)-1)*Integer.parseInt(rows));
			}else {
				pageBean.setStart((pageBean.getPage()-1)*pageBean.getRows());
			}
			
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(userdao.userList(con, pageBean,user.getSchoolCode()));
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

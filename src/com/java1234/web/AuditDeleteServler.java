package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import com.java1234.dao.SchoolteacherDao;
import com.java1234.dao.UserListDao;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.ResponseUtil;

/**
 * @ClassName: GradeDeleteServlet
 * @Description: 删除学校工作人员
 * @author: wrwang
 * @date: 2018年2月8日 下午1:34:08
 */
public class AuditDeleteServler extends HttpServlet{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	UserListDao userdao = new UserListDao();
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*权限验证。只有管理员才可以对学生进行删除操作*/
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		if(!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		/*要删除记录的ID*/
		String delIds=request.getParameter("delIds");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			/*json工具对象*/
			JSONObject result=new JSONObject();
			/*删除记录数*/
			int delNums=userdao.studentDelete(con, delIds);
			/*判断是否删除成功*/
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
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

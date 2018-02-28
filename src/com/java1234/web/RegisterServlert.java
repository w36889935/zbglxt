package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.java1234.dao.UserDao;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.StringUtil;

/**
 * @author Mr.wang
 * 用户注册
 */
public class RegisterServlert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	UserDao userDao=new UserDao();
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
		
		request.setCharacterEncoding("utf-8");
		
		/*获取用户*/
		String userName = request.getParameter("userName");
		/*真实姓名*/
		String realName = request.getParameter("realName");
		/*获取密码*/
		String password = request.getParameter("password");
		/*获取密保问题*/
		String passQues = request.getParameter("passQues");
		/*获取密码答案*/
		String quesAnswer = request.getParameter("quesAnswer");
		/*获取手机号码*/
		String phoneNumber = request.getParameter("phoneNumber");
		/*获取邮箱*/
		String passEmail = request.getParameter("passEmail");
		/*获取学校名*/
		String schoollist = request.getParameter("schoollist");
		
		
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(realName)||StringUtil.isEmpty(password)||StringUtil.isEmpty(passQues)||StringUtil.isEmpty(quesAnswer)||StringUtil.isEmpty(phoneNumber)||StringUtil.isEmpty(passEmail)||StringUtil.isEmpty(schoollist)) {
			request.setAttribute("error", "注册失败！必填项不能为空");
			// 服务器跳转
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		User  user = new  User(userName,password,schoollist,realName,passQues,quesAnswer,phoneNumber,passEmail);
		
		Connection con=null;
		try {
			con=dbUtil.getCon();
			int currentUser=userDao.userAdd(con,user);
			if(currentUser!=1){
				request.setAttribute("error", "注册失败！");
				// 服务器跳转
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}else{
				// 客户端跳转
				response.sendRedirect("index.jsp");
				return;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
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

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
 * �û�ע��
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
		
		/*��ȡ�û�*/
		String userName = request.getParameter("userName");
		/*��ʵ����*/
		String realName = request.getParameter("realName");
		/*��ȡ����*/
		String password = request.getParameter("password");
		/*��ȡ�ܱ�����*/
		String passQues = request.getParameter("passQues");
		/*��ȡ�����*/
		String quesAnswer = request.getParameter("quesAnswer");
		/*��ȡ�ֻ�����*/
		String phoneNumber = request.getParameter("phoneNumber");
		/*��ȡ����*/
		String passEmail = request.getParameter("passEmail");
		/*��ȡѧУ��*/
		String schoollist = request.getParameter("schoollist");
		
		
		if(StringUtil.isEmpty(userName)||StringUtil.isEmpty(realName)||StringUtil.isEmpty(password)||StringUtil.isEmpty(passQues)||StringUtil.isEmpty(quesAnswer)||StringUtil.isEmpty(phoneNumber)||StringUtil.isEmpty(passEmail)||StringUtil.isEmpty(schoollist)) {
			request.setAttribute("error", "ע��ʧ�ܣ��������Ϊ��");
			// ��������ת
			request.getRequestDispatcher("index.jsp").forward(request, response);
			return;
		}
		
		User  user = new  User(userName,password,schoollist,realName,passQues,quesAnswer,phoneNumber,passEmail);
		
		Connection con=null;
		try {
			con=dbUtil.getCon();
			int currentUser=userDao.userAdd(con,user);
			if(currentUser!=1){
				request.setAttribute("error", "ע��ʧ�ܣ�");
				// ��������ת
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}else{
				// �ͻ�����ת
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

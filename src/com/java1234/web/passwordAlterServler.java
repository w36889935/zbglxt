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
import com.java1234.model.Schoolteacher;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

/**
 * @ClassName: GradeSaveServlet
 * @Description: �û������ȡ
 * @author: wrwang
 * @date: 2018��2��8�� ����5:56:36
 */
public class passwordAlterServler extends HttpServlet{
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
		request.setCharacterEncoding("utf-8");
		/*��þ�����*/
		String past=request.getParameter("past")+".0";
		/*���������*/
		String passwrod=request.getParameter("passwrod");
		HttpSession session=request.getSession();
		User user = (User) session.getAttribute("currentUser");
		User users = new User();
		users.setUserName(user.getUserName());
		users.setPassword(passwrod);
		
		
		System.out.println("=="+past.equals(user.getPassword())+"="+past+"="+user.getPassword()+"==="+user.getUserName());
		
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			if(past.equals(user.getPassword())) {
				System.out.println("=0=");
				if(ExistStaticfinal.ADMINUSER.equals(user.getUserType())||ExistStaticfinal.DOMESTIC.equals(user.getUserType())) {
					saveNums=userdao.alterUser(con, users, true);
				}else {
					saveNums=userdao.alterUser(con, users, false);
				}
					if(saveNums==1) {
						request.setAttribute("error", "�����Ѹ��������µ�¼");
						request.getRequestDispatcher("passwordAlter.jsp").forward(request, response);
					}else {
						request.setAttribute("error", "�����޸�ʧ��");
						request.getRequestDispatcher("passwordAlter.jsp").forward(request, response);
					}
			}else {
						request.setAttribute("error", "���������,�޸�ʧ��");
						request.getRequestDispatcher("passwordAlter.jsp").forward(request, response);
			}
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

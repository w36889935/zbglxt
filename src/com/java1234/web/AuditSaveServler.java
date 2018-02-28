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
 * @Description: ��ѧУ�û���Ϣ���и��ĺ����
 * @author: wrwang
 * @date: 2018��2��8�� ����5:56:36
 */
public class AuditSaveServler extends HttpServlet{
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
		/*���ְ��*/
		String syyqgl=request.getParameter("syyqgl");
		/*�������*/
		String syjxgl=request.getParameter("syjxgl");
		/*����Ա�*/
		String ytmgl=request.getParameter("ytmgl");
		/*����ֻ���*/
		String xxjbxx=request.getParameter("xxjbxx");
		/*�����ƾ*/
		String canUse=request.getParameter("canUse");
		String id=request.getParameter("stuId");
		
		System.out.println(syyqgl+"="+syjxgl+"="+ytmgl+"="+xxjbxx+"="+canUse);
		
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		/*Ȩ����֤��ֻ�й���Ա�ſ��Զ���Ա������¼����ɾ������*/
		if(!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		User users=new User();
		if("1".equals(syyqgl)||"����".equals(syyqgl)) {
			users.setSyyqgl(true);
		}else {
			users.setSyyqgl(false);
		}
		if("1".equals(syjxgl)||"����".equals(syjxgl)) {
			users.setSyjxgl(true);
		}else {
			users.setSyjxgl(false);
		}
		if("1".equals(ytmgl)||"����".equals(ytmgl)) {
			users.setYtmgl(true);
		}else {
			users.setYtmgl(false);
		}
		if("1".equals(xxjbxx)||"����".equals(xxjbxx)) {
			users.setXxjbxx(true);
		}else {
			users.setXxjbxx(false);
		}
		if("1".equals(canUse)||"����".equals(canUse)) {
			users.setCanUse(true);
		}else {
			users.setCanUse(false);
		}
		users.setId(Integer.parseInt(id));
		
		
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			saveNums=userdao.studentModify(con,users);
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "����ʧ��");
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

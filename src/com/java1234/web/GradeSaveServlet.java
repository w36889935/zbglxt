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
public class GradeSaveServlet extends HttpServlet{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	SchoolteacherDao gradeDao=new SchoolteacherDao();
	
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
		String workroom=request.getParameter("workroom");
		/*�������*/
		String name=request.getParameter("name");
		/*����Ա�*/
		String sex=request.getParameter("sex");
		/*����ֻ���*/
		String telephone=request.getParameter("telephone");
		/*�����ƾ*/
		String wenpin=request.getParameter("wenpin");
		String id=request.getParameter("id");
		
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		/*Ȩ����֤��ֻ�й���Ա�ſ��Զ���Ա������¼����ɾ������*/
		if(!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		
		Schoolteacher grade=new Schoolteacher(user.getSchoolName(),workroom,name,sex,telephone,wenpin);
		if(StringUtil.isNotEmpty(id)){
			grade.setID(Integer.parseInt(id));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			/*�ж���ID�Ƿ�Ϊ�գ��������������Ӽ�¼������Ϊ�޸ļ�¼*/
			if(StringUtil.isNotEmpty(id)){
				saveNums=gradeDao.gradeModify(con, grade);
			}else{
				saveNums=gradeDao.gradeAdd(con, grade,user.getSchoolCode());
			}
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

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
import com.java1234.dao.SchoolteacherDao;
import com.java1234.model.PageBean;
import com.java1234.model.Schoolteacher;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;

/**
 * @ClassName: GradeListServlet
 * @Description: ��ȡѧУ������Ա��Ϣ
 * @author: wrwang
 * @date: 2018��2��8�� ����5:44:15
 */
public class GradeListServlet extends HttpServlet{
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
		System.out.println("===");
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		/*�������*/
		String gradeName=request.getParameter("gradeName");
		/*���ѧУ��*/
		String schoolName=request.getParameter("schoolName");
		/*���ְ��*/
		String workroom=request.getParameter("workroom");
		/*ѧУ���*/
		String schoolCode=null;
		/*���û����*/
		String xianCode=null;
		/*�ж��Ƿ�ΪѧУ�û���������ѧУ���*/
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
		if(schoolName==null){
			schoolName="";
		}
		if(workroom==null){
			workroom="";
		}
		Schoolteacher grade=new Schoolteacher();
		grade.setTeacherName(gradeName);
		grade.setSchoolName(schoolName);
		grade.setWorkroom(workroom);
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			/*��ȡ�ܼ�¼��*/
			int total=gradeDao.gradeCount(con,grade,schoolCode,xianCode);
			/*�ж��Ƿ�Ϊ���һҳ*/
			if((Integer.parseInt(page)*Integer.parseInt(rows))>total) {
				pageBean.setRows(total%10);
				pageBean.setStart((Integer.parseInt(page)-1)*Integer.parseInt(rows));
			}else {
				pageBean.setStart((pageBean.getPage()-1)*pageBean.getRows());
			}	
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(gradeDao.gradeList(con, pageBean,grade,schoolCode,xianCode));	
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

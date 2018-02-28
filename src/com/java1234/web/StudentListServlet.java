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
import com.java1234.dao.StudentDao;
import com.java1234.model.roomUseRecord;
import com.java1234.model.PageBean;
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.JsonUtil;
import com.java1234.util.ResponseUtil;

/**
 * @ClassName: StudentListServlet
 * @Description: 
 * @author: ������ʹ�ü�¼��ѯ
 * @date: 2018��2��8�� ����7:41:25
 */
public class StudentListServlet extends HttpServlet{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	StudentDao studentDao=new StudentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*��ȡѧУ����*/
		String schoolName=request.getParameter("schoolName");
		/*��ȡ����������*/
		String roomName=request.getParameter("roomName");
		/*��ȡ��ʼʱ��*/
		String bbirthday=request.getParameter("bbirthday");
		/*��ȡ����ʱ��*/
		String ebirthday=request.getParameter("ebirthday");
		/*��ȡ�Ͽν�ʦ*/
		String teacher=request.getParameter("teacher");
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
		
		roomUseRecord student=new roomUseRecord();
		if(schoolName!=null){
			student.setSchoolName(schoolName);
		}
		if(teacher!=null){
			student.setTeacher(teacher);
		}
		if(roomName!=null){
			student.setRoomName(roomName);
		}
		
		String page=request.getParameter("page");
		String rows=request.getParameter("rows");
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		Connection con=null;
		try{
			con=dbUtil.getCon();
			JSONObject result=new JSONObject();
			//�ܼ�¼��
			int total=studentDao.studentCount(con,student,bbirthday,ebirthday,schoolCode,xianCode);
			/*�ж��Ƿ�Ϊ���һҳ*/
			if((Integer.parseInt(page)*Integer.parseInt(rows))>total) {
				pageBean.setRows(total%10);
				pageBean.setStart((Integer.parseInt(page)-1)*Integer.parseInt(rows));
			}else {
				pageBean.setStart((pageBean.getPage()-1)*pageBean.getRows());
			}
			JSONArray jsonArray=JsonUtil.formatRsToJsonArray(studentDao.studentList(con, pageBean,student,bbirthday,ebirthday,schoolCode,xianCode));
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

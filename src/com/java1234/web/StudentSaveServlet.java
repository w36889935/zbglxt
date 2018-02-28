package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import com.java1234.dao.StudentDao;
import com.java1234.model.User;
import com.java1234.model.roomUseRecord;
import com.java1234.util.DateUtil;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

/**
 * @ClassName: StudentSaveServlet
 * @Description: ������ʹ�ü�¼�������Ӻ��޸�
 * @author: wrwang
 * @date: 2018��2��8�� ����7:48:36
 */
public class StudentSaveServlet extends HttpServlet{
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
		request.setCharacterEncoding("utf-8");
		/*��ȡ����������*/
		String roomName=request.getParameter("roomName");
		/*��ȡʹ������*/
		String userDate=request.getParameter("userDate");
		/*��ȡ�ڼ��ڿ�*/
		String classNo=request.getParameter("classNo");
		/*��ȡ�����*/
		String classInfo=request.getParameter("classInfo");
		/*��ȡ���ʦ*/
		String teacher=request.getParameter("teacher");
		/*��ȡID*/
		String stuId=request.getParameter("stuId");
		roomUseRecord student=null;
		
		
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		/*Ȩ����֤��ֻ��ѧУ�û��ſ��ԶԹ�����ʹ�ü�¼����ɾ������*/
		if(!ExistStaticfinal.DOMESTIC.equals(user.getUserType())&&!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		try {
			student = new roomUseRecord(roomName,DateUtil.formatString(userDate,"yyyy-MM-dd"), classNo,
					classInfo, teacher);
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(StringUtil.isNotEmpty(stuId)){
			student.setId(Integer.parseInt(stuId));
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			if(StringUtil.isNotEmpty(stuId)){
				saveNums=studentDao.studentModify(con, student);
			}else{
				saveNums=studentDao.studentAdd(con, student,user.getSchoolCode(),user.getSchoolName());
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

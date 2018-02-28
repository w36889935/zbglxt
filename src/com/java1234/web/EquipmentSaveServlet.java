package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import com.java1234.dao.EquipmentDao;
import com.java1234.model.EquipmentBorrow;
import com.java1234.model.User;
import com.java1234.util.DateUtil;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.ResponseUtil;
import com.java1234.util.StringUtil;

/**
 * @ClassName: EquipmentSaveServlet
 * @Description: �����Ľ��ü�¼�������Ӻ��޸�
 * @author: wrwang
 * @date: 2018��2��8�� ����3:07:49
 */
public class EquipmentSaveServlet extends HttpServlet{
	/**
	 * @fieldName: serialVersionUID
	 * @fieldType: long
	 * @Description: TODO
	 */
	private static final long serialVersionUID = 1L;
	DbUtil dbUtil=new DbUtil();
	EquipmentDao studentDao=new EquipmentDao();
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		/*��ȡѧУ����*/
		String schoolName=request.getParameter("schoolName");
		/*��ȡ����������*/
		String equipmentRoom=request.getParameter("EquipmentRoom");
		/*��ȡ��������*/
		String equipmentName=request.getParameter("EquipmentName");
		/*��ȡ��λ*/
		String unit=request.getParameter("unit");
		/*��ȡ����*/
		String num=request.getParameter("num");
		/*��ȡʹ����*/
		String applyName=request.getParameter("applyName");
		/*��ȡʹ������*/
		String applyDate=request.getParameter("applyDate");
		/*�黹����*/
		String returnDate=request.getParameter("returnDate");
		/*��ȡ�黹���*/
		String returnInfo=request.getParameter("returnInfo");
		/*��ȡ��ע*/
		String memo=request.getParameter("memo");
		/*��ȡID*/
		String stuId=request.getParameter("stuId");
		
		EquipmentBorrow student=null;
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		/*Ȩ����֤��ֻ��ѧУ�û��ſ��ԶԹ�����ʹ�ü�¼����ɾ������*/
		if(!ExistStaticfinal.DOMESTIC.equals(user.getUserType())&&!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		try {
			student = new EquipmentBorrow(schoolName,equipmentRoom,equipmentName,unit,num,applyName,DateUtil.formatString(applyDate,"yyyy-MM-dd"),returnInfo, memo);
		}  catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(StringUtil.isNotEmpty(stuId)){
			student.setId(Integer.parseInt(stuId));
		}
		if(StringUtil.isNotEmpty(returnDate)) {
			try {
				student.setReturnDate(DateUtil.formatString(returnDate,"yyyy-MM-dd"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		Connection con=null;
		try{
			con=dbUtil.getCon();
			int saveNums=0;
			JSONObject result=new JSONObject();
			/*�ж���ID�Ƿ�Ϊ�գ��������������Ӽ�¼������Ϊ�޸ļ�¼*/
			if(StringUtil.isNotEmpty(stuId)){
				saveNums=studentDao.studentModify(con, student);
			}else{
				saveNums=studentDao.studentAdd(con, student,user.getSchoolCode(),user.getSchoolName());
			}
			/*�ж��Ƿ��޸ĳɹ�*/
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

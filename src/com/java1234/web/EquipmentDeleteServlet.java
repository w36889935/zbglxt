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
import com.java1234.model.User;
import com.java1234.util.DbUtil;
import com.java1234.util.ExistStaticfinal;
import com.java1234.util.ResponseUtil;

/**
 * @ClassName: EquipmentDeleteServlet
 * @Description: �޸����Ľ��ü�¼
 * @author: wrwang
 * @date: 2018��2��8�� ����1:27:52
 */
public class EquipmentDeleteServlet extends HttpServlet{
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
		/*Ȩ����֤��ֻ��ѧУ�û��ſ��ԶԹ�����ʹ�ü�¼����ɾ������*/
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		if(!ExistStaticfinal.DOMESTIC.equals(user.getUserType())&&!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		/*Ҫɾ����¼��ID*/
		String delIds=request.getParameter("delIds");
		Connection con=null;
		try{
			con=dbUtil.getCon();
			/*json���߶���*/
			JSONObject result=new JSONObject();
			/*ɾ����¼��*/
			int delNums=studentDao.studentDelete(con, delIds);
			/*�ж��Ƿ�ɾ���ɹ�*/
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "ɾ��ʧ��");
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

package com.java1234.web;

import java.io.IOException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

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
 * @ClassName: EquipmentreturnServlet
 * @Description: �黹����
 * @author: wrwang
 * @date: 2018��2��8�� ����3:04:10
 */
public class EquipmentreturnServlet extends HttpServlet{
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
		/*Ȩ����֤��ֻ��ѧУ�û��ſ��ԶԹ�����ʹ�ü�¼����ɾ������,������ר��¼����*/
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		if(!ExistStaticfinal.DOMESTIC.equals(user.getUserType())&&!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		/*���Ҫ�黹���ļ�¼��ID*/
		String delIds=request.getParameter("delIds");
		Connection con=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//�������ڸ�ʽ
		try{
			con=dbUtil.getCon();
			/*ʵ����JSONת������*/
			JSONObject result=new JSONObject();
			/*�޸����Ĺ黹��¼*/
			int delNums=studentDao.studentreturn(con, delIds,df.format(new Date()));
			/*�ж��Ƿ�黹�ɹ�*/
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "�黹ʧ��");
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

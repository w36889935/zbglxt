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
 * @Description: 归还器材
 * @author: wrwang
 * @date: 2018年2月8日 下午3:04:10
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
		/*权限验证。只有学校用户才可以对功能室使用记录进行删除操作,否则跳专登录界面*/
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		if(!ExistStaticfinal.DOMESTIC.equals(user.getUserType())&&!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		/*获得要归还器材记录的ID*/
		String delIds=request.getParameter("delIds");
		Connection con=null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
		try{
			con=dbUtil.getCon();
			/*实例化JSON转换工具*/
			JSONObject result=new JSONObject();
			/*修改器材归还记录*/
			int delNums=studentDao.studentreturn(con, delIds,df.format(new Date()));
			/*判断是否归还成功*/
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "归还失败");
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

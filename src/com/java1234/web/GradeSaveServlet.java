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
 * @Description: 对学校用户信息进行更改和添加
 * @author: wrwang
 * @date: 2018年2月8日 下午5:56:36
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
		/*获得职务*/
		String workroom=request.getParameter("workroom");
		/*获得姓名*/
		String name=request.getParameter("name");
		/*获得性别*/
		String sex=request.getParameter("sex");
		/*获得手机号*/
		String telephone=request.getParameter("telephone");
		/*获得文凭*/
		String wenpin=request.getParameter("wenpin");
		String id=request.getParameter("id");
		
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		/*权限验证。只有管理员才可以对人员激励记录进行删除操作*/
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
			/*判断是ID是否为空，如果是则进行增加记录，否则为修改记录*/
			if(StringUtil.isNotEmpty(id)){
				saveNums=gradeDao.gradeModify(con, grade);
			}else{
				saveNums=gradeDao.gradeAdd(con, grade,user.getSchoolCode());
			}
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
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

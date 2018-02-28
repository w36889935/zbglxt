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
 * @Description: 对学校用户信息进行更改和添加
 * @author: wrwang
 * @date: 2018年2月8日 下午5:56:36
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
		/*获得职务*/
		String syyqgl=request.getParameter("syyqgl");
		/*获得姓名*/
		String syjxgl=request.getParameter("syjxgl");
		/*获得性别*/
		String ytmgl=request.getParameter("ytmgl");
		/*获得手机号*/
		String xxjbxx=request.getParameter("xxjbxx");
		/*获得文凭*/
		String canUse=request.getParameter("canUse");
		String id=request.getParameter("stuId");
		
		System.out.println(syyqgl+"="+syjxgl+"="+ytmgl+"="+xxjbxx+"="+canUse);
		
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		/*权限验证。只有管理员才可以对人员激励记录进行删除操作*/
		if(!ExistStaticfinal.ADMINUSER.equals(user.getUserType())) {
			return;
		}
		User users=new User();
		if("1".equals(syyqgl)||"可用".equals(syyqgl)) {
			users.setSyyqgl(true);
		}else {
			users.setSyyqgl(false);
		}
		if("1".equals(syjxgl)||"可用".equals(syjxgl)) {
			users.setSyjxgl(true);
		}else {
			users.setSyjxgl(false);
		}
		if("1".equals(ytmgl)||"可用".equals(ytmgl)) {
			users.setYtmgl(true);
		}else {
			users.setYtmgl(false);
		}
		if("1".equals(xxjbxx)||"可用".equals(xxjbxx)) {
			users.setXxjbxx(true);
		}else {
			users.setXxjbxx(false);
		}
		if("1".equals(canUse)||"可用".equals(canUse)) {
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

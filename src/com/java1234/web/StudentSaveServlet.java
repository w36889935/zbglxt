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
 * @Description: 功能室使用记录进行增加和修改
 * @author: wrwang
 * @date: 2018年2月8日 下午7:48:36
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
		/*获取功能室名称*/
		String roomName=request.getParameter("roomName");
		/*获取使用日期*/
		String userDate=request.getParameter("userDate");
		/*获取第几节课*/
		String classNo=request.getParameter("classNo");
		/*获取活动主题*/
		String classInfo=request.getParameter("classInfo");
		/*获取活动教师*/
		String teacher=request.getParameter("teacher");
		/*获取ID*/
		String stuId=request.getParameter("stuId");
		roomUseRecord student=null;
		
		
		HttpSession session=request.getSession();
		User user= (User) session.getAttribute("currentUser");
		/*权限验证。只有学校用户才可以对功能室使用记录进行删除操作*/
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

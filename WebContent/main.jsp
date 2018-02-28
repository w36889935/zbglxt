<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生信息管理系统主界面</title>
<%
	// 权限验证
	if(session.getAttribute("currentUser")==null){
		response.sendRedirect("index.jsp");
		return;
	}
%>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	$(function(){
		// 数据
		var treeData=[
			<c:if test="${currentUser.ytmgl }">
			{
			text:"音体美管理",
			children:[{
				text:"音体美器材管理",
				attributes:{
					url:"equipmentBorrow.jsp"
				}
			},{
				text:"功能室使用记录",
				attributes:{
					url:"studentInfoManage.jsp"
				}
			}]
		},
		</c:if>
		<c:if test="${currentUser.xxjbxx }">
		{
			text:"学校基本信息",
			children:[{
				text:"学校工作人员",
				attributes:{
					url:"gradeInfoManage.jsp"
				}
			}]
		},
		</c:if>
		<c:if test="${currentUser.syyqgl }">
		{
			text:"实验仪器管理",
			children:[{
				text:"仪器入库",
				attributes:{
					url:""
				}
			}]
		},
		</c:if>
		<c:if test="${currentUser.syjxgl }">
		{
			text:"实验教学管理",
			children:[{
				text:"实验教学计划",
				attributes:{
					url:""
				}
			},{
				text:"实验申请单",
				attributes:{
					url:""
				}
			}]
		},
		</c:if>
		{
			text:"账号管理",
			children:[{
				text:"修改密码",
				attributes:{
					url:"passwordAlter.jsp"
				}
			}
			<c:if test="${currentUser.userType eq '学校管理员'}">
			,{
				text:"账号管理",
				attributes:{
					url:"userList.jsp"
				}
			}
			</c:if>
			]
		}
		];
		
		// 实例化树菜单
		$("#tree").tree({
			data:treeData,
			lines:true,
			onClick:function(node){
				if(node.attributes){
					openTab(node.text,node.attributes.url);
				}
			}
		});
		
		// 新增Tab
		function openTab(text,url){
			if($("#tabs").tabs('exists',text)){
				$("#tabs").tabs('select',text);
			}else{
				var content="<iframe frameborder='0' scrolling='auto' style='width:100%;height:100%' src="+url+"></iframe>";
				$("#tabs").tabs('add',{
					title:text,
					closable:true,
					content:content
				});
			}
		}
	});
</script>
</head>
<body class="easyui-layout">
	<div region="north" style="height: 80px;background-color: #E0EDFF">
		<div align="left" style="width: 50%;float: left;"><img src="images/main.png"></div>
		<div  style="padding-top: 50px;padding-right: 10px;margin-right:10px; ">
			当前用户：&nbsp;
			<font color="red" >[${currentUser.userName }] &nbsp;&nbsp;
				<c:choose>
				     <c:when test="${currentUser.userType eq '学校管理员' || currentUser.userType eq '普通用户'}">
				         [${currentUser.userType }]
				         &nbsp;&nbsp;[${currentUser.schoolName}]
				     </c:when>
				     <c:otherwise>
				           [县级用户]
				           &nbsp;&nbsp;[${currentUser.userType}]
				     </c:otherwise>
				</c:choose>
			</font>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href="index.jsp" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">退出系统</a>
			</div>
	</div>
	<div region="center">
		<div class="easyui-tabs" fit="true" border="false" id="tabs">
			<div title="首页" >
				<div align="center" style="padding-top: 100px;"><font color="red" size="10">欢迎使用</font></div>
			</div>
		</div>
	</div>
	<div region="west" style="width: 150px;" title="导航菜单" split="true">
		<ul id="tree"></ul>
	</div>
	<div region="south" style="height: 25px;" align="center">版权所有&nbsp;&nbsp;<a href="">刘龙庭</a></div>
</body>
</html>
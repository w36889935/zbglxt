<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>功能室管理</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	
	function deleteStudent(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("studentDelete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示',result.errorMsg);
					}
				},"json");
			}
		});
	}

	function searchStudent(){
		$('#dg').datagrid('load',{
			roomName:$('#roomName').val(),
			teacher:$('#teacher').val(),
			bbirthday:$('#s_bbirthday').datebox("getValue"),
			ebirthday:$('#s_ebirthday').datebox("getValue"),
			schoolName:$('#schoolName').val()
		});
	}
	
	function ecxlStudent(){
	 	 window.location.href="${pageContext.request.contextPath }/StudentExcelSevlet?roomName="+$('#roomName').val()+"&teacher="+$('#teacher').val()+"&bbirthday="+$('#s_bbirthday').datebox("getValue")+"&ebirthday="+$('#s_ebirthday').datebox("getValue");
	}
	function openStudentAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加信息");
		url="studentSave";
	}
	
	function saveStudent(){
		$("#fm").form("submit",{
			url:url,
			success:function(result){
				if(result.errorMsg){
					$.messager.alert("系统提示",result.errorMsg);
					return;
				}else{
					$.messager.alert("系统提示","保存成功");
					resetValue();
					$("#dlg").dialog("close");
					$("#dg").datagrid("reload");
				}
			}
		});
	}
	
	function resetValue(){
		$("#stuNo").val("");
		$("#stuName").val("");
		$("#sex").combobox("setValue","");
		$("#birthday").datebox("setValue","");
		$("#gradeId").combobox("setValue","");
		$("#email").val("");
		$("#stuDesc").val("");
	}
	
	function closeStudentDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function openStudentModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑学生信息");
		$("#fm").form("load",row);
		url="studentSave?stuId="+row.id;
	}
</script>
</head>
<body style="margin: 5px;">
	<table id="dg" title="学生信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="studentList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="50" align="center">编号</th>
				<c:if test="${currentUser.userType != '学校管理员' && currentUser.userType != '普通用户'}">
					<th field="schoolName" width="100" align="center">学校名称</th>
			    </c:if>	
				<th field="roomName" width="100" align="center">功能室名称</th>
				<th field="useDate" width="100" align="center">使用日期</th>
				<th field="classNo" width="100" align="center">第几节课</th>
				<th field="classInfo" width="100" align="center">活动主题</th>
				<th field="teacher" width="100" align="center">上课教师</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
		     <c:if test="${currentUser.userType eq '学校管理员' || currentUser.userType eq '普通用户'}">
		       <a href="javascript:openStudentAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
				<a href="javascript:openStudentModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				<a href="javascript:deleteStudent()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		     </c:if>	
		     	<a href="javascript:ecxlStudent()" class="easyui-linkbutton" iconCls="icon-print" plain="true">下载Exce</a>
		</div>
		<div>
		<c:if test="${currentUser.userType != '学校管理员' && currentUser.userType != '普通用户'}">
		&nbsp;学校名称：&nbsp;<input type="text" name="schoolName" id="schoolName" size="10"/>
		</c:if>	
		&nbsp;功能室名称：&nbsp;<input type="text" name="roomName" id="roomName" size="10"/>
		&nbsp;上课老师：&nbsp;<input type="text" name="teacher" id="teacher" size="10"/>
		&nbsp;使用日期：&nbsp;<input class="easyui-datebox" name="s_bbirthday" id="s_bbirthday" editable="false" size="10"/>-><input class="easyui-datebox" name="s_ebirthday" id="s_ebirthday" editable="false" size="10"/>
		<a href="javascript:searchStudent()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 350px;height: 300px;padding: 20px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>&nbsp;&nbsp;&nbsp;</td>
					<td>功能室名称：</td>
					<td><input type="text" name="roomName" id="roomName" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td></td>
					<td>使用日期：</td>
					<td><input class="easyui-datebox" name="userDate" id="userDate" required="true" editable="false" /></td>
				</tr>
				<tr>
					<td></td>
					<td>第几节课：</td>
					<td><input type="text" name="classNo" id="classNo" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td></td>
					<td>活动主题：</td>
					<td><input type="text" name="classInfo" id="classInfo" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td></td>
					<td>上课教师：</td>
					<td><input type="text" name="teacher" id="teacher" class="easyui-validatebox" required="true"/></td>
					
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveStudent()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeStudentDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>
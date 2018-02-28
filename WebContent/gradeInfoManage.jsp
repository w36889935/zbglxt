<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>人员信息管理</title>
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	var url;
	
	function searchGrade(){
		$('#dg').datagrid('load',{
			gradeName:$('#s_gradeName').val(),
			schoolName:$('#s_schoolName').val(),
			workroom:$('#s_workroom').combobox("getValue")
		});
	}
	
	function ecxlStudent(){
	 	 window.location.href="${pageContext.request.contextPath }/GradeExcelSevlet?gradeName="+$('#s_gradeName').val();
	}
	
	function deleteGrade(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要删除的数据！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].ID);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("gradeDelete",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功删除<font color=red>"+result.delNums+"</font>条数据！");
						$("#dg").datagrid("reload");
					}else{
						$.messager.alert('系统提示','<font color=red>'+selectedRows[result.errorIndex].gradeName+'</font>'+result.errorMsg);
					}
				},"json");
			}
		});
	}
	
	
	function openGradeAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加人员信息");
		url="gradeSave";
	}
	
	function openGradeModifyDialog(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length!=1){
			$.messager.alert("系统提示","请选择一条要编辑的数据！");
			return;
		}
		var row=selectedRows[0];
		$("#dlg").dialog("open").dialog("setTitle","编辑人员信息");
		$("#fm").form("load",row);
		url="gradeSave?id="+row.ID;
	}
	
	function closeGradeDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	
	function resetValue(){
		$("#gradeName").val("");
		$("#gradeDesc").val("");
	}
	
	
	function saveGrade(){
		$("#fm").form("submit",{
			url:url,
			onSubmit:function(){
				return $(this).form("validate");
			},
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
	
	
</script>
</head>
<body style="margin: 5px;">
	<table id="dg" title="人员信息管理" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="gradeList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="ID" width="115">编号</th>
				<th field="schoolName" width="115">学校</th>
				<th field="workroom" width="115">职务</th>
				<th field="teacherName" width="115">姓名</th>
				<th field="sex" width="115">性别</th>
				<th field="telephone" width="115">手机</th>
				<th field="wenpin" width="115">文凭</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>	
			 
			 <c:if test="${currentUser.userType eq '学校管理员' }">
		       <a href="javascript:openGradeAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
				<a href="javascript:openGradeModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				<a href="javascript:deleteGrade()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		     </c:if>
		     	<a href="javascript:ecxlStudent()" class="easyui-linkbutton" iconCls="icon-print" plain="true">下载Exce</a>
		</div>
		<div>
		&nbsp;学校名称：&nbsp;<input type="text" name="s_schoolName" id="s_schoolName" size="10"/>
		&nbsp;姓名：&nbsp;<input type="text" name="s_gradeName" id="s_gradeName" size="10"/>
		&nbsp;职务：&nbsp;<input class="easyui-combobox" id="s_workroom" name="s_workroom" size="15" data-options="panelHeight:'auto',editable:false,valueField:'workroom',textField:'workroom',url:'gradeComboList'"/>
		<a href="javascript:searchGrade()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a></div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 400px;height: 280px;padding: 30px 60px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table>
				<tr>
					<td>职务：</td>
					<td><input type="text" name="workroom" id="workroom" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>姓名：</td>
					<td><input type="text" name="name" id="name" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>性别：</td>
					<td><select class="easyui-combobox" id="sex" name="sex" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="男">男</option>
						<option value="女">女</option>
					</select></td>
				</tr>
				<tr>
					<td>手机：</td>
					<td><input type="text" name="telephone" id="telephone" class="easyui-validatebox" required="true"/></td>
				</tr>
				<tr>
					<td>文凭：</td>
					<td><input type="text" name="wenpin" id="wenpin" class="easyui-validatebox" required="true"/></td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveGrade()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeGradeDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>
</html>
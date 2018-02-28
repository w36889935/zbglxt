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
			strIds.push(selectedRows[i].Id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要删掉这<font color=red>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.post("auditDeleteServler",{delIds:ids},function(result){
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
			syyqgl:$('#syyqgl').combobox("getValue"),
			syjxgl:$('#syjxgl').combobox("getValue"),
			ytmgl:$('#ytmgl').combobox("getValue"),
			xxjbxx:$('#xxjbxx').combobox("getValue"),
			canUse:$('#canUse').combobox("getValue"),
		});
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
		$syyqgl:$('#syyqgl').combobox("setValue","");
		$syjxgl:$('#syjxgl').combobox("setValue","");
		$ytmgl:$('#ytmgl').combobox("setValue","");
		$xxjbxx:$('#xxjbxx').combobox("setValue","");
		$canUse:$('#canUse').combobox("setValue","");
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
		$("#dlg").dialog("open").dialog("setTitle","编辑用户信息");
		$("#fm").form("load",row);
		url="auditSaveServler?stuId="+row.Id;
	}
</script>
</head>
<body style="margin: 5px;">
	<table id="dg" title="学生信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="AuditServler" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="Id" width="50" align="center">编号</th>
				<th field="userName" width="100" align="center">用户名</th>
				<th field="password" width="100" align="center">密码</th>
				<th field="realName" width="100" align="center">真实姓名</th>
				<th field="phoneNumber" width="100" align="center">手机号</th>
				<th field="syyqgl" width="100" align="center">实验仪器管理</th>
				<th field="ytmgl" width="100" align="center">音体美管理</th>
				<th field="syjxgl" width="100" align="center">实验教学管理</th>
				<th field="xxjbxx" width="100" align="center">学校基本信息管理</th>
				<th field="canUse" width="100" align="center">审核状态</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
		     <c:if test="${currentUser.userType eq '学校管理员' || currentUser.userType eq '普通用户'}">
				<a href="javascript:openStudentModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				<a href="javascript:deleteStudent()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
		     </c:if>	
		</div>
	</div>
	
	<div id="dlg" class="easyui-dialog" style="width: 350px;height: 300px;padding: 20px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>&nbsp;&nbsp;&nbsp;</td>
					<td>实验仪器管理</td>
					<td><select class="easyui-combobox" id="syyqgl" name="syyqgl" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="1">可用</option>
						<option value="0">不可用</option>
					</select></td>
				</tr>
				<tr>
					<td></td>
					<td>音体美管理</td>
					<td><select class="easyui-combobox" id="ytmgl" name="ytmgl" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="1">可用</option>
						<option value="0">不可用</option>
					</select></td>
				</tr>
				<tr>
					<td></td>
					<td>实验教学管理</td>
					<td><select class="easyui-combobox" id="syjxgl" name="syjxgl" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="1">可用</option>
						<option value="0">不可用</option>
					</select></td>
				</tr>
				<tr>
					<td></td>
					<td>学校基本信息管理</td>
					<td><select class="easyui-combobox" id="xxjbxx" name="xxjbxx" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="1">可用</option>
						<option value="0">不可用</option>
					</select></td>
				</tr>
				<tr>
					<td></td>
					<td>审核状态</td>
					<td><select class="easyui-combobox" id="canUse" name="canUse" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="1">可用</option>
						<option value="0">不可用</option>
					</select></td>
					
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
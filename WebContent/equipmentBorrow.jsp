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
				$.post("equipmetDelete",{delIds:ids},function(result){
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
	
	function openStudentreturn(){
		var selectedRows=$("#dg").datagrid('getSelections');
		if(selectedRows.length==0){
			$.messager.alert("系统提示","请选择要归还吗！");
			return;
		}
		var strIds=[];
		for(var i=0;i<selectedRows.length;i++){
			strIds.push(selectedRows[i].id);
		}
		var ids=strIds.join(",");
		$.messager.confirm("系统提示","您确认要归还这<font color=red>"+selectedRows.length+"</font>条器材数据吗？",function(r){
			if(r){
				$.post("equipmentreturn",{delIds:ids},function(result){
					if(result.success){
						$.messager.alert("系统提示","您已成功归还<font color=red>"+result.delNums+"</font>条器材数据！");
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
				schoolName:$('#schoolName').val(),
				EquipmentRoom:$('#EquipmentRoom').combobox("getValue"),
				EquipmentName:$('#EquipmentName').val(),
				applyName:$('#applyName').val(),
				bbirthday:$('#s_bbirthday').datebox("getValue"),
				ebirthday:$('#s_ebirthday').datebox("getValue"),
				
			});
			
	}
	function ecxlStudent(){
	 	 window.location.href="${pageContext.request.contextPath }/EqupemtExcelSevlet?schoolName="+$('#schoolName').val()+"&EquipmentRoom="+$('#EquipmentRoom').combobox("getValue")+"&EquipmentName="+$('#EquipmentName').val()+"&applyName="+$('#applyName').val()+"&bbirthday="+$('#s_bbirthday').datebox("getValue")+"&ebirthday="+$('#s_ebirthday').datebox("getValue");
	}
	
	
	function openStudentAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle","添加信息");
		url="equipmetSave";
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
		url="equipmetSave?stuId="+row.id;
	}
	

	
</script>
</head>
	<body style="margin: 5px;">
	<table id="dg" title="学生信息" class="easyui-datagrid" fitColumns="true"
	 pagination="true" rownumbers="true" url="equipmetList" fit="true" toolbar="#tb">
		<thead>
			<tr>
				<th field="cb" checkbox="true"></th>
				<th field="id" width="80">编号</th>
				<th field="schoolName" width="80">学校名称</th>
				<th field="EquipmentRoom" width="80">器材室名称</th>
				<th field="EquipmentName" width="80">器材名称</th>
				<th field="unit" width="80">单位</th>
				<th field="num" width="80">数量</th>
				<th field="applyName" width="80">使用人</th>
				<th field="applyDate" width="80">借用日期</th>
				<th field="returnDate" width="80">归还日期</th>
				<th field="returnInfo" width="80">归还情况</th>
				<th field="memo" width="80">备注</th>
			</tr>
		</thead>
	</table>
	
	<div id="tb">
		<div>
			 <c:if test="${currentUser.userType eq '学校管理员' || currentUser.userType eq '普通用户' }">
		        <a href="javascript:openStudentAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
				<a href="javascript:openStudentModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
				<a href="javascript:deleteStudent()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
				<a href="javascript:openStudentreturn()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">归还</a>
		     </c:if>
		    	<a href="javascript:ecxlStudent()" class="easyui-linkbutton" iconCls="icon-print" plain="true">下载Exce</a>
		</div>
		<div>
		&nbsp;学校名称：&nbsp;<input type="text" name="schoolName" id="schoolName" size="10"/>
		&nbsp;器材室名称：&nbsp;<select class="easyui-combobox" id="EquipmentRoom" name="EquipmentRoom" editable="false" panelHeight="auto" style="width: 155px">
							    <option value="">请选择...</option>
								<option value="音乐器材室">音乐器材室</option>
								<option value="体育器材室">体育器材室</option>
								<option value="美术器材室">美术器材室</option>
								<option value="卫生器材室">卫生器材室</option>
							</select>
		&nbsp;器材名称：&nbsp;<input type="text" name="EquipmentName" id="EquipmentName" size="10"/>
		&nbsp;使用人：&nbsp;<input type="text" name="applyName" id="applyName" size="10"/>
		&nbsp;使用日期：&nbsp;<input class="easyui-datebox" name="s_bbirthday" id="s_bbirthday" editable="false" size="10"/>-><input class="easyui-datebox" name="s_ebirthday" id="s_ebirthday" editable="false" size="10"/>
		<a href="javascript:searchStudent()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
		</div>
	</div>
	<div id="dlg" class="easyui-dialog" style="width: 600px;height: 450px;padding: 30px 20px"
		closed="true" buttons="#dlg-buttons">
		<form id="fm" method="post">
			<table cellspacing="5px;">
				<tr>
					<td>器材名称：</td>
					<td><input type="text" name="EquipmentName" id="EquipmentName" class="easyui-validatebox" required="true"/></td>
					<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
					<td>器材室名称：</td>
					<td><select class="easyui-combobox" id="EquipmentRoom" name="EquipmentRoom" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="音乐器材室">音乐器材室</option>
						<option value="体育器材室">体育器材室</option>
						<option value="美术器材室">美术器材室</option>
						<option value="卫生器材室">卫生器材室</option>
					</select></td>
				</tr>
				<tr>
					<td>使用人：</td>
					<td><input type="text" name="applyName" id="applyName" class="easyui-validatebox" required="true"/></td>
					<td></td>
					<td>单位：</td>
					<td><input type="text" name="unit" id="unit" class="easyui-validatebox" required="true" /></td>
				</tr>
				<tr>
					<td>数量：</td>
					<td><input type="text" id="num" name="num"  class="easyui-validatebox" required="true"/></td>
					<td></td>
					<td>归还情况：</td>
					<td><select class="easyui-combobox" id="returnInfo" name="returnInfo" editable="false" panelHeight="auto" style="width: 155px">
					    <option value="">请选择...</option>
						<option value="归还">归还</option>
						<option value="未归还">未归还</option>
					</select></td>
				</tr>
				<tr>
					<td>借用日期：</td>
					<td><input class="easyui-datebox" name="applyDate" id="applyDate" required="true" editable="false" /></td>
					<td></td>
					<td>归还日期：</td>
					<td><input class="easyui-datebox" name="returnDate" id="returnDate"  editable="false" /></td>
				</tr>
				<tr>
					<td valign="top">备注：</td>
					<td colspan="4"><textarea rows="7" cols="50" name="memo" id="memo"></textarea></td>
				</tr>
				<tr><td></td><td><input type="hidden" id="schoolName" class="easyui-validatebox"  name="schoolName" value="${currentUser.schoolCode}"/></td><td></td><td></td><td></td></tr>
			</table>
		</form>
	</div>
	
	<div id="dlg-buttons">
		<a href="javascript:saveStudent()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
		<a href="javascript:closeStudentDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
	</div>
</body>

</html>
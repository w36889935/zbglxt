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

$(function () {
	 $("#passwrod").blur(function(){
		  if($("#passwrod2").val()!=$("#passwrod").val()){
		    	 $("#passwrod").val("");
		    	 alert("两次密码不一致");
		  }
		  });
})
</script>
</head>
<div style="position:fixed;top:20%;left:20%;width:100%;">
    <div class="easyui-panel" title="密码修改" style="width:500px;padding:10px;">  
        <form id="ff" action="passwordAlterServler" method="post">  
            <table>  
                <tr>  
                    <td>请输入旧密码:</td>  
                    <td><input name="past" type="text"></input></td>  
                </tr>  
                <tr>  
                    <td>请输入新密码:</td>  
                    <td><input id="passwrod2" name="passwrod1" type="password"></input></td>  
                </tr>  
                <tr>  
                    <td>再输入一次密码：</td>  
                    <td><input id="passwrod" name="passwrod" type="password"></input></td>  
                </tr>  
                <tr>  
                    <td></td>  
                    <td><input type="submit" value="提交"></input></td>  
                </tr>  
            </table>  
        </form>  
    </div>  
    <c:if test="${not empty error}">
		<div style="backgroundColor: '#666';borderColor: '#666';">
					  		${error }
		</div>
	</c:if>
</div>
</body>
</html>
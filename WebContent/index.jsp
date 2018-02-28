<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>学生信息管理系统登录</title>
<!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

<!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
<link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<link rel="stylesheet" href="dist/css/bootstrap-select.css">
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://code.jquery.com/jquery-1.11.3.min.js" type="text/javascript"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script src="dist/js/bootstrap-select.js"></script>
</script>
<script type="text/javascript">
	$(document).ready(function(){
	  $("#login1").hide();
	  $("#hide").click(function(){
	  $("#login").hide();
	  $("#login1").show();
	  });
	  $("#show").click(function(){
	  	
	  $("#login1").hide();
	  $("#login").show();
	  });
	});
	
	 $(function () {
		 $("#password1").blur(function(){
			    if($("#password2").val()!=$("#password1").val()){
			    	 $("#password1").val("");
			    	 alert("两次密码不一致");
			    }
			  });
		 
		    //默认绑定省
		    ProviceBind();
		    //绑定事件
		    $("#Province").change( function () {
		        CityBind();
		    })
		    
		    $("#City").change(function () {
		        VillageBind();
		    })
		   
		    $("#Village").change(function () {
		    	SchoolBind();
		    })

		})
	    function Bind(str) {
		    alert($("#Province").html());
		    $("#Province").val(str);


		}
		function ProviceBind() {
		    //清空下拉数据
		    $("#Province").html("");

		    var str = "<option>==请选择===</option>";
		    $.ajax({
		        type: "POST",
		        url: "addressServlet",
		        data: { "parentiD": "", "MyColums": "Province" },
		        dataType: "JSON",
		        async: false,
		        success: function (data) {
		            //从服务器获取数据进行绑定
		            $.each(data.Data, function (i, item) {
		                str += "<option value=" + item.inSheng + ">" + item.inSheng + "</option>";
		            })
		            //将数据添加到省份这个下拉框里面
		            $("#Province").append(str);
		        },
		        error: function () { alert("Error"); }
		    });


		   
		        
		}
		function CityBind() {
		    var provice = $("#Province").val();
		    //判断省份这个下拉框选中的值是否为空
		    if (provice == "") {
		        return;
		    }
		    $("#City").html("");
		    var str = "<option>==请选择===</option>";


		    $.ajax({
		        type: "POST",
		        url: "addressServlet",
		        data: { "parentiD": provice, "MyColums": "City" },
		        dataType: "JSON",
		        async: false,
		        success: function (data) {
		            //从服务器获取数据进行绑定
		            $.each(data.Data, function (i, item) {
		                str += "<option value=" + item.inShi + ">" + item.inShi + "</option>";
		            })
		            //将数据添加到省份这个下拉框里面
		            $("#City").append(str);
		        },
		        error: function () { alert("Error"); }
		    });


		}
		function VillageBind() {


		    var provice = $("#City").val();
		    //判断市这个下拉框选中的值是否为空
		    if (provice == "") {
		        return;
		    }
		    $("#Village").html("");
		    var str = "<option>==请选择===</option>";
		    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
		    $.ajax({
		        type: "POST",
		        url: "addressServlet",
		        data: { "parentiD": provice, "MyColums": "Village" },
		        dataType: "JSON",
		        async: false,
		        success: function (data) {
		            //从服务器获取数据进行绑定
		            $.each(data.Data, function (i, item) {
		                str += "<option value=" + item.inXian + ">" + item.inXian + "</option>";
		            })
		            //将数据添加到省份这个下拉框里面
		            $("#Village").append(str);
		        },
		        error: function () { alert("Error"); }
		    });
		}
		    function SchoolBind() {


			    var provice = $("#Village").val();
			    //判断市这个下拉框选中的值是否为空
			    if (provice == "") {
			        return;
			    }
			    $("#schoollist").html("");
			    var str = "<option>==请选择===</option>";
			    //将市的ID拿到数据库进行查询，查询出他的下级进行绑定
			    $.ajax({
			        type: "POST",
			        url: "addressServlet",
			        data: { "parentiD": provice, "MyColums": "school" },
			        dataType: "JSON",
			        async: false,
			        success: function (data) {
			            //从服务器获取数据进行绑定
			            $.each(data.Data, function (i, item) {
			                str += "<option value=" + item.schoolName + ">" + item.schoolName + "</option>";
			            })
			            //将数据添加到省份这个下拉框里面
			            $("#schoollist").append(str);
			        },
			        error: function () { alert("Error"); }
			    });
		    //$.post("/Home/GetAddress", { parentiD: provice, MyColums: "Village" }, function (data) {  
		    //    $.each(data.Data, function (i, item) {
		    //        str += "<option value=" + item.Id + ">" + item.MyTexts + "</option>";
		    //    })
		    //    $("#Village").append(str);
		    //})
		} 
</script>
<style type="text/css">
	body {
		background-image: url(images/bj.jpg);
		background-size:100%
	}
	#loginfrom{
		background: hsla(0,0%,100%,0.3); 
		background-color:#FFFFFF;
		opacity: 0.6;
		border-radius:13px;
	}
	#register{
		background: hsla(0,0%,100%,0.3); 
		background-color:#FFFFFF;
		opacity: 0.6;
		border-radius:13px;
	}
	<div style="visibility:hidden;">
</style>
</head>
<body>
	<div class="row" id="login">
	<div class="row" style="margin-top:10%;">
		<div id="login" class="col-xs-4 col-md-offset-4" >
			<div id="loginfrom">
				<div class="row">
					<div class="col-xs-6 col-md-offset-3">
						<h3>教育装备管理系统</h3>
					</div>
				</div>
				<br/>
			   <form class="form-horizontal" action="login" method="post">
				  <div class="form-group">
					<label for="inputEmail3" class="col-sm-2 control-label">账号</label>
					<div class="col-sm-9">
					  <input type="text" class="form-control" name="userName" id="userName" placeholder="请输入账号">&nbsp;&nbsp;
					</div>
				  </div>
				  <div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">密码</label>
					<div class="col-sm-9">
					  <input type="password" class="form-control" name="password" id="password" placeholder="请输入密码">
					</div>
				  </div>
				  <div class="form-group">
					<div class="col-sm-offset-2 col-sm-6">
					  <button type="submit" class="btn btn-default">登陆</button>
					</div>
					<div class="col-sm-4">
					  <button id="hide"  class="btn btn-default" type="button">注册</button>
					</div>
				  </div>
				  <div class="row" style="margin-top:10%;">
					  <c:if test="${not empty error}">
					  	<div class="col-sm-offset-1 col-sm-10 alert alert-danger">
					  		${error }
					  	</div>
					  </c:if>
				  </div>
				</form>
			</div>
		</div>
	</div>
	</div>
	<!--注册-->
	
	<div class="row" style="margin-top:4%;" >
		<div id="login1" class="col-xs-8 col-md-offset-2" >
			<div id="register">
				<div class="row">
					<div class="col-xs-2 col-md-offset-5">
						<h3>账号注册</h3>
					</div>
				</div>
				<br/>
			   <form class="form-horizontal"  action="RegisterServlert" method="post">
				  <div class="form-group">
					<label for="inputEmail3" class="col-sm-1 col-md-offset-1 control-label">账号</label>
					<div class="col-sm-3">
					  <input type="text" class="form-control" name="userName" id="userName" placeholder="请输入账号">
					</div>
					<label for="inputEmail3" class="col-sm-2  control-label">真实姓名</label>
					<div class="col-sm-3">
					  <input type="text" class="form-control" name="realName" id="realName" placeholder="请输入真实姓名">
					</div>
				  </div>
				  <div class="form-group">
					<label for="inputPassword3" class="col-sm-1 col-md-offset-1 control-label">密码</label>
					<div class="col-sm-3">
					  <input type="password" class="form-control" name="password" id="password2" placeholder="请输入密码">
					</div>
					<label for="inputPassword3" class="col-sm-2  control-label">重输密码</label>
					<div class="col-sm-3">
					  <input type="password" class="form-control" name="password1" id="password1" placeholder="请输入密码">&nbsp;&nbsp;
					</div>
				  </div>
				  <div class="form-group">
					<label for="inputEmail3" class="col-sm-2 control-label">密保提问</label>
					<div class="col-sm-3">
						<select id="passQues" name="passQues" class="form-control selectpicker">
						  <option>你父亲姓名？</option>
						  <option>你母亲姓名？</option>
						  <option>你出生地？</option>
						</select>
					</div>
					<label for="inputEmail3" class="col-sm-2  control-label">省份</label>
					<div class="col-sm-3">
						<select class="form-control" name="Province" id="Province">
	                        <option>==请选择===</option>
	                    </select>
					</div>
				  </div>
				  <div class="form-group">
					<label for="inputPassword3" class="col-sm-2 control-label">提出答案</label>
					<div class="col-sm-3">
					  <input type="text" class="form-control" name="quesAnswer" id="quesAnswer" placeholder="请输入答案">
					</div>
					<label for="inputPassword3" class="col-sm-2  control-label">地市</label>
					<div class="col-sm-3">
					    <select class="form-control" name="City" id="City">
	                        <option>==请选择===</option>
	                    </select>
					</div>
				  </div>
				  <div class="form-group">
					<label for="inputEmail3" class="col-sm-2  control-label">手机号码</label>
					<div class="col-sm-3">
					  <input type="text" class="form-control" name="phoneNumber" id="phoneNumber" placeholder="请输入手机号">
					</div>
					<label for="inputEmail3" class="col-sm-2  control-label">县区</label>
					<div class="col-sm-3">
					  <select class="form-control" name="Village" id="Village">
	                        <option>==请选择===</option>
	                  </select>
					</div>
				  </div>
				  <div class="form-group">
					<label for="inputPassword3" class="col-sm-2  control-label">密保邮箱</label>
					<div class="col-sm-3">
					  <input type="email" class="form-control" name="passEmail" id="passEmail" placeholder="请输入邮箱">
					</div>
					<label for="inputPassword3" class="col-sm-2 control-label">学校</label>
					<div class="col-sm-3">
					  <select class="form-control" name="schoollist" id="schoollist">
	                        <option>==请选择===</option>
	                  </select>
					</div>
				  </div>
				  <div class="form-group">
					<div class="col-sm-offset-2 col-sm-6">
					  <button type="submit" class="btn btn-default">注册</button>
					</div>
					<div class="col-sm-4">
					  <button id="show"  class="btn btn-default" type="button">登录</button>
					</div>
				  </div>
				  <div class="row" style="margin-top:10%;">
				  </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
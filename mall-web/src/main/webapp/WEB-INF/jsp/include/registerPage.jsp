
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>

	
<script>
    /**
	 * 对注册页面各项操作的提示
     */
    $(function(){

        //回显错误操作提示信息
	<c:if test="${!empty msg}">
	$("span.errorMessage").html("${msg}");
	$("div.registerErrorMessageDiv").css("visibility","visible");		
	</c:if>

        //未输入用户名时显示提示语句
	$(".registerForm").submit(function(){
		if(0==$("#name").val().length){
			$("span.errorMessage").html("请输入用户名");
			$("div.registerErrorMessageDiv").css("visibility","visible");
			return false;
		}

		//提示用户名长度不合法
        if(6>$("#name").val().length){
            $("span.errorMessage").html("用户名长度不能少于6位");
            $("div.registerErrorMessageDiv").css("visibility","visible");
            return false;
        }

        //密码未输入时显示提示信息
		if(0==$("#password").val().length){
			$("span.errorMessage").html("请输入密码");
			$("div.registerErrorMessageDiv").css("visibility","visible");
			return false;
		}

		//提示密码长度不合法
        if(6>$("#password").val().length){
            $("span.errorMessage").html("密码长度不能小于6位");
            $("div.registerErrorMessageDiv").css("visibility","visible");
            return false;
        }

        //提示输入确认密码
		if(0==$("#repeatpassword").val().length){
			$("span.errorMessage").html("请输入重复密码");
			$("div.registerErrorMessageDiv").css("visibility","visible");
			return false;
		}

		//提示两次密码输入不一致
		if($("#password").val() !=$("#repeatpassword").val()){
			$("span.errorMessage").html("重复密码不一致");
			$("div.registerErrorMessageDiv").css("visibility","visible");
			return false;
		}

		return true;
	});
})
</script>



<form method="post" action="foreregister" class="registerForm">


<div class="registerDiv">
	<div class="registerErrorMessageDiv">
		<div class="alert alert-danger" role="alert">
		  <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
		  	<span class="errorMessage"></span>
		</div>		
	</div>

	
	<table class="registerTable" align="center">
		<tr>
			<td  class="registerTip registerTableLeftTD">设置用户名</td>
			<td></td>
		</tr>
		<tr>
			<td class="registerTableLeftTD">登陆名</td>
			<td  class="registerTableRightTD"><input id="name" name="username" placeholder="用户名一旦设置成功，无法修改" > </td>
		</tr>
		<tr>
			<td  class="registerTip registerTableLeftTD">设置登陆密码</td>
			<td  class="registerTableRightTD">登陆时验证，保护账号信息</td>
		</tr>		
		<tr>
			<td class="registerTableLeftTD">登陆密码</td>
			<td class="registerTableRightTD"><input id="password" name="password" type="password"  placeholder="设置你的登陆密码" > </td>
		</tr>
		<tr>
			<td class="registerTableLeftTD">密码确认</td>
			<td class="registerTableRightTD"><input id="rePassword" name="rePassword" type="password"   placeholder="请再次输入你的密码" > </td>
		</tr>
				
		<tr>
			<td colspan="2" class="registerButtonTD">
				<input type="submit" value="提交">
			</td>
		</tr>				
	</table>
</div>
</form>
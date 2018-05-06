<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
	<link rel="stylesheet" type="text/css" href="/DataShare/css/style.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
</head>
<body>
  <input type="hidden" id="userId" name="userId" value="${userId}"/>
  <div id="iframeheight">
  		<div class="pbox">
			
            <div class="base-info">
                <div class="base-info-input">
                	<fieldset>
			    	<legend>用户信息</legend>
                    <ul>
                    	<li>
                        	<span>用户名</span>
                            <label><input name="userName" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>中文名称</span>
                            <label><input name="userCName" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>性别</span>
                            <label><input name="userGender" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>所属单位</span>
                            <label><input name="userUnit" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>联系方式</span>
                            <label><input name="userPhone" type="text" value="" class="input01 require" ></label>
                        </li>
						<li>
                        	<span>邮箱</span>
                            <label><input name="userEmail" type="text" value="" class="input01 require" ></label>
                        </li>
						
						 <li>
                        	<span>注册日期</span>
                            <label><input name="regDateStr" type="text" value="" class="input01 require" err="请填写联系电话"></label>
                        </li>
						<li>
                        	<span>是否管理员</span>
                            <label><input name="userTypeStr" type="text" value="" class="input01 require" err="请填写邮箱"></label>
                        </li>
                    </ul>               	 	
					<div class="clear"></div>
               	 	</fieldset>
                </div>

                <div class="fun-box">
                	<a href="javascript:void(0)" class="btn1">返回</a>
                    <div class="error">请输入密码</div>
                </div>
                
            </div>
        </div>
        </div>
        
    <script>
    	$.ajaxSetup({ cache: false });//全局禁用缓存
		var userId = $("#userId").val();
    	$(document).ready(function(){
    		var url=window.location.href;
			$(".base-info").addClass("base-info-back");
			$(".input01").attr("disabled","disabled");
			ajaxRequest("/DataShare/querUserInfoById.action?userId="+userId,"get",function(res){
				$(".input01").eq(0).val(res[0].userInfo.userName);
				$(".input01").eq(1).val(res[0].userInfo.userCName);
				$(".input01").eq(2).val(res[0].userInfo.userGender);
				$(".input01").eq(3).val(res[0].userInfo.userUnit);
				$(".input01").eq(4).val(res[0].userInfo.userMobile);
				$(".input01").eq(5).val(res[0].userInfo.userEmail);
				$(".input01").eq(6).val(res[0].userInfo.regDateStr);
				$(".input01").eq(7).val(res[0].userInfo.userTypeStr);
			})
			
			
			//返回
			$(".fun-box a").click(function(){
				window.location.href="/DataShare/userInfoListToJsp.action?applyTitle=用户管理";	
			})
    	})
    	
    </script>
</body>
</html>

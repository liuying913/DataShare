<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <title></title>
	<link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
</head>
<body>
  <input type="hidden" id="userId" name="userId" value="${userId}"/>
  <div id="iframeheight">
  
  		<div class="pbox tspbox" style="padding-bottom:0px;">
           <div class="ptit">用户信息编辑</div>
  	    </div>
  	   
  		<div class="pbox">
            <div class="base-info">
                <div class="base-info-input">
                    <ul>
                    	<li>
                        	<span>用户名</span>
                            <label><input id="userName" name="userName" type="text" value="" class="input01 require" err="请填写用户名"></label>
                        </li>
                        <li>
                        	<span>中文名称</span>
                            <label><input name="userCName" type="text" value="" class="input01 require" err="请填写中文名称"></label>
                        </li>
                        <li>
                        	<span>性别</span>
                            <label>
								<select name="userGender" class="input01 require" err="请选择性别">
								  <option value="男">男</option>  
								  <option value="女">女</option>  
								</select>  
							</label>
                        </li>
                        <li>
                        	<span>所属单位</span>
                            <label><input name="userUnit" type="text" value="" class="input01 require" err="请填写所属单位"></label>
                        </li>
                        <li>
                        	<span>联系方式</span>
                            <label><input name="userPhone" type="text" value="" class="input01 require" err="请填写联系方式"></label>
                        </li>
						<li>
                        	<span>邮箱</span>
                            <label><input name="userEmail" type="text" value="" class="input01 require" err="请填写邮箱"></label>
                        </li>
                    </ul>
               	 	<div class="clear"></div>
                </div>

				<div class="warp-button" style="text-align: center">
					<br><br>
					<button type="button" id="save" class="shengfen-button" style="width:111px;height: 26px;float: none;display: inline-block"><!--<img src="/DataShare/img/s1.png"/>-->保存</button>
					<button type="button" id="back" class="shengfen-button" style="margin-left: 15px;width:111px;height: 26px;float: none;display: inline-block"><!--<img src="/DataShare/img/s2.png"/>-->返回</button>
				</div>
                <div class="blank200"></div>
            </div>
        </div>
        </div>
        
    <script>		$.ajaxSetup({ cache: false });//全局禁用缓存
		var userId = $("#userId").val();
		var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;
		var i=0;
    	$("#save").click(function(){
    		i=0;
			str="1=1";
			$(".require").each(function(e){
				if($(this).val()==""){
					$(".fun-box .error").show();
					$(".fun-box .error").text($(this).attr("err"));	
					$(this).addClass("err");
					i=1;
				}else{
					if(e==rlen-1&&i==0){
						$(".input01").each(function(a) {
							str=str+"&"+$(this).prop("name")+"="+$(this).val();//str=str+"/"+$(this).name+':'+$(this).attr("name")+" "+$(this).prop("name");
						  	if(a==len-1){
						  		str = encodeURI(str);
								var URL="/DataShare/updateUserInfo.action?uOp=upd&userId="+userId+"&"+str;
								ajaxRequest(URL,"get",function(res){
									if(res.date[0].code==1){
										window.location.href="/DataShare/userInfoListToJsp.action?applyTitle=用户管理";	
									}else{
										alert(res.date[0].msg);	
									}
								})
								
							}  
						});	
					}
				}
			})	
			
		})
		
		
		
		
		$(document).ready(function(){
    		var url=window.location.href;
			//$(".base-info").addClass("base-info-back");
			$(".userName").attr("disabled","disabled");
			ajaxRequest("/DataShare/querUserInfoById.action?userId="+userId,"get",function(res){
				$(".input01").eq(0).val(res[0].userInfo.userName);
				$(".input01").eq(1).val(res[0].userInfo.userCName);
				$(".input01").eq(2).val(res[0].userInfo.userGender);
				$(".input01").eq(3).val(res[0].userInfo.userUnit);
				$(".input01").eq(4).val(res[0].userInfo.userPhone);
				$(".input01").eq(5).val(res[0].userInfo.userEmail);
				$(".input01").eq(6).val(res[0].userInfo.regDateStr);
				$(".input01").eq(7).val(res[0].userInfo.userTypeStr);
			})
		
			//返回
			$("#back").click(function(){
				var url="/DataShare/userInfoListToJsp.action?applyTitle=用户管理";
				window.location.href=url;	
			})
		
		
    	})
    	
    </script>
</body>
</html>

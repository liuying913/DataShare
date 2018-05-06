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
	<meta charset="utf-8" />
	<link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <link href="/DataShare/user/head/jsp/style/base.css" rel="stylesheet"/>
	<link href="/DataShare/user/head/jsp/style/layout.css" rel="stylesheet"/>
	<link href="/DataShare/user/head/imgareaselect/css/imgareaselect-animated.css" rel="stylesheet"/>
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<script type="text/javascript" src="/DataShare/user/scripts/fullAvatarEditor.js"></script>
	
	<script src="/DataShare/user/head/jsp/javascript/jquery-1.8.3.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/jsp/javascript/thirdtool/data_dropdown/data_dw.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/jsp/javascript/thirdtool/jquery-validation-1.14.0/jquery.validate.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/jsp/javascript/thirdtool/jquery-validation-1.14.0/messages_zh.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/jsp/javascript/jquery.cookie.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/swfupload/swfupload.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/swfupload/swfupload.queue.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/swfupload/fileprogress.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/swfupload/handlers.js"></script>
	<script type="text/javascript" src="/DataShare/user/head/imgareaselect/scripts/jquery.imgareaselect.js"></script>
	<script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
	<script type="text/javascript" src="/DataShare/js/common/check.js"></script>
	<style type="text/css">
		.base-info-input ul li{
			width: 365px;
		}
	</style>
</head>
<body>
  <input type="hidden" id="userId" name="userId" value="${userId}"/>
  <div id="iframeheight">
		<div class="pbox tspbox clearfix" style="padding-bottom:0px;">
           <div class="ptit ptit1" style="width:0;overflow:hidden;">用户信息查看</div>
           <div class="ptit ptit1 selectedItem">用户信息编辑</div>
           <div class="ptit ptit1">修改密码</div>
  	   </div>
       <ul class="detailInfo clearfix">
           <li class="infoShow" style="display: none">
			   <div class="pbox">
				   <div class="base-info baseInfo">
					   <div class="base-info-input">
						   <ul style="margin: 10px 0 0 100px;">
							   <li>
								   <span>用户名 ：</span>
								   <label><input name="userName" type="text" value="" class="input01 require" ></label>
							   </li>
							   <li>
								   <span>中文名称 ：</span>
								   <label><input name="userCName" type="text" value="" class="input01 require" ></label>
							   </li>
							   <li>
								   <span>性别 ：</span>
								   <label><input name="userGender" type="text" value="" class="input01 require" ></label>
							   </li>
							   <li>
								   <span>所属单位 ：</span>
								   <label><input name="userUnit" type="text" value="" class="input01 require" ></label>
							   </li>
							   <li>
								   <span>联系方式 ：</span>
								   <label><input name="userPhone" type="text" value="" class="input01 require" ></label>
							   </li>
							   <li>
								   <span>电子邮箱 ：</span>
								   <label><input name="userEmail" type="text" value="" class="input01 require" ></label>
							   </li>

							   <li>
								   <span>注册日期 ：</span>
								   <label><input name="regDateStr" type="text" value="" class="input01 require" err="请填写联系电话"></label>
							   </li>
							   <li>
								   <span>是否管理员 ：</span>
								   <label><input name="userTypeStr" type="text" value="" class="input01 require" err="请填写邮箱"></label>
							   </li>
						   </ul>

						   <div class="clear"></div>
					   </div>
				   </div>
				   <div class="clearfix">
					   <button type="button" id="back" class="back shengfen-button" style="margin-top:25px;margin-left: 412px;text-align:center;float:none;display: inline-block;width:110px;height: 35px;border-radius: 5px">返回</button>
				   </div>
			   </div>
		   </li>
           <li class="userInfo" >

           <!-- 头像上传和修改 -->
           		<div class="clearfix" style="width: 140px; display: inline-block;float: left;margin-left: 10px;">
					<dl class="infopart clearfix" >
						<dt style="text-align: center;">我的头像</dt>
						<dd style="position:relative;">
							<div class="userpicbox" style=" margin: 0 15px;"><img id="preview" src="" alt="" class="previewimg">
								<div class="userpicbtn">								
									<span id="avatarSwfButton"></span><input type="button" value="" style="width:100px;height:100px;border:0 none;"/> &nbsp;
									<input id="avatarSwfCancel" type="hide" style="width:0;heigth:0;display:none;"/>
								</div>
							</div>
							<div style="position:relative;top:10px;color:#999;clear:left;padding:0 0 0 11px;font-size:11px;">图片大小不要超过1M</div>
							<div id="avatarSwfProgress" style="position:absolute;top:44px;left:266px;"></div>
						</dd>
					</dl>
					<div id="avatarDiv"></div>
					<form id="avatarForm" action="/swfUpload/avatarSave.jspx" method="post" class="mt20 hide">
						<input type="hidden" name="status_0" value="头像修改成功！"/>
						<input type="hidden" name="nextUrl" value=""/>
						<input type="hidden" id="top" name="top" value=""/>
						<input type="hidden" id="left" name="left" value=""/>
						<input type="hidden" id="width" name="width" value=""/>
						<input type="hidden" id="height" name="height" value=""/>
						<input type="button" value="提交" class="btnline btnline_blue" style="margin-top:10px;" id="avatarSubmit"/>
					</form>

				</div>

           <!-- 头像上传和修改结束 -->
			   <div class="pbox">
				   <div class="base-info  baseInfo">
					   <div class="base-info-input">
						   <ul id="userInfo">
							   <li>
								   <span><b class="requireFlag">*</b>用户名 ：</span>
								   <label><input id="userName" name="userName" type="text" value="" class="input01" disabled="disabled"></label>
								<!--    <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
						           <div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">用户名</p></div> -->
							   </li>
							   <li>
								   <span><b class="requireFlag">*</b>中文名称 ：</span>
								   <label><input name="userCName" type="text" value="" class="input01 require"></label>
								   	<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
						           <div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">中文名称</p></div>
							   </li>
							   <li>
								   <span><b class="requireFlag">*</b>性别 ：</span>
								   <label>
										<input type="hidden" name="userGender" class="input01" id="arrangement" value="男">
										<div class="ck-switch ck-switch1">
											<span class="ck-switch-on ck-switch-on1">男</span>
											<span class="ck-switch-off ck-switch-off1 ck-switch-current ck-switch-current-40"></span>
										</div>
									</label>
							   </li>
							   <li>
								   <span><b class="requireFlag">*</b>所属单位 ：</span>
								   <label><input name="userUnit" type="text" value="" class="input01 require"></label>
								   	<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
						            <div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">所属单位</p></div>
							   </li>
							   <li>
								   <span>联系方式 ：</span>
								   <label><input name="userPhone" type="text" value="" class="input01" ></label>
							   </li>
							   <li>
								   <span>电子邮箱 ：</span>
								   <label><input name="userEmail" type="text" value="" class="input01"></label>
							   </li>
							   <li>
								   <span>注册日期 ：</span>
								   <label><input name="regDateStr" type="text" value="" class="input01 require" err="请填写联系电话"></label>
							   </li>
							   <li>
								   <span>是否管理员 ：</span>
								   <label>
										<input type="hidden" name="userTypeStr" class="input01" id="arrangement1" value="是">
										<div class="ck-switch ck-switch2">
											<span class="ck-switch-on ck-switch-on2">是</span>
											<span class="ck-switch-off ck-switch-off2 ck-switch-current ck-switch-current-40">否</span>
										</div>
									</label>
							   </li>
						   </ul>
						   <div class="clear"></div>
					   </div>
					 
				   </div>
			     <div class="warp-button" style="margin-top: 20px;">
					   <button type="button" id="saveInfo" class="shengfen-button" style="width:110px;height: 35px;border-radius: 5px"><!--<img src="/DataShare/img/s1.png"/>-->保存</button>
					   <button type="button"  class="back shengfen-button" style="margin-left: 15px;width:110px;height: 35px;border-radius: 5px"><!--<img src="/DataShare/img/s2.png"/>-->返回</button>
				   </div>
			   </div>
		   </li>
           <li class="pwdChange" style="display: none">
			   <div class="pbox tspbox">
					   <div class="base-info tsbase-info clearfix baseInfo">
						   <form action="/DataShare/updateUserInfo.action" method="get">
							   <div class="base-info-input">
								   <ul  style="margin-left:270px;">
									   <li>
										   <span><b class="requireFlag">*</b>新密码 ：</span>
										   <label><input id="newUserPwd" name="newUserPwd" type="password" value="" class="input01 require" err="请填写新密码" style="-webkit-box-shadow: 0 0 0px 1000px white inset !important;"></label>
										   <div  class="sucuState"  style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										   <div id="result_Name" class="errState" style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写新密码</p></div>
									   </li>
									   <li>
										   <span><b class="requireFlag">*</b>重复密码 ：</span>
										   <label><input id="newUserPwd2" name="newUserPwd2" type="password" value="" class="input01 require" err="两次密码不一致" style="-webkit-box-shadow: 0 0 0px 1000px white inset !important;"></label>
										   <div  class="sucuState"  style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										   <div id="result_Name" class="errState" style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">两次密码不一致</p></div>
									   </li>
									   
									    <li>
											温馨提示：该密码也是ftp的密码。
									   </li>
								   </ul>

							   </div>
							   <input id="uOp" name="uOp" type="hidden" value="pas">
						   </form>
					   </div>
				   	   <div class="clearfix">
						   <button type="button" id="savePwd" class="shengfen-button" style="margin-top:25px;margin-left: 337px;text-align:center;display: inline-block;width:110px;height: 35px;border-radius: 5px"><!--<img src="/DataShare/img/s2.png"/>-->保存</button>
						   <button type="button" class="back shengfen-button" style="margin-top:25px;margin-left: 15px;width:110px;height: 35px;border-radius: 5px"><!--<img src="/DataShare/img/s2.png"/>-->返回</button>
					</div>
				   </div>
		   </li>
       </ul>
    <script>
		var userId = $("#userId").val();
		var perviewV = new Date().getTime();
		$.ajaxSetup({ cache: false });//全局禁用缓存
    	$(document).ready(function(){
    		var url=window.location.href;
			$(".base-info").addClass("base-info-back");
			$(".infoShow .input01").attr("disabled","disabled");
			ajaxRequest("/DataShare/querUserInfoById.action?userId="+userId,"get",function(res){
				$(".input01").eq(0).val(res[0].userInfo.userName);
				$(".input01").eq(1).val(res[0].userInfo.userCName);
				$(".input01").eq(2).val(res[0].userInfo.userGender);
				$(".input01").eq(3).val(res[0].userInfo.userUnit);
				$(".input01").eq(4).val(res[0].userInfo.userMobile);
				$(".input01").eq(5).val(res[0].userInfo.userEmail);
				$(".input01").eq(6).val(res[0].userInfo.regDateStr);
				$(".input01").eq(7).val(res[0].userInfo.userTypeStr);
				
				if(res[0].userInfo.imgPath == null || res[0].userInfo.imgPath ==''){
					$("#preview").attr("src","/DataShare/uploads/users/1/avatar_large.jpg?v="+ perviewV);
				}else{
					$("#preview").attr("src","/DataShare/uploads/users/"+userId+"/avatar_large.jpg?v="+new Date().getTime());
				}
			})

			$('.ck-switch .ck-switch-off1').unbind('click').bind('click',function(){
				$('.ck-switch1').addClass('active');
				$('.ck-switch .ck-switch-on1').addClass('ck-switch-current ck-switch-current-3').html("");
				$('.ck-switch .ck-switch-off1').removeClass('ck-switch-current ck-switch-current-40').html("女");
				$("#arrangement").val( "女");
			});

			$('.ck-switch .ck-switch-on1').unbind('click').bind('click',function(){
				if($(this).hasClass('ck-switch-on')){
					$('.ck-switch1').removeClass('active');
					$('.ck-switch .ck-switch-on1').removeClass('ck-switch-current ck-switch-current-3').html("男");
					$('.ck-switch .ck-switch-off1').addClass('ck-switch-current ck-switch-current-40').html("");
					$("#arrangement").val( "男");
				}
			});
			$('.ck-switch .ck-switch-off2').unbind('click').bind('click',function(){
				$('.ck-switch2').addClass('active');
				$('.ck-switch .ck-switch-on2').addClass('ck-switch-current ck-switch-current-3').html("");
				$('.ck-switch .ck-switch-off2').removeClass('ck-switch-current ck-switch-current-40').html("否");
				$("#arrangement1").val( "否");
			});

			$('.ck-switch .ck-switch-on2').unbind('click').bind('click',function(){
				if($(this).hasClass('ck-switch-on')){
					$('.ck-switch2').removeClass('active');
					$('.ck-switch .ck-switch-on2').removeClass('ck-switch-current ck-switch-current-3').html("是");
					$('.ck-switch .ck-switch-off2').addClass('ck-switch-current ck-switch-current-40').html("");
					$("#arrangement1").val( "是");
				}
			});

			//返回
			$(".back").click(function(){
				window.location.href="/DataShare/userInfoListToJsp.action?applyTitle=用户管理";
			})
			/*tab切换*/
			$(".pbox").find(".ptit1").click(function(){
				$(this).addClass("selectedItem").siblings().removeClass("selectedItem");
				var index = $(this).index();
				$(".detailInfo").find(">li").eq( index ).show().siblings().hide();
			});

			/*修改密码模块*/
			var str="";
			var lenPwd=$(".pwdChange .input01").length;
			var rlenPwd=$(".pwdChange .require").length;
			var i=0;
			var flag_1 = true;//原始密码
			var flag_2 = true;//新密码
			var flag_3 = true;//重新输入新密码
			var flag_4 = true;//两次密码是否相同
			$("#savePwd").click(function(){
				i=0;
				$(".pwdChange .require").each(function(e){
					if($(this).val()==""){
						$(this).parents("li").find(".errState").show();
						$(this).parents("li").find(".sucuState").hide();
						i=1;
					}else{
						if(e==rlenPwd-1&&i==0 && flag_2 && flag_3 && flag_4){
							$(".pwdChange .input01").each(function(a) {
								str=str+"&"+$(this).prop("name")+"="+$(this).val();
								if(a==lenPwd-1){
									$(this).parents("li").find(".errState").hide();
									$(this).parents("li").find(".sucuState").show();
									str = encodeURI(str);
									var URL="/DataShare/manager/managerUpdateUserPassword.action?userId="+userId+str;
									ajaxRequest(URL,"get",function(res){
										if(res.date[0].code!=-1){
											/*CheckPassWord();*/
											$(this).removeClass("err");
											//init( "修改成功" ,0 );
	                                        //$(".confirm",window.parent.document).click( function(){
	                                        	//var nextUrl="/DataShare/manager/userInfoListToJsp.action?applyTitle=用户管理";
	 											//window.location.href="/DataShare/manager/successToJsp.action?title=修改密码&message=密码修改成功!&url="+nextUrl;
	 		                                //})
	 		                                
	 		                                init( "修改成功" ,0 );
	                                        $(".confirm",window.parent.document).click( function(){
	 											window.location.href="/DataShare/manager/userInfoListToJsp.action?applyTitle=用户管理";
	 		                                })
										}else{
											init( res.date[0].msg ,2 );
										}
									});
									str="";
								}
							});
						}
					}
				})
			});
			
			
	        //第一次输入的密码
	        $("#newUserPwd").blur(function(){
				 var type_new = $("#newUserPwd").val();
	             var checkObj = $("#newUserPwd").parent();
	             if(!CheckPassWord(type_new)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为字母或数字且长度4到8位");
	                 flag_2 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_2 = true;
	            }
			});
	        //第二次输入的密码
	        $("#newUserPwd2").blur(function(){
				 var type_new = $("#newUserPwd2").val();
	             var checkObj = $("#newUserPwd2").parent();
	             
	             var newUserPwd = $("#newUserPwd").val();
	             if(!CheckPassWord(type_new)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为字母或数字且长度4到8位");
	                 flag_3 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_3 = true;
	            }
	            
	            //判断两次密码是否相同
	            if(type_new!=newUserPwd){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>两次密码不同");
	                 flag_4 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_4 = true;
	            }
	            
	            
			});
	        

			/*信息编辑模块*/
			var strInfo="";	
			var j=0;
			$("#saveInfo").click(function(){
			   var len=$("#userInfo .input01").length;
			   var rlen=$("#userInfo .require").length;
				j=0;
				strInfo="1=1";			
				$("#userInfo .require").each(function(e){
					if( $(this).val()==""){
					   $(this).parent().parent("li").find(".errState").show();
					   $(this).parent().parent("li").find(".sucuState").hide();
						j=1;

					}else{
						$(this).parent().parent("li").find(".errState").hide();
					    $(this).parent().parent("li").find(".sucuState").show();

						if(e==rlen-1&&j==0){
							$("#userInfo .input01").each(function(a) {
								strInfo=strInfo+"&"+$(this).prop("name")+"="+$(this).val();
								if(a==len-1){
									var URL="/DataShare/updateUserInfo2.action?uOp=upd&userId="+userId+"&"+strInfo;
									URL=encodeURI(URL);
									ajaxRequest(URL,"get",function(res){
										if(res.date[0].code==1){
											console.log(res.date[0])
											init( "保存成功" ,0 );
	                                        $(".confirm",window.parent.document).click( function(){		                  
												window.location.href="/DataShare/manager/userInfoListToJsp.action?applyTitle=用户管理";
	 		                                 })

										}else{
										
											init( res.date[0].msg ,2 );
										}
									})
								}
							});
						}
					}
				})

			})

			ajaxRequest("/DataShare/querUserInfoById.action?userId="+userId,"get",function(res){
				$(".userInfo .input01").eq(0).val(res[0].userInfo.userName);
				$(".userInfo .input01").eq(1).val(res[0].userInfo.userCName);
				$(".userInfo .input01").eq(2).val(res[0].userInfo.userGender);
				$(".userInfo .input01").eq(3).val(res[0].userInfo.userUnit);
				$(".userInfo .input01").eq(4).val(res[0].userInfo.userPhone);
				$(".userInfo .input01").eq(5).val(res[0].userInfo.userEmail);
				$(".userInfo .input01").eq(6).val(res[0].userInfo.regDateStr);
				$(".userInfo .input01").eq(7).val(res[0].userInfo.userTypeStr);
				
                if( res[0].userInfo.userGender == "女" ){
                   $('.ck-switch1').addClass('active');
				   $('.ck-switch .ck-switch-on1').addClass('ck-switch-current ck-switch-current-3').html("");
				   $('.ck-switch .ck-switch-off1').removeClass('ck-switch-current ck-switch-current-40').html("女");
				   $("#arrangement").val( "女");
                }else{
                  if($(this).hasClass('ck-switch-on')){
					$('ck-switch1').removeClass('active');
					$('.ck-switch .ck-switch-on1').removeClass('ck-switch-current ck-switch-current-3').html("男");
					$('.ck-switch .ck-switch-off1').addClass('ck-switch-current ck-switch-current-40').html("");
					$("#arrangement").val( "男");
				  }

                }
				
				if(res[0].userInfo.userTypeStr == "否" ){
                   $('.ck-switch2').addClass('active');
					$('.ck-switch .ck-switch-on2').addClass('ck-switch-current ck-switch-current-3').html("");
					$('.ck-switch .ck-switch-off2').removeClass('ck-switch-current ck-switch-current-40').html("否");
					$("#arrangement1").val( "否");
                }else{
                  if($(this).hasClass('ck-switch-on')){
					$('.ck-switch2').removeClass('active');
					$('.ck-switch .ck-switch-on2').removeClass('ck-switch-current ck-switch-current-3').html("是");
					$('.ck-switch .ck-switch-off2').addClass('ck-switch-current ck-switch-current-40').html("");
					$("#arrangement1").val( "是");
				  }

                }

				if(res[0].userInfo.imgPath == null || res[0].userInfo.imgPath ==''){
					$("#preview").attr("src","/DataShare/uploads/users/1/avatar_large.jpg?v="+ perviewV);
				}else{
					$("#preview").attr("src","/DataShare/uploads/users/"+userId+"/avatar_large.jpg?v="+new Date().getTime());
				} 
			})

            /*头像修改模块*/
			/*上传照片修改头像*/
			$(".photo").click(function(){
				$(this).hide();
				$(".fileUpload").show();
			});
			var urls='/DataShare/user/upload.jsp?userId='+userId;
			
    	})
    	
    

		/*****SWFUploader初始化****/
		$(function () {
			var perviewV = new Date().getTime();
			//$("#preview").attr("src","/DataShare/uploads/users/1/avatar_large.jpg?v="+ perviewV);
		    $("#validForm").validate();
			var settings = {
				//upload_url: "/swfUpload/avatarUpload.jspx",
				
				upload_url: "/DataShare/avatarUpload.action;jsessionid=1",
				file_size_limit: "8192 KB",
				file_types: "*.jpg;*.jpeg;*.png;*.gif",
				file_types_description : "Images",
				file_post_name: "file",
				
				flash_url: "/DataShare/user/head/swfupload/swfupload.swf",
				flash9_url: "/DataShare/user/head/swfupload/swfupload_fp9.swf",
				file_upload_limit : 0,
				file_queue_limit : 0,
				custom_settings : {
					progressTarget : "avatarSwfProgress",
					cancelButtonId : "avatarSwfCancel"
				},
				
		        post_params: {"userUpId" : userId },
				debug: false,
				
				button_cursor: SWFUpload.CURSOR.HAND,
				button_width: "100",
				button_height: "100",
				button_placeholder_id: "avatarSwfButton",
				button_window_mode: SWFUpload.WINDOW_MODE.TRANSPARENT,
				button_action: SWFUpload.BUTTON_ACTION.SELECT_FILE,
				
				// The event handler functions are defined in handlers.js
				file_queued_handler : fileQueued,
				// file_queue_error_handler : fileQueueError,
				file_queue_error_handler :function(aaa,bbb,ccc){
					basetip(ccc);
					fileQueueError;
				},
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_complete_handler : uploadComplete,
				upload_success_handler : function(file, serverData) {
					doUploadSuccess(file,serverData,this);
					var data = $.parseJSON(serverData);
					fileUrl = data.fileUrl;
					imgSelectInit();
				}
			};
			var avatarSwfUpload = new SWFUpload(settings);
			var container = 700;
			var fileUrl = null;
			var srcWidth = null;
			var srcHeight = null;
			var scale = 1;
			function imgSelectInit() {
				var imgHtml="<img id='avatarImgSrc' src='"+fileUrl+"?d="+new Date()*1+"' style='position:absolute;top:-99999px;left:-99999px'/>";
				//$(imgHtml).appendTo($("#avatracontent"));
				$(imgHtml).appendTo(document.body).load(function() {
					srcWidth=this.width;
					srcHeight=this.height;
				  if(srcWidth>container || srcHeight>container) {
				    scale = container/srcWidth < container/srcHeight ? container/srcWidth : container/srcHeight;
				  }
					setImgAreaSelect();
					$(this).remove();
				});
			}
			
			function setImgAreaSelect() {
				$("#avatarImg").imgAreaSelect({remove:true}).remove();
				// $("#avatarDiv").empty().hide();
				$("<img id='avatarImg' style='max-height:700px;max-width:700px;'/>").click(function() {
					this.src=fileUrl+'?d='+new Date()*1;
				}).appendTo("#avatarDiv").load(function() {
					//alert(document.body.scrollHeight);
					$('#iframe', parent.document).css("height",document.body.scrollHeight);
				});
				$("#avatarImg").attr("src",fileUrl+"?d="+new Date()*1);
				var ms = 120*scale;
				$("#avatarImg").imgAreaSelect({aspectRatio: "1:1", minWidth: ms, minHeight: ms,x1:0, y1:0, x2:ms, y2:ms, handles: true, onSelectChange: onSelectChange});
				$("#avatarDiv").show();
				$("#avatarForm").show();
				$("#avatarSubmit").css("display","block");
			}
			
			function onSelectChange(img, selection) {
				//selection.width,selection.height,selection.x1,selection.x2,selection.y1,selection.y2
			     if (!selection.width || !selection.height) {
			    	return;
			  	}
				//Math.round()
				$("#left").val(Math.round(selection.x1/scale));
				$("#top").val(Math.round(selection.y1/scale));
				$("#width").val(Math.round(selection.width/scale));
				$("#height").val(Math.round(selection.height/scale));
			}
			
			$("#imgcancel").live("click",function(){
				$("#avatarDiv").hide();
				$("#avatarForm").hide();
				
			})
		$("#avatarSubmit").live("click",function(e){
				$.ajax({
			        type: "post",
			        url: "/DataShare/avatarSave.action",
			        data: {
							 "top":$("#top").val(),
							 "left":$("#left").val(),
							 "width":$("#width").val(),
							 "height":$("#height").val(),
							 "userId":userId
						 },
			        dataType:"json",
			        success: function (msg) {
			        	if(msg.value==1){
			        		$("#avatarDiv").hide();
			        		$(".imgareaselect-outer").css("display","none");
			        		$(".imgareaselect-selection").parent("div").css("display","none");
			        		//addScore(e,8);
		        			$("#preview").attr("src","/DataShare/uploads/users/"+userId+"/avatar_large.jpg?v="+new Date().getTime());
			        		setTimeout(function(){
			        			$("#avatarSubmit").css("display","none");
			        		},1500)
			        	}
			        }
			    })
			})
		});

    </script>

</body>
</html>

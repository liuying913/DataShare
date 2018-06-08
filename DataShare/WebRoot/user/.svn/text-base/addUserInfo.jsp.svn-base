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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/check.js"></script>
    <style type="text/css">
    	.myInfo ul li {width: 500px;;}
    	.myInfo ul li span {width: 60px;}
    	.myInfo ul {margin: 10px 0 0 290px;}
		.tsfun-box a:hover {
			color: #fff;
		}
    </style>
</head>
<body>
  <input type="hidden" id="userId" name="userId" value="${userId}"/>
    <div id="iframeheight">
  		<div class="pbox tspbox">
  			<div class="ptit">用户信息添加</div>
        </div>
            <div class="base-info-input baseInfo myInfo" style="margin-top: 12px;">
            	<fieldset>
                <ul>
                	<li>
                    	<span><b class="requireFlag">*</b>用户名</span>
                        <label><input id="userName" name="userName" type="text" value="" class="input01 require" ></label>
						<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
						<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写用户名</p></div>
                    </li>
                    <li>
                    	<span><b class="requireFlag">*</b>中文名称</span>
                        <label><input id="userCName" name="userCName" type="text" value="" class="input01 require" ></label>
						<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
						<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写中文名称</p></div>
                    </li>
                    
                    <li>
                    	<span><b class="requireFlag">*</b>性别</span>
                        <label>
							<input type="hidden"  name="userGender"   class="input01" id="arrangement" value="男" />
							<div class="ck-switch">
								<span class="ck-switch-on">男</span>
								<span class="ck-switch-off ck-switch-current ck-switch-current-40"></span>
							</div>
						</label>
						<div class="sucuState" style="display:none;"></div>
						<div class="errState" style="display:none;"></div>
                    </li>

                    <li>
                    	<span><b class="requireFlag">*</b>所属单位</span>
                        <label><input id="userUnit" name="userUnit" type="text" value="" class="input01 require" ></label>
						<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
						<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写所属单位</p></div>
                    </li>
                    <li>
                    	<span><b class="requireFlag">*</b>联系方式</span> 
                        <label><input id="userPhone" name="userPhone" type="text"  class="input01 require"></label>
                        <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
						<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写联系方式</p></div>
                    </li>
					<li>
                    	<span><b class="requireFlag">*</b>邮箱</span>
                        <label><input id="userEmail" name="userEmail" type="text" class="input01 require"></label>
                        <div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
						<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写邮箱</p></div>
                    </li>
                </ul>
           	 	</fieldset>
            </div>
            
           <div class="fun-box tsfun-box">
			  <a href="javascript:void(0);"  class="shengfen-button"  id="btn2" style="margin:-7px auto 0;float:none;line-height: 35px;"><!--<img src="/DataShare/img/s2.png"/>-->返回</a>
			  <a href="javascript:void(0);"  class="shengfen-button"  id="btn1" style="margin:-7px auto 0;float:none;line-height: 35px;"><!--<img src="/DataShare/img/s2.png"/>-->保存</a>
		   </div>
      </div>
    <script>
    	$.ajaxSetup({ 
    		//contentType: "application/x-www-form-urlencoded; charset=utf-8",
    		cache: false
    	});//全局禁用缓存
		var userId = $("#userId").val();
		var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;
		var i=0;
		var flag1 =false;
		var flag2 =false;
		var flag_1 = true;
		var flag_2 = true;
		var flag_3 = true;
		var flag_4 = true;
		var flag_5 = true;
		
    	$("#btn1").click(function(){
			i=0;
			str="1=1";
			$(".require").each(function(e){
				if($(this).val()==""){
					$(this).parents("li").find(".errState").show();
					$(this).parents("li").find(".sucuState").hide();
					i=1;
				}else{
					// 添加上这个 && flag_1 && flag_2 && flag_3 && flag_4 && flag_5
					if( flag1 == false && flag2 == false && flag_1 && flag_2 && flag_3 && flag_4 && flag_5 ){
						$(this).parents("li").find(".errState").hide();
					    $(this).parents("li").find(".sucuState").show();
	                    if(e==rlen-1&&i==0){
							$(".input01").each(function(a) {
								str=str+"&"+$(this).prop("name")+"="+$(this).val();
							  	if(a==len-1){
							  	    str = encodeURI(str);
									var URL="/DataShare/insertUserInfo.action?"+str;
									$.ajax({
										url: URL,
										type: "get",
										data: "json",
										beforeSend:function() {
											$('.zbg', window.parent.document).show();
											$('.myload', window.parent.document).show();
										},
										complete:function(){
											$('.zbg', window.parent.document).hide();
											$('.myload', window.parent.document).hide();
										}
									}).done(function(res){
										if(res.date[0].code==1){
											 init("保存成功",0);
											 $(".confirm",window.parent.document).click( function(){
												window.location.href="/DataShare/manager/userInfoListToJsp.action?applyTitle=用户管理";	
	 		                                 })
										}else{
											init( res.date[0].msg ,1 );
										}
									})
								}
							});	
						}
				    }
				}
			})
		})

		$(document).ready(function(){
    		var url=window.location.href;
			//$(".base-info").addClass("base-info-back");
			$(".userName").attr("disabled","disabled");
			ajaxRequest("/DataShare/querUserInfoById.action?userId="+userId,"get",function(res){
				if( res[0].userInfo == null ){
					return false;
				}else{
					$(".input01").eq(0).val(res[0].userInfo.userName);
					$(".input01").eq(1).val(res[0].userInfo.userCName);
					$(".input01").eq(2).val(res[0].userInfo.userGender);
					$(".input01").eq(3).val(res[0].userInfo.userUnit);
					$(".input01").eq(4).val(res[0].userInfo.userPhone);
					$(".input01").eq(5).val(res[0].userInfo.userEmail);
					$(".input01").eq(6).val(res[0].userInfo.regDateStr);
					$(".input01").eq(7).val(res[0].userInfo.userTypeStr);
				}
			})
		
			//返回
			$("#btn2").click(function(){
				var url="/DataShare/manager/userInfoListToJsp.action?applyTitle=用户管理";
				window.location.href=url;	
			})

			$('.ck-switch .ck-switch-off').unbind('click').bind('click',function(){
				$('.ck-switch').addClass('active');
				$('.ck-switch .ck-switch-on').addClass('ck-switch-current ck-switch-current-3').html('');
				$('.ck-switch .ck-switch-off').removeClass('ck-switch-current ck-switch-current-40').html('女');
				$("#arrangement").val( "女");
			});

			$('.ck-switch .ck-switch-on').unbind('click').bind('click',function(){
				if($(this).hasClass('ck-switch-on')){
					$('.ck-switch').removeClass('active');
					$('.ck-switch .ck-switch-on').removeClass('ck-switch-current ck-switch-current-3').html('男');
					$('.ck-switch .ck-switch-off').addClass('ck-switch-current ck-switch-current-40').html('');
					$("#arrangement").val( "男");
				}
			});


			/*判断用户名是否重复   + 校验*/
			
            $("#userName").blur(function(){
            	 //****校验用户名****
            	 var apply_Person = $("#userName").val();
	             var checkObj = $("#userName").parent();
	             if(!checkEnName(apply_Person)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为英文字母");
	                 flag_1 = false;
	                 return;
	            }else{
	             	if(!checkLength(apply_Person)){
		                 checkObj.siblings(".errState").show();
		                 checkObj.siblings(".sucuState").hide();
		                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
		                 flag_1 = false;
		                 return;
		            }else{
		             	checkObj.siblings(".sucuState").hide();
						checkObj.siblings(".errState").hide();
						flag_1 = true;
		            }
	            }
	            //****校验用户名结束****
            
                 var userName = $("#userName").val()
                 var URL1 ="/DataShare/user/checkUserNameRepeat.action?userName="+userName;
                 ajaxRequest(URL1,"get",function(res){
                    if(res.date[0].code== -1 && userName != ''){
                        $("#userName").parent().siblings(".errState").show();
                         $("#userName").parent().siblings(".sucuState").hide();
                         $("#userName").parent().siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>用户名重复")
                         flag1 =true;
                         return;
					}else{
						 $("#userName").parent().siblings(".sucuState").hide();
						 $("#userName").parent().siblings(".errState").hide();
						 flag1 =false;
					}
                 })                          
             })


             /*判断中文名称是否重复  +  校验*/
             //var flag_2 = true;
             $("#userCName").blur(function(){
             	 //****校验中文名****
	             var apply_Person = $("#userCName").val();
	             var checkObj = $("#userCName").parent();
	             if(!checkCNName(apply_Person)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须包含中文字符");
	                 flag_2 = false;
	                 return;
	             }else{
	             	if(!checkLength(apply_Person)){
		                 checkObj.siblings(".errState").show();
		                 checkObj.siblings(".sucuState").hide();
		                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
		                 flag_2 = false;
		                 return;
		            }else{
		             	checkObj.siblings(".sucuState").hide();
						checkObj.siblings(".errState").hide();
						flag_2 = true;
		            }
	            }
	            //****校验用户名结束****
             
                var userCName = $("#userCName").val()
                userCName = encodeURI(userCName);
                var URL2="/DataShare/user/checkUserNameRepeat.action?userCName="+userCName;
                 ajaxRequest(URL2,"get",function(res){
                    if(res.date[0].code== -1 && userCName != ''){
                    	 $("#userCName").parent().siblings(".errState").show();
                    	 $("#userCName").parent().siblings(".sucuState").hide();
                         $("#userCName").parent().siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>中文名称重复")
                         flag2 =true;
                         return false;
					}else{
						 $("#userCName").parent().siblings(".errState").hide();
						 $("#userCName").parent().siblings(".sucuState").hide();
						 flag2 =false;
					}
                 })
             })
    	})
    	
        
        //所属单位校验
        //var flag_3 = true;
   		$("#userUnit").blur(function(){
             var apply_Person = $("#userUnit").val();
             var checkObj = $("#userUnit").parent();
             if(!checkLengthParam(apply_Person,2,20)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到2个字节");
                 flag_3 = false;
                 return;
            }else{
             	checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_3 = true;
            }
        });
        
        
        //联系方式校验
        ///var flag_4 = true;
   		$("#userPhone").blur(function(){
             var apply_Person = $("#userPhone").val();
             var checkObj = $("#userPhone").parent();
             if(checkMobile(apply_Person) || checkPhone(apply_Person)){
                checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_4 = true;
            }else{
				checkObj.siblings(".errState").show();
                checkObj.siblings(".sucuState").hide();
                checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>请输入正确的手机或电话号码");
                flag_4 = false;
                return;
            }
        });
        
        
        //邮箱校验
        //var flag_5 = true;
   		$("#userEmail").blur(function(){
             var apply_Person = $("#userEmail").val();
             var checkObj = $("#userEmail").parent();
             if(!checkMail(apply_Person)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>请输入正确的邮箱");
                 flag_5 = false;
                 return;
            }else{
	            if(!checkLengthParam(apply_Person,1,30)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度不能超过30个字节");
	                 flag_5 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_5 = true;
	            }
            }
        });
    </script>
</body>
</html>

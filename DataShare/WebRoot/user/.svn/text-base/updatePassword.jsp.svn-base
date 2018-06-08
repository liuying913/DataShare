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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
</head>
<body>
  <input type="hidden" id="userId" name="userId" value="${userId}"/>
  <div id="iframeheight">
  		<div class="pbox tspbox">
        	<div class="ptit">修改密码</div>
            <div class="base-info tsbase-info">
				<form action="/DataShare/updateUserInfo.action" method="get">
					<div class="base-info-input">
							<ul style="padding-left:100px;">
                        	<li>
                                <span>原密码</span>
                                <label><input id="oldUserPwd" name="oldUserPwd" type="password" value="" class="input01 require" err="请填写原密码"></label>
                                <div id="result_Name" style="display:none;"><p class="tishi"><img src="/DataShare/img/wrong_0131.jpg">请填写原密码</p></div>
                            </li>
                            <li>
                               <span>新密码</span>
			                   <label><input id="newUserPwd" name="newUserPwd" type="password" value="" class="input01 require" err="请填写新密码"></label>
			                   <div id="result_Name" style="display:none;"><p class="tishi"><img src="/DataShare/img/wrong_0131.jpg">请填写新密码</p></div>
                            </li>
                            <li>
                                <span>新密码</span>
			                    <label><input id="newUserPwd2" name="newUserPwd2" type="password" value="" class="input01 require" err="两次密码不一致"></label>
			                    <div id="result_Name" style="display:none;"><p class="tishi"><img src="/DataShare/img/wrong_0131.jpg">两次密码不一致</p></div>
                            </li>
                            <li>
                                	<span>&nbsp;&nbsp;&nbsp;</span>
									<label>
									
										<div>
								              <button type="button" id="back" class="shengfen-button" style="margin-top:5px;text-align:center;float:none;display: inline-block;width:111px;height: 26px;"><!--<img src="/DataShare/img/s2.png"/>-->保存</button>
								        </div>
								        
                                    	
                					</label>
                             </li>
						</ul>
						<div class="clear"></div>
					</div>
					<input id="uOp" name="uOp" type="hidden" value="pas">
                </form> 
            </div>
        </div>
        <div class="blank10"></div>
        </div>
        
    <script>		$.ajaxSetup({ cache: false });//全局禁用缓存
	    var userId = $("#userId").val();
		var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;
		var i=0;
    	$("#back").click(function(){
			i=0;
			$(".require").each(function(e){
				if($(this).val()==""){
					$(this).addClass("err");
					$(this).parents("li").find("div").show();
					i=1;
				}else{
					$(this).removeClass("err");
					$(this).parents("li").find("div").hide();
					if(e==rlen-1&&i==0){
						$(".input01").each(function(a) {
							str=str+"&"+$(this).prop("name")+"="+$(this).val();//str=str+"/"+$(this).name+':'+$(this).attr("name")+" "+$(this).prop("name");
						  	if(a==len-1){
						  		str = encodeURI(str);
								var URL="/DataShare/updateUserPassword.action?userId="+userId+str;
								//alert(URL);
								ajaxRequest(URL,"get",function(res){
								    //alert(res.date[0].code);
									if(res.date[0].code!=-1){
										window.location.href="/DataShare/passwordSuccessToJsp.action?applyTitle=修改成功！";	
									}else{
										alert(res.date[0].msg);	
									}
								});
								str="";
							}  
						});	
					}
				}
			})
		})
    </script>
</body>
</html>

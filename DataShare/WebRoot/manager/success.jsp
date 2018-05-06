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
  <input type="hidden" id="title" name="title" value="${title}"/>
  <input type="hidden" id="message" name="message" value="${message}"/>
  <input type="hidden" id="url" name="url" value="${url}"/>
  <input type="hidden" id="userId" name="userId" value="${userId}"/>
  <div id="iframeheight">
  		<div class="pbox tspbox">
        	<div class="ptit">${title}</div>
            <div class="base-info tsbase-info">
				<form action="/DataShare/updateUserInfo.action" method="get">
					<div class="base-info-input">
						<ul  class="clearfix" style="margin-left:220px;">
                        	<li style="width: 178px; font-size: 16px;margin-left: 144px;color: #666;">
	                            <div class="fun-box tsfun-box">
									<span class="successState3"></span>
	                 			      ${message}
	             				</div>
                            </li>
						</ul>
						<div class="clearfix">
							<button type="button" id="back" class="shengfen-button" style="margin-top:15px;margin-left:410px;text-align:center;float:none;display: inline-block;width:110px;height: 35px;border-radius: 5px"><!--<img src="/DataShare/img/s2.png"/>-->返回</button>
						</div>
						<div class="clear"></div>
					</div>
                </form> 
            </div>
        </div>
        <div class="blank10"></div>
        </div>
        
    <script>
    	var url = $("#url").val();
    	$("#back").click(function(){
			window.location.href=url;	
		})
    </script>
</body>
</html>

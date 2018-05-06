<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>GNSS数据资源共享与信息发布平台</title>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
	<link rel="stylesheet" type="text/css" href="/DataShare/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css"/>
</head>
<body>
    <!--  ==========  -->
	<!--  ==顶部==  -->
	<%@ include file="../../head.jsp" %>
	
    <div class="banner_son">
		<img src="/DataShare/img/img_zxjj.jpg" />
    </div>
    <!--  ==========  -->
    <!--  ==页面主体==  -->
    <!--  ==========  -->
    <div class="ContactUs-main">
        <div class="ContactUs-header">
            <div class="header-tit">联系我们</div>
            <div class="header-nav"><a href="/DataShare/index.action">主页></a><a href="#" class="onactive">联系我们</a></div>
        </div>
		
        <div style="float:left;width:70%">
			<img src="/DataShare/img/address.png"/>
		</div>
       

		
		<div style="float:right;margin-top: 15px;width:25%;float:right; color: #666666; font-size: 14px; line-height: 25px; letter-spacing: 0.5px;">
            <div>
		                地址：北京市西城区三里河路56号<br>
		                电话：010-68512355<br>
		                手机：18600781413<br>
		                传真：010-68512203<br>
		        Email：cmonoc@seis.ac.cn<br>
		                邮编：100045
		        <br />
		                合影照片！！！
            </div>
            <!--<img src="/DataShare/img/contactus-pic.png" />-->
        </div>

        
    </div>
	<!--  = 底部 =  -->
	<%@ include file="../../foot.jsp" %>
	
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
</body>
</html>

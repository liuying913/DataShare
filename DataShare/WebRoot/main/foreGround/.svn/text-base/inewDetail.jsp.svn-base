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
            <div class="header-tit">新闻动态</div>
            <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="/DataShare/index.action?id=3" class="onactive">新闻动态</a></div>
        </div>
        <!--左边导航-->
		</br>
       	<h3 class="ntit"></h3>
        <div class="ncon">
        	
        </div>
    </div>
    <!--  ==========  -->
    <!--  = 底部 =  -->
	<%@ include file="../../foot.jsp" %>
     
    <style>
    	h3.ntit{ color:#2867b2;font-size:18px;font-weight:bold;line-height:44px; font-family:"微软雅黑";text-align:center;}
		.ncon{font-size:15px; color:#666666; line-height:28px; text-align:left;font-family: 微软雅黑;}
		.ncon{width:100%;display:block;} 
    </style>
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script>  		$.ajaxSetup({ cache: false });//全局禁用缓存
		var url=document.location.href; 
		var id=url.split("=")[1];
    	$.ajax({
		url: "/DataShare/news/getNewsInoDetail.action?id="+id,
			type: "get",
			data: "json",
		}).done(function (data) {
			$("h3.ntit").text(data.newsList[0].News[0].title);
			$(".ncon").html(data.newsList[0].News[0].content);
		})
    </script>
</body>
</html>

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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
</head>
<body>
    <input type="hidden" id="id" name="id" value="${id}"/>
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
            <div class="header-tit">数据动态</div>
            <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="#" class="onactive">数据动态</a></div>
        </div>
        <!--左边导航-->
		</br>
       	<h3 class="ntit"></h3>
        <div class="ncon"></div>
        
        <div id="earthQuakeTimeStr" class="ncon"></div>
        <div id="siteLat" class="ncon"></div>
        <div id="siteLon" class="ncon"></div>
        <div id="grade" class="ncon"></div>
        <div id="height" class="ncon"></div>
        <div id="address" class="ncon"></div>
        <div id="remark" class="ncon"></div>
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
    <script>  		
    	$.ajaxSetup({ cache: false });//全局禁用缓存
		var id = $("#id").val();
    	$.ajax({
		url: "/DataShare/news/data30_DataQualityDetail.action?id="+id,
			type: "get",
			data: "json",
		}).done(function (data) {
			$("h3.ntit").text(data[0].title);
			$(".ncon").html(data[0].content);
			
			$("#earthQuakeTimeStr").html("发生时间："+data[0].earthQuakeTimeStr);
			$("#siteLat").html("经度："+data[0].siteLat);
			$("#siteLon").html("纬度："+data[0].siteLon);
			$("#grade").html("震级："+data[0].grade);
			$("#height").html("深度："+data[0].height+"千米");
			$("#address").html("地址："+data[0].address);
			$("#remark").html("备注："+data[0].remark);
		})
    </script>
</body>
</html>

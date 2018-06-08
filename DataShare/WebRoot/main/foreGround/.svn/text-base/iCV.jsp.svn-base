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
	<!--  ==顶部==  -->
	<%@ include file="../../head.jsp" %>
	
	
	<div class="banner_son"></div>
    <!--  ==========  -->
    <!--  ==页面主体==  -->
    <!--  ==========  -->
    <div class="CenterP-main">
    
         <div class="leftbar">
       <div class="leftbar_top"><h3>平台简介</h3></div>
        <!--左侧弹性竖菜单-->
        <div id="menu">
	        <h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_10"><a href="/DataShare/index.action?id=2" class="active">平台简介</a></h1>
	        <h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_11"><a href="/DataShare/index.action?id=21">中心简介</a></h1>
	        <h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_12"><a href="/DataShare/index.action?id=22">共享理念</a></h1>
        </div>
		
     </div>
    
        
        <!--右边内容-->
        <div class="CenterP-right">
            <div class="CenterP-right-header">
                <div class="header-tit">平台简介</div>
                <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="/DataShare/index.action?id=2">平台简介></a><a href="/DataShare/index.action?id=2" class="onactive">平台简介</a></div>
            </div>
            <div class="CenterP-right-content">
              GNSS数据资源共享与信息发布平台：依托以中国大陆构造环境监测网络为纽带的全国GNSS数据资源共享任务,结合地壳运动监测工程研究中心的相关业务，建设对海量GNSS数据进行有效整理、预处理、计算、共享及结果发布的数据共享与信息发布平台。
			    该平台需要对汇集在北京数据共享中心的全国GNSS数据资源进行实时采集、实时汇集、自动整理、实时共享、解析、计算和存储等，同时对数据资源的使用和共享进行管理统计，并提供多元的可视化发布手段。
            </div>
            <img style="margin-top: 20px; width: 645px;" src="/DataShare/img/zxjj-pic.png" />
        </div>
    </div>
    
	<!--  = 底部 =  -->
	<%@ include file="../../foot.jsp" %>
	
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script>
        $("#c_"+10).addClass("on");
        $("#c_"+10).parent().css('display','block');
        $("#c_"+10).parent().parent().css('display','block');
        </script>
        <!--左侧弹性菜单结束-->
		<script>
            $("#c_"+10).addClass("on");
        </script>
</body>
</html>

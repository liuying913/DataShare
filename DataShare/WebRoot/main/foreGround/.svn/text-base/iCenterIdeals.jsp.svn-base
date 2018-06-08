<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8" />
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
                <div class="header-tit">共享理念</div>
                <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="/DataShare/index.action?id=2">平台简介></a><a href="/DataShare/index.action?id=22" class="onactive">共享理念</a></div>
            </div>
            <div class="CenterP-right-content">
               地壳运动工程研究中心以服务于国家防震减灾事业、国民经济建设、社会减灾、科学研究和国防建设为宗旨；
			   以"科学、民主、规范、开放、透明"为管理思想，实行权责分明、激励和约束的管理机制，依法自主运行；
			   以"务实求效、开拓奋进、创新报国、追求卓越"为团队文化。
			   地壳工程中心实行主任领导下的首席负责制、项目总工程师或项目首席科学家科学技术负责制、总会计师财务负责制。
            </div>
            <img style="margin-top: 20px; width: 645px;" src="/DataShare/img/zxjj-pic.png" />
        </div>
    </div>
    
	<!--  = 底部 =  -->
	<%@ include file="../../foot.jsp" %>
	
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script>
        $("#c_"+12).addClass("on");
        $("#c_"+12).parent().css('display','block');
        $("#c_"+12).parent().parent().css('display','block');
        </script>
        <!--左侧弹性菜单结束-->
		<script>
            $("#c_"+12).addClass("on");
    </script>
</body>
</html>

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
	        <h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_10"><a href="/DataShare/index.action?id=2">平台简介</a></h1>
	        <h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_11"><a href="/DataShare/index.action?id=21" class="active">中心简介</a></h1>
	        <h1 onClick="javascript:ShowMenu(this,'NO2')" id="c_12"><a href="/DataShare/index.action?id=22">共享理念</a></h1>
        </div>
		
     </div>
    
        
        <!--右边内容-->
        <div class="CenterP-right">
            <div class="CenterP-right-header">
                <div class="header-tit">中心简介</div>
                <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="/DataShare/index.action?id=2">平台简介></a><a href="/DataShare/index.action?id=21" class="onactive">中心简介</a></div>
            </div>
            <div class="CenterP-right-content">
                地壳运动监测工程研究中心是中国地震局直属的集项目管理、工程建设和科学研究三位一体的事业单位。承担的主要任务是负责
                国家建设项目"中国数字地震观测网络"项目的组织和管理；承担中国地震局、总参测绘局、中国科学院、国家测绘局和中国气象局五部
                委联合承建"中国地壳运动观测网络"的项目法人职责；国家重大基础设施建设项目"陆构造环境监测工程"的立项申请工作。 地壳工程
                中心以服务于国家防震减灾事业、国民经济建设、社会减灾、科学研究和国防建设为宗旨；以"务实求效、开拓奋进、创新报国、追求
                卓越"为团队文化。
            </div>
            <img style="margin-top: 20px; width: 645px;" src="/DataShare/img/zxjj-pic.png" />
        </div>
    </div>
    
	<!--  = 底部 =  -->
	<%@ include file="../../foot.jsp" %>
	
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script>
        $("#c_"+11).addClass("on");
        $("#c_"+11).parent().css('display','block');
        $("#c_"+11).parent().parent().css('display','block');
        </script>
        <!--左侧弹性菜单结束-->
		<script>
            $("#c_"+11).addClass("on");
        </script>
</body>
</html>

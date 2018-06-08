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

 <div class="bg"></div>
 <div class="tck">
 	<div class="close"> <img src="/DataShare/img/searchclose.png"></div>
 	<h3>用户登录</h3>
    <div class="line"></div>
    <ul>
    	<li><span>用户名：</span><input type="text" id="userName"></li>
    
    	<li><span>密　码：</span><input type="password" id="userPwd"></li>
    </ul>
    <p class="cuowu" id="msgid">用户名或密码错误！</p>
    <!--
    <p class="kong">请输入用户名密码！</p
    -->
    <div class="zcwjmm"><!--
        <a class="zc" href="/DataShare/index.action">注册</a>
        <a class="wjmm" href="/DataShare/index.action">忘记密码</a>
    --></div>
    <button id = "submit" type="submit">登 录</button>

 </div>
    <input type="hidden" id="board_id" name="board_id" value="${board_id}"/>
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
            <div class="header-tit">意见建议</div>
            <div class="header-nav">
                <a href="/DataShare/index.action?id=1">主页></a>
                <a href="/DataShare/index.action?id=9" class="onactive">意见建议</a>
            </div>
        </div>
        <!--左边导航-->
        <div id="jy"></div>
    </div>
    <div>
    	<input type="hidden" id="userType" value="${sessionScope.userType}">
    	<input type="hidden" id="userCName" value="${sessionScope.userInfo.userCName}">
    	
    </div>
    <!--  ==========  -->
    <!--  ==顶部==  -->
    <%@ include file="../../foot.jsp" %>
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/base.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>

    <script>var board_id = $("#board_id").val(); $.ajaxSetup({ cache: false }); //全局禁用缓存 </script>
    <div class="zbg" style="display:none;"></div>
        <!-- <div class="myload" style="display:none;">数据加载中,请稍后...<br/><img src="/DataShare/img/pcgzs/load.gif"></div> -->
        <div class="mytips" style="display:none;">留言成功</div>
</body>
</html>
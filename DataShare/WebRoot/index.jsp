<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
	<title>GNSS数据资源共享与信息发布平台</title>
	<link rel="stylesheet" type="text/css" href="/DataShare/css/css.css"/>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css"/>
  </head>
 <body>
 <div class="bg"></div>
 <div class="tck">
 	<div class="close"> <img src="/DataShare/img/searchclose.png"></div>
 	<h3>用户登录</h3>
    <div class="line"></div>
    <ul>
    	<li><span>用户名：</span><input type="text" id="userName" style="border:1px solid #ccc;width:200px;"></li>
    	<li><span>密　码：</span><input type="password" id="userPwd" style="border:1px solid #ccc;width:200px;"></li>
    </ul>
    <p class="cuowu" id="msgid">用户名或密码错误！</p>
    <button id = "submit" type="submit">登 录</button>
 </div>
		<!--  ==顶部==  -->
		<jsp:include page="head.jsp" flush="true" />
		
		<!--  ==页面主体==  -->
		<div class="index-main">
			<div class="index-banner">
				<div class="mian-01 antialias"><img src="/DataShare/img/001.jpg"/></div>
				<!--<div class="antialias"><img src="/DataShare/img/000.jpg"/></div>
				<div class="antialias"><img src="/DataShare/img/img-01.png"/></div>
				--><div class="main-warp">
					<!--<div class="mian-container-left"><img src="/DataShare/img/index-left.png"/></div>
					<div class="mian-container-right"><img src="/DataShare/img/index-right.png"/></div>
				--></div>
			</div>
                <!--banner下边的大导航-->
              <div class="link_two">
                <ul>
                  <li><a href="/DataShare/index.action?id=6"><img src="/DataShare/img/index-img-16.png"/>共享指南</a></li>
                  <li><a href="/DataShare/index.action?id=7"><img src="/DataShare/img/index-img-17.png"/>数据动态</a></li>
              <!--<li><a id="sjsq_from" class="sjsq_z" ><div class="sjsq_img"><img src="/DataShare/img/index-img-18.png"/></div>数据申请</a></li> -->
                  <li><a id="sjsq_from" style="font-family : 微软雅黑,宋体;font-size:16px;color : #fff;text-decoration: none;"><img src="/DataShare/img/index-img-18.png"/>共享平台</a></li>
                  <li><a href="/DataShare/index.action?id=8"><img src="/DataShare/img/index-img-19.png"/>软件下载</a></li>
                  <li><a href="/DataShare/index.action?id=9"><img src="/DataShare/img/index-img-20.png"/>意见建议</a></li>
                  <!-- <li><a href="/DataShare/message/boardPageToJsp.action"><img src="/DataShare/img/index-img-20.png"/>意见建议</a></li> -->
                  <li style="width:1px; padding:0px;"><a href="#">&nbsp;</a></li>
                </ul>
              </div>
            
            
            <!--正文-->
            <div class="zhongjian">
               <div class="content">
                  <div class="zxdt">
                    <h3>新闻动态<a href="/DataShare/index.action?id=3">更多<!-- <img src="/DataShare/img/more.png" /> --></a></h3>
                    <ul id="newswenzi"></ul>
                  </div>
                  
                  <div class="zxjj">
                     <h3>平台简介<a href="/DataShare/index.action?id=2">更多<!-- <img src="/DataShare/img/more.png" /> --></a></h3>
                     <div class="zxjjwz">
                     　　<div class="zxjjwzimg"><img src="/DataShare/img/index-img-08.png"/></div>
                         <div class="zxjjfont">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;本依托于以中国大陆构造环境监测网络为纽带的全国GNSS数据资源共享任务,结合地壳运动监测工程研究中心的相关业务，建设对海量GNSS数据进行有效整理、预处理、计算、共享及结果发布的数据共享与信息发布平台。该平台需要对汇集在北京数据共享中心的全国GNSS数据资源进行实时采集、实时汇集、自动整理、实时共享、解析、计算和存储等，同时对数据资源...</div>
                     </div>
                  </div>
               </div>
            </div>
            <!--end 中间-->
		</div>
		<!--  = 底部 =  -->
		<jsp:include page="foot.jsp" flush="true" />
		<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
		<script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
        <script src="/DataShare/js/sjsq.js" type="text/javascript" ></script>
        <script src="/DataShare/js/dengl.js" type="text/javascript" ></script>
       <script type="text/javascript" src="/DataShare/js/extra.js"></script>
       <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>

 <script>$.ajaxSetup({ cache: false });//全局禁用缓存
     $(document).ready(function(){
         ajaxRequest("/DataShare/news/getNews30DayFlag.action?type=1&json","get",function(res) {
             data = res;
             var News = data.newsList[0].News;
             var str = "";
             
             var newNumbers = News.length;
             if(News.length>=7){newNumbers=7;}
             
             for( var i=0;i<newNumbers;i++){
            	 
            	 var title = News[i].title;
                 if(title.length>22){
                	 title= title.substring(0,22)+"...";
                 }
                 
                 if(News[i].flags.indexOf('in30')>-1){
                 	str +=" <li><span>"+News[i].time+"</span><a href='/DataShare/news/newsDetailToJsp.action?id="+News[i].id+"'>"+title+"<img src='/DataShare/img/new.jpg' /></a></li>";
                 }else{
                 	str +=" <li><span>"+News[i].time+"</span><a href='/DataShare/news/newsDetailToJsp.action?id="+News[i].id+"'>"+title+"</a></li>";
                 }
             }
             $("#newswenzi").append( str );
         })
     })
 </script>
	</body>
</html>

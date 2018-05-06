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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css"/>
    <style>
        .newstable-content .newlist {
            font-size: 13px; padding: 0px 0 20px 0px;  display: inline-block; margin-top: 15px;
            width: 100%;
        }
        .newlist li{
            line-height: 30px;
            text-align: left;
            border-bottom: 1px dotted #ccc;
            padding: 4px 0;
            margin: 0 auto;
        }
        .newlist li a{
            background: url(img/blackjt.png) no-repeat left top;
            display: inline-block;
            padding-left: 16px;
            color: #6c6c6c;
            font-size: 14px;
        }
        .newlist li span{float: right;color: #7a7a7a;  margin:  0 5px 0 0 ;}
    </style>
</head>

<body>
	<!--  ==顶部==  -->
	<%@ include file="../../head.jsp" %>
    <div class="banner_son">
		<img src="/DataShare/img/img_zxjj.jpg" />
    </div>
    <!--  ==页面主体==  -->
    <div class="newstable-main">
        <div class="ContactUs-header">
            <div class="header-tit">数据动态</div>
            <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="/DataShare/index.action?id=7" class="onactive">数据动态</a></div>
        </div>
        <div class="newstable-content">
            <div class="newslist-all">
                <ul class="newlist"></ul>
            </div>
            <!--分页-->
            <div class="newstable-fy">
                <div class="newstable-fy-pages">
                    <span>共<font color="#ff0000" id="zongyeshu"></font>页<font color="#ff0000" id="zongshuju"></font>条记录</span>
                    <a href="#" onclick="yeshu(1)">首页</a>
                    <a href="#" id="prevpage" onclick="shang();">上一页</a>
                    <span class="everypages">
                    </span>
                    <a href="#" onclick="next();">下一页</a>
                    <a href="#" onclick="yeshu('end')">末页</a>
                    <span>跳转至
                        <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);" class="optionpages">
                        </select>
                    </span>
                </div>
            </div>
        </div>
    </div>
    <!--  ==========  -->
	<!--  = 底部 =  -->
	<%@ include file="../../foot.jsp" %>
	
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/pageJson.js" type="text/javascript" charset="utf-8"></script>
    <script>
     $(function(){
        pages("/DataShare/news/data30_DataQualityList.action");
     })
	
    $.ajaxSetup({ cache: false });//全局禁用缓存
    </script>
</body>
</html>

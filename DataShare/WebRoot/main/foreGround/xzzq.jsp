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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css"/>
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
    <div class="newstable-main">

        <div class="newstable-header">
            <div class="header-tit">软件下载</div>
            <div class="header-tit"  style="margin-left:440px;">常用软件</div>
            <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="#" class="onactive">软件下载</a></div>
        </div>
        <div class="newstable-content1" style="margin-top: 30px;">
            <div class="newslist-all clearfix">
                <ul class="newlist myList"></ul>
                <ul class="myList" style="margin-left: 60px;">
                    <li><a href="" target="_blank">天宝接收机使用说明</a><span></span></li>
                    <li><a href="" target="_blank">压缩软件使用说明</a><span></span></li>
                    <li><a href="" target="_blank">重力仪使用说明</a><span></span></li>
                    
                </ul>
            </div>
            <!--分页-->
               <!-- <div class="newstable-content" >
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
                        <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);" class="optionpages"></select>
                    </span>
                </div> -->
            </div>
         </div>
           
        </div>
    </div>
    <!--  ==========  -->
    <!--  = 底部 =  -->
    <%@ include file="../../foot.jsp" %>
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/doc/down.js" type="text/javascript" charset="utf-8"></script>
    <script>		$.ajaxSetup({ cache: false });//全局禁用缓存
        window.onload=function(){
            pages("/DataShare/queryDocUseInfoList.action");
            $('.footerout').css({marginTop:175})
        }
    </script>
</body>
</html>

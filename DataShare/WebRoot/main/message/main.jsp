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
	<style>
.yinjian{background:#EEF1F6;height:75px;margin-top:10px;}
.yinjian .yinjian_left{width:520px;margin-left:20px;float: left;font-size: 15px;color: #666;margin-top: 9px;}
.yinjian .yinjian_left p{line-height:28px;}
.yinjian .yinjian_left span{color:#0066FF;}
.yinjian .yinjian_right{float:right;margin-right:20px;margin-top: 17px;}
.yinjian .yinjian_right a{   background: #306bb5;
    color: #fff;
    width: 160px;
    height: 38px;
    line-height: 38px;
    font-size: 16px;
    text-align: center;
    border: none;
    float: right;
    margin-right: 15px;
}

.yinjian_ly{background:#EEF1F6;padding:10px 0;margin-top:15px;text-align: center; display: none;}
.yinjian_ly input.ly_tit ,.yinjian_ly textarea{width:98%;height:35px;line-height:35px;border:1px solid #ccc;margin:10px auto 5px 0;text-indent: 15px;}
.yinjian_ly textarea{height: 130px;padding: 15px; width: 95%;text-indent: 0px;}
.yinjian_ly .ly_ty{text-align: right;
    width: 300px;
    font-size: 12px;
    margin: 5px auto;
    float: left;}
.yinjian_ly .ly_ty a{color:#d73240;}
.yinjian_ly .ly_lx{width: 65%;
    color: #888;
    text-align: left;
    margin-top: 20px;
    margin-left: 20px;
    float: left;
}
.yinjian_ly .ly_lx input{height:28px;line-height:28px;border: 1px solid #ddd;text-indent: 4px;font-size: 12px;}
.yinjian_ly .ly_ty input.ly_sub{float:right;width:150px;background:#ccc;text-align:center;border:0;color:#fff;font-size:16px;height:34px;cursor: pointer;}
.yinjian_ly .ly_ty input.selected{background:#306bb5;}

.yinjian_cont ul li{line-height: 25px;border: #d5d5d5 1px solid;width: 145px;height: 100px;float: left;margin: 15px 26px 0 0;padding:10px  12px;}
.yinjian_cont ul li a{ width: 100%;height: 30px;line-height: 30px;overflow: hidden;display: block; }
.yinjian_cont ul li b{display: block;font-size: 16px;color: #212121;font-weight: normal;}
.yinjian_cont ul li p{font-size: 14px;color: #7a7a7a;}
.yinjian_cont ul li p i{color: #d73240;font-style: normal;}
</style>
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
            <div class="header-tit">意见建议</div>
            <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="/DataShare/index.action?id=9" class="onactive">意见建议</a></div>
        </div>
        <!--左边导航-->
		<div class="yinjian">
			<div class="yinjian_left">
				<!-- <p>历史留言总量：<span>111</span>&nbsp;&nbsp;历史回复总量：<span>111</span>&nbsp;&nbsp;历史浏览总量：<span>111</span></p>
				<p>本月留言总量：<span>111</span>&nbsp;&nbsp;本月回复总量：<span>111</span>&nbsp;&nbsp;本月浏览总量：<span>111</span></p> -->
				
			</div>
			<div class="yinjian_right">
				<a id="yinjian_a">我要留言</a>
				<a href="/DataShare/message/boardPageToJsp.action">查看更多</a>
			</div>
		</div>
		<div class="yinjian_ly">
			<input type="text" value="" class="ly_tit" name="" placeholder="请填写标题">
	<%--		<textarea   placeholder="请输入留言内容（请清晰叙述,不得超过2000字）"> </textarea>--%>
            <textarea name="content" id="content" rows="6" style="color: #aaa;" onfocus="if(this.value.substring(0, 3) == '请输入') {this.value='';}this.style.color='#333';" onblur="if(this.value=='') {this.value='请输入留言内容（请清晰叙述,不得超过2000字）';this.style.color='#ccc';}">请输入留言内容（请清晰叙述,不得超过2000字）</textarea>
	      <div class="clearfix">
              <p class="ly_lx">
                 <span style="color: red;">*</span>
                  联系方式&nbsp;&nbsp;&nbsp;&nbsp;<input type="text" placeholder="真实姓名" value='' name='name'>
                  &nbsp;&nbsp;&nbsp;&nbsp;<input placeholder="联系电话" type="text" value='' name='phone'>
                  &nbsp;&nbsp;仅供工作人员查看，不对外公开
                  <%--    <input class='ly_sub' type="submit" value='提交留言'>--%>
              </p>
              <p class="ly_ty">
                  <input type="checkbox" name='gltl'   id="ischeck" style="margin-right: 5px;">我已同意<a href="javascript:void(0)">《留言板管理条例》</a>
                  <input class='ly_sub' type="submit"  disabled = "disabled"  value='提交留言' style="margin-top: 10px;">
              </p>
          </div>
			
		</div>
		<div class="yinjian_cont  clearfix">
			<ul></ul>
		</div>
    </div>
    <!--  ==========  -->
	<!--  ==顶部==  -->
	<%@ include file="../../foot.jsp" %>
	
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/main.js" type="text/javascript" charset="utf-8"></script>
 <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
    <div class="zbg" style="display:none;"></div>
        <!-- <div class="myload" style="display:none;">数据加载中,请稍后...<br/><img src="/DataShare/img/pcgzs/load.gif"></div> -->
    	<div class="mytips" style="display:none;">留言成功</div>
<%--    	<div class="mytips1" style="display:none;">留言内容不能为空</div>--%>

 <script>		
     $.ajaxSetup({ cache: false });//全局禁用缓存
     $(document).ready(function(){
         var doc=document,
                 inputs=doc.getElementsByTagName('input'),
                 supportPlaceholder='placeholder'in doc.createElement('input'),

                 placeholder=function(input){
                     var text=input.getAttribute('placeholder'),
                             defaultValue=input.defaultValue;
                     if(defaultValue==''){
                         input.value=text
                     }
                     input.onfocus=function(){
                         if(input.value===text)
                         {
                             this.value=''
                         }
                     };
                     input.onblur=function(){
                         if(input.value===''){
                             this.value=text
                         }
                     }
                 };

         if(!supportPlaceholder){
             for(var i=0,len=inputs.length;i<len;i++){
                 var input=inputs[i],
                         text=input.getAttribute('placeholder');
                 if(input.type==='text'&&text){
                     placeholder(input)
                 }
             }
         }
     });
 </script>
</body>
</html>

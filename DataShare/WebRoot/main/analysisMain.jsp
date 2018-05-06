<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" type="text/css" href="/DataShare/css/css.css"/>
        <link rel="stylesheet" type="text/css" href="/DataShare/css/nhead.css"/>
		<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
		<script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    	<script type="text/javascript" src="/DataShare/js/extra.js"></script>
		<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
		<script type="text/javascript" src="/DataShare/js/timeCommon/todayTime.js"></script>
		<script src="/DataShare/js/js.js" type="text/javascript" charset="utf-8"></script>
	</head>
	<body>
		<!-- toper -->
		
		<div class="toper">
			<div class="warp clearfix">
				<div class="toper_l">
					<div class="twel fl">
                    	<div class="tinfo fl">欢迎您，<a href="/DataShare/login.action?id=4">${sessionScope.userInfo.userName}!&nbsp;<img style="width:20px;width:20px;border-radius:50%;" src="${sessionScope.userInfo.imgPath}"/></a>&nbsp;&nbsp;<span id="time"></span></div>
                        <div class="ttb fl">
                        	<a href="javascript:;" class="nownews">
                                <i class="tico tico_news"></i><i class="tnews"></i>
                                <div class="tnews-list"><i></i><ul></ul></div> 
                            </a>
                            <a href="/DataShare/index.action" title="返回主页"><i class="tico tico_home"></i></a>
                        </div>
                    </div>
                    <div class="tfun fr"> 
                    	<a href="javascript:;" onClick="logout()" title="退出系统"><i class="tico tico_layout"></i>退出</a>
                    </div>   
                    <script>
							$.ajaxSetup({ cache: false });//全局禁用缓存
						$(document).ready(function(e) {
							getNewsNum(); 
							getNewsList();
	

							/*控制iframe高度 */
							var browserVersion = window.navigator.userAgent.toUpperCase();
							var isOpera = browserVersion.indexOf("OPERA") > -1;
							var isFireFox = browserVersion.indexOf("FIREFOX") > -1;
							var isChrome = browserVersion.indexOf("CHROME") > -1;
							var isSafari = browserVersion.indexOf("SAFARI") > -1;
							var isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
							var isIE9More = (! -[1, ] == false);
							function reinitIframe(iframeId, minHeight) {
								try {
									var iframe = document.getElementById(iframeId);
									var bHeight = 0;
									if (isChrome == false && isSafari == false)
										bHeight = iframe.contentWindow.document.body.scrollHeight;

									var dHeight = 0;
									if (isFireFox == true)
										dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
									else if (isIE == false && isOpera == false)
										dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
									else if (isIE == true && isIE9More) {//ie9+
										var heightDeviation = bHeight - eval("window.IE9MoreRealHeight" + iframeId);
										if (heightDeviation == 0) {
											bHeight += 3;
										} else if (heightDeviation != 3) {
											eval("window.IE9MoreRealHeight" + iframeId + "=" + bHeight);
											bHeight += 3;
										}
									}
									else//ie[6-8]、OPERA
										bHeight += 3;
									dHeight += 20;
									var height = Math.max(bHeight, dHeight);
									if( height > 1800 ){
										height = 1800;
										return false;
									}
									if (height < minHeight) height = minHeight;
									iframe.style.height = height + "px";
								} catch (ex) { }
							}
							function startInit(iframeId, minHeight) {
								eval("window.IE9MoreRealHeight" + iframeId + "=0");
								window.setInterval(function () {
									reinitIframe(iframeId, minHeight);
								}, 100);
							}
							//startInit('iframe', 300); // 通过传iframe的id 和最小高度来控制iframe
                        }); 
						/*$("body").click(function(){
							$(".tnews-list").hide();
						})*/
                    	function getNewsNum(){
							var url="/DataShare/note/queryCount_NoteInfo.action";
							$.ajax({
								url:url,
								type: "get",
								data: "json",
							}).done(function(data) {
								if(data.date[0].code == 0){
									$(".nownews .tnews").hide();
									$(".tnews-list").hide();

								}else{
									$(".nownews .tnews").show().text(data.date[0].code);
									
								}


								/*$(".nownews .tnews").text(data.date[0].code);*/
								
							})	
							setTimeout('getNewsNum()',100000);
						}
						function getNewsList(){
							var url="/DataShare/note/getNoteInfoList.action";
							$.ajax({
								url:url,
								type: "get",
								data: "json",
							}).done(function(data) {
								var str=""; 
								$.each(data,function(k,v){
									str=str+"<li id='"+v.id+"'>"+v.content+"</li>";//+v.createStrTime.substr(0,10)+"："
								})
								$(".tnews-list ul").html(str);
							})		
						}
                    </script>
				</div>
			</div>
		</div>
		<!--  ==========  -->
		<!--  ==顶部==  -->
		<!--  ==========  -->
		<div class="header">
			<div class="warp">
				<div class="logo">
					<table>
						<tr>
							<td><a href="#"><img style="width:45px;height:45px;" src="/DataShare/img/plogo.png"/></a></td>
							<td><a href="#"><i>&nbsp;&nbsp;</i></a></td>
							<td><a href="#"><i style="font-weight:900;font-faimly:Times, TimesNR, 'New Century Schoolbook', Georgia, 'New York', serif;font-size: 18px;">GNSS数据资源共享与信息发布平台</i></a></td>
						</tr>
					</table>
					
				</div>
				<div class="nav">
					<ul id="nav-ul">
						<li><a href="/DataShare/login.action?id=2">数据目录</a></li>
						<li><a href="/DataShare/login.action?id=3" class="sjcx">数据申请</a></li>
						
						<c:if test="${sessionScope.userType != '1'}"><li><a href="/DataShare/login.action?id=4">个人中心</a></li></c:if>
						<c:if test="${sessionScope.userType == '1'}"><li><a href="/DataShare/login.action?id=4">用户中心</a></li></c:if>
						
						<c:if test="${sessionScope.userType == '1'}"><li class="active"><a href="/DataShare/login.action?id=5">数据统计</a></li></c:if>
						<c:if test="${sessionScope.userType == '1'}"><li><a href="/DataShare/login.action?id=6">综合管理</a></li></c:if>
					</ul>
				</div>
			</div>
		</div>
		<!--  ==========  -->
		<!--  ==页面主体==  -->
		<!--  ==========  -->
		<div class="main">
			<div class="main-wap">
				<!--  ==巨幕==  -->
				<div class="column">
					<img src="/DataShare/img/img-01.png"/>
				</div>
				<!--  ==功能区==  -->
				<div class="shop">
					<!--  ==左侧栏目==  -->
					<div class="shop-left">
						<!--  ==陆态数据==  -->
                       
                        <ul class="shop-left-ul sidebar">
							<li  class="add"><a href="javascript:;">陆态30秒日常分析</a></li>
							<ul class="subnav analysis0" style="display: block;" id="wei">
								
								<li><a href="javascript:;" rel="/DataShare/analysis/s30sZoneToJsp.action?applyTitle=文件下载地区统计">文件下载地区统计</a></li>
								<li><a href="javascript:;" rel="/DataShare/analysis/s30sDepToJsp.action?applyTitle=文件下载部委统计">文件下载部委统计</a></li>
								
								<li><a href="javascript:;" rel="/DataShare/analysis/s30sFileMoreDayToJsp.action?applyTitle=文件下载地图展示">文件下载地图展示</a></li>
								<li><a href="javascript:;" rel="/DataShare/analysis/s30sSiteMoreTimeToJsp.action?applyTitle=台站文件下载地图展示">台站文件下载地图展示</a></li>
								
								<li><a href="javascript:;" rel="/DataShare/analysis/s30sFileMoreMonthToJsp.action?applyTitle=文件下载月度统计">文件下载月度统计</a></li>
								<li><a href="javascript:;" rel="/DataShare/analysis/s30sFileMoreYearToJsp.action?applyTitle=文件下载年度统计">文件下载年度统计</a></li>
								

								<!--<li><a href="javascript:;" rel="/DataShare/analysisUser/userApplyToJsp.action?applyTitle=申请信息统计&fileType=1&siteType=1">申请信息统计</a></li>
								
								--><!--<li><a href="javascript:;" rel="/DataShare/analysis/s30SDownHistoryListToJsp.action?applyTitle=综合统计">综合统计</a></li>
								--><!--<li><a href="javascript:;" rel="/DataShare/errorBuildIng.action?applyTitle=用户文件下载地图展示">用户文件下载地图展示</a></li>-->
							</ul>
							<li class="add3"><a href="javascript:;">陆态流动观测数据</a></li>
							<ul class="subnav analysis2">
								<li><a href="javascript:;" rel="/DataShare/analysis/flowZoneToJsp.action?applyTitle=文件下载地区统计">文件下载地区统计</a></li>
								<li><a href="javascript:;" rel="/DataShare/analysis/flowDepToJsp.action?applyTitle=文件下载部委统计">文件下载部委统计</a></li>
								
								<li><a href="javascript:;" rel="/DataShare/analysis/flowFileMoreYearToJsp.action?applyTitle=文件下载年度统计">文件下载年度统计</a></li>

								<!--<li><a href="javascript:;" rel="/DataShare/analysis/flowDownHistoryListToJsp.action?applyTitle=综合统计">综合统计</a></li>
							--></ul>
							<li class="add4 new-add4-3"><a href="javascript:;">其他项目共享数据</a></li>
							<ul class="subnav analysis3">
								
								<li><a href="javascript:;" rel="/DataShare/analysis/shareZoneToJsp.action?applyTitle=文件下载地区统计">文件下载地区统计</a></li>
								<li><a href="javascript:;" rel="/DataShare/analysis/shareDepToJsp.action?applyTitle=文件下载部委统计">文件下载部委统计</a></li>
								
								<li><a href="javascript:;" rel="/DataShare/analysis/shareFileMoreDayToJsp.action?applyTitle=文件下载地图展示">文件下载地图展示</a></li>
								<li><a href="javascript:;" rel="/DataShare/analysis/shareSiteMoreTimeToJsp.action?applyTitle=台站文件下载地图展示">台站文件下载地图展示</a></li>
								
								<li><a href="javascript:;" rel="/DataShare/analysis/shareFileMoreMonthToJsp.action?applyTitle=文件下载月度统计">文件下载月度统计</a></li>
								<li><a href="javascript:;" rel="/DataShare/analysis/shareFileMoreYearToJsp.action?applyTitle=文件下载年度统计">文件下载年度统计</a></li>
								
								<!--<li><a href="javascript:;" rel="/DataShare/errorBuildIng.action?applyTitle=未处理申请">用户文件下载地图展示</a></li>-->
								<!--<li><a href="javascript:;" rel="/DataShare/analysis/shareDownHistoryListToJsp.action?applyTitle=综合统计">综合统计</a></li>-->
						   </ul>
						   <li class="add2"><a href="javascript:;">陆态地震应急数据</a></li>
						   <ul class="subnav analysis1">
								<!--<li><a href="javascript:;" rel="/DataShare/errorBuildIng.action?applyTitle=事件分析">事件分析</a></li>-->
								<li><a href="javascript:;" rel="/DataShare/analysis/earthQuake/downTableToJsp.action?applyTitle=文件下载表格展示">文件下载表格展示</a></li>
								<li><a href="javascript:;" rel="/DataShare/earthQuake/eventApplyMapToJsp.action?applyTitle=文件下载地图展示">文件下载地图展示</a></li>
								<!--<li><a href="javascript:;" rel="/DataShare/earthQuake/analyTableToJsp.action?applyTitle=综合统计">综合统计</a></li>-->
								<!--<li><a href="javascript:;" rel="/DataShare/analysis/earthQuake/downHistoryListToJsp.action?applyTitle=综合统计">综合统计</a></li>-->
								<!--<li><a href="javascript:;" rel="/DataShare/showContingencyToJsp.action?applyTitle=未处理申请">应急数据查看</a></li> -->
						   </ul>
						   <!--<li class="add5"><a href="javascript:;">站点信息及大事记</a></li>
							    <ul class="subnav analysis4">
							    	<li><a href="javascript:;" rel="/DataShare/errorBuildIng.action?applyTitle=未处理申请">综合统计</a></li>
									<li><a href="javascript:;" rel="/DataShare/errorBuildIng.action?applyTitle=未处理申请">站点信息及大事记列表</a></li>
									<li><a href="javascript:;" rel="/DataShare/errorBuildIng.action?applyTitle=未处理申请">站点信息及大事记查看</a></li>
							   </ul>
						--></ul>
						
						
					
					</div>
					<!--  ==右侧功能区==  -->
					<div class="shop-right">
						  <iframe src="" id="iframe" frameborder="0" scrolling="no" height="auto" marginheight="0" marginwidth="0" onload="setFrameHeight('iframe')"></iframe>
					</div>
                    <div class="pbtit" style="display:none;"></div>
					<input type="hidden" id="purl" value="" onClick="setcookie(this);">
					<script>
						showtime();
						var url="/DataShare/analysis/s30sFileMoreDayToJsp.action?applyTitle=文件下载地图展示";
						$("#iframe").attr("src",url);
						var w;
						$(window).resize(function() {
						  w=document.documentElement.clientWidth;
						  if(w>=1200){
								$('.pbtit').css("left",(w-1200)/2+240);
							}
							else{
								$('.pbtit').css("left","240px");	
							}
						});
						$('#iframe').load(function(){
							var h=$('#iframe').contents().find("#iframeheight").height();
							$('#iframe').css("height",h);
							w=document.documentElement.clientWidth;
							
							$('.pbtit').text($('#iframe').contents().find(".ptit").text());
							if(w>=1200){
								$('.pbtit').css("left",(w-1200)/2+240);
							}
							else{
								$('.pbtit').css("left","240px");	
							}
						})
						$("#wei li a").click(function(){
							$("#iframe").attr("src",$(this).attr("rel"));
							seturl($(this).attr("rel"));
						})
						$(".sjcx").click(function(){
							seturl("apply/applyPage1.jsp");
							window.location.href="30S.jsp";
						})
						function setcookie(obj){
							seturl($(obj).val());
						}
						$(document).ready(function(){
							$('.sidebar').eq(0).show();	
							$(document).scroll(function(){
								var top = $(document).scrollTop();
								if(top >330){
									$(".shop-left").addClass("sfixed");
									$(".pbtit").show();
								}else{
									$(".shop-left").removeClass("sfixed");
									$(".pbtit").hide();
								}
							});
						})
					</script>
				</div>
			</div>
		</div>
		<!--  ==========  -->
		<!--  ==顶部==  -->
		<%@ include file="../foot.jsp" %>
		
		<script src="/DataShare/js/js.js" type="text/javascript" charset="utf-8"></script>
		<script src="/DataShare/js/leftLogojs.js" type="text/javascript" charset="utf-8"></script>
		
		<div class="zbg" style="display:none;"></div>
        <div class="myload" style="display:none;">数据加载中,请稍后...<br/><img src="/DataShare/img/pcgzs/load.gif"></div>
    	<div class="mytips" style="display:none;">加入成功</div>
	</body>
</html>

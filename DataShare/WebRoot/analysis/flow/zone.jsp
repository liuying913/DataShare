<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	
	<script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/analysis/personOption.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/analysis/s30S/zone.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
  <input type="hidden" id="siteType" name="siteType" value="${siteType}"/>
  <input type="hidden" id="fileType" name="fileType" value="${fileType}"/>
  <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
  <div id="iframeheight">
        <div class="pbox tspbox">
        <div class="ptit">${applyTitle}</div>
        <div class="base-info">
		<div class="shop-right">		
		<div class="shop-warp02" style="display: block;">
			<div class="warp02-top">
				<form action="" method="post" id="warp02-top-form">
					<div class="shengfen shengfen03" style="padding: 0 105px!important;">
						<div class="span pp">
							<table>
								<tr>
									<td><span style="width:70px;">开始时间</span></td>
									<td>&nbsp;&nbsp;</td>
									<td><input type="text" name="kaishi" class="kaishi" style="width:140px;height:20px;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><span style="width:70px;">结束时间</span></td>
									<td>&nbsp;&nbsp;</td>
									<td><input type="text" name="jieshu" class="jieshu" style="width:140px;height:20px;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/></td>
									
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><span style="width:70px;">人员</span></td>
									<td>&nbsp;&nbsp;</td>
									<td><select id="userId" class="userId" name="userId" style="width:140px; margin-top: 0px;" ></select></td>
								</tr>
							</table>
						</div>
					</div>
					<br/>
					<div class="warp-button">
						<button type="button" value="30S" class="shengfen-button"><img src="/DataShare/img/s1.png"/>查询</button>
						<button type="button" class="button"><img src="/DataShare/img/s2.png"/>导出</button>
					</div>
				</form>

			</div>
			<style type="text/css"> 
			<!-- 
			#exhibitor114{ margin:0 auto;width:auto;height:100%} 
			#exhibitorleft{ float:left; width:100%; height:400px;} 
			#exhibitorright{ float:left; width:100%;height:400px;} 		
			--> 
			</style>
			<div id="exhibitor114" style="float:inherit;height:800px;"> 
				<div id="exhibitorleft"></div>
				<div id="exhibitorright"></div>
			</div>
			<div id="tbtable" style="display:name;float:inherit;"></div>
		</div>		
		</div>
        </div>
        </div>
    </div>
	 <script>		$.ajaxSetup({ cache: false });//全局禁用缓存</script>
</body>
</html>

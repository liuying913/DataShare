<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
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
	<script src="/DataShare/js/analysis/earthQuake/downMap.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/analysis/personOption.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
  <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
  <input type="hidden" id="siteType" name="siteType" value="${siteType}"/>
  <input type="hidden" id="fileType" name="fileType" value="${fileType}"/>
  

  <div id="iframeheight">
        <div class="pbox tspbox">
        <div class="ptit">地震应急数据地图展示</div>
        <br/>
        <div class="base-info">
		<div class="shop-right">
		
		<div class="shop-warp02" style="display: block;">
			<div class="warp02-top">
				<form action="" method="post" id="warp02-top-form">
					<div class="shengfen shengfen03">
						<div class="span pp">
							<table>
								<tr>
									<td><span style="width:80px;height:24px;line-height: 20px;padding:0px 5px; margin-top: 0px;font-size:14px;margin-left:0px;">年</span></td>

									<td><select name="ddl_year" id="ddl_year" style="width:145px;height:20px;line-height: 20px;padding:0px 5px; margin-top: 0px;font-size:14px;margin-left:5px;"></select></td>

									
									<td><span style="width:80px;height:24px;line-height: 20px;padding:0px 5px; margin-top: 0px;font-size:14px;margin-left:65px;">事件</span></td>

									<td><select id="eventId" class="eventId" name="eventId" style="width:145px;height:20px;line-height: 20px;padding:0px 5px; margin-top: 0px;font-size:14px;margin-left:5px;"></select></td>
									
									<td><span style="width:80px;height:24px;line-height: 20px;padding:0px 5px; margin-top: 0px;font-size:14px;margin-left:65px;">人员</span></td>

									<td><select id="userId" class="userId" name="userId" style="width:145px;height:20px;line-height: 20px;padding:0px 5px; margin-top: 0px;font-size:14px;margin-left:5px;"></select></td>
								</tr>
							</table>
						</div>					
					</div>
					
					<div class="warp-button">
						<button type="button" value="30S" class="shengfen-button"><img src="/DataShare/img/s1.png"/>查询</button>
						<!--<button type="button" class="button"><img src="/DataShare/img/s2.png"/>导出</button>-->
					</div>
				</form>

			</div>
			<!--  ==表格==  -->
			<div id="main" style="width:960px;height:500px;display:none;"></div>
		</div>
		</div>
        </div>
        </div>
    </div>
	 <script>		$.ajaxSetup({ cache: false });//全局禁用缓存</script>
</body>
</html>

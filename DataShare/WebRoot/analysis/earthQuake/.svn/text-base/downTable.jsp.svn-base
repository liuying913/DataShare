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
	
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/analysis/earthQuake/downTable.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/analysis/personOption.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
  <input type="hidden" id="earthQuakeId" name="earthQuakeId" value="${earthQuakeId}"/>
  <div id="iframeheight">
        <div class="pbox tspbox">
        	<div class="ptit">应急事件列表</div>
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
						<button type="button" value="30S" class="shengfen-button">查询</button>
						<button type="button" class="button">导出</button>
					</div>
				</form>
			</div>
			<div style="display: none;" id="tipoi">下载数量：<i class="hs"></i>0<i class="bs"></i>1-2<i class="ls"></i>3-4<i class="rs"></i>>=5</div>
			<!--  ==表格==  -->
			<div class="warp02-bottom" style="border-top:none;margin-top:-55px;"></div>
		</div>
        
		</div>
        <style>
        	.table01 th, .table01 td{padding:8px 0;}
			.hy1{margin-top:20px;}
			.warp02-bottom{border-top:none;padding-bottom:20px;}
			.warp02-bottom h3{display:block;padding:20px 0 10px 0;}
			.table01 td.yellow{ background-color:#ffff99;}
			.table01 td.green{ background-color:#6ad76a;}
			.table01 td.red{ background-color:#e53341;}
        </style>
        </div>
        </div>
    </div>
	 <script>		$.ajaxSetup({ cache: false });//全局禁用缓存</script>
</body>
</html>

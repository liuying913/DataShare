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
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	
	<script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/analysis/s30S/dep.js" type="text/javascript" charset="utf-8"></script>
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
					<div class="shengfen shengfen03">
						<div class="span pp"><span>开始时间</span></div>
						<input type="text" class="kaishi" style="width:140px;height:20px;line-height: 20px;" value="2016-01-01" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
						<div class="span pp"><span>结束时间</span></div>
						<input type="text" class="jieshu" style="width:140px;height:20px;line-height: 20px;" value="2016-12-31" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
						
						<div class="span pp"><span>用户列表</span></div>
						<select name="userId" id="userId">
                        	<option>选择用户</option>
							<option value="2013">2013</option>
							<option value="2014">2014</option>
							<option value="2015">2015</option>
							<option value="2016" selected="selected" >2016</option>
						</select>
					</div>
					<br/>
					<button type="button" value="30S" class="shengfen-button">查询</button>
				</form>
			</div>
			<style type="text/css"> 
			<!-- 
			#exhibitor114{ margin:0 auto;width:auto;height:100%} 
			#exhibitorleft{ float:left; width:50%; height:400px; } 
			#exhibitorright{ float:left; width:50%;height:400px; } 					
			--> 
			</style>
			<div id="exhibitor114" style="float:inherit;    height: 400px;"> 
				<div  id="exhibitorleft"></div>
				<div  id="exhibitorright"></div>
			</div> 
			<style type="text/css">
				.tftable
				{
					font-size: 12px;
					color: #333333;
					width: 92%;
					border-width: 0px;
					border-color: #306bb5;
					border-collapse: collapse;
				}
				.tftable th
				{
					font-size: 12px;
					background-color: #306bb5;
					border-width: 0px;
					padding: 8px;
					border-style: solid;
					border-color: #306bb5;
					text-align: left;
					color: #fff;
				}
				.tftable tr
				{
					background-color: #ffffff;
				}
				.tftable tr:nth-child(2n){ background: #F1F1F1;}
				.tftable td
				{
					font-size: 12px;
					border-width: 0px;
					padding: 8px;
					border-style: solid;
					border-color: #306bb5;
				}
				.tftable tr:hover {background-color:#DAEAFF;}
			</style>
			<div id="tbtable" style="display:name;float:inherit;"></div>
		</div>		
		</div>
        </div>
        </div>
    </div>
	 <script>		$.ajaxSetup({ cache: false });//全局禁用缓存</script>
</body>
</html>

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
	<script src="/DataShare/js/analysis/s30S/fileMoreTime.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/common/frame.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/analysis/personOption.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
  <input type="hidden" id="siteType" name="siteType" value="${siteType}"/>
  <input type="hidden" id="fileType" name="fileType" value="${fileType}"/>
  <div id="iframeheight">
        <div class="pbox tspbox">
        <div class="ptit">${applyTitle}</div>
        <div class="base-info">
		<div class="shop-right">
		
		<div class="shop-warp02" style="display: block;">
			<div class="warp02-top">
				<form action="" method="post" id="warp02-top-form">
					<div class="shengfen shengfen03">
						<div class="span pp">
							<table>
								<tr>
									<td><span style="width:70px;">开始时间</span></td>
									<td>&nbsp;&nbsp;</td>
									<td><input type="text" name="startTime" class="startTime" style="width:140px;height:20px;line-height: 20px;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/></td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><span style="width:70px;">结束时间</span></td>
									<td>&nbsp;&nbsp;</td>
									<td><input type="text" name="endTime" class="endTime"     style="width:140px;height:20px;line-height: 20px;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/></td>
									
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><span style="width:70px;">人员</span></td>
									<td>&nbsp;&nbsp;</td>
									<td><select id="userId" class="userId" name="userId" style="width:140px; margin-top: 0px;" ></select></td>
								</tr>
							</table>
						</div>
					</div>
					<div class="shengfen shengfen01">
						<div class="span yy"><span>地区</span></div>
                        <div class="shengfen-right da_inner">
                            <div class="input-warp input-warp01 clearfix"></div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					<div class="shengfen shengfen02" >
						<div class="span"><span>台站</span></div>
                        <div class="shengfen-right da_inner">
                            <div class="input-warp input-warp02 clearfix"></div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan2" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan2" value="" /><em>反选</em></label>
							</div>
                         </div>
					</div>
					<br/>
					<div class="warp-button">
						<button type="button" value="30S" class="shengfen-button"><img src="/DataShare/img/s1.png"/>查询</button>
						<button type="button" class="button"><img src="/DataShare/img/s2.png"/>导出</button>
					</div>
				</form>
			</div>
			<!--  ==表格==  -->
			<div id="main" style="width:960px;height:500px;display:none;"></div>
		</div>
		</div>
        </div>
        	<style>
        	.table01 th, .table01 td{padding:8px 0;}
        </style>
        </div>
    </div>
	 <script>		$.ajaxSetup({ cache: false });//全局禁用缓存</script>
</body>
</html>

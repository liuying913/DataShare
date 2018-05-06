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
	
	<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/showData/earthQuakeShow.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
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
					<div class="shengfen shengfen02"> 
						<div class="span pp"><span>部委</span></div>
						<div class="input-warp input-warp00"></div>	
					</div>
					<div class="shengfen shengfen01">
						<div class="span  yy"><span>地区</span></div>
                        <div class="shengfen-right">
                            <div class="input-warp input-warp01 clearfix"></div>
                            <div class="anniu">
                                 <label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					<div class="shengfen shengfen02" >
						<div class="span"><span>台站</span></div>
                         <div class="shengfen-right">
                            <div class="input-warp input-warp02 clearfix">
                            </div>
                            <div class="anniu">
                                <label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan2" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan2" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					<div class="shengfen shengfen02"> 
						<div class="span pp"><span>类型</span></div>
						<div class="input-warp input-warp03">
							<div class="input-warp-contnor check_style check_y1"><input type="checkbox" name="hzType" value="1"><label>1HZ</label></div>
							<div class="input-warp-contnor check_style check_y1"><input type="checkbox" name="hzType" value="50"><label>50HZ</label></div>
						</div>	
					</div>
					<div class="warp-button">
						<button type="button" value="30S" class="shengfen-button">查询</button>
						<button type="button" class="button" id="earthQuakeSupplyData">启动整理</button>
					</div>
				</form>
			</div>
			<div style="display: none;" id="tipoi">图例：<i class="hs"></i>缺失<i class="bs"></i>空缺<i class="ls"></i>完整<i class="rs"></i>缺失</div>
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
    <script>		
    $.ajaxSetup({ cache: false });//全局禁用缓存
    var earthQuakeId = $("#earthQuakeId").val();
    </script>
</body>
</html>

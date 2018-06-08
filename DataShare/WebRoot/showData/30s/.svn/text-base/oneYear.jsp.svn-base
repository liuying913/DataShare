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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/showData/s30sMonth.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
  <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
  <input type="hidden" id="siteType" name="siteType" value="${siteType}"/>
  <input type="hidden" id="fileType" name="fileType" value="${fileType}"/>

  <div id="iframeheight">
        <div class="pbox tspbox">
        <div class="ptit">单站年度数据目录生成</div>
        <div class="base-info">
		<div class="shop-right">
		
		<div class="shop-warp02" style="display: block;">
			<div class="warp02-top">
				<form action="" method="post" id="warp02-top-form">
					<div class="shengfen shengfen03">
						<div class="span pp" ><span>日期</span></div>
						<div class="da_inner">
							<select name="ddl_year" id="ddl_year"></select>
						</div>
					</div>
					<div class="shengfen shengfen02">
						<div class="span pp"><span>部委</span></div>
						<div class="input-warp input-warp00 da_inner"></div>	
					</div>
					<div class="shengfen shengfen01">
						<div class="span yy"><span>地区</span></div>
                        <div class="shengfen-right da_inner">
                            <div class="input-warp input-warp01 clearfix">
                            </div>
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
                        </div>
					</div>
					</br>
					<div class="warp-button">
						<button type="button" value="30S" class="shengfen-button"><img src="/DataShare/img/s1.png"/>查询</button>
						<c:if test="${sessionScope.userType == '1'}"><button type="button" class="button"><img src="/DataShare/img/s2.png"/>导出</button></c:if>
					</div>
				</form>
			</div>
			<div style="display: none;" id="tipoi">图例：<i class="hs"></i>空缺<i class="bs"></i>完整<i class="rs"></i>缺失</div>
			<!--  ==表格==  -->
			<div class="warp02-bottom newdate" style="margin-bottom:10px;">
				<div class="warp02-bottom-left">
					<ul class="ul-img">
					</ul>
				</div>
				<div class="warp02-bottom-right fl">
					<table width="100%" border="1" class="table">
					  <tr class="tr-01">
					    <th scope="col"><i></i>年</th>
					    <th scope="col"><i></i>年积日</th>
					    <th scope="col"><i></i>站点名称</th>
					    <th scope="col"><i></i>文件名称</th>
					    <th scope="col"><i></i>文件大小</th>
					    <th scope="col"><i></i>地区</th>
					    <th scope="col"><i></i>是否完整</th>
					  </tr>
					</table>
				</div>
			</div>
            
		</div>
		</div>
        </div>
            <script>		$.ajaxSetup({ cache: false });//全局禁用缓存
				var applyId = $("#applyId").val();
				//下一步
            	$(".btn1").click(function(){
            		window.location.href="/DataShare/applyPage3ToJsp.action?applyId="+applyId;
            	})
				//上一步
            	$(".btn2").click(function(){
            		window.location.href="applyPage1.jsp?back=1";	
            	})
            	
            </script>
        </div>
    </div>
</body>
</html>

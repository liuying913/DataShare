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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	
	<script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/showData/dataShow.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
  <input type="hidden" id="siteType" name="siteType" value="${siteType}"/>
  <input type="hidden" id="fileType" name="fileType" value="${fileType}"/>
  <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
  <div id="iframeheight">
        <div class="pbox tspbox">
        <div class="ptit">数据文件属性</div>
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
									<td>  <input type="text" name="ddl_year" class="kaishi" style="width:140px;height:22px;line-height: 22px;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/> 
									</td>
									<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
									<td><span style="width:70px;">结束时间</span></td>
									<td>&nbsp;&nbsp;</td>
									<td>
										<input type="text" name="ddl_year" class="jieshu" style="width:140px;height:22px;line-height: 22px;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/> 
									</td>
								</tr>
							</table>
						</div>
					</div>
					<div class="shengfen shengfen02">
						<div class="span pp"><span>部委</span></div>
						<div class="input-warp input-warp00 da_inner"></div>	
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
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan2 quan" value=""/><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan2" value="" /><em>反选</em></label>
							</div>
                         </div>
					</div>
					<br>
					<div class="warp-button">
						<button type="button" value="30S" class="shengfen-button"><img src="/DataShare/img/s1.png"/>查询</button>
						<c:if test="${sessionScope.userType == '1'}"><button type="button" class="button" id="exportDataExcel"><img src="/DataShare/img/s2.png"/>导出</button></c:if>
					</div>
				</form>
			</div>
			<!--  ==表格==  -->
			<div class="warp02-bottom tswarp01" style="width:100%;min-height:500px;">
            	<div class="warp02-box" style="width: 99.2%;">
                    <div class="warp02-bottom-left" style="width:9.6%">
                        <ul class="ul-img">
                        </ul>
                    </div>
                    <div class="warp02-bottom-right fl" style="width:90%">
                        <table border="1" class="table"  width="100%">
	                        <tr class="tr-01">
	                            <th scope="col" style="white-space: nowrap;"><i></i>台站编号</th>
	                            <th scope="col" style="white-space: nowrap;"><i></i>台站名称</th>
	                            <th scope="col" style="white-space: nowrap;"><i></i>文件名称</th>
	                            <th scope="col" style="white-space: nowrap;"><i></i>文件大小(KB)</th>
	                            <th scope="col" style="white-space: nowrap;"><i></i>历元数</th>
	                            <th scope="col" style="white-space: nowrap;"><i></i>MP1</th>
	                            <th scope="col" style="white-space: nowrap;"><i></i>MP2</th>
	                            <th scope="col" style="white-space: nowrap;"><i></i>是否完整</th>
	                        </tr>
                        </table>
                    </div>
                    <div class="pclear"></div>
                </div>
                <div class="blank20"></div>
			</div>
		</div>
		</div>
	
       </div>
            <script>		
                $.ajaxSetup({ cache: false });//全局禁用缓存
				var applyId = $("#applyId").val();
				window.onload = function() {
					$('.quan').parent().addClass('on')
					$('.quan').parent().removeClass('on')
				}
            </script>
        </div>
    </div>
</body>
</html>

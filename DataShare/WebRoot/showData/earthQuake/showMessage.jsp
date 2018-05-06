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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
</head>
<body>
  <input type="hidden" id="earthQuakeId" name="earthQuakeId" value="${earthQuakeId}"/>
  <div id="iframeheight">
  	    <div class="pbox tspbox" style="padding-bottom:0px;">
           <div class="ptit">陆态地震应急事件信息</div>
  	    </div>
  		<div class="pbox">
            <div class="base-info baseInfo">
                <div class="base-info-input">
                    <ul class="clearfix">
                        <li>
                        	<span>名称</span>
                            <label><input id="name" name="name" type="text" value="" class="input01 require" err="名称"></label>
                        </li>
                        <li>
                        	<span>发生时间</span>
                            <label><strong><input type="text" name="strTime" class="input01 require" value=""></strong></label>
                        </li>
                        
                        <li>
                        	<span>纬度</span>
                            <label><input name="siteLat" type="text" value="" class="input01 require" err="纬度"></label>
                        </li>
						<li>
                        	<span>经度</span>
                            <label><input name="siteLon" type="text" value="" class="input01 require" err="经度"></label>
                        </li>
                        
                        <li>
                        	<span>震级</span>
                            <label><input name="grade" type="text" value="" class="input01 require" err="震级"></label>
                        </li>
						
						<li>
                        	<span>震源深度</span>
                            <label><input name="height" type="text" value="" class="input01 require" err="震源深度"></label>
                        </li>
						<li>
                        	<span>地点</span>
                            <label><input name="address" type="text" value="" class="input01 require" err="地点"></label>
                        </li>
                        
                        <li>
                        	<span>描述</span>
                            <label><input name="remark" type="text" value="" class="input01 require" err="描述"></label>
                        </li>
                    </ul>
                </div>
            </div>
            <div>
              <button type="button" id="back" class="shengfen-button" style="margin:25px auto 0;float:none;"><!--<img src="/DataShare/img/s2.png"/>-->返回</button>
            </div>
        </div>
        </div>
        
    <script>		$.ajaxSetup({ cache: false });//全局禁用缓存
		var earthQuakeId = $("#earthQuakeId").val();
    	$(document).ready(function(){
    		var url=window.location.href;
			$(".base-info").addClass("base-info-back");
			$(".input01").attr("disabled","disabled");
			ajaxRequest("/DataShare/showData/earthQuakeMessage.action?earthQuakeId="+earthQuakeId,"get",function(res){
				$(".input01").eq(0).val(res[0].name);
				$(".input01").eq(1).val(res[0].strTime);
				$(".input01").eq(2).val(res[0].siteLat);
				$(".input01").eq(3).val(res[0].siteLon);
				$(".input01").eq(4).val(res[0].grade);
				$(".input01").eq(5).val(res[0].height);
				$(".input01").eq(6).val(res[0].address);
				$(".input01").eq(7).val(res[0].remark);
			})
    	})
    	
    	//返回
		$("#back").click(function(){
			//$('#purl', parent.document).val("/DataShare/showData/contingencyEventListToJsp.action");
			//$('#purl', parent.document).trigger("click");
			//window.location.href="/DataShare/showData/contingencyEventListToJsp.action";	
			window.history.back(-1); 
		})
    </script>
</body>
</html>

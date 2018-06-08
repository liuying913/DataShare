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
  <input type="hidden" id="siteNumber" name="siteNumber" value="${siteNumber}"/>
    <div id="iframeheight">
         <div class="pbox tspbox" style="padding-bottom:0px;">
           <div class="ptit">共享站信息查看</div>
  	    </div>
  		<div class="pbox">
            <div class="base-info baseInfo">
                <div class="base-info-input">
                    <ul  class="clearfix">
                    	<li>
                        	<span>台站编号</span>
                            <label><input name="userName" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>台站名称</span>
                            <label><input name="userCName" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>纬度</span>
                            <label><input name="userGender" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>经度</span>
                            <label><input name="userUnit" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>所属地区</span>
                            <label><input name="userPhone" type="text" value="" class="input01 require" ></label>
                        </li>
						<li>
                        	<span>所属部委</span>
                            <label><input name="userEmail" type="text" value="" class="input01 require" ></label>
                        </li>
						 <li>
                        	<span>排序</span>
                            <label><input name="regDateStr" type="text" value="" class="input01 require" err="请填写联系电话"></label>
                        </li>
						<li>
                        	<span>地址</span>
                            <label><input name="userTypeStr" type="text" value="" class="input01 require" err="请填写邮箱"></label>
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
		var siteNumber = $("#siteNumber").val();
    	$(document).ready(function(){
    		var url=window.location.href;
			$(".base-info").addClass("base-info-back");
			$(".input01").attr("disabled","disabled");
			ajaxRequest("/DataShare/getBaseSiteInfoById.action?siteNumber="+siteNumber,"get",function(res){
				$(".input01").eq(0).val(res[0].siteNumber);
				$(".input01").eq(1).val(res[0].siteName);
				$(".input01").eq(2).val(res[0].siteLat);
				$(".input01").eq(3).val(res[0].siteLng);
				$(".input01").eq(4).val(res[0].zoneName);
				$(".input01").eq(5).val(res[0].departmentName);
				$(".input01").eq(6).val(res[0].order);
				$(".input01").eq(7).val(res[0].address);
			})
			
			//返回
			$("#back").click(function(){
				window.location.href="/DataShare/baseSiteInfoListToJsp.action?applyTitle=共享站管理&siteType=3";	
			})
    	})
    	
    </script>
</body>
</html>

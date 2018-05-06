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
	<link rel="stylesheet" type="text/css" href="/DataShare/css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <style>
		.base-info-input ul li {
			width: 609px;
			text-align:center;
		}
		.warp-button{
			width: 160px;
			margin: 20px auto 0;
		    padding-left:0;
		}
		.tspbox .tsbase-info1 .input01 {
			background: #fefefe;
			color: #999;
			font-size:14px;font-family:微软雅黑;
			border-left:0px;
			border-top:0px;
			border-right:0px;
			border-bottom:1px;
		}
		.tspbox .tsbase-info1 .base-info-input ul li {
			float: left;
			width: 400px;
		}
	</style>
</head>
<body>
  <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
  <input id="isManager" type="hidden" name="isManager" value="${isManager}"/>
  <div id="iframeheight" style=" margin-top: 0px;background:#f7f7f7;">
	  <div class="web-width" >
	     <div class="for-liucheng">
		      <div class="liulist for-cur"></div>
		      <div class="liulist"></div>
		      <div class="liulist"></div>
		      <div class="liulist"></div>
		      <div class="liutextbox">
		          <div class="liutext for-cur"><em>1</em><br /><strong>基本信息填写</strong></div>
		          <div class="liutext"><em>2</em><br /><strong>申请数据筛选</strong></div>
		          <div class="liutext"><em>3</em><br /><strong>审批表下载</strong></div>
		          <div class="liutext"><em>4</em><br /><strong>审核结果</strong></div>
		      </div>
	      </div>
      </div>
	  <div style="height:15px;margin: 0 auto;background:white;"></div>
  	  <div class="pbox tspbox">
            <div class="base-info tsbase-info1">
                <div class="base-info-input clearfix">
					<div class="base-info tsbase-info1"  style="padding:12px 0"><span>申请者信息</span></div>
                    <ul style="padding: 0 0 0 65px;">
                    	<li>
                        	<span>申请人:</span>
                            <label><input type="text" value="" class="input01 require"  err="请填写申请人"></label>
                        </li>
                    	<li>
                        	<span>申请单位:</span>
                            <label><input type="text" value="" class="input01 require"  err="请填写申请单位"></label>
                        </li>
                        <li>
                        	<span>联系电话:</span>
                            <label><input type="text" value="" class="input01 require"  err="请填写电话"></label>
                        </li>
                        <li>
                        	<span>邮箱:</span>
                            <label><input type="text" value="" class="input01 require"  err="请填写邮箱"></label>
                        </li>
                        <li>
                        	<span>身份证号码:</span>
                            <label><input type="text" value="" class="input01 require"  err="请填写身份证号码"></label>
                        </li>
                    </ul>
                </div>
				<div style="height:15px;margin: 0 auto;background:white;"></div>
				<div class="base-info tsbase-info1  toggleItem"  style="width: 209px;padding: 12px 0;cursor: pointer; color: #0d85ba;overflow: hidden;"><span>支持的研究课题项目介绍</span></div>
                <div class="base-info-input  clearfix" style="border-top:none">
                    <ul style="padding:0 0 0 65px;" id="programContent">
                    	<li>
                        	<span>项目名称:</span>
                            <label><input type="text" value="" class="input01" ></label>
                        </li>
                        <li>
                        	<span>项目编号:</span>
                            <label><input type="text" value="" class="input01"></label>
                        </li>
                        <li>
                        	<span>经费:</span>
                            <label><input type="text" value="" class="input01"></label>
                        </li>
                    </ul>
            </div>
        </div>
        </div>
  </div>
  <div class="warp-button">
  	  <!--<button type="button" id="back" class="shengfen-button"><img src="/DataShare/img/s1.png"/>上一步</button>-->
	  <button type="button" id="next" class="shengfen-button"><!--<img src="/DataShare/img/s2.png"/>-->下一步</button>
  </div>
    <script>
    	$("#programContent").slideToggle(1);
		$.ajaxSetup({ cache: false });//全局禁用缓存
    	$(document).ready(function(){
			var applyId = $("#applyId").val();
			var isManager = $("#isManager").val();
			$(".base-info").addClass("base-info-back");
			$(".input01").attr("disabled","disabled");
			ajaxRequest("/DataShare/queryApplyFile.action?applyId="+applyId,"get",function(res){
				$(".input01").eq(0).val(res[0].applyUser.apply_Person);//response_person
				$(".input01").eq(1).val(res[0].applyUser.apply_Unit);
				$(".input01").eq(2).val(res[0].applyUser.apply_Phone);
				$(".input01").eq(3).val(res[0].applyUser.appply_Email);
				$(".input01").eq(4).val(res[0].applyUser.apply_PersonId);
				$(".input01").eq(5).val(res[0].applyUser.result_Name);
				$(".input01").eq(6).val(res[0].applyUser.result_Person);
				$(".input01").eq(7).val(res[0].applyUser.result_Money);
			});
			
			//上一步
	    	$("#back").click(function(){
				var url="/DataShare/applyOrShowToJsp.action?thisNum=1&backOrDown=back&applyTitle=数据申请&isManager="+isManager+"&applyId="+applyId;
	    		window.location.href=url;	
	    	});
    	
			//下一步
			$("#next").click(function(){
				var url="/DataShare/applyOrShowToJsp.action?thisNum=1&backOrDown=down&applyTitle=数据申请&isManager="+isManager+"&applyId="+applyId;
				window.location.href=url;	
			});
			$(".toggleItem").click(function(){
				$(this).next(".base-info-input").find("ul").slideToggle();
				$(this).find("span").toggleClass( "openState");
				$('#iframe', parent.document).css("height",530);
			});
    	});
    	
    </script>
</body>
</html>

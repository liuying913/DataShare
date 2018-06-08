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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
</head>
<body>
     <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
     <div id="iframeheight" style=" margin-top: 0px;background:#f7f7f7;">
					   	  <div class="web-width" >
						     <div class="for-liucheng">
							      <div class="liulist for-cur"></div>
							      <div class="liulist for-cur"></div>
							      <div class="liulist for-cur"></div>
							      <div class="liulist for-cur" style="border-radius: 0 5px 5px 0;"></div>
							      <div class="liutextbox">
							          <div class="liutext for-cur"><em>1</em><br /><strong>基本信息填写</strong></div>
							          <div class="liutext for-cur"><em>2</em><br /><strong>申请数据筛选</strong></div>
							          <div class="liutext for-cur"><em>3</em><br /><strong>审批表上传</strong></div>
							          <div class="liutext for-cur"><em>4</em><br /><strong>审核结果</strong></div>
							      </div>
						      </div>
					      </div>
					      
		<div style="height:15px;margin: 0 auto;background:white;"></div>
		
  		<div class="pbox tspbox" style=" margin-top: -15px;">
            <div class="base-info tsbase-info1">
			
				<div class="base-info-input">
                	<div class="pbox tspbox">
			            <div class="base-info tsbase-info1">
			                <div class="base-info-input" style="text-align: center;width: 100%;height: 50px;font-size: 14px;color: #316bb5; margin-top: 0px;line-height: 0px;font-weight: bold;">
			                	<fieldset>
						    	<legend style="display:none;">审核结果通知</legend>
			               	 	</fieldset>
			                </div>
			            </div>
			        </div>
					<div style="text-align:center; width:100%; height:50px;font-size:14px; color:#316bb5; margin-top:35px; line-height:0px; font-weight:bold;">
						<span class="successState1"></span>
						${message}
					</div>

	                <div class="base-info-input" style="display:none;">
	                	<h3>审核不通过，不通过原因：</h3>
	                    <div class="tips">您的项目不符合条件</div>
	                </div>
	                <br>
	                <div class="warp-button">
						 <button type="button" id="back" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->上一步</button>
					</div>
	                <div class="blank200"></div>
            </div>
        </div>
    </div>
    <script>
		$.ajaxSetup({ cache: false });//全局禁用缓存
		$("#back").click(function(){
			var applyId = $("#applyId").val();
			var url="/DataShare/applyOrShowToJsp.action?thisNum=4&backOrDown=back&isManager=false&applyId="+applyId;
			window.location.href=url;	
		})
    </script>
</body>
</html>

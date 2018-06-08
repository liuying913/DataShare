<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<style>
		.warp-button{
			width: 160px;
			margin: 20px auto 0;
			padding-left:0;
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
				  <div class="liulist for-cur"></div>
				  <div class="liulist for-cur"></div>
				  <div class="liulist for-cur"></div>
				  <div class="liutextbox">
					  <div class="liutext for-cur"><em>1</em><br /><strong>基本信息填写</strong></div>
					  <div class="liutext for-cur"><em>2</em><br /><strong>申请数据筛选</strong></div>
					  <div class="liutext for-cur"><em>3</em><br /><strong>审批表下载</strong></div>
					  <div class="liutext for-cur"><em>4</em><br /><strong>审核结果</strong></div>
				  </div>
			  </div>
		  </div>
	    <div style="height:15px;margin: 0 auto;background:white;"></div>
  		<div class="pbox tspbox" style=" margin-top: -15px;">
            <div class="base-info tsbase-info1">
                <fieldset>
					<legend style="display:none;">审核结果通知</legend>
	                <div class="base-info-input">
						<div class="titleBar">${applyCheck}</div>
						<div class="beizhu" id="dayLangth" style="color: #999;"></div>
						<div class="beizhu" id="dayLangth-1" style="color: #999;"></div>
						<textarea id="applyCheckText" disabled  class="textArea" name="applyCheckText">
						${applyCheckText}
						</textarea>
	                </div>
                </fieldset>
            </div>
        </div>
         <div class="warp-button">
			 <button type="button" id="back" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->上一步</button>
		 </div>
		 <br>
    </div>
    <script>		
    	$.ajaxSetup({ cache: false });//全局禁用缓存
		var applyId = $("#applyId").val();
		var isManager = $("#isManager").val();
		//上一步
		$(document).ready(function(){
			$("#back").click(function(){
				var url="/DataShare/applyOrShowToJsp.action?thisNum=4&backOrDown=back&isManager="+isManager+"&applyId="+applyId;
				window.location.href=url;	
			})
		});
		
		
		//如果申请通过的话   显示  申请有效期  申请起止时间 
		//如果申请不通过  显示原因  applyCheckText
		
		//申请有效期
  		ajaxRequest("/DataShare/earthQuakeConfigQuery.action?siteType=1","get",function(res){
			$("#dayLangth").html("下载有效时长"+res[0].applyperiod+"天");
  		});

  		
  		//申请起止时间
  		ajaxRequest("/DataShare/getApplyStartAndEnd.action?applyId="+applyId,"get",function(res){
  			//开始时间：sh_Start
  			//结束时间：sh_End
  			$("#dayLangth-1").html("下载有效期"+res[0].applyTime5+"~"+res[0].applyTime7);
  		});

  		/*申请通过*/
 
          if( $(".titleBar").text() == "审核通过"){
          	$("#applyCheckText").hide();

          }else{
          	$("#applyCheckText").show();
          	$("#dayLangth").hide();
          	$("#dayLangth-1").hide();

          }



    </script>
</body>
</html>

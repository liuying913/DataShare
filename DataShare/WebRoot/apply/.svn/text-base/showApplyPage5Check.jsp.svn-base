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
	<link rel="stylesheet" type="text/css" href="/DataShare/css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
	<style>
		.warp-button{
			width: 300px;
			margin: 20px auto 0;
			padding-left: 0;
		}
		.tspbox .tsbase-info1  textarea.input01{
			display:block;
			width:380px;
			height:80px;
			margin:0 auto;
			padding:10px;
			border:1px solid #ccc;
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
			          <div class="liutext for-cur"><em>3</em><br /><strong>审批表上传</strong></div>
			          <div class="liutext for-cur"><em>4</em><br /><strong>审核结果</strong></div>
			      </div>
		     </div>
        </div>
	    <div style="height:15px;margin: 0 auto;background:white;"></div>
  		<div class="pbox tspbox" style=" margin-top: -15px;">
             <div class="base-info tsbase-info1" style="height:220px">
                <fieldset>
					<legend style="display:none;">审核结果通知</legend>
	                <div class="base-info-input">
						<div style="text-align:center; width:100%; height:40px;font-size:14px; color:#316bb5; margin-top:40px; line-height:50px; font-weight:bold;">${applyCheck}
							<input checked  type="radio" id="applyCheck" name="applyCheck" class="pass" value="5" >审核通过</input>
							<input type="radio" id="applyCheck" class="out" name="applyCheck" value="6" >审核不通过</input>
							<!--<input type="textarea"  id="applyCheckText" name="applyCheckText"></text-->
						</div>
						<div id="iniRemark" class="iniRemark" style="text-align:center;margin-top: 25px;">
							<div class="beizhu" id="dayLangth" style="color: #999;"></div>
							<div class="beizhu" style="color: #999;">温馨提示：如果审核不通过，请填写原因!</div>
						</div>
						 <textarea id="applyCheckText" name="applyCheckText"  
						       onKeyDown="textdown(event)" onKeyUp="textup()"
					     	   onfocus="if(value==''){value=''}"
						      onblur="if (value ==''){value=''}" class="input01 require"></textarea>
	                </div>
					<br>
                </fieldset> 
            </div>
        </div>
         <div class="warp-button" style="margin-top: 0!important;padding-top: 0px;">
			 <button type="button" id="back" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->上一步</button>
			 <button type="button" id="save" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->保存</button>
		 </div>
		 <br>
    </div>
	
    <script>
		$.ajaxSetup({ cache: false });//全局禁用缓存
		var applyId = $("#applyId").val();
		var isManager = $("#isManager").val();
		$("#applyCheckText").hide();
		$(document).ready(function(){//该方法未改../test.json  测试用的
		
		    //申请有效期
	  		ajaxRequest("/DataShare/earthQuakeConfigQuery.action?siteType=1","get",function(res){
				$("#dayLangth").html("下载有效时长"+res[0].applyperiod+"天");
	  		});
	  		
			$(".pass").click(function(){
				$("#applyCheckText").hide();	
				$("#applyCheckText").val("");
				$("#iniRemark").show();
			})
			$(".out").click(function(){
				$("#applyCheckText").show().focus();
				$("#iniRemark").hide();
			});

			
			//保存
			$("#save").click(function(){
				var applyId = $("#applyId").val();
				var applyCheck = $('input:radio:checked').val();
				var applyCheckText = $("#applyCheckText").val();
				
				if(applyCheck==6){
					if(null==applyCheckText || applyCheckText.length==0 || applyCheckText=="请填写不通过原因"){
						//alert("请填写不通过原因");
						init( "请填写不通过原因",2);
						return false;
					}
				}

				$.ajax({
					url: "/DataShare/ApplyCheck.action?applyId="+applyId+"&applyCheck="+applyCheck+"&applyCheckText="+applyCheckText,
					type: "get",
					data: "json",
					timeout : 30000, //超时时间设置，单位毫秒
					beforeSend:function() {
						$('.zbg', window.parent.document).show();
						$('.myload', window.parent.document).show();
					},
					complete:function(){
						$('.zbg', window.parent.document).hide();
						$('.myload', window.parent.document).hide();
					}
				}).done(function(data) {
					init( "保存成功！",0);
	                $(".confirm",window.parent.document).click( function(){
	                   window.location.href="/DataShare/applyUserListToJsp.action?isManager=true&applyState=2&applyTitle=常规数据审核";
	                })
		
				});
			})
			//上一步
			$("#back").click(function(){
				var url="/DataShare/applyOrShowToJsp.action?thisNum=4&backOrDown=back&isManager="+isManager+"&applyId="+applyId;
				window.location.href=url;	
			})
		})
    </script>
</body>
</html>

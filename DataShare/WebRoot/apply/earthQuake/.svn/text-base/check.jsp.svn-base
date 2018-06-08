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
			border:0px solid #ccc;
		}
	</style>
</head>
<body>
     <input type="hidden" id="id" name="id" value="${id}"/>
     <div id="iframeheight" style=" margin-top: 0px;background:#f7f7f7;">
	    
	    <div class="pbox tspbox" style="padding-bottom:0px;">
           <div class="ptit">${applyTitle}</div>
  	    </div>
  	    
  		<div class="pbox tspbox" style=" margin-top: -25px;">
             <div class="base-info tsbase-info1" style="height:220px;">
                <fieldset style="border:0px solid #ccc;    padding-top: 10px;">
					<legend style="display:none;">审核结果通知</legend>
	                <div class="base-info-input" style="border:0px solid #ccc;">
						<div style="text-align:center; width:100%; height:40px;font-size:14px; color:#316bb5; margin-top:20px; line-height:50px; font-weight:bold;">${type}
							<input checked  type="radio" id="type" name="type" class="pass" value="5" >审核通过</input>
							<input type="radio" id="type" class="out" name="type" value="6" >审核不通过</input>
						</div>
						
						
						<div id="iniRemark" class="iniRemark" style="text-align:center;margin-top: 35px;">
							<div class="beizhu" id="dayLangth" style="color: #999;"></div>
						    <div class="beizhu" style="color: #999;">温馨提示：如果审核不通过，请填写原因!</div>
						</div>
						
						 <textarea id="remark" name="remark"  style="padding-top: 20px;"
						       onKeyDown="textdown(event)" onKeyUp="textup()"
					     	   onfocus="if(value==''){value=''}"
						      onblur="if (value ==''){value=''}" class="input01 require"></textarea>
	                </div>
					<br>
                </fieldset> 
            </div>
        </div>
        
         <div class="warp-button">
			 <button type="button" id="save" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->保存</button>
			 <button type="button" id="back" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->返回</button>
		 </div>
		 <br>
    </div>
	
    <script>
        $.ajaxSetup({ cache: false });//全局禁用缓存
		var id = $("#id").val();
		$("#remark").hide();
		$("#iniRemark").show();
		$(document).ready(function(){//该方法未改../test.json  测试用的
		
			//申请有效期
	  		ajaxRequest("/DataShare/earthQuakeConfigQuery.action?siteType=1","get",function(res){
				$("#dayLangth").html("下载有效时长"+res[0].applyvalidityday+"天");
	  		});
		
			$(".pass").click(function(){
				$("#remark").hide();	
				$("#remark").val("");
				$("#iniRemark").show();
			})
			$(".out").click(function(){
				$("#remark").show().focus();
				$("#iniRemark").hide();
			});
			
			//保存
			$("#save").click(function(){
				var type = $('input:radio:checked').val();
				var remark = $("#remark").val();
				if(type==4){
					if(null==remark || remark.length==0 || remark=="请填写不通过原因"){
						init( "请填写不通过原因",2);
						return false;
					}
				}
				$.ajax({
					url: "/DataShare/earthQuake/updateApplyType.action?id="+id+"&type="+type+"&remark="+remark,
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
	                   window.location.href="/DataShare/earthQuake/waitCheckListForManagerToJsp.action?applyTitle=待审核申请";
	                })
		
				});
			})
			//上一步
			$("#back").click(function(){
				window.location.href="/DataShare/earthQuake/waitCheckListForManagerToJsp.action?applyTitle=待审核申请";
			})
			//点击返回
			$("#back").click(function(){
				window.history.back(-1); 
			});
		})
    </script>
</body>
</html>

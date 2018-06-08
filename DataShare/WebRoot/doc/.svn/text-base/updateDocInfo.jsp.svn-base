<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/check.js"></script>
	<style>
		.baseInfo .base-info-input ul li{
          padding:0;
		}
		.baseInfo .base-info-input ul li input{
			height: 30px;
			line-height: 30px;
			width: 202px;
		}
		.uploadContainer {
			position: relative;
			margin: 0 auto;
		}
		#pathUpload {
			width: 202px;
			cursor: pointer;
			height: 30px;
			border: 1px solid #ddd;
			text-indent: 0.5em;
		}
		.uploadContainer #fileImage {
			display: inline-block;
			position: absolute;
			top: 0;
			left: 0;
			width: 202px;
			height: 30px;
			text-indent: 0.5em;
			opacity: 0;
			-moz-opacity: 0;
			filter: alpha(opacity=0);
			bottom: 15px;
		}
	</style>
</head>
<body>
<input type="hidden" id="tmpDocName" name="tmpDocName" value="${tmpDocName}"/>
<input type="hidden" id="tmpPath" name="tmpPath" value="${tmpPath}"/>
  <div id="iframeheight">
  		<div class="pbox tspbox">
        	<div class="ptit">文件上传</div>
            <div class="base-info tsbase-info baseInfo">
				<form enctype="multipart/form-data" action="/DataShare/upDocToSave2.action" method="post">
					<div class="base-info-input" style="margin-top: 0px;">
							<ul style="margin-left:270px;">
								<li>
									<span><b class="requireFlag">*</b>文件名称</span>
									<label><input id="docName" name="docName" type="text" value="" class="input01 require"></label>
									<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
									<div class="errState" id="errDocName" style="display:none;"><p class="tishi" style="text-indent: 0px;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写文件名称</p></div>
								</li>
								
								<li>
									<span><b class="requireFlag">*</b>选择文件</span>
									<label>
										<div class="uploadContainer">
											<input type="text" id="pathUpload" class="input01 require" style="filter:alpha(opacity=0)\9;border:1px solid #ccc\9" placeholder="请选择上传文件">
											<input  id="fileImage" type="file" size="30" name="file1"  style="filter:alpha(opacity=100)\9;background: #fafafa\9;border:1px solid #ccc\9">
										</div>
									</label>
									<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
									<div class="errState" id="errPath" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请上传文件</p></div>
								</li>
								<li>
									<span>文件描述</span>
									<label><input id="docDesc" name="docDesc" type="text" value="" class="input01"></label>
									<div class="sucuState"   style="display:none;"><p class="tishi" ><img src="/DataShare/img/pcgzs/success.png"></p></div>
									<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png"></p></div>
								</li>
                                <li>
                               		<span>&nbsp;</span>
									<table>
										<tr>
											<td><input type="submit" id="save" class="shengfen-button" value="上传" style="margin-left: -30px;width:111px;height: 26px;"/></td>
											<td><button type="button" id="back" class="shengfen-button" style="margin-left: 15px;width:111px;height: 26px;"><!--<img src="/DataShare/img/s2.png"/>-->返回</button></td>
										</tr>
									</table>
                                </li>
							</ul>
							<div class="clear"></div>
					</div>
                </form> 
            </div>
        </div>
        <div class="blank10"></div>
        </div>
        
    <script>
		$.ajaxSetup({ cache: false });//全局禁用缓存
		
		var tmpDocName = $("#tmpDocName").val();
		var tmpPath = $("#tmpPath").val();
		
		if(null!=tmpDocName && ''!=tmpDocName){
			$("#errDocName").show();
		}
		if(null!=tmpPath && ""!=tmpPath){
			$("#errPath").show();
		}
		
		$("#fileImage").change(function () {
			document.getElementById("pathUpload").value = this.value;
		});

    	var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;

    	$("#save").click(function( event ){
			$(".require").each(function(e){
				if($(this).val()==""){
					$(this).parents("li").find(".errState").show();
					$(this).parents("li").find(".sucuState").hide();
					return;
				}else{
					$(this).parents("li").find(".errState").hide();
					$(this).parents("li").find(".sucuState").show();					 
				}
			})
		});
		
    	//返回
		$("#back").click(function(){
			window.location.href="/DataShare/docInfoListToJsp.action?applyTitle=成果管理";	
		});

       
       //文档名称的长度
       $("#docName").blur(function(){
             var inputValue = $("#docName").val();
             if(null==inputValue || inputValue.length==0){
             	return true;
             }
             var checkObj = $("#docName").parent();
             if(!checkLengthParam(inputValue,2,10)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到10个字节");
                 flag_2 = false;
                 return;
            }else{
             	checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_2 = true;
            }
       })
       
       
        //文档描述的长度
       $("#docDesc").blur(function(){
             var inputValue = $("#docDesc").val();
             if(null==inputValue || inputValue.length==0){
             	return true;
             }
             var checkObj = $("#docDesc").parent();
             if(!checkLengthParam(inputValue,0,40)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度不能超过40字节");
                 flag_2 = false;
                 return;
            }else{
             	checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_2 = true;
            }
       })
    </script>
</body>
</html>

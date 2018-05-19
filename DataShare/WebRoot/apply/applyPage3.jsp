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
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    
    <link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
</head>
<body>
    <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
      <div id="iframeheight" style=" margin-top: 0px;background:#f7f7f7;">

	   	   <div class="web-width" >
		       <div class="for-liucheng">
			        <div class="liulist for-cur"></div>
			        <div class="liulist for-cur"></div>
			        <div class="liulist for-cur"></div>
			        <div class="liulist" style="border-radius: 0 5px 5px 0;"></div>
			        <div class="liutextbox">
			            <div class="liutext for-cur"><em>1</em><br /><strong>基本信息填写</strong></div>
			            <div class="liutext for-cur"><em>2</em><br /><strong>申请数据筛选</strong></div>
			            <div class="liutext for-cur"><em>3</em><br /><strong>审批表上传</strong></div>
			            <div class="liutext"><em>4</em><br /><strong>审核结果</strong></div>
			        </div>
		        </div>
	       </div>
					
		<div style="height:15px;margin: 0 auto;background:white;"></div>  
		
  		<div class="pbox tspbox" style=" margin:0 auto;background:#f7f7f7;">
            <div class="base-info tsbase-info1">
               <div class="base-info-input">
                	<div class="pbox tspbox">
			            <div class="base-info tsbase-info1">
			                <div class="base-info-input" style="padding:12px 0">
								<div class="base-info tsbase-info1"><span>审批表上传</span></div>
			                	<fieldset style="width: 430px;margin: 0 auto;">
									<div class="clearfix">
										<span  class="pointBig"></span>
										<div class="itemsContainer">
											必须使用网站提供的《数据共享使用规定审批表》
											<button type="button" id="down" class="download"><!--<img src="/DataShare/img/s1.png"/>-->下载</button>
										</div>
									</div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
							       <div  class="clearfix">
									   <span class="pointBig"></span>
										<div class="itemsContainer">
											该审批表一年内可以重复使用
										</div>
									</div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div  class="clearfix">
										<span class="pointBig"></span>
										<div class="itemsContainer">
											请您保存审批表原件以备后续环节使用
										</div>
									</div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div class="point"></div>
									<div  class="clearfix">
										<span class="pointBig"></span>
										<div class="itemsContainer">
												<form id="upForm" enctype="multipart/form-data" action="/DataShare/ApplyUpload.action?applyId=${applyId}" method="post">
													<div class="uploadContainer">
														<input type='text'  id='pathUpload'  placeholder='请选择审批表上传' >
														<input id="fileImage" type="file" size="30"  name="file1" multiple />
														<!-- <button type="button" class="chooseFile" style="position: relative;">选择</button>  -->
														<!-- <input id="chooseFile" class="chooseFile-1" type="file" size="30"  name="file1" value="选择" multiple style="width: 10%;height: 100%;z-index: 99999999999; opacity: 0;position: absolute;" /> -->
													</div>
													<input type="hidden" name="alais" />

												</form>
										</div>
									</div>
			               	 	</fieldset>
			                </div>
			            </div>
			        </div>
                </div>
            </div>
        </div>
        <div class="warp-button" style="background: #fff;margin-top: 0!important;padding-top: 15px;">
			 <button type="button" id="back" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->上一步</button>
			 <button type="button" id="up" class="shengfen-button" ><!--<img src="/DataShare/img/s1.png"/>-->下一步</button>
		</div>
    </div>

	

   <script>		
   		$.ajaxSetup({ cache: false });//全局禁用缓存
	   //判断浏览器是否支持placeholder属性
	   $(document).ready(function(){	
	   		$('.chooseFile').click(function(){
	   			$('.chooseFile-1').click();
	   		})
		   var doc=document,
		   inputs=doc.getElementsByTagName('input'),
		   supportPlaceholder='placeholder'in doc.createElement('input'),

		   placeholder=function(input){
			   var text=input.getAttribute('placeholder'),
			   defaultValue=input.defaultValue;
			   if(defaultValue==''){
				   input.value=text
			   }
			   input.onfocus=function(){
				   if(input.value===text)
				   {
					   this.value=''
				   }
			   };
			   input.onblur=function(){
				   if(input.value===''){
					   this.value=text
				   }
			   }
		   };
		   if(!supportPlaceholder){
			   for(var i=0,len=inputs.length;i<len;i++){
				   var input=inputs[i],
						   text=input.getAttribute('placeholder');
				   if(input.type==='text'&&text){
					   placeholder(input)
				   }
			   }
		   }
	   });
	   
	    //上一步
    	$("#back").click(function(){
    		var applyId = $("#applyId").val();
			var url="/DataShare/applyOrShowToJsp.action?thisNum=3&backOrDown=back&applyTitle=数据申请&isManager=false&applyId="+applyId;
    		window.location.href=url;	
    	});

    	$("#fileImage").change(function () {
			document.getElementById("pathUpload").value = this.value;
		});
    	
		$("#chooseFile").change(function () {
			document.getElementById("pathUpload").value = this.value;
		});
		
		$("#up").click(function(){

			 var paths = $("#pathUpload").val();
			 if(''==paths){
				init( "请选择审批表",2);
			 	return;
			 }
			 document.getElementById("upForm").submit();
		})
	
	
		
		//当点击下载时，进行文件下载操作
		$("#down").click(function(){
			var applyId = $("#applyId").val();
			window.location.href="/DataShare/download.action?applyId="+applyId;
		})
   </script>
   
</body>
</html>

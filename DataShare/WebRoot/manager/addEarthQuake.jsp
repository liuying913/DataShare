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
	<link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
	<script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/check.js"></script>
	<style>
		.shengfen-button:hover{
			color: #fff;;
		}
		.base-info-input ul {
           margin: 10px 0 0 70px;
        }
		.base-info-input ul li {
			width: 432px;
			text-align: left;
			padding: 10px 5px;
		}
		.base-info-input ul li span {
			height: 30px;
			line-height: 30px;
			width: 60px;
			margin-right: 8px;
			text-align: right;
		}
		.sucuState .tishi  img{
			position: relative;
			top:2px;
		}
	</style>
</head>
<body>
  <input type="hidden" id="siteNumber" name="siteNumber" value="${siteNumber}"/>
  <div id="iframeheight">
	    <div class="pbox tspbox" style="padding-bottom:0px;">
		  <div class="ptit">添加地震应急事件</div>
	  </div>
  		<div class="pbox">
            <div class="base-info  baseInfo" style="margin-top: 15px; ">
                <div class="base-info-input" style="margin-top: 0;">

                    <ul  class="clearfix">
                    	<li>
                        	<span><b class="requireFlag">*</b>名称</span>
                            <label><input id="name" name="name" type="text" value="" class="input01 require" ></label>
							<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写名称</p></div>
                        </li>
                        <li>
                        	<span><b class="requireFlag">*</b>发生时间</span>
                            <label><strong><input type="text" name="strTime" class="input01 require" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></strong></label>
							<div class="sucuState"   style="display:none;"><p class="tishi" ><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写发生时间</p></div>
                        </li>
                        <li>
                        	<span><b class="requireFlag">*</b>纬度</span>
                            <label><input id="siteLat" name="siteLat" type="text" value="" class="input01 require" ></label>
							<div class="sucuState"   style="display:none;"><p class="tishi" ><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写纬度</p></div>
                        </li>
						<li>
                        	<span><b class="requireFlag">*</b>经度</span>
                            <label><input id="siteLon" name="siteLon" type="text" value="" class="input01 require" ></label>
							<div class="sucuState"   style="display:none;"><p class="tishi" ><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写经度</p></div>
                        </li>
                        
                        <li>
                        	<span><b class="requireFlag">*</b>震级</span>
                            <label><input id="grade" name="grade" type="text" value="" class="input01 require" >级</label>
							<div class="sucuState"   style="display:none;"><p class="tishi" ><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写震级</p></div>
                        </li>
						<li>
                        	<span><b class="requireFlag">*</b>震源深度</span>
                            <label><input id="height" name="height" type="text" value="" class="input01 require">千米</label>
                            <div class="sucuState"   style="display:none;"><p class="tishi" ><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写震源深度</p></div>
                        </li>
                         <li>
                        	<span>整理方式</span>
                            <label >
								<input type="hidden"  name="crawType"   class="input01"   id="arrangement" value="0" />
								<div class="ck-switch">
									<span class="ck-switch-on">自动</span>
									<span class="ck-switch-off ck-switch-current ck-switch-current-40"></span>
								</div>
							</label>
                        </li>
                        
                        <li  class="arrangementArea">
                        	<span><b class="requireFlag myFlag" style="display: none;">*</b>整理范围</span>
                            <label><input name="crawRange" type="text" value="" class="crawRange input01" style="background: #eee" disabled="disabled">公里</label>
							<div class="sucuState"   style="display:none;"><p class="tishi" ><img src="/DataShare/img/pcgzs/success.png"></p></div>
                            <div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写整理范围</p></div>
                        </li>
						<li>
                        	<span>地点</span>
                            <label><input name="address" type="text" value="" class="input01" ></label>
                        </li>
                        <li>
                        	<span>描述</span>
                            <label><input name="remark" type="text" value="" class="input01" ></label>
                        </li>
                    </ul>
                </div>

            </div>
        </div>
	  <div class="fun-box tsfun-box">
		  <a href="javascript:void(0);"  class="shengfen-button"  id="btn1" style="margin:-2px auto 0;float:none;line-height: 35px;">启动整理</a>
		  <a href="javascript:void(0);"  class="shengfen-button"  id="btn3" style="margin:-2px auto 0;float:none;line-height: 35px;">极速整理</a>
		  <a href="javascript:void(0);"  class="shengfen-button"  id="btn2" style="margin:-2px auto 0;float:none;line-height: 35px;">返回</a>
	  </div>
</div>
        
    <script>
	    $.ajaxSetup({ cache: false });//全局禁用缓存
		var siteNumber = $("#siteNumber").val();
		var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;
		var i=0;
		var flag_1 = true;var flag_2 = true;var flag_3 = true;
		var flag_4 = true;var flag_5 = true;var flag_6 = true;
		
		//启动整理
    	$("#btn1").click(function(){
    	
    		//必须所有的校验都通过才可以
    		if(  !(flag_1 && flag_2 && flag_3 && flag_4 && flag_5 && flag_6)){
    			return;
    		}
    		i=0;
			str="1=1";
			 var len=$(".input01").length;
		     var rlen=$(".require").length;

			$(".require").each(function(e){
			
				if($(this).val()==""){
					$(this).parents("li").find(".errState").show();
					$(this).parents("li").find(".sucuState").hide();
					i=1;
				}else{
					$(this).parents("li").find(".errState").hide();
					$(this).parents("li").find(".sucuState").show();

					var  value = $("#arrangement").val();	

					if(e==rlen-1 && i==0){
						$(".input01").each(function(a) {
							str=str+"&"+$(this).prop("name")+"="+$(this).val();
						  	if(a==len-1){
						  	    str = encodeURI(str);
								var URL="/DataShare/earthQuakeDataReduction/addEarthQuake.action?"+str;
								ajaxRequest(URL,"get",function(res){
									if(res.date[0].code==1){
										init( "保存成功" ,0 );
										 $(".confirm",window.parent.document).click( function(){
						                   window.location.href="/DataShare/manager/manager/earthQuakeListToJsp.action?applyTitle=地震应急事件管理";
						                });
									}else{
										
										init( res.date[0].msg ,1 );
									}
								})
								
							}  
						});	
					}
				}
			})
		})

		//极速整理模式
    	$("#btn3").click(function(){
    	
    		//必须所有的校验都通过才可以
    		if(  !(flag_1 && flag_2 && flag_3 && flag_4 && flag_5 && flag_6)){
    			return;
    		}
    		i=0;
			str="1=1";
			 var len=$(".input01").length;
		     var rlen=$(".require").length;

			$(".require").each(function(e){
			
				if($(this).val()==""){
					$(this).parents("li").find(".errState").show();
					$(this).parents("li").find(".sucuState").hide();
					i=1;
				}else{
					$(this).parents("li").find(".errState").hide();
					$(this).parents("li").find(".sucuState").show();

					var  value = $("#arrangement").val();	

					if(e==rlen-1 && i==0){
						$(".input01").each(function(a) {
							str=str+"&"+$(this).prop("name")+"="+$(this).val();
						  	if(a==len-1){
						  	    str = encodeURI(str);
								var URL="/DataShare/earthQuakeDataReduction/addEarthQuakeByEmergency.action?"+str;
								ajaxRequest(URL,"get",function(res){
									if(res.date[0].code==1){
										init( "保存成功" ,0 );
										 $(".confirm",window.parent.document).click( function(){
						                   window.location.href="/DataShare/manager/manager/earthQuakeListToJsp.action?applyTitle=地震应急事件管理";
						                });
									}else{
										
										init( res.date[0].msg ,1 );
									}
								})
								
							}  
						});	
					}
				}
			})
		})
		
		
		$(document).ready(function(){
			$(".userName").attr("disabled","disabled");
			ajaxRequest("/DataShare/getBaseSiteInfoById.action?siteNumber="+siteNumber,"get",function(res){
				if( res[0] == null ){
					 return false
				}else{
					$(".input01").eq(0).val(res[0].siteNumber);
					$(".input01").eq(1).val(res[0].siteName);
					$(".input01").eq(2).val(res[0].siteLat);
					$(".input01").eq(3).val(res[0].siteLng);
					$(".input01").eq(4).val(res[0].zoneCode);
					$(".input01").eq(5).val(res[0].departmentCode);
					$(".input01").eq(6).val(res[0].order);
					$(".input01").eq(7).val(res[0].address);
				}
			})
    	});
    	
    	
    	
    	//名称校验
        $("#name").blur(function(){
             //****校验中文名****
             var inputValue = $("#name").val();
             var checkObj = $("#name").parent();
             if(!checkCNName(inputValue)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须包含中文字符");
                 flag_1 = false;
                 return;
             }else{
             	if(!checkLengthParam(inputValue,2,40)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到40个字节");
	                 flag_1 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_1 = true;
	            }
            }
       })
       //****校验用户名结束****
    	
        
       //纬度校验
       $("#siteLat").blur(function(){
             var inputValue = $("#siteLat").val();
             var checkObj = $("#siteLat").parent();
             if(!IsDoubleTwo(inputValue)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为2位小数以内");
                 flag_2 = false;
                 return;
             }else{
             	if(!checkLengthParam(inputValue,1,6)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为1到6个字节");
	                 flag_2 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_2 = true;
	            }
            }
       })
       //***纬度校验结束****
       
       //经度校验
       $("#siteLon").blur(function(){
             var inputValue = $("#siteLon").val();
             var checkObj = $("#siteLon").parent();
             if(!IsDoubleTwo(inputValue)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为2位小数以内");
                 flag_3 = false;
                 return;
             }else{
             	if(!checkLengthParam(inputValue,1,6)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为1到6个字节");
	                 flag_3 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_3 = true;
	            }
            }
       })
       //***经度校验结束****
    	
    	
       //震级校验
       var flag_4 = true;
       $("#grade").blur(function(){
             var inputValue = $("#grade").val();
             var checkObj = $("#grade").parent();
             if(!IsDoubleOne(inputValue)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为1位小数以内");
                 flag_4 = false;
                 return;
             }else{
             	if(!checkLengthParam(inputValue,1,4)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为1到4个字节");
	                 flag_4 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_4 = true;
	            }
            }
       })
       //***震级校验结束****
       
       
       //震源深度校验
       $("#height").blur(function(){
             var inputValue = $("#height").val();
             var checkObj = $("#height").parent();
             if(!IsDoubleTwo(inputValue)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为2位小数以内");
                 flag_5 = false;
                 return;
             }else{
             	if(!checkLengthParam(inputValue,1,6)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为1到6个字节");
	                 flag_5 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_5 = true;
	            }
            }
       })
       //***震源深度校验结束****
       
       
       
       //整理范围校验
       $("#crawRange").blur(function(){
             var inputValue = $("#crawRange").val();
             var checkObj = $("#crawRange").parent();
             if(!IsDoubleTwo(inputValue)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为2位小数以内");
                 flag_6 = false;
                 return;
             }else{
             	if(!checkLengthParam(inputValue,1,7)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为1到7个字节");
	                 flag_6 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_6 = true;
	            }
            }
       })
       
       /*整理方式不同 整理范围是否可填写*/
		$('.ck-switch .ck-switch-off').unbind('click').bind('click',function(){
			$('.ck-switch').addClass('active');
			$('.ck-switch .ck-switch-on').addClass('ck-switch-current ck-switch-current-3').html('');
			$('.ck-switch .ck-switch-off').removeClass('ck-switch-current ck-switch-current-40').html('手动');
			$(".arrangementArea .crawRange").removeAttr("disabled").addClass("require");
			$(".arrangementArea .crawRange").css({background:"#fff"})
			$(".myFlag").show();
			$("#arrangement").val( "1");

		});

		$('.ck-switch .ck-switch-on').unbind('click').bind('click',function(){
			if($(this).hasClass('ck-switch-on')){
				$('.ck-switch').removeClass('active');
				$('.ck-switch .ck-switch-on').removeClass('ck-switch-current ck-switch-current-3').html('自动');
				$('.ck-switch .ck-switch-off').addClass('ck-switch-current ck-switch-current-40').html('');
				$(".arrangementArea .crawRange").attr({disabled:"disabled"}).removeClass("require").val('');
				$(".arrangementArea .crawRange").css({background:"#eee"})
			
				$(".myFlag").hide();
				$("#arrangement").val( "0");
			}
		});
			
       //点击返回
		$("#btn2").click(function(){
			window.history.back(-1); 
		});
    </script>
</body>
</html>

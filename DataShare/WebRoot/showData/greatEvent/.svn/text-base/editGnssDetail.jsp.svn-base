<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    
    <script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <style type="text/css">
    	.base-info-input ul li {
		    width: 368px;
		    text-align: left;
		    padding: 10px;
		}
    </style>
</head>
<body>
  <input type="hidden" id="id" name="id" value="${id}"/>
    <div id="iframeheight">
         <div class="pbox tspbox" style="padding-bottom:0px;">
           <div class="ptit">更换接收机详情</div>
  	    </div>
  		<div class="pbox">
            <div class="base-info baseInfo ">
                <div class="base-info-input">
                    <ul class="clearfix" style="margin: 10px 0 0 113px;">
                    	<li>
                        	<span>台站编号</span>
                            <label><input name="siteNumber" type="text" value="" class="input01" disabled="disabled" ></label>
                        </li>
                        <li>
                        	<span>台站名称</span>
                            <label><input name="siteName" type="text" value="" class="input01" disabled="disabled"></label>
                        </li>
                        <li>
                        	<span><b class="requireFlag">*</b>开始时间</span>
                            <label><input name="startTimeStr" type="text" value="" class="input01 require" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></label>
                            <div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                            <div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">开始时间</p></div>
                        </li>
                        <li>
                        	<span><b class="requireFlag">*</b>结束时间</span>
                            <label><input name="endTimeStr" type="text" value="" class="input01 require" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></label>
                            <div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                            <div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">结束时间</p></div>
                        </li>
                        	 <li>
                        	<span>接收机类型(前)</span>
                            <label><input name="model_old" type="text" value="" class="input01"></label>
                        </li>
						<li>
                        	<span>接收机类型(后)</span>
                            <label><input name="model_new" type="text" value="" class="input01"></label>
                        </li>
                        
                         <li>
                        	<span>SN(前)</span>
                            <label><input name="sn_old" type="text" value="" class="input01"></label>
                        </li>
						<li>
                        	<span>SN(后)</span>
                            <label><input name="sn_new" type="text" value="" class="input01"></label>
                        </li>
                        
                         <li>
                        	<span>FW(前)</span>
                            <label><input name="fw_old" type="text" value="" class="input01"></label>
                        </li>
						<li>
                        	<span>FW(后)</span>
                            <label><input name="fw_new" type="text" value="" class="input01"></label>
                        </li>
                        <li>
                        	<span>问题描述</span>
                            <label><input name="describe" type="text" value="" class="input01" ></label>
                        </li>
						<li>
                        	<span>维修反馈</span>
                            <label><input name="feedback" type="text" value="" class="input01" ></label>
                        </li>
						
					
                    </ul>
                </div>
            </div>
			<div class="warp-button" style="text-align: center">
				</br></br>
				<button type="button" id="save" class="shengfen-button" style="width:111px;height: 26px;float: none;display: inline-block"><!--<img src="/DataShare/img/s1.png"/>-->保存</button>
				<button type="button" id="back" class="shengfen-button" style="margin-left: 15px;width:111px;height: 26px;float: none;display: inline-block"><!--<img src="/DataShare/img/s2.png"/>-->返回</button>
			</div>
        </div>
        </div>
    <script>		$.ajaxSetup({ cache: false });//全局禁用缓存
		var id = $("#id").val();
    	$(document).ready(function(){
    		var url=window.location.href;
		/*	$(".userName").attr("disabled","disabled");*/
			ajaxRequest("/DataShare/greatEvent/typelList.action?id="+id,"get",function(res){
				$(".input01").eq(0).val(res[0].siteNumber);
				$(".input01").eq(1).val(res[0].siteName);
				$(".input01").eq(2).val(res[0].startTimeStr);
				$(".input01").eq(3).val(res[0].endTimeStr);
				
				$(".input01").eq(4).val(res[0].model_old);
				$(".input01").eq(5).val(res[0].model_new);
				
				$(".input01").eq(6).val(res[0].sn_old);
				$(".input01").eq(7).val(res[0].sn_new);
				
				$(".input01").eq(8).val(res[0].fw_old);
				$(".input01").eq(9).val(res[0].fw_new);
				
				$(".input01").eq(10).val(res[0].describe);
				$(".input01").eq(11).val(res[0].feedback);
			});
			 /*保存功能*/
			var str="";
			var len=$(".input01").length;
			var rlen=$(".require").length;
			var i=0;
			$("#save").click(function(){
	    		i=0;
				str="1=1";
				$(".require").each(function(e){
					console.log( $(this).val() );

					if($(this).val()==""){

						$(this).parents("li").find(".errState").show();
						$(this).parents("li").find(".sucuState").hide();
					
						i=1;
					}else{

						$(this).parents("li").find(".errState").hide();
					    $(this).parents("li").find(".sucuState").show();

						if(e==rlen-1&&i==0){
							$(".input01").each(function(a) {
								str=str+"&"+$(this).prop("name")+"="+$(this).val();

							  	if(a==len-1){
									var URL="/DataShare/greatEvent/updateGreatEvent.action?id="+id+"&"+str;
									URL=encodeURI(URL);     
									ajaxRequest(URL,"get",function(res){

										if(res.code==1){
											window.location.href="/DataShare/manager/greatEventListToJsp.action?applyTitle=站点大事记管理";	
										}else{
											alert(res.msg);	
										}
									})
								}  
							});	
						}
					}
				})	
			});
			
			//返回
			$("#back").click(function(){
				window.history.back(-1);
				//window.location.href="/DataShare/greatEvent/listToJsp2.action?applyTitle=站点信息及大事记列表2";	
			})
    	})
    </script>
</body>
</html>

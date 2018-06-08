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
  <input type="hidden" id="siteType" name="siteType" value="${siteType}"/>
  <div id="iframeheight">
  
    	<div class="pbox tspbox" style="padding-bottom:0px;">
           <div class="ptit">基准站信息编辑</div>
  	    </div>
  	    
  		<div class="pbox">
            <div class="base-info baseInfo">
                <div class="base-info-input">
                    <ul  class="clearfix">
                    	<li>
                        	<span>台站编号</span>
                            <label><input id="siteNumber2" name="siteNumber2" type="text" value="" class="input01 require" err="请填写台站编号"></label>
                        </li>
                        <li>
                        	<span>台站名称</span>
                            <label><input name="siteName" type="text" value="" class="input01 require" err="请填写台站名称"></label>
                        </li>
                        
                        <li>
                        	<span>纬度</span>
                            <label><input name="siteLat" type="text" value="" class="input01 require" err="请填写纬度"></label>
                        </li>
						<li>
                        	<span>经度</span>
                            <label><input name="siteLng" type="text" value="" class="input01 require" err="请填写经度"></label>
                        </li>
						
						<li>
                        	<span>所属地区</span>
                            <label>
								<select id="zoneCode" name="zoneCode" class="input01 require" err="请填写所属地区">
								</select>
							</label>
                        </li>
                        <li>
                        	<span>所属部委</span>
                            <label>
								<select id="departmentCode" name="departmentCode" class="input01 require" err="请填写所属部委">
								</select>
							</label>
                        </li>
                        
                        <li>
                        	<span>排序</span>
                            <label><input name="order" type="text" value="" class="input01 require" err="请填写排序"></label>
                        </li>
						<li>
                        	<span>地址</span>
                            <label><input name="address" type="text" value="" class="input01 require" err="请填写地址"></label>
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
    <script>
			$.ajaxSetup({ cache: false });//全局禁用缓存
		var siteNumber = $("#siteNumber").val();
		var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;
		var i=0;
    	$("#save").click(function(){
    		i=0;
			str="1=1";
			$(".require").each(function(e){
				if($(this).val()==""){
					$(".fun-box .error").show();
					$(".fun-box .error").text($(this).attr("err"));	
					$(this).addClass("err");
					i=1;
				}else{
					if(e==rlen-1&&i==0){
						$(".input01").each(function(a) {
							str=str+"&"+$(this).prop("name")+"="+$(this).val();//str=str+"/"+$(this).name+':'+$(this).attr("name")+" "+$(this).prop("name");
						  	if(a==len-1){
						  	    str = encodeURI(str);
								var URL="/DataShare/updateSiteInfo.action?siteNumber="+siteNumber+"&"+str;
								ajaxRequest(URL,"get",function(res){
									if(res.date[0].code==1){
										window.location.href="/DataShare/baseSiteInfoListToJsp.action?applyTitle=基准站管理&siteType=1";	
									}else{
										alert(res.date[0].msg);	
									}
								})
								
							}  
						});	
					}
				}
			})	
			
		})
		
		
		
		
		$(document).ready(function(){
		
			//地区下拉列表
			ajaxRequest("/DataShare/getZoneInfoList.action","get",function(res){
				 $.each(res, function(key, value) {
					 $("#zoneCode").append("<option value='"+value.zoneCode+"'>"+value.zoneName+"</option>");
				 });
			})
			//部委下拉列表
			ajaxRequest("/DataShare/getDicInfoListByType.action?dicType=dic-001","get",function(res){
				 $.each(res, function(key, value) {
					 $("#departmentCode").append("<option value='"+value.dicEnName+"'>"+value.dicCnName+"</option>");
				 });
			})
			
			$(".userName").attr("disabled","disabled");
			ajaxRequest("/DataShare/getBaseSiteInfoById.action?siteNumber="+siteNumber,"get",function(res){
				$(".input01").eq(0).val(res[0].siteNumber);
				$(".input01").eq(1).val(res[0].siteName);
				$(".input01").eq(2).val(res[0].siteLat);
				$(".input01").eq(3).val(res[0].siteLng);
				$(".input01").eq(4).val(res[0].zoneCode);
				$(".input01").eq(5).val(res[0].departmentCode);
				$(".input01").eq(6).val(res[0].order);
				$(".input01").eq(7).val(res[0].address);
			})
		
			//返回
			$("#back").click(function(){
				var url="/DataShare/baseSiteInfoListToJsp.action?applyTitle=基准站管理&siteType=1";
				window.location.href=url;	
			})
		
		
    	})
    	
    </script>
</body>
</html>

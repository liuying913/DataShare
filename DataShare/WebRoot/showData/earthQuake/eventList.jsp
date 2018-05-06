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
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
  <div id="iframeheight" style="height: 459px;">
  		<div class="pbox tspbox">
  			<div class="ptit">地震应急事件列表</div>
  			<div class="sea-box" style="margin-top:10px;">
				<div class="shengfen shengfen03" style="text-align:center;">
					<div class="span pp" style="text-align:center;"> 
                    	<span  style="width:80px;height: 32px;line-height: 24px;    font-size: 14px;    line-height: 30px;">开始时间</span>   
                    	<input class="startTime" type="text"  style="width:100px;height:30px;line-height: 30px;font-size: 14px;padding-bottom:0px;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
                    </div>	 
					<div class="span pp">
                    	<span  style="width:80px;height: 32px;line-height: 24px;    line-height: 30px;">结束时间</span>   
                    	<input class="endTime"  type="text"   style="width:100px;height: 30px;line-height: 30px;font-size: 14px;" onFocus="WdatePicker({isShowClear:false,readOnly:true})"/>
                    </div>
					<input type="text" value="" placeholder="请输入要搜索的文字" class="input01"  style="width:300px;height: 30px;line-height: 30px;font-size: 16px;">
					<input type="button" value="搜索" class="seabtn"  style="width:100px;height: 32px; font-size: 14px;margin-left:-10px;">							
				</div>
  				<div class="clear" style="height:0px;"></div>
  			</div>  
			<style> 
            	.sea-box{ box-shadow:none;height:auto;width: 95%;margin-left: 5%;}
				.tspbox .shengfen03{ background-color:#fff;padding-left:0;float:left;margin-top:0;padding:0;}
				.span span{float:left;}
				.sea-box .span{width:auto;margin-top:0;}
				.shengfen03 input{height:23px;line-height:23px;margin-left:5px;margin-top:0;width:150px;}
				.sea-box .input01{width:250px;height:23px;line-height:23px;border: 1px solid #b0b0b0 !important;}
            </style>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th style="white-space: nowrap;">年</th>
                <th style="white-space: nowrap;">地震名称</th>
                <th style="white-space: nowrap;">震中纬度</th>
                <th style="white-space: nowrap;">震中经度</th>
                <th style="white-space: nowrap;">震级</th>
                <th style="white-space: nowrap;">发生时间</th>
                <th class="ts" style="white-space: nowrap;">操作</th>
              </tr>
              <tbody id="tbody">
              </tbody>
            </table>
        </div>
        <!--分页-->
        <div class="newstable-content">
         <div class="newstable-fy">
             <div class="newstable-fy-pages">
                 <span>共<font color="#ff0000" id="zongyeshu"></font>页<font color="#ff0000" id="zongshuju"></font>条记录</span>
                 <a href="#" onclick="yeshu(1)">首页</a>
                 <a href="#" id="prevpage" onclick="shang();">上一页</a>
                 <span class="everypages">
                 </span>
                 <a href="#" onclick="next();">下一页</a>
                 <a href="#" onclick="yeshu('end')">末页</a>
                 <span>跳转至
                     <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);" class="optionpages"></select>
                 </span>
             </div>
         </div>
     </div>
    </div>
  <script>
  	$.ajaxSetup({ cache: false });//全局禁用缓存
    var startTime = getYearOne();
  	var endTime = getToday();
	$('.startTime').val(startTime);
	$('.endTime').val(endTime);
  	$(document).ready(function(){
  		var str="";
  		ajaxRequest("/DataShare/showData/contingencyEventList.action?show=true","get",function(res){
			data = res;
			shua();
			page();
  		})
  		$(".seabtn").click(function(){
  			var item=$(".sea-box .input01").val();
  			var startTime=$(".startTime").val();
  			var endTime = $(".endTime").val();
  			ajaxRequest("/DataShare/showData/contingencyEventList.action?show=true&startTime="+startTime+"&endTime="+endTime+"&keyWords="+item,"get",function(res){
				data = res;
	            shua();
				page();
	  		})
  		})
  	});
  	
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value){
				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+value.year+"</td>"
					+"	    <td>"+value.name+"</td>"
					+"	    <td>"+value.siteLat+"</td>"
					+"	    <td>"+value.siteLon+"</td>"
					+"	    <td>"+value.grade+"</td>"
					+"	    <td>"+value.strTime+"</td>"
					+"	    <td><a href='/DataShare/showData/showEventMessageToJsp.action?earthQuakeId="+value.id+"'>事件查看</a>&nbsp;<a href='/DataShare/showData/earthQuakeMapToJsp.action?earthQuakeId="+value.id+"'>地图展示</a>&nbsp;<a href='/DataShare/showData/showEarthQuakeToJsp.action?earthQuakeId="+value.id+"'>数据信息</a></td>"
					+"    </tr>"
				}else{ 
					str=str+"<tr>"
					+"	    <td>"+value.year+"</td>"
					+"	    <td>"+value.name+"</td>"
					+"	    <td>"+value.siteLat+"</td>"
					+"	    <td>"+value.siteLon+"</td>"
					+"	    <td>"+value.grade+"</td>"
					+"	    <td>"+value.strTime+"</td>"
					+"	    <td><a href='/DataShare/showData/showEventMessageToJsp.action?earthQuakeId="+value.id+"'>事件查看</a>&nbsp;<a href='/DataShare/showData/earthQuakeMapToJsp.action?earthQuakeId="+value.id+"'>地图展示</a>&nbsp;<a href='/DataShare/showData/showEarthQuakeToJsp.action?earthQuakeId="+value.id+"'>数据信息</a></td>"
					+"    </tr>"
				}
		  	}
		});
		$("#tbody").html(str); 
		$('#iframe', parent.document).css("height",$("#iframeheight").height());
	}
  </script>
</body>
</html>

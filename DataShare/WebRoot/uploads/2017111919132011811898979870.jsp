<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>短信发布平台</title>
	<link rel="stylesheet" type="text/css" href="/SMSServer/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/SMSServer/css/css.css" />
        <link rel="stylesheet" type="text/css" href="/SMSServer/css/drop-down.css" />
    <link rel="stylesheet" type="text/css" href="/SMSServer/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/SMSServer/css/qd.css"/>
    <link rel="stylesheet" type="text/css" href="/SMSServer/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/SMSServer/css/mypcgzs.css" />
    <script type="text/javascript" src="/SMSServer/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/SMSServer/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/SMSServer/js/extra.js"></script>
	<script type="text/javascript" src="/SMSServer/js/pageJsonList.js"></script>
	<script src="/SMSServer/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/SMSServer/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/SMSServer/js/select/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/SMSServer/js/select/select-widget-min.js"></script>
</head>

<style>
.sea-box .userId {
    float: left;
    color: #333;
    margin-right: 15px;
    width: 100px;
    height: 34px;
    line-height: 34px;

    text-indent: 0.5em;
    padding: 8px 0\9;
    box-shadow:none;
}
.userId {
    float: left;
    color: #333;
    margin-right: 15px;
    width: 100px;
    height: 22px;
    line-height: 22px;
  
    text-indent: 0.5em;
    padding: 8px 0\9;
    box-shadow: none;
}
#userId{ 
    border:1px solid #ddd !important;
	}
.sea-box select {
    -webkit-appearance: none !important;
    -moz-appearance: none !important;
    -webkit-border-radius: 0;
    background: url(data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZlcnNpb249IjEuMSIgeD0iMTJweCIgeT0iMHB4IiB3aWR0aD0iMjRweCIgaGVpZ2h0PSIzcHgiIHZpZXdCb3g9IjAgMCA2IDMiIGVuYWJsZS1iYWNrZ3JvdW5kPSJuZXcgMCAwIDYgMyIgeG1sOnNwYWNlPSJwcmVzZXJ2ZSI+PHBvbHlnb24gcG9pbnRzPSI1Ljk5MiwwIDIuOTkyLDMgLTAuMDA4LDAgIi8+PC9zdmc+) 100% center no-repeat #fff;
}

</style>

<body>
	<%@ include file="head.jsp" %>
	
    <div class="banner_son">
		<img src="/SMSServer/img/img_zxjj.jpg" style="margin:-350px 0px;"/>
    </div>
    
  <div id="iframeheight">
  		<div class="pbox tspbox">
  			<div class="clear" style="height:10px;"></div>
  			<div class="sea-box" style="text-align:center;margin:0 auto;height:36px;margin-top:20px;">
  			
	  			<div class="shengfen shengfen03" style="width:auto;height:36px;">
	  			
					<div class="span pp"> 
                    	<span class="startTitle"  style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑'; padding:0px 5px; margin-top: 0px;margin-left: 30px;">开始时间</span>   
                    	<input class="startTime" style="width:160px;height:34px;font-size: 14px;line-height: 32px;font-family: '微软雅黑';" type="text"  onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true})"/>
                    </div>	 
					<div class="span pp">
                    	<span class="enTitle" style="margin-left: 15px;">结束时间</span>   
                    	<input class="endTime" style="width:160px;height:34px;font-size: 14px;line-height: 32px;font-family: '微软雅黑';" type="text" onFocus="WdatePicker({isShowClear:false,dateFmt:'yyyy-MM-dd HH:mm',readOnly:true})"/>
                    </div>  
  
				</div>
				
				<div class="span pp">
		  			<span class="myTip" style="margin-left: 15px;">短信类型</span>
             		<div class="select-container">
	                   <select id="userId" style="height:34px;font-size: 14px;line-height: 14px;font-family: '微软雅黑';margin-left: -5px;" class="userId" name="userId">
	                   	  <option value='' style="font-size: 14px; font-family: '微软雅黑';">全部短信</option>
	                   	  <option value='true' style="font-size: 14px; font-family: '微软雅黑';">发送成功</option>
	                   	  <option value='false' style="font-size: 14px; font-family: '微软雅黑';">发送失败</option>
	                   </select>
	                </div>
    			</div>  
    			
  				<input type="button" value="搜索" class="seabtn" id="select" style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑';margin-left: 20px;">
  				<div class="span pp">
                <input type="button" value="导出" class="seabtn" id="excel" style="height:34px;font-size: 16px;line-height: 32px;font-family: '微软雅黑'; padding:0px 5px; margin-top: 0px;margin-left: 30px;">
  				</div>
  			</div>  
			<style> 
            	.sea-box{ box-shadow:none;height:auto;width: 95%;margin-left: 28%;}
				.tspbox .shengfen03{ background-color:#fff;padding-left:0;float:left;margin-top:0;padding:0;}
				.span span{float:left;}
				.sea-box .span{width:auto;margin-top:0;}
				.shengfen03 input{height:34px;line-height:34px;margin-left:5px;margin-top:0;width:220px;}
				.sea-box .input01{width:250px;height:34px;line-height:34px;border: 1px solid #b0b0b0 !important;}
				.seabtn{height:34px;}
				.startTitle{height:34px;}
				.enTitle{height:34px;}
            </style>
            
            
           <div style="min-height: 160px;text-align:center;margin-top:-10px;" >
			   <table width="80%" border="0" cellspacing="0" cellpadding="0" class="table01" style="text-align:center;">
				   <tr>
					   <th style="white-space: nowrap;">序号</th>
					   <th style="white-space: nowrap;">台站名称</th>
					   <th style="white-space: nowrap;">台站编号</th>
					   <th style="white-space: nowrap;">所属省份</th>
					   <th style="white-space: nowrap;">所属部委</th>
					   <th style="white-space: nowrap;">故障类型</th>
					   <th style="white-space: nowrap;">故障开始时间</th>
					   <th style="white-space: nowrap;">故障结束时间</th>
					   <th style="white-space: nowrap;">发送号码</th>
					   <th style="white-space: nowrap;">短信发送时间</th>
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
	                        <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);" class="optionpages">
	                        </select>
	                    </span>
	                </div>
	            </div>
	        </div>
        </div>
  </div>
  <script>
    $.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  		var startTime = getMonthOne();
  		var endTime = getToday();
  		$(".startTime").val(startTime);
  		$(".endTime").val(endTime); //结束时间赋值
  		var url = "/SMSServer/querySmsInfoList.action?startTime="+startTime+"&endTime="+endTime;
  		ajaxRequest(url,'get',function(res){
			data = res;
			shua();
			page();
			
			
			/*修改select的默认方式*/
            $("#userId").selectWidget({
                change  : function (changes) {
                  return changes;
                },
                effect       : "slide",
                keyControl   : true,
                speed        : 200,
                scrollHeight : 250
            });
  		})
  	});
  	
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value){
    			if(key%2!=0){
    				str=str+"<tr class='even'>"
    				+"	    <td>"+value.id+"</td>"
    				+"	    <td>"+value.siteName+"</td>"
    				+"	    <td>"+value.siteNumber+"</td>"
    				+"	    <td>"+value.zoneName+"</td>"
    				+"	    <td>"+value.departMent+"</td>"
    				+"	    <td>"+value.description+"</td>"
    				+"	    <td>"+value.startTimeStr+"</td>"
    				+"	    <td>"+value.endTimeStr+"</td>"
    				+"	    <td>"+value.phone+"</td>"
    				+"	    <td>"+value.createTimeStr+"</td>"
    				+"    </tr>"
    			}else{
    				str=str+"<tr>"
    				+"	    <td>"+value.id+"</td>"
    				+"	    <td>"+value.siteName+"</td>"
    				+"	    <td>"+value.siteNumber+"</td>"
    				+"	    <td>"+value.zoneName+"</td>"
    				+"	    <td>"+value.departMent+"</td>"
    				+"	    <td>"+value.description+"</td>"
    				+"	    <td>"+value.startTimeStr+"</td>"
    				+"	    <td>"+value.endTimeStr+"</td>"
    				+"	    <td>"+value.phone+"</td>"
    				+"	    <td>"+value.createTimeStr+"</td>"
    				+"    </tr>"
    			}
		  	}
		});
		$("#tbody").html(str); 
		$('#iframe', parent.document).css("height",$("#iframeheight").height());
	}
  	
  	
	$("#select").click(function(){
		var isFlag=$("#userId").val();
		var startTime=$(".startTime").val();
		var endTime=$(".endTime").val();
		ajaxRequest("/SMSServer/querySmsInfoList.action?isFlag="+isFlag+"&startTime="+startTime+"&endTime="+endTime,"get",function(res){
		data = res;
          shua();
		  page();
		})
	});
	
	//===导出
	$('#excel').click(function() {
		var isFlag=$("#userId").val();
		var startTime=$(".startTime").val();
		var endTime=$(".endTime").val();
		var url = "/SMSServer/dataExcel.action?isFlag="+isFlag+"&startTime="+startTime+"&endTime="+endTime;
		window.location.href=url;
	});
	
	
	//获得当月第一天
	function getMonthOne(){//获得今天
		var today=new Date();
		year = today.getFullYear();
		month = today.getMonth()+1;
		if(month<10){month="0"+month;}
		var iniEndTime = year+"-"+month+"-01"+" 00:00:00";
		return iniEndTime;
	}

	function getToday(){//获得今天
		var today=new Date();
		year = today.getFullYear();
		month = today.getMonth()+1;
		if(month<10){month="0"+month;}
		date = today.getDate();
		if(date<10){date="0"+date;}
		var iniEndTime = year+"-"+month+"-"+date+" 23:59:59";
		return iniEndTime;
	}
  </script>
  <%@ include file="foot.jsp" %>
</body>
</html>

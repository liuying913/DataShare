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
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    
    <script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
	<script src="/DataShare/js/analysis/personOption.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
  <input id="applyState" type="hidden" name="applyState" value="${applyState}"/>
  <div id="iframeheight">
  		<div class="pbox tspbox">
			<div class="ptit">${applyTitle}</div>
			
			<select id="userId" class="userId" name="userId" style="width:140px; margin-top: 0px;" ></select>
			
			<select name="apply-state" id="apply-state">
				<option value="">全部</option>
				<!--<option value="0">待审核</option>-->
				<option value="3">审核通过</option>
				<!--<option value="4">审核未通过</option>-->
				<option value="5">申请过期</option>
			</select>
  			<div class="sea-box" style="margin: 0 0 10px 195px;">

  				<input type="text" value=""  placeholder="请输入要搜索的文字" class="input01">
  				<input type="button" value="搜索" class="seabtn">
  			</div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th width="20%">地震名称</th>
                <th width="6%">申请人</th>
                <th width="8%">所属单位</th>
                <th width="15%">申请时间</th>
                <th width="18%">下载有效期</th>
                <th width="8%">申请状态</th>
                <th width="8%" class="ts">操作</th>
              </tr>
              <tbody id="tbody">
              </tbody>
            </table>
            <!--分页-->
            <div class="newstable-content">
	            <div class="newstable-fy">
	                <div class="newstable-fy-pages">
	                    <span>共<font color="#ff0000" id="zongyeshu"></font>页<font color="#ff0000" id="zongshuju"></font>条记录</span>
	                    <a href="#" onclick="yeshu(1)">首页</a>
	                    <a href="#" id="prevpage" onclick="shang();">上一页</a>
	                    <span class="everypages"></span>
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
  		function_UserList();
  		var str="";
  		var userId = $("#userId").val(); //用户id
  		ajaxRequest("/DataShare/earthQuake/analyTableApplyList.action?userId="+userId+"&siteType=1","get",function(res){
			data = res;
			shua();
			page();
  		})
  		$(".seabtn").click(function(){
  			var txtParam=$(".sea-box .input01").val();
			var state=$("#apply-state").val();
  			ajaxRequest("/DataShare/earthQuake/analyTableApplyList.action?userId="+userId+"&state="+state+"&siteType=1&txtParam="+txtParam,"get",function(res){
				data = res;
		        shua();
				page();
	  		})
  		});
  		
  		$("#apply-state").change(function(){
			var state=$("#apply-state").val();
			$(".sea-box .input01").val("");
  			ajaxRequest("/DataShare/earthQuake/analyTableApplyList.action?userId="+userId+"&state="+state+"&siteType=1","get",function(res){
				data = res;
		        shua();
				page();
	  		})
  		});

  		$(".select-items").click(function(){
			var state=$("#apply-state").val();
			$(".sea-box .input01").val("");
  			ajaxRequest("/DataShare/earthQuake/analyTableApplyList.action?userId="+userId+"&state="+state+"&siteType=1","get",function(res){
				data = res;
		        shua();
				page();
	  		})
  		});


  	});
  	
  	
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		str="";

		//var stateArr = ["待审核","审核通过","未通过","申请过期"];
		var colorAll =["#e18cde","#47a447","#ff9873","#7bc0f2"];

	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value){
	    	
	    		var operation="<td><a href='/DataShare/earthQuake/userDownHistoryListToJsp.action?earthQuakeId="+value.earthquake_id+"&applyTitle=应急数据下载查询'>下载查询</a></td>";

				/*申请状态添加不同的颜色*/
				var  strColor = "";
				if( value.type == 0 ){
					strColor = "<td style='color:"+colorAll[0]+"'>"+value.typeStr+"</td>";
				}else  	if( value.type == 3 ){
					strColor = "<td style='color:"+colorAll[1]+"'>"+value.typeStr+"</td>";
				}else  	if( value.type == 4 ){
					strColor = "<td style='color:"+colorAll[2]+"'>"+value.typeStr+"</td>";
				}else  	if( value.type == 5 ){
					strColor = "<td style='color:"+colorAll[3]+"'>"+value.typeStr+"</td>";
				}

				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+value.earthquakeName+"</td>"
					+"	    <td>"+value.user_cname+"</td>"
					+"	    <td>"+value.user_unit+"</td>"
					+"	    <td>"+value.createTimeStr+"</td>"
					+"	    <td>"+value.startTimeStr+"—"+value.endTimeStr+"</td>"
					+       strColor
					+       operation
					+"    </tr>"
				}else{
					str=str+"<tr>"
					+"	    <td>"+value.earthquakeName+"</td>"
					+"	    <td>"+value.user_cname+"</td>"
					+"	    <td>"+value.user_unit+"</td>"
					+"	    <td>"+value.createTimeStr+"</td>"
					+"	    <td>"+value.startTimeStr+"—"+value.endTimeStr+"</td>"
					+       strColor
					+       operation
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

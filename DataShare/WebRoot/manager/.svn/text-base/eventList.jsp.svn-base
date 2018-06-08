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
	<script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
</head>
<body>
  <div id="iframeheight" style="height: 459px;">
  		<div class="pbox tspbox">
  			<div class="ptit">地震应急事件列表</div>
  			<br>
  			<div class="sea-box"  style="width: 100%;margin-left:130px;">
			    <input type="text" value=""  placeholder="请输入要搜索的文字" class="input01" style="width:375px;margin-left:30px;">
	            <input type="button" value="搜索" id="search"   class="seabtn" style="margin-right:10px;">
	            <input type="button" value="添加" id="upButton" class="seabtn">
  			</div>
  			
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
                      <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);" class="optionpages">
                      </select>
                  </span>
              </div>
          </div>
      </div>
     </div>

  <script>		
    $.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  		var str="";
  		ajaxRequest("/DataShare/showData/contingencyEventList.action","get",function(res){
			data = res;
			shua();
			page();
  		})
  		$("#search").click(function(){
  			var item=$(".sea-box .input01").val();
  			ajaxRequest("/DataShare/showData/contingencyEventList.action?keyWords="+item,"get",function(res){
				data = res;
	            shua();
				page();
	  		})
  		})
  		
  		//添加事件
		$("#upButton").click(function(){
			window.location.href="/DataShare/manager/addEarthQuakeToJsp.action";	
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
					+"	    <td><a href='/DataShare/showData/showEventMessageToJsp.action?earthQuakeId="+value.id+"'>事件查看</a></td>"
					+"    </tr>"
				}
				else{ 
					str=str+"<tr>"
					+"	    <td>"+value.year+"</td>"
					+"	    <td>"+value.name+"</td>"
					+"	    <td>"+value.siteLat+"</td>"
					+"	    <td>"+value.siteLon+"</td>"
					+"	    <td>"+value.grade+"</td>"
					+"	    <td>"+value.strTime+"</td>"
					+"	    <td><a href='/DataShare/showData/showEventMessageToJsp.action?earthQuakeId="+value.id+"'>事件查看</a></td>"
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

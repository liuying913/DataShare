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
  <input id="applyState" type="hidden" name="applyState" value="${applyState}"/>
  <div id="iframeheight" style="height: 459px;">
  		<div class="pbox tspbox">
			<div class="ptit">${applyTitle}</div>
			<br>
  			<div class="sea-box">
  				<input type="text" value="" placeholder="请输入要搜索的文字" class="input01">
  				<input type="button" value="搜索" class="seabtn">
  			</div>
			
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th width="10%">共享站编号</th>
                <th width="10%">共享站名称</th>
                <th width="12%">所属地区</th>
                <th width="17%">承建部委</th>
                <th width="17%" class="ts">基本操作</th>
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
  <script>		$.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  		var str="";
  		ajaxRequest("/DataShare/getSiteInfoList.action?siteType=3","get",function(res){
			data = res;
			shua();
			page();
  		})
  		$(".seabtn").click(function(){
  			var item=$(".sea-box .input01").val();
  			ajaxRequest("/DataShare/getSiteInfoList.action?siteType=3&userParam="+item,"get",function(res){
				data = res;
	            shua();
				page();
	  		});
  		})
  	});
  	
  	
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+value.siteNumber+"</td>"
					+"	    <td>"+value.siteName+"</td>"
					+"	    <td>"+value.zoneName+"</td>"
					+"	    <td>"+value.departmentName+"</td>"
					+"	    <td><a href='/DataShare/showBaseSiteInfoToJsp.action?siteType=3&siteNumber="+value.siteNumber+"'>查看</a>&nbsp;<a href='/DataShare/updateBaseSiteInfoToJsp.action?siteType=3&siteNumber="+value.siteNumber+"'>编辑</a></td>"
					+"    </tr>"
				}
				else{
					str=str+"<tr>"
					+"	    <td>"+value.siteNumber+"</td>"
					+"	    <td>"+value.siteName+"</td>"
					+"	    <td>"+value.zoneName+"</td>"
					+"	    <td>"+value.departmentName+"</td>"
					+"	    <td><a href='/DataShare/showBaseSiteInfoToJsp.action?siteType=3&siteNumber="+value.siteNumber+"'>查看</a>&nbsp;<a href='/DataShare/updateBaseSiteInfoToJsp.action?siteType=3&siteNumber="+value.siteNumber+"'>编辑</a></td>"
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

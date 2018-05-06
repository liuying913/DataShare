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
			
			<div class="sea-box"  style="width: 100%;margin-left:130px;">
  				<input type="text" value=""  placeholder="请输入要搜索的文字" class="input01" style="width:375px;margin-left:30px;">
  				<input type="button" value="搜索" id="search" class="seabtn" style="margin-right:10px;">
  				<a href='/DataShare/main/foreGround/newsDetailInfo.jsp?operType=insert&tag=1' target='_blank'><input type="button" value="添加" id="addButton" class="seabtn"></a>
			</div>

            <table border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th style="white-space: nowrap;">新闻标题</th>
                <th style="white-space: nowrap;">发布时间</th>
                <th style="white-space: nowrap;">操作</th>
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
  <script>		
    $.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  		ajaxRequest("/DataShare/manager/getNewsInfoList.action?type=1","get",function(res){
			data = res;
			shua();
			page();
  		})
  		$("#search").click(function(){
  			var selectParam=$(".sea-box .input01").val();
  			ajaxRequest("/DataShare/manager/getNewsInfoList.action?type=1&selectParam="+selectParam,"get",function(res){
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
	    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
	    	
	    		var title = value.title;
	    		if(title.length>=28){title = title.substring(0,28);}
	    		
				var content = value.content;
				if(content.length>=12){content = content.substring(0,12);}
				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+title+"</td>"
					//+"	    <td>"+content+"</td>"
					+"	    <td>"+value.time+"</td>"
					//+"	    <td>"+value.orders+"</td>"
					+"		<td><a href='/DataShare/news/newsDetailToJsp.action?id="+value.id+"' target='_blank'>预览</a>&nbsp;"
					+"	    <a href='/DataShare/news/bfmodifyNews.action?id="+value.id+"' target='_blank'>编辑</a>&nbsp;"
					+"      <a href='/DataShare/manager/deleteNewsToJsp.action?id="+value.id+"'>删除</a></td>"
					+"    </tr>"
				}else{
					str=str+"<tr>"
					+"	    <td>"+title+"</td>"
					//+"	    <td>"+content+"</td>"
					+"	    <td>"+value.time+"</td>"
					//+"	    <td>"+value.orders+"</td>"
					+"		<td><a href='/DataShare/news/newsDetailToJsp.action?id="+value.id+"' target='_blank'>预览</a>&nbsp;"
					+"	    <a href='/DataShare/news/bfmodifyNews.action?id="+value.id+"' target='_blank'>编辑</a>&nbsp;"
					+"		<a href='/DataShare/manager/deleteNewsToJsp.action?id="+value.id+"'>删除</a></td>"
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

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
    <script src="/DataShare/js/analysis/personOption.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
</head>
<body>
  <input id="applyState" type="hidden" name="applyState" value="成果管理"/>
  <div id="iframeheight"  style="height: 459px;">
  		<div class="pbox tspbox">
			<div class="ptit">${applyTitle}</div>
			<br>
			<div class="sea-box"  style="width: 100%;margin-left:130px;">
  				<input type="text" value=""  placeholder="请输入要搜索的文字" class="input01" style="width:375px;margin-left:30px;">
  				<input type="button" value="搜索" id="search" class="seabtn" style="margin-right:10px;">
  				<input type="button" value="添加" id="upButton" class="seabtn">
			</div>
  			
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th style="white-space: nowrap;">软件名称</th>
                <th style="white-space: nowrap;">上传日期</th>
                <th style="white-space: nowrap;">排序</th>
                <th style="white-space: nowrap;">文档描述</th>
                <th class="ts"  style="white-space: nowrap;">基本操作</th>
              </tr>
              <tbody id="tbody"></tbody>
            </table>
        </div>
        <!--分页-->
        <div class="newstable-content">
         <div class="newstable-fy">
             <div class="newstable-fy-pages">
                 <span>共<font id="zongyeshu"></font>页&nbsp;&nbsp;<font id="zongshuju"></font>条记录</span>
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
  	function_UserList();//设置 用户列表下拉菜单
  	$(document).ready(function(){
  		var str="";
  		ajaxRequest("/DataShare/doc/queryDocSoftList.action","get",function(res){
			data = res;
			shua();
			page();
  		});
  		
  	    function shua(){
			if(flag!=0){page();}else{flag=1;}
			str="";
		    $.each(data, function(key, value) {
		    	if( key>=(shu-1)*num_value && key< shu*num_value){
		    		if(key%2!=0){
		    				str=str+"<tr class='even'>"
							+"	    <td>"+value.docName+"</td>"
							+"	    <td>"+value.docStrDate+"</td>"
							+"	    <td>"+value.docOrder+"</td>"
							+"	    <td>"+value.docDesc+"</td>"
							+"	    <td><a href='/DataShare/downDoc.action?docId="+value.docId+"'>下载</a>&nbsp;<a href='/DataShare/doc/deleteSoft.action?docId="+value.docId+"'>删除</a></td>"
							+"    </tr>"
		    			}else{
		    				str=str+"<tr>"
							+"	    <td>"+value.docName+"</td>"
							+"	    <td>"+value.docStrDate+"</td>"
							+"	    <td>"+value.docOrder+"</td>"
							+"	    <td>"+value.docDesc+"</td>"
							+"	    <td><a href='/DataShare/downDoc.action?docId="+value.docId+"'>下载</a>&nbsp;<a href='/DataShare/doc/deleteSoft.action?docId="+value.docId+"'>删除</a></td>"
							+"    </tr>"
		    			}
			  	}
			});
			$("#tbody").html(str); 
			$('#iframe', parent.document).css("height",$("#iframeheight").height());
		}
	
		//搜索按钮
  		$("#search").click(function(){
  			var item=$(".sea-box .input01").val();
  			ajaxRequest("/DataShare/doc/queryDocSoftList.action?userId=-1&keyWords="+item,"get",function(res){
				data = res;
	            shua();
				page();
	  		})
  		});
		
		//上传按钮
		$("#upButton").click(function(){
			window.location.href="/DataShare/doc/upSoftToJsp.action";	
		})
  	})
  </script>
</body>
</html>

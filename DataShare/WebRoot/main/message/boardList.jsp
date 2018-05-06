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
  <div id="iframeheight"  style="height: 459px;">
  		<div class="pbox tspbox">
			<div class="ptit">留言管理</div>
			<br>
  			<div class="sea-box">
  				<table>
  					<tr>
  						<td><input type="text" value="" placeholder="请输入要搜索的文字" class="input01"></td>
  						<td><input type="button" value="搜索" id="search" class="seabtn" style="float:right;"></td>
  					</tr>
  				</table>
  			</div>
           <div style="min-height: 160px">
			   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
				   <tr>
					   <th style="white-space: nowrap;">标题</th>
					   <th style="white-space: nowrap;">用户</th>
					   <th style="white-space: nowrap;">联系人</th>
					   <th style="white-space: nowrap;">联系电话</th>
					   <th style="white-space: nowrap;">访问量</th>
					   <th style="white-space: nowrap;">评论数</th>
					   <th style="white-space: nowrap;">发表日期</th>
					   <th class="ts" style="white-space: nowrap;">操作</th>
				   </tr>
				   <tbody id="tbody">
				   </tbody>
			   </table>
		   </div>
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


  <script>		
    $.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  		var str="";
  		ajaxRequest("/DataShare/message/board.action","get",function(res){
			data = res;
			shua();
			page();
  		})
  		
  		$("#search").click(function(){
  			var param=$(".sea-box .input01").val();
  			param = encodeURI(param);
  			ajaxRequest("/DataShare/message/board.action?param="+param,"get",function(res){
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
	    	    var createTime = value.createTimeStr.substring(0,11);
	    	    var titles = value.title.substring(0,10);
    			if(key%2!=0){
    				str=str+"<tr class='even'>"
    				+"	    <td>"+titles+"</td>"
    				+"	    <td>"+value.user_name+"</td>"
    				+"	    <td>"+value.contact_people+"</td>"
    				+"	    <td>"+value.contact_mobile+"</td>"
    				+"	    <td>"+value.browse_number+"</td>"
    				+"	    <td>"+value.comment_number+"</td>"
    				+"	    <td>"+createTime+"</td>"
					+"	    <td><a  href='/DataShare/message/commentToJsp.action?board_id="+value.id+"' target='_blank'>查看</a>&nbsp;<a href='/DataShare/manager/deleteBoard.action?id="+value.id+"'>删除</a></td>"
    				+"    </tr>"
    			}else{
    				str=str+"<tr>"
    				+"	    <td>"+titles+"</td>"
    				+"	    <td>"+value.user_name+"</td>"
    				+"	    <td>"+value.contact_people+"</td>"
    				+"	    <td>"+value.contact_mobile+"</td>"
    				+"	    <td>"+value.browse_number+"</td>"
    				+"	    <td>"+value.comment_number+"</td>"
    				+"	    <td>"+createTime+"</td>"
					+"	    <td><a  href='/DataShare/message/commentToJsp.action?board_id="+value.id+"' target='_blank'>查看</a>&nbsp;<a href='/DataShare/manager/deleteBoard.action?id="+value.id+"'>删除</a></td>"
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

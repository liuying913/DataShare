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
			<div class="ptit">用户列表</div>
			<br>
			
			<div class="sea-box"  style="width: 100%;margin-left:130px;">
  				<input type="text" value=""  placeholder="请输入要搜索的文字" class="input01" style="width:375px;margin-left:30px;">
  				<input type="button" value="搜索" id="search" class="seabtn" style="margin-right:10px;">
  				<input type="button" value="添加" id="upButton" class="seabtn">
			</div>
		
		   <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
			   <tr>
				   <th style="white-space: nowrap;">用户名</th>
				   <th style="white-space: nowrap;">中文名称</th>
				   <th style="white-space: nowrap;">性别</th>
				   <th style="white-space: nowrap;">所属单位</th>
				   <th style="white-space: nowrap;">联系电话</th>
				   <th style="white-space: nowrap;">邮箱</th>
				   <th style="white-space: nowrap;">注册日期</th>
				   <th class="ts" style="white-space: nowrap;">操作</th>
			   </tr>
			   <tbody id="tbody"></tbody>
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

  <script>
    var userType="";
    $.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  	    userType="${sessionScope.userInfo.userType}";
  		var str="";
  		ajaxRequest('/DataShare/queryAllUserInfoList.action','get',function(res){
			data = res;
			shua();
			page();
  		})
  		$("#search").click(function(){
  			var item=$(".sea-box .input01").val();
  			ajaxRequest('/DataShare/queryAllUserInfoList.action?userParam='+item,'get',function(res){
				data = res;
	            shua();
				page();
	  		})
  		})
  		
  		//添加用户界面
		$("#upButton").click(function(){
			window.location.href="/DataShare/addUserToJsp.action";	
		})
  	});
  	
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value){
    			if(key%2!=0){
    				str=str+"<tr class='even'>"
    				+"	    <td>"+value.userName+"</td>"
    				+"	    <td>"+value.userCName+"</td>"
    				+"	    <td>"+value.userGender+"</td>"
    				+"	    <td>"+value.userUnit+"</td>"
    				+"	    <td>"+value.userPhone+"</td>"
    				+"	    <td>"+value.userEmail+"</td>"
    				+"	    <td>"+value.regDateStr+"</td>"
    				+"	    <td><a href='/DataShare/manager/managerUserInfoToJsp.action?userId="+value.userId+"'>编辑</a>&nbsp;<a  href='/DataShare/deleteUserInfo.action?manager=true&userId="+value.userId+"'>删除</a></td>"
/*    				+"	    <td><a href='/DataShare/querUserInfoByIdToJsp.action?userId="+value.userId+"'>查看</a>&nbsp;<a href='/DataShare/updateUserInfoToJsp.action?userId="+value.userId+"'>编辑</a>&nbsp;<a href='/DataShare/updateUserHeadToJsp.action?userId="+value.userId+"'>修改头像</a>&nbsp;<a href='/DataShare/updatePasswordToJsp.action?userId="+value.userId+"'>修改密码</a>&nbsp;<a href='/DataShare/deleteUserInfo.action?userId="+value.userId+"'>删除</a></td>"*/
    				+"    </tr>"
    			}else{
    				str=str+"<tr>"
    				+"	    <td>"+value.userName+"</td>"
    				+"	    <td>"+value.userCName+"</td>"
    				+"	    <td>"+value.userGender+"</td>"
    				+"	    <td>"+value.userUnit+"</td>"
    				+"	    <td>"+value.userPhone+"</td>"
    				+"	    <td>"+value.userEmail+"</td>"
    				+"	    <td>"+value.regDateStr+"</td>"
					+"	    <td><a href='/DataShare/manager/managerUserInfoToJsp.action?userId="+value.userId+"'>编辑</a>&nbsp;<a  href='/DataShare/deleteUserInfo.action?manager=true&userId="+value.userId+"'>删除</a></td>"
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

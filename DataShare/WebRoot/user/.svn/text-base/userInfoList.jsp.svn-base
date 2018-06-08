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
  <div id="iframeheight">
  		<div class="pbox tspbox">
			<div class="ptit">用户列表(取消掉的功能)</div>
			
			</br>
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
					   <th width="10%">用户名</th>
					   <th width="10%">中文名称</th>
					   <th width="6%">性别</th>
					   <th width="17%">所属单位</th>
					   <th width="12%">联系电话</th>
					   <th width="16%">邮箱</th>
					   <th width="14%">注册日期</th>
					   <th width="8%" class="ts">操作</th>
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
  <script>$.ajaxSetup({ cache: false });//全局禁用缓存
    var userType="";
  	$(document).ready(function(){
  	    userType="${sessionScope.userInfo.userType}";
  		var str="";
  		ajaxRequest("/DataShare/queryAllUserInfoList.action","get",function(res){
			data = res;
			shua();
			page();
  		})
  		$("#search").click(function(){
  			var item=$(".sea-box .input01").val();
  			ajaxRequest("/DataShare/queryAllUserInfoList.action?userParam="+item,"get",function(res){
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
	    				+"	    <td><a href='/DataShare/querUserInfoByIdToJsp.action?userId="+value.userId+"'>编辑</a></td>"
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
						+"	    <td><a href='/DataShare/querUserInfoByIdToJsp.action?userId="+value.userId+"'>编辑</a></td>"
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

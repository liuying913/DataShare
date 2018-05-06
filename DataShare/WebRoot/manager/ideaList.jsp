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
	<script type="text/javascript" src="/DataShare/js/manager/ideaList.js"></script>
</head>
<body>
  <div id="iframeheight">
  		<div class="pbox tspbox">
  			<div class="ptit">${applyTitle}</div>
  			<div class="sea-box">
  				<input type="text" value="" placeholder="请输入要搜索的文字" class="input01">
  				<input type="button" value="搜索" class="seabtn">
  			</div>
			</br>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th width="10%">联系人</th>
                <th width="10%">联系方式</th>
                <th width="12%">意见内容</th>
                <th width="17%">提交时间</th>
                <th width="17%" class="ts">基本操作</th>
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
  		var str="";
  		ajaxRequest("/DataShare/idea/ideaList.action","get",function(res){
			data = res;
			shua();
			page();
  		})
  		$(".seabtn").click(function(){
  			var item=$(".sea-box .input01").val();
  			if(item==""){
  				alert("输入内容");
  			}else{
	  			ajaxRequest("/DataShare/getSiteInfoList.action?param="+item,"get",function(res){
					//res[0].applyUser.response_person
					
		  			//if(res[0].code==1){
		  				//data = res.date[0].result.sqlist;
						data = res;
			            shua();
						page();
						
					//}else{
					//	alert(res.date[0].msg)
					//}
		  		})
	  		}
  		})
  	})
  </script>
</body>
</html>

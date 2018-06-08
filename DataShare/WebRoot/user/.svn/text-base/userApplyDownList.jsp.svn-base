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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/drop-down.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
	<link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
	
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <script src="/DataShare/js/analysis/personOption.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
	<script type="text/javascript" src="/DataShare/js/select/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/select/select-widget-min.js"></script>
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
  <input id="applyState" type="hidden" name="applyState" value="${applyState}"/>
  <div id="iframeheight" style="height: 459px;">
  		<div class="pbox tspbox">
	        <div class="ptit">${applyTitle}</div>
			<br>
  			<div class="sea-box"  style="width: 100%;margin-left:82px;">
                <div class="select-container">
                	 <select id="userId" class="userId" name="userId" style="visibility: hidden;"></select>
                </div>
                <input type="text" value=""  placeholder="请输入要搜索的文字" class="input01" style="width:375px;margin-left:30px;">
  				<input type="button" value="搜索" id="search" class="seabtn" style="margin-right:10px;">
  			</div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
               	<th style="white-space: nowrap;">申请时间</th>
               	<th style="white-space: nowrap;">下载有效期</th>
                <th style="white-space: nowrap;">申请人</th>
                <th style="white-space: nowrap;">申请单位</th>
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
                  <span class="everypages"></span>
                  <a href="#" onclick="next();">下一页</a>
                  <a href="#" onclick="yeshu('end')">末页</a>
                  <span>跳转至
                      <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);" class="optionpages"></select>
                  </span>
              </div>
          </div>
      </div>
     </div>
  
  <script>		
  	$.ajaxSetup({ cache: false });//全局禁用缓存
    function_UserList();//设置 用户列表下拉菜单
  	$(document).ready(function(){
		var applyState = $("#applyState").val();
		var url="/DataShare/queryApplyUserList.action?applyTitle=下载记录&applyState="+applyState;
  		ajaxRequest(url,"get",function(res){
			data = res;
			shua();
			page();
			
	  	    //选择以后触发事件  用户列表 点击发生变化
            $(".select-items").click(function(){
            	var userId='';

            	$("#userId").find("option").each(function(){
            		if( $(this).val() == $("#userId").val() ){
                       userId=$(this).val();                
            		}
            	});
                var item=$(".sea-box .input01").val();
                //var userId=$("#userId").val();
                $(".sea-box .input01").val("");
                ajaxRequest("/DataShare/queryApplyUserList.action?userId="+userId+"&applyTitle=下载记录&applyState="+applyState,"get",function(res){
                   data = res;
                   shua();
                   page();
               })
            });
  		})
  		$(".seabtn").click(function(){
  			var item=$(".sea-box .input01").val();
  			var userId=$("#userId").val();
  			var url="/DataShare/queryApplyUserList.action?userId="+userId+"&applyTitle=下载记录&applyState="+applyState+"&selectParams="+item;
  			ajaxRequest(url,"get",function(res){
				data = res;
				shua();
				page();
	  		})
  		});
  		
  		//用户列表 点击发生变化
  		$("#userId").change(function(){
			var userId=$("#userId").val();
			$(".sea-box .input01").val("");
  			ajaxRequest("/DataShare/queryApplyUserList.action?userId="+userId+"&applyTitle=下载记录&applyState="+applyState,"get",function(res){
				data = res;
		        shua();
				page();
	  		})
  		});
  	});
  	
	function shua(){
		if(flag!=0){page();}else{flag=1;}
		
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+value.applyTime1+"</td>"
					+"	    <td style='color:#47a447'>"+value.applyTime5+"-"+value.applyTime7+"</td>"
					+"	    <td>"+value.apply_Person+"</td>"
					+"	    <td>"+value.apply_Unit+"</td>"
					//+"	    <td>"+value.apply_Phone+"</td>"
					//+"	    <td>"+value.result_Name+"</td>"
					//+"	    <td>"+value.result_Person+"</td>"
					//+"	    <td>"+value.result_Money+"</td>"
					//+"	    <td>"+value.result_from+"</td>"
					+"	    <td><a href='/DataShare/userDownHistoryListToJsp.action?applyTitle=下载记录&applyId="+value.id+"&liuCheng="+value.liuCheng+"'>查看记录</a></td>"
					+"    </tr>"
				}
				else{ 
					str=str+"<tr>"
					+"	    <td>"+value.applyTime1+"</td>"
					+"	    <td style='color:#47a447'>"+value.applyTime5+"-"+value.applyTime7+"</td>"
					+"	    <td>"+value.apply_Person+"</td>"
					+"	    <td>"+value.apply_Unit+"</td>"
					//+"	    <td>"+value.apply_Phone+"</td>"
					//+"	    <td>"+value.result_Name+"</td>"
					//+"	    <td>"+value.result_Person+"</td>"
					//+"	    <td>"+value.result_Money+"</td>"
					//+"	    <td>"+value.result_from+"</td>"
					+"	    <td><a href='/DataShare/userDownHistoryListToJsp.action?applyTitle=下载记录&applyId="+value.id+"&liuCheng="+value.liuCheng+"'>查看记录</a></td>"
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

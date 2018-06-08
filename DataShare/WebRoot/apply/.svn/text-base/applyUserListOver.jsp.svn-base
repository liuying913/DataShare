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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/drop-down.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
	 <script type="text/javascript" src="/DataShare/js/select/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/select/select-widget-min.js"></script>
</head> 
<body>
  <input id="applyState" type="hidden" name="applyState" value="${applyState}"/>
  <input id="isManager" type="hidden" name="isManager" value="${isManager}"/>
  <div id="iframeheight" style="height: 459px;">

  		<div class="pbox tspbox">
			<div class="ptit">${applyTitle}</div>
			<br>
			<div class="sea-box"  style="width: 100%;margin-left:82px;">
				<!-- <span class="myTip">审核状态</span>	 -->	
			    <div class="select-container">
			     	<select name="apply-state" id="apply-state">
						<option value="all">全部</option>
						<option value="ftp">审核通过</option>
						<option value="4">审核未通过</option>
						<option value="7">申请过期</option>
					</select>
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
                <th style="white-space: nowrap;">申请人电话</th>
                <!--<th style="white-space: nowrap;">课题名称</th>
                <th style="white-space: nowrap;">课题编号</th>-->
                <th class="ts" style="white-space: nowrap;">记录查看</th>
                <th style="white-space: nowrap;">状态</th>
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
	                        <!--<a href="#">1</a>
	                        <a href="#">2</a>-->
	                    </span>
	                    <a href="#" onclick="next();">下一页</a>
	                    <a href="#" onclick="yeshu('end')">末页</a>
	                    <span>跳转至
	                        <select name="select" onchange="yeshu(this.options[this.options.selectedIndex].value);" class="optionpages">
	                            <!--<option value="1" selected="selected">1</option>
	                            <option value="2">2</option>-->
	                        </select>
	                    </span>
	                </div>
	            </div>
	        </div>
        </div>
  <script>
    $.ajaxSetup({ cache: false });//全局禁用缓存
    var isManager = $("#isManager").val();
  	$(document).ready(function(){
		var applyState = $("#applyState").val();
		var url="/DataShare/queryApplyUserList.action?isManager="+isManager+"&applyTitle=下载记录&applyState="+applyState;
  		ajaxRequest(url,"get",function(res){
			data = res;
			shua();
			page();
  		})
  		$(".seabtn").click(function(){
  			var item=$(".sea-box .input01").val();
  			var url="/DataShare/queryApplyUserList.action?isManager="+isManager+"&applyTitle=下载记录&applyState="+applyState+"&selectParams="+item;
  			ajaxRequest(url,"get",function(res){
				data = res;
				shua();
				page();
	  		});
  		});
  		
  		/*选择状态后自动查询*/
  		$("#apply-state").change(function(){
  			var state = $(this).val();
  			$(".sea-box .input01").val("");
  			ajaxRequest("/DataShare/queryApplyUserList.action?isManager="+isManager+"&applyTitle=下载记录&applyState="+state,"get",function(res){
				data = res;
		        shua();
				page();
	  		})
  		});
  		/*修改select的默认方式*/
  		$("#apply-state").selectWidget({
			change  : function (changes) {
				return changes;
			},
			effect       : "slide",
			keyControl   : true,
			speed        : 200,
			scrollHeight : 250
		});
  		
  		//选择以后触发事件  用户列表 点击发生变化
        $(".select-items").click(function(){
            var state = $("#apply-state").val();
  			$(".sea-box .input01").val("");
  			ajaxRequest("/DataShare/queryApplyUserList.action?isManager="+isManager+"&applyTitle=下载记录&applyState="+state,"get",function(res){
				data = res;
		        shua();
				page();
	  		});
	  	});
  	});
  	
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		
		//var stateArr = ["待审核","审核通过","未通过","申请过期"];
		var colorAll =["#e18cde","#47a447","#ff9873","#7bc0f2"];
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
	    	
	    		/*申请状态添加不同的颜色*/
				var  strColor = "";
				if( value.liuCheng == 4 ){
					strColor = "<td style='color:"+colorAll[0]+"'>"+value.liuChengName+"</td>";
				}else  	if( value.liuCheng == 5 ){
					strColor = "<td style='color:"+colorAll[1]+"'>"+value.liuChengName+"</td>";
				}else  	if( value.liuCheng == 6 ){
					strColor = "<td style='color:"+colorAll[2]+"'>"+value.liuChengName+"</td>";
				}else  	if( value.liuCheng == 7 ){
					strColor = "<td style='color:"+colorAll[3]+"'>"+value.liuChengName+"</td>";
				}
				
				
				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+value.applyTime1+"</td>"
					+"	    <td>"+value.applyTime5+"-"+value.applyTime7+"</td>"
					+"	    <td>"+value.apply_Person+"</td>"
					+"	    <td>"+value.apply_Unit+"</td>"
					+"	    <td>"+value.apply_Phone+"</td>"
					//+"	    <td>"+value.result_Name+"</td>"
					//+"	    <td>"+value.result_Person+"</td>"
					+"	    <td><a href='/DataShare/applyListToPage1ToJsp.action?applyId="+value.id+"&liuCheng="+value.liuCheng+"'>查看</a></td>"
					+       strColor 
					+"    </tr>"
				}
				else{ 
					str=str+"<tr>"
					+"	    <td>"+value.applyTime1+"</td>"
					+"	    <td>"+value.applyTime5+"-"+value.applyTime7+"</td>"
					+"	    <td>"+value.apply_Person+"</td>"
					+"	    <td>"+value.apply_Unit+"</td>"
					+"	    <td>"+value.apply_Phone+"</td>"
					//+"	    <td>"+value.result_Name+"</td>"
					//+"	    <td>"+value.result_Person+"</td>"
					+"	    <td><a href='/DataShare/applyListToPage1ToJsp.action?applyId="+value.id+"&liuCheng="+value.liuCheng+"'>查看</a></td>"
					+       strColor
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

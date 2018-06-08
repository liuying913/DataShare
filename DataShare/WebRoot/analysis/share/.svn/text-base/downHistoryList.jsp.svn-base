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
	
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
</head>
<body>
  <input id="applyState" type="hidden" name="applyState" value="${applyState}"/>
  <input type="hidden" id="siteType" name="siteType" value="${siteType}"/>
  <input type="hidden" id="fileType" name="fileType" value="${fileType}"/>
  <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
  <div id="iframeheight">
  		<div class="pbox tspbox">
			<div class="ptit">${applyTitle}</div>
			</br>
  			<div class="sea-box">
  				<input type="text" value="" placeholder="请输入要搜索的文字" class="input01">
  				<input type="button" value="搜索" class="seabtn">
  			</div>

            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th width="12%">文件名称</th>
                <th width="12%">台站编号</th>
                <th width="17%">文件目录</th>
                <th width="10%">申请单位</th>
                <th width="10%">申请人</th>
                <th width="17%">下载时间</th>
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
  <script> $.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  		var str="";
  		ajaxRequest("/DataShare/analysis/s30S/downHistoryList.action?siteType=3&fileType=3","get",function(res){
			data = res;
			shua();
			page();
  		})
  		$(".seabtn").click(function(){
  			var item=$(".sea-box .input01").val();
  			if(item==""){
  				alert("输入内容");
  			}else{
	  			ajaxRequest("/DataShare/analysis/s30S/downHistoryList.action?siteType=3&fileType=3&fileParams="+item,"get",function(res){
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
  	});
  	
  	
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+value.fileName+"</td>"
					+"	    <td>"+value.site_number+"</td>"
					+"	    <td>"+value.fileyear+"/"+value.fileDayyear+"</td>"
					+"	    <td>"+value.apply_unit+"</td>"
					+"	    <td>"+value.apply_person+"</td>"
					+"	    <td>"+value.downTimeStr+"</td>"
					+"	    <td>"+value.fileComp+"</td>"
					+"    </tr>"
				}
				else{
					str=str+"<tr>"
					+"	    <td>"+value.fileName+"</td>"
					+"	    <td>"+value.site_number+"</td>"
					+"	    <td>"+value.fileyear+"/"+value.fileDayyear+"</td>"
					+"	    <td>"+value.apply_unit+"</td>"
					+"	    <td>"+value.apply_person+"</td>"
					+"	    <td>"+value.downTimeStr+"</td>"
					+"	    <td>"+value.fileComp+"</td>"
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

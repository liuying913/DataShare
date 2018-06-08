<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
    
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    
    <link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/page/page5JsonList.js"></script>
	<style>
		.table01{
			width: 95%;margin: 0 auto;
		}
		.warp-button{
			width: 300px;
			margin: 20px auto 0;
			padding-left: 0;
		}
	</style>
</head>
<body>
 <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
 <input id="isManager" type="hidden" name="isManager" value="${isManager}"/>
      <div id="iframeheight" style=" margin-top: 0px;background:#f7f7f7;height: 509px;">
		<div class="web-width" >
			 <div class="for-liucheng">
				  <div class="liulist for-cur"></div>
				  <div class="liulist for-cur"></div>
				  <div class="liulist for-cur"></div>
				  <div class="liulist"></div>
				  <div class="liutextbox">
					  <div class="liutext for-cur"><em>1</em><br /><strong>基本信息填写</strong></div>
					  <div class="liutext for-cur"><em>2</em><br /><strong>申请数据筛选</strong></div>
					  <div class="liutext for-cur"><em>3</em><br /><strong>审批表下载</strong></div>
					  <div class="liutext"><em>4</em><br /><strong>审核结果</strong></div>
				  </div>
			  </div>
		</div>
	    <div style="height:15px;margin: 0 auto;background:white;"></div>
  		<div class="pbox tspbox" style=" margin-top: -15px;">
            <div class="base-info tsbase-info1">
                 <div class="base-info-input">
                    <fieldset>
						<legend style="display:none;">审批表下载</legend>
						<div style="text-align:center">
								<div class="gd-list" style="margin-left:0px">  
									<a href="/DataShare/downApplyWord.action?applyId=${applyId}" target="_blank" id="download" class="dload"><i class="icon-cloud-download"></i> 下载</a>
								</div>
							</div>
						<div class="beizhu" style="color: #999;">温馨提示：下载完成后，请仔细核对申请信息</div>
					</fieldset>
	            </div>
	            <div class="beizhu" style=" float:left;">用户上传的成果</div>
	            <table border="0" cellspacing="0" cellpadding="0" class="table01">
	              <tr>
	                <th style="white-space: nowrap;">用户名称</th>
	                <th style="white-space: nowrap;">文档名称</th>
	                <th style="white-space: nowrap;">文档描述</th>
	                <th style="white-space: nowrap;">上传日期</th>
	                <th style="white-space: nowrap;" style="background: none">操作</th>
	              </tr>
	              <tbody id="tbody">
	              </tbody>
	            </table>
            </div>
        </div>
        
        
        	<!--分页-->
		<div class="newstable-content"  style="margin-bottom: -45px;">
			<div class="newstable-fy">
				<div class="newstable-fy-pages">
					<span>共<font id="zongyeshu"></font>页&nbsp;&nbsp;<font id="zongshuju"></font>条记录</span>
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
			<div class="warp-button" style="margin-bottom: 45px;">
			   <button type="button" id="back" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->上一步</button>
			   <button type="button" id="next" class="shengfen-button"><!--<img src="/DataShare/img/s2.png"/>-->下一步</button>
		    </div>
		</div>
    </div>

    <script>
		$.ajaxSetup({ cache: false });//全局禁用缓存
		$("#download").show();
		$(".btn1").show();
		var applyId = $("#applyId").val();
		var isManager = $("#isManager").val();
		
		//上一步
    	$("#back").click(function(){
			var url="/DataShare/applyOrShowToJsp.action?thisNum=3&backOrDown=back&applyTitle=数据申请&isManager="+isManager+"&applyId="+applyId;
    		window.location.href=url;	
    	})
		
		//下一步
		$("#next").click(function(){
			var url="/DataShare/applyOrShowToJsp.action?thisNum=3&backOrDown=down&applyTitle=数据申请&isManager="+isManager+"&applyId="+applyId;;
    		window.location.href=url;	
    	})

    	$(document).ready(function(){
	  		ajaxRequest("/DataShare/queryDocInfoList.action?applyId="+applyId,"get",function(res){
				data = res;
				shua();
				page();
	  		})
  		})
  		
		function shua(){
			if(flag!=0){page();}else{flag=1;}
			str="";
		    $.each(data, function(key, value) {
		    	
		    	var docName = value.docName;
			    if(docName.length>10){docName=docName.substring(0,10);}
			    var docDesc = value.docDesc;
			    if(docDesc.length>40){docDesc=docDesc.substring(0,40);}
		    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
					if(key%2!=0){
						str=str+"<tr class='even'>"
						+"	    <td>"+value.userName+"</td>"
						+"	    <td>"+docName+"</td>"
						+"	    <td>"+docDesc+"</td>"
						+"	    <td>"+value.docStrDate+"</td>"
						+"	    <td><a href='/DataShare/downDoc.action?docId="+value.docId+"'>下载</a></td>"
						+"    </tr>"
					}
					else{
						str=str+"<tr>"
						+"	    <td>"+value.userName+"</td>"
						+"	    <td>"+docName+"</td>"
						+"	    <td>"+docDesc+"</td>"
						+"	    <td>"+value.docStrDate+"</td>"
						+"	    <td><a href='/DataShare/downDoc.action?docId="+value.docId+"'>下载</a></td>"
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

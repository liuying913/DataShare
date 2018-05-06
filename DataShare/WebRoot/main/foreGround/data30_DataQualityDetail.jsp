<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
    <title>GNSS数据资源共享与信息发布平台</title>
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/style.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css"/>
    
    <link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
</head>
<body>
    <input type="hidden" id="id" name="id" value="${id}"/>
    <!--  ==========  -->
    <!--  ==顶部==  -->
	<%@ include file="../../head.jsp" %>
	
    <div class="banner_son">
		<img src="/DataShare/img/img_zxjj.jpg" />
    </div>
    <!--  ==========  -->
    <!--  ==页面主体==  -->
    <!--  ==========  -->
    <div class="ContactUs-main">
        <div class="ContactUs-header">
            <div class="header-tit">数据动态</div>
            <div class="header-nav"><a href="/DataShare/index.action?id=1">主页></a><a href="#" class="onactive">数据动态</a></div>
        </div>
        <!--左边导航-->
		</br>
       	<h3 class="ntit"></h3>
        <div class="ncon">
        	
        </div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th style="white-space: nowrap;">序号</th>
                <th style="white-space: nowrap;">名称</th>
                <th style="white-space: nowrap;">编号</th>
                <th style="white-space: nowrap;">部委</th>
                <th style="white-space: nowrap;">总天数</th>
                <th style="white-space: nowrap;">有效天数</th>
                <th style="white-space: nowrap;">完整率</th>
                <th style="white-space: nowrap;">MP1(M)</th>
                <th style="white-space: nowrap;">MP2(M)</th>
                <th style="white-space: nowrap;">O/slps</th>
                <th style="white-space: nowrap;">月数据量(MB)</th>
              </tr>
              <tbody id="tbody">
              </tbody>
            </table>
            
    </div>
    <!--  ==========  -->
    <!--  = 底部 =  -->
	<%@ include file="../../foot.jsp" %>
     
    <style>
    	h3.ntit{ color:#2867b2;font-size:18px;font-weight:bold;line-height:44px; font-family:"微软雅黑";text-align:center;}
		.ncon{font-size:15px; color:#666666; line-height:28px; text-align:left;font-family: 微软雅黑;}
		.ncon{width:100%;display:block;} 
    </style>
    <script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
    <script src="/DataShare/js/new_file.js" type="text/javascript" charset="utf-8"></script>
    <script>  		
    	$.ajaxSetup({ cache: false });//全局禁用缓存
		var id = $("#id").val();
    	$.ajax({
		url: "/DataShare/news/data30_DataQualityDetail.action?id="+id,
			type: "get",
			data: "json",
		}).done(function (data) {
			$("h3.ntit").text(data[0].title);
			$(".ncon").html(data[0].content);
			
			str="";
		    $.each(data, function(key, value) {
    			if(key%2!=0){
    				str=str+"<tr class='even'>"
    				+"	    <td>"+(key+1)+"</td>"
    				+"	    <td>"+value.siteName+"</td>"
    				+"	    <td>"+value.siteNumber+"</td>"
    				+"	    <td>"+value.departName+"</td>"
    				+"	    <td>"+value.daySum+"</td>"
    				+"	    <td>"+value.dayValieSum+"</td>"
    				+"	    <td>"+value.integrityRate+"</td>"
    				+"	    <td>"+value.mp1+"</td>"
    				+"	    <td>"+value.mp2+"</td>"
    				+"	    <td>"+value.o_slps+"</td>"
    				+"	    <td>"+value.fileSize+"</td>"
    				+"    </tr>"
    			}else{
    				str=str+"<tr>"
    				+"	    <td>"+(key+1)+"</td>"
    				+"	    <td>"+value.siteName+"</td>"
    				+"	    <td>"+value.siteNumber+"</td>"
    				+"	    <td>"+value.departName+"</td>"
    				+"	    <td>"+value.daySum+"</td>"
    				+"	    <td>"+value.dayValieSum+"</td>"
    				+"	    <td>"+value.integrityRate+"</td>"
    				+"	    <td>"+value.mp1+"</td>"
    				+"	    <td>"+value.mp2+"</td>"
    				+"	    <td>"+value.o_slps+"</td>"
    				+"	    <td>"+value.fileSize+"</td>"
    				+"    </tr>"
    			}
			});
			$("#tbody").html(str); 
		})
    </script>
</body>
</html>

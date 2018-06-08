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
	<link rel="stylesheet" type="text/css" href="/DataShare/css/style.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
</head>
<body>
  <input type="hidden" id="id" name="id" value="${id}"/>
  
    <div id="iframeheight">
  		<div class="pbox tspbox">
        	<div class="ptit">合作交流信息查看</div>
        	
            <div class="base-info tsbase-info1">
                <div class="base-info-input">
                	<fieldset>
                    <ul>
                    	<li>
                        	<span>联系人</span>
                            <label><input name="personName" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>联系方式</span>
                            <label><input name="telPhone" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>提交时间</span>
                            <label><input name="timeStr" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>建议内容</span>
                            <label><input name="content" type="text" value="" class="input01 require" ></label>
                        </li>
                      
                    </ul> 
               	 	</fieldset>
                </div>
 				<div class="fun-box">
                	<a href="javascript:void(0)" class="btn1">返回</a>
                    <div class="error">请输入密码</div>
                </div>
                
            </div>
        </div>
        </div>
    <script>
    	$.ajaxSetup({ cache: false });//全局禁用缓存
		var id = $("#id").val();
    	$(document).ready(function(){
    		var url=window.location.href;
			$(".base-info").addClass("base-info-back");
			$(".input01").attr("disabled","disabled");
			ajaxRequest("/DataShare/idea/ideaList.action?id="+id,"get",function(res){
				$(".input01").eq(0).val(res[0].personName);
				$(".input01").eq(1).val(res[0].telPhone);
				$(".input01").eq(2).val(res[0].timeStr);
				$(".input01").eq(3).val(res[0].content);
			})
			
			//返回
			$(".fun-box a").click(function(){
				window.location.href="/DataShare/idea/ideaListToJsp.action?applyTitle=合作交流管理";	
			})
    	})
    	
    </script>
</body>
</html>

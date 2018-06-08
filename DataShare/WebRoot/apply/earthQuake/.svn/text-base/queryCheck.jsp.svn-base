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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
</head>
<body>
    <input type="hidden" id="id" name="id" value="${id}"/>
    <div id="iframeheight">
         <div class="pbox tspbox" style="padding-bottom:0px;">
           <div class="ptit">${applyTitle}</div>
  	    </div>
  		<div class="pbox">
            <div class="base-info baseInfo ">
                <div class="base-info-input">
                    <ul class="clearfix">
                    	<li>
                        	<span>申请人</span>
                            <label><input name="userName" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>所属单位</span>
                            <label><input name="userCName" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>申请时间</span>
                            <label><input name="userGender" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>申请状态</span>
                            <label><input name="userUnit" type="text" value="" class="input01 require" ></label>
                        </li>
                        <li>
                        	<span>下载起始时间</span>
                            <label><input name="userPhone" type="text" value="" class="input01 require" ></label>
                        </li>
						<li>
                        	<span>下载结束时间</span>
                            <label><input name="userEmail" type="text" value="" class="input01 require" ></label>
                        </li>
						
						 <li>
                        	<span>备注</span>
                            <label><input name="regDateStr" type="text" value="" class="input01 require"></label>
                        </li>
                    </ul>
                </div>
            </div>
            <div>
              <button type="button" id="back" class="shengfen-button" style="margin:25px auto 0;float:none;"><!--<img src="/DataShare/img/s2.png"/>-->返回</button>
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
			ajaxRequest("/DataShare/earthQuake/queryCheck.action?noCheckFlag=false&id="+id,"get",function(res){
				$(".input01").eq(0).val(res[0].user_cname);
				$(".input01").eq(1).val(res[0].user_unit);
				$(".input01").eq(2).val(res[0].createTimeStr);
				$(".input01").eq(3).val(res[0].typeStr);
				$(".input01").eq(4).val(res[0].startTimeStr);
				$(".input01").eq(5).val(res[0].endTimeStr);
				$(".input01").eq(6).val(res[0].remark);
			})
			
			//点击返回
			$("#back").click(function(){
				window.history.back(-1); 
			});
    	})
    </script>
</body>
</html>

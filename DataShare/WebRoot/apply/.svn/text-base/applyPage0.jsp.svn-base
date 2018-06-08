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
	<link rel="stylesheet" type="text/css" href="css/style.css" />
	
	<link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
	<script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<style>
		.tspbox .warp-button   input.selected{background:#ccc;}
		.liulist{float:left;width:20%; height:7px; background:#ccc;}
		.liutextbox .liutext{float:left;width:20%; text-align:center;}
	</style>
</head>
<body>
  <input type="hidden" id="applyTitle" name="applyTitle" value="${applyTitle}"/>
  <input id="isManager" type="hidden" name="isManager" value="${isManager}"/>
  <div id="iframeheight" style=" margin-top: 0px;background:#f7f7f7;">
  		<div class="pbox tspbox">
            <div class="base-info tsbase-info1">
            	<div class="pbox tspbox">
                <div class="base-info-input">
                	<div class="pbox tspbox">
			            <div class="base-info tsbase-info1">
			                <div class="base-info-input" style="width: 90%; margin: -10px auto 0px; padding: 20px 20px 0;  ">
						    	<div style="text-align:center; width:100%; height:15px;font-size:16px; color:#306ab5; margin-top:15px; line-height:0px; font-weight:bold;">
									数据共享使用规定
								</div>
						    		<div class="CenterP-right-content" style="font-size:14px;font-family:微软雅黑;">
			                    	&nbsp;&nbsp;&nbsp;1.&nbsp;原则上每次共享数据的时间氛围最长为一年，申请单位一年内仅限申请一次；<br/></div>
			                    	
			                    	<div class="CenterP-right-content" style="font-size:14px;font-family:微软雅黑;">
			                        &nbsp;&nbsp;&nbsp;2.&nbsp;共享数据仅限于在申请单位内部使用，不得向任何第三方提供，否则后果自负；<br/></div>
			                        
			                        <div class="CenterP-right-content" style="font-size:14px;font-family:微软雅黑;">
			                        &nbsp;&nbsp;&nbsp;3.&nbsp;凡使用陆态共享数据所产生的论文、论著等成果必须明确标注数据来源于“中国大陆构造环境监测网络”，并及时通报给地壳运动监测工程中心备案；<br/></div>
			                        
			                        <div class="CenterP-right-content" style="font-size:14px;font-family:微软雅黑;">
									&nbsp;&nbsp;&nbsp;4.&nbsp;凡使用其他项目共享数据所产生的论文、论著等成果必须明确标注数据来源于“中国大陆构造环境监测网络”，并及时通报给地壳运动监测工程中心备案；<br/></div>
			                        
			                        <div class="CenterP-right-content" style="font-size:14px;font-family:微软雅黑;">
			                        &nbsp;&nbsp;&nbsp;5.&nbsp;凡属于二次以上申请数据的单位，须提交上次使用数据的研究成果和论著；<br/></div>
			                        
			                        <div class="CenterP-right-content" style="font-size:14px;font-family:微软雅黑;">
			                        &nbsp;&nbsp;&nbsp;6.&nbsp;申请人同时提交申请表和身份证复印件进行数据共享<br/></div>
			                        
			                        <div class="CenterP-right-content" style="font-size:14px;font-family:微软雅黑;">
			                        &nbsp;&nbsp;&nbsp;7.&nbsp;所获得的数据资料按照国家数据相关规定使用；<br/></div>
			                        
			                        <div class="CenterP-right-content" style="font-size:14px;font-family:微软雅黑;">
			                        &nbsp;&nbsp;&nbsp;8.&nbsp;违反以上规定的单位将被取消数据共享资格，并按国家有关规定承担相应责任。<br/><br/></div>
			                        <div style="text-align:center">
										<input type="checkbox" value="" id="ischeck"><b style="margin-left:10px;text-align:center;height:10px;font-size:14px; color:#316bb5; margin-top:0px; line-height:0px; font-weight:bold;">申请人阅读并遵守以上规定</b>
										<div class="warp-button">
											<br>
						                	<input type="button"  disabled="disabled"  id="next" class="shengfen-button selected" value="开始申请"  style="display: inline-block"><!--<img src="/DataShare/img/s1.png"/>-->
						                </div>
									</div>	
			                </div>
			            </div>
			        </div>
                     </div>
                </div>
            </div>
        </div>
    </div>
    <script>		
    	$.ajaxSetup({ cache: false });//全局禁用缓存
		$("#ischeck").click(function(){
			if ($(this).prop("checked")) {
				//设置checked属性
				$('#next').removeClass("selected").removeAttr("disabled").prop("checked", true);
			} else {
				//设置checked属性
				$('#next').addClass("selected").attr("disabled","disabled").prop("checked",false);
			}
		});

		//下一步
		$("#next").click(function(){
    		window.location.href="/DataShare/applyOrShowToJsp.action?thisNum=0&backOrDown=down";
    	})
    </script>
  
</body>
</html>

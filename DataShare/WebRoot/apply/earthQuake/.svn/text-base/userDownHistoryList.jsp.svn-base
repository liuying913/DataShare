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
	<script type="text/javascript" src="/DataShare/apply/earthQuake/userdownHistoryList.js"></script>
</head>
<body>
  <input id="earthQuakeId" type="hidden" name="earthQuakeId" value="${earthQuakeId}"/>
  <div id="iframeheight" style="height: 459px;">
  		<div class="pbox tspbox">
  			<div class="ptit frame_title">${applyTitle}</div>
  			<div class="sea-box" style="width:820px;">
  				<input type="text" value="" placeholder="请输入要搜索的文字" class="input01">
  				<input type="button" value="搜索" class="seabtn">
				<input type="button" value="导出" id="exportUserHistoryExcel" class="seabtn" style="margin-left:10px;">
  			</div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th style="white-space: nowrap;">年</th>
                <th style="white-space: nowrap;">地震名称</th>
                <th style="white-space: nowrap;">申请人</th>
                <th style="white-space: nowrap;">所属单位</th>
                <th style="white-space: nowrap;">文件路径</th>
                <th style="white-space: nowrap;">文件名称</th>
                <th style="white-space: nowrap;">下载时间</th>
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
                  <a href="#" id="previous" onclick="shang();">上一页</a>
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

  <script>
      $.ajaxSetup({ cache: false });//全局禁用缓存
  </script>
</body>
</html>

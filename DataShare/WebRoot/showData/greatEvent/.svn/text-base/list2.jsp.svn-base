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
    <link rel="stylesheet" type="text/css" href="/DataShare/css/drop-down.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	<script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
	<script type="text/javascript" src="/DataShare/js/showData/greatEventList.js" ></script>
    <script type="text/javascript" src="/DataShare/js/select/jquery-ui.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/select/select-widget-min.js"></script>
</head>
<body>
  <input id="applyState" type="hidden" name="applyState" value="${applyState}"/>
  <div id="iframeheight" style="height: 459px;">
  	  <div class="pbox tspbox">
	  <div class="ptit">${applyTitle}</div>
      <br/>
      <div class="sea-box"  style="width: 100%;margin-left:22px;">
          <div class="select-container" style="margin-right:10px;">
             <select name="apply-state" id="apply-state" style="float: left; color: #333;">
                <option value="">全部</option>
                <option value="1">更换接收机</option>
                <option value="2">更换天线</option>
                <option value="3">接收机固件升级</option>
             </select>
          </div>
          <input type="text" value=""  placeholder="请输入要搜索的文字" class="input01" style="width:375px;">
          <input type="button" value="搜索" id="search" class="seabtn" style="margin-right:10px;">
          &nbsp;&nbsp;&nbsp;&nbsp;
          <input type="button" value="导出" id="upButton" class="seabtn">
      </div>
          <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th style="white-space: nowrap;">台站编号</th>
                <th style="white-space: nowrap;">台站名称</th>
                <th style="white-space: nowrap;">发现时间</th>
                <th style="white-space: nowrap;">解决时间</th>
                <th style="white-space: nowrap;">类型</th>
                <th style="white-space: nowrap;">问题描述</th>
                <th style="white-space: nowrap;">维修反馈</th>
                <th style="white-space: nowrap;">操作</th>
              </tr>
              <tbody id="tbody"></tbody>
           </table>
       </div>
       <div class="newstable-content">
        <div class="newstable-fy">
            <div class="newstable-fy-pages">
                <span>共<font color="#ff0000" id="zongyeshu"></font>页<font color="#ff0000" id="zongshuju"></font>条记录</span>
                <a href="javascript:void(0)" onclick="yeshu(1)">首页</a>
                <a href="javascript:void(0)" id="prevpage" onclick="shang();">上一页</a>
                <span class="everypages"></span>
                <a href="javascript:void(0)" onclick="next();">下一页</a>
                <a href="javascript:void(0)" onclick="yeshu('end')">末页</a>
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

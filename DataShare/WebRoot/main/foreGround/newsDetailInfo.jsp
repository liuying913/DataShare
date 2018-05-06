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
    <script type="text/javascript" src="/DataShare/ueditor/ueditor.config.js"></script>
    <script type="text/javascript" src="/DataShare/ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
    <script type="text/javascript" src="/DataShare/js/news/newsinfo.js"></script>

	<script type="text/javascript">
	  var operType = '<%=request.getAttribute("operType")%>';
	  if(operType == 'null')
	  {
	  	operType = '<%=request.getParameter("operType")%>';
	  }
	</script>
		<style type="text/css">
		.input-1{
			width: 400px;
		    height: 25px;
		    text-align: left;
		    text-indent: 0.5em;
		    border: 1px solid #ccc;
		}
		.input-2{
			width: 200px;
		    height: 25px;
		    text-align: left;
		    background: #fafafa;
		    border: 1px solid #ccc;
		    text-indent: 0.5rem;
		}
		.title-1{
			padding: 0 10px;
    		font-weight: bold;
    		font-size: 18px;
    		color:#316bb5;
		}
		#operatorDiv{
            text-align: center;
          margin-top: 20px;
		}
		#operatorDiv input{
         float: none;
         width: 110px;
	     height: 35px;
	     line-height: 35px;
	     margin: 0 auto;
	     text-align: center;
	     background: #306bb5;
	     border: none;
	     font-size: 14px;
	     color: #FFFFFF;
	     margin-left: 15px;
	     border-radius: 5px;
		}
	</style>
</head>
<body>
  	 <form action="#" method="post" style="height: 100%">
  	 <input type="hidden" id="newsID" value="${news.id}"/>
  	 	<!-- 标题编辑 -->
  	 	<div id="titleDiv" style="height: 40px;">
  	 		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="l-table-edit">
              <tr class='even'>
                <td class="l-table-edit-td title-1" align="right">新闻标题:</td>
                <td class="l-table-edit-td" align="left"><input class="input-1" type="text" id="titleContainer"  value = "${news.title}"/></td>

				<td class="l-table-edit-td title-1" align="right">排序:</td>
                <td class="l-table-edit-td" align="left"><input class="input-2" type="text" id="orderContainer" value="${news.orders}"/></td>
              
                <td class="l-table-edit-td title-1" align="right">发布时间:</td>
                <td class="l-table-edit-td" align="left"><input class="input-2" type="text" id="timeContainer"  disabled="disabled" value="${news.time}"/></td>
              </tr>
            </table>
  	 	</div>
  	 	<!-- 内容编辑 -->
        <div id="containerDiv" style="height: 580px;overflow: hidden">
	        <script id="container" name="content" type="text/plain" style="width:99%;height:500px">
        	</script>
        </div>
        <!-- 保存、返回功能区 -->
        <div id="operatorDiv">
	        <input type="button" id="saveBtn" value="保存" onclick="operNews()"/>
	       <!--  <input type="button" id="backBtn" value="返回" onclick="window.location.href = '/DataShare/manager/newsInfoListToJsp.action?applyTitle=新闻管理'"/> -->
        </div>
    </form>
   
    <!-- 实例化编辑器 -->
	<script type="text/javascript">
	
		$.ajaxSetup({ cache: false });//全局禁用缓存
		Date.prototype.Format = function(fmt) { //author: meizz 
			var o = {
				"M+" : this.getMonth() + 1, //月份 
				"d+" : this.getDate(), //日 
				"h+" : this.getHours(), //小时 
				"m+" : this.getMinutes(), //分 
				"s+" : this.getSeconds(), //秒 
				"q+" : Math.floor((this.getMonth() + 3) / 3), //季度 
				"S" : this.getMilliseconds()
			//毫秒 
			};
			if (/(y+)/.test(fmt))
				fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
						.substr(4 - RegExp.$1.length));
			for ( var k in o)
				if (new RegExp("(" + k + ")").test(fmt))
					fmt = fmt.replace(RegExp.$1,
							(RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k])
									.substr(("" + o[k]).length)));
			return fmt;
		}

		var editor = UE.getEditor('container');
		$('#iframe', parent.document).css("height",
				$(".shop-left", parent.document).height());
		if (operType == "insert") {

			$("#timeContainer").attr("value",new Date().Format("yyyy/MM/dd"));

		}
		editor.ready(function() {
			editor.setContent(unescape('${news.content}'));
		});
	</script>
</body>
</html>

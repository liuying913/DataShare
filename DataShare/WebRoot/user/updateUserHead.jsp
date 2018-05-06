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
        <script type="text/javascript" src="/DataShare/user/scripts/swfobject.js"></script>
        <script type="text/javascript" src="/DataShare/user/scripts/fullAvatarEditor.js"></script>
    </head>
    <body>
	
	${imgPath}
    <div><img alt="" src="/head/${imgPath}"></div>
    
    	<input type="hidden" id="userId" name="userId" value="${userId}"/>
		<div style="width:630px;height:1000px;text-align:center">
			<!--<h1 style="text-align:center">富头像上传编辑器演示</h1>-->
			<div>
				<p id="swfContainer">
                    本组件需要安装Flash Player后才可使用，请从<a href="flashplayer24pp_va_install.exe">这里</a>下载安装。
				</p>
			</div>
        </div>
        
        <script type="text/javascript">
            //alert("${sessionScope.userInfo.userName}");
       		var userId =document.getElementById("userId").value;
       		var urls='/DataShare/user/upload.jsp?userId='+userId;
            swfobject.addDomLoadEvent(function () {
                var swf = new fullAvatarEditor("swfContainer", {
				     id: 'swf',
					 //upload_url: '/DataShare/upLoadHead.action',
					 upload_url: urls,
					 src_upload:2,
					 avatar_sizes: "300*300",//100*100|50*50|32*32,表示一组或多组头像的尺寸。其间用"|"号分隔。
					 isShowUploadResultIcon: true,//在上传完成时（无论成功和失败），是否显示表示上传结果的图标
					 
					 src_size: "8MB",//选择的本地图片文件所允许的最大值，必须带单位，如888Byte，88KB，8MB
		             src_size_over_limit: "文件大小超出2MB，请重新选择图片。",//当选择的原图片文件的大小超出指定最大值时的提示文本。可使用占位符{0}表示选择的原图片文件的大小。
		             src_box_width: "300",//原图编辑框的宽度
		             src_box_height: "300",//原图编辑框的高度
		             tab_visible: true,//是否显示选项卡*
                
			     },function (msg){
					switch(msg.code){
						case 1 : break;//alert("页面成功加载了组件！");
						case 2 : break;//alert("已成功加载默认指定的图片到编辑面板。");
						case 3 :
							if(msg.type == 0){
								alert("摄像头已准备就绪且用户已允许使用。");
							}else if(msg.type == 1){
								alert("摄像头已准备就绪但用户未允许使用！");
							}else{
								alert("摄像头被占用！");
							}
							break;
						case 5:
							if(msg.type == 0){
								if(msg.content.sourceUrl){
									alert("原图已成功保存至服务器，url为：\n" +　msg.content.sourceUrl);
								}
								alert("头像已成功保存至服务器，url为：\n" + msg.content.avatarUrls.join("\n"));
							}
							break;
				    }
				 }
			);
				
			//自定义上传按钮
			//document.getElementById("upload").onclick=function(){
			//	swf.call("upload");
			//};
	   });
        </script>
    </body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/font-awesome.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/css.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/pcgzs.css" />
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/mypcgzs.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/check.js"></script>
	<style>
		.base-info-input ul li {
            float: left;
			width: 421px;
			/*text-align:center;*/
		}
        .input01 {width: 250px;}
        .errState {
            position: relative;
        }
        .errState .tishi {
            position: absolute;
            left: 79px;
            top: 26px;
        }
        .pbox {
            padding-bottom: 20px;
        }
	</style>
</head>
<body>
  <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
  <input id="isManager" type="hidden" name="isManager" value="${isManager}"/>
  <div id="iframeheight" style=" margin:0 auto;background:#f7f7f7;">
   	     <div class="web-width" >
			 <div class="for-liucheng">
			      <div class="liulist for-cur"></div>
			      <div class="liulist"></div>
			      <div class="liulist"></div>
			      <div class="liulist" style="border-radius: 0 5px 5px 0;"></div>
			      <div class="liutextbox">
			          <div class="liutext for-cur"><em>1</em><br /><strong>基本信息填写</strong></div>
			          <div class="liutext"><em>2</em><br /><strong>申请数据筛选</strong></div>
			          <div class="liutext"><em>3</em><br /><strong>审批表上传</strong></div>
			          <div class="liutext"><em>4</em><br /><strong>审核结果</strong></div>
			      </div>
			 </div>
        </div>
        <div style="height:15px;margin: 0 auto;background:white;"></div>
  		<div class="pbox tspbox">
                <div class="base-info-input clearfix" style="padding: 12px 0;" >
			    	<div class="base-info tsbase-info1"><span>申请者信息</span></div>
                    <ul style="padding:0 0 0 62px;">
                    	<li>
                        	<span><b class="requireFlag">*</b>申请人:</span>
                            <label><input id="apply_Person" name="apply_Person" type="text"  value="" class="input01 require"  err="请填写申请人"></label>
							<div class="sucuState"   style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                            <div class="errState" id="apply_Person" style="display:none;"><p class="tishi" style="text-indent: 0px;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写申请人</p></div>
                        </li>
                    	<li>
                        	<span><b class="requireFlag">*</b>申请单位:</span>
                            <label><input id="apply_Unit" name="apply_Unit" type="text"   value="" class="input01 require"  err="请填写申请单位"></label>
                            <div class="sucuState"   style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                            <div class="errState" id="apply_Unit" style="display:none;"><p class="tishi" style="text-indent: 0px;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写申请单位</p></div>
                        </li>
                        <li>
                        	<span><b class="requireFlag">*</b>联系电话:</span>
                            <label><input id="apply_Phone" name="apply_Phone" type="text"  value="" class="input01 require"  err="请填写电话"></label>
							<div class="sucuState"  style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                            <div class="errState" id="apply_Phone" style="display:none;"><p class="tishi" style="text-indent: 0px; color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写电话</p></div>
                        </li>
                        <li>
                        	<span><b class="requireFlag">*</b>邮箱:</span>
                            <label><input id="appply_Email" name="appply_Email" type="text"   value="" class="input01 require"  err="请填写邮箱"></label>
							<div class="sucuState"   style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                            <div class="errState" id="appply_Email" style="display:none;"><p class="tishi" style="text-indent: 0px;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写邮箱</p></div>
                        </li>
                        <li>
                        	<span><b class="requireFlag">*</b>身份证号码:</span>
                            <label><input id="apply_PersonId" name="apply_PersonId" type="text"   value="" class="input01 require"  err="请填写身份证号码"></label>
							<div  class="sucuState"  style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                            <div class="errState" id="apply_PersonId" style="display:none;"><p class="tishi" style="text-indent: 0px;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写身份证号码</p></div>
                        </li>
                    </ul>
                   
               </div>
               <div style="height:15px;margin: 0 auto;background:white;"></div>
       		  <div class="base-info tsbase-info1  toggleItem"  style="width: 209px;padding: 12px 0;cursor: pointer; color: #0d85ba;overflow: auto;"><span>支持的研究课题项目介绍</span></div>
	               <div class="base-info-input clearfix" style="border-top:none;">
					   <ul style="padding:0 0 0 62px;" id="programContent">
						   <li>
							   <span>项目名称:</span>
							   <label><input id="result_Name" name="result_Name" type="text" value="" class="input01" err="请填写项目名称"/></label>
							   <div class="sucuState"   style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                               <div class="errState" id="apply_Person" style="display:none;"><p class="tishi" style="text-indent: 0px;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">字符长度</p></div>
						   </li>
						   <li>
							   <span>项目编号:</span>
							   <label><input id="result_Person" name="result_Person" type="text" value="" class="input01"  err="请填写项目编号"></label>
							   <div class="sucuState"   style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                               <div class="errState" id="apply_Person" style="display:none;"><p class="tishi" style="text-indent: 0px;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">字符长度</p></div>
						   </li>
						   <li>
							   <span>经费:</span>
							   <label><input id="result_Money" name="result_Money" type="text" value="" class="input01"  err="请填写经费"/></label>
							   <div class="sucuState"   style="display:none;"><p class="tishi" style="text-indent: 0px;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
                               <div class="errState" id="apply_Person" style="display:none;"><p class="tishi" style="text-indent: 0px;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">字符长度</p></div>
						   </li>
					   </ul>
				   </div>
        </div>
   </div>
   <div class="warp-button">
	 <button type="button" id="back" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->上一步</button>
	 <button type="button" id="next" class="shengfen-button"><!--<img src="/DataShare/img/s2.png"/>-->下一步</button>
  </div>

    <script>		
    	$.ajaxSetup({ cache: false });//全局禁用缓存
    	var applyId = $("#applyId").val();
    	//上一步
    	$("#back").click(function(){
			var url="/DataShare/applyOrShowToJsp.action?thisNum=1&backOrDown=back&applyTitle=数据申请&isManager=false&applyId="+applyId;
    		window.location.href=url;	
    	})
    	
    	var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;
		var i=0;
		//下一步
    	$("#next").click(function(){
    		str="";
    	    if(flag_1 && flag_2 && flag_3 && flag_4 && flag_5 && flag_6 && flag_7 && flag_8){
	    	    i=0;
				$(".require").each(function(e){
					if($(this).val()==""){
						$(this).addClass("err");
						$(this).parents("li").find(".errState").show();
						$(this).parents("li").find(".sucuState").hide();
						i=1;
					}else{
						$(this).removeClass("err");
						$(this).parents("li").find(".errState").hide();
						$(this).parents("li").find(".sucuState").show();
						if(e==rlen-1&&i==0){
							$(".input01").each(function(a) {
								str=str+"&"+$(this).prop("name")+"="+$(this).val();//str=str+"/"+$(this).name+':'+$(this).attr("name")+" "+$(this).prop("name");
							  	if(a==len-1){
							  	   str = encodeURI(str);
									var URL="/DataShare/saveApplyUser.action?"+str;
									//alert(URL);
									ajaxRequest(URL,"get",function(res){
									   // alert(res.date[0].code);
										if(res.date[0].code!=-1){
											window.location.href="/DataShare/applyOrShowToJsp.action?thisNum=1&backOrDown=down&isManager=false&applyId="+res.date[0].code;
										}else{
											
											alert(res.date[0].msg);	
										}
									})
								}  
							});	
						}
					}
				})
    	    }
		});
		//判断浏览器是否支持placeholder属性
		$(document).ready(function(){
		    $("#programContent").slideToggle(1);
			var doc=document,
					inputs=doc.getElementsByTagName('input'),
					supportPlaceholder='placeholder'in doc.createElement('input'),

					placeholder=function(input){
						var text=input.getAttribute('placeholder'),
								defaultValue=input.defaultValue;
						if(defaultValue==''){
							input.value=text
						}
						input.onfocus=function(){
							if(input.value===text){
								this.value=''
							}
						};
						input.onblur=function(){
							if(input.value===''){
								this.value=text
							}
						}
					};

			if(!supportPlaceholder){
				for(var i=0,len=inputs.length;i<len;i++){
					var input=inputs[i],text=input.getAttribute('placeholder');
					if(input.type==='text'&&text){
						placeholder(input)
					}
				}
			}
		});

    	$(".base-info-input ul li div").click(function(){
			$(this).hide();
		}) 
		$(".toggleItem").click(function(){
			//$(this).next(".base-info-input").find("ul").slideToggle();
			$("#programContent").slideToggle();
			$(this).find("span").toggleClass( "openState");
			$('#iframe', parent.document).css("height",530);
		})
		
		
		//申请人校验
		var flag_1 = true;
   		$("#apply_Person").blur(function(){
             var apply_Person = $("#apply_Person").val();
             var checkObj = $("#apply_Person").parent();
             if(!checkCNName(apply_Person)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须包含中文字符");
                 flag_1 = false;
                 return;
            }else{
             	if(!checkLength(apply_Person)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
	                 flag_1 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_1 = true;
	            }
            }
        });
        
        //申请人校验
        var flag_2 = true;
   		$("#apply_Unit").blur(function(){
             var apply_Person = $("#apply_Unit").val();
             var checkObj = $("#apply_Unit").parent();
             if(!checkLength2(apply_Person)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到20个字节");
                 flag_2 = false;
                 return;
            }else{
             	checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_2 = true;
            }
        });
        
        //联系方式校验
        var flag_3 = true;
   		$("#apply_Phone").blur(function(){
             var apply_Person = $("#apply_Phone").val();
             var checkObj = $("#apply_Phone").parent();
             if(checkMobile(apply_Person) || checkPhone(apply_Person)){
                checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_3 = true;
            }else{
				checkObj.siblings(".errState").show();
                checkObj.siblings(".sucuState").hide();
                checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>请输入正确的手机或电话号码");
                flag_3 = false;
                return;
            }
        });
        
        //邮箱校验
        var flag_4 = true;
   		$("#appply_Email").blur(function(){
             var apply_Person = $("#appply_Email").val();
             var checkObj = $("#appply_Email").parent();
             if(!checkMail(apply_Person)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>请输入正确的邮箱");
                 flag_4 = false;
                 return;
            }else{
             	if(!checkLengthParam(apply_Person,1,30)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度不能超过30个字节");
	                 flag_4 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_4 = true;
	            }
            }
        });
        
        //身份证校验
        var flag_5 = true;
   		$("#apply_PersonId").blur(function(){
             var apply_Person = $("#apply_PersonId").val();
             var checkObj = $("#apply_PersonId").parent();
             if(!isCardNo(apply_Person)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>请输入正确的身份证号码");
                 flag_5 = false;
                 return;
            }else{
             	checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_5 = true;
            }
        });
        
        
        //项目名称
        var flag_6 = true;
   		$("#result_Name").blur(function(){
             var apply_Person = $("#result_Name").val();
             var checkObj = $("#result_Name").parent();
             if(!checkLength2(apply_Person)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到20个字节");
                 flag_6 = false;
                 return;
            }else{
             	checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_6 = true;
            }
        });
        
        //项目编号
        var flag_7 = true;
   		$("#result_Person").blur(function(){
             var apply_Person = $("#result_Person").val();
             var checkObj = $("#result_Person").parent();
             if(!checkLength2(apply_Person)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到20个字节");
                 flag_7 = false;
                 return;
            }else{
             	checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_7 = true;
            }
        });
        
        //经费
        var flag_8 = true;
   		$("#result_Money").blur(function(){
             var apply_Person = $("#result_Money").val();
             var checkObj = $("#result_Money").parent();
             if(!checkLength(apply_Person)){
                 checkObj.siblings(".errState").show();
                 checkObj.siblings(".sucuState").hide();
                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
                 flag_8 = false;
                 return;
            }else{
             	checkObj.siblings(".sucuState").hide();
				checkObj.siblings(".errState").hide();
				flag_8 = true;
            }
        });
        
    </script>
</body>
</html>

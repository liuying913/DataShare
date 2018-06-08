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
    <script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
    <script type="text/javascript" src="/DataShare/js/common/check.js"></script>
	<style>
		.base-info-input ul.myul li{ width:350px;}
		.title-data{ font-size: 14px;  font-weight: bold;  margin-left: 117px;  margin-top: 30px;color: #306bb5;}
	</style>
</head>
<body>
  <input type="hidden" id="siteNumber" name="siteNumber" value="${siteNumber}"/>
  <input type="hidden" id="siteType" name="siteType" value="${siteType}"/>
  <div id="iframeheight" style="height: 459px;">
    	<div class="pbox tspbox" style="padding-bottom:0px;">
           <div class="ptit">数据整理设置</div>
  	    </div>
  		<div class="pbox" >
            <div class="base-info baseInfo" style="margin-top: 8px;">
                <div class="base-info-input">
					<div class="title-data">常规数据配置<span style="display: inline-block;margin-left: 286px;">应急数据配置</span></div>
					<ul  class="clearfix myul"  style="margin: 10px 0 0 100px;">
						<li>
							<span><b class="requireFlag">*</b>下载有效期 </span>
							<label><input name="applyPeriod" id="applyPeriodid" type="text" value="" class="input01 require" err="请填写申请期限"> 天</label>
							<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
						</li>
						<li>
							<span><b class="requireFlag">*</b>下载有效期 </span>
							<label><input name="applyValidityDayid" id="applyValidityDayid" type="text" value="1" class="input01 require" err="请填写申请期限"> 天</label>
							<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
						</li>
					</ul>
					
					<div class="title-data">应急数据下载范围</div>
                    <ul  class="clearfix myul"  style="margin: 10px 0 0 100px;">
                    	<li>
                        	<span><b class="requireFlag">*</b>[5-6)级地震</span>
                            <label> <input name="earthQuakeFirst" id="earthQuakeFirstid" type="text" value="" class="input01 require" err="请填写5-6级地震公里数"> 公里</label>
							<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
						</li>
                        <li>
                        	<span><b class="requireFlag">*</b>[6-7)级地震 </span>
                            <label><input name="earthQuakeSecond" id="earthQuakeSecondid" type="text" value="" class="input01 require" err="请填写6-7级地震公里数"> 公里</label>
							<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>

                        </li>
                        <li>
                        	<span><b class="requireFlag">*</b>{7-8)级地震</span>
                            <label> <input name="earthQuakeThird" id="earthQuakeThirdid" type="text" value="" class="input01 require" err="请填写7-8级地震公里数"> 公里</label>
							<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
                        </li>
						<li>
                        	<span><b class="requireFlag">*</b>超过8地震</span>
                            <label> <input name="earthQuakeForth" id="earthQuakeForthid" type="text" value="" class="input01 require" err="请填写超过8级地震公里数"> 公里 </label>
							<div class="sucuState" style="display:none;"><p class="tishi" style="text-indent: 0;"><img src="/DataShare/img/pcgzs/success.png"></p></div>
							<div class="errState" style="display:none;"><p class="tishi" style="text-indent: 0;color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
						</li>
                        <li>
							<label><input name="id" id="earthid" type="hidden" value="" class="input01" err="主键id"></label>
						</li>
                    </ul>
                    <div style="font-size: 12px;margin-left: 117px;  margin-top: 10px;margin-bottom: 0px;color: #306bb5;">温馨提示：本次修改对之前的数据申请下载有效期无影响</div>
                </div>
            </div>
			<div>
				<button type="button" id="save" class="shengfen-button" style="margin:10px auto;margin-top: 25px;float:none">保存</button>
			</div>
        </div>
    </div>
    <script>
    $.ajaxSetup({ cache: false });//全局禁用缓存
   	$("#save").click(function(){
		var id = $("#earthid").val();

		var len=$(".input01").length;
		var rlen=$(".require").length;
		var i=0;

		$(".require").each(function(e){

			if($(this).val()==""){
				$(this).parents("li").find(".errState").show();
				$(this).parents("li").find(".sucuState").hide();
                 i = 1;
			}else {
				$(this).parents("li").find(".errState").hide();
				$(this).parents("li").find(".sucuState").show();
				if(e==rlen-1&&i==0){
					$(".input01").each(function(a) {

						var earthQuakeFirst = $("#earthQuakeFirstid").val();
						var earthQuakeSecond = $("#earthQuakeSecondid").val();
						var earthQuakeThird = $("#earthQuakeThirdid").val();
						var earthQuakeForth = $("#earthQuakeForthid").val();
						var applyValidityDay = $("#applyValidityDayid").val();
						var applyPeriod = $("#applyPeriodid").val();

						if(a==len-1){
						    var str= "id="+id+"&earthquakefirst="+earthQuakeFirst+"&earthquakesecond="+earthQuakeSecond
								+"&earthquakethird="+earthQuakeThird+"&earthquakeforth="+earthQuakeForth
								+"&applyvalidityday="+applyValidityDay+"&applyperiod="+applyPeriod
							str = encodeURI(str);
							$.ajax({
								type: "post",
								url: "/DataShare/earthQuakeConfigUpdate.action",
								data: str,
								success: function(ref){
									//alert(ref.msg);
									init( ref.msg ,0 );
								}
							});
						}
					});
				}

			}
	     })
	})


	$(document).ready(function(){
		queryearthQuakeConfigQuery();
   	})
   	function queryearthQuakeConfigQuery(){
   			ajaxRequest("/DataShare/earthQuakeConfigQuery.action","get",function(res){
			$("#earthid").val(res[0].id);
			$("#earthQuakeFirstid").val(res[0].earthquakefirst);
			$("#earthQuakeSecondid").val(res[0].earthquakesecond);
			$("#earthQuakeThirdid").val(res[0].earthquakethird);
			$("#earthQuakeForthid").val(res[0].earthquakeforth);
			$("#applyValidityDayid").val(res[0].applyvalidityday);
			$("#applyPeriodid").val(res[0].applyperiod);
		})
   	};
   	
   	
   	
   	//5_6深度校验
    var flag_1 = true;
    $("#earthQuakeFirstid").blur(function(){
            var apply_Person = $("#earthQuakeFirstid").val();
            var checkObj = $("#earthQuakeFirstid").parent();
            if(!IsDouble(apply_Person) && !IsInteger(apply_Person)){
                checkObj.siblings(".errState").show();
                checkObj.siblings(".sucuState").hide();
                checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为数值");
                flag_1 = false;
                return;
            }else{
            	if(!checkLength3(apply_Person)){
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
    })
    
    //6 7
    var flag_2 = true;
    $("#earthQuakeSecondid").blur(function(){
            var apply_Person = $("#earthQuakeSecondid").val();
            var checkObj = $("#earthQuakeSecondid").parent();
            if(!IsInteger(apply_Person)){
                checkObj.siblings(".errState").show();
                checkObj.siblings(".sucuState").hide();
                checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为整数");
                flag_2 = false;
                return;
            }else{
            	if(!checkLength3(apply_Person)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
	                 flag_2 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_2 = true;
	            }
           }
    })
    
    //7 8
    var flag_3 = true;
    $("#earthQuakeThirdid").blur(function(){
            var apply_Person = $("#earthQuakeThirdid").val();
            var checkObj = $("#earthQuakeThirdid").parent();
            if(!IsInteger(apply_Person)){
                checkObj.siblings(".errState").show();
                checkObj.siblings(".sucuState").hide();
                checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为整数");
                flag_3 = false;
                return;
            }else{
            	if(!checkLength3(apply_Person)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
	                 flag_3 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_3 = true;
	            }
           }
    })
    
    //8
    var flag_4 = true;
    $("#earthQuakeForthid").blur(function(){
            var apply_Person = $("#earthQuakeForthid").val();
            var checkObj = $("#earthQuakeForthid").parent();
            if(!IsInteger(apply_Person)){
                checkObj.siblings(".errState").show();
                checkObj.siblings(".sucuState").hide();
                checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为整数");
                flag_4 = false;
                return;
            }else{
            	if(!checkLength3(apply_Person)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
	                 flag_4 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_4 = true;
	            }
           }
    })
    
    
    //申请有效期
    var flag_5 = true;
    $("#applyValidityDayid").blur(function(){
            var apply_Person = $("#applyValidityDayid").val();
            var checkObj = $("#applyValidityDayid").parent();
            if(!IsInteger(apply_Person)){
                checkObj.siblings(".errState").show();
                checkObj.siblings(".sucuState").hide();
                checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为整数");
                flag_5 = false;
                return;
            }else{
            	if(!checkLength3(apply_Person)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
	                 flag_5 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_5 = true;
	            }
           }
    })
    
    //申请有效期
    var flag_6 = true;
    $("#applyPeriodid").blur(function(){
            var apply_Person = $("#applyPeriodid").val();
            var checkObj = $("#applyPeriodid").parent();
            if(!IsInteger(apply_Person)){
                checkObj.siblings(".errState").show();
                checkObj.siblings(".sucuState").hide();
                checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>必须为整数");
                flag_6 = false;
                return;
            }else{
            	if(!checkLength3(apply_Person)){
	                 checkObj.siblings(".errState").show();
	                 checkObj.siblings(".sucuState").hide();
	                 checkObj.siblings(".errState").find(".tishi").html("<img src='/DataShare/img/pcgzs/error.png'>字符长度为2到8个字节");
	                 flag_6 = false;
	                 return;
	            }else{
	             	checkObj.siblings(".sucuState").hide();
					checkObj.siblings(".errState").hide();
					flag_6 = true;
	            }
           }
    })
    	
    </script>
</body>
</html>

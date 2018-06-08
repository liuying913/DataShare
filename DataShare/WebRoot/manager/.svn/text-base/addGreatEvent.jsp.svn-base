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
	<script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	
	<script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/manager/addGreatEvent.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/DataShare/js/common/check.js"></script>
	<style>

		.base-info-input ul{
			margin: 10px 0 0 105px;
		}

		.base-info-input ul li span {
			color: #333;
			width:82px;
		}
		.base-info-input ul li {
			width: 356px;
			text-align: left;
			padding: 10px;
		}
		.tishi{
			height: 28px;
			line-height: 32px;
			display: inline-block;
			float: left;
			text-indent: 0px;
			margin-left:10px;
		}
		.tishi img{margin-right: 4px;}
		.base-info-input ul li.type2{display: none;}

	</style>
</head>
<body>
  <input type="hidden" id="applyId" name="applyId" value="1"/>
  <input type="hidden" id="siteType" name="siteType" value="1"/>
  <input type="hidden" id="fileType" name="fileType" value="1"/>

  <div id="iframeheight">
        <div class="pbox tspbox">
        <div class="ptit">添加站点大事记</div>
        <div class="base-info">
		<div class="shop-right">
		
		<div class="shop-warp02" style="display: block;">
			<div class="warp02-top">
				<form action="" method="post" id="warp02-top-form">
					<div class="shengfen shengfen03">
						<div class="span pp" ><span>类别</span></div>
						<div class="da_inner input-warp03">
							<label class="input-warp-contnor check_style check_y4 on"><input type="checkbox" name="type" value="1" checked="checked"><em>更换接收机</em></label>
							<label class="input-warp-contnor check_style check_y4"><input type="checkbox" name="type" value="2"><em>更换天线</em></label>
							<label class="input-warp-contnor check_style check_y4"><input type="checkbox" name="type" value="3"><em>固件升级</em></label>
						</div>
					</div>

					<div class="shengfen shengfen02">
						<div class="span pp"><span>部委</span></div>
						<div class="input-warp input-warp00 da_inner"></div>	
					</div>
					<div class="shengfen shengfen01">
						<div class="span yy"><span>地区</span></div>
                        <div class="shengfen-right da_inner">
                            <div class="input-warp input-warp01 clearfix">
                            </div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					<div class="shengfen shengfen02" >
						<div class="span"><span>台站</span></div>
                        <div class="shengfen-right da_inner">
                            <div class="input-warp input-warp02 clearfix"></div>
                        </div>
					</div>

					<div class="pbox"  style="width:100%;" id="tipoi" >
						<div class="base-info baseInfo  base-info-back">
							<div class="base-info-input">
								<ul class="clearfix" >
									
									<li>
										<span><b class="requireFlag">*</b>开始时间</span>
										<label><input name="startTimeStr" type="text" value="" class="input01 require" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" err="开始时间"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li>
										<span><b class="requireFlag">*</b>结束时间</span>
										<label><input name="endTimeStr" type="text" value="" class="input01 require" err="结束时间" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
										
									<li  class="type1">
										<span>接收机类型(前)</span>
										<label><input id="type_new" name="model_old" type="text" value="" class="input01" err="接收机类型(前)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li class="type1">
										<span>接收机类型(后)</span>
										<label><input name="model_new" type="text" value="" class="input01" err="接收机类型(后)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>

									<li class="type2">
										<span>天线高(前)</span>
										<label><input name="height_new" type="text" value="" class="input01" err="天线高(前)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li class="type2">
										<span>天线高(后)</span>
										<label><input name="height_old" type="text" value="" class="input01" err="天线高(后)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>

									<li class="type2">
										<span>天线型号(前)</span>
										<label><input name="model_old" type="text" value="" class="input01" err="天线型号(前)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li class="type2">
										<span>天线型号(后)</span>
										<label><input name="model_new" type="text" value="" class="input01" err="天线型号(后)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>

									<li class="type2">
										<span>RINEX名(前)</span>
										<label><input name="rinex_old" type="text" value="" class="input01" err="RINEX名(前)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li class="type2">
										<span>RINEX名(后)</span>
										<label><input name="rinex_new" type="text" value="" class="input01" err="RINEX名(后)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li>
										<span>SN(前)</span>
										<label><input name="sn_old" type="text" value="" class="input01" err="SN(前)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li>
										<span>SN(后)</span>
										<label><input name="sn_new" type="text" value="" class="input01" err="SN(后)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li   class="type1 type3">
										<span>FW(前)</span>
										<label><input name="fw_old" type="text" value="" class="input01" err="FW(前)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									<li  class="type1  type3">
										<span>FW(后)</span>
										<label><input  name="fw_new" type="text" value="" class="input01" err="FW(后)"></label>
										<div class="sucuState"   style="display:none;"><p class="tishi"><img src="/DataShare/img/pcgzs/success.png"></p></div>
										<div class="errState"  style="display:none;"><p class="tishi" style="color: #f73e4c"><img src="/DataShare/img/pcgzs/error.png">请填写</p></div>
									</li>
									
									<li>
										<span>问题描述</span>
										<label><input name="describe" type="text" value="" class="input01" err="问题描述"></label>
									</li>
									<li>
										<span>维修反馈</span>
										<label><input name="feedback" type="text" value="" class="input01" err="维修反馈"></label>
								
									</li>
										
								</ul>

							</div>
						</div>
					</div>
					</br>
					<div class="warp-button">
						<button type="button" value="30S" class="shengfen-button" id="addEvent">添加大事记</button>
					</div>
				</form>
			</div>
            
		</div>
		</div>
        </div>
            <script>
				$.ajaxSetup({ cache: false });//全局禁用缓存		
        
            </script>
        </div>
    </div>
</body>
</html>

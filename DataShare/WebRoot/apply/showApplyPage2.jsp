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
    
    <link rel="stylesheet" type="text/css" href="/DataShare/css/apply.css" />
    <link rel="stylesheet" type="text/css" href="/DataShare/css/qd.css" />
    <script type="text/javascript" src="/DataShare/js/jquery-1.7.1.min.js"></script>
    <script type="text/javascript" src="/DataShare/js/layer/layer.js"></script>
    <script type="text/javascript" src="/DataShare/js/jquery.cookie.js"></script>
    <script type="text/javascript" src="/DataShare/js/extra.js"></script>
	
	<script src="/DataShare/js/timeCommon/WdatePicker.js"  type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/timeCommon/todayTime.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/jQueryv1.9.js" type="text/javascript" charset="utf-8"></script>
	<script src="/DataShare/js/showApplyPage.js" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="/DataShare/js/common/popup/commonPopup.js"></script>
	
	 <style>
          #jwInput::-webkit-input-placeholder{
              <!--color: red;-->
              font-size: 12px;
              line-height: 30px;
          }
      </style>
</head>
<body>
  <input type="hidden" id="applyId" name="applyId" value="${applyId}"/>
  <input id="isManager" type="hidden" name="isManager" value="${isManager}"/>
  <div id="iframeheight" style=" margin-top: -0px;min-height:600px;">
  
	   	 <div class="web-width" >
		     <div class="for-liucheng">
				 <div class="liulist for-cur"></div>
				 <div class="liulist for-cur"></div>
				 <div class="liulist"></div>
				 <div class="liulist" style="border-radius: 0 5px 5px 0;"></div>
			      <div class="liutextbox">
			          <div class="liutext for-cur"><em>1</em><br /><strong>基本信息填写</strong></div>
			          <div class="liutext for-cur"><em>2</em><br /><strong>申请数据筛选</strong></div>
			          <div class="liutext"><em>3</em><br /><strong>审批表下载</strong></div>
			          <div class="liutext"><em>4</em><br /><strong>审核结果</strong></div>
			      </div>
		      </div>
	     </div>
			
		<div style="height:15px;margin: 0 auto;background:white;"></div>
  		<div class="pbox tspbox" style=" margin-top: -15px;">
            <div class="base-info tsbase-info1">	
		<div class="shop-right" >
		 <!--  ==查询1==  -->		
		<div class="shop-warp" >
			<div class="liet bianliang">
					<i></i><p>陆态30秒日常数据</p><img src="/DataShare/img/img-06_03.png"/>
			</div>
		</div>
		<div class="shop-warp02" style="display: block;">
			<div class="warp02-top">
				<form action="" method="post" id="warp02-top-form">
					<div class="shengfen shengfen03">
						<div class="span pp"><span>开始时间</span></div>
						<input type="text" name="" class="kaishi" id="kaishi" disabled="true" value="2017-01-01" />
						<div class="span pp"><span>结束时间</span></div>
						<input type="text" name="" class="jieshu" id="jieshu" disabled="true" value="2017-12-31" />
					</div>
					<div class="shengfen shengfen03">
						<div class="span pp"><span>经纬度</span></div>
						<input type="text" id="jwInput" class="dongwei" style="font-size:12px;" value="" disabled="true"  placeholder="最小纬度"/>
						<input type="text" id="jwInput" class="xiwei"   style="font-size:12px;" value="" disabled="true"  placeholder="最大纬度"/>
						<input type="text" id="jwInput" class="dongjin" style="font-size:12px;" value="" disabled="true"  placeholder="最小经度"/>
						<input type="text" id="jwInput" class="xijin"   style="font-size:12px;" value="" disabled="true" placeholder="最大经度"/>
						<input type="hidden" name="" class="Datetype"   value="1" />		
					</div>
					
					<div class="shengfen shengfen01">
						<div class="span yy"><span>地区</span></div>
                        <div class="shengfen-right da_inner">
                            <div class="input-warp input-warp01 clearfix"></div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					
					<div class="shengfen shengfen02" >
						<div class="span">
							<span>台站</span>
						</div>
                        <div class="shengfen-right">
                            <div class="input-warp input-warp02 clearfix"></div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan2" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan2" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					<div class="warp-button">
						<button type="button" value="30S" class="shengfen-button"><img src="/DataShare/img/s1.png"/>查询</button>
					</div>
				</form>
			</div>
			<!--  ==表格==  -->
			<div class="warp02-bottom tswarp01" style="min-height:500px;">
            <div class="warp02-box">
				<div class="warp02-bottom-left">
					<ul class="ul-img"></ul>
				</div>
				<div class="warp02-bottom-right fl">
					<table width="100%" border="1" class="table">
					  <tr class="tr-01">
					        <th scope="col"><i></i>台站编号</th>
                            <th scope="col"><i></i>台站名称</th>
                            <th scope="col"><i></i>文件名称</th>
                            <th scope="col"><i></i>文件大小</th>
                            <th scope="col"><i></i>历元数</th>
                            <th scope="col"><i></i>地区</th>
                            <th scope="col"><i></i>是否完整</th>
					  </tr>
					</table>
				</div>
                <div class="pclear"></div>
			</div></div>
		</div>
		<!--  ==查询2==  -->
		<div class="shop-warp" >
			<div class="liet">
					<i></i><p>陆态流动观测数据</p><img src="/DataShare/img/img-06_03.png"/>
			</div>
		</div>
		<div class="shop-warp02" >
			<div class="warp02-top">
				<form action="" method="post" id="warp02-top-form">
					<div class="shengfen shengfen03">
						<div class="span pp"><span>开始时间</span></div>
						<input type="text" name="" class="kaishi" id="kaishi" disabled="true" value="2017-01-01" />						
						<div class="span pp"><span>结束时间</span></div>
						<input type="text" name="" class="jieshu" id="jieshu" disabled="true" value="2017-12-31" />
					</div>
					<div class="shengfen shengfen03">
						<div class="span pp"><span>经纬度</span></div>
						<input type="text" id="jwInput" style="font-size:12px;" class="dongwei" disabled="true" value="" placeholder="最小纬度"/>
						<input type="text" id="jwInput" style="font-size:12px;" class="xiwei" disabled="true" value="" placeholder="最大纬度"/>
						<input type="text" id="jwInput" style="font-size:12px;" class="dongjin" disabled="true" value="" placeholder="最小经度"/>
						<input type="text" id="jwInput" style="font-size:12px;" class="xijin" disabled="true" value="" placeholder="最大经度"/>
						<input type="hidden" name="" class="Datetype" value="2" />		
					</div>
					<div class="shengfen shengfen01">
						<div class="span  yy">
							<span>地区</span>
							
						</div>
                        <div class="shengfen-right">
                            <div class="input-warp input-warp01 clearfix"></div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					<div class="shengfen shengfen02" >
						<div class="span">
						<span>台站</span>
							
						</div>
                        <div class="shengfen-right">
                            <div class="input-warp input-warp02 clearfix"></div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan2" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan2" value="" /><em>反选</em></label> 
							</div>
                        </div>
					</div>
					<div class="warp-button">
						<button type="button" class="shengfen-button"><img src="/DataShare/img/s1.png"/>查询</button>
					</div>
				</form>
			</div>
			<!--  ==表格==  -->
			<div class="warp02-bottom tswarp01" style="min-height:500px;">
            	<div class="warp02-box">
				<div class="warp02-bottom-left">
					<ul class="ul-img"></ul>
				</div>
				<div class="warp02-bottom-right fl">
					<table width="100%" border="1" class="table">
					  <tr class="tr-01">
					   
					    <th scope="col"><i></i>年</th>
					    <th scope="col"><i></i>年积日</th>
					    <th scope="col"><i></i>站点名称</th>
					    <th scope="col"><i></i>文件名称</th>
					    <th scope="col"><i></i>文件大小</th>
					    <th scope="col"><i></i>地区</th>
					    <th scope="col"><i></i>是否完整</th>
					  </tr>
					</table>
				</div><div class="pclear"></div></div>
			</div>
		</div>
		<!--  ==查询3==  -->
		<div class="shop-warp" >
			<div class="liet">
					<i></i><p>其他项目共享数据</p><img src="/DataShare/img/img-06_03.png"/>
			</div>
		</div>
		<div class="shop-warp02" >
			<div class="warp02-top">
				<form action="" method="post" id="warp02-top-form">
					<div class="shengfen shengfen03">
						<div class="span pp"><span>开始时间</span></div>
						<input type="text" name="" class="kaishi" id="kaishi" disabled="true" value="2017-01-01" />						
						<div class="span pp"><span>结束时间</span></div>
						<input type="text" name="" class="jieshu" id="jieshu" disabled="true" value="2017-12-31" />
					</div>
					<div class="shengfen shengfen03">
						<div class="span pp"><span>经纬度</span></div>
						<input type="text" name="" id="jwInput" style="font-size:12px;" class="dongwei" disabled="true"  value="" placeholder="最小纬度"/>
						<input type="text" name="" id="jwInput" style="font-size:12px;" class="xiwei" disabled="true"  value="" placeholder="最大纬度"/>
						<input type="text" name="" id="jwInput" style="font-size:12px;" class="dongjin" disabled="true"  value="" placeholder="最小经度"/>
						<input type="text" name="" id="jwInput" style="font-size:12px;" class="xijin" disabled="true"  value="" placeholder="最大经度"/>
						<input type="hidden" name="" class="Datetype" value="3" />		
					</div>
					<div class="shengfen shengfen01">
						<div class="span  yy">
							<span>地区</span>
							
						</div>
                        <div class="shengfen-right">
                            <div class="input-warp input-warp01 clearfix"></div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					<div class="shengfen shengfen02" >
						<div class="span">
						<span>台站</span>
							
						</div> 
                        <div class="shengfen-right">
                            <div class="input-warp input-warp02 clearfix"></div>
                            <div class="anniu">
								<label class="check_style check_y3"><input type="checkbox" name="" class="quanxuan2" value="" /><em>全选</em></label>
								<label class="check_style check_y3"><input type="checkbox" name="" class="fanxuan2" value="" /><em>反选</em></label>
							</div>
                        </div>
					</div>
					<div class="warp-button">
						<button type="button" class="shengfen-button"><img src="/DataShare/img/s1.png"/>查询</button>
					</div>
				</form>
			</div>
			<!--  ==表格==  -->
			<div class="warp02-bottom tswarp01" style="min-height:500px;">
            	<div class="warp02-box">
				<div class="warp02-bottom-left">
					<ul class="ul-img"></ul>
				</div>
				<div class="warp02-bottom-right fl">
					<table width="100%" border="1" class="table">
					  <tr class="tr-01">
					    <th scope="col"><i></i>年</th>
					    <th scope="col"><i></i>年积日</th>
					    <th scope="col"><i></i>站点名称</th>
					    <th scope="col"><i></i>文件名称</th>
					    <th scope="col"><i></i>文件大小</th>
					    <th scope="col"><i></i>地区</th>
					    <th scope="col"><i></i>是否完整</th>
					  </tr>
					</table>
				</div>
                <div class="pclear"></div>
			</div></div>
		</div>
		</div>
		<br>
		<div class="warp-button">
			<button type="button" id="back" class="shengfen-button"><!--<img src="/DataShare/img/s1.png"/>-->上一步</button>
			<button type="button" id="next" class="back-button"><!--<img src="/DataShare/img/s2.png"/>-->下一步</button>
		</div>
    </div>
            <script>
				$.ajaxSetup({ cache: false });//全局禁用缓存
            	var isManager = $("#isManager").val();
            	var applyId = $("#applyId").val();
            	//上一步
            	$("#back").click(function(){
					var url="/DataShare/applyOrShowToJsp.action?thisNum=2&backOrDown=back&applyTitle=数据申请&isManager="+isManager+"&applyId="+applyId;
					window.location.href=url;	
            	});
            	
            	//下一步
            	$("#next").click(function(){
					var url="/DataShare/applyOrShowToJsp.action?thisNum=2&backOrDown=down&applyTitle=数据申请&isManager="+isManager+"&applyId="+applyId;;
            		window.location.href=url;	
            	});
            </script>
        </div>
    </div>
    <div class="myload">数据加载中,请稍后...<br/><img src="/DataShare/img/pcgzs/load.gif"></div>
    <div class="mytips">加入成功</div>
</body>
</html>

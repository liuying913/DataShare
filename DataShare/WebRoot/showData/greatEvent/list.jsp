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
	<script type="text/javascript" src="/DataShare/js/common/page/pageJsonList.js"></script>
</head>
<body>
  <input id="applyState" type="hidden" name="applyState" value="${applyState}"/>
  <div id="iframeheight">
  		<div class="pbox tspbox">
			<div class="ptit">${applyTitle}</div>
			</br>
  			<div class="sea-box">

  				<table>
  					<tr>
  						<td><input type="text" value="" placeholder="请输入要搜索的文字" class="input01"></td>
  						<td><input type="button" value="搜索" class="seabtn"></td>
  						<td>&nbsp;</td>
  						<td><td><input type="button" value="导出" id="upButton" class="seabtn"></td></td>
  					</tr>
  				</table>
  			</div>
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class="table01">
              <tr>
                <th width="10%">基准站编号</th>
                <th width="10%">基准站名称</th>
                <th width="12%">所属地区</th>
                <th width="12%">承建部委</th>
                <th width="15%">更换接收机</th>
                <th width="15%">更换天线</th>
                <th width="15%">固件升级</th>
              </tr>
              <tbody id="tbody">
              </tbody>
            </table>
			<%--<div class="zprob" style="width:100%; height:20px;background-color:#e9f0f8"></div>--%>
            <!--分页-->
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
  </div>
  <script>		$.ajaxSetup({ cache: false });//全局禁用缓存
  	$(document).ready(function(){
  		var str="";
        var string="";
  		ajaxRequest("/DataShare/greatEvent/list.action","get",function(res){
			data = res;
			shua();
			page();
  		});
  		$(".seabtn").click(function(){
  			var item=$(".sea-box .input01").val();
  			ajaxRequest("/DataShare/greatEvent/list.action?param="+item,"get",function(res){
	  			//if(res[0].code==1){
	  				//data = res.date[0].result.sqlist;
					data = res;
		            shua();
					page();
				//}else{
				//	alert(res.date[0].msg)
				//}
	  		})
  		})

  	});

  	function shua( ){
        $("#tbody").html("");
		if(flag!=0){page();}else{flag=1;}
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+value.siteNumber+"</td>"
					+"	    <td>"+value.siteName+"</td>"
					+"	    <td>"+value.zoneName+"</td>"
					+"	    <td>"+value.departmentName+"</td>"
					+"	    <td>"+value.aTimeStr+"<span class='ico' data-id='ico-js-"+key+"'</span></td>"
					+"	    <td>"+value.bTimeStr+"<span class='ico' data-id='ico-ts-"+key+"'</span></td>"
					+"	    <td>"+value.cTimeStr+"<span class='ico' data-id='ico-gj-"+key+"'</span></td>"
					+"</tr>"
					+"<tr class='content'>"
                            +"<td colspan='7'>"
                            +"<div class='time1' id='ico-js-"+key+"'>"

                            +"</div>"
                            +"<div class='time2' id='ico-ts-"+key+"'>"

                            +"</div>"
                            +"<div class='time3' id='ico-gj-"+key+"'>"

                            +"</div>"
                            +"</td>"

					+"</tr>"
				}else{
					str=str+"<tr>"
					+"	    <td>"+value.siteNumber+"</td>"
					+"	    <td>"+value.siteName+"</td>"
					+"	    <td>"+value.zoneName+"</td>"
					+"	    <td>"+value.departmentName+"</td>"
					+"	    <td>"+value.aTimeStr+"<span class='ico' data-id='ico-js-"+key+"'></span></td>"
					+"	    <td>"+value.bTimeStr+"<span class='ico' data-id='ico-ts-"+key+"'></span></td>"
					+"	    <td>"+value.cTimeStr+"<span class='ico' data-id='ico-gj-"+key+"'></span></td>"
					+"    </tr>"
                            +"<tr  class='content'>"
                            +"<td colspan='7'>"
                            +"<div class='time1' id='ico-js-"+key+"'>"

                            +"</div>"
                            +"<div class='time2' id='ico-ts-"+key+"'>"

                            +"</div>"
                            +"<div class='time3' id='ico-gj-"+key+"'>"
                            +"</div>"
                            +"</td>"
                            +"</tr>"
				}
		  	}
    /*        ajaxRequest("typelList.action?siteNumber=BJFS&type=1","get",function(res){
                data = res;
                string="";
                for(var i=0 ; i<data.length;i++){
                    string =string +"<div class='items'>"
                            +"<div class='advert'>"
                            +"<img src='../img/pcgzs/advert.jpg' />"
                            +"</div>"
                            +"<div class='main'>"
                            +"<ul>"
                            +"<li>"
                            +"<p>开始时间："+data[i].startTimeStr+"</p>"
                            +"<p>结束时间："+data[i].endTimeStr+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p>接收机类型变更前："+data[i].model_old+"</p>"
                            +"<p>接收机类型变更后："+data[i].model_new+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p>SN版本变更前："+data[i].sn_old+"</p>"
                            +"<p>SN版本变更后："+data[i].sn_new+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p>FW版本变更前："+data[i].fw_old+"</p>"
                            +"<p>FW版本变更后："+data[i].fw_new+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                            +"</li>"
                            +"</ul>"
                            +"</div>"
                            +"</div>"
                }
                $("#ico-js-"+key).html(string);
            });
            ajaxRequest("typelList.action?siteNumber=BJFS&type=2","get",function(res){
                data = res;
                string="";
                for(var i=0 ; i<data.length;i++){
                    string =string +"<div class='items'>"
                            +"<div class='advert'>"
                            +"<img src='../img/pcgzs/advert.jpg' />"
                            +"</div>"
                            +"<div class='main'>"
                            +"<ul>"
                            +"<li>"
                            +"<p>开始时间："+data[i].startTimeStr+"</p>"
                            +"<p>结束时间："+data[i].endTimeStr+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p>天线高变更前："+data[i].height_old+"</p>"
                            +"<p>天线高变更后："+data[i].height_new+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p>天线型号变更前："+data[i].model_old+"</p>"
                            +"<p>天线型号变更后："+data[i].model_new+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p>RINEX变更前："+data[i].rinex_old+"</p>"
                            +"<p>RINEX变更后："+data[i].rinex_new+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p>SN版本变更前："+data[i].sn_old+"</p>"
                            +"<p>SN版本变更后："+data[i].sn_new+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                            +"</li>"
                            +"</ul>"
                            +"</div>"
                            +"</div>"
                }
                $("#ico-ts-"+key).html(string);
            });
            ajaxRequest("typelList.action?siteNumber=BJFS&type=3","get",function(res){
                data = res;
                string="";
                for(var i=0 ; i<data.length;i++){
                    string =string +"<div class='items'>"
                            +"<div class='advert'>"
                            +"<img src='../img/pcgzs/advert.jpg' />"
                            +"</div>"
                            +"<div class='main'>"
                            +"<ul>"
                            +"<li>"
                            +"<p>开始时间："+data[i].startTimeStr+"</p>"
                            +"<p>结束时间："+data[i].endTimeStr+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p>FW版本变更前："+data[i].fw_old+"</p>"
                            +"<p>FW版本变更后："+data[i].fw_new+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                            +"</li>"
                            +"<li>"
                            +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                            +"</li>"
                            +"</ul>"
                            +"</div>"
                            +"</div>"
                }
                $("#ico-gj-"+key).html(string);
            });*/
		});
/*        ajaxRequest("typelList.action?siteNumber=BJFS&type=1","get",function(res){
            data = res;
            string="";
            for(var i=0 ; i<data.length;i++){
                string =string +"<div class='items'>"
                        +"<div class='advert'>"
                        +"<img src='../img/pcgzs/advert.jpg' />"
                        +"</div>"
                        +"<div class='main'>"
                        +"<ul>"
                        +"<li>"
                        +"<p>开始时间："+data[i].startTimeStr+"</p>"
                        +"<p>结束时间："+data[i].endTimeStr+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p>接收机类型变更前："+data[i].model_old+"</p>"
                        +"<p>接收机类型变更后："+data[i].model_new+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p>SN版本变更前："+data[i].sn_old+"</p>"
                        +"<p>SN版本变更后："+data[i].sn_new+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p>FW版本变更前："+data[i].fw_old+"</p>"
                        +"<p>FW版本变更后："+data[i].fw_new+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                        +"</li>"
                        +"</ul>"
                        +"</div>"
                        +"</div>"
            }
            $("#ico-js-1").html(string);
        });
        ajaxRequest("typelList.action?siteNumber=BJFS&type=2","get",function(res){
            data = res;
            string="";
            for(var i=0 ; i<data.length;i++){
                string =string +"<div class='items'>"
                        +"<div class='advert'>"
                        +"<img src='../img/pcgzs/advert.jpg' />"
                        +"</div>"
                        +"<div class='main'>"
                        +"<ul>"
                        +"<li>"
                        +"<p>开始时间："+data[i].startTimeStr+"</p>"
                        +"<p>结束时间："+data[i].endTimeStr+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p>天线高变更前："+data[i].height_old+"</p>"
                        +"<p>天线高变更后："+data[i].height_new+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p>天线型号变更前："+data[i].model_old+"</p>"
                        +"<p>天线型号变更后："+data[i].model_new+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p>RINEX变更前："+data[i].rinex_old+"</p>"
                        +"<p>RINEX变更后："+data[i].rinex_new+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p>SN版本变更前："+data[i].sn_old+"</p>"
                        +"<p>SN版本变更后："+data[i].sn_new+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                        +"</li>"
                        +"</ul>"
                        +"</div>"
                        +"</div>"
            }
            $("#ico-ts-1").html(string);

        });
        ajaxRequest("typelList.action?siteNumber=BJFS&type=3","get",function(res){
            data = res;
            string="";
            for(var i=0 ; i<data.length;i++){
                string =string +"<div class='items'>"
                        +"<div class='advert'>"
                        +"<img src='../img/pcgzs/advert.jpg' />"
                        +"</div>"
                        +"<div class='main'>"
                        +"<ul>"
                        +"<li>"
                        +"<p>开始时间："+data[i].startTimeStr+"</p>"
                        +"<p>结束时间："+data[i].endTimeStr+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p>FW版本变更前："+data[i].fw_old+"</p>"
                        +"<p>FW版本变更后："+data[i].fw_new+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                        +"</li>"
                        +"<li>"
                        +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                        +"</li>"
                        +"</ul>"
                        +"</div>"
                        +"</div>"
            }
            $("#ico-gj-1").html(string);
        });*/
		$("#tbody").html(str);
		$('#iframe', parent.document).css("height",$("#iframeheight").height());
        /*toggel事件*/
        $(".ico").click(function(){
            var timeId = "#"+$(this).attr("data-id");
            var divAry=timeId.split("-");
            if($(timeId).css("display")=="block"){
                $(timeId).css("display","none");
                $(this).removeClass("up");
             /*   $(this).parent().parent().next(".content").animate({height:0,opacity:0},"slow").hide();*/
                $(this).parent().parent().next(".content").hide();
            }else{
                ajaxRequest("typelList.action?siteNumber=BJFS&type=1","get",function(res){
                    data = res;
                    string="";
                    for(var i=0 ; i<data.length;i++){
                        string =string +"<div class='items'>"
                                +"<div class='advert'>"
                                +"<img src='../img/pcgzs/advert.jpg' />"
                                +"</div>"
                                +"<div class='main'>"
                                +"<ul>"
                                +"<li>"
                                +"<p>开始时间："+data[i].startTimeStr+"</p>"
                                +"<p>结束时间："+data[i].endTimeStr+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p>接收机类型变更前："+data[i].model_old+"</p>"
                                +"<p>接收机类型变更后："+data[i].model_new+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p>SN版本变更前："+data[i].sn_old+"</p>"
                                +"<p>SN版本变更后："+data[i].sn_new+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p>FW版本变更前："+data[i].fw_old+"</p>"
                                +"<p>FW版本变更后："+data[i].fw_new+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                                +"</li>"
                                +"</ul>"
                                +"</div>"
                                +"</div>"
                    }
                    $("#ico-js-"+divAry[2]).html(string);
                });
                ajaxRequest("typelList.action?siteNumber=BJFS&type=2","get",function(res){
                    data = res;
                    string="";
                    for(var i=0 ; i<data.length;i++){
                        string =string +"<div class='items'>"
                                +"<div class='advert'>"
                                +"<img src='../img/pcgzs/advert.jpg' />"
                                +"</div>"
                                +"<div class='main'>"
                                +"<ul>"
                                +"<li>"
                                +"<p>开始时间："+data[i].startTimeStr+"</p>"
                                +"<p>结束时间："+data[i].endTimeStr+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p>天线高变更前："+data[i].height_old+"</p>"
                                +"<p>天线高变更后："+data[i].height_new+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p>天线型号变更前："+data[i].model_old+"</p>"
                                +"<p>天线型号变更后："+data[i].model_new+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p>RINEX变更前："+data[i].rinex_old+"</p>"
                                +"<p>RINEX变更后："+data[i].rinex_new+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p>SN版本变更前："+data[i].sn_old+"</p>"
                                +"<p>SN版本变更后："+data[i].sn_new+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                                +"</li>"
                                +"</ul>"
                                +"</div>"
                                +"</div>"
                    }
                    $("#ico-ts-"+divAry[2]).html(string);
                });
                ajaxRequest("typelList.action?siteNumber=BJFS&type=3","get",function(res){
                    data = res;
                    string="";
                    for(var i=0 ; i<data.length;i++){
                        string =string +"<div class='items'>"
                                +"<div class='advert'>"
                                +"<img src='../img/pcgzs/advert.jpg' />"
                                +"</div>"
                                +"<div class='main'>"
                                +"<ul>"
                                +"<li>"
                                +"<p>开始时间："+data[i].startTimeStr+"</p>"
                                +"<p>结束时间："+data[i].endTimeStr+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p>FW版本变更前："+data[i].fw_old+"</p>"
                                +"<p>FW版本变更后："+data[i].fw_new+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>问题描述："+data[i].describe+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>维修反馈："+data[i].feedback+"</p>"
                                +"</li>"
                                +"<li>"
                                +"<p style='width: 100%;'>备注："+data[i].remark+"</p>"
                                +"</li>"
                                +"</ul>"
                                +"</div>"
                                +"</div>"
                    }
                    $("#ico-gj-"+divAry[2]).html(string);
                });

                $(this).parent().parent().next(".content").show();
                //$(this).parent().parent().next(".content").show().animate({height:'100%',opacity:1},"slow");
                $(".ico").removeClass("up");
                $(this).addClass("up");
                $(timeId).css("display","block");
                if(divAry[1]=="js"){
                    var divId1=divAry[0]+"-ts-"+divAry[2];
                    var divId2=divAry[0]+"-gj-"+divAry[2];
                    $(divId1).css("display","none");
                    $(divId2).css("display","none");
                }else if(divAry[1]=="ts"){
                    var divId1=divAry[0]+"-js-"+divAry[2];
                    var divId2=divAry[0]+"-gj-"+divAry[2];
                    $(divId1).css("display","none");
                    $(divId2).css("display","none");
                }else if(divAry[1]=="gj"){
                    var divId1=divAry[0]+"-ts-"+divAry[2];
                    var divId2=divAry[0]+"-js-"+divAry[2];
                    $(divId1).css("display","none");
                    $(divId2).css("display","none");
                }
            }
        });
        
	}
  </script>
</body>
</html>

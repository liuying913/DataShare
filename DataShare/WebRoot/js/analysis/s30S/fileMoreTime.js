function jsalert() {
    var arg = arguments;
    layer.alert(arg[0], {
        shade: false
    },
    function() {
        if (typeof arg[1] == 'function') {
            arg[1]();
        }
    });

}
$(document).ready(function() {
	
	var iniStartTime = getYearOne();
	var iniEndTime = getToday();
	
	$('.startTime').val(iniStartTime); //开始时间赋值
	$('.endTime').val(iniEndTime); //结束时间赋值

	function_UserList();//设置 用户列表下拉菜单
	//====全选事件
	$(function() {
		$('body').delegate('#input-01', 'click', function() {
			var Lnput = $('#table input');
			Lnput.each(function() {
				if(Lnput.prop("checked") == false) {
					Lnput.prop("checked", true);
				} else {
					Lnput.prop("checked", false);
				}
			});
		})
	}())
	
	$('.shop-warp').click(function() {
	    var gparent = $(this).parentsUntil('.shop-warp02');
		function_name(gparent);
	})
	
	var siteType = $("#siteType").val();
	var fileType = $("#fileType").val();
	var applyId = $("#applyId").val();

	//部委列表
	function function_department(obj) {
		$.ajax({
			url: "/DataShare/getDicInfoListByType.action?dicType=dic-001",
			type: "get",
			data: "json",
		}).done(function(data) {
			var chtml='';
			for(var i = 0; i < data.length; i++) {
				chtml+='<label class="input-warp-contnor check_style check_y1"><input type="checkbox" value="'+data[i]['dicEnName']+'"><em>'+data[i]['dicCnName']+'</em></label>';					
			}
			obj.find('.input-warp00').html(chtml);
			parent.$('#iframe').css("height",$("#iframeheight").height());
		})
	}
	function_department($('.shop-warp02').eq(0));
	
	function in_array(search,array){
	    for(var i in array){
	        if(array[i]==search){
	            return true;
	        }
	    }
	    return false;
	}
	//省份列表
	function function_name(obj) {
		$.ajax({
			url: "/DataShare/getZoneSiteInfoList.action?siteType="+siteType,
			type: "get",
			data: "json",
		}).done(function(data) {
			var chtml='';
			for(var i = 0; i < data['provincesList'].length; i++) {
				chtml+='<label class="input-warp-contnor check_style check_y2"><input type="checkbox" value="'+data['provincesList'][i]['id']+'"><em>'+data['provincesList'][i]['zoneName']+'</em></label>';					
			}
			obj.find('.input-warp01').html(chtml);
			parent.$('#iframe').css("height",$("#iframeheight").height());
		})
	}
	function_name($('.shop-warp02').eq(0));	
	function csx(m){
		$('body').append('<div>'+m+'</div>')
	}

	//=====省份部委联动
	function  getdelCitys(obj){
		$.ajax({
			url: "/DataShare/getZoneSiteInfoList.action?siteType="+siteType,
			type: "get",
			data: "json",
		}).done(function(data) {
			var ahtml='';
			//选中的部委
			var bwFlag="false";
			var buwei=[];
			for(var i = 0; i < $(".input-warp00").find('input:checked').length; i++) {buwei[i] = $(".input-warp00").find('input:checked').eq(i).val();}		
			if (buwei != null && buwei != undefined && buwei != '' ) {bwFlag="true";}

			//选中的省份
			var zoneFlag="false";
			var zone=[];
			for(var i = 0; i < $(".input-warp01").find('input:checked').length; i++) {zone[i] = $(".input-warp01").find('input:checked').eq(i).val();}		
			if (zone != null && zone != undefined && zone != '' ) {zoneFlag="true";}
			for(var i=0;i<data.provincesList.length;i++){
				var provdata = data.provincesList[i].citys;
				if(! provdata.length) continue;
				for(var a=0;a<provdata.length;a++){
					var htmlStr="";
					if((window.location.href.indexOf("OneYearToJsp")>0 || window.location.href.indexOf("OneMonthToJsp")>0)){
						htmlStr='<label class="input-warp-contnor check_style check_y4"><input type="checkbox" name="Sex" value="'+provdata[a].siteNumber+'"><em>'+provdata[a].siteName+'</em></label>';
					}else{
						htmlStr='<label class="input-warp-contnor check_style check_y4"><input type="checkbox" value="'+provdata[a].siteNumber+'"><em>'+provdata[a].siteName+'</em></label>';
					}
					
					//省份 部委 都有
					if (bwFlag=="true" && zoneFlag=="true"){
						if (in_array(provdata[a].zoneCode,zone) && in_array(provdata[a].departmentCode,buwei)) {
							ahtml+=htmlStr;
						}
					}
					//只有 部委
					if (bwFlag=="true" && zoneFlag=="false"){
						if (in_array(provdata[a].departmentCode,buwei) ) {
							ahtml+=htmlStr;
						}
					}
					//只有省份
					if (bwFlag=="false" && zoneFlag=="true"){
						if (in_array(provdata[a].zoneCode,zone)) {
							ahtml+=htmlStr;
						}
					}
					//都没有
					if (bwFlag=="false" && zoneFlag=="false"){}
				}
			}
			obj.find(".input-warp02").html(ahtml);
			parent.$('#iframe').css("height",$("#iframeheight").height());
		})	
	}
	
	//=====选择省份获取台站
	$(document).on('change','.input-warp01 input',function(){
		var gparent = $(this).parentsUntil('.shop-warp02');
		getdelCitys(gparent)

	});
	
	//=====选择部委获取台站
	$(document).on('change','.input-warp00 input',function(){
		var gparent = $(this).parentsUntil('.shop-warp02');
		getdelCitys(gparent)

	});
	
	//绑定全选、
	$(document).on('change','.quanxuan',function(){
		var gparent = $(this).parentsUntil('.shop-warp02');
		if($(this).is(':checked')) {
			gparent.find('.input-warp01 input').prop('checked',true);
			getdelCitys(gparent);
			$('.fanxuan').prop('checked',false);
			$('.fanxuan').parent().removeClass('on');
		} else {
			gparent.find('.input-warp01 input').prop('checked',false);
			gparent.find(".input-warp02").empty();
		}
		gparent.find('.input-warp01 input').each(function(){
			checkstyle($(this));
		})
	})
	
	//绑定反选、
	$(document).on('change','.fanxuan',function(){
		var gparent = $(this).parentsUntil('.shop-warp02');
		$('.fanxuan').prop('checked',true);
		$('.fanxuan').parent().addClass('on');
		$('.quanxuan').prop('checked',false);
		$('.quanxuan').parent().removeClass('on');
		
		gparent.find('.input-warp01 input').each(function(){
			if($(this).is(':checked')){
				$(this).prop('checked',false);
			}else{
				$(this).prop('checked',true);
			}
			checkstyle($(this));
		})
		getdelCitys(gparent);
		//isquanxuan($('.input-warp01 .check_style'),$('.quanxuan'));
	})
	
	//全选2
	$(document).on('change','.quanxuan2',function(){
		var gparent = $(this).parentsUntil('.shop-warp02');
		if($(this).is(':checked')) {
			gparent.find('.input-warp02 input').prop('checked',true);
			$('.fanxuan2').prop('checked',false);
			$('.fanxuan2').parent().removeClass('on');
		} else {
			gparent.find('.input-warp02 input').prop('checked',false);
		}
		gparent.find('.input-warp02 input').each(function(){
			checkstyle($(this));
		})
	})
	//反选2
	$(document).on('change','.fanxuan2',function(){
		var gparent = $(this).parentsUntil('.shop-warp02');
		$('.fanxuan2').prop('checked',true);
		$('.fanxuan2').parent().addClass('on');
		$('.quanxuan2').parent().removeClass('on');
		if($(this).is(':checked')) {
			$('.quanxuan2').prop('checked',false);
			$('.quanxuan2').parent().removeClass('on');
		}
		
		gparent.find('.input-warp02 input').each(function(){
			if($(this).is(':checked')){
				$(this).prop('checked',false);
			}else{
				$(this).prop('checked',true);
			}
			checkstyle($(this));
		})
		//isquanxuan($('.input-warp02 .check_style'),$('.quanxuan2'));
	})

	
	
	function loadScript(sScriptSrc,callbackfunction){
		var oHead = document.getElementsByTagName('head')[0];  
		if(oHead)  
		{        
			var oScript = document.createElement('script');  
			oScript.setAttribute('src',sScriptSrc);  
			oScript.setAttribute('type','text/javascript');  
			var loadFunction = function()  
				{  
					if (this.readyState == 'complete' || this.readyState == 'loaded')  
					{  
						callbackfunction();   
					}  
				};  
			oScript.onreadystatechange = loadFunction;   
			oScript.onload = callbackfunction;        
			oHead.appendChild(oScript);  
		}  
	} 
	
	var dynamicLoading = {
		css: function(path){
			if(!path || path.length === 0){
				throw new Error('argument "path" is required !');
			}
			var head = document.getElementsByTagName('head')[0];
			var link = document.createElement('link');
			link.href = path;
			link.rel = 'stylesheet';
			link.type = 'text/css';
			head.appendChild(link);
		},
		js: function(path){
			if(!path || path.length === 0){
				throw new Error('argument "path" is required !');
			}
			var head = document.getElementsByTagName('head')[0];
			var script = document.createElement('script');
			script.src = path;
			script.type = 'text/javascript';
			head.appendChild(script);
		}
	}
	
	//====提交查询
	$('.shengfen-button').click(function() {
		$(".warp02-bottom").html("");			
		//var year = $('#ddl_year').val(); //开始时间
		//var ddlmonth = $('#ddl_month').val(); //结束时间		
				
		var This = $('.shengfen-button').index($(this));		
		var startTime = $('.startTime').eq(This).val(); //开始时间
		var endTime = $('.endTime').eq(This).val(); //结束时间
		
		var userId = $("#userId").val(); 
		
		$('.warp02-bottom').eq(This).slideDown(); //提交--显示
		$('.tr-01').eq(This).nextAll().remove();
		$('.ul-img').eq(This).html('');		
		
		var buwei = [];//部委
		var shengfen = []; //省份
		var taizhang = []; //台站
		
		//部委
		for(var i = 0; i < $(".input-warp00").eq(This).find('input:checked').length; i++) {buwei[i] = $(".input-warp00").eq(This).find('input:checked').eq(i).val();}
		//省份
		for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();}
		//台站
		for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();}	
        
		
			var params = "userId="+userId+"&startTime="+startTime+"&endTime="+endTime  +"&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang;
			var url="/DataShare/analysis/s30S/fileDownGroupForMap.action?";
			params+="&siteType="+siteType+"&fileType="+fileType;
			$.ajax({
				url: url+params,
				type: "get",
				data: "json",
				beforeSend:function() {
					//$(".myload").show();
					$('.zbg', window.parent.document).show();
					$('.myload', window.parent.document).show();
				},
				complete:function(){
					$('.zbg', window.parent.document).hide();
					$('.myload', window.parent.document).hide();
				}
			}).done(function(data) {
				setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);			
				var SuccessCallback=function(){
					var UArray=[];
					var cds="{";
					$.each(data,function(key,value){	
						$.each(value, function(i, item) {
							var obj = {name: item.siteName, value: parseInt(item.fileFlag)};
							UArray.push(obj);	
							cds+="\""+item.siteName+"\": ["+item.siteLng+","+item.siteLat+"],"							
						})
					});		
					require.config({
						paths: {
							echarts: '/DataShare/js/common'
						}
					});        
					require(
						[
							'echarts',
							'echarts/chart/map'
						],
						function (ec) {
							var maindiv = document.getElementById('main');
							maindiv.style.display="";
							var myChart = ec.init(maindiv); 
							var curIndx = 0;
							var mapType = [
								'china',
								'广东', '青海', '四川', '海南', '陕西', 
								'甘肃', '云南', '湖南', '湖北', '黑龙江',
								'贵州', '山东', '江西', '河南', '河北',
								'山西', '安徽', '福建', '浙江', '江苏', 
								'吉林', '辽宁', '台湾',
								'新疆', '广西', '宁夏', '内蒙古', '西藏', 
								'北京', '天津', '上海', '重庆',
								'香港', '澳门'
							];
							var option = {
								title: {
									text : '',
									x:'center',
									subtext : '地图展示',
									textStyle:{
									color:'#306bb5'
									},
								},
								tooltip : {
									/*trigger: 'item',
									formatter: '{b}'*/
									//trigger: 'item'
									formatter: '{b}'
								},
								legend: {
									orient: 'vertical',
									x:'left',
									data:['']
								},
								/*dataRange: {
									min: 0,
									max: 5000,
									color: ['maroon','purple','red','orange','yellow','lightgreen'],
									text:['高','低'],         
									calculable : true
								}, */
								
								
								
								dataRange: {
							        min : 0,
							        max : 2000,
							        splitList: [
					                    {start: 11, label: '大于10次', color: '#00F5FF'},
					                    {start: 6, end: 10, label: '6到10次', color: '#C0FF3E'},
					                    {start: 1, end: 5, label: '1到5次', color: '#7FFF00'},
					                    {start: 0, end: 0, label: '无记录', color: '#7AC5CD'}
					                    //{start: 0, end: 0, label: '5条内', color: 'black'}//,
					                    //{end: 0, label: '无记录'}
					                ],
							        color: ['red','#ff9900','#ffff00']
							    },
							    
							    roamController: {
							        show: true,
							        //x: '800',
									//y:'120',
									width:'80',
									//height:'80',
									// backgroundColor:'fff',
									borderColor:'#306bb5',
									//borderWidth:'3',
									// padding:'2',
									fillerColor:'white',
									// handleColor:'#CFCFCF',
									handleColor:'#306bb5',
									step:'10',
									z:1111,
							        mapTypeControl: {
							            'china': true
							        }
							    },
							    
							    
								toolbox: {
							        show : false,
							        orient : 'vertical',
							        x: 'right',
							        y: 'center',
							        feature : {
							            mark : {show: true},
							            dataView : {show: true, readOnly: false},
							            restore : {show: true},
							            saveAsImage : {show: true}
							        }
							    },
								series : [{
										//name: '接口数据',
										type: 'map',
										mapType: 'china',
										roam: false,//是否支持鼠标拖动
										hoverable: false,//省份区域 是否显示
										//selectedMode : 'single',//是否打开  单个省份界面
/* 											itemStyle:{
												normal:{label:{show:false}},
												emphasis:{label:{show:false}}
											}, */
										itemStyle: {
								            normal: {
								                //borderColor:'#87cefa',
								                //color: 'orange',
								                label: {
								                    show: false
								                }
								            },
											emphasis:{
												color: '#FF0000',
												label:{show:true}}
								        },

    										
										data:[],
										markPoint : {
								            symbolSize: 4,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
								            itemStyle: {
								                normal: {
								                    borderColor: '#F8F8FF',  //87cefa
													//color: 'orange',//是否颜色统一的标志
								                    borderWidth: 1, // 标注边线线宽，单位px，默认为1
								                    label: {
								                        show: false,//是否在地图上面 显示  台站中文名称
														formatter: function (params,ticket,callback) {
															return params.name;
								                       }  							
								                    }
								                },
								                emphasis: {
								                    borderColor: '#1e90ff',
								                    borderWidth: 4,
								                    label: {
								                        show: true
								                    }
								                }
								            },
								            data : UArray
								        }, 
								        geoCoord: eval('(' + "{"+cds.substring(cds.length-1,1)+"}" + ')')
								}]
							};
							
						    myChart.setOption(option); 
							var varecConfig= require('echarts/config');
							var zrEvent = require('zrender/tool/event');
							myChart.on(varecConfig.EVENT.MAP_SELECTED, function (param){
								var len = mapType.length;
								var mt = mapType[curIndx % len];
								if (mt == 'china') {
									var selected = param.selected;
									for (var i in selected) {
										if (selected[i]) {
											mt = i;
											while (len--) {
												if (mapType[len] == mt) {
													curIndx = len;
												}
											}
											break;
										}
									}
									option.tooltip.formatter = '{b}';
								}else {
									curIndx = 0;
									mt = 'china';
									option.tooltip.formatter = '{b}';
								}
								option.series[0].mapType = mt;
								option.title.subtext = mt + ' （滚轮或点击切换）';
								myChart.setOption(option, true);
							});
						}
					);
				}
				loadScript("/DataShare/js/common/echarts.js",SuccessCallback); 									
						
			})
		});
		
	
	
	//===导出
	$('.button').click(function() {
		$(".warp02-bottom").html("");			
		//var year = $('#ddl_year').val(); //开始时间
		//var ddlmonth = $('#ddl_month').val(); //结束时间		
				
		var This = $('.shengfen-button').index($(this));		
		var startTime = $('.startTime').eq(This).val(); //开始时间
		var endTime = $('.endTime').eq(This).val(); //结束时间
		
		var userId = $("#userId").val(); 
		
		$('.warp02-bottom').eq(This).slideDown(); //提交--显示
		$('.tr-01').eq(This).nextAll().remove();
		$('.ul-img').eq(This).html('');		
		
		var buwei = [];//部委
		var shengfen = []; //省份
		var taizhang = []; //台站
		
		//部委
		for(var i = 0; i < $(".input-warp00").eq(This).find('input:checked').length; i++) {buwei[i] = $(".input-warp00").eq(This).find('input:checked').eq(i).val();}
		//省份
		for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();}
		//台站
		for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();}	
		
		var params = "userId="+userId+"&startTime="+startTime+"&endTime="+endTime  +"&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang;
		var url="/DataShare/excel/analysis.action?";
		params+="&siteType="+siteType+"&fileType="+fileType;
		window.location.href=url+params;
		
	});
		
	function checkstyle(obj){
		if(obj.is(':checked')){
			obj.parent().addClass('on');
		}else{
			obj.parent().removeClass('on');
		}
	}
	$(document).on('change','.check_style input',function(){
		checkstyle($(this));
	})
	$(document).on('change','.input-warp01 input',function(){
		isquanxuan($('.input-warp01 .check_style'),$('.quanxuan'));
	})
	$(document).on('change','.input-warp02 input',function(){
		isquanxuan($('.input-warp02 .check_style'),$('.quanxuan2'));
	})
	function isquanxuan(obj,objall){
		var isall=true;
		obj.each(function(){
			if(!$(this).find('input').is(':checked')){
				isall=false;
				return false;
			}
		})
		//objall.prop('checked',isall);
		//checkstyle(objall);
	}
})
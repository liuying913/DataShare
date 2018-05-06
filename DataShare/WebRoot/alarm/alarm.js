
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


function mapChange(){
	$(".warp02-bottom").html("");		
	var This = $('.shengfen-button').index($(this));		
	var url="/DataShare/news/querycdayData.action";
	ddlmonth="";

		$.ajax({
			url: url,
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
			
				//$(".warp02-bottom").attr("id","main").css("width",'940px').css("height","500px")		
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
									text : '全国各省市自治区',
									x:'center',
									subtext : 'china'
								},
								tooltip : {
									/*trigger: 'item',
									formatter: '{b}'*/
									trigger: 'item'
								},
								legend: {
									orient: 'vertical',
									x:'left',
									data:['']
								},
								
								dataRange: {//设置图例
							        min : 0,
							        max : 2000,
							        splitList: [
										{start: 0, end: 0, label: '空缺', color: '#87CEFF'},
										{start: 1, end: 1, label: '完整', color: '#6ad76a'},
										{start: 2, end: 2, label: '补回', color: '#ffff99'},
							            {start: 3, end: 6, label: '缺失', color: '#e53341'}
					                    //{start: 0, end: 0, label: '5条内', color: 'black'}//,
					                    //{end: 0, label: '无记录'}
					                ],
							        color: ['red','#ff9900','#ffff00']
							    },
							    
							    roamController: {//地图工具栏
							        show: true,
							        //x: '800',
									//y:'120',
									width:'80',
									//height:'80',
									//backgroundColor:'fff',
									//borderColor:'green',
									//borderWidth:'3',
									//padding:'2',
									fillerColor:'white',
									handleColor:'#CFCFCF',
									step:'10',
									z:1111,
							        mapTypeControl: {
							            'china': true
							        }
							    },
								toolbox: {
							        show : true,
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
										/*itemStyle:{
												normal:{label:{show:false}},
												emphasis:{label:{show:false}}
											}, */
										itemStyle: {
								            normal: {
								                //borderColor:'#87cefa',
								                //color: 'orange',
									            show: true,
												borderWidth:40,
												//borderColor:'lightgreen',
								                label: {
								                    show: true
								                }
								            },
											emphasis:{
												color: '#FF0000',
												label:{show:true}}
								        },

    										
										data:[],
										markPoint : {
								            symbolSize: 5,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
								            itemStyle: {
								                normal: {
								                    //borderColor: '#87cefa',F8F8FF
								        			borderColor: '#F8F8FF',   //87cefa
													//color: 'orange',//是否颜色统一的标志
								                    borderWidth: 1, // 标注边线线宽，单位px，默认为1
								                    label: {
								                        show: false,//是否在地图上面 显示  台站中文名称
														formatter: function (params,ticket,callback) {
															return params.name + params.value;
								                       }  							
								                    }
								                },
								                emphasis: {
								                    borderColor: '#1e90ff',
								                    borderWidth: 5,
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
	
}

function loadScript(sScriptSrc,callbackfunction){
	var oHead = document.getElementsByTagName('head')[0];  
	if(oHead){        
		var oScript = document.createElement('script');  
		oScript.setAttribute('src',sScriptSrc);  
		oScript.setAttribute('type','text/javascript');  
		var loadFunction = function(){  
				
			};  
		oScript.onreadystatechange = loadFunction;   
		oScript.onload = callbackfunction;        
		oHead.appendChild(oScript);  
	}  
} 
			
$(document).ready(function() {
	setInterval("mapChange()",500);

	
	
	//====提交查询
	$('.shengfen-button').click(function() {
		mapChange();
	});
	


	function checkstyle(obj){
		if(obj.parent().hasClass("check_y4")){
			if(window.location.href.indexOf("s30sOneYearToJsp")>0||window.location.href.indexOf("s30sOneMonthToJsp")>0){
				if(obj.is(':checked')){
					obj.parents(".input-warp02").find(".check_style").removeClass('on');
					obj.parents(".input-warp02").find(".check_style input").attr("checked",false);
					obj.parent().addClass('on');
					obj.prop("checked",true);
				}else{
					obj.parent().removeClass('on');
				}
			}
			else{
				if(obj.is(':checked')){
					obj.parent().addClass('on');
				}else{
					obj.parent().removeClass('on');
				}
			}
		}
		else{
			if(obj.is(':checked')){
				obj.parent().addClass('on');
			}else{
				obj.parent().removeClass('on');
			}
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
		objall.prop('checked',isall);
		checkstyle(objall);
	}
})
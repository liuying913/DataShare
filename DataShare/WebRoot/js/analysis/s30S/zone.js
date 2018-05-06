$(document).ready(function() {
	
	﻿var siteType = $("#siteType").val();
	var fileType = $("#fileType").val();
	var siteTypeZone = parseInt(siteType)-1;
	function_UserList();//设置 用户列表下拉菜单
	var iniStartTime = getYearOne();
	var iniEndTime = getToday();
	
	$('.kaishi').val(iniStartTime); //开始时间赋值
	$('.jieshu').val(iniEndTime); //结束时间赋值
	
	getAjax("all",iniStartTime,iniEndTime,siteType,fileType);
	
	$(document).bind("selectstart", function() {return false});
	$('body').delegate('#input-warp01 input', 'mouseover', function() {
		$(function() {
			$('.warp02-bottom-right table tr:even').css('background', '#eeeeee')
			$('.warp02-bottom-right table tr').eq(0).css('background', '#306bb5')
		}())
	})
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
	function function_name(obj) {
		$.ajax({
			url: "/DataShare/getZoneSiteInfoList.action?siteType="+siteTypeZone,
			type: "get",
			data: "json",
		}).done(function(data) {
			var chtml='';
			for(var i = 0; i < data['provincesList'].length; i++) {
				chtml+='<div class="input-warp-contnor"><input type="checkbox" value="'+data['provincesList'][i]['id']+'"><label>'+data['provincesList'][i]['zoneName']+'</label></div>';
			}
			obj.find('.input-warp01').html(chtml);
			parent.$('#iframe').css("height",$("#iframeheight").height());
		})
	}
	function_name($('.shop-warp02').eq(0));
	function  getdelCitys(obj){
		$.ajax({
			url: "/DataShare/getZoneSiteInfoList.action?siteType="+siteTypeZone,
			type: "get",
			data: "json",
		}).done(function(data) {
			var ahtml='';
			obj.find('.input-warp01 input').each(function(){
				if($(this).is(':checked')) {
					var t=$(this).val();
					for(var i=0;i<data.provincesList.length;i++){
						if(data.provincesList[i].id==t ){
							var provdata = data.provincesList[i].citys;
							if(! provdata.length) continue;
							for(var a=0;a<provdata.length;a++){
								ahtml+='<div class="input-warp-contnor"><input type="checkbox" value="'+provdata[a].siteNumber+'"><label>'+provdata[a].siteName+'</label></div>';
							}
						}
					}
				}
			})
			obj.find(".input-warp02").html(ahtml);
			parent.$('#iframe').css("height",$("#iframeheight").height());
		})	
	}
	//全选
	$('body').delegate('.quanxuan', 'click', function() {
		var gparent = $(this).parentsUntil('.shop-warp02');
		if($(this).is(':checked')) {
			gparent.find('.input-warp01 input').prop('checked',true);
			getdelCitys(gparent);
		} else {
			gparent.find('.input-warp01 input').prop('checked',false);
			gparent.find(".input-warp02").empty();
		}
	})
	//反选
	$('body').delegate('.fanxuan', 'click', function() {
		var gparent = $(this).parentsUntil('.shop-warp02');
		gparent.find('.input-warp01 input').each(function(){
			if($(this).is(':checked')){
				$(this).prop('checked',false);
			}else{
				$(this).prop('checked',true);
			}
		})
		getdelCitys(gparent);
	})
	$('.shop-warp div').click(function() {
		$('.shop-warp div').removeClass('bianliang');
		$('.shop-warp div').css('background', 'url(/DataShare/img/img-07.png) no-repeat  865px  20px');
		$('.shop-warp02').slideUp();
		$('#iframe', parent.document).css("height",$("#iframeheight").height());
		if($(this).parent().next().css('display') == 'block') {
			$('.shop-warp02').slideUp();
		} else {
			$(this).css('background', 'url(/DataShare/img/img-07.png) no-repeat  865px  -77px');
			$(this).addClass("bianliang");
			$('.liet img').hide();
			$(this).parent().next().slideDown();
		}
	})
	$('body').delegate('.input-warp01 input', 'click', function() {
		var gparent = $(this).parentsUntil('.shop-warp02');
		getdelCitys(gparent)

	});
	$('.quanxuan2').click(function() {
		var Tish = $('.quanxuan2').index($(this));
		$(".fanxuan2:checkbox").prop("checked", false);
		if($('.quanxuan2').eq(Tish).prop("checked") == false) {
			$(".input-warp02").eq(Tish).find('input:checkbox').prop("checked", false);
		} else {
			$(".input-warp02").eq(Tish).find('input:checkbox').prop("checked", true);
		}
	})
	$('.fanxuan2').click(function() {
		var Tish = $('.fanxuan2').index($(this));
		$(".quanxuan2:checkbox").prop("checked", false);
		var Lnput = $('.input-warp02').eq(Tish).find('input');
		Lnput.each(function() {
			$(this).prop("checked", !$(this).prop("checked"));
		})
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
	
	//导出
	$(".warp-button .button").click(function(){
		
		$(".warp02-bottom").html("");
		$("#tbtable").html("");		
		var This = $('.shengfen-button').index($(this));		
		
		var kaishi = $('.kaishi').eq(This).val(); //开始时间
		var jieshu =  $('.jieshu').eq(This).val(); //结束时间
		var userId = $("#userId").val(); //用户id

		var url="/DataShare/analysisExcel/s30S/zone.action?userId="+userId+"&startTime="+kaishi+"&endTime="+jieshu+"&siteType="+siteType+"&fileType="+fileType;
		window.location.href=url;
	})  
	//查询
	$('.shengfen-button').click(function() {
		$(".warp02-bottom").html("");
		$("#tbtable").html("");		
		var This = $('.shengfen-button').index($(this));		
		
		var kaishi = $('.kaishi').eq(This).val(); //开始时间
		var jieshu =  $('.jieshu').eq(This).val(); //结束时间
		var userId = $("#userId").val(); //用户id
		//获得数据
		// console.log(333)
		getAjax(userId,kaishi,jieshu,siteType,fileType);
		
		$('.warp02-bottom').eq(This).slideDown(); //提交--显示
		$('.tr-01').eq(This).nextAll().remove();
		$('.ul-img').eq(This).html('');		
	});
	
	$('body').delegate('.ul-img .angDown', 'click', function() {
		$(this).parent().next().toggle();
	});
	
	//获得数据
	function getAjax(userId,kaishi,jieshu,siteType,fileType){
		
		url="/DataShare/analysis/s30S/zoneDownAnaly.action?userId="+userId+"&startTime="+kaishi+"&endTime="+jieshu+"&siteType="+siteType+"&fileType="+fileType;
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
			//$(".warp02-bottom").attr("id","main").css("width",'550px').css("height","500px")											
			var SuccessCallback=function(){
				console.log("");	
				var UArray=[];	
				var UArrayV=[];	
				var UArrayT=[];	
				var tyr='<table class="table01" border="0" width="100%" id="project"><tbody><tr><th>部委名称</th><th>总下载数量</th><th>平均下载数量</th></tr></tbody><tbody id="tbody">';							 		
				$.each(data,function(key,value){
					UArray.push(value.groupCnName);
					UArrayV.push(value.downNum);	
					UArrayT.push(value.avgDownNum);		
					tyr=tyr+'<tr><td>'+value.groupCnName+'</td><td>'+value.downNum+'</td><td>'+value.avgDownNum+'</td></tr> ';
				});	
				tyr=tyr+'</tbody></table>';	
				//$("#tbtable").html(tyr).show();  //暂时注释，将来可能有用
				
				//第一个图
				require.config({
					paths: {
						echarts: '/DataShare/js/common'
					}
				});        
				require(
					[
						'echarts',
						'echarts/chart/bar'
					],
					function (ec) {
						var exhibitorleft = ec.init(document.getElementById('exhibitorleft')); 								
						var optionleft = {
							color: ['#306bb5'],
							title: {x: 'center', y: 'top', text: '各地区总下载数量'},						
							tooltip: {},
							xAxis: {																		
								// splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								splitLine : {show:false,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								// splitArea:{show:false},//保留网格区域
								splitArea:{show:false},//保留网格区域
								axisLabel: {
									formatter: '{value} ',
									interval:0,//横轴信息全部显示
								    rotate:60//60度角倾斜显示
								},
								data: UArray
							},
							yAxis: {
								splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								// splitArea:{show:false}//保留网格区域
								splitArea:{show:false}//保留网格区域
								
							},
							grid :{ borderWidth :'0px' },
							series: [{
								name: '',
								type: 'bar',
								itemStyle: {
				                   normal: {
				　　　　　　　　　　　　//好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
				                       color: function(params) {
				                            // build a color map as your need.
				                            var colorList = [
				                               '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
				                               '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
				                               '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
				                               '#C1232B','#B5C334','#FCCE10','#E87C25','#27727B',
				                               '#FE8463','#9BCA63','#FAD860','#F3A43B','#60C0DD',
				                               '#D7504B','#C6E579','#F4E001','#F0805A','#26C0C0',
				                               '#C1232B','#B5C334'
				                            ];
				                            return colorList[params.dataIndex]
				                        },
				　　　　　　　　　　　　　//以下为是否显示，显示位置和显示格式的设置了
				                        label: {
				                            show: true,
				                            position: 'inside',
				                            formatter: '{c}'
				                            //formatter: '{b}\n{c}'
				                        }
				                    }
				               },
			　　　　　　　　　　
			　　　　　　　　　　barWidth:15,//设置柱的宽度，要是数据太少，柱子太宽不美观~
							   data: UArrayV
							}]
						};						
						exhibitorleft.setOption(optionleft);						
					}
				);		
				
				//第二个图
				require.config({
					paths: {
						echarts: '/DataShare/js/common'
					}
				});        
				require(
					[
						'echarts',  
						'echarts/theme/macarons',  
						'echarts/chart/bar',  
						'echarts/chart/line'  
					],
					function (ec) {
						var exhibitorright = ec.init(document.getElementById('exhibitorright')); 									
						var optionright = {
							color: ['#B5C334'],
							title: {x: 'center', y: 'top', text: '各地区平均下载数量'},	
							tooltip: {
								trigger: 'axis'
							},
							toolbox: {
								show: true,
								feature: {
									saveAsImage: {}
								}
							},
							xAxis:  {
								splitLine : {show:false,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								splitArea:{show:false},//保留网格区域
								axisLabel: {
									formatter: '{value} ',
									interval:0,//横轴信息全部显示
								    rotate:60//60度角倾斜显示
								},
								type: 'category',
								axisLine: {onZero: false},
								data: UArray
							},
							yAxis: {
								splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},//显示虚线
								splitArea:{show:false},//保留网格区域
								type: 'value',
								axisLine: {onZero: false}
							},
							grid :{ borderWidth :'0px' },
							visualMap: {
								show: false,
								dimension: 0
							},
							series: [
								{
									name:'',//提示框中的文字
									type:'line',
									smooth: true,
									data: UArrayT
								}
							]
						};
						exhibitorright.setOption(optionright);							
					}
				);
				
				
				parent.$('#iframe').css("height",$("#iframeheight").height());	
			}
			loadScript("/DataShare/js/common/echarts.js",SuccessCallback); 																		
		});
	}
	
	
})
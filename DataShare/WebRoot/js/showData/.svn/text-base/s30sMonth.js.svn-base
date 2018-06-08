﻿﻿﻿function DayNumOfMonth(Year,Month){
    Month--;
    var d = new Date(Year,Month,1);
    d.setDate(d.getDate()+32-d.getDate());
    return (32-d.getDate());
}

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
	if(window.location.href.indexOf("OneYearToJsp")>0){
		getYearSelect("#ddl_year");
	}else if(window.location.href.indexOf("OneMonthToJsp")>0){			
		getYearSelect("#ddl_year");
		getMonthSelect("#ddl_month");

	}else if(window.location.href.indexOf("DayToJsp")>0){
		var iniEndTime = getToday();
		$('.ddl_year').val(iniEndTime);
	}else if( window.location.href.indexOf("MoreMonthToJsp")>0){
		getYearSelect("#ddl_year");
		getMonthSelect("#ddl_month");
	}
	
	var SloppyJs = {}; (function() {
		SloppyJs.calendar = {
			getDateString: function(y, m) {
				var DayArray = [];
				for (var i = 0; i < 42; i++) DayArray[i] = " ";
				for (var i = 0; i < new Date(y, m, 0).getDate(); i++) DayArray[i + new Date(y, m - 1, 1).getDay()] = i + 1;
				return DayArray;
			},
			getConString: function(y, m) {
				//alert(m+"  >>m2");
				var DStr = "<table width=\"0%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr class='xq'>";
				var DateArray = ['日', '一', '二', '三', '四', '五', '六'];
				for (var i = 0; i < 7; i++) {
					if(i==0||i==6){
						DStr += "<td class='xts'>" + DateArray[i] + "</td>";
					}
					else{
						DStr += "<td>" + DateArray[i] + "</td>";
					}
				}
				DStr += "</tr>";
				for (var i = 0; i < 6; i++) {
					DStr += "<tr>";
					for (var j = 0; j < 7; j++) {
						var CS = new Date().getDate() == this.getDateString(y, m)[i * 7 + j] ? "classOver": "caletr";
						var mTwo = m;if(mTwo<=9){mTwo="0"+mTwo;}
						if (this.getDateString(y, m)[i * 7 + j] == " ") {
							DStr += "<td class='' id='dateNum"+mTwo+ this.getDateString(y, m)[i * 7 + j] + "'onclick=\"SloppyJs.calendar.alertClick(event)\" ><span>" + this.getDateString(y, m)[i * 7 + j] + "</span></td>";
						} else {
							DStr += "<td class='" + CS + "' id='dateNum" +mTwo+ this.getDateString(y, m)[i * 7 + j] + "' onclick=\"SloppyJs.calendar.alertClick(event)\"><span>" + this.getDateString(y, m)[i * 7 + j] + "</span></td>";
						}
					}
					DStr += "</tr>";
				}
				DStr += "</table>";
				return DStr;
			},
			rewriteConString: function(y, m) {
				var TArray = this.getDateString(y, m);
				var len = TArray.length;
				for (var i = 0; i < len; i++) {
					var mTwo = m;if(mTwo<=9){mTwo="0"+mTwo;}
					document.getElementById('dateNum'+mTwo + i).innerHTML = TArray[i];
					document.getElementById('dateNum'+mTwo + i).className = "";
					if (new Date().getYear() == y && new Date().getMonth() + 1 == m && new Date().getDate() == TArray[i]) {
						document.getElementById('dateNum'+mTwo + i).className = "classOver";
					}
				}
			},
			getNewTime: function(action) {
				return true;
				var YearNO = document.getElementById('currentYear').innerHTML;
				var MonthNO = document.getElementById('currentMonth').innerHTML;
				if (action == "b") {
					if (MonthNO == "1") {
						MonthNO = 13;
						YearNO = YearNO - 1;
					}
					document.getElementById('currentMonth').innerHTML = MonthNO - 1;
					document.getElementById('currentYear').innerHTML = YearNO;
					this.rewriteConString(YearNO, MonthNO - 1);
				}
				if (action == "n") {
					if (MonthNO == "12") {
						MonthNO = 0;
						YearNO = YearNO - ( - 1);
					}
					document.getElementById('currentYear').innerHTML = YearNO;
					document.getElementById('currentMonth').innerHTML = MonthNO - ( - 1);
					this.rewriteConString(YearNO, MonthNO - ( - 1));
				}
			},
			writeDivString: function() {
				var _element = document.getElementById(arguments[0]);
				_element.style.position = "relative";
				var _value = _element.innerHTML;
				//year
				if (arguments[1] == "yeartype") {
					var _containerid = "yeardiv";
					if (document.getElementById(_containerid)) {
						var _c = document.getElementById(_containerid);
						document.body.removeChild(_c);
					}
					if (document.getElementById("monthdiv")) {
						var _cc = document.getElementById("monthdiv");
						document.body.removeChild(_cc);
					}
					var _container = document.createElement("div");
					_container.setAttribute("id", _containerid);
					_container.className = "yearContainer";
					var _ul = document.createElement("ul");
					for (var i = 0; i < 15; i++) {
						var _li = document.createElement("li");
						var _text = document.createTextNode(_value - 7 + i);
						_li.appendChild(_text);
						_ul.appendChild(_li);
					}
					_container.appendChild(_ul);
					_container.style.top = _element.offsetTop + 20 + "px";
					_container.style.left = _element.offsetLeft + "px";
					document.body.appendChild(_container);
					var _ali = document.getElementById(_containerid).getElementsByTagName("li");
					for (var j = 0; j < _ali.length; j++) {
						_ali[j].onmouseover = function() {
							this.style.background = "#ff0000";
							this.style.color = "#FFFFFF";
							this.style.cursor = "pointer";
						};
						_ali[j].onmouseout = function() {
							this.style.background = "#ffffff";
							this.style.color = "#000000";
						};
						_ali[j].onclick = function() {
							document.getElementById("yeardiv").style.display = "none";
							_element.innerHTML = this.innerHTML;
							var _alii = document.getElementById(_containerid).getElementsByTagName("li");
							for (var k = 0; k < _alii.length; k++) {
								_ul.removeChild(_alii[k]);
							}
							_container.removeChild(_ul);
							document.body.removeChild(_container);
							_element.style.position = "";
							var _y = document.getElementById('currentYear').innerHTML;
							var _m = document.getElementById('currentMonth').innerHTML;
							SloppyJs.calendar.rewriteConString(_y, _m);
						};
					}
					_container.onmouseout = function() {
						this.style.display = "none";
						_element.style.position = "";
					};
					_container.onmouseover = function() {
						this.style.display = "";
					};
				}
				//month
				if (arguments[1] == "monthtype") {
					var _containerid = "monthdiv";
					if (document.getElementById(_containerid)) {
						var _c = document.getElementById(_containerid);
						document.body.removeChild(_c);
					}
					if (document.getElementById("yeardiv")) {
						var _cc = document.getElementById("yeardiv");
						document.body.removeChild(_cc);
					}
					var _container = document.createElement("div");
					_container.setAttribute("id", _containerid);
					_container.className = "monthContainer";
					var _ul = document.createElement("ul");
					for (var i = 0; i < 12; i++) {
						var _li = document.createElement("li");
						var _text = document.createTextNode(i + 1);
						_li.appendChild(_text);
						_ul.appendChild(_li);
					}
					_container.appendChild(_ul);
					_container.style.top = _element.offsetTop + 20 + "px";
					_container.style.left = _element.offsetLeft + "px";
					document.body.appendChild(_container);
					var _ali = document.getElementById(_containerid).getElementsByTagName("li");
					for (var l = 0; l < _ali.length; l++) {
						_ali[l].onmouseover = function() {
							this.style.background = "#ff0000";
							this.style.color = "#FFFFFF";
							this.style.cursor = "pointer";
						};
						_ali[l].onmouseout = function() {
							this.style.background = "#ffffff";
							this.style.color = "#000000";
						};
						_ali[l].onclick = function() {
							document.getElementById(_containerid).style.display = "none";
							_element.innerHTML = this.innerHTML;
							var _alii = document.getElementById(_containerid).getElementsByTagName("li");
							for (var k = 0; k < _alii.length; k++) {
								_ul.removeChild(_alii[k]);
							}
							_container.removeChild(_ul);
							document.body.removeChild(_container);
							_element.style.position = "";
							var _y = document.getElementById('currentYear').innerHTML;
							var _m = document.getElementById('currentMonth').innerHTML;
							SloppyJs.calendar.rewriteConString(_y, _m);
						};
					}
					_container.onmouseout = function() {
						this.style.display = "none";
						_element.style.position = "";
					};
					_container.onmouseover = function() {
						this.style.display = "";
					};
				}
			},
			alertClick: function(e) {
				var e = e || event;
				var targets = e.target || event.srcElement;
			}
		};
	})();


	
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
			//beforeSend:function() {
					//$(".myload").show();
					//$('.zbg', window.parent.document).show();
					//$('.myload', window.parent.document).show();
				//},
				//complete:function(){
					//$('.zbg', window.parent.document).hide();
					//$('.myload', window.parent.document).hide();
				//}
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
			//beforeSend:function() {
					//$(".myload").show();
					//$('.zbg', window.parent.document).show();
					//$('.myload', window.parent.document).show();
				//},
				//complete:function(){
					//$('.zbg', window.parent.document).hide();
					//$('.myload', window.parent.document).hide();
				//}
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
		if(oHead){        
			var oScript = document.createElement('script');  
			oScript.setAttribute('src',sScriptSrc);  
			oScript.setAttribute('type','text/javascript');  
			var loadFunction = function(){
				if (this.readyState == 'complete' || this.readyState == 'loaded'){  
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
	
	
	
	
	
	
	//柱状图
	function get30sOneMonthData(url,year,ddlmonth){
		$.ajax({
			url: url,
			type: "get",
			data: "json",
		}).done(function(data) {
			//$(".warp02-bottom").attr("id","main").css("width",'550px').css("height","500px")
			var yearMonth = "";
			if(ddlmonth.length==1){ddlmonth = "0"+ddlmonth;}
			yearMonth = year+"-"+ddlmonth+"-";
			
			var SuccessCallback=function(){
				//$("#tipoi").show();
				//console.log("");	
				var UArray=[];	
				var UArrayV=[];	
				
				var UArrayMp1=[];	
				var UArrayMp2=[];	
				
				var UArrayT=[];	
				var tyr='<table class="table01" border="0" width="100%" id="project"><tbody><tr><th>日期</th><th>历元数量</th><th>MP1</th><th>MP2</th><th>状态</th></tr></tbody><tbody id="tbody">';							 		
				
				$.each(data,function(key,value){
					
					//设置日期
					if(value.dayNumber.length==1){
						value.dayNumber="0"+value.dayNumber;
					}else{
						value.dayNumber=value.dayNumber;
					}
					UArray.push(value.dayNumber);
					
					UArrayV.push(value.ephemNumber);
					UArrayMp1.push(value.mp1);
					UArrayMp2.push(value.mp2);
					
					UArrayT.push(value.fileFlag);		
					var status = "";
					if(value.fileFlag==1){status = "完整";}
					else if(value.fileFlag==2){status = "补回";}
					else if(value.fileFlag==0){status = "";}
					else{status = "缺失";}
				
					if( key%2 ==0 ){
                      tyr=tyr+'<tr class="even"><td>'+yearMonth+value.dayNumber+'</td><td>'+value.ephemNumber+'</td><td>'+value.mp1+'</td><td>'+value.mp2+'</td><td>'+status+'</td></tr> ';
					}else{
						tyr=tyr+'<tr><td>'+yearMonth+value.dayNumber+'</td><td>'+value.ephemNumber+'</td><td>'+value.mp1+'</td><td>'+value.mp2+'</td><td>'+status+'</td></tr> ';
					}
				
				});	
				tyr=tyr+'</tbody></table>';	
				$("#tbtable").html(tyr).show();  //暂时注释，将来可能有用
				
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
						var liyuan = ec.init(document.getElementById('liyuan')); 								
						var optionleft = {
							color: ['#7FFF00'],
							title: {x: 'center', y: 'top', text: '每日历元数量'},						
							tooltip: {},
							xAxis: {	
								name:'日期',
								splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								splitArea:{show:false},//保留网格区域
								axisLabel: {
									formatter: '{value} ',
									interval:0,//横轴信息全部显示
								    rotate:35//60度角倾斜显示
								},
								data: UArray
							},
							yAxis: {
								//type : 'value',
				                name:'历元',
								splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								splitArea:{show:false}//保留网格区域
							},
							grid :{ borderWidth :'5px' },
							series: [{
								name: '',
								type: 'line',
								
								 //symbol:'none',  //这句就是去掉点的  
						         //smooth:true,  //这句就是让曲线变平滑的  
						           
								 itemStyle: {
				                   normal: {
				　　　　　　　　　　　　//好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
				                       color: function(params) {
				                            // build a color map as your need.
				                            var colorList = [
				                               '#00FFFF','#00FFFF','#00FFFF','#00FFFF','#00FFFF',
				                               '#00FFFF','#00FFFF','#00FFFF','#00FFFF','#00FFFF',
				                               '#00FFFF','#00FFFF','#00FFFF','#00FFFF','#00FFFF',
				                               '#00FFFF','#00FFFF','#00FFFF','#00FFFF','#00FFFF',
				                               '#00FFFF','#00FFFF','#00FFFF','#00FFFF','#00FFFF',
				                               '#00FFFF','#00FFFF','#00FFFF','#00FFFF','#00FFFF',
				                               '#00FFFF','#00FFFF'
				                            ];
				                            return colorList[params.dataIndex]
				                        },
				　　　　　　　　　　　　　    //以下为是否显示，显示位置和显示格式的设置了
				                        label: {
				                            show: true,
				                            position: 'inside',
				                            //position: 'top',//顶部显示
				                            formatter: '{c}'
				                            //formatter: '{b}\n{c}'
				                        }
				                    }
				                 },
			　　　　　　　　　　
			　　　　　　　　　        　barWidth:15,//设置柱的宽度，要是数据太少，柱子太宽不美观~
							     data: UArrayV
							}]
						};						
						liyuan.setOption(optionleft);						
					}
				);		
				
				
				
				
				
				//Mp1
				require.config({paths: {echarts: '/DataShare/js/common'}});        
				require(
					[
						'echarts',  
						'echarts/theme/macarons',  
						'echarts/chart/bar',  
						'echarts/chart/line'  
					],
					function (ec) {
						var mp1 = ec.init(document.getElementById('mp1')); 								
						var optionleft = {
							color: ['#FCCE10'],
							title: {x: 'center', y: 'top', text: 'MP1'},						
							tooltip: {},
							xAxis: {	
								name:'日期',
								splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								splitArea:{show:false},//保留网格区域
								axisLabel: {
									formatter: '{value} ',
									interval:0,//横轴信息全部显示
								    rotate:35//60度角倾斜显示
								},
								data: UArray
							},
							yAxis: {
								//type : 'value',
				                name:'MP1',
								splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								splitArea:{show:false}//保留网格区域
							},
							grid :{ borderWidth :'0px' },
							series: [{
								name: '',
								type: 'line',
								//symbol:'none',//这句就是去掉点的
								//smooth:true,  //这句就是让曲线变平滑的 
								itemStyle: {
				                   normal: {
				　　　　　　　　　　　　//好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
				                       color: function(params) {
				                            // build a color map as your need.
				                            var colorList = [
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00'
				                            ];
				                            return colorList[params.dataIndex]
				                        },
				　　　　　　　　　　　　　    //以下为是否显示，显示位置和显示格式的设置了
				                        label: {
				                            show: true,
				                            position: 'inside',
				                            //position: 'top',//顶部显示
				                            formatter: '{c}'
				                            //formatter: '{b}\n{c}'
				                        }
				                    }
				               },
			　　　　　　　　　　
			　　　　　　　　　　barWidth:15,//设置柱的宽度，要是数据太少，柱子太宽不美观~
							   data: UArrayMp1
							}]
						};						
						mp1.setOption(optionleft);						
					}
				);	
				
				
				
				
				
				//Mp2
				require.config({paths: {echarts: '/DataShare/js/common'}});        
				require(
					[
						'echarts',  
						'echarts/theme/macarons',  
						'echarts/chart/bar',  
						'echarts/chart/line'  
					],
					function (ec) {
						var mp2 = ec.init(document.getElementById('mp2')); 								
						var optionleft = {
							color: ['#00FFFF'],
							title: {x: 'center', y: 'top', text: 'MP2'},						
							tooltip: {},
							xAxis: {	
								name:'日期',
								splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								splitArea:{show:false},//保留网格区域
								axisLabel: {
									formatter: '{value} ',
									interval:0,//横轴信息全部显示
								    rotate:35//60度角倾斜显示
								},
								data: UArray
							},
							yAxis: {
								//type : 'value',
				                name:'MP2',
								splitLine : {show:true,lineStyle: {color: '#483d8b',type: 'dashed',width: 1}},
								splitArea:{show:false}//保留网格区域
							},
							grid :{ borderWidth :'0px' },
							series: [{
								name: '',
								type: 'line',
								itemStyle: {
				                   normal: {
				　　　　　　　　　　　　//好，这里就是重头戏了，定义一个list，然后根据所以取得不同的值，这样就实现了，
				                       color: function(params) {
				                            // build a color map as your need.
				                            var colorList = [
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00','#7FFF00','#7FFF00','#7FFF00',
												'#7FFF00','#7FFF00'
				                            ];
				                            return colorList[params.dataIndex]
				                        },
				　　　　　　　　　　　　　    //以下为是否显示，显示位置和显示格式的设置了
				                        label: {
				                            show: true,
				                            position: 'inside',
				                            //position: 'top',//顶部显示
				                            formatter: '{c}'
				                            //formatter: '{b}\n{c}'
				                        }
				                    }
				               },
			　　　　　　　　　　
			　　　　　　　　　　barWidth:15,//设置柱的宽度，要是数据太少，柱子太宽不美观~
							   data: UArrayMp2
							}]
						};						
						mp2.setOption(optionleft);						
					}
				);	
				
				
				
				parent.$('#iframe').css("height",$("#iframeheight").height());	
			}
			loadScript("/DataShare/js/common/echarts.js",SuccessCallback); 																		
		});
	}
	
	
	//====提交查询
	$('.shengfen-button').click(function() {
		$(".warp02-bottom").html("");		
		var This = $('.shengfen-button').index($(this));		
		var year = $('#ddl_year').val(); //开始时间
		var ddlmonth = $('#ddl_month').val(); //结束时间	
		$('.warp02-bottom').eq(This).slideDown(); //提交--显示
		$('.tr-01').eq(This).nextAll().remove();
		$('.ul-img').eq(This).html('');		
		var dongwei = $('.dongwei').eq(This).val(); //东纬度
		var xiwei = $('.xiwei').eq(This).val(); //西纬度
		var dongjin = $('.dongjin').eq(This).val(); //东经度
		var xijin = $('.xijin').eq(This).val(); //西经度		
		var Datetype = $('.Datetype').eq(This).val(); //数据第几块
		
		var buwei = [];//部委
		var shengfen = []; //省份
		var taizhang = []; //台站
		
		//部委
		for(var i = 0; i < $(".input-warp00").eq(This).find('input:checked').length; i++) {buwei[i] = $(".input-warp00").eq(This).find('input:checked').eq(i).val();}
		//省份
		for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();}
		//台站
		for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();}	
		
		if((window.location.href.indexOf("OneYearToJsp")>0 || window.location.href.indexOf("OneMonthToJsp")>0) && taizhang.length!=1){			
			jsalert("请选择一个单站",function (){layer.closeAll();});
			return false;			
		}
		var params = "";
		var ui="";
		var url="/DataShare/showData/s30s260MonthData.action?";
		if(window.location.href.indexOf("OneYearToJsp")>0){
			ddlmonth="";
		}else if(window.location.href.indexOf("OneMonthToJsp")>0){
			url="/DataShare/showData/s30sOneMonthData.action?";
			params = "year="+year+"&ddlmonth="+ddlmonth  +"&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang+"&fileType="+fileType+"&siteType="+siteType;
			get30sOneMonthData(url+params,year,ddlmonth);
			return;
		}else if(window.location.href.indexOf("MoreMonthToJsp")>0){
			ui=DayNumOfMonth(year,ddlmonth);
		}else if(window.location.href.indexOf("DayToJsp")>0){
			url="/DataShare/showData/querycdayData.action?";
			ddlmonth="";
		}
		params = "year="+year+"&ddlmonth="+ddlmonth  +"&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang;
		params+="&fileType="+fileType+"&siteType="+siteType;
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
				
				if(window.location.href.indexOf("DayToJsp")>0){
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
									
									dataRange: {//设置图例
								        min : 0,
								        max : 2000,
								        splitList: [
											{start: 0, end: 0, label: '空缺', color: '#008B00'},
											{start: 1, end: 2, label: '完整', color: '#7FFF00'},
								            {start: 3, end: 6, label: '缺失', color: '#e53341'}
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
											hoverable: false,//省份区域 是否显示  省份背景色
											selectedMode : 'single',//是否打开  单个省份界面
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
									            symbolSize: 4,       // 标注大小，半宽（半径）参数，当图形为方向或菱形则总宽度为symbolSize * 2
									            itemStyle: {
									                normal: {
									                    //borderColor: '#87cefa',F8F8FF
									        			borderColor: '#F8F8FF',   //87cefa
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
				}
				
				if(window.location.href.indexOf("OneYearToJsp")>0){
					$("#tipoi").show();
					var tyd="<style type='text/css'>";
					tyd+=".calendar{margin:0 0.5%;float: left;width:24%;font-size:11px; }";
					tyd+=".calendar table{ border-collapse:collapse;margin-bottom: 14px;}";
					tyd+=".calendar table td{ font-size: x-small;height:30px; width:61px; border:0px solid #ff6600; text-align:center;}";
					tyd+=".calendar .ttyu table .threetd{ width:62px;}";
					tyd+=".calendar .ttyu table .twotd{ width:243px;    font-weight: 700;}";
					tyd+=".calendar .ttyu table td{ border-bottom:0px;}";
					tyd+=".yearContainer{background:#ffffff;border:1px solid #ff6600;position:absolute;width:62px;}";
					tyd+=".yearContainer ul{list-style:none;margin:0px;padding:0px;}";
					tyd+=".yearContainer ul li{height:18px;line-height:18px;display:block;text-align:center;}";
					tyd+=".monthContainer{background:#ffffff;border:1px solid #ff6600;position:absolute;width:41px;}";
					tyd+=".monthContainer ul{list-style:none;margin:0px;padding:0px;}";
					tyd+=".monthContainer ul li{height:18px;line-height:18px;display:block;text-align:center;}";
					tyd+="a{ text-decoration:none; color:#990000;}";
					tyd+="</style>";
					$(".warp02-bottom").html(tyd);	
					setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);		
					var year=$("#ddl_year").val();		
					for(j=1;j<13;j++){
						var titleTable = "<div class=\"calendar\" id=\"calendar"+j+"\"><div class=\"ttyu calendarTitle"+j+"\">";
						titleTable += "<table width=\"0%\" border=\"0\" cellspacing=\"0\" class='tstable' cellpadding=\"0\"><tr><td style='display:none;'>";
						titleTable += "<a href=\"javascript:void(0)\" onclick=\"SloppyJs.calendar.getNewTime('b')\"  onfocus=\"this.blur()\" title=''><img src='/DataShare/img/pcgzs/rq01.png'></a></td>";
						titleTable += "<td class=\"threetd\" id=\"currentYear"+j+"\" onclick=\"SloppyJs.calendar.writeDivString('currentYear','yeartype')\"></td>";
						titleTable += "<td class=\"twotd\" id=\"currentMonth"+j+"\" onclick=\"SloppyJs.calendar.writeDivString('currentMonth','monthtype')\"></td>";
						titleTable += "<td style='display:none;'><a href=\"javascript:void(0)\" onclick=\"SloppyJs.calendar.getNewTime('n')\" onfocus=\"this.blur()\" title=''><img src='/DataShare/img/pcgzs/rq02.png'></a></td></a></td></tr></table></div>";
	
						$(".warp02-bottom").append(titleTable);
						var _todayMonth = document.getElementById('currentMonth'+j).innerHTML = j;
						document.getElementById('currentMonth'+j).innerHTML =j+"月";						
						var _calendarStr = SloppyJs.calendar.getConString(year, _todayMonth);
						
						$(".calendarTitle"+_todayMonth).append(_calendarStr);	
						$(".calendarTitle"+_todayMonth).append("</div>");
						//alert($(".calendarTitle"+_todayMonth).parent().html()+"//"+".calendarTitle"+_todayMonth);						
					}
					$.each(data,function(key,value){
						var _todayMonth=1;
						var ui=DayNumOfMonth(year,_todayMonth);
						var uty=0;
						//document.getElementById('currentMonth'+_todayMonth).innerHTML=key+"["+_todayMonth+"月]";
						//document.getElementById('currentMonth'+_todayMonth).innerHTML=year+"年"+_todayMonth+"月";
						$.each(value, function(i, item) {
							i=i-1000;
							if(i>ui){
								_todayMonth+=1;
								uty=ui;
								ui+=DayNumOfMonth(year,_todayMonth);
								//document.getElementById('currentMonth'+_todayMonth).innerHTML=key+"["+_todayMonth+"月]";
								//document.getElementById('currentMonth'+_todayMonth).innerHTML=year+"年"+_todayMonth+"月";
							}
							//if(i==340){
								//alert(_todayMonth+"  "+(i-uty));
							//}
							if(_todayMonth==12){
								//alert("#dateNum"+_todayMonth+(i-uty));
							}
							
							var monthTwo = _todayMonth;
							if(monthTwo<=9){monthTwo="0"+monthTwo;}
							if(item.fileFlag=="0"){								
								//$("#dateNum"+monthTwo+(i-uty)).addClass("green");	
							}else if(item.fileFlag=="1"){								
								$("#dateNum"+monthTwo+(i-uty)).addClass("green");	
							}else if(item.fileFlag=="2"){								
								$("#dateNum"+monthTwo+(i-uty)).addClass("green");	
							}else{								
								$("#dateNum"+monthTwo+(i-uty)).addClass("red");	
							}													
						});
					});	
				}	
				
				if(window.location.href.indexOf("MoreMonthToJsp")>0){
					$("#tipoi").show();
					//标题
					var hy="<table class='table01' border='1' width='100%' style=''><tr>";
					hy+='<th>地区</th><th>台站</th>';
					for(j=1;j<=ui;j++){if(j<=9){hy+='<th>0'+j+'</th>';}else{hy+='<th>'+j+'</th>';}}
					hy+='<th style="width:35px;">完整率</th>';
					
					hy+="<tbody id='tbody'>";
					$.each(data,function(key,value){
						hy+="<tr><td class='ts01'>" + key.split("_")[0].split("&")[1] + "</td> <td>" + key.split("_")[1] + "</td>";
							var ty="0";
							var gooNumber=0;
							var dayNumber=0;
							$.each(value, function(i, item) {
								ty="1";
								//if(item.fileFlag=="1"){hy+="<td class='yellow'>"+item.fileFlag+"</td>";}else{hy+="<td>"+item.fileFlag+"</td>";}
								if(item.fileFlag=="0"){
									hy+="<td></td>";
								}else if(item.fileFlag=="1"){
									dayNumber = parseInt(dayNumber)+1;
									gooNumber = parseInt(gooNumber)+1;
									hy+="<td class='green'></td>";
								}
								else if(item.fileFlag=="2"){
									dayNumber = parseInt(dayNumber)+1;
									gooNumber = parseInt(gooNumber)+1;
									hy+="<td class='green'></td>";
								}else if(item.fileFlag=="3"){
									dayNumber = parseInt(dayNumber)+1;
									hy+="<td class='red'></td>";
								}else if(item.fileFlag=="4"){
									dayNumber = parseInt(dayNumber)+1;
									hy+="<td class='red'></td>";
								}else{
									dayNumber = parseInt(dayNumber)+1;
									hy+="<td class='red'></td>";
								}
								if(i=="10"+ui){hy+="<td>"+(gooNumber/dayNumber*100).toFixed(2)+"%</td>";}
								return true;
							});							
						hy+="</tr>";
					});	
					hy+="</tbody>";
					$(".warp02-bottom").html(hy);		
					setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);
				}	
			})
		});
	
	
	
	//===导出
	$('.button').click(function() {
		
		/*$(".warp02-bottom").html("");	*/	
		var This = $('.shengfen-button').index($(this));		
		var year = $('#ddl_year').val(); //开始时间
		var ddlmonth = $('#ddl_month').val(); //结束时间	
		$('.warp02-bottom').eq(This).slideDown(); //提交--显示
		$('.tr-01').eq(This).nextAll().remove();
		$('.ul-img').eq(This).html('');		
		var dongwei = $('.dongwei').eq(This).val(); //东纬度
		var xiwei = $('.xiwei').eq(This).val(); //西纬度
		var dongjin = $('.dongjin').eq(This).val(); //东经度
		var xijin = $('.xijin').eq(This).val(); //西经度		
		var Datetype = $('.Datetype').eq(This).val(); //数据第几块
		
		var buwei = [];//部委
		var shengfen = []; //省份
		var taizhang = []; //台站
		
		//部委
		for(var i = 0; i < $(".input-warp00").eq(This).find('input:checked').length; i++) {buwei[i] = $(".input-warp00").eq(This).find('input:checked').eq(i).val();}
		//省份
		for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();}
		//台站
		for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();}	
        
		if((window.location.href.indexOf("OneYearToJsp")>0 || window.location.href.indexOf("OneMonthToJsp")>0) && taizhang.length!=1){			
			jsalert("请选择一个单站",function (){layer.closeAll();});
			return false;			
		}
		
		var params = "";
		var url="/DataShare/excel/dataShare.action?";
		if(window.location.href.indexOf("OneYearToJsp")>0){
			ddlmonth="";
		}else if(window.location.href.indexOf("OneMonthToJsp")>0 || window.location.href.indexOf("MoreMonthToJsp")>0){			
			
		}else if(window.location.href.indexOf("DayToJsp")>0){
			url="/DataShare/excel/dataShareDayExcel.action?";
			ddlmonth="";
		}
		params = "year="+year+"&ddlmonth="+ddlmonth  +"&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang;
		params+="&fileType="+fileType+"&siteType="+siteType;
		window.location.href=url+params;
		
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
	}
})
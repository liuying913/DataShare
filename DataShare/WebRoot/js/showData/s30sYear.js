function DayNumOfMonth(Year,Month){
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
	
	getYearSelect("#ddl_year");

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
		var This = $('.shengfen-button').index($(this));		
		var kaishi = $('#ddl_year').eq(This).val(); //开始时间
		var jieshu = ""; //结束时间			
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
		var url="/DataShare/showData/s30s260MonthData.action?";
		params = "year="+kaishi+"&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang;
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
		}).done(function(data){
			$("#tipoi").show();
			var hy="<table class='table01' border='0' width='100%'><tbody><tr>";
			hy+='<th style="width:35px;">地区</th><th style="width:50px;">台站</th>';
			var dui=365;
			var d;
			if((kaishi % 4 == 0) && (kaishi % 100 != 0 || kaishi % 400 == 0)){dui=366;}
			var dayNumber = getDayNumber(kaishi,dui);
			for(j=1;j<=12;j++){
				if(j==1||j==3||j==5||j==7||j==8||j==10||j==12){
					d=31;
				}else if(j==2){
					if(dui==365){
						d=28;
					}else{
						d=29;	
					}
				}else{
					d=30;	
				}
				if(j<=9){
					hy+='<th colspan='+d+' data-i="'+j+'">0'+j+'</th>';
				}else{
					hy+='<th colspan='+d+' data-i="'+j+'">'+j+'</th>';
				}
			}
			hy+='<th style="width:30px;">完整率</th>';
			
			hy+="<tbody id='tbody'>";
			$.each(data,function(key,value){
				hy+="<tr><td>" + key.split("_")[0].split("&")[1] + "</td> <td>" + key.split("_")[1] + "</td>";
				var gooNumber=0;
				$.each(value, function(i, item) {
					if(item.fileFlag=="0"){
						hy+="<td></td>";
					}else if(item.fileFlag=="1"){
						hy+="<td class='green'></td>";
						gooNumber = parseInt(gooNumber)+1;
					}else if(item.fileFlag=="2"){
						gooNumber = parseInt(gooNumber)+1;
						hy+="<td class='green'></td>";
					}else{
						hy+="<td class='red'></td>";		
					}
					
					if(i=="1"+dui){hy+="<td>"+(gooNumber/dayNumber*100).toFixed(2)+"%</td>";}
					return true;
				});
				hy+="</tr>";
			});	
			hy+="</tbody>";
			$(".warp02-bottom").html(hy);																	
			setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);				
		})
	});
	
	
	function getDayNumber(kaishi,dui){
		var myDate = new Date();
		var nowYear = myDate.getFullYear(); //获取完整的年份(4)
		if(kaishi.indexOf(nowYear)>-1){//选择的是今年
			var firstDay = new Date(myDate.getFullYear(),0,1);
			//计算当前时间与本年第一天的时差(返回一串数值，代表两个日期相差的毫秒数)
			var dateDiff = myDate - firstDay;
			//一天的毫秒数
			var msPerDay = 1000 * 60 * 60 * 24;
			//计算天数
			var diffDays = Math.ceil(dateDiff/ msPerDay)-1;
			return diffDays;
		}else{
			return dui;
		}
	}
	
	//===导出
	$('.button').click(function() {
		
		/*$(".warp02-bottom").html("");*/		
		var This = $('.shengfen-button').index($(this));		
		var kaishi = $('#ddl_year').eq(This).val(); //开始时间
		var jieshu = ""; //结束时间			
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

		var params = "";
		var url="/DataShare/excel/dataShare.action?";
		params = "year="+kaishi+"&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang;
		params+="&fileType="+fileType+"&siteType="+siteType;
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
	}
})
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

	//部委列表
	function function_department(obj) {
		$.ajax({
			url: "/DataShare/getDicInfoListByType.action?dicType=dic-001",
			type: "get",
			data: "json",
		}).done(function(data) {
			var chtml='';
			for(var i = 0; i < data.length; i++) {
				chtml+='<div class="input-warp-contnor check_style check_y1"><input type="checkbox" value="'+data[i]['dicEnName']+'"><label>'+data[i]['dicCnName']+'</label></div>';					
			}
			obj.find('.input-warp00').html(chtml);
			parent.$('#iframe').css("height",$("#iframeheight").height());
		})
	}
	function_department($('.shop-warp02').eq(0));
	
	
	var earthQuakeId = $("#earthQuakeId").val();//alert(earthQuakeId);
	//省份列表
	function function_name(obj) {
		$.ajax({
			url: "/DataShare/getEarthquakeSiteList.action?earthQuakeId="+earthQuakeId,
			type: "get",
			data: "json",
		}).done(function(data) {
			var chtml='';
			for(var i = 0; i < data['provincesList'].length; i++) {
				chtml+='<div class="input-warp-contnor  check_style check_y2"><input type="checkbox" value="'+data['provincesList'][i]['id']+'"><label>'+data['provincesList'][i]['zoneName']+'</label></div>';					
			}
			obj.find('.input-warp01').html(chtml);
			parent.$('#iframe').css("height",$("#iframeheight").height());
		})
	}
	function_name($('.shop-warp02').eq(0));	
	
	//=====省份部委联动
	function  getdelCitys(obj){
		$.ajax({
			url: "/DataShare/getEarthquakeSiteList.action?earthQuakeId="+earthQuakeId,
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
						htmlStr='<div class="input-warp-contnor check_style check_y4"><input type="radio" name="Sex" value="'+provdata[a].siteNumber+'"><label>'+provdata[a].siteName+'</label></div>';
					}else{
						htmlStr='<div class="input-warp-contnor check_style check_y4"><input type="checkbox" value="'+provdata[a].siteNumber+'"><label>'+provdata[a].siteName+'</label></div>';
					}
					
					//省份 部委 都有
					if (bwFlag=="true" && zoneFlag=="true"){
						if (zone.indexOf(provdata[a].zoneCode)>-1 && buwei.indexOf(provdata[a].departmentCode)>-1 ) {
							ahtml+=htmlStr;
						}
					}
					//只有 部委
					if (bwFlag=="true" && zoneFlag=="false"){
						if (buwei.indexOf(provdata[a].departmentCode)>-1 ) {
							ahtml+=htmlStr;
						}
					}
					//只有省份
					if (bwFlag=="false" && zoneFlag=="true"){
						if (zone.indexOf(provdata[a].zoneCode)>-1) {
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
	$('body').delegate('.input-warp01 input', 'click', function() {
		var gparent = $(this).parentsUntil('.shop-warp02');
		getdelCitys(gparent)

	});
	
	//=====选择部委获取台站
	$('body').delegate('.input-warp00 input', 'click', function() {
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

	

	
		
	//启动整理
	$('#earthQuakeSupplyData').click(function() {
		var buwei = [];//部委
		var shengfen = []; //省份
		var taizhang = []; //台站
		var HzType = [];//1赫兹  50赫兹
		var This = $('#earthQuakeSupplyData').index($(this));		
		//部委
		for(var i = 0; i < $(".input-warp00").eq(This).find('input:checked').length; i++) {buwei[i] = $(".input-warp00").eq(This).find('input:checked').eq(i).val();}
		//省份
		for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();}
		//台站
		for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();}	
		//1赫兹  50赫兹
		for(var i = 0; i < $(".input-warp03").eq(This).find('input:checked').length; i++) {HzType[i] = $(".input-warp03").eq(This).find('input:checked').eq(i).val();}	

		if((window.location.href.indexOf("OneYearToJsp")>0 || window.location.href.indexOf("OneMonthToJsp")>0) && taizhang.length!=1){			
			jsalert("请选择一个单站",function (){layer.closeAll();});
			return false;			
		}
	
		var params = "";
		var url="/DataShare/earthQuakeDataReduction/earthQuakeSupplyData.action?earthQuakeId="+earthQuakeId;
		params = "&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang+"&hzType="+HzType;
		$.ajax({
			url: url+params,
			type: "get",
			data: "json",
		}).done(function(data) {
			var dataApplyFlag = data['date'][0]['code'];
			if(dataApplyFlag=='true'){//可以执行
				init("开始执行",0);
				$(".confirm",window.parent.document).click( function(){
					
                })
			}else{
				init("有未完成的任务，请稍后再试！",2);
				$(".confirm",window.parent.document).click( function(){
					
                })
			}
		})
	});
	
	
	//导出
	$('#exportEventExcel').click(function() {
		$(".warp02-bottom").html("");		
		var This = $('.shengfen-button').index($(this));		
		var year = $('#ddl_year').val(); //开始时间
		var ddlmonth = $('#ddl_month').val(); //结束时间		
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

		if((window.location.href.indexOf("OneYearToJsp")>0 || window.location.href.indexOf("OneMonthToJsp")>0) && taizhang.length!=1){			
			jsalert("请选择一个单站",function (){layer.closeAll();});
			return false;			
		}
		var params = "";
		var url="/DataShare/excel/earthQuakeExcel.action?earthQuakeId="+earthQuakeId;
		params = "&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang;
		window.location.href=url+params;
	});
	
	//====提交查询
	$('.shengfen-button').click(function() {
		$(".warp02-bottom").html("");		
		var This = $('.shengfen-button').index($(this));		
		var year = $('#ddl_year').val(); //开始时间
		var ddlmonth = $('#ddl_month').val(); //结束时间		
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

		if((window.location.href.indexOf("OneYearToJsp")>0 || window.location.href.indexOf("OneMonthToJsp")>0) && taizhang.length!=1){			
			jsalert("请选择一个单站",function (){layer.closeAll();});
			return false;			
		}
	
		var params = "";
		var url="/DataShare/showData/earthQuakeData.action?earthQuakeId="+earthQuakeId;
		params = "&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang;
		params+="&fileType=1&siteType=1";
		$.ajax({
			url: url+params,
			type: "get",
			data: "json",
		}).done(function(data) {
			$("#tipoi").show();
			$(".warp02-bottom").html("<h3 class='hz'>1赫兹</h3><table class='table01 hy' border='0' width='100%'></table><h3>50赫兹</h3><table class='table01 ty' border='0' width='100%' style='display:none;'></table>");
			var hy="";
			var ty="";
			var e=0;
			var str='<tbody><tr><th class="ts02">台站</th>';
			for(var i=1;i<=12;i++){
				if(i<10){
					str+='<th class="ts01">0'+i+'</th>'
				}else{
					str+='<th class="ts01">'+i+'</th>'
				}
			}
			var str1='<tbody><tr><th class="ts02">台站</th>';
			var str2;
			for(var i=0;i<=23;i++){
				if(i<10){
					str1+='<th colspan="4" class="ts01">0'+i+'</th>'
				}else{
					str1+='<th colspan="4" class="ts01">'+i+'</th>'
				}
			}
			$(".hy").append(str1+"</tr></tbody>");
			$(".ty").append(str+"</tr></tbody><tbody id='tbody'></tbody>");
			var name=[];
			$.each(data, function(key, val) {
				var i=key.split("_");
				if(i[1]!=50){
					if($.inArray(i[0],name)==-1){
						e=e+1;
						name.push(i[0]);
						$(".hy").append("<tbody id='tbody' class='tbody"+e+"'></tbody>");
					}else{
						e=$.inArray(i[0],name)+1;	
					}
					
					if(i[1]==2){
						str2='<td>'+i[0]+'</td>';
						var van;
						$.each(val,function(key1,val1){
							if(val1.fileFlag=="1"){
								str2+="<td class='green'></td>";
							}else if(val1.fileFlag=="2"){
								str2+="<td class='green'></td>";
							}else if(val1.fileFlag=="3"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="4"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="5"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="0"){
								str2+="<td></td>";
							}else{
								str2+="<td></td>";		
							}
						})
						
						$(".tbody"+e).append("<tr>"+str2+"</tr>");
					}else if(i[1]==1){
						str2="";
						str2='<td>震前一天</td>';
						var van;
						$.each(val,function(key1,val1){
							if(val1.fileFlag=="1"){
								str2+="<td class='green'></td>";
							}else if(val1.fileFlag=="2"){
								str2+="<td class='green'></td>";
							}else if(val1.fileFlag=="3"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="4"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="5"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="0"){
								str2+="<td></td>";
							}else{
								str2+="<td></td>";		
							}
						})
						$(".tbody"+e).append("<tr>"+str2+"</tr>");
					}else{
						str2="";
						str2='<td>震后一天</td>';
						$.each(val,function(key1,val1){
							if(val1.fileFlag=="1"){
								str2+="<td class='green'></td>";
							}else if(val1.fileFlag=="2"){
								str2+="<td class='green'></td>";
							}else if(val1.fileFlag=="3"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="4"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="5"){
								str2+="<td class='red'></td>";
							}else if(val1.fileFlag=="0"){
								str2+="<td></td>";
							}else{
								str2+="<td></td>";		
							}
						})
						$(".tbody"+e).append("<tr>"+str2+"</tr>");
					}
				}else{
					$(".ty").show();
					var str1="<td>"+i[0]+"</td>";
					$.each(val,function(key1,val1){
						
						if(val1.fileFlag=="1"){
							if(key1>4&&key1<9){
								str1+="<td class='green'></td>";
							}else{
								str1+="<td class='green'></td>";
							}
						}else if(val1.fileFlag=="2"){
							if(key1>4&&key1<9){
								str1+="<td class='green'></td>";
							}else{
								str1+="<td class='green'></td>";
							}
						}else if(val1.fileFlag=="3"){
							if(key1>4&&key1<9){
								str1+="<td class='red'></td>";
							}else{
								str1+="<td class='red'></td>";
							}
						}else if(val1.fileFlag=="4"){
							if(key1>4&&key1<9){
								str1+="<td class='red'></td>";
							}else{
								str1+="<td class='red'></td>";
							}
						}else if(val1.fileFlag=="5"){
							if(key1>4&&key1<9){
								str1+="<td class='red'></td>";
							}else{
								str1+="<td class='red'></td>";
							}
						}else if(val1.fileFlag=="0"){
							if(key1>4&&key1<9){
								str1+="<td></td>";
							}else{
								str1+="<td></td>";
							}
						}else{
							if(key1>4&&key1<9){
								str1+="<td></td>";
							}else{
								str1+="<td></td>";	
							}
						}
					})
					$(".ty").find("#tbody").append("<tr>"+str1+"</tr>");
				}
				setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);		
			})					
		})
	})
	
	$(document).on('change','.check_style input',function(){
			checkstyle($(this));
		})
		$(document).on('change','.input-warp01 input',function(){
			isquanxuan($('.input-warp01 .check_style'),$('.quanxuan'));
		})
		$(document).on('change','.input-warp02 input',function(){
			isquanxuan($('.input-warp02 .check_style'),$('.quanxuan2'));
		})
	})

	function checkstyle(obj){
		if(obj.is(':checked')){
			obj.parent().addClass('on');
		}else{
			obj.parent().removeClass('on');
		}
	}
	function isquanxuan(obj,objall){
		var isall=true;
		obj.each(function(){
			if(!$(this).find('input').is(':checked')){
				isall=false;
				return false;
			}
		})
	}

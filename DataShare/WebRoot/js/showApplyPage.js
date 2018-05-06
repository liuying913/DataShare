function jsalert(){
	var arg=arguments;
	layer.alert(arg[0],{shade:false},function(){
		if(typeof arg[1] == 'function'){
			arg[1]();
		}
	});
}

$(document).ready(function() {
	//var iniStartTime = getYearOne();
	//var iniEndTime = getToday();
	//$('.kaishi').val(iniStartTime); //开始时间赋值
	//$('.jieshu').val(iniEndTime); //结束时间赋值
	//====隔行变色
	$('body').delegate('#input-warp01 input', 'mouseover', function() {
		$(function() {
			$('.warp02-bottom-right table tr:even').css('background', '#eeeeee')
			$('.warp02-bottom-right table tr').eq(0).css('background', '#306bb5')
		}())
	})

	//====全选事件
	$(function() {
		//判断浏览器是否支持placeholder属性
			var doc=document,
				inputs=doc.getElementsByTagName('input'),
				supportPlaceholder='placeholder'in doc.createElement('input'),

				placeholder=function(input){
					var text=input.getAttribute('placeholder'),
						defaultValue=input.defaultValue;
					if(defaultValue==''){
						input.value=text
					}
					input.onfocus=function(){
						if(input.value===text)
						{
							this.value=''
						}
					};
					input.onblur=function(){
						if(input.value===''){
							this.value=text
						}
					}
				};

			if(!supportPlaceholder){
				for(var i=0,len=inputs.length;i<len;i++){
					var input=inputs[i],
						text=input.getAttribute('placeholder');
					if(input.type==='text'&&text){
						placeholder(input)
					}
				}
			};
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
	var pindex=0;
	var siteType = 1;
	var applyId = $("#applyId").val();
	var applyIdParam="&applyId="+applyId;
	
	var gparent = $('.shop-warp02').eq(0);
	setTimeAndJw(gparent);//设置开始结束时间、经纬度坐标
	$('.shop-warp').click(function() {
	    var si = $(".shop-warp").index(this);
		pindex=si;
	    var gparent = $(this).parentsUntil('.shop-warp02');
	    siteType=parseInt(si)+1; 
		function_name(gparent);
		setTimeAndJw(gparent);
	})
	
	function setTimeAndJw(obj){
		$.ajax({
			url: "/DataShare/getTimeAndJW.action?p=1"+applyIdParam,
			type: "get",
			data: "json",
		}).done(function(data) {
			var chtml='';
			if(siteType==1){
				obj.find('.kaishi').val(data[0].s_Start.replace("/","-").replace("/","-"));
				obj.find('.jieshu').val(data[0].s_End.replace("/","-").replace("/","-"));
				obj.find('.dongwei').val(data[0].s_Lat_small);
				obj.find('.xiwei').val(data[0].s_Lat_big);
				obj.find('.dongjin').val(data[0].s_Lon_small);
				obj.find('.xijin').val(data[0].s_Lon_big);
				
			}else if(siteType==2){
				
				//obj.find('.kaishi').val(data[0].f_Start.replaceAll("/","-"));
				//obj.find('.jieshu').val(data[0].f_End.replaceAll("/","-"));
				
				obj.find('.dongwei').val(data[0].flow_Lat_small);
				obj.find('.xiwei').val(data[0].flow_Lat_big);
				obj.find('.dongjin').val(data[0].flow_Lon_small);
				obj.find('.xijin').val(data[0].flow_Lon_big);
				
			}else if(siteType==3){
				
				//obj.find('.kaishi').val(data[0].sh_Start.replaceAll("/","-"));
				//obj.find('.jieshu').val(data[0].sh_End.replaceAll("/","-"));
				
				obj.find('.dongwei').val(data[0].share_Lat_small);
				obj.find('.xiwei').val(data[0].share_Lat_big);
				obj.find('.dongjin').val(data[0].share_Lon_small);
				obj.find('.xijin').val(data[0].share_Lon_big);
				
			}
			obj.find('.input-warp01').val(chtml);
		})
	}
	
	//====获取省份
	function function_name(obj) {
		$.ajax({
			url: "/DataShare/ShowZoneSiteInfoList.action?siteType="+siteType+applyIdParam,
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
			//循环json数据，创建html
			for(var i = 0; i < data['provincesList'].length; i++) {
				chtml+='<div class="input-warp-contnor check_style check_y2"><input type="checkbox" value="'+data['provincesList'][i]['id']+'"><label>'+data['provincesList'][i]['zoneName']+'</label></div>';
				// var box = $("<div>").addClass('input-warp-contnor').appendTo(".input-warp01");
				// $("<input>").attr('type', 'checkbox').val(data['provincesList'][i]['id']).appendTo(box);
				// //$("<input>").attr('value', data['provincesList'][i]['zoneCode']).appendTo(box);
				// //alert(box.html());
				// $("<label>").text(data['provincesList'][i]['zoneName']).appendTo(box);
			}
			obj.find('.input-warp01').html(chtml);
			setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);
		})
	}
	function_name($('.shop-warp02').eq(0));

	function  getdelCitys(obj){

		$.ajax({
			url: "/DataShare/ShowZoneSiteInfoList.action?siteType="+siteType+applyIdParam,
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
			obj.find('.input-warp01 input').each(function(){
				if($(this).is(':checked')) {
					var t=$(this).val();
					for(var i=0;i<data.provincesList.length;i++){
						if(data.provincesList[i].id==t ){
							var provdata = data.provincesList[i].citys;
							if(! provdata.length) continue;
							for(var a=0;a<provdata.length;a++){
								ahtml+='<div class="input-warp-contnor check_style check_y4"><input type="checkbox" value="'+provdata[a].siteNumber+'"><label>'+provdata[a].siteName+'</label></div>';
							}
						}
					}
				}
			});
			obj.find(".input-warp02").html(ahtml);
			setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);
		});
	}


	//===右侧箭头事件
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
	});

	//=====获取台站
	$('body').delegate('.input-warp01 input', 'click', function() {
		var gparent = $(this).parentsUntil('.shop-warp02');
		getdelCitys(gparent);
	});
    //绑定全选、
	$(document).on('change','.quanxuan',function(){
		var gparent = $(this).parentsUntil('.shop-warp01');
		if($(this).is(':checked')) {
			gparent.find('.input-warp01 input').prop('checked',true);
			getdelCitys(gparent);
		} else {
			gparent.find('.input-warp01 input').prop('checked',false);
			gparent.find(".input-warp02").empty();
		}
		gparent.find('.input-warp01 input').each(function(){
			checkstyle($(this));
		});
	});
	
	//绑定反选、
	$(document).on('change','.fanxuan',function(){
		var gparent = $(this).parentsUntil('.shop-warp01');
		gparent.find('.input-warp01 input').each(function(){
			if($(this).is(':checked')){
				$(this).prop('checked',false);
			}else{
				$(this).prop('checked',true);
			}
			checkstyle($(this));
		});
		getdelCitys(gparent);
		isquanxuan($('.input-warp01 .check_style'),$('.quanxuan'));
	});
	//全选2
	$(document).on('change','.quanxuan2',function(){
		var gparent = $(this).parentsUntil('.shop-warp02');
		if($(this).is(':checked')) {
			gparent.find('.input-warp02 input').prop('checked',true);
		} else {
			gparent.find('.input-warp02 input').prop('checked',false);
		}
		gparent.find('.input-warp02 input').each(function(){
			checkstyle($(this));
		});
	});
	//反选2
	$(document).on('change','.fanxuan2',function(){
		var gparent = $(this).parentsUntil('.shop-warp02');
		gparent.find('.input-warp02 input').each(function(){
			if($(this).is(':checked')){
				$(this).prop('checked',false);
			}else{
				$(this).prop('checked',true);
			}
			checkstyle($(this));
		});
		isquanxuan($('.input-warp02 .check_style'),$('.quanxuan2'));
	});

	
    //====拼接查询参数
	function getParams(This){
		var kaishi = $('.kaishi').eq(This).val(); //开始时间
		var jieshu = $('.jieshu').eq(This).val(); //结束时间
		var startYear = '';
		var endYear = '';
		if(kaishi == undefined ||  jieshu == undefined ){
			var myDate = new Date();
			startYear = myDate.getFullYear();
			endYear =myDate.getFullYear();
		}else{
			startYear = kaishi.substring(0, 4);
			endYear = jieshu.substring(0, 4);
		}
		if(startYear!=endYear){
		    init( "不能跨年" ,2 );
			return false;
		}
		//$('.warp02-bottom').eq(This).slideDown(); //提交--显示
		//$('.tr-01').eq(This).nextAll().remove();
		//$('.ul-img').eq(This).html('');
		
		var dongwei = $('.dongwei').eq(This).val(); //东纬度
		if (dongwei.indexOf("最")>-1) {
			dongwei = "";
		}
		var xiwei = $('.xiwei').eq(This).val(); //西纬度
		if (xiwei.indexOf("最")>-1) {
			xiwei = "";
		}
		var dongjin = $('.dongjin').eq(This).val(); //东经度
		if (dongjin.indexOf("最")>-1) {
			dongjin = "";
		}
		var xijin = $('.xijin').eq(This).val(); //西经度
		if (xijin.indexOf("最")>-1) {
			xijin = "";
		}
		var Datetype = $('.Datetype').eq(This).val(); //数据第几块
		
		var shengfen = []; //省份
		var taizhang = []; //台站
		for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {
			shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();
		}
		for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {
			taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();
		}

		var params = "kaishi="+kaishi+"&jieshu="+jieshu+"&dongwei="+dongwei+"&xiwei="+xiwei+"&dongjin="+dongjin+"&xijin="+xijin+"&shengfen="+shengfen+"&taizhang="+taizhang+"&Datetype="+Datetype+"&applyId="+applyId;
		params = encodeURI(params);
		return params;
	}
	
	
	//====提交查询
	$('.shengfen-button').click(function() {
		var This = $('.shengfen-button').index($(this));
		var Datetype = $('.Datetype').eq(This).val(); //数据第几块
		$('.warp02-bottom').eq(This).slideDown(); //提交--显示
		$('.tr-01').eq(This).nextAll().remove();
		$('.ul-img').eq(This).html('');
		var params = getParams(This);
		$.ajax({
			url: "/DataShare/showData/getYearDayFileInfoList.action?"+params,
			type: "get",
			data: "json",
		}).done(function(data) {
			for(var p in data){
				var td = $("<li>").appendTo($('.ul-img').eq(This));
				td.html("<div class='angDown' id='year"+This+"'><span class='TText'>" + p + "</span><i class='icon-angle-down'></i></div>");
				var div = $("<ul>").addClass('bottom-left-ul').appendTo($('.ul-img').eq(This));
				var uhtml='';
				var iniYearDayFlag = "true";//判断是否第一次
				for(var d in data[p]){
					d = d.substring(1,d.length);
					if(iniYearDayFlag.indexOf("true")>-1){
						fillLeftData(This,d,Datetype);
						iniYearDayFlag="false";
					}
					uhtml+='<li>'+d+'<i class="icon-angle-right"></i></li>';
				}
				div.html(uhtml);
				$('#iframe', parent.document).css("height",document.body.scrollHeight);
			}
			$('#iframe', parent.document).css("height",document.body.scrollHeight);
		});
	});
	
	
	//=====获取table内容
	var shuci = 0; //声明定义——禁止双次绑定表格全选事件

	//点击左侧 树桩结构
	$('body').delegate('.bottom-left-ul li', 'click', function(e) {
		var gparent = $(this).parents('.shop-warp02');
		var This = $(".shop-warp02").index(gparent);
		gparent.find('.bottom-left-ul li').removeClass('on');
		$(this).addClass('on');
		var yearDay = $(this).text(); //获取当前年积日
		var Datetype = $('.Datetype').eq(This).val(); //数据第几块
		fillLeftData(This,yearDay,Datetype);
	});
	
	
	//左侧 出现数据
	function fillLeftData(This,yearDay,Datetype){
		var gparent = $(".shop-warp02").eq(This);
		gparent.find('.bottom-left-ul li').removeClass('on');
		$(this).addClass('on');
		shuci++;
		var nianfen = parseInt(gparent.find('.angDown').text()); //获取当前年份
		params = getParams(This)+"&fileYear="+nianfen+"&fileDayYear="+yearDay;
		//获取表格json数据
		$.ajax({
			url: "/DataShare/getFileInfoList.action?"+params,
			type: "get",
			data: "json",
			beforeSend:function() {
				$('.zbg', window.parent.document).show();
				$('.myload', window.parent.document).show();
			},
			complete:function(){
				$('.zbg', window.parent.document).hide();
				$('.myload', window.parent.document).hide();
			}
		}).done(function(data) {
			gparent.find('.tr-01').nextAll().remove();
			var dnt=data[nianfen][yearDay];
			for(var i = 0; i < dnt.length; i++) {
				var box = $("<tr>").appendTo(gparent.find(".table"));
				$("<td>").text(dnt[i]['siteNumber']).appendTo(box);
				$("<td>").text(dnt[i]['siteName']).appendTo(box);
				$("<td>").text(dnt[i]['fileName']).appendTo(box);
				$("<td>").text(dnt[i]['fileSize']).appendTo(box);
				$("<td>").text(dnt[i]['ephemNumber']).appendTo(box);//历元数量
				$("<td>").text(dnt[i]['zoneName']).appendTo(box);
				$("<td>").text(dnt[i]['fileComp']).appendTo(box);
			}

			//绑定表格隔行变色事件
			$('.warp02-bottom-right table tr:even').css('background', '#eeeeee')
			$('.warp02-bottom-right table tr').eq(0).css('background', '#306bb5')

			//绑定表格全选事件
			if(shuci == 1) {
				$('.input-01').click(function() {
					if($(this).prop("checked") == false) {
						$(".table input:checkbox").prop("checked", false);
					} else {
						$(".table input:checkbox").prop("checked", true);
					}
				});
			}
			//setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);
			$('#iframe', parent.document).css("height",document.body.scrollHeight);
		});
	}
	
	
	//文件点击事件
	$('body').delegate('.ul-img .angDown', 'click', function() {
		$(this).parent().next().toggle();
	});

	$(document).on('change','.check_style input',function(){
		checkstyle($(this));
	});
	$(document).on('change','.input-warp01 input',function(){
		isquanxuan($('.input-warp01 .check_style'),$('.quanxuan'));
	});
	$(document).on('change','.input-warp02 input',function(){
		isquanxuan($('.input-warp02 .check_style'),$('.quanxuan2'));
	});
});
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
		});
		objall.prop('checked',isall);
		checkstyle(objall);
	}
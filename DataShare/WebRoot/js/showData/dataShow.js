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
	
	var iniStartTime = getMonthOne();
	var iniEndTime = getToday();
	$('.kaishi').val(iniStartTime); //开始时间赋值
	$('.jieshu').val(iniEndTime); //结束时间赋值
	
	//====隔行变色
	$('body').delegate('#input-warp01 input', 'mouseover', function() {
		$(function() {
			$('.warp02-bottom-right table tr:even').css('background', '#eeeeee')
			$('.warp02-bottom-right table tr').eq(0).css('background', '#306bb5')
		}())
	})


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

	var siteType = $("#siteType").val();
	var fileType = $("#fileType").val();
	var applyId = $("#applyId").val();
	//var siteTypeZone = parseInt(siteType)-1; 
	
	$('.shop-warp').click(function() {
		if($(this).parent().next().css('display') == 'block'){return;}
	    var gparent = $(this).parentsUntil('.shop-warp02');
		function_name(gparent);
	})
	
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

	
	//====提交查询
	$('.shengfen-button').click(function() {
		again();
		function again() {
			
			var This = $('.shengfen-button').index($(this));
		
			var kaishi = $('.kaishi').eq(This).val(); //开始时间
			var jieshu = $('.jieshu').eq(This).val(); //结束时间
			
			getYearFlagOrNo(kaishi,jieshu);//判定是否跨年
			
			$('.warp02-bottom').eq(This).slideDown(); //提交--显示
			$('.tr-01').eq(This).nextAll().remove();
			$('.ul-img').eq(This).html('');
			setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',300);
			var buwei = [];//部委
			var shengfen = []; //省份
			var taizhang = []; //台站
			
			//部委
			for(var i = 0; i < $(".input-warp00").eq(This).find('input:checked').length; i++) {buwei[i] = $(".input-warp00").eq(This).find('input:checked').eq(i).val();}
			//省份
			for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();}
			//台站
			for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();}	

			var params = "kaishi="+kaishi+"&jieshu="+jieshu+"&buwei="+buwei  +"&shengfen="+shengfen+"&taizhang="+taizhang+"&Datetype="+siteType+"&applyOrShow=apply";
			$.ajax({
				url: "/DataShare/showData/getYearDayFileInfoList.action?"+params,
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

				for(var p in data){
					var td = $("<li>").appendTo($('.ul-img').eq(This));
					td.html("<div class='angDown'><span class='TText'>" + p + "</span><i class='icon-angle-down'></i></div>")
					var div = $("<ul>").addClass('bottom-left-ul').appendTo($('.ul-img').eq(This));
					var uhtml='';
					var iniYearDayFlag = "true";//判断是否第一次
					var yearDayFirst = "001";
					for(var d in data[p]){
						d = d.substring(1,d.length);
						if(iniYearDayFlag.indexOf("true")>-1){
							yearDayFirst = d;
							iniYearDayFlag="false";
						}
						uhtml+='<li>'+d+'<i class="icon-angle-right"></i></li>';
					}
					div.html(uhtml);
					
					//右侧  默认显示第一天的表格
					fillData(This,p,yearDayFirst,kaishi,jieshu,siteType);
				}
				setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',300);
			})
		}
	})
	
	
	function fillData(This,nianfen,textx,kaishi,jieshu,Datetype){
		var gparent = $(".shop-warp02").eq(This);
		gparent.find('.bottom-left-ul li').removeClass('on');
		$(this).addClass('on');
		shuci++;
		
		var shengfen = []; //省份
		var taizhang = []; //台站
		for(var i = 0; i < $(".input-warp01").find('input:checked').length; i++) {
			shengfen[i] = $(".input-warp01").find('input:checked').eq(i).val();
		}
		for(var i = 0; i < $(".input-warp02").find('input:checked').length; i++) {
			taizhang[i] = $(".input-warp02").find('input:checked').eq(i).val();
		}
		var params = "kaishi="+kaishi+"&jieshu="+jieshu+"&shengfen="+shengfen+"&taizhang="+taizhang+"&fileYear="+nianfen+"&fileDayYear="+textx+"&Datetype="+Datetype+"&applyOrShow=apply";
		params = encodeURI(params);
		//获取表格json数据
		$.ajax({
			url: "/DataShare/getFileInfoList.action?"+params,
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
			gparent.find('.tr-01').nextAll().remove();
			var dnt=data[nianfen][textx];
			for(var i = 0; i < dnt.length; i++) {
				var box = $("<tr>").appendTo(gparent.find(".table"));
				$("<td>").text(dnt[i]['siteNumber']).appendTo(box);
				$("<td>").text(dnt[i]['siteName']).appendTo(box);
				$("<td>").text(dnt[i]['fileName']).appendTo(box);
				$("<td>").text(dnt[i]['fileSize']).appendTo(box);
				$("<td>").text(dnt[i]['ephemNumber']).appendTo(box);//历元数量
				$("<td>").text(dnt[i]['mp1']).appendTo(box);
				$("<td>").text(dnt[i]['mp2']).appendTo(box);
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
	
	//=====获取table内容
	var shuci = 0; //声明定义——禁止双次绑定表格全选事件
	var gparent;
	var nianfen;
	var textx;
	var params;
	$('body').delegate('.bottom-left-ul li', 'click', function() {
		gparent = $(this).parentsUntil('.shop-warp02');
		gparent.find('.bottom-left-ul li').removeClass('on');
		$(this).addClass('on');

		nianfen = parseInt(gparent.find('.angDown').text()); //获取当前年份
		textx = $(this).text(); //获取当前年积日
		$('.input-01').prop("checked", false);
		shuci++;
		params = getParams()+"&fileYear="+nianfen+"&fileDayYear="+textx+"&Datetype="+siteType+"&applyOrShow=apply";
		//获取表格json数据
		$.ajax({
			url: "/DataShare/getFileInfoList.action?"+params,
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
			gparent.find('.tr-01').nextAll().remove();
			var dnt=data[nianfen][textx];
			for(var i = 0; i < dnt.length; i++) {
				var box = $("<tr>").appendTo(gparent.find(".table"));
				//var td = $("<td>").appendTo(box);
				//$("<input>").attr('type', 'checkbox').appendTo(td);
				//$("<td>").text(dnt[i]['fileYear']).appendTo(box);
				//$("<td>").text(dnt[i]['fileDayYear']).appendTo(box);
				$("<td>").text(dnt[i]['siteNumber']).appendTo(box);
				$("<td>").text(dnt[i]['siteName']).appendTo(box);
				$("<td>").text(dnt[i]['fileName']).appendTo(box);
				$("<td>").text(dnt[i]['fileSize']).appendTo(box);
				$("<td>").text(dnt[i]['ephemNumber']).appendTo(box);//历元数量
				$("<td>").text(dnt[i]['mp1']).appendTo(box);
				$("<td>").text(dnt[i]['mp2']).appendTo(box);
				$("<td>").text(dnt[i]['fileComp']).appendTo(box);
			}
             
			//绑定表格隔行变色事件
			$('.warp02-bottom-right table tr:even').addClass('even');
			//$('.warp02-bottom-right table tr').eq(0).css('background', '#306bb5')

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
			setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',100);	
		});
	});
		
	
	//文件点击事件
	$('body').delegate('.ul-img .angDown', 'click', function() {
		$(this).parent().next().toggle();
	});


	function getParams(){
		var kaishi = $('.kaishi').val(); //开始时间
		var jieshu = $('.jieshu').val(); //结束时间
		getYearFlagOrNo(kaishi,jieshu);//判定是否跨年
		
		var buwei = [];//部委
		var shengfen = []; //省份
		var taizhang = []; //台站
		
		//部委
		for(var i = 0; i < $(".input-warp00").find('input:checked').length; i++) {
			buwei[i] = $(".input-warp00").find('input:checked').eq(i).val();
		}
		//省份
		for(var i = 0; i < $(".input-warp01").find('input:checked').length; i++) {shengfen[i] = $(".input-warp01").find('input:checked').eq(i).val();}
		//台站
		for(var i = 0; i < $(".input-warp02").find('input:checked').length; i++) {taizhang[i] = $(".input-warp02").find('input:checked').eq(i).val();}	

		var params = "kaishi="+kaishi+"&jieshu="+jieshu+"&buwei="+buwei+"&shengfen="+shengfen+"&taizhang="+taizhang;
		return params;
	}
	
	//导出事件
	$('#exportDataExcel').click(function() {
		
		var kaishi = $('.kaishi').val(); //开始时间
		var jieshu = $('.jieshu').val(); //结束时间
		getYearFlagOrNo(kaishi,jieshu);//判定是否跨年
		
		var url="/DataShare/excel/exportDataExcel.action?applyOrShow=apply&"+getParams();
		window.open(url);
	})
	
	function checkstyle(obj){
		if(obj.is(':checked')){
			obj.parent().addClass('on');
		}else{
			obj.parent().removeClass('on');
		}
	}
	$(document).on('change','.check_style input',function(){
		checkstyle($(this));
	});
	$(document).on('change','.input-warp01 input',function(){
		isquanxuan($('.input-warp01 .check_style'),$('.quanxuan'));
	});
	$(document).on('change','.input-warp02 input',function(){
		isquanxuan($('.input-warp02 .check_style'),$('.quanxuan2'));
	});
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
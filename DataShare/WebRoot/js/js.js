function logout() {
	window.location.href="/DataShare/logoutToJsp.action";
	//$.ajax({
	//	url: "/DataShare/logout.action",
	//	type: "get",
	//	data: "json",
	//}).done(function(data) { alert(data);
	//	window.location.href="/DataShare/index.action?id=1";
	//})
    
}

function jsalert(){
	var arg=arguments;
	layer.alert(arg[0],{shade:false},function(){
		if(typeof arg[1] == 'function'){
			arg[1]();
		}
	});
}

$(document).ready(function() {
	
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
	var pindex=0;
	var siteType = 1;
	var applyId = $("#applyId").val();
	var applyIdParam="&applyId="+applyId;
	// alert(applyId);
	$('.shop-warp').click(function() {
	    var si = $(".shop-warp").index(this);
		pindex=si;
	    var gparent = $(this).parentsUntil('.shop-warp02');
	    siteType=parseInt(si)+1; 
		//alert(siteType);
		//alert($('.Datetype').val());
		// layer.alert(siteType,{share:false});
		function_name(gparent);
	})
	
	//====获取省份
	function function_name(obj) {
		$.ajax({
			//url: "shengfen.json",
			url: "/DataShare/getZoneSiteInfoList.action?siteType="+siteType+applyIdParam,
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
				chtml+='<div class="input-warp-contnor  check_style check_y2"><input type="checkbox" value="'+data['provincesList'][i]['id']+'"><label>'+data['provincesList'][i]['zoneName']+'</label></div>';
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
			url: "/DataShare/getZoneSiteInfoList.action?siteType="+siteType+applyIdParam,
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
								ahtml+='<div class="input-warp-contnor  check_style check_y4"><input type="checkbox" value="'+provdata[a].siteNumber+'"><label>'+provdata[a].siteName+'</label></div>';
							}
						}
					}
				}
			})
			obj.find(".input-warp02").html(ahtml);
			setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);		
		})	
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
		//		var top=$('.shop-warp div').eq(0).offset().top;
		//		$('html,body').stop(true,true).animate({scrollTop:top}, 500);
	})

	//=====获取台站
	$('body').delegate('.input-warp01 input', 'click', function() {
		var gparent = $(this).parentsUntil('.shop-warp02');
		getdelCitys(gparent)

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
		})
	})
	
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
		})
		getdelCitys(gparent);
		isquanxuan($('.input-warp01 .check_style'),$('.quanxuan'));
	})
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
		})
	})
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
		})
		isquanxuan($('.input-warp02 .check_style'),$('.quanxuan2'));
	})
	
	
	
	
	
	
	
	//====提交查询
	$('.shengfen-button').click(function() {
		
		var njrNumber = 0;
		//alert($("#applyId").val());
		var This = $('.shengfen-button').index($(this));
		
		var kaishi = $('.kaishi').eq(This).val(); //开始时间
		var jieshu = $('.jieshu').eq(This).val(); //结束时间
		
		var startYear = kaishi.substr(0, 4);
		var endYear = jieshu.substr(0, 4);
		if(startYear!=endYear){
			alert("时间选取不能跨年！");
			return false;
		}
		
		$('.warp02-bottom').eq(This).slideDown(); //提交--显示
		$('.tr-01').eq(This).nextAll().remove();
		$('.ul-img').eq(This).html('');
		
		var dongwei = $('.dongwei').eq(This).val(); //东纬度
		var xiwei = $('.xiwei').eq(This).val(); //西纬度
		var dongjin = $('.dongjin').eq(This).val(); //东经度
		var xijin = $('.xijin').eq(This).val(); //西经度
		
		var Datetype = $('.Datetype').eq(This).val(); //数据第几块
		//alert(Datetype);
		
		var shengfen = []; //省份
		var taizhang = []; //台站
		for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {
			shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();
		}
		for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {
			taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();
		}

		var params = "kaishi="+kaishi+"&jieshu="+jieshu+"&dongwei="+dongwei+"&xiwei="+xiwei+"&dongjin="+dongjin+"&xijin="+xijin+"&shengfen="+shengfen+"&taizhang="+taizhang+"&Datetype="+Datetype+"&applyOrShow=apply";
		$.ajax({
			url: "/DataShare/showData/getYearDayFileInfoList.action?"+params,
			type: "get",
			data: "json",
		}).done(function(data) {
			for(var p in data){
				var td = $("<li>").appendTo($('.ul-img').eq(This));
				td.html("<div class='angDown'><span class='TText'>" + p + "</span><i class='icon-angle-down'></i></div>")
				var div = $("<ul>").addClass('bottom-left-ul').appendTo($('.ul-img').eq(This));
				var uhtml='';
				for(var d in data[p]){
					
					d = d.substring(1,d.length);
					
					uhtml+='<li>'+d+'<i class="icon-angle-right"></i></li>'
				}
				div.html(uhtml);
				setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);
			}
		})
		
		//=====获取table内容
		var shuci = 0; //声明定义——禁止双次绑定表格全选事件
		$('body').delegate('.bottom-left-ul:eq('+pindex+') li', 'click', function(e) {
			
			var gparent = $(this).parentsUntil('.shop-warp02');
			
			gparent.find('.bottom-left-ul li').removeClass('on');
			$(this).addClass('on');

			var nianfen = parseInt(gparent.find('.angDown').text()); //获取当前年份
			var textx = $(this).text(); //获取当前年积日
			//$('.input-01').prop("checked", false);
			shuci++;
			
			var dongwei = $('.dongwei').eq(This).val(); //东纬度
			var xiwei = $('.xiwei').eq(This).val(); //西纬度
			var dongjin = $('.dongjin').eq(This).val(); //东经度
			var xijin = $('.xijin').eq(This).val(); //西经度
			var shengfen = []; //省份
			var taizhang = []; //台站
			for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {
				shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();
			}
			for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {
				taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();
			}
			var params = "kaishi="+kaishi+"&jieshu="+jieshu+"&dongwei="+dongwei+"&xiwei="+xiwei+"&dongjin="+dongjin+"&xijin="+xijin
			           +"&shengfen="+shengfen+"&taizhang="+taizhang+"&fileYear="+nianfen+"&fileDayYear="+textx+"&Datetype="+Datetype+"&applyOrShow=apply";
			
			//alert(params);
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
					$("<td>").text(dnt[i]['zoneName']).appendTo(box);
					$("<td>").text(dnt[i]['fileComp']).appendTo(box);
				}

				//绑定表格隔行变色事件
				$('.warp02-bottom-right table tr:even').css('background', '#eeeeee')
				$('.warp02-bottom-right table tr').eq(0).css('background', '#306bb5')

				//绑定表格全选事件
				if(shuci == 1) {
					$('.input-01').click(function() {
						var Lnpute = $('.table input');
						if($(this).prop("checked") == false) {
							$(".table input:checkbox").prop("checked", false);
						} else {
							$(".table input:checkbox").prop("checked", true);
						}
					})
				}
				setTimeout('parent.$("#iframe").css("height",$("#iframeheight").height());',600);	
			})
		})
	});

	//文件点击事件
	$('body').delegate('.ul-img .angDown', 'click', function() {
		$(this).parent().next().toggle();
	})



	//===保存
	$('.button').click(function() {

		var This = $('.button').index($(this));
		var kaishi = $('.kaishi').eq(This).val(); //开始时间
		var jieshu = $('.jieshu').eq(This).val(); //结束时间
		var dongwei = $('.dongwei').eq(This).val(); //东纬度
		var xiwei = $('.xiwei').eq(This).val(); //西纬度
		var dongjin = $('.dongjin').eq(This).val(); //东经度
		var xijin = $('.xijin').eq(This).val(); //西经度
		var shengfen = []; //省份
		var taizhang = []; //台站
		for(var i = 0; i < $(".input-warp01").eq(This).find('input:checked').length; i++) {
			shengfen[i] = $(".input-warp01").eq(This).find('input:checked').eq(i).val();
		}
		for(var i = 0; i < $(".input-warp02").eq(This).find('input:checked').length; i++) {
			taizhang[i] = $(".input-warp02").eq(This).find('input:checked').eq(i).val();
		}
		var Datetype = $('.Datetype').eq(This).val(); //数据第几块
		//alert(Datetype);
		//alert($("#applyId").val());
		var applyId = $("#applyId").val();
		/*jsalert("选中地区为：" + shengfen + '\n' + "选中台站为：" + taizhang + "\n" + '纬度：' + dongwei + "--" + xiwei + '--' + dongjin + '--' + xijin + "\n" + '开始时间：' + kaishi + '\n' + "结束时间：" + jieshu+"  &Datetype="+Datetype,
			function(){*/
				var params = "kaishi="+kaishi+"&jieshu="+jieshu+"&dongwei="+dongwei+"&xiwei="+xiwei+"&dongjin="+dongjin+"&xijin="+xijin+"&shengfen="+shengfen+"&taizhang="+taizhang+"&Datetype="+Datetype+"&applyId="+applyId;
				$.ajax({
					url: "/DataShare/saveBigFileInfoList.action?"+params,
					type: "get",
					data: "json",
					beforeSend:function() {
						//$(".myload").show();
						$('.zbg', window.parent.document).show();
						$('.myload', window.parent.document).show();
					},
					complete:function(){
						//$(".myload").hide();
						$('.zbg', window.parent.document).hide();
						$('.myload', window.parent.document).hide();
					}
				}).done(function(data) {
					//layer.alert("保存成功！",{icon:1,share:false});
					//$(".mytips").show();
					//setTimeout('$(".mytips").hide();',3000);
					$('.mytips', window.parent.document).show();
					setTimeout('$(".mytips", window.parent.document).hide();',3000);
				})
				
			}
		)	
	//})
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
		objall.prop('checked',isall);
		checkstyle(objall);
	}
	
	
function setFrameHeight(frameid)
{
	 document.getElementById(frameid).height=document.getElementById(frameid).contentWindow.document.body.scrollHeight;  
}
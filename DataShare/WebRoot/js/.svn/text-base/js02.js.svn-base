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

	//====获取省份
	var wifi=0;
	function function_name () {
		$('.input-warp01').html('');		
		$('.input-warp02').html('');	
		wifi++;
		$.ajax({
			url: "shengfen.json",
			type: "get",
			data: "json",
			}).done(function(data) {
			//循环json数据，创建html
			for(var i = 0; i < data['provincesList'].length; i++) {
				var box = $("<div>").addClass('input-warp-contnor').appendTo(".input-warp01");
				$("<input>").attr('type', 'checkbox').appendTo(box);
				$("<label>").text(data['provincesList'][i]['Name']).appendTo(box);
			}
			//定义】绑定全选反选
			if(wifi==1){
				//全选、、
				$('.quanxuan').click(function() {
						$(".fanxuan:checkbox").prop("checked", false);
						var Lnput = $('.input-warp01 input');
						Lnput.each(function() {
							if($('.quanxuan').prop("checked") == false) {
								$(".input-warp01 input:checkbox").prop("checked", false);
							} else {
								$(".input-warp01 input:checkbox").prop("checked", true);
							}
						})
						if($(this).is(':checked')) {
							$.ajax({
								url: "shengfen.json",
								type: "get",
								data: "json",
							}).done(function(data) {
								//循环json数据
								for(var y = 0; y < 34; y++) {
									var lenght = data['provincesList'][y]['Citys'].length;
									for(var i = 0; i < lenght; i++) {
										var box = $("<div>").attr('class', 'input-warp-contnor').addClass(y.toString()).appendTo(".input-warp02");
										$("<input>").attr('type', 'checkbox').appendTo(box);
										$("<label>").text(data['provincesList'][y]['Citys'][i]['Name']).appendTo(box);
									}
								}
							})
						} else {
							$(".input-warp02 ").html('');
						}
					})
				//反选、、、
				$('.fanxuan').click(function() {
					$(".quanxuan:checkbox").prop("checked", false);
					var Lnput = $('.input-warp01 input');
					Lnput.each(function() {
						$(this).prop("checked", !$(this).prop("checked"));
					})
					var shengfen = {}; //定义省份下标
					var shengfetn = []; //获取被选中的身份
					var zong = []; //获取被选中的省份下标
					for(var i = 0; i < 34; i++) {
						shengfen[$(".input-warp01 input").eq(i).next().text()] = i;
					}
					for(var i = 0; i < $(".input-warp01 input:checked").length; i++) {
						shengfetn[i] = $(".input-warp01 input:checked").eq(i).next().text();
						zong[i] = shengfen[shengfetn[i]];
					}
					$.ajax({
						url: "shengfen.json",
						type: "get",
						data: "json",
					}).done(function(data) {
						$(".input-warp02 ").html('');
						//循环json数据
						function ggg(ll) {
							var lenght = data['provincesList'][zong[ll]]['Citys'].length;
							for(var i = 0; i < lenght; i++) {
								var box = $("<div>").attr('class', 'input-warp-contnor').addClass(zong[ll].toString()).appendTo(".input-warp02");
								$("<input>").attr('type', 'checkbox').appendTo(box);
								$("<label>").text(data['provincesList'][zong[ll]]['Citys'][i]['Name']).appendTo(box);
							}
						}
						if(zong.length>34){
							zong.length=34;
						}
						for(var b = 0; b < zong.length; b++) {
							ggg(b)
						}
					})
				})
			}
		})
	}
	function_name ();
	
	
	//===右侧箭头事件
	$('.shop-warp div').click(function() {
//		$('.warp02-bottom').slideUp();
		$('.shop-warp div').removeClass('bianliang'); 
		$('.shop-warp div').css('background', 'url(img/img-07.png) no-repeat  865px  20px');
		$('.shop-warp02').slideUp();
//		$(".fanxuan:checkbox").prop("checked", false);
//		$(".fanxuan2:checkbox").prop("checked", false);
//		$(".quanxuan:checkbox").prop("checked", false);
//		$(".quanxuan2:checkbox").prop("checked", false);
		if($(this).parent().next().css('display')=='block'){
			$('.shop-warp02').slideUp();
		}else{
			$(this).css('background', 'url(img/img-07.png) no-repeat  865px  -77px');
			$(this).addClass("bianliang");  
			$('.liet img').hide();
			$(this).parent().next().slideDown(300,function  () {
//				function_name ();
			});
		}
	})
	
	
	
	
	//=====获取台站
	var shuci2 = 0;
	$('body').delegate('.input-warp01 input', 'click', function() {
		var index = $(".input-warp01 input").index($(this));
//		alert(index)
		if($(this).is(':checked')) {
			$.ajax({
				url: "shengfen.json",
				type: "get",
				data: "json",
			}).done(function(data) {
				//循环json数据
				var lenght = data['provincesList'][index]['Citys'].length;
				for(var i = 0; i < lenght; i++) {
					var box = $("<div>").attr('class', 'input-warp-contnor').addClass(index.toString()).appendTo(".input-warp02");
					$("<input>").attr('type', 'checkbox').appendTo(box);
					$("<label>").text(data['provincesList'][index]['Citys'][i]['Name']).appendTo(box);
				}
				//站台全选、、
				$('.quanxuan2').click(function() {
						$(".fanxuan2:checkbox").prop("checked", false);
						var Lnput = $('.input-warp02 input');
						Lnput.each(function() {
							if($('.quanxuan2').prop("checked") == false) {
								$(".input-warp02 input:checkbox").prop("checked", false);
							} else {
								$(".input-warp02 input:checkbox").prop("checked", true);
							}
						})
					})
					//站台反选、、、
				shuci2++;
				if(shuci2 == 1) {
					$('.fanxuan2').click(function() {
						$(".quanxuan2:checkbox").prop("checked", false);
						$('.input-warp02 .input-warp-contnor input').each(function() {
							$(this).prop("checked", !$(this).prop("checked"));
						})
					})
				}
			})
		} else {
			var uu = '.' + index.toString()
			$('.input-warp-contnor').remove(uu);
		}
	});

	//====提交查询
	$('.shengfen-button').click(function() {
		$('.warp02-bottom').slideDown();//提交--显示
		$('.tr-01').nextAll().remove();
		var kaishi = $('.kaishi').val(); //开始时间
		var jieshu = $('.jieshu').val(); //结束时间
		var dongwei = $('.dongwei').val(); //东纬度
		var xiwei = $('.xiwei').val(); //西纬度
		var dongjin = $('.dongjin').val(); //东经度
		var xijin = $('.xijin').val(); //西经度
		var shengfen = [];//省份
		var taizhang = [];//台站
		for(var i = 0; i < $(".input-warp01 input:checked").length; i++) {
			shengfen[i] = $(".input-warp01 input:checked").eq(i).next().text();
		}
		for(var i = 0; i < $(".input-warp02 input:checked").length; i++) {
			taizhang[i] = $(".input-warp02 input:checked").eq(i).next().text();
		}
//		alert("选中省份为："+shengfen+'\n'+"选中台站为："+taizhang+"\n"+'纬度：'+dongwei+"--"+xiwei+'--'+dongjin+'--'+xijin+"\n"+'开始时间：'+kaishi+'\n'+"结束时间："+jieshu);
		var hh = '';
		var jie = '';
		for(var t = 0; t < 4; t++) {
			hh += kaishi[t];
			jie += jieshu[t]
		};
		var geshu = hh - jie + 1;
		var jietext = hh;
		$('.ul-img').html('');
		var nianjiri = ["123", "234", "345", "456", "678"]; //年积日
		for(var t = 0; t < geshu; t++) {
			var td = $("<li>").appendTo('.ul-img');
			td.html("<img src='img/img-09.png'/>" + "<span class='TText'>" + jietext-- + "</span>")
			var div = $("<ul>").addClass('bottom-left-ul').appendTo('.ul-img');
			$("<span>").appendTo(div);
			for(var r = 0; r < nianjiri.length; r++) {
				var li = $("<li>").appendTo(div);
				$("<i>").appendTo(li);
				var A = $("<a>").appendTo(li);
				A.html("<img src='img/img-10.png'/>" + nianjiri[r])
			}
		}
		//点击年份 全选事件
		$('.TText').click(function() {
			if($(this).attr('id') == 'mmm') {
				$(this).attr('id', '')
			} else {
				$(this).attr('id', 'mmm');
			};
			var Lnpute = $('.table input');
			Lnpute.each(function() {
				if($('.input-01').prop("checked") == false) {
					$(".table input:checkbox").prop("checked", true);
				} else {
					$(".table input:checkbox").prop("checked", false);
				}
			})
		})
	})

		//文件点击事件
	$('body').delegate('.ul-img>li>img', 'click', function() {
//		$('.bottom-left-ul').hide();
		$(this).parent().next().toggle();
	})	

	//=====获取table内容
	var shuci = 0; //声明定义——禁止双次绑定表格全选事件
	$('body').delegate('.bottom-left-ul li', 'click', function() {
		$('.bottom-left-ul li a').css('color', '#555555')
		$(this).find('a').css('color', 'red');
		var nianfen = $(this).parent().prev().text() //获取当前年份
		var Text = parseInt($(this).text()); //获取当前年积日
		$('.input-01').prop("checked", false);
		shuci++;
		//获取表格json数据
		$.ajax({
			url: "teble.json",
			type: "get",
			data: "json",
		}).done(function(data) {
			$('.tr-01').nextAll().remove();
			for(var i = 0; i < data[nianfen][Text].length; i++) {
				var box = $("<tr>").appendTo(".table");
				var td = $("<td>").appendTo(box);
				$("<input>").attr('type', 'checkbox').appendTo(td);
				$("<td>").text(data[nianfen][Text][i]['年']).appendTo(box);
				$("<td>").text(data[nianfen][Text][i]['年积日']).appendTo(box);
				$("<td>").text(data[nianfen][Text][i]['地区']).appendTo(box);
				$("<td>").text(data[nianfen][Text][i]['站点名称']).appendTo(box);
				$("<td>").text(data[nianfen][Text][i]['经纬度']).appendTo(box);
				$("<td>").text(data[nianfen][Text][i]['文件名称']).appendTo(box);
				$("<td>").text(data[nianfen][Text][i]['是否完整']).appendTo(box);
			}
			//绑定表格隔行变色事件
			$(function() {
					$('.warp02-bottom-right table tr:even').css('background', '#eeeeee')
					$('.warp02-bottom-right table tr').eq(0).css('background', '#306bb5')
				}())
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
		})
	})

})
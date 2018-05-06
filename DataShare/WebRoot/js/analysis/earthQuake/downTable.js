$(document).ready(function() {
	getYearSelect2017over("#ddl_year");
	function_UserNameList();//设置 用户列表下拉菜单
	
	var today=new Date();
	var year = today.getFullYear();
	earthQuakeListOver2017(year);
	var earthQuakeId = $("#eventId").val();//alert(earthQuakeId);

	$("#ddl_year").change(function() {
		year = $("#ddl_year").val(); //年份
		earthQuakeListOver2017(year);
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
		
		var eventId = $('#eventId').val(); //地震应急事件Id
		var year = $('#ddl_year').val(); //年份
		var userName = $('#userId').val(); //用户名
		
		var url="/DataShare/analysis/earthQuake/downTable.action?earthQuakeId="+eventId+"&year="+year+"&userName="+userName;
		$.ajax({
			url: url,
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
					
					if(i[1]==1){
						str2="";
						str2='<td>'+i[0]+'震前</td>';
						var van;
						$.each(val,function(key1,val1){
							if(parseInt(val1.fileFlag)>=1 && parseInt(val1.fileFlag)<=2){//1到2
								str2+="<td class='green'></td>";
							}else if(parseInt(val1.fileFlag)>=3 && parseInt(val1.fileFlag)<=4){//3到4
								str2+="<td class='yellow'></td>";
							}else if(parseInt(val1.fileFlag)>=5){
								str2+="<td class='red'></td>";
							}else{
								str2+="<td></td>";		
							}
						})
						$(".tbody"+e).append("<tr>"+str2+"</tr>");
					}else if(i[1]==2){
						str2='<td>'+i[0]+'</td>';
						var van;
						$.each(val,function(key1,val1){
							if(parseInt(val1.fileFlag)>=1 && parseInt(val1.fileFlag)<=2){//1到2
								str2+="<td class='green'></td>";
							}else if(parseInt(val1.fileFlag)>=3 && parseInt(val1.fileFlag)<=4){//3到4
								str2+="<td class='yellow'></td>";
							}else if(parseInt(val1.fileFlag)>=5){
								str2+="<td class='red'></td>";
							}else{
								str2+="<td></td>";		
							}
						})
						
						$(".tbody"+e).append("<tr>"+str2+"</tr>");
					}else{
						str2="";
						str2='<td>'+i[0]+'震后</td>';
						
						$.each(val,function(key1,val1){
							if(parseInt(val1.fileFlag)>=1 && parseInt(val1.fileFlag)<=2){//1到2
								str2+="<td class='green'></td>";
							}else if(parseInt(val1.fileFlag)>=3 && parseInt(val1.fileFlag)<=4){//3到4
								str2+="<td class='yellow'></td>";
							}else if(parseInt(val1.fileFlag)>=5){
								str2+="<td class='red'></td>";
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
						if(parseInt(val1.fileFlag)>=1 && parseInt(val1.fileFlag)<=2){//1到2
							str1+="<td class='green'></td>";
						}else if(parseInt(val1.fileFlag)>=3 && parseInt(val1.fileFlag)<=4){//3到4
							str1+="<td class='yellow'></td>";
						}else if(parseInt(val1.fileFlag)>=5){
							str1+="<td class='red'></td>";
						}else{
							str1+="<td></td>";		
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
		objall.prop('checked',isall);
		checkstyle(objall);
	}
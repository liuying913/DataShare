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

};
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
					var htmlStr='<label class="input-warp-contnor check_style check_y4"><input type="checkbox" name="Sex" value="'+provdata[a].siteNumber+'"><em>'+provdata[a].siteName+'</em></label>';
					
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

     //====添加大事记
	$('#addEvent').click(function() {		
		var buwei = [];//部委
		var shengfen = []; //省份
		var taizhang = []; //台站
		for(var i = 0; i < $(".input-warp02").find('input:checked').length; i++) {taizhang[i] = $(".input-warp02").find('input:checked').eq(i).val();}	

		/*获取数据*/
		var type = $(".input-warp03 .on input").val();

		var str="";
		var len=$(".input01").length;
		var rlen=$(".require").length;
		var m=0;

		if( taizhang.length ==0){
			alert("台站不能为空")
			return false;
		}else{
			$(".require").each(function(e){

				if($(this).val()==""){
					$(this).parents("li").find(".errState").show();
					$(this).parents("li").find(".sucuState").hide();
					m=1;
				}else{

					$(this).parents("li").find(".errState").hide();
					$(this).parents("li").find(".sucuState").show();

					if( e==rlen-1&& m==0){
						$(".input01").each(function(a) {
							str=str+"&"+$(this).prop("name")+"="+$(this).val();
							
							if(a==len-1){
								var url="/DataShare/greatEvent/addGreatEvent.action?type="+type+"&siteNumber="+taizhang+"&"+str;
								url = encodeURI(url);
								$.ajax({
									url: url,
									type: "get",
									data: "json",
								}).done(function(data) {
									window.location.href="/DataShare/manager/greatEventListToJsp.action?applyTitle=站点大事记管理";
								})
							}
						});
					}
				}

			});
		}

	});	


		
	function checkstyle(obj){
		if(obj.parent().hasClass("check_y4")){
			if(obj.is(':checked')){
				obj.parents(".input-warp02").find(".check_style").removeClass('on');
				obj.parents(".input-warp02").find(".check_style input").attr("checked",false);
				obj.parents(".input-warp03").find(".check_style").removeClass('on');
				obj.parents(".input-warp03").find(".check_style input").attr("checked",false);
				obj.parent().addClass('on');
				obj.prop("checked",true);
			}else{
				obj.parent().removeClass('on');
			}
		}else{
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
	$(document).on('change','.input-warp03 input',function(){
		isquanxuan($('.input-warp03 .check_style'),$('.on'));
		$("#tipoi").show();
		$(".errState").hide();
		$(".sucuState").hide();
		var type = $(this).val();
		 if( type ==1 ){
			 $("#tipoi").find(".type2").hide().addClass("err0");
			 $("#tipoi").find(".type1").show().removeClass("err0");
		 }else if( type ==2 ){
			 $("#tipoi").find(".type1").hide().addClass("err0");
			 $("#tipoi").find(".type2").show().removeClass("err0");
		 }else{
			$("#tipoi").find(".type1").hide().addClass("err0");
			$("#tipoi").find(".type2").hide().addClass("err0");
			$("#tipoi").find(".type3").show().removeClass("err0");
		 }
	});
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
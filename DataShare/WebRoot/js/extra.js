function pcgzsqh(controlid,conid,n,page){
	
	var len=$("#"+conid+" li").length;
	var tt=0;
	if(page){
		var p=Math.ceil(len/2*n);
		for(i=0;i<p;i++){
			$("#"+controlid+" .jspage").append("<span><\/span>");
		}
		$("#"+controlid+" .jspage span").bind("click",function(){
			
			var f=$(this).index();
			var d=$("#"+controlid+" .jspage span.cur").index();
			var left=$("#"+conid).css("margin-left");
			left=left=="auto"?0:left;
			left=parseInt(left);
			
			$("#"+conid).animate({marginLeft:left-(f-d)*n*w},"1000","swing",function(){tt=0;});	
			$(this).addClass("cur").siblings("#"+controlid+" .jspage span").removeClass("cur");
		})
		
	}
	var aa=0;
	var af="";
	$("#"+controlid+" .jspage span:eq(0)").addClass("cur");
	var margin=parseInt($("#"+conid+" li").css("margin-right"))+parseInt($("#"+conid+" li").css("margin-left"));
	var padding=parseInt($("#"+conid+" li").css("padding-right"))+parseInt($("#"+conid+" li").css("padding-left"));
	var w=$("#"+conid+" li").width()+margin+padding;
	$("#"+conid).css("width",w*len);
	$("#"+controlid+" .left").addClass("leftc");
	var showWidth=$("#"+conid).parent().width();
	if(n>=len||w*len<=showWidth){
		$("#"+controlid+" .left").addClass("leftc");
		$("#"+controlid+" .right").addClass("rightc");
		}
	$("#"+controlid+" .left").click(function(){
		if(tt==0){			
			tt=1;
			var left=$("#"+conid).css("margin-left");
			left=left=="auto"?0:left;
			left=parseInt(left);
			$("#"+controlid+" .right").removeClass("rightc");
			$("#"+conid).animate({marginLeft:left+n*w},"1000","swing",function(){
				tt=0;
				aa=aa-1;
				if(aa==-1){
					aa=len/2-1;
					$("#"+conid).css("margin-left",-1*w*len/2+w);
				}
			});
			var i=$("#spec-list li.on").index();
			$("#spec-list li").eq(i-1).find("img").trigger("click");
			var v=$("#"+controlid+" .jspage span.cur").index();
			if(v==0){
				v=0;	
			}
			else{
				v=v-1;
			}
			$("#"+controlid+" .jspage span").removeClass("cur");
			$("#"+controlid+" .jspage span:eq("+v+")").addClass("cur");
			
		}
	})
	$("#"+controlid+" .right").click(function(){
		
		if(tt==0){
			tt=1;	
			var left=$("#"+conid).css("margin-left");
			left=left=="auto"?0:left;
			left=parseInt(left);
			$("#"+controlid+" .left").removeClass("leftc");
			$("#"+conid).animate({marginLeft:left-n*w},"1000","swing",function(){
				tt=0;
				aa=aa+1;
				if(aa>=len/2){
					aa=0;
					$("#"+conid).css("margin-left","0");
				}
			});
			var i=$("#spec-list li.on").index();
			$("#spec-list li").eq(i+1).find("img").trigger("click");	
		}
		else{
			/*$("#"+conid).animate({marginLeft:0},"1000","swing",function(){tt=0;});
			$("#"+controlid+" .left").addClass("leftc");
			$("#"+controlid+" .right").removeClass("rightc");*/
			
		}
		var v=$("#"+controlid+" .jspage span.cur").index();
		if(v==p-1){
			v=0;	
		}
		else{
			v=v+1;
		}
		$("#"+controlid+" .jspage span").removeClass("cur");
		$("#"+controlid+" .jspage span:eq("+v+")").addClass("cur");
	})
	var is=true;
	af=setInterval(autoshow,10000);
	
	function autoshow(){

		if(is){
       	 $("#"+controlid+" .right").trigger("click");
		}
    }
	function stopTimer(){
		is=false;
	}
	function startTimer(){
		is=true;
	}
	$(".banjs").mouseover(function(){
		stopTimer();	
	})
	$(".banjs").mouseout(function(){
		startTimer();
	})
	$(".left").mouseover(function(){
		stopTimer();	
	})
	$(".left").mouseout(function(){
		 startTimer();
	})
	$(".right").mouseover(function(){
		stopTimer();	
	})
	$(".right").mouseout(function(){
		 startTimer();
	})
	
}
function contentqh(idname,navclass,conclass){
	$("#"+idname+" ."+navclass+" ul li").click(function(){
		$(this).addClass("cur").siblings("#"+idname+" ."+navclass+" ul li").removeClass("cur");
		$("#"+idname+" ."+conclass).eq($(this).index()).show().siblings("#"+idname+" ."+conclass).hide();

	})
}
function textareanr(classname,callbacktext){
	$("."+classname).focus(function(){
		if($(this).val()==callbacktext){
			
			$(this).val("");
		}
	})	
	$("."+classname).blur(function(){
		if($(this).val()==""){
			$(this).val(callbacktext);
		}
	})
	
}
/*左右切换*/
function qhcontrol(controlid,conid,n,page){
	var len=$("#"+conid+" li").length;
	var tt=0;
	if(page){
		var p=Math.ceil(len/n);
		for(i=0;i<p;i++){
			$("#"+controlid+" .jspage").append("<span><\/span>");
		}
		$("#"+controlid+" .jspage span").bind("click",function(){
			
			var f=$(this).index();
			var d=$("#"+controlid+" .jspage span.cur").index();
			var left=$("#"+conid).css("margin-left");
			left=left=="auto"?0:left;
			left=parseInt(left);
			
			$("#"+conid).animate({marginLeft:left-(f-d)*n*w},"1000","swing",function(){tt=0;});	
			$(this).addClass("cur").siblings("#"+controlid+" .jspage span").removeClass("cur");
		})
		
	}
	$("#"+controlid+" .jspage span:eq(0)").addClass("cur");
	var margin=parseInt($("#"+conid+" li").css("margin-right"))+parseInt($("#"+conid+" li").css("margin-left"));
	var padding=parseInt($("#"+conid+" li").css("padding-right"))+parseInt($("#"+conid+" li").css("padding-left"));
	var w=$("#"+conid+" li").width()+margin+padding;
	$("#"+conid).css("width",w*len);
	$("#"+controlid+" .left").addClass("leftc");
	var showWidth=$("#"+conid).parent().width();
	if(n>=len||w*len<=showWidth){
		$("#"+controlid+" .left").addClass("leftc");
		$("#"+controlid+" .right").addClass("rightc");
		}
	$("#"+controlid+" .left").click(function(){
		if($("#"+controlid+" .leftc").length<=0&&tt==0){			
			tt=1;
			var left=$("#"+conid).css("margin-left");
			left=left=="auto"?0:left;
			left=parseInt(left);
			$("#"+controlid+" .right").removeClass("rightc");
			$("#"+conid).animate({marginLeft:left+n*w},"1000","swing",function(){tt=0;});
			if(Math.abs(left)<=n*w){
				$("#"+controlid+" .left").addClass("leftc");
			}
			var v=$("#"+controlid+" .jspage span.cur").index();
			if(v==0){
				v=0;	
			}
			else{
				v=v-1;
			}
			$("#"+controlid+" .jspage span").removeClass("cur");
			$("#"+controlid+" .jspage span:eq("+v+")").addClass("cur");
			
		}
	})
	$("#"+controlid+" .right").click(function(){
		if($("#"+controlid+" .rightc").length<=0&&tt==0){
			tt=1;	
			var left=$("#"+conid).css("margin-left");
			left=left=="auto"?0:left;
			left=parseInt(left);
			$("#"+controlid+" .left").removeClass("leftc");
			$("#"+conid).animate({marginLeft:left-n*w},"1000","swing",function(){tt=0;});
			if(Math.abs(left)>=w*len-showWidth-(n-1)*w-w/2){
				$("#"+controlid+" .right").addClass("rightc");
			}
		}
		else{
			/*$("#"+conid).animate({marginLeft:0},"1000","swing",function(){tt=0;});
			$("#"+controlid+" .left").addClass("leftc");
			$("#"+controlid+" .right").removeClass("rightc");*/
			
		}
		var v=$("#"+controlid+" .jspage span.cur").index();
		if(v==p-1){
			v=0;	
		}
		else{
			v=v+1;
		}
		$("#"+controlid+" .jspage span").removeClass("cur");
		$("#"+controlid+" .jspage span:eq("+v+")").addClass("cur");
	})
	var t=setInterval(autoshow,10000);
	function autoshow(){
        //$("#"+controlid+" .right").trigger("click");
    }
}

function ajaxRequest(url,type,callbackfun){
/*ajax 请求通用方法*/
/*
  url:请求网址
  callback：请求成功后的回调函数
*/
	 $.ajax({
		url:url,
		type:type,
	    timeout:1000, 
		dataType:'json',
		beforeSend:function() {
			$(".loading").show();
		},
		complete:function(){
			$(".loading").hide();
		},
		success:function(result){
			callbackfun(result);
		}
	 })	
}

function seturl(url){
	$.cookie("purl",url);
}
function geturl(){
	return $.cookie("purl");
}
$(document).ready(function(e) {
	var w=$("body").width();
	var d=(parseInt(w)-300)/2;
	var h=document.documentElement.clientHeight;
    $(".myload").css({"left":d,"top":h/2});
    $(".mytips").css({"left":d,"top":h/2});
});

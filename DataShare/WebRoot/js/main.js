$(document).ready(function() {
	$.post('/DataShare/message/total.action', function(res) {
		$('.yinjian_left').html('<p>历史留言总量：<span>' + res[0].cou + '</span>&nbsp;&nbsp;历史回复总量：<span>' + res[0].sumCom + '</span>&nbsp;&nbsp;历史浏览总量：<span>' + res[0].sumBro + '</span></p><p>本月留言总量：<span>' + res[0].monthCou + '</span>&nbsp;&nbsp;本月回复总量：<span>' + res[0].monthSumCom + '</span>&nbsp;&nbsp;本月浏览总量：<span>' + res[0].monthSumBro + '</span></p>');
	});
	gesDataList();
});
function gesDataList() {
	$.post('/DataShare/message/boardTop10.action', function(res) {
		$('.yinjian_cont>ul').empty();
		$.each(res, function(index, val) {
			if (!val.comment_number) {
				val.comment_number = 0;
			}
			if (!val.browse_number) {
				val.browse_number = 0;
			}
			var tit = val.title;
			if(tit.length>=8){
				tit = tit.substring(0,8)+"...";
			}
			if(( index+1) % 5 == 0 ){
			$('.yinjian_cont>ul').append('<li style="margin-right:0px;"><a href="/DataShare/message/commentToJsp.action?board_id='+val.id+'"><b>' + tit + '</b></a><p class="grey2">评论量: <i class="red">' + val.comment_number + '</i> 条</p><p class="grey2">浏览量: <i class="red">' + val.browse_number + '</i> 条</p><p class="grey2">时&nbsp;&nbsp;&nbsp;间: <i class="red">' + val.createTimeStr.substring(0,10) + '</i></p></li>');
			}else{
			$('.yinjian_cont>ul').append('<li><a href="/DataShare/message/commentToJsp.action?board_id='+val.id+'"><b>' + tit + '</b></a><p class="grey2">评论量: <i class="red">' + val.comment_number + '</i> 条</p><p class="grey2">浏览量: <i class="red">' + val.browse_number + '</i> 条</p><p class="grey2">时&nbsp;&nbsp;&nbsp;间: <i class="red">' + val.createTimeStr.substring(0,10) + '</i></p></li>');
			}
			/*$('.yinjian_cont>ul').append('<li><a href="/DataShare/message/commentToJsp.action?board_id='+val.id+'"><b>' + tit + '</b></a><p class="grey2">评论量: <i class="red">' + val.comment_number + '</i> 条</p><p class="grey2">浏览量: <i class="red">' + val.browse_number + '</i> 条</p><p class="grey2">时&nbsp;&nbsp;&nbsp;间: <i class="red">' + val.createTimeStr.substring(0,10) + '</i></p></li>');*/
		});
	});
}

$("#ischeck").click(function(){
	if ($(this).prop("checked")) {
		//设置checked属性
		$("input[name='gltl']").prop("checked", true);
		$('.yinjian_ly .ly_ty input.ly_sub').addClass("selected").removeAttr("disabled");
	} else {
		//设置checked属性
		$("input[name='gltl']").prop("checked",false);
		$('.yinjian_ly .ly_ty input.ly_sub').removeClass("selected").attr("disabled","disabled");
	}
});
$('#yinjian_a').on('click', function(event) {
	//event.preventDefault();
	$('.yinjian_ly').slideToggle();
});

$('.yinjian_ly .ly_ty input.ly_sub').on('click', function(event) {
	//event.preventDefault();
	sub();
});

function sub() { //评论提交
	//event.preventDefault();
	$.ajax({
		url: '/DataShare/loginFlag.action',
		type: 'get',
		cache: false,
		dataType: 'json',
		success: function(data) {
			if (data[0]['code'] == 0) { //未登陆   弹出 用户名密码输入框
				$(".bg").css('display', 'block');
				$(".tck").css('display', 'block');
				$(".close").click(function() {
					$(".bg").css('display', 'none');
					$(".tck").css('display', 'none');
				});
			} else if (data[0]['code'] == 1) { //已经登陆过了，不做处理
				// alert('已经登陆过了，不需再登陆，此处可以去掉！');
				tjly();
			}
		},
		error: function() {
			/*alert("网络异常！");*/
			init( "网络异常" ,2 );
		}
	});
}
//提交表单
$("#submit").click(function() {
	var un = $('#userName').val();
	var pw = $('#userPwd').val();
	$.ajax({
		url: '/DataShare/login2.action',
		data: {
			userName: un,
			userPwd: pw
		},
		type: 'get',
		cache: false,
		dataType: 'json',
		success: function(data) {
			if (data['date'][0]['code'] == 1) {
				$(".bg").css('display', 'none');
				$(".tck").css('display', 'none');
				init( "登陆成功" ,0 );
/*				alert('登陆成功！');*/
			} else if (data['date'][0]['code']) {
				$(".cuowu").css('display', 'block');
				$("#msgid").text(data['date'][0]['msg']);
				//$(".kong").css('display','block');
			} else if (data['date'][0]['code'] == 0) {
				//$(".cuowu").css('display','block');
			}
		},
		error: function() {
			/*alert("网络异常！");*/
			init( "网络异常" ,2 );
		}
	});
});

function tjly() { //提交留言
	var title = $('.yinjian_ly input.ly_tit').val();
	var contents = $('.yinjian_ly textarea').val();
	var contact_mobile = $('.yinjian_ly .ly_lx input[name=phone]').val();
	var contact_people = $('.yinjian_ly .ly_lx input[name=name]').val();
	var parms = JSON.stringify({
		title: title,
		contents: contents,
		contact_mobile: contact_mobile,
		contact_people: contact_people
	});
	if (!$('.ly_ty input[name=gltl]').is(':checked')) {

	}
	// /DataShare/message/insertBoard.action?title=title&contents=contents&contact_mobile=contact_mobile&contact_people=contact_people

	/*判断标题超出20个字符*/

	 if(  title == ' ' || title == '' || title.substring(0, 5) == '请填写标题'){
		  init( "留言标题不能为空" ,2 );
		  return false;
	
	  }else if(  title.length >20){
		  init( "留言标题不能超过20个字符" ,2 );
		  return false;	 
	  }else if(  contents == ' ' || contents == '' || contents.substring(0, 7) == '请输入留言内容'){
		  init( "留言内容不能为空" ,2 );
		  return false;
	  }else if(  contents.length >2000){
		  init( "留言内容不能超过2000个字符" ,2 );
		  return false;
	  }else if(  contact_people === ' '  || contact_people === '' || contact_people.substring(0, 4) == '真实姓名'　){
		  init( "真实姓名不能为空" ,2 );
		  return false;
	   }else if(  contact_mobile == ' ' || contact_mobile == '' || contact_mobile.substring(0, 4) == '联系电话'){
	       init( "联系电话不能为空" ,2 );
	      return false;
	
	  }else{

	  	for(var i=0;i<contents.length;i++ ){
           contents = contents.replace("\n","</br>");
	  	}
	  	
		  //获取表格json数据
	  	  var str = "/DataShare/message/insertBoard.action?title=" + title + "&contents=" + contents + "&contact_mobile=" + contact_mobile + "&contact_people=" + contact_people + "";
	  	  str = encodeURI(str);
		  $.ajax({
			  url: str,
			  type: "get",
			  data: "json",
			  beforeSend: function() {
				  //$(".myload").show();
				  $('.zbg', window.parent.document).show();
				  $('.mytips', window.parent.document).show();
			  },
			  complete: function() {
				  setTimeout(function() {
					  $('.zbg', window.parent.document).hide();
					  $('.mytips', window.parent.document).hide();
					  $('.yinjian_ly input.ly_tit').val('');
					  $('.yinjian_ly textarea').val('');
					  $('.yinjian_ly .ly_lx input[name=phone]').val('');
					  $('.yinjian_ly .ly_lx input[name=name]').val('');
					  $('.yinjian_ly').slideToggle();
				  }, 1000);

			  }
		  }).done(function(data) {
			  gesDataList();
		  });
	  }

}
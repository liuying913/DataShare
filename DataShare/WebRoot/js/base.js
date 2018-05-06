$(document).ready(function() {
	getList();
	getList2();
	$('.plbtn').on('click',function(event) {
		event.preventDefault();
		console.log(333)
		if($(this)[0].className == 'plbtn active') {
			$('.jy_pl1').stop().animate({height:0},1000)
			$(this).removeClass('active')
		} else {
			$('.jy_pl1').stop().animate({height:230},1000)
			$(this).addClass('active')
		}
	})
});

function getList() {
	$.ajaxSettings.async = false;
	$.post('/DataShare/message/board.action?board_id=' + board_id, function(res) {
		$('#jy').empty();
		$.each(res, function(index, val) {
			$('#jy').append('<div class="jy_list"><h1 style="line-height: 28px;font-size: 20px;margin-top: 20px;line-height: 20px;letter-spacing: 1px;text-align:center">'
					+ val.title + '<span class="plbtn">评论</span></h1><h2><span>' + val.contact_people + '</span><span>' + val.createTimeStr + '</span><span>评论量： <b>'
					+ val.comment_number + '</b></span><span>浏览量： <b>' + val.browse_number + 
					'</b></span><span class="telAndName"></span></h2><p style="background:#f2f2f2;padding:5px 10px;line-height: 28px;color: #666;font-size: 15px;margin-top: 10px;line-height: 20px;letter-spacing: 1px;padding-left:10px;padding-right:10px;">'+ val.contents +'</p>');
					$('#jy').append('</div>');	

                     
					if(($('#userType').val()==1&&$('#userType').val()!=null)||($('#userType').val()!=1&&$('#userCName').val()==val.user_id)){
                        console.log(1);
						$('.telAndName').append('<span>联系人：' + val.contact_people + '</span><span>联系方式：' + val.contact_mobile + '</span>');
					}
						
		});
		$('#jy').append('<div class="jy_gf"><p><span class="h1">官方回复</span>您好！感谢您对GNSS数据资源共享与信息发布平台的宝贵意见，我们会认真阅读！</p></div><div class="jy_pl jy_pl1"><textarea placeholder="必填，请清晰叙述，字数控制在200字以内。"></textarea><button id="submitComment" calss="submitComment">提交评论</button></div>');
	});
	sub();
}


function sub() { //评论提交
	$('#submitComment').on('click', function(event) {
		event.preventDefault();
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
					var txt = $('#jy>.jy_pl>textarea').val();
					  for(var i=0;i<txt.length;i++ ){
	                    txt = txt.replace("\n","</br>");
		  	          }
					if (!txt) {
						 init( "请填写评论！" ,2 );
						return;
					}else if (txt.length > 200) {
						 init( "回复内容不能超过200个字符" ,2 );
						return;
					} else {
						// alert(board_id +" ddd "+txt);
						submit1(board_id, txt);
					}
					$('.jy_pl1').stop().animate({height:0},1000)
					$('.plbtn').removeClass('active')
				}
			},
			error: function() {
				alert("网络异常！");
			}
		});
	});
}

function submit1(board_id, contents) { //成功提交一级评论	
	contents = encodeURI(contents);
	var url = "/DataShare/message/insertComment.action?board_id=" + board_id + "&contents=" + contents;

	$.ajax({
		url: url,
		type: 'get',
		cache: false,
		dataType: 'json',
		beforeSend: function() {
			//$(".myload").show();
			$('.zbg', window.parent.document).show();
			$('.mytips', window.parent.document).show();
		},
		success: function(res) {
			if (res.date[0].code == 1) {
				// alert(res.date[0].msg);
				setTimeout(function() {
					$('.zbg', window.parent.document).hide();
					$('.mytips', window.parent.document).hide();
					$('#jy>.jy_pl>textarea').val('');
				}, 3000);
				getList2();
			}
		},
		error: function() {
			alert("网络异常！");
		}
	});
}


function getList2() { //一级评论
	$.ajaxSettings.async = false;
	$.post('/DataShare/message/comment.action?board_id=' + board_id, function(res) {
		$('#jy>.jy_pl_list').remove();
	
		$.each(res, function(index, val) {
			var html = "";
			html += '<div class="jy_pl_list" id=' + val.id + '>';
			html += '        <div class="jy_pl_list_main">';
			html += '            <img src="'+val.imgPath+'?v='+new Date().getTime()+'">';
			html += '            <div class="jy_pl_list_con">';
			html += '                <span class="user_id" data-id=' + val.user_id + '>' + val.user_id + '：</span>';
			html += '                <label>' + val.contents + '</label>';
			html += '            </div>';
			html += '            <div class="jy_pl_list_con2">';
			html += '                <span>' + val.createTimeStr + '</span>';
			html += '                <a href="#">回复</a>';
			if($('#userType').val()==1&&$('#userType').val()!=null){
				html += '                <a href="#" onclick="delete1('+board_id+','+val.id+')">删除</a>';
			}
			else if($('#userType').val()!=1&&$('#userCName').val()==val.user_id){
				html += '                <a href="#" onclick="delete1('+board_id+','+val.id+')">删除</a>';
			}
			html += '            </div>';
			html += '        </div>';
			html += '    </div>';
			$('#jy').append(html);
	
		});

	});
	getID();
}


function delete1(board_id,id){
	if(confirm("是删除回复")){
		$.post('/DataShare/message/deleteComment.action?board_id=' + board_id+'&commentid='+id,
		function(res) {
			alert(res.date[0].msg);
		});
		getList2();
	}
}
function delete2(id){
	if(confirm("是删除回复")){
		$.post('/DataShare/message/deleteParent.action?commentid='+id, 
		function(res) {
			alert(res.date[0].msg);
		});
		getList2();
	}
}

function getID() {
	$.each($('#jy>.jy_pl_list'), function(index, val) {
		getList3($(this).attr('id'));
	});
	submitTWO();
}

function getList3(id) { //二级评论
	$.post('/DataShare/message/comment.action?parentid=' + id, function(res) {
		$.each(res, function(index, val) {
			if ($('#' + id + '.jy_pl_list')) {
				var html = "";
				html += '        <div class="jy_pl_list_main2">';
				html += '            <img src="'+val.imgPath+'?v='+new Date().getTime()+'">';//var imagePath = "/DataShare/uploads/users/"+val.user_id+"/avatar_large.jpg?v="+new Date().getTime();
				html += '            <div class="jy_pl_list_con">';
				html += '                <span class="user_id" data-id=' + val.user_id + '>';
				html += '                    ' + val.user_id + ' </span><i>回复</i><span>';
				html += '                    ' + val.face_user_id + '：';
				html += '                </span>';
				html += '                <label>' + val.contents + '</label>';
				html += '            </div>';
				html += '            <div class="jy_pl_list_con2">';
				html += '                <span>' + val.createTimeStr + '</span>';
				html += '                <a href="#">回复</a>';
				if($('#userType').val()==1&&$('#userType').val()!=null){
					html += '                <a href="#" onclick="delete1('+board_id+','+val.id+')">删除</a>';
				}
				else if($('#userType').val()!=1&&$('#userCName').val()==val.user_id){
					html += '                <a href="#" onclick="delete1('+board_id+','+val.id+')">删除</a>';
				}
				html += '            </div>';
				html += '        </div>';
				$('#' + val.parentId + '.jy_pl_list').append(html);
			
			}
		});
	});
}

function submitTWO() { //二级评论
	var face_user_id;
	var parentId;
	$('.jy_pl_list').find('a').on('click', function(event) {
		event.preventDefault();
		if ($(this).parents('.jy_pl_list_main').find('.user_id').length === 0) {
			face_user_id = $(this).parents('.jy_pl_list_main2').find('.user_id').attr('data-id');
		} else {
			face_user_id = $(this).parents('.jy_pl_list_main').find('.user_id').attr('data-id');
		}
		parentId = $(this).parents('.jy_pl_list').attr('id');
		// if ($(this).parents('.jy_pl_list').find('.jy_pl').length === 0) {

		// }
		var obj = $('.jy_pl_list>.jy_pl');
		$('.jy_pl_list>.jy_pl').stop().animate({height:0},1000,function() {
			obj.remove();
		})
		$(this).parents('.jy_pl_list').append('<div class="jy_pl" style="display:none;"><textarea placeholder="必填，请清晰叙述，字数控制在200字以内。"></textarea><button id="submitTWO" calss="submitComment">回复</button></div>');
		$('.jy_pl_list>.jy_pl').slideDown();
		sub2(parentId, face_user_id);
	});
}

function sub2(parentId, face_user_id) {
	$('#submitTWO').on('click', function(event) {
		event.preventDefault();
		var txt = $('.jy_pl_list>.jy_pl>textarea').val();
            for(var i=0;i<txt.length;i++ ){
	             txt = txt.replace("\n","</br>");
		  	 }

		if (!txt) {
			//alert('请填写评论！');
			init( "请填写评论！" ,2 );
			return;
		}else if (txt.length > 200) {
			 init( "回复内容不能超过200个字符" ,2 );
		   return;
		} else {
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
						submit2(parentId, txt, face_user_id);
					}
				},
				error: function() {
					alert("网络异常！");
				}
			});

		}
	});
}

function submit2(parentId, contents, face_user_id) {
	var str = "/DataShare/message/insertComment.action?board_id="+board_id+"&parentId=" + parentId + "&contents=" + contents + "&face_user_id=" + face_user_id;
	str = encodeURI(str);
	var url = str;
	$.ajax({
		url: url,
		type: 'get',
		cache: false,
		dataType: 'json',
		beforeSend: function() {
			//$(".myload").show();
			$('.zbg', window.parent.document).show();
			$('.mytips', window.parent.document).show();
		},
		success: function(res) {
			if (res.date[0].code == 1) {
				// alert(res.date[0].msg);
				setTimeout(function() {
					$('.zbg', window.parent.document).hide();
					$('.mytips', window.parent.document).hide();
				}, 3000);
				
				$('.jy_pl_list>.jy_pl').remove();
			}
			$(".jy_pl_list_main2").remove();
			getID()
			//getList3();
		},
		error: function() {
			alert("网络异常！");
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
				alert('登陆成功！');
				window.location.reload();
			} else if (data['date'][0]['code']) {
				$(".cuowu").css('display', 'block');
				$("#msgid").text(data['date'][0]['msg']);
				window.location.reload();
				//$(".kong").css('display','block');
			} else if (data['date'][0]['code'] == 0) {
				//$(".cuowu").css('display','block');
				window.location.reload();
			}
		},
		error: function() {
			alert("网络异常！");
		}
	});
});
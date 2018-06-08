//提交表单
$("#submit").click(function () {
	
	var un = $('#userName').val();
	var pw = $('#userPwd').val();
	$.ajax({
            url:'/DataShare/login2.action',// 跳转到 action 具体的url地址
            data:{
                userName:un ,userPwd:pw //传输参数 多个参数用逗号分割
            },
            type:'get',//传输类型 post 或  get
            cache:false,
            dataType:'json',
            success:function(data) {
				//alert(data);
            if(data['date'][0]['code'] == 1){
				window.location.href="/DataShare/login.action?id=2";     //在同当前窗口中打开窗口
            } else if (data['date'][0]['code']){
				$(".cuowu").css('display','block');
				$("#msgid").text(data['date'][0]['msg']);
                //$(".kong").css('display','block');
            }else if (data['date'][0]['code'] == 0){
                //$(".cuowu").css('display','block');
            }

            },
            error : function() {
                // view("异常！");
                alert("网络异常！");
            }
    });
})								

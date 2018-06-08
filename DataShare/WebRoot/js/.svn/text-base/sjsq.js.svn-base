//提交表单
$("#sjsq_from").click(function () {
	$.ajax({
            url:'/DataShare/loginFlag.action',// 跳转到 action 具体的url地址
            type:'get',//传输类型 post 或  get
            cache:false,
            dataType:'json',
            success:function(data) {
                if(data[0]['code'] == 0){
					$(".bg").css('display','block');
					$(".tck").css('display','block');
					$(".close").click(function(){
						$(".bg").css('display','none');
						$(".tck").css('display','none');
					});
					
					$("#submit").click(function(){
						//window.location.href="/DataShare/applyMain.jsp";     //在同当前窗口中打开窗口
					});
                    //alert("弹出登录框");
//						window.location.href="/DataShare/applyMain.jsp";     //在同当前窗口中打开
                }else if (data[0]['code'] == 1){
                   // alert("直接进入系统");
					window.location.href="/DataShare/login.action?id=2";     //在同当前窗口中打开窗口
					$(".bg").css('display','block');
					$(".tck").css('display','block');
					$(".close").click(function(){
						$(".bg").css('display','none');
						$(".tck").css('display','none');
					});
                }
            },error : function() {
                // view("异常！");
                alert("网络异常！");
            }
     });
})
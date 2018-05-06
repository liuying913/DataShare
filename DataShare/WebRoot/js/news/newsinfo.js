function modifyNews(postData) {

	var ret = null;
	$.ajax({
		type : 'post',
		url : "/DataShare/news/modifyNews.action",
		// contentType: "application/json;charset=UTF-8",
		"contentType" : "application/x-www-form-urlencoded; charset=utf-8",
		dataType : 'json',
		async : false,
		data : postData,
		success : function(res) {

			if (res){
				alert("修改成功");
				//init( "保存成功！" ,0 );	
				//window.location.href = "/DataShare/manager/newsInfoListToJsp.action?applyTitle=新闻管理";
			}else{
				alert("修改失败");
			   //init( "网络错误，保存失败！" ,2 );
			}
		},
		error : function(xhr, textStatus, errorThrown) {
			//alert("网络错误，保存失败！");
			init( "网络错误，保存失败！" ,2 );
		}
	});
	return ret;
}

function insertNews(postData) {

	postData["id"] = -1;
	var ret = null;
	$.ajax({
				type : 'post',
				url : "/DataShare/news/insertNewsoper.action",
				"contentType" : "application/x-www-form-urlencoded; charset=utf-8",
				dataType : 'json',
				async : false,
				data : postData,
				success : function(res) {
					if (res){
						//init( "添加完成" ,0 );
						//init( "保存成功！" ,0 );
						alert("保存成功");
						window.location.reload();
						//window.location.href = "/DataShare/manager/newsInfoListToJsp.action?applyTitle=新闻管理";
					    //$(".confirm",window.parent.document).click( function(){
                        //window.location.href = "/DataShare/manager/newsInfoListToJsp.action?applyTitle=新闻管理";
					    //})
					}else
					    //init( "网络错误，保存失败！" ,2 );
						alert("保存失败");
				},
				error : function(xhr, textStatus, errorThrown) {
					//alert("网络错误，保存失败！");
					init( "网络错误，保存失败！" ,2 );
				}
			});
	return ret;
}

function operNews() {
	
	var newsid = $("#newsID").val();
	var title = $("#titleContainer").val();
	var orders = $("#orderContainer").val();
	var content = editor.getContent();
	var time = $("#timeContainer").val();
	if (title == null || title == "") {
		alert("新闻标题不能为空！");
		init( "新闻标题不能为空！" ,2 );
		return;
	}
	if (content == null || content == "" ) {
		alert("新闻内容不能为空！");
		init( "新闻内容不能为空！" ,2 );
		return;
	}

	var postData = {
		"id" : newsid,
		"title" : title,
		"content" : content,
		"orders" : orders,
		"time" : time
	};
	if (operType == "update") {
		modifyNews(postData);
	} else if (operType == "insert") {
		insertNews(postData);
	} else {
		//alert("网络错误，保存失败！");
		init( "网络错误，保存失败！" ,2 );
	}
}

function setMainPage(){
	alert("设为主页失败，请在浏览器里手动设置。");
}
function setCollect(){
	alert("加入收藏夹失败，请在浏览器里手动设置。");
}

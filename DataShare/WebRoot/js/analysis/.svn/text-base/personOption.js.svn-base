function function_UserList() {
	var str="";
	$.ajax({
		url: "/DataShare/queryAllUserInfoList.action",
		type: "get",
		data: "json",
	}).done(function (data) {
		str=str+"<option value='all'>全部</option>"
		$.each(data,function($k,$value){
			str=str+"<option value='"+$value.userId+"'>"+$value.userName+"</option>"
		})
		$("#userId").html(str);

			 $("#userId").selectWidget({
				change  : function (changes) {
					return changes;
				},
				effect       : "slide",
				keyControl   : true,
				speed        : 200,
				scrollHeight : 250
			});
	});
};


function function_UserNameList() {
	var str="";
	$.ajax({
		url: "/DataShare/queryAllUserInfoList.action",
		type: "get",
		data: "json",
	}).done(function (data) {
		str=str+"<option value='all'>全部</option>"
		$.each(data,function($k,$value){
			str=str+"<option value='"+$value.userName+"'>"+$value.userName+"</option>"
		})
		$("#userId").html(str);

			 $("#userId").selectWidget({
				change  : function (changes) {
					return changes;
				},
				effect       : "slide",
				keyControl   : true,
				speed        : 200,
				scrollHeight : 250
			});
	});
};

//地震应急事件列表
function function_earthQuakeList() {
	var str="";
	$.ajax({
		url: "/DataShare/showData/contingencyEventList.action",
		type: "get",
		data: "json",
	}).done(function (data) {
		str=str+"<option value='all'>全部</option>"
		$.each(data,function($k,$value){
			str=str+"<option value='"+$value.id+"'>"+$value.name+"</option>"
		})
		$("#eventId").html(str);
	});
};


//地震应急事件列表
function function_earthQuakeNoAllList(year) {
	var str="";
	$.ajax({
		url: "/DataShare/showData/contingencyEventList.action?year="+year,
		type: "get",
		data: "json",
	}).done(function (data) {
		$.each(data,function($k,$value){
			str=str+"<option value='"+$value.id+"'>"+$value.name+"</option>"
		})
		$("#eventId").html(str);
	});
};



//地震应急事件列表  2017年
function earthQuakeListOver2017(year) {
	var str="";
	$.ajax({
		url: "/DataShare/earthQuake/earthQuakeListOver2017.action?year="+year,
		type: "get",
		data: "json",
	}).done(function (data) {
		$.each(data,function($k,$value){
			str=str+"<option value='"+$value.id+"'>"+$value.name+"</option>"
		})
		$("#eventId").html(str);
	});
};

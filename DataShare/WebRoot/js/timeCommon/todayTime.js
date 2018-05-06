function getYearOne(){//获得当年第一天
	var today=new Date();
	year = today.getFullYear();
	
	var iniStartTime = year+"-01-01";
	return iniStartTime;
}

//获得当月第一天
function getMonthOne(){//获得今天
	var today=new Date();
	year = today.getFullYear();
	
	month = today.getMonth()+1;
	if(month<10){month="0"+month;}
	
	var iniEndTime = year+"-"+month+"-01";
	return iniEndTime;
}

function getToday(){//获得今天
	
	var today=new Date();
	year = today.getFullYear();
	
	month = today.getMonth()+1;
	if(month<10){month="0"+month;}
	
	date = today.getDate();
	if(date<10){date="0"+date;}
	
	var iniEndTime = year+"-"+month+"-"+date;
	return iniEndTime;
}

function getMonthSelect(monthId){//获得当月
	var today=new Date();
	month = today.getMonth()+1;
/*	if(month<10){month="0"+month;}
	$(monthId).val(month);*/

	
	$(monthId).find("option").each(function(){
		if( month == $(this).val() ){
           $(this).attr({selected:"selected"})
		}
	})

	return month;
}

function getYearSelect(yearId){
	var today=new Date();
	var year = today.getFullYear();
	for(var i=year;i>=2012;i--){
		$(yearId).append("<option value='"+i+"'>"+i+"</option>");
	}
}

function getYearSelect2017over(yearId){
	var today=new Date();
	var year = today.getFullYear();
	for(var i=year;i>=2017;i--){
		$(yearId).append("<option value='"+i+"'>"+i+"</option>");
	}
}

function getSelectToday(todayId){//选中今天
	var today=new Date();
	year = today.getFullYear();
	
	month = today.getMonth()+1;
	if(month<10){month="0"+month;}
	
	date = today.getDate();
	if(date<10){date="0"+date;}
	
	var iniEndTime = year+"-"+month+"-"+date;
	$(todayId).val(iniEndTime); //结束时间赋值
}

function getYearFlagOrNo(startTime,endTime){
	startTime = startTime.substr(0, 4);
	endTime = endTime.substr(0, 4);
	if(startTime!=endTime){
		alert("时间选取不能跨年！");
		return false;
	}else{
		return true;
	}
}

function showtime(){
	var today,hour,second,minute,year,month,date;
	var strDate ;
	today=new Date();
	var n_day = today.getDay();
	switch (n_day){
	    case 0:{
	      strDate = "星期日"
	    }break;
	    case 1:{
	      strDate = "星期一"
	    }break;
	    case 2:{
	      strDate ="星期二"
	    }break;
	    case 3:{
	      strDate = "星期三"
	    }break;
	    case 4:{
	      strDate = "星期四"
	    }break;
	    case 5:{
	      strDate = "星期五"
	    }break;
	    case 6:{
	      strDate = "星期六"
	    }break;
	    case 7:{
	      strDate = "星期日"
	    }break;
	}
	year = today.getFullYear();
	month = today.getMonth()+1;
	date = today.getDate();
	hour = today.getHours();
	minute =today.getMinutes();
	second = today.getSeconds();
	document.getElementById('time').innerHTML = year + "年" + month + "月" + date + "日    " + strDate +" " + hour + ":" + minute + ":" + second; //显示时间
	setTimeout("showtime();", 1000); //设定函数自动执行时间为 1000 ms(1 s)
}
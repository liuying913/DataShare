﻿//=====分页
function pages(urls) {
	$.ajax({
		url: urls,
		type: "get",
		data: "json",
	}).done(function (data) {
		fenyedata = data;
		shua();
		fenye();
	})
}
var pageFlag=true;
function fenye() {
    data = fenyedata['newsList'][0]['News'];
    var pages = (data.length % num_value==0? data.length / num_value: Math.ceil(data.length / num_value));
    $("#zongyeshu")[0].innerHTML = pages;       //总共页数
    $("#zongshuju")[0].innerHTML = data.length;      //一共有多少条数据
	
	//默认显示的五个界面
	$(".everypages").empty();
    $("<a href='#' onclick='yeshu(this)';>").text(shu).appendTo(".everypages");

    //下拉列表
    if(pageFlag==true){
    	for (var i = 1; i <= pages; i++) {
            $(".optionpages").append("<option value="+i+">" + i + "</option>");//跳转页面
        }
    	pageFlag=false;
    }
    $(".everypages").children()[0].style.color="red";
}


var fenyedata,num=0,shu=1,num_value=5;

function shua(){
	//默认显示的五个界面
	$(".everypages").empty();
    $("<a href='#' onclick='yeshu(this)';>").text(shu).appendTo(".everypages");
	
    data = fenyedata['newsList'][0]['News'];
    var numb=num;
    $(".newlist")[0].innerHTML="";
	
	var thisNum = shu*num_value;
	
    for(var i=numb;i<thisNum;i++){//img\pcgzs\djxz.jpg
    	var hrefs = '/DataShare/news/newsDetailToJsp.action?id='+data[i]['id'];
        var box = $("<li>").appendTo(".newlist");
        $("<a href="+hrefs+">").text(data[i]['docName']).appendTo(box);
        //$("<span>").text(data[i]['docName']).appendTo(box);
        $("<img src='/DataShare/img/pcgzs/djxz.jpg'>").appendTo(box);
    }

}

function yeshu(div) {
    if(!isNaN(div)){
        shub=div;
    }else{
        if(div=='end'){
            shub=(data.length % num_value==0? data.length / num_value: Math.ceil(data.length / num_value));
        }else{var shub=parseInt(div.innerText);}
    }
    shu=shub;
    num=num_value*(shub-1);
    shua();
}

function next() {
    shu++;
    num=num_value*(shu-1);
    if(num>data.length){return;}
    shua();
}

function shang() {
    shu--;
    if(shu<=0){shu=1;return;}
    num=num_value*(shu-1);
    shua();
}


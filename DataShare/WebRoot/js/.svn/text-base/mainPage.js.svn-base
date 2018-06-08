//=====分页
function pages(urls) {
	$.ajax({
		url: urls,
		type: "get",
		data: "json"
	}).done(function (data) {
		fenyedata = data;
		shua();
		fenye();
	})
}

function fenye() {
    data = fenyedata;
    var pages = (data.length % num_value==0? data.length / num_value: Math.ceil(data.length / num_value));
    $("#zongyeshu")[0].innerHTML = pages;       //总共页数
    $("#zongshuju")[0].innerHTML = data.length;      //一共有多少条数据
	
	//默认显示的五个界面
	$(".everypages").empty();
    $("<a href='javascript:void(0)' onclick='yeshu(this)';>").text(shu).appendTo(".everypages");

    //下拉列表
    var pageFlag = true;
    if(pageFlag){
    	for (var i = 1; i <= pages; i++) {
            $(".optionpages").append("<option value="+i+">" + i + "</option>");//跳转页面
        }
    	pageFlag=false;
    }
    var browser = navigator.appName;
    var b_version = navigator.appVersion;
    var version = b_version.split(";");
    if (version.length > 1) {
        var trim_Version = parseInt(version[1].replace(/[ ]/g, "").replace(/MSIE/g, ""));
        if (trim_Version < 9) {
            $(".everypages").append("<a style='color: red;' onclick='yeshu(this)' href='javascript:void(0)' >"+shu+"</a>")
        }else{
            $(".everypages").children()[0].style.color="red";
        }
    }
    //$(".everypages").children()[0].style.color="red";
}

var fenyedata,num=0,shu=1,num_value=5;

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
function shua() {
    //默认显示的五个界面
    $(".newlist").empty();
    $(".everypages").empty();
    $("<a href='javascript:void(0)' onclick='yeshu(this)';>").text(shu).appendTo(".everypages");
    data = fenyedata;
    var numb = num;
    $(".newlist")[0].innerHTML = "";

    var thisNum = shu * num_value;

    for (var i = numb; i < thisNum; i++) {
        if (!data[i].comment_number) {data[i].comment_number=0;}
        if (!data[i].browse_number) {data[i].browse_number=0;}
        
        var cont = data[i].contents;
		if(cont.length>=150){
			cont = cont.substring(0,150)+"...";
		}
        $('.newlist').append('<li><h2><b><a href=/DataShare/message/commentToJsp.action?board_id='+data[i].id+'>'+data[i].title+'</a></b> </h2> <span class="grey2"> '+data[i].user_name+'</span><span   style="margin-left: 15px;">'+data[i].createTimeStr+'</span><span   style="margin-left: 15px;">评论量：<span style="color:#35a1ff;">'+data[i].comment_number+'</span></span><span  style="margin-left: 15px;">浏览量：<span style="color:#35a1ff;">'+data[i].browse_number+'</span></span> <p>'+cont+'<a href="/DataShare/message/commentToJsp.action?board_id='+data[i].id+'"> <i class="red" style="font-size: 14px;">[查看全文]</i></a></p>  <em class="grey2" id="ops_share"></em> </li>');
       // $('.newlist').append('<li><h2><b><a href=/DataShare/message/commentToJsp.action?board_id='+data[i].id+'>'+data[i].title+'</a><em>|&nbsp;&nbsp;评论量：'+data[i].comment_number+'</em><em>|&nbsp;&nbsp;浏览量：'+data[i].browse_number+'</em></b> </h2>  <span class="grey2"> '+data[i].user_name+' 　　'+data[i].createTimeStr+'</span><p>'+cont+'<a href="/DataShare/message/commentToJsp.action?board_id='+data[i].id+'"> <i class="red">[查看全文]</i></a></p>  <em class="grey2" id="ops_share"></em> </li>');
        // var hrefs = pageUrl;
        // var box = $("<li>").appendTo(".newlist");
        // $("<a href=" + hrefs + ">").text(data[i]['title']).appendTo(box);
        // $("<span>").text(data[i]['createTimeStr']).appendTo(box);
    }
}
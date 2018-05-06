var data;
var num=0;
var shu=1;//第几页
var num_value=5;//每页显示的数量
var pageFlag=true;
var pages = 0;//总页数
function page(){
	$(".everypages").empty();
	//$(".everypages").text("");
    pages = (data.length % num_value==0? data.length / num_value: Math.ceil(data.length / num_value));
    $("#zongyeshu")[0].innerHTML = pages;       //总共页数
    $("#zongshuju")[0].innerHTML = data.length;      //一共有多少条数据
    
    //默认显示的五个界面
    //for (var i = shu; i < shu+5; i++) {
        $("<a href='javascript:void(0)' onclick='yeshu(this)';>").text(shu).appendTo(".everypages");
    //}
    //下拉列表
    if(pageFlag==true){
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
}
var flag=0;
function yeshu(div) {
    if(!isNaN(div)){
        shub=div;
    }else{
        if(div=='end'){
            shub=(data.length % num_value==0? data.length / num_value: Math.ceil(data.length / num_value));
        }else{
        	var shub=parseInt(div.innerText);
        }
    }
    shu=shub;
    num=num_value*(shub-1);
    shua();
}

function next() {
	if(pages==shu){return;}//已经是最后一页了
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



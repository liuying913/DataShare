var data;
var num=0;
var shu=1;//第几页
var num_value=5;//每页显示的数量
var pageFlag=true;
function page(){
	$(".everypages").empty();
	//$(".everypages").text("");
    var pages = (data.length % num_value==0? data.length / num_value: Math.ceil(data.length / num_value));
    $("#zongyeshu")[0].innerHTML = pages;       //总共页数
    $("#zongshuju")[0].innerHTML = data.length;      //一共有多少条数据
    
    //默认显示的五个界面
    //for (var i = shu; i < shu+5; i++) {
        $("<a href='#' onclick='yeshu(this)';>").text(shu).appendTo(".everypages");
    //}
    //下拉列表
    if(pageFlag==true){
    	for (var i = 1; i <= pages; i++) {
            $(".optionpages").append("<option value="+i+">" + i + "</option>");//跳转页面
        }
    	pageFlag=false;
    }
    $(".everypages").children()[0].style.color="red";
}
var flag=0;
function shua(){
	if(flag!=0){page();}else{flag=1;}
	
	str="";
    $.each(data, function(key, value) {
    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
			if(key%2!=0){
				str=str+"<tr class='even'>"
				+"	    <td>"+value.personName+"</td>"
				+"	    <td>"+value.telPhone+"</td>"
				+"	    <td>"+value.content+"</td>"
				+"	    <td>"+value.timeStr+"</td>"
				+"	    <td><a href='/DataShare/idea/ideaDetailToJsp.action?id="+value.id+"'>查看</a>&nbsp;<a href='/DataShare/idea/deleteIdeaToJsp.action?id="+value.id+"'>删除</a></td>"
				+"    </tr>"
			}
			else{
				str=str+"<tr>"
				+"	    <td>"+value.personName+"</td>"
				+"	    <td>"+value.telPhone+"</td>"
				+"	    <td>"+value.content+"</td>"
				+"	    <td>"+value.timeStr+"</td>"
				+"	    <td><a href='/DataShare/idea/ideaDetailToJsp.action?id="+value.id+"'>查看</a>&nbsp;<a href='/DataShare/idea/deleteIdeaToJsp.action?id="+value.id+"'>删除</a></td>"
				+"    </tr>"
			}
	  	}
	});
	$("#tbody").html(str); 
	$('#iframe', parent.document).css("height",$("#iframeheight").height());
}
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



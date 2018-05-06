var data,num=0,shu=1,num_value=5;
function page(){
    var pages = (data.length % num_value==0? data.length / num_value: Math.ceil(data.length / num_value));
    $("#zongyeshu")[0].innerHTML = pages;       //总共页数
    $("#zongshuju")[0].innerHTML = data.length;      //一共有多少条数据
     for (var i = 1; i <= pages; i++) {
        $("<a href='#' onclick='yeshu(this)';>").text(i).appendTo(".everypages");  //每一个页面
        $(".optionpages").append("<option value="+i+">" + i + "</option>");   //跳转页面
    }
    $(".everypages").children()[0].style.color="red";
}
function shua(){
	str="";
	var numb=num;
    $.each(data, function(key, value) {
    	if(key>=numb){
			if(key%2!=0){
				str=str+"<tr class='even'>"
				+"	    <td>"+value.apply_Person+"</td>"
				+"	    <td>"+value.apply_Unit+"</td>"
				+"	    <td>"+value.apply_Phone+"</td>"
				+"	    <td>"+value.result_Name+"</td>"
				+"	    <td>"+value.result_Money+"</td>"
				+"	    <td>"+value.result_Person+"</td>"
				+"	    <td>"+value.result_from+"</td>"
				+"	    <td><a href='#'>查看</a></td>"
				+"    </tr>"
			}
			else{ 
				str=str+"<tr>"
				+"	    <td>"+value.apply_Person+"</td>"
				+"	    <td>"+value.apply_Unit+"</td>"
				+"	    <td>"+value.apply_Phone+"</td>"
				+"	    <td>"+value.result_Name+"</td>"
				+"	    <td>"+value.result_Money+"</td>"
				+"	    <td>"+value.result_Person+"</td>"
				+"	    <td>"+value.result_from+"</td>"
				+"	    <td><a href='#'>查看</a></td>"
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
        }else{var shub=parseInt(div.innerText);}
    }
    var chi=$(".everypages").children();
    for(var i=0;i<chi.length;i++){
        chi[i].style.color="black";
    }
    chi[shub-1].style.color="red";

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



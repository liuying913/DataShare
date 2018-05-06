/*****显示省份*****/
function sheng(parendId,id) {
    $.ajax(
    {
        type: "post",
        url: "/jspxcms/jsp/javascript/json/sheng.json",
        dataType:"json",
        success: function (msg) {        	
            for (var i = 0; i < msg.data.length; i++) {
                $("#sheng").append("<option value=" + msg.data[i].id + " id="+msg.data[i].id+">" + msg.data[i].name + "</option>");
            }
            ////设置省
			if(null==parendId){//市显示该省份下 全部
				$("#sheng").find("option[id="+id+"]").attr("selected",true);
				shi(null);
			    }else{
			    	$("#sheng").find("option[id="+parendId+"]").attr("selected",true);
			    	shi(id);
			    }
        }
    })
};

 /*****显示省份对应的市*****/
function shi(shiId) {
    $("#shi").html("");
    var newCity = $('#sheng option:selected').attr("id");
    if(newCity==377||newCity==378||newCity==379){
    	$("#shi").hide();
    	return false;
    }else{
    	$("#shi").show();
	    $.ajax(
	    {
	        type: "get",
	        url: "/jspxcms/jsp/javascript/json/subregion/"+newCity+".json",
	        dataType:"json",
	        success: function (msg) {
	            for (var i = 0; i < msg.data.length; i++) {
	                $("#shi").append("<option value=" + msg.data[i].id + " qu="+msg.data[i].id+">" + msg.data[i].name + "</option>");
	            }
	            if(null==shiId){
	            	$("#shi").prepend('<option value="1000" selected="selected">全部</option>');
	            }else{
	            	// $("#shi").find("option[qu="+shiId+"]").attr("selected",true);
	            }
	        }
	    })
    }
};


/********显示省份对应的区*********/
// function qu() {
//     $("#qu").html("");
//     $.ajax(
//     {
//         type: "post",
//         url: "../javascript/json/qu.json",
//         dataType:"json",
//         // data: { "type": "district","cityID":$('#S2').attr("value") }, //在服务器端查询
//         success: function (msg) {
//             var newQu = $('#shi option[value='+$('#shi').attr("value")+']').attr("qu");
//             // alert(newQu);
//             var newMsg = msg[newQu];
//             for (var i = 0; i < newMsg.length; i++) {
//                 $("#qu").append("<option value=" + newMsg[i].value + ">" + newMsg[i].name + "</option>");
//             }
//         }
//     })
// };
/*********行业*********/
function hangye(dateStr){
    $.ajax(
    {
        type: "post",
        url: "/jspxcms/queryInd.jspx",
        dataType:"json",
        success: function (msg) {
            var newMsg = msg.data;
            for (var i = 0; i < newMsg.length; i++) {
                $("#hangye").append("<option value=" + newMsg[i].industryId + ">" + newMsg[i].industryName + "</option>");
            }
            setGfpIndustryId(dateStr);
        }
    })
}

/*********年限*********/
function nian(dateStr){
    $.ajax(
    {
        type: "post",
        url: "/jspxcms/queryProAge.jspx",
        dataType:"json",
        success: function (msg) {
            var newMsg = msg.data;
            for (var i = 0; i < newMsg.length; i++) {
                $("#nian").append("<option value=" + newMsg[i].professionageid + ">" + newMsg[i].content + "</option>");
            }
            setObjectOtherSelect(dateStr,"nian");
        }
    })
}
/*********教育*********/
function edu(dateStr){
    $.ajax(
    {
        type: "post",
        url: "/jspxcms/queryDeg.jspx",
        dataType:"json",
        success: function (msg) {
            var newMsg = msg.data;
            for (var i = 0; i < newMsg.length; i++) {
                $("#edu").append("<option value=" + newMsg[i].degreeId + ">" + newMsg[i].content + "</option>");
            }
            setObjectOtherSelect(dateStr,"edu");
        }
    })
}

/*********关心的问题*********/
function question(dateStr){
    $.ajax(
    {
        type: "post",
        url: "/jspxcms/queryConc.jspx",
        dataType:"json",
        success: function (msg) {
            var newMsg = msg.data;
            for (var i = 0; i < newMsg.length; i++) {
                $("#question").append("<option value=" + newMsg[i].concernId + ">" + newMsg[i].content + "</option>");
            }
            setObjectOtherSelect(dateStr,"question");
        }
    })
}

/********职业********/
function professionId(dateStr){
    $.ajax(
    {
        type: "post",
        url: "/jspxcms/queryPros.jspx",
        dataType:"json",
        success: function (msg) {
            var newMsg = msg.data;
            for (var i = 0; i < newMsg.length; i++) {
                $("#professionId").append("<option value=" + newMsg[i].professionId + ">" + newMsg[i].content + "</option>");
            }
            setProfessionId(dateStr);
        }
    })
}
/****验证参数****/
function validate_info(){
    $("#commentForm").validate({
        /******定义规则******/
    rules: {
        userName: {
            required: true,
            minlength: 2
          },
        gender: {
            required: true
          },
        email: {
            required: true,
            email:true
          },
        mobile: {
            required: true
          },
        professionId:{

        },
        gfpIndustryId:{

        },
        professionAgeId:{

        },
        company:{

        },
        degreeid:{

        },
        concernid:{

        },
        concernid:{

        },
        userpic:{

        }
    },
        /******自定义错误提示******/
    messages: {
      userName: {
        required: "请输入用户名",
        minlength: "用户名必需由两个字母组成"
      },
      email: "请输入一个正确的邮箱"
    },
    //message结束
    //自定义提交事件
    submitHandler:function(form){
        // alert("验证通过，提交事件!");   
        // var aaa = $("#commentForm").serialize();
        // alert(aaa);
        // form.submit();
        //// jspxcms/manageInfo.jspx?regionId=.value
        $.ajax({
            type:"post",
            url:"/jspxcms/updateUserInfo.jspx",
            data:$("#commentForm").serialize(),
            dataType:"json",
            success:function(msg){
            	if(msg.result=='OK'){
            		if(msg.data[0].addScore==0){
            			basetip("信息保存成功！");
            		}else{            			
            			basetip("信息保存成功！积分 +"+msg.data[0].addScore);
            		}
            	}else{
            		basetip("保存失败");
            	}
            },
            error:function(e){
            	basetip("保存失败");
            }
        })

    }
  });
}


//上传图片预览
function setImagePreview(avalue) {
var docObj=document.getElementById("userpic");
 
var imgObjPreview=document.getElementById("preview");
if(docObj.files &&docObj.files[0])
{
//火狐下，直接设img属性
imgObjPreview.style.display = 'block';
imgObjPreview.style.width = '150px';
imgObjPreview.style.height = '180px'; 
imgObjPreview.src = docObj.files[0].getAsDataURL();
 
//火狐7以上版本不能用上面的getAsDataURL()方式获取，需要一下方式
imgObjPreview.src = window.URL.createObjectURL(docObj.files[0]);
}
else
{
//IE下，使用滤镜
docObj.select();
var imgSrc = document.selection.createRange().text;
var localImagId = document.getElementById("localImag");
//必须设置初始大小
localImagId.style.width = "150px";
localImagId.style.height = "180px";
//图片异常的捕捉，防止用户修改后缀来伪造图片
try{
localImagId.style.filter="progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale)";
localImagId.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = imgSrc;
}
catch(e)
{
alert("您上传的图片格式不正确，请重新选择!");
return false;
}
imgObjPreview.style.display = 'none';
document.selection.empty();
}
return true;
}


function setGender(dateStr){//性别
	    var gender = document.getElementsByName("gender");
	    for(var i=0;i<gender.length;i++) {//循环
	         if(gender[i].value==dateStr){  //比较值
	             gender[i].checked=true; //修改选中状态
	             break;
	         }
	    }
	}
	function setBirthYear(birthDays){//修改生日
		var bYear = birthDays.substring(0, birthDays.indexOf("-"));
		var sel_year = document.getElementById("sel_year");
		var divIdObject = document.getElementById("sel_year");
	    for (var i = 0; i < sel_year.options.length; i++) {        
	        if (sel_year.options[i].text == bYear) {        
	        	sel_year.options[i].selected = true;             
	            break;        
	        }        
	    }
	}
	function setBirthMonth(birthDays){//修改生日
		birthDays = birthDays.substring(birthDays.indexOf("-")+1, birthDays.length);
		var bMonth = birthDays.substring(0, birthDays.indexOf("-"));
		if(bMonth.substring(0, 1)=="0"){
			bMonth=bMonth.substring(1, 2);
		}
		var sel_month = document.getElementById("sel_month");
	    for (var i = 0; i < sel_month.options.length; i++) {        
	        if (sel_month.options[i].text == bMonth) {        
	        	sel_month.options[i].selected = true;                    
	        }        
	    }
	    var bDay = birthDays.substring(birthDays.indexOf("-")+1, birthDays.length);
	    bDay = birthDays.substring(birthDays.indexOf("-")+1,birthDays.indexOf(" "));
	    if(bDay.substring(0, 1)=="0"){bDay=bDay.substring(1, 2);}
	    var sel_day = document.getElementById("sel_day");
	    for (var i = 0; i < sel_day.options.length; i++) {        
	        if (sel_day.options[i].text == bDay) {        
	        	sel_day.options[i].selected = true;                    
	        }        
	    }
	}
	
	
	function setObjectOtherSelect(dataStr,divId){
		var divIdObject = document.getElementById(divId);
	    for (var i = 0; i < divIdObject.options.length; i++) {
	        if (divIdObject.options[i].value == dataStr) {        
	        	divIdObject.options[i].selected = true;             
	            break;        
	        }        
	    }
	}


	//职业
	function setProfessionId(dateStr){
	    setObjectOtherSelect(dateStr,"professionId");
	}
	
	//行业
	function setGfpIndustryId(dateStr){
		setObjectOtherSelect(dateStr,"hangye");
	}
	
	///日期
	function setDay(day){
        if(day){
            var aDay = [];
    		var day1 = day.split("-");
            var day2 = day1[2].split(" ");
            aDay.push(day1[0],day1[1],day2[0]);
            $("#sel_year").attr("rel",aDay[0]);
            $("#sel_month").attr("rel",aDay[1]);
            $("#sel_day").attr("rel",aDay[2]);
        }            

	}

////页面提示
	function basetip(tipmsg){
		$("body").append('<p id="oktip">'+tipmsg+'</p>');
		$("#oktip").show();
		setTimeout(function(){
	        $("#oktip").remove();
	    },2000);	
	}



////积分提示
	function addScore(e,scoreid){
		$.ajax({
		      type:"get",
		      url:"/jspxcms/getScore.jspx",
		      data:'reasonid='+scoreid,
		      dataType:"json",
		      success:function(msg){
		      	if(msg.data[0].addScore==0){		      		
		      		// window.location.reload();
		      		$("#avatarSubmit").css("display","none");
		      	}else{
		      		bonusPoints(e,msg.data[0].addScore);
		      	}
		      },
		      error:function(e){
		    	  console.log(e);
		      }
		  })
		
	}
	function bonusPoints(e,n) {
		
	    var $i = $("<b id='eeeee'>").text("+" + n);
	    var x = $("#avatarSubmit").offset().left;
	    var y = $("#avatarSubmit").offset().top;    
	    $i.css({
	        top: y-20,
	        left: x+40,
	        position: "absolute",
	        color: "#4e7ebc",
	        "font-size": "1em"
	    });
	   
	    $("body").append($i);
	    $i.animate({
	        top: y - 200,
	        opacity: 0,
	        "font-size": "2em",
	        speed:2000,
	        easing:'linear'
	    },
	    2500,
	    function() {
	        $i.remove();
	    });
	    e.stopPropagation();
	}














//验证手机号码
function checkMobile(str) {
	var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/; 
	if(myreg.test(str)){
	    return true; 
	}else{
		return false;
	}
}

//验证电话号码（固定电话）
function checkPhone(str){
   var re = /^0\d{2,3}-?\d{7,8}$/;
   if(re.test(str)){
       return true;
   }else{
       return false;
   }
}

//电子邮件校验
function checkMail(str){
   var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(.[a-zA-Z0-9_-])+/; 
   if (reg.test(str)){
       return true;
   }else{
       return false;
   }
}

//验证身份证号码
function isCardNo(card){
   // 身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X  
   var reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/;  
   if(reg.test(card))  {
       return true;  
   }else{
	   return false;
   }
}

//中文校验(包含中文字符就行)
function checkCNName(cnName){
	if (/[\u4E00-\u9FA5]/i.test(cnName)) {
		return true;
	}else{
		return false;
	}
}

//校验英文字母
function checkEnName(str){     
    if(str.length!=0){    
	    reg=/^[a-zA-Z]+$/;     
	    if(!reg.test(str)){    
	        return　false;
	    }   
    }
    return true;
}

//判断数值是否 在 两位小数之内 或者整数
function IsDoubleOne(str){
	//这里是正则表达式在js里的判断用法
	if(/^\d+(\.\d{1})?$/.test(str)){
		return true;
	}else{
		return false;
	}
}

//判断数值是否 在 两位小数之内 或者整数
function IsDoubleTwo(str){
	//这里是正则表达式在js里的判断用法
	if(/^\d+(\.\d{1,2})?$/.test(str)){
		return true;
	}else{
		return false;
	}
}
//判断输入的字符是否为整数    
function IsInteger(str){       
    if(str.length!=0){    
	    reg=/^[-+]?\d*$/;     
	    if(!reg.test(str)){    
	        alert("对不起，您输入的整数类型格式不正确!");//请将“整数类型”要换成你要验证的那个属性名称！    
	        return false;
	    }    
    }
    return true;
}
   
//判断输入的字符是否为双精度    
function IsDouble(str){     
    if(str.length!=0){
	    reg=/^[-\+]?\d+(\.\d+)?$/;    
	    if(!reg.test(str)){    
	        alert("对不起，您输入的双精度类型格式不正确!");//请将“双精度类型”要换成你要验证的那个属性名称！    
	        return false;
	    }    
    }
    return true;
}

//校验长度
function checkLength(str){ 
	if(str.length<2 || str.length>8){
		return false;
	}else{
		return true;
	}
}

//校验长度
function checkLength2(str){ 
	if(str.length<2 || str.length>20){
		return false;
	}else{
		return true;
	}
}

//校验长度
function checkLength3(str){ 
	if(str.length<1 || str.length>3){
		return false;
	}else{
		return true;
	}
}

//校验长度(参数)
function checkLengthParam(str,smallLength,bigLength){ 
	if(str.length<smallLength || str.length>bigLength){
		return false;
	}else{
		return true;
	}
}

//密码  必须为字母加数字且长度4到8位  //并且不能包含特殊字符
function CheckPassWord(password) {
    var str = password;
    if (str.length<4 || str.length>8) {
        return false;
    }
    var reg1 = new RegExp(/^[0-9a-zA-Z]*$/g);
    if (reg1.test(str)) {
        return true;
    }else{
    	return false;
    }
 
}
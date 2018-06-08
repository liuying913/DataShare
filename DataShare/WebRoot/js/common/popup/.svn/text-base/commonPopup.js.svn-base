/*2017-03-09*/
  var classStates = ["successState","errorState","warningState"];
   function init( tip, num ){
	   /*num代表不同状态
	   *  num = 0  表示成功
	   *  num = 1  表示失败
	   *  num = 2  表示警告
	   * */
	   if( num == null  ||  num > 2 ||  num < 0 ){
		   num = 0;
		   return false;
	   }
	   $('.zbg', window.parent.document).show();
 
     /*创建元素*/
	 /*  var popupBg="<div class='bg'></div>";*/
	   var popupContent= "<div class='mytck'>"+
		   "<div class='tip'><span class='"+classStates[num]+"'></span>" + tip+ "</div>"+
		   "<button class = 'confirm' type='button'>确定</button>"+
		   "</div>";
	   $(window.parent.document).find("body").append(popupContent);

	   /*点击确认按钮*/
	   $(".confirm",window.parent.document).click(function() {

		   var delNode = $(".mytck",window.parent.document);

		   if( delNode == null ){
			   return false;
		   }else{
		   	    $('.mytck', window.parent.document).remove();

			    $('.zbg', window.parent.document).hide();
		   }
	   });

	    
   }
   







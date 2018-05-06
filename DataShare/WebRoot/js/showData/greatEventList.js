	$(document).ready(function(){
	
  		ajaxRequest("/DataShare/greatEvent/typelList.action","get",function(res){
			data = res;
			shua();
			page();
  		});
  		
  		$(".seabtn").click(function(){
  			var keyWords=$(".sea-box .input01").val();
  			var state = $("#apply-state").val();
  			ajaxRequest("/DataShare/greatEvent/typelList.action?keyWords="+keyWords+"&state="+state,"get",function(res){
				data = res;
		        shua();
				page();
	  		});
  		});
  		
  		/*选择状态后自动查询*/
  		$("#apply-state").change(function(){
  			var keyWords=$(".sea-box .input01").val();
  			var state = $(this).val();
  			ajaxRequest("/DataShare/greatEvent/typelList.action?keyWords="+keyWords+"&state="+state,"get",function(res){
				data = res;
		        shua();
				page();
	  		});
  		});
  		
  		/*修改select的默认方式*/
  		$("#apply-state").selectWidget({
			change  : function (changes) {
				return changes;
			},
			effect       : "slide",
			keyControl   : true,
			speed        : 200,
			scrollHeight : 250
		});
	    
  	    //===导出
		$('#upButton').click(function() {	
			var params = "";
			var keyWords=$(".sea-box .input01").val();
			var url="/DataShare/excel/exportGreatEvent.action?";
			params = "keyWords="+keyWords;
			window.location.href=url+params;

		});
	});


  	//分页
  	function shua(){
		if(flag!=0){page();}else{flag=1;}
		str="";
	    $.each(data, function(key, value) {
	    	if( key>=(shu-1)*num_value && key< shu*num_value    ){
	    		
	    		var startTime = value.startTimeStr.substring(0,10);
	    		var endTime = value.endTimeStr.substring(0,10);
				if(key%2!=0){
					str=str+"<tr class='even'>"
					+"	    <td>"+value.siteNumber+"</td>"
					+"	    <td>"+value.siteName+"</td>"
					+"	    <td>"+startTime+"</td>"
					+"	    <td>"+endTime+"</td>"
					+"	    <td>"+value.typeStr+"</td>"
					+"	    <td>"+value.describe+"</td>"
					+"	    <td>"+value.feedback+"</td>"
					+"	    <td><a href='/DataShare/greatEvent/detailToJsp.action?id="+value.id+"&type="+value.type+"'>事件查看</a></td>"
					+"    </tr>";
				}else{ 
					str=str+"<tr>"
					+"	    <td>"+value.siteNumber+"</td>"
					+"	    <td>"+value.siteName+"</td>"
					+"	    <td>"+startTime+"</td>"
					+"	    <td>"+endTime+"</td>"
					+"	    <td>"+value.typeStr+"</td>"
					+"	    <td>"+value.describe+"</td>"
					+"	    <td>"+value.feedback+"</td>"
					+"	    <td><a href='/DataShare/greatEvent/detailToJsp.action?id="+value.id+"&type="+value.type+"'>事件查看</a></td>"
					+"    </tr>";
				}
		  	}
		});
		$("#tbody").html(str); 
		$('#iframe', parent.document).css("height",$("#iframeheight").height());
	}	

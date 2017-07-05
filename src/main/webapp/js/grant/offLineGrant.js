
$(document).ready(function(){
	
	// 导出，
	$("#daoBtn").click(function(){
		hide();
	})
	// 上传
	$("#shangBtn").click(function(){
		show();
	})
	// 线下放款确认
	$("#offlineSure").click(function(){
		var op=$(":input[name='tp']:checked").val();
		var grantWay=null;
		if(op=="导出"){
			// 获得导出选择的是哪个按钮
			grantWay=$(":selected").val();
		}else{
			// 获得上传的是哪个按钮
			grantWay=$(":selected").val();
		}
		    window.returnValue = grantWay;
		    window.close(); 
	})
	
		function show(){
				$("#T1").removeAttr("style");
				$("#T2").removeAttr("style");
				$("#T3").attr("style","display:none;");
		   }
	   function hide(){
				$("#T1").attr("style","display:none;");
				$("#T2").attr("style","display:none;");
				$("#T3").attr("style","display:inline;");
		   } 
	   
	  
})

	

/**
 * 资金托管待放款
 */
$(document).ready(function(){
	
	
	//批量交易成功
	$('#tradeSuccess').click(function(){
		var curDate=new Date();
		var contractCode='';
		if($(":input[name='checkBoxItem']").length==0){
			art.dialog.alert("列表没有操作数据！");
			return false;
		}
		else if($(":input[name='checkBoxItem']").length > 0){
			if($(":input[name='checkBoxItem']:checked").length>0){
			   $(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			contractCode+=","+$(this).attr("contractCode");
        		}else{
        			contractCode=$(this).attr("contractCode");
        		}
        	});
			}}else{
		    	 $(":input[name='checkBoxItem']").each(function(){
		        		if(idVal!="")
		        		{
		        			contractCode+=","+$(this).attr("contractCode");
		        		}else{
		        			contractCode=$(this).attr("contractCode");
		        		}
		        	});
		      }
		
		window.location.href=ctx+"/borrow/grant/grantSure/tradeSuccess?contractCode="+contractCode+"&curDate="+curDate;
	});
	
	
	/**
	 * 导出Excel1
	 */
	$("#export1").click(function(){
//		 if(!checkStatus('0')){
//			 art.dialog.alert("所选数据包含不能Excel1导出的数据");
//			 return false;
//		 }
		 isSelectCount = $("#tableList").find("input[type=checkbox][name='checkBoxItem']:checked");
		 if(isSelectCount.length > 0){
			 // 导出选择的数据
			 param = getSelectCodes();
			 if(param.length==0){
				 art.dialog.alert("没有可导出的数据");
				 return;
			 }
			 param = "?" + param;
			 window.location.href=ctx+"/borrow/trustee/grant/exportExcel1" + param;
		 }else{
			 length = $("#tableList").find("input[type=checkbox][name='checkBoxItem']").length;
			 if(length==0){
				 art.dialog.alert("没有可导出的数据");
				 return;
			 }
			 // 导出列表所有数据
			 param = $("#grantForm").serialize();
			 param = param + "&excelName=1&listFlag=TG"
			 window.location.href=ctx+"/borrow/trustee/grant/exportExcel" + "?" + param;
		 }
	 });
	 
	/**
	 * 导出Excel2
	 */
	 $("#export2").click(function(){
//		 if(!checkStatus('2')){
//			 art.dialog.alert("所选数据包含不能Excel2导出的数据");
//			 return false;
//		 }
		 isSelectCount = $("#tableList").find("input[type=checkbox][name='checkBoxItem']:checked");
		 if(isSelectCount.length > 0){
			 // 导出选择的数据
			 param = getSelectCodes();
			 if(param.length==0){
				 art.dialog.alert("没有可导出的数据");
				 return;
			 }
			 param = "?" + param;
			 window.location.href=ctx+"/borrow/trustee/grant/exportExcel2" + param;
		 }else{
			 length = $("#tableList").find("input[type=checkbox][name='checkBoxItem']").length;
			 if(length==0){
				 art.dialog.alert("没有可导出的数据");
				 return;
			 }
			 // 导出列表所有数据
			 param = $("#grantForm").serialize();
			 param = param + "&excelName=2&listFlag=TG"
			 window.location.href=ctx+"/borrow/trustee/grant/exportExcel" + "?" + param;
		 }
	 });
	 
	 /**
	  * 导出解冻1
	  */
	 $("#export3").click(function(){
//		 if(!checkStatus('1')){
//			 art.dialog.alert("所选数据包含不能解冻1导出的数据");
//			 return false;
//		 }
		 isSelectCount = $("#tableList").find("input[type=checkbox][name='checkBoxItem']:checked");
		 if(isSelectCount.length > 0){
			 // 导出选择的数据
			 param = getSelectCodes();
			 if(param.length==0){
				 art.dialog.alert("没有可导出的数据");
				 return;
			 }
			 param = "?" + param;
			 window.location.href=ctx+"/borrow/trustee/grant/exportExcel3" + param;
		 }else{
			 length = $("#tableList").find("input[type=checkbox][name='checkBoxItem']").length;
			 if(length==0){
				 art.dialog.alert("没有可导出的数据");
				 return;
			 }
			 // 导出列表所有数据
			 param = $("#grantForm").serialize();
			 param = param + "&excelName=3&listFlag=TG"
			 window.location.href=ctx+"/borrow/trustee/grant/exportExcel" + "?" + param;
		 }
	 });
	 
	 /**
	  * 导出解冻2
	  */
	 $("#export4").click(function(){
//		 if(!checkStatus('3')){
//			 art.dialog.alert("所选数据包含不能解冻2导出的数据");
//			 return false;
//		 }
		 isSelectCount = $("#tableList").find("input[type=checkbox][name='checkBoxItem']:checked");
		 if(isSelectCount.length > 0){
			 // 导出选择的数据
			 param = getSelectCodes();
			 if(param.length==0){
				 art.dialog.alert("没有可导出的数据");
				 return;
			 }
			 param = "?" + param;
			 window.location.href=ctx+"/borrow/trustee/grant/exportExcel4" + param;
		 }else{
			 length = $("#tableList").find("input[type=checkbox][name='checkBoxItem']").length;
			 if(length==0){
				 art.dialog.alert("没有可导出的数据");
				 return;
			 }
			 // 导出列表所有数据
			 param = $("#grantForm").serialize();
			 param = param + "&excelName=4&listFlag=TG"
			 window.location.href=ctx+"/borrow/trustee/grant/exportExcel" + "?" + param;
		 }
	 });
	 
	 function getSelectCodes(){
		 param = "";
		 $("#tableList").find("input[type=checkbox][name='checkBoxItem']:checked").each(function(){
			 code = $(this).attr("contractCode");
			 if(code && code.length>0){
				 param = param + "&code=" + code;
			 }
			 param = param.replace(/^&/,'');
		 });
		 return param;
	 }
	 /**
	  * 判断文件导出状态是否正确
	  */
	 function checkStatus(status){
		 var flag=true;
		 $.each($("#tableList").find("input[type=checkbox][name='checkBoxItem']:checked"),function(){
			 var trustGrantOutputStatus = $(this).attr("trustGrantOutputStatus");
			 if(trustGrantOutputStatus==''){
				 trustGrantOutputStatus='0';
			 }
			 if( trustGrantOutputStatus != status){
				 flag = false;
			 }			 
		 });		 
		 return flag;
	 }
	 /**
	  * 批量导出完成
	  */
	 $("#btnNext").click(function(){
		 if($(":input[name='checkBoxItem']").length==0){
				art.dialog.alert("列表没有操作数据！");
				return false;
			}
		 
		 if(!checkStatus('4')){
			 art.dialog.alert("所选数据包含解冻2未导出的数据");
			 return false;
		 }
		 //选择的数据
		 selects = getSelectCodes();
		 // 导出列表所有数据
		 param = $("#grantForm").serialize();
		 param = param + "&" + selects+"&listFlag=TG"
		 
		 $.ajax({  
		   type : "POST",
		   data:param,
			url : ctx+"/borrow/trustee/grant/grantToNext",
			datatype : "json",
			success : function(data){
				art.dialog.alert("放款完成");
				document.forms[0].submit();
			},
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
		 });
	 });
	 /**
	  * 交易成功
	  */
	 $(".goNext").click(function(){
		 if($(this).attr("trustGrantOutputStatus")!="4"){
			 art.dialog.alert("所选数据包含解冻2未导出的数据");
			 return false;
		 }
		 var param = "code=" + $(this).attr("contractCode")+"&listFlag=TG";
		 $.ajax({  
		   type : "POST",
		   data:param,
		   url : ctx+"/borrow/trustee/grant/grantToNext",
		   datatype : "json",
			success : function(data){
				 art.dialog.alert("交易成功",document.forms[0].submit());
			},
			error: function(){
				art.dialog.alert("服务器没有返回数据，可能服务器忙，请重试");
			}
		 });
	 });
});

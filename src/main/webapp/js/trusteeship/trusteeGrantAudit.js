/**
 * 资金托管待放款审核
 */
$(document).ready(function(){
	/**
	 * 导出委托提现
	 */
	$("#export1").click(function(){
		 param = getSelectCodes();
		 isSelectCount = $("#tableList").find("input[type=checkbox][name=checkBoxItem]:checked");
		 if(isSelectCount.length > 0 && param.length > 0){
			 // 导出选择的数据
			 param = "?" + param;
			 window.location.href=ctx+"/borrow/trustee/grantAudit/exportEntrustReflect" + param;
		 }else{
			 art.dialog.alert("请选择导出的数据");
		 }
	 });
	 
	 function getSelectCodes(){
		 param = "";
		 $("#tableList").find("input[type=checkbox][name=checkBoxItem]:checked").each(function(){
			 code = $(this).attr("contractCode");
			 if(code && code.length>0){
				 param = param + "&codes=" + code;
			 }
			 param = param.replace(/^&/,'');
		 });
		 return param;
	 }
	 /**
	  * 上传回盘结果（委托提现）
	  */
	 $("#export2").click(function(){
		 art.dialog({
				content: document.getElementById("trustRechargeDiv"),
				title:'上传回盘结果',
				fixed: true,
				lock:true,
				width:400,
				id: 'rechargeDivid',
				okVal: '确认',
				ok: function () {
					ajaxFileUpload2();
					return true;
				},
				cancel: true
			});
	 });
	 
	 function ajaxFileUpload2() {
		 var file=$("#fileid").val();
	     if($.trim(file)==''){
	   	   art.dialog.alert("请选择文件");
	       return false;
	     }
	     var extStart=file.lastIndexOf(".");
		 var ext=file.substring(extStart,file.length).toUpperCase();
		 if(ext!=".XLSX" && ext!=".XLS"){
	    	art.dialog.alert("仅限于上传Excel文件");
	    	$("#fileid").val('');
	    	return false;
		 }
		 
	     var url=ctx+"/borrow/grant/grantSure/importExcel";    		  
	     // 开始上传文件时显示一个图片
	     $.ajaxFileUpload({
		  	   url : url,
		       type: 'post',
		       secureuri: false, //一般设置为false
		       fileElementId: "fileid", // 上传文件的id、name属性名
		       dataType: 'json', //返回值类型，一般设置为json、application/json
		       data:{
		      	  
		       },
		       // elementIds: elementIds, //传递参数到服务器
		       success: function(data, status){ 
			       	 if(data==""){
			       		art.dialog.alert("上传成功！",function(){
			       			window.location.reload(true);
			       		});	
						
			       	 }else{
			       		art.dialog.alert(data.replace(/;/g,"<br>"),function(){
			       			window.location.reload(true);
			       		});
			       		
			       	 }
		       },
		       error: function(data, status, e){
		       	  	 art.dialog.alert(e);
		       }
	   	  });
	}
});

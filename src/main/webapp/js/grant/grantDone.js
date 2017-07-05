
$(document).ready(function(){
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){
	 $(':input','#grantForm')
	 .not(':button, :submit,:reset,:hidden')
	  .val('')
	  .removeAttr('checked')
	  .removeAttr('selected');
	 $('select').val();
	 $('select').trigger("change");
	 $('#grantForm').submit();
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
			document.getElementById("T3").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			document.getElementById("T3").style.display='none';
		}
	});
	
	// 导出excel,默认导出全部
	$("#daoBtn").click(function(){
		var idVal = "";
		var listFlag = $("#listFlag").val();
		var grantForm = $("#grantForm").serialize();
		if(listFlag=='TG'){
			grantForm+='&tgFlag=1';
		}
		if($(":input[name='checkBoxItem']:checked").length>0){
			$(":input[name='checkBoxItem']:checked").each(function(){
        		if(idVal!="")
        		{
        			idVal+=","+$(this).attr("fId");
        		}else{
        			idVal=$(this).attr("fId");
        		}
        	});
		}
		window.location.href=ctx+"/borrow/grant/grantDone/grantDoneExl?idVal="+idVal+"&"+grantForm+"&listFlag="+listFlag;
	});
	
	// 点击全选
	$("#checkAll").click(function(){
		var curMoney = 0.0;
		var curNum = 0;
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked')){
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				  curMoney = parseFloat($(this).attr("contractMoney"))+curMoney;
				  curNum=curNum+1;
				});
			$('#hiddenNum').val(curNum);
			$('#hiddenGrant').val(curMoney);
			$('#totalNum').text(curNum);
			$('#totalGrantMoney').text(fmoney(curMoney, 2));
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
			$('#hiddenGrant').val(0.00);
			$('#hiddenNum').val(0);
			$('#totalNum').text($("#hiddenTotalNum").val());
			$('#totalGrantMoney').text(fmoney($("#hiddenTotalMoney").val(), 2));
		}
	});
	
	// 单选计算金额
	$(":input[name='checkBoxItem']").click(function(){
		var cumulativeLoan  = parseFloat($("#hiddenGrant").val());
		var totalNum = parseFloat($("#hiddenNum").val(),10);
		var hiddenTotalMoney = $("#hiddenTotalMoney").val();
		var hiddenTotalNum = $("#hiddenTotalNum").val();
		var hiddeNum = 0,hiddenMoney = 0.00;
		if ($(this).is(":checked")) {
			hiddenMoney = cumulativeLoan += parseFloat($(this).attr("contractMoney"));
			hiddeNum = totalNum += 1;
		} else {
			if ($("input[name='checkBoxItem']:checked").length == 0){
				cumulativeLoan = hiddenTotalMoney;
				totalNum = hiddenTotalNum;
			} else {
				hiddenMoney = cumulativeLoan -= parseFloat($(this).attr("contractMoney"));
				hiddeNum = totalNum -= 1 ;
			}
		}
		$("#checkAll").prop("checked",($("input[name='checkBoxItem']").length == $("input[name='checkBoxItem']:checked").length ? true : false));
		
		$("#totalGrantMoney").text(fmoney(cumulativeLoan,2));
		$("#hiddenGrant").val(hiddenMoney);
		$("#totalNum").text(totalNum);
		$("#hiddenNum").val(hiddeNum);
	});
	
	// 导出委托提现
	$("#export1").click(function(){
		 param = getSelectCodes();
		 isSelectCount = $("#tableList").find("input[type=checkbox][name=checkBoxItem]:checked");
		 if(isSelectCount.length > 0 && param.length > 0){
			 // 导出选择的数据
			 param = "?" + param;
			 window.location.href=ctx+"/borrow/grant/grantDone/exportEntrustReflect" + param;
		 }else{
			 art.dialog.alert("请选择导出的数据");
//			 length = $("#tableList").find("input[type=checkbox][name=checkBoxItem]").length;
//			 if(length==0){
//				 art.dialog.alert("没有可导出的数据");
//				 return;
//			 }
//			 window.location.href=ctx+"/borrow/grant/grantDone/exportEntrustReflect";
		 }
	 });
	 
	 function getSelectCodes(){
		 param = "";
		 $("#tableList").find("input[type=checkbox][name=checkBoxItem]:checked").each(function(){
			 code = $(this).attr("fId");
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
		 
	     var url=ctx+"/borrow/grant/grantDone/importExcel";    		  
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
			       			window.location.reload(true)
			       		});
			       	 }else{
			       		art.dialog.alert(data.replace(/;/g,"<br>"),function(){
			       			window.location.reload(true)
			       		});
			       	 }
		       },
		       error: function(data, status, e){
		       	  	 art.dialog.alert(e);
		       }
	   	  });
	}
	 
	//格式化，保留两个小数点
	 function fmoney(s, n) {  
	     n = n > 0 && n <= 20 ? n : 2;  
	     s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	     var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
	     t = "";  
	     for (i = 0; i < l.length; i++) {  
	         t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
	     }  
	     return t.split("").reverse().join("") + "." + r;  
	 }
	
})

	

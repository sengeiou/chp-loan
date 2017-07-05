
$(document).ready(function(){
	// 点击清除，清除搜索页面中的数据，DOM
	$("#clearBtn").click(function(){
		$('#customerName').val('');
		$('#contractCode').val('');
		$('#storesCode').val('');
		$('#storeOrgId').val('');
		$('#startDate').val('');
		$('#endDate').val('');
		$('#htStartDate').val('');
		$('#htEndDate').val('');
		$('#midBankName').val('');
		$('#bankName').val('');
		$('#bankId').val('');
		$('#bankCardNo').val('');
		$("#productCode").val('');
		$("#productCode").trigger("change");
		$("#loanFlag").val('');
		$("#loanFlag").trigger("change");
		$("#model").val('');
		$("#model").trigger("change");
	});
	
	// 伸缩
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
		}
	});
	

	
	// 点击全选
	$("#checkAll").click(function(){
		if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
		{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).attr("checked",'true');
				});
		}else{
			$(":input[name='checkBoxItem']").each(function() {
				$(this).removeAttr("checked");
			});
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
	 


	
})

function downFile(loanCode){
	//$('#subDIV').modal('show');
	window.location.href = ctx + "/borrow/grant/grantDone/getFile?loanCode=" + loanCode;
	//$('#subDIV').modal('hide');
	/*$.ajax({
		type : 'post',
		url : ctx + '/borrow/grant/grantDone/getFile',
		data :{loanCode:loanCode},
		dataType : "json",
		success : function(result) {
			$('#subDIV').modal('hide');
			if(result.msg=="false"){
				art.dialog.alert("下载影像平台文件到服务器出错");
			}else if(result.msg=="0"){
				art.dialog.alert("此合同没文件可下载");
			}else{
				window.location.href = ctx + "/borrow/grant/grantDone/downFile?loanCode=" + result.loanCode + "&path=" + result.path;
			}
		},
		error : function() {
			$('#subDIV').modal('hide');
			 art.dialog.alert('请求异常！');
		}
		 
	});*/
}	

function downFiles(){
	var loanCodes = '';
	var nu = 0;
	var re = '已选择';
	$(":input[name='checkBoxItem']").each(function() {
		if($(this).attr('checked')=='checked'|| $(this).attr('checked')){
			loanCodes += $(this).val() + ",";
			nu += 1;
		}
	});
	if(loanCodes.length==0){
		re = '默认选择当前页面数据';
		$(":input[name='checkBoxItem']").each(function() {
			loanCodes += $(this).val() + ",";
			nu += 1;
		});
	}
	if(loanCodes.length!=0){
		loanCodes = loanCodes.substr(0,loanCodes.length-1);
	}else{
		art.dialog.alert("没有可下载数据");
		return;
	}
	re += nu + '条';
	art.dialog.confirm("单次批量下载最多下载当前页数据，"+re+"。下载可能时间稍长，是否确认下载？",
			function(){
		$('#loanCode').val(loanCodes);
		//$('#subDIV').modal('show');
		var obj = document.getElementById('grantForm');
		obj.action = ctx + "/borrow/grant/grantDone/getFiles";
		obj.submit();
		obj.action = ctx + "/borrow/grant/grantDone/wthkList";
		//window.location.href = ctx + "/borrow/grant/grantDone/getFiles?loanCode=" + loanCodes;
		//$('#subDIV').modal('hide');
	})
	
	/*art.dialog.confirm("单次批量下载最多下载当前页数据，"+re+"。下载可能时间稍长，是否确认下载？",
			function(){
		$('#loanCode').val(loanCodes);
		$('#subDIV').modal('show');
		$.ajax({
			type : 'post',
			url : ctx + '/borrow/grant/grantDone/getFiles',
			data : $('#grantForm').serializeArray(),
			dataType : "json",
			success : function(result) {
				$('#subDIV').modal('hide');
				if(result.msg=="false"){
					art.dialog.alert("下载影像平台文件到服务器出错");
				}else if(result.msg=="0"){
					art.dialog.alert("此合同没文件可下载");
				}else{
					window.location.href = ctx + "/borrow/grant/grantDone/downFiles?path=" + result.path;
				}
			},
			error : function() {
				$('#subDIV').modal('hide');
				 art.dialog.alert('请求异常！');
			}
		});
	})*/
}
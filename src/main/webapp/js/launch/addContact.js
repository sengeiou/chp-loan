var contact={};
contact.delRow=function(obj,tableId,ItemType){
	//校验联系人数量
	var flag =  contact.checkContactCount(tableId);
	if(!flag){
		return false;
	}
	
	art.dialog.confirm("是否确认删除", function(){
		var id = $(obj).parent().parent().find("td :input[name$='.id']").val();
		var preResponse = $('#response').val();
		var url = ctx+"/apply/dataEntry/delAdditionItem";
		if(preResponse!=undefined && preResponse!=''){
			url=url+"?preResponse="+preResponse;
		}
		if(id!= null && id!='' && id!= undefined){
		   	$.ajax({ type:'post', url:url, data:{'delType':ItemType, 'tagId':id}, async: false, 
		   		success:function(data){
		   			if(data=='true'){
		   				$(obj).parent().parent().remove();
		   				top.$.jBox.tip('删除成功');
		   			}else{
		   				art.dialog.alert("删除失败！");
		   			}
		   		},
		   		error:function(){
		   			art.dialog.alert("服务器异常！");
		   		}
				
		   	});
		}else{
			top.$.jBox.tip('删除成功');
			$(obj).parent().parent().remove();	
		}
	});
};
contact.delCon=function(obj,tableId,ItemType){
	
	//校验联系人数量
	var flag =  contact.checkContactCount(tableId);
	if(!flag){
		return false;
	}
	
	art.dialog.confirm("是否确认删除", function(){
		var id = $(obj).parent().parent().find("td :input[name$='.id']").val();
		if(id!= null && id!='' && id!= undefined){
			$.ajax({type:'post', url:ctx+"/apply/dataEntry/delAdditionItem",
        		data:{
        			'delType':ItemType,
        			'tagId':id
        		},
        		async: false,
        		success:function(data){
        			if(data=='true'){
        				$(obj).parent().parent().remove();
        				top.$.jBox.tip("删除成功！");
        			}else{
        				top.$.jBox.tip("删除失败！");
        			}
        		},
        		error:function(){
        			art.dialog.alert("服务器异常！");
        		}
        	});
		}else{
			top.$.jBox.tip('删除成功');
			$(obj).parent().parent().remove();	
		}
	});
};

contact.delConCoboByReturn=function(obj,tableId,ItemType){
	
	//校验联系人数量
	var flag =  contact.checkContactCount(tableId);
	if(!flag){
		return false;
	}
	art.dialog.confirm("是否确认删除", function(){
		var id = $(obj).parent().parent().find("td :input[name$='.id']").val();
		if(id!= null && id!='' && id!= undefined){
			var preResponse = $('#response').val();
	   		$.ajax({
        		type:'post',
        		url:ctx+"/apply/storeReviewController/delContact",
        		data:{
        			'delType':ItemType,
        			'contactId':id,
        			'preResponse':preResponse
        		},
        		async: false,
        		success:function(data){
        			if(data=='true'){
        				$(obj).parent().parent().remove();
        				top.$.jBox.tip("删除成功！");
        			}else{
        				top.$.jBox.tip("删除失败！");
        			}
        		},
        		error:function(){
        			art.dialog.alert("服务器异常！");
        		}
        	});
		}else{
			top.$.jBox.tip('删除成功');
			$(obj).parent().parent().remove();	
		}
	});
};
contact.delConMainByReturn=function(obj,tableId,ItemType){

	//校验联系人数量
	var flag =  contact.checkContactCount(tableId);
	if(!flag){
		return false;
	}
	
	art.dialog.confirm("是否确认删除", function(){
		var id = $(obj).parent().parent().find("td :input[name$='.id']").val();
		if(id!= null && id!='' && id!= undefined){
			var preResponse = $('#response').val();
		   	$.ajax({
        		type:'post',
        		url:ctx+"/apply/storeReviewController/delContact",
        		data:{
        			'delType':ItemType,
        			'contactId':id,
        			'preResponse':preResponse
        		},
        		async: false,
        		success:function(data){
        			if(data=='true'){
        				$(obj).parent().parent().remove();
        				top.$.jBox.tip("删除成功！");
        				var win = art.dialog.open.origin;//来源页面
    			    	win.location.href = ctx+"/bpm/flowController/openForm?applyId="+$('#applyId',win.document).val()+"&wobNum="+$('#wobNum',win.document).val()+"&token="+$('#token',win.document).val()+"&dealType=0";
        			}else{
        				top.$.jBox.tip("删除失败！");
        			}
        		},
        		error:function(){
        			art.dialog.alert("服务器异常！");
        		}
        	});
		}else{
			top.$.jBox.tip('删除成功');
			$(obj).parent().parent().remove();	
		}
	});
};

contact.delConMainByReturnNew=function(obj,tableId,ItemType){

	//校验联系人数量
	var flag =  contact.checkContactCount(tableId);
	if(!flag){
		return false;
	}
	
	art.dialog.confirm("是否确认删除", function(){
		var id = $(obj).parent().parent().find("td :input[name$='.id']").val();
		if(id!= null && id!='' && id!= undefined){
			var preResponse = $('#response').val();
	   		$.ajax({
        		type:'post',
        		url:ctx+"/apply/storeReviewController/delContact",
        		data:{
        			'delType':ItemType,
        			'contactId':id,
        			'preResponse':preResponse
        		},
        		async: false,
        		success:function(data){
        			if(data=='true'){
        				$(obj).parent().parent().remove();
        				top.$.jBox.tip("删除成功！");
        			}else{
        				top.$.jBox.tip("删除失败！");
        			}
        		},
        		error:function(){
        			art.dialog.alert("服务器异常！");
        		}
        	});
		}else{
			top.$.jBox.tip('删除成功');
			$(obj).parent().parent().remove();	
		}
	});
};

contact.checkContactCount = function(tableId){
	
	if(tableId != undefined && tableId != null && tableId != ''){
		//联系人个数
		var length = $("#"+tableId +" tr").length;
		
		//亲属联系人
		if(("table_contactRelationArea" == tableId || tableId.indexOf("relatives_contact_table") == 0) && length > 1){
			return true;
		}else if("table_contactRelationArea" == tableId || tableId.indexOf("relatives_contact_table") == 0){
			art.dialog.alert("至少保留1个亲属联系人！");
			return false;
		}
		//工作证明人
		if(("table_workProvePersonArea" == tableId || tableId.indexOf("worktogether_contact_table")  == 0) && length > 1){
			return true;
		}else if("table_workProvePersonArea" == tableId || tableId.indexOf("worktogether_contact_table")  == 0){
			art.dialog.alert("至少保留1个工作证明人！");
			return false;
		}
		//其他联系人
		if(("table_otherArea" == tableId || tableId.indexOf("other_contact_table")  == 0) && length > 2){
			return true;
		}else if("table_otherArea" == tableId || tableId.indexOf("other_contact_table")  == 0){
			art.dialog.alert("至少保留2个其他联系人！");
			return false;
		}
		//旧版申请表，联系人
		if(length > 3){
			return true;
		}else{
			art.dialog.alert("至少保留3个联系人！");
			return false;
		}
	}else{
		return false;
	}
}

/**
 * 初始化联系人
 * By 任志远  2016年09月27日
 */
contact.init = function(){
	//配偶住址
	loan.initCity("mateAddressProvince", "mateAddressCity", "mateAddressArea");
	areaselect.initCity("mateAddressProvince", "mateAddressCity", "mateAddressArea", $("#mateAddressCityHidden").val(),$("#mateAddressAreaHidden").val());
	
	//亲属联系人
	$('#relationAdd').bind('click',function(){
		var tabLength=parseInt($('#relationIndex').val())+1;
		launch.additionItem('table_contactRelationArea','_loanFlowContactRelationItem',tabLength,'',null);
		$('#relationIndex').val(tabLength);
	});
	//工作证明人
	$('#workProvePersonAdd').bind('click',function(){
		var tabLength=parseInt($('#workProvePersonIndex').val())+1;
		launch.additionItem('table_workProvePersonArea','_loanFlowContactWorkProvePersonItem',tabLength,'',null);
		$('#workProvePersonIndex').val(tabLength);
	});
	//其他联系人
	$('#otherAdd').bind('click',function(){
		var tabLength=parseInt($('#otherIndex').val())+1;
		launch.additionItem('table_otherArea','_loanFlowContactOtherItem',tabLength,'',null);
		$('#otherIndex').val(tabLength);
	});
	
	contact.initTableOther();
}

contact.initTableOther = function(){
	$("#table_workProvePersonArea").find("select[name$='.contactRelation']").each(function(){
		contact.initOtherContact($(this));
		$(this).unbind("change");
		$(this).bind("change", function(){
			contact.initOtherContact($(this));
		});
	});
}

contact.initOtherContact = function(ele){
	
	var value = ele.val();
	var remark = ele.parent().parent().find("input[name$='remarks']");
	if(value == 2){
		remark.addClass("required");
	}else{
		remark.val("");
		remark.removeClass("required");
	}
}



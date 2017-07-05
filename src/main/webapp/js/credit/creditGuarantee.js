// 祥版征信相关操作（担保明细）


$(function(){
	// 初始化数据
	initGuaranteeData();
});

// 初始化数据
function initGuaranteeData(){
	$("#guaranteeoneTable").find("tbody").html("");
	
	var data = $(window.parent.document).find("form[id='param']").serialize();
	
	$.ajax({
		url: ctx+"/credit/showGuaranteeData",
		data:data,
		type: "post",
		dataType:'json',
		success:function(data){
		 
			if( data != null ){
				// 担保明细信息
				if(data != null && data.creditGuaranteeDetailedList[0].id != null){
					for( var i=0; i<data.creditGuaranteeDetailedList.length; i++ ){
						if( data.creditGuaranteeDetailedList[0].id != null ){
							//append selce2难用，用clone
							var vTb=$('#guaranteeoneTable');
							var vTr=$('#guaranteeoneTable_trRow_1');
							var htmInfo=vTr.cloneSelect2(true,true);
							
							htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id","infoTr"+data.creditGuaranteeDetailedList[i].id);
							htmInfo.find("input[name='id']").val(data.creditGuaranteeDetailedList[i].id);
							htmInfo.find("input[name='otherGuaranteeAmount']").val(formatMoney(data.creditGuaranteeDetailedList[i].otherGuaranteeAmount));
							htmInfo.find("input[name='realPrincipal']").val(formatMoney(data.creditGuaranteeDetailedList[i].realPrincipal));
							htmInfo.find("input[name='relationId']").val(data.creditGuaranteeDetailedList[i].relationId);
							htmInfo.find("input[name='deleteName']").attr("onClick","removeGuaranteeData(this,'"+data.creditGuaranteeDetailedList[i].id+"')");
							htmInfo.find("tr").attr("id","infoTr"+data.creditGuaranteeDetailedList[i].id);
							
							htmInfo.appendTo(vTb); 
							
							
						}
					}
				
					// 显示编号
					sortGuarantee();
				}
				 
			}
		}
	});
}




//显示编号
function sortGuarantee(){
	// 循环担保信息(进行排序)
	var allInfoNum = $("#guaranteeoneTable").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
	});

}

//添加信息
function addGuaranteeData(){
	var data = new Date().getTime()+"";
	var num = Math.floor(Math.random()*100+1)+"";
	var randomNum = data+num;
	// 担保一信息
	var vTr=$('#guaranteeoneTable_trRow_1');
	var htmInfo1=vTr.cloneSelect2(true,true);
	htmInfo1.find("input[name='num']").parent("td").parent("tr").attr("id","infoTr"+randomNum);
	htmInfo1.find("input[name='deleteName']").attr("onClick","removeGuaranteeData(this,'"+randomNum+"')");
	$("#guaranteeoneTable").find("tbody").append(htmInfo1);

	// 显示编号
	sortGuarantee();
}


//保存数据
function saveGuaranteeData(){
	//
	if ( !checkForm($("#guaranteeoneForm") ) ) {
		return;
	}
	
	if (!checkDataBig($("#guaranteeoneForm"),$("#guaranteeoneForm"),
			"otherGuaranteeAmount","realPrincipal",
			"担保金额需要大于等于担保余额")) {
		return;
	}
	var param = makeParamGuarantee();
	var loanCode = $(window.parent.document).find("form[id='param']").find("input[name='loanCode']").val();
	var rCustomerCoborrowerId = $(window.parent.document).find("form[id='param']").find("input[name='rCustomerCoborrowerId']").val();
	var dictCustomerType = $(window.parent.document).find("form[id='param']").find("input[name='dictCustomerType']").val();
	var data = param+"&loanCode="+loanCode+"&type="+dictCustomerType+"&relId="+rCustomerCoborrowerId;
	data+="&relationId=1";
	// 担保
	$.ajax({
		url: ctx+"/credit/saveGuaranteeData",
		data:data,
		type: "post",
		dataType:'json',
		async: false,
		success:function(data){
			if(data){
				art.dialog.tips("操作成功");
			}
			else{
				art.dialog.tips("操作失败");
			}
			initGuaranteeData();
		}
	});
}

// 做好保存参数
function makeParamGuarantee(){
	// 担保信息
	var GuaranteeForm1=$("#guaranteeoneForm");

	var relationId = GuaranteeForm1.find("input[name='relationId']"); 

	var otherGuaranteeAmount = GuaranteeForm1.find("input[name='otherGuaranteeAmount']");  
	var realPrincipal = GuaranteeForm1.find("input[name='realPrincipal']");  
	var id = GuaranteeForm1.find("input[name='id']"); 
	var param = "";
	for(var i = 0; i < otherGuaranteeAmount.length; i++ ){
		param+="&creditGuaranteeDetailedList["+i+"].otherGuaranteeAmount="+formatMoneyEx($(otherGuaranteeAmount[i]).val());
		param+="&creditGuaranteeDetailedList["+i+"].realPrincipal="+formatMoneyEx($(realPrincipal[i]).val());
		param+="&creditGuaranteeDetailedList["+i+"].relationId="+$(relationId[i]).val();
		param+="&creditGuaranteeDetailedList["+i+"].id="+$(id[i]).val();
	}

	return param;
}

//删除担保信息
function removeGuaranteeData(t,id){
	
	if(id != "" && id.length>20){
		
		if(confirm("该数据已在数据库中存在,确定要删除吗？")){
			$.ajax({
				
				url: ctx+"/credit/deleteGuaranteeData",
				data:"id="+id,
				type: "post",
				dataType:'json',
				success:function(data){
					if(data != "0"){
						art.dialog.tips("删除成功")
						$(t).parent("td").parent("tr").remove();
						
					
						// 显示编号
						sortGuarantee();
					}else{
						alert("删除失败")
					}
				}
			});
		}
	}else{
		$(t).parent("td").parent("tr").remove();
	
		// 显示编号
		sortGuarantee();
	}
}
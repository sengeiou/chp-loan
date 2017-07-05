function queryBlackLog(mateCertType,mateCertNum){
	if(mateCertType!=null && mateCertType!='' && mateCertNum!=null && mateCertNum!=''){
		$.ajax({
			url:ctx+'/apply/black/asynQueryBlack',
			type:'post',
			data:{
				'dictBlackType':mateCertType,
				'blackMsg':mateCertNum
			},
			dataType:'json',
			success:function(data){
				if($('#flag').length>0){
					$('#flag').val(data.isWhite);
					$('#message').val(data.message);
				}
				$('#blackTip').html(data.message);   
			},
			error:function(){
				art.dialog.alert('请求异常');
			},
			async:false
		});
	}
}
function queryCustomerBaseByCertNum(idType,mateCertNum){
	$.ajax({
		url:ctx+"/telesales/consult/asynFindByCertNum",
		type:'post',
		data:{'mateCertNum':mateCertNum,'certType':idType},
		dataType:'json',
		cache:false,
		success:function(data){
		  if(data!=null){
			  if(data.flag=='0'){
				  art.dialog.alert(data.message);
				  $('#flag').val(data.flag);
				  $('#message').val(data.message);
			  }else{
				  $('#flag').val(data.flag);
				  $('#message').val(data.message);
				  $('#customerCode').val(data.customerCode);
				  if($('#customerName').val()=='' || $('#customerName').val()==null){
				    $('#customerName').val(data.customerName);
				  }
				  
				  if($('#customerMobilePhone').val()=='' || $('#customerMobilePhone').val()==null){
				    $('#customerMobilePhone').val(data.customerMobilePhone);
				  }
				  if($('#areaNo').val()=='' || $('#areaNo').val()==null){
					  $('#areaNo').val(data.areaNo);
				  }
				  if($('#telephoneNo').val()=='' || $('#telephoneNo').val()==null){
					  $('#telephoneNo').val(data.telephoneNo);
				  }
				  if($('#idStartDay').val()=='' || $('#idStartDay').val()==null){
					  $('#idStartDay').val(data.idStartDayStr);
				  }
				  
				  if($('#idEndDay').val()=='' || $('#idEndDay').val()==null){
					  if(data.idEndDayStr!=null){
						  $('#idEndDay').val(data.idEndDayStr);
					  }else{
						  if($('#idStartDay').val()!='' && $('#idStartDay').val()!=null){
							  $('#longTerm').attr('checked',true);	
							  $('#idEndDay').attr('disabled',true);
						  }
					  }
				  }
				  var compIndustry = $('#dictCompIndustry option:selected').val();
				  if(compIndustry == '' || compIndustry == null){
					  $('#dictCompIndustry').val(data.dictCompIndustry).trigger("change");
				  }
				  var boolCheck = $('input:radio[name="customerBaseInfo.customerSex"]').is(":checked");
				  if(!boolCheck){
	    		   $('.customerSex').each(function(){
					  if($(this).val()==data.customerSex){
						  $(this).attr('checked',true);
					  }
				   });
				  }
			  }
		  }
		},
		error:function(){
			art.dialog.alert('服务器异常');
		},
		async:false
	});
}


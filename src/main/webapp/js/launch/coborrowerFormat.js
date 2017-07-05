var coborrower = {
		coboInputProp:new Array("].id",".coboName",".coboCertNum",".idStartDay",".idEndDay",".coboMobile",".coboMobile2",".coboHouseholdAddress",".coboNowAddress",".coboCompAddress",
				".coboNowTel",".coboGraduationDay",".coboEmail",".customerFirtArriveYear",".customerFirstLivingDay",".coboCompany.compSalary",".coboCompany.compOtherAmount",".coboCompany.id",".coboLivings.id",".coboLivings.customerRentMonth",".coboGetrent",".coboCompName",".coboQq",".coboWeibo"),
		coboRadioProp:new Array(".coboSex",".coboHaveChild",".coboContractIsKnow"),
		coboSelectProp:new Array(".dictMarry",".coboHouseholdProvince",".coboHouseholdCity",".coboHouseholdArea",
				".coboLiveingProvince",".dictCustomerDiff",".customerHouseHoldProperty",".dictEducation",".dictCertType",".coboLiveingCity",".coboLiveingArea",".coboCompProvince", ".coboCompCity", ".coboCompArer"),
		coboPrefix:'loanCoborrower',
        coboContactInputProp:new Array(".id",".contactName",".contactSex",".contactNowAddress",".contactUnitTel",".contactMobile"),
        coboContactSelectProp:new Array(".relationType",".contactRelation"),
        coboContactPrefix:".coborrowerContactList"
		
};


coborrower.del = function(parentViewId, delType, parentDbId) {
	art.dialog.confirm("是否确认删除", function() {
		if (parentDbId != null && parentDbId != '' && parentDbId != '-1' && parentDbId != undefined) {
			$.ajax({
				type : 'post',
				url : ctx + "/apply/dataEntry/delAdditionItem",
				data : {
					'delType' : delType,
					'tagId' : parentDbId
				},
				dataType : 'json',
				success : function(data) {
					if (data) {
						$("#" + parentViewId).remove();
						top.$.jBox.tip('删除成功');
					} else {
						top.$.jBox.tip('删除失败');
					}
				},
				error : function() {
					art.dialog.alert("服务器异常！");
				}
			});
		} else {
			top.$.jBox.tip('删除成功');
			$("#" + parentViewId).remove();
		}
	});
};

coborrower.deleteCobo = function(parentViewId, preResponse, parentDbId) {
	art.dialog.confirm("是否确认删除", function() {
		if (parentDbId != null && parentDbId != '' && parentDbId != '-1' && parentDbId != undefined) {
			$.ajax({
				type : 'post',
				url : ctx + "/apply/storeReviewController/delCoborrower",
				data : {
					'preResponse' : preResponse,
					'coboId' : parentDbId,
					'loanCustomer.applyId' : $('#coboApplyId').val()
				},
				async : false,
				success : function(data) {
					if (data == 'success') {
						var delNum = $("input[name='delCoborrower']").length;
						if (delNum > 1) {
							$("#" + parentViewId).remove();
							top.$.jBox.tip('删除成功');
							// 删除一个共借人就会刷新页面
							var win = art.dialog.open.origin;// 来源页面
							win.location.href = ctx + "/bpm/flowController/openForm?applyId=" + $('#coboApplyId').val() + "&wobNum=" + $('#coboWobNum').val() + "&token=" + $('#coboToken').val() + "&dealType=0";
						} else {
							var win = art.dialog.open.origin;// 来源页面
							win.location.href = ctx + "/bpm/flowController/openForm?applyId=" + $('#coboApplyId').val() + "&wobNum=" + $('#coboWobNum').val() + "&token=" + $('#coboToken').val() + "&dealType=0";
						}
					} else {
						top.$.jBox.tip('删除失败');
					}
				},
				error : function() {
					art.dialog.alert("服务器异常！");
					var win = art.dialog.open.origin;// 来源页面
					win.location.href = ctx + "/bpm/flowController/openForm?applyId=" + $('#coboApplyId').val() + "&wobNum=" + $('#coboWobNum').val() + "&token=" + $('#coboToken').val() + "&dealType=0";
				}
			});
		} else {
			var delNum = $("input[name='delCoborrower']").length;
			if (delNum == 1) {
				art.dialog.alert("当前页禁止删除");
				return;
			}
			top.$.jBox.tip('删除成功');
			$("#" + parentViewId).remove();
		}
	});
};
coborrower.format=function(){
	var index = null;
	var initCoboIndex=0;
	var initCoboContactIndex=0;
	var props = null;
	var curProp = null;
	var tableId = null;
	var curPanelId = null;
	$('.contactPanel').each(function(){
		index = $(this).attr('index');
		curPanelId = $(this).attr("id");
		props = coborrower.coboInputProp;
		for(m in props){
			curProp = props[m];
		  $("#"+curPanelId+" :input[name$='"+curProp+"']").each(function(){
			  if($(this).attr('name')!='loanCustomer.id'){
				  if($(this).attr('name').indexOf("].id")!=-1){
					  if($(this).attr('name').indexOf("].idStartDay")==-1 && $(this).attr('name').indexOf("].idEndDay")==-1){
						  $(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+curProp);  
					  }else{
						  $(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+curProp);  
					  }
				  }else{
					  $(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+curProp);
				  }
			  }
		  });	
		}
		props = coborrower.coboRadioProp;
		for(m in props){
			 curProp = props[m];
		  $("#"+curPanelId+" :input[name$='"+curProp+"']").each(function(){
		     $(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+curProp);
		     
		   });	
		}
		props = coborrower.coboSelectProp;
		for(m in props){
			 curProp = props[m];
		  $("#"+curPanelId+" select[name$='"+curProp+"']").each(function(){
		     $(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+curProp);
		   });	
		}
		tableId = 'table_'+index;
		props = coborrower.coboContactSelectProp;
		for(m in props){
		  curProp = props[m];
		  initCoboContactIndex=0;
		  $("#"+tableId+" select[name$='"+curProp+"']").each(function(){
		     $(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+coborrower.coboContactPrefix+"["+initCoboContactIndex+"]"+curProp);
		     initCoboContactIndex++;
		  });

		}
		props = coborrower.coboContactInputProp;
		for(m in props){
		  curProp = props[m];
		  initCoboContactIndex=0;
		  $("#"+tableId+" input[name$='"+curProp+"']").each(function(){
			  if($(this).attr('name')!=='loanCustomer.id'){
		        $(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+coborrower.coboContactPrefix+"["+initCoboContactIndex+"]"+curProp);
		        initCoboContactIndex++;
			  }
		  });
		}
		initCoboIndex++;
	});
};
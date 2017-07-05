var coborrower = {
		coboInputProp:new Array(".loanInfoCoborrower.loanApplyAmount",".loanInfoCoborrower.borrowingPurposesRemark",".loanInfoCoborrower.mainPaybackResourceRemark",
				".loanInfoCoborrower.highPaybackMonthMoney","].id",".coboName",".coboCertNum",".idStartDay",".idEndDay",".childrenNum",".supportNum",
				".personalYearIncome",".homeMonthIncome",".homeMonthPay",".homeTotalDebt",".customerFirtArriveYear",".customerFirstLivingDay",
				".coboFamilyTel",".coboNowAddress",".residentialCategoryRemark",".socialSecurityNumber",".coboMobile",".coboHouseholdAddress",
				".coboEmail",".coboWeibo",".coboQq",".creditUserName",".creditPassword",".creditAuthCode",".coboCompany.compName",".coboCompany.compTel",
				".coboCompany.compSalary",".coboCompany.compSalaryDay",".coboCompany.compAddress",".coboCompany.id",".loanInfoCoborrower.id",
				".dictCertType",".whoCanKnowTheBorrowing",".whoCanKnowTheBorrowingRemark",".coboCompany.compTelExtension", ".issuingAuthority"),
		coboRadioProp:new Array(".coboSex"),
		coboSelectProp:new Array(".dictMarry",".coboHouseholdProvince",".coboHouseholdCity",".coboHouseholdArea",".coboLiveingProvince",".customerHouseHoldProperty",
				".dictEducation",".coboLiveingCity",".coboLiveingArea",".loanInfoCoborrower.productType",".loanInfoCoborrower.loanMonths",
				".loanInfoCoborrower.borrowingPurposes",".loanInfoCoborrower.mainPaybackResource",".coboCompany.compProvince", ".coboCompany.compCity", ".coboCompany.compArer",
				".registerProperty", ".coboCompany.dictSalaryPay"),
		coboTextareaProp:new Array(".loanInfoCoborrower.remarks"),
		coboPrefix:'loanCoborrower',
        coboContactInputProp:new Array(".id",".contactName",".contactSex",".contactMobile",".certNum",".homeTel",".department",".post",".remarks",".relationType",".contactRelation"),
        coboContactSelectProp:new Array(".contactRelation"),
        coboContactPrefix:".coborrowerContactList"
		
};
coborrower.del = function(parentViewId,delType,parentDbId){
	art.dialog.confirm("是否确认删除",function(){
		if(parentDbId!= null && parentDbId!='' && parentDbId!='-1' && parentDbId!= undefined){
			$.ajax({
				type:'post',
	    		url:ctx+"/apply/dataEntry/delAdditionItem",
	    		data:{
	    			'delType':delType,
	    			'tagId':parentDbId
	    		},
	    		dataType:'json',
	    		success:function(data){
	    			if(data){
    					$("#"+parentViewId).remove();
    					top.$.jBox.tip('删除成功');
	    			}else{
	    				top.$.jBox.tip('删除失败');
	    			}
	    		},
	    		error:function(){
	    			art.dialog.alert("服务器异常！");
	    		}
			});
	    }else{
			top.$.jBox.tip('删除成功');
			$("#"+parentViewId).remove();
		}	
	});
};

coborrower.deleteCobo = function(parentViewId,preResponse,parentDbId){
	art.dialog.confirm("是否确认删除", function(){
		if(parentDbId!= null && parentDbId!='' && parentDbId!='-1' && parentDbId!= undefined){
			$.ajax({
				type:'post',
				url:ctx+"/apply/storeReviewController/delNaturalGuarantor",
				data:{
					'preResponse':preResponse,
					'coboId':parentDbId,
					'loanCustomer.applyId':$('#coboApplyId').val()
				},
				async: false,
				success:function(data){
					if(data=='success'){
						$("#"+parentViewId).remove();
						top.$.jBox.tip('删除成功');
					}else{
						top.$.jBox.tip('删除失败');
					}
				},
				error:function(){
					art.dialog.alert("服务器异常！");
				}
			});
		}else{
			top.$.jBox.tip('删除成功');
		  	$("#"+parentViewId).remove();
		}	
	});
};
coborrower.format=function(){
	var index = null;
	var initCoboIndex=0;
	var props = null;
	var curProp = null;
	var tableId = null;
	var curPanelId = null;
	$('.contactPanel').each(function(){
		var initCoboContactIndex = 0;
		var contactPanel = $(this);
		index = contactPanel.attr('index');
		curPanelId = contactPanel.attr("id");
		props = coborrower.coboInputProp;
		for(m in props){
			curProp = props[m];
			contactPanel.find(":input[name$='"+curProp+"']").each(function(){
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
			contactPanel.find("input[name$='"+curProp+"']").each(function(){
				$(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+curProp);
			});	
		}
		props = coborrower.coboSelectProp;
		for(m in props){
			curProp = props[m];
			contactPanel.find("select[name$='"+curProp+"']").each(function(){
				$(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+curProp);
			});	
		}
		props = coborrower.coboTextareaProp;
		for(m in props){
			curProp = props[m];
			contactPanel.find("textarea[name$='"+curProp+"']").each(function(){
				$(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+curProp);
			});
		}
		//联系人table
		tableIds = new Array('relatives_contact_table_'+index,'worktogether_contact_table_'+index,'other_contact_table_'+index);
		for(i in tableIds){
			tableId = tableIds[i];
			var table = $("#"+tableId);
			var endIndex = initCoboContactIndex;
			props = coborrower.coboContactSelectProp;
			for(m in props){
				curProp = props[m];
				var coboContactIndex = initCoboContactIndex;
				table.find("select[name$='"+curProp+"']").each(function(){
					$(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+coborrower.coboContactPrefix+"["+coboContactIndex+"]"+curProp);
					coboContactIndex++;
				});
			}
			props = coborrower.coboContactInputProp;
			for(m in props){
				curProp = props[m];
				var coboContactIndex = initCoboContactIndex;
				table.find("input[name$='"+curProp+"']").each(function(){
					if($(this).attr('name')!=='loanCustomer.id'){
						$(this).attr('name',coborrower.coboPrefix+"["+initCoboIndex+"]"+coborrower.coboContactPrefix+"["+coboContactIndex+"]"+curProp);
						coboContactIndex++;
						if(endIndex < coboContactIndex){
							endIndex = coboContactIndex;
						}
					}
				});
			}
			initCoboContactIndex = endIndex;
		}
		initCoboIndex++;
	});
};


coborrower.initCustomerHouseHoldProperty = function(ele){
	var value = ele.val();
	var remark = ele.siblings("input[name$='residentialCategoryRemark']");
	if(value == 7){
		remark.show();
		remark.addClass("required");
	}else{
		remark.hide();
		remark.val("");
		remark.removeClass("required");
	}
}

coborrower.initBorrowingPurposes = function(ele){
	var value = ele.val();
	var remark = ele.siblings("input[name$='borrowingPurposesRemark']");
	if(value == 12){
		remark.show();
		remark.addClass("required");
	}else{
		remark.hide();
		remark.val("");
		remark.removeClass("required");
	}
}

coborrower.initMainPaybackResource = function(ele){
	var value = ele.val();
	var remark = ele.siblings("input[name$='mainPaybackResourceRemark']");
	if(value == 7){
		remark.show();
		remark.addClass("required");
	}else{
		remark.hide();
		remark.val("");
		remark.removeClass("required");
	}
}

coborrower.initWhoCanKnowTheBorrowing = function(ele){
	var chooseOther = false;
	ele.parent().find("input[name$='whoCanKnowTheBorrowing']").each(function(){
		var checkBoxInput = $(this);
		if(checkBoxInput.prop("checked") && checkBoxInput.val() == '4'){
			chooseOther = true;			
		}
	});
	var remark = ele.parent().siblings("input[name$='whoCanKnowTheBorrowingRemark']");
	if(chooseOther){
		remark.show();
		remark.addClass("required");
	}else{
		remark.hide();
		remark.val("");
		remark.removeClass("required");
	}
}

coborrower.initOtherContactTable = function(){
	
	//工作联系人 选择其他时，备注必填
	$("table[id^='worktogether_contact_table']").each(function(){
		$(this).find("select[name$='contactRelation']").each(function(){
			coborrower.initOtherContact($(this));
			$(this).unbind("change");
			$(this).bind("change", function(){
				coborrower.initOtherContact($(this));
			});
		});
	});
}

coborrower.initOtherContact = function(ele){
	
	var value = ele.val();
	var remark = ele.parent().parent().find("input[name$='remarks']");
	if(value == 2){
		remark.addClass("required");
	}else{
		remark.val("");
		remark.removeClass("required");
	}
}

/**
 * 初始化自然人保证人信息
 * By 任志远  2016年09月27日
 */
coborrower.naturalGuarantorInit = function(page){
	//添加联系人按钮
	$(":input[name='addCobContactBtn']").each(function(){
		$(this).unbind("click");
		$(this).bind('click',function(){
			var taObj = $(this).attr('parentIndex');
			var tableId = $(this).attr('tableId');
			var view;
			if(tableId.indexOf('relatives') != -1){
				view = "_loanFlowNaturalGuarantorContactRelationItem";
			}else if(tableId.indexOf('worktogether') != -1){
				view = "_loanFlowNaturalGuarantorContactWorkTogetherItem";
			}else{
				view = "_loanFlowNaturalGuarantorContactOtherItem";
			}
			var tabLengthStr=$('#'+tableId).attr("currIndex");
			var tabLength = parseInt(tabLengthStr)+1;
		  	launch.additionItem(tableId, view, taObj, tabLength, null);
		  	$('#'+tableId).attr("currIndex",tabLength);
		});
	});
	//住宅类别 选择其他时，设置必填项
	$("select[name$='customerHouseHoldProperty']").each(function(){
		coborrower.initCustomerHouseHoldProperty($(this));
		$(this).unbind("change");
		$(this).bind("change", function(){
			coborrower.initCustomerHouseHoldProperty($(this));
		});
	});
	//主要借款用途	选择其他时 设置必填项
	$("select[name$='borrowingPurposes']").each(function(){
		coborrower.initBorrowingPurposes($(this));
		$(this).unbind("change");
		$(this).bind("change", function(){
			coborrower.initBorrowingPurposes($(this));
		});
	});
	//主要还款来源 选择其他时，设置必填项
	$("select[name$='mainPaybackResource']").each(function(){
		coborrower.initMainPaybackResource($(this));
		$(this).unbind("change");
		$(this).bind("change", function(){
			coborrower.initMainPaybackResource($(this));
		});
	});
	//以上可知晓本次借款的联系人
	$("input[name$='whoCanKnowTheBorrowing']").each(function(){
		coborrower.initWhoCanKnowTheBorrowing($(this));
		$(this).unbind("click");
		$(this).bind("click", function(){
			coborrower.initWhoCanKnowTheBorrowing($(this));
		});
	});
	
	coborrower.initOtherContactTable();
	
	if("apply" == page){
		// 申请 删除自然人保证人
		$("input[name='delCoborrower']").each(function(){
			$(this).unbind("click");
			$(this).bind('click',function(){
				var index = $(this).attr("index");
				var coboId = $(this).attr("coboId");
				coborrower.del("contactPanel_"+index,"COBORROWER",coboId);
			});
		});
	}else if("storeReview"){
		// 代办 删除自然人保证人
		$("input[name='delCoborrower']").each(function(){
			$(this).bind('click',function(){
				var index = $(this).attr("index");
				var coboId = $(this).attr("coboId");
				coborrower.deleteCobo("contactPanel_"+index,$('#response').val(),coboId);
			});
		});
	}
	
	
	//初始化户籍地址
	loan.batchInitCity("coboHouseholdProvince", "coboHouseholdCity", "coboHouseholdArea");
	areaselect.batchInitCity("coboHouseholdProvince", "coboHouseholdCity", "coboHouseholdArea");
	//初始化居住地址
	loan.batchInitCity("coboLiveingProvince", "coboLiveingCity", "coboLiveingArea");
	areaselect.batchInitCity("coboLiveingProvince", "coboLiveingCity", "coboLiveingArea");
	//初始化公司地址
	loan.batchInitCity("compProvince", "compCity", "compArer");
	areaselect.batchInitCity("compProvince", "compCity", "compArer");
	
	//初始化借款意愿产品
	$("select[name$='productType']").each(function(){
		//产品类别ID
		var productId = $(this).prop("id");
		//申请期限
		var loanMonths = $(this).parents("tr").find("select[name$='loanMonths']");
		//申请期限ID
		var monthId = loanMonths.prop("id");
		//申请期限 数据库记录
		var loanMonthsRecord = loanMonths.siblings("input").val();
		//咨询时间
		var consultTime = $("#consultTime").val();
		//申请金额下限
		var limitLowerId = $(this).parents("tr").find("input[id^='limitLower']").prop("id");
		//申请金额上限
		var limitUpperId = $(this).parents("tr").find("input[id^='limitUpper']").prop("id");
		
		loan.initProductWithLimit(productId, monthId,limitLowerId,limitUpperId, consultTime);
		loan.selectedProductWithLimit(productId, monthId, loanMonthsRecord, limitLowerId, limitUpperId, consultTime);
	})
	
	//主借人产品类型
	var customerProductType = $("#customerProductType").val();
	//薪水借，优房借，优卡借，精英借 月税后工资必填
	if('A003' == customerProductType || 'A008' == customerProductType || 'A007' == customerProductType || 'A014' == customerProductType){
		$("input[name$='compSalary']").each(function(){
			$(this).siblings("label").children("span").show();
			$(this).removeClass("required");
			$(this).addClass("required");
		});
	}else{
		$("input[name$='compSalary']").each(function(){
			$(this).siblings("label").children("span").hide();
			$(this).removeClass("required");
		});
	}
	//为农信借时，字段为非必填（邮箱和户籍地址,工作信息模块非必填）
	if('A021'==customerProductType){
		$("select[name$='coboHouseholdProvince']").each(function(){
			$(this).siblings("label").children("span").hide();
			$(this).removeClass("required");
		});
		$("select[name$='coboHouseholdCity']").each(function(){
			$(this).removeClass("required");
		});
		$("select[name$='coboHouseholdArea']").each(function(){
			$(this).removeClass("required");
		});
		$("input[name$='coboHouseholdAddress']").each(function(){
			$(this).removeClass("required");
		});
		$("input[name$='coboEmail']").each(function(){
			$(this).siblings("label").children("span").hide();
			$(this).removeClass("required");
		});
		$("input[name$='compName']").each(function(){
			$(this).siblings("label").children("span").hide();
			$(this).removeClass("required");
		});
		$("input[name$='compTel']").each(function(){
			$(this).siblings("label").children("span").hide();
			$(this).removeClass("required");
		});
		$("input[name$='compTelExtension']").each(function(){
			$(this).removeClass("required");
		});
		$("input[name$='compSalaryDay']").each(function(){
			$(this).siblings("label").children("span").hide();
			$(this).removeClass("required day");
		});
		$("select[name$='dictSalaryPay']").each(function(){
			$(this).siblings("label").children("span").hide();
			$(this).removeClass("required");
		});
		$("select[name$='compProvince']").each(function(){
			$(this).siblings("label").children("span").hide();
			$(this).removeClass("required");
		});
		$("select[name$='compCity']").each(function(){
			$(this).removeClass("required");
		});
		$("select[name$='compArer']").each(function(){
			$(this).removeClass("required");
		});
		$("input[name$='compAddress']").each(function(){
			$(this).removeClass("required");
		});
	}else{
		$("select[name$='coboHouseholdProvince']").each(function(){
			$(this).siblings("label").children("span").show();
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("select[name$='coboHouseholdCity']").each(function(){
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("select[name$='coboHouseholdArea']").each(function(){
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("input[name$='coboHouseholdAddress']").each(function(){
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("input[name$='coboEmail']").each(function(){
			$(this).siblings("label").children("span").show();
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("input[name$='compName']").each(function(){
			$(this).siblings("label").children("span").show();
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("input[name$='compTel']").each(function(){
			$(this).siblings("label").children("span").show();
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("input[name$='compTelExtension']").each(function(){
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("input[name$='compSalaryDay']").each(function(){
			$(this).siblings("label").children("span").show();
			$(this).removeClass("required day");
			$(this).addClass("required day");
		});
		$("select[name$='dictSalaryPay']").each(function(){
			$(this).siblings("label").children("span").show();
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("select[name$='compProvince']").each(function(){
			$(this).siblings("label").children("span").show();
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("select[name$='compCity']").each(function(){
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("select[name$='compArer']").each(function(){
			$(this).removeClass("required");
			$(this).addClass("required");
		});
		$("input[name$='compAddress']").each(function(){
			$(this).removeClass("required");
			$(this).addClass("required");
		});
	}
}
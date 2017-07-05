var launch = {};
launch.viewName = new Array('_loanFlowCustomer','_loanFlowMate','_loanFlowApplyInfo','_loanFlowCoborrower','_loanFlowCreditInfo','_loanFlowCompany','_loanFlowHouse','_loanFlowContact','_loanFlowBank');
var checkResult=true;
var customError = "";
/**
 * 发起流程
 * @param num
 * @param bankForm
 * 
 */
function startFlow (num, bankForm,btnId) {
	   
}
launch.launchFlow = function(num, bankForm,btnId) {
	$("#"+bankForm).validate({
			onkeyup: false, 
			rules : {
				'loanBank.bankAccount' : {
					equalTo:"#firstInputBankAccount",
					digits:true,
					min:0,
					maxlength:19
				},
				'firstInputBankAccount' : {
					minlength:16
				}
			},
			messages : {
				'loanBank.bankAccount':{
					equalTo: "输入银行卡号不一致",
					digits:  "银行卡号必须为数字类型",
					min:"输入的数据不合法",
					maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串")
				},
				'firstInputBankAccount' : {
					minlength:$.validator.format("请输入一个长度最少是{0}的数字字符串")
				}
			},
			submitHandler : function(form) {
				$('#launchFlow').attr('disabled',true);
				$('#tempSave').attr('disabled',true);
				if(formConfig.button=='launchFlow'){
					var loanCode = $("#loanCode").val();
					var oneedition =  $("#oneedition").val();
					$.ajax({
						url:ctx+"/borrow/borrowlist/asynCheckInput?loanCode="+loanCode+"&oneedition="+oneedition,
						type:'post',
						data:{},
						dataType:'json',
						success:function(data){
							if(!data.success){
								if(data.flag!='4'){
									art.dialog.alert(data.message);
									$('#tempSave').removeAttr('disabled');
									$('#launchFlow').removeAttr('disabled');
									return data.success;
								}else{
								    art.dialog.confirm(data.message,
									  function(){
								    	$.ajax({
											type:'post',
											url:ctx+"/apply/dataEntry/delAdditionItem",
											data:{
												'delType':data.itemType,
												'tagId':  data.tagId
											},
											dataType:'json',
											success:function(result){
												if(result){
													top.$.jBox.tip('删除成功');
													$('#tempSave').removeAttr('disabled');
													$('#launchFlow').removeAttr('disabled');
												}else{
													top.$.jBox.tip('删除失败');
													$('#tempSave').removeAttr('disabled');
													$('#launchFlow').removeAttr('disabled');
												}
											},
											error:function(){
												art.dialog.alert("服务器异常！");
											}
					  					});
						     			},function(){
											});
								}
							}else{
								art.dialog.confirm(data.message,
									function(){
									var url = ctx + "/borrow/borrowlist/launchFlow?flag=" + num;
									form.action = url;
									form.submit();
									 //startFlow (num, bankForm,btnId);
									},function(){
										$('#tempSave').removeAttr('disabled');
										$('#launchFlow').removeAttr('disabled');
									}
							    );
							}
						},
						async:false, 
						error:function(){
							art.dialog.alert('服务器异常！');
							return false;
						}
					});
				}else if(formConfig.button=='tempSave'){
					top.$.jBox.tip('正在保存数据...');
					form.action = ctx + "/apply/dataEntry/saveApplyInfo?"+"&flag=" + num;
					form.submit();
				}
			},
			showErrors:function(errorMap,errorList) {
				$('#tempSave').removeAttr('disabled');
				$('#launchFlow').removeAttr('disabled');
				this.defaultShowErrors();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio")
					|| element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
};

launch.getCustomerInfo = function(flag){
	var url="";
	if(flag=="1"){
	    url = ctx+"/bpm/flowController/openLaunchForm";
	    $('#custInfoForm').attr('action',url);
	    $('#custInfoForm').submit();
	}else{
		url = ctx+"/apply/dataEntry/getApplyInfo?flag="+flag+"&viewName="+launch.viewName[parseInt(flag)-1];
		$('#custInfoForm').attr('action',url);
	    $('#custInfoForm').submit();
	}
};
launch.checkInput = function(num, bankForm,btnId){
	var loanCode = $("#loanCode").val();
	$.ajax({
		url:ctx+"/borrow/borrowlist/asynCheckInput?loanCode="+loanCode,
		type:'post',
		data:{},
		dataType:'json',
		success:function(data){
			if(!data.success){
				if(data.flag!='4'){
					art.dialog.alert(data.message);
					return data.success;
				}else{
				    art.dialog.confirm(data.message,
					  function(){
				    	$.ajax({
							type:'post',
							url:ctx+"/apply/dataEntry/delAdditionItem",
							data:{
								'delType':data.itemType,
								'tagId':  data.tagId
							},
							dataType:'json',
							success:function(result){
								if(result){
									top.$.jBox.tip('删除成功');
								}else{
									top.$.jBox.tip('删除失败');
								}
							},
							error:function(){
								art.dialog.alert("服务器异常！");
							}
	  					});
		     			},function(){
							return true;
						});
				}
			}else{
				art.dialog.confirm(data.message,
					function(){
					return true;
					 //startFlow (num, bankForm,btnId);
					},function(){
						$('#tempSave').removeAttr('disabled');
						$('#launchFlow').removeAttr('disabled');
						return false;
					}
			    );
			}
		},
		async:false, 
		error:function(){
			art.dialog.alert('服务器异常！');
			return false;
		}
	});
};
//------------0-




//-------------





// param 自动扩展参数
launch.additionItem = function(tagetId, viewName, parentIndex, currIndex,param) {
	var url = ctx + "/apply/dataEntry/oneedition?viewName="+ viewName + "&parentIndex=" + parentIndex
	+ "&currIndex=" + currIndex+"&oneedition="+$('#oneedition').val();
	
	if(param!=null && param!=''){
		url +="&"+param; 
	}
	$.ajax({
				type : "POST",
				url  : url,
				dataType : 'html',
				success : function(data) {
					if(tagetId=="contactArea" || tagetId.indexOf("table_")!=-1 ||
							tagetId=="loanCreditArea"){
						$('#' + tagetId+" tbody").append(data);
					}else{
						$('#' + tagetId).append(data);
					}
					// 重新绑定增加联系人事件
					if ($(":input[name='addCobContactBtn']").length > 0) {
						$(":input[name='addCobContactBtn']")
								.each(function() {
											$(this).unbind("click");
											$(this).bind('click',function(){
													var taObj = $(this).attr('parentIndex');
													var tabLengthStr=$('#table_'+taObj).attr("currIndex");
													var tabLength = parseInt(tabLengthStr)+1;
												  launch.additionItem($(this).attr('tableId'),'_loanFlowCoborrowerContactItem',taObj,tabLength);
												  $('#table_'+taObj).attr("currIndex",tabLength);
												});
								    		$(this).addClass("");
										});
					}
					if ($(".contactPanel").length > 0) {
						$("select").each(function() {
							$(this).unbind('change');
						});
						loan.batchInitCity("coboHouseholdProvince",
								"coboHouseholdCity", "coboHouseholdArea");
						loan.batchInitCity("coboLiveingProvince",
								"coboLiveingCity", "coboLiveingArea");
						//新增初始化单位地址
						loan.batchInitCity("coboCompProvince", 
								"coboCompCity", "coboCompArer");
						
						$("input[name='delCoborrower']").each(function(){
							$(this).unbind('click');
							$(this).bind('click',function(){
								var index = $(this).attr("index");
								var coboId = $(this).attr("coboId");
								coborrower.del("contactPanel_"+index,"COBORROWER",coboId);
							});
						});
					}
					// 重新初始化省市联动事件
					if ($("table[id^='loanHouseArea']").length>0) {
						loan.batchInitCity('houseProvince', 'houseCity',
								'houseArea');
					}
					
					
					
					// 重新绑定房产信息删除事件
					if($("input[name='delHouseItem']").length>0){
						$("input[name='delHouseItem']").each(function(){
							$(this).unbind('click');
							$(this).bind('click',function(){
								house.delItem($(this).attr("currId"),"loanHouseArea",$(this).attr("dbId"),'HOUSE');
							});
						});
					}
					// 重新绑定关系联动事件
					if($("select[name$='relationType']").length>0){
						$("select[name$='relationType']").each(function(){
							$(this).unbind('change');
							$(this).bind('change',function(){
								var id = $(this).attr("id");
								var index = $(this).attr("index");
								var targetId = "contactRelation_"+index;
								launch.getRelationDict($('#'+id+" option:selected").val(),targetId);
							});
						});
					}
					// 重新渲染下拉列表
					if($("select").length>0){
						$("select").each(function(){
							$(this).select2();
						});
					}
				}
			});
};

// 手机号码验证
jQuery.validator.addMethod("isMobile", function(value, element) {
    var length = value.length;
    var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "手机号有误");
//车牌验证
jQuery.validator.addMethod("plateNum", function(value, element) {
    var length = value.length;
    var plateNumber = /^[A-Z]{1}[A-Z0-9]{5}$/;
    var flag = false;
    if (length == 7) {
    	var str1 = new Array("京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "渝", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新", "学");
    	for (var i = 0; i < str1.length; i++) {
	    	if (value.substr(0, 1) == str1[i]) {
	    		flag = true;
	    		value = value.substr(1, length);
	    		break;
	    	} else {
	    		continue;
	    	}
	    }
    }
    return this.optional(element) || (flag && plateNumber.test(value));
}, "车牌格式，例：京A88888");
//邮箱格式验证
jQuery.validator.addMethod("isEmail", function(value, element) {
    var eEmail = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return this.optional(element) || (eEmail.test(value));
}, "您的电子邮件格式不正确");
// 贷款余额验证
jQuery.validator.addMethod("balanceAmountCheck", function(value, element) {
	var isOK = true;
	var targName = element.name;
	var clLimitName = targName.split(".")[0]+".creditLoanLimit";
	if(value!='' && value!=null){
	  var clLimitValue = $("input[name='"+clLimitName+"']")[0].value;
	  if(clLimitValue=="" || clLimitValue==null || parseFloat(clLimitValue)<parseFloat(value)){
		  isOK = false;
	  }
	}
    return isOK;
}, "贷款余额不能大于贷款额度");
// 贷款供额验证
jQuery.validator.addMethod("monthsAmountCheck", function(value, element) {
	var isOK = true;
	var targName = element.name;
	var clLimitName = targName.split(".")[0]+".creditLoanLimit";
	if(value!='' && value!=null){
	  var clLimitValue = $("input[name='"+clLimitName+"']")[0].value;
	  if(clLimitValue=="" || clLimitValue==null || parseFloat(clLimitValue)<parseFloat(value)){
		  isOK = false;
	  }
	}
    return isOK;
}, "每月供额度不能大于贷款额度");
// 累计工作年限验证当前年龄-16
jQuery.validator.addMethod("compWorkExperience", function(value, element) {
	var isOk = true;
	var customerCode = $("#customerCode").val();
	$.ajax({
		type : "POST",
		url : ctx + "/apply/storeReviewController/wokeAgeCheck",
		data : {
			'customerCode' : customerCode
		},
		dataType:'json',
		async:false,
		success : function(data) {
			var workAge = parseInt(data) - 16 - value;			
			isOk = (workAge >= 0);
		}
	});
	//return isOk;
	if(value!=''){
		var regex= /^(\d+\.\d{1,1}|\d+)$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "工作年限不合法");
// 信用卡总数验证
jQuery.validator.addMethod("totalCardNumCheck", function(value, element) {
	var isOK = true;
	var cardNum = parseInt(value);
	if(!isNaN(cardNum)){
       if(value>2147483647){
    	   isOK = false;
       }
	
	}
    return isOK;
}, "输入不合法");
//信用卡总数验证
jQuery.validator.addMethod("contactCheck", function(value, element) {
	var isOK = true;
	var contacts = $(":input[name$='contactName']");
	var curName = element.name;
	var indexName = null;
	var indexValue = null;
	for(var i=0;i<contacts.length;i++){
		indexName =contacts[i].name;
		indexValue = contacts[i].value;
		if(indexName!=curName){
			if(indexValue==value){
				isOK = false;
				break;
			}
		}
	}
    return isOK;
}, "联系人姓名相同");
// 房屋面积输入校验
jQuery.validator.addMethod("houseAreaCheck", function(value, element) {
   var isOK = true;
   if(value!=''){
		var intPart;
		var numPart;
		if(value.indexOf(".")!=-1){
			intPart = value.split(".")[0];
		}else{
			intPart = value;
		}
	   if(intPart.length>10){
		 isOK = false;
      }
	   numPart = parseFloat(value); 
	   if(numPart<=0){
		 isOK = false; 
	   }
	}
   return this.optional(element) || (isOK);
}, "房屋面积必须大于0，且整数部分不能超过10位");
// 中英文校验
jQuery.validator.addMethod("stringCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z\u4e00-\u9fa5 .·]{1,30}$/; 
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能输入长度为30位中文、英文、空格或者点号");
//中英文校验
jQuery.validator.addMethod("stringCheck2", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z\u4e00-\u9fa5 ]{1,30}$/; 
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能输入长度为30位中文、英文、空格");
//验证详细地址
jQuery.validator.addMethod("detailAddress", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z0-9-\u4E00-\u9FA5]{1,50}$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "您输入的地址格式有误！");
//验证单位名称
jQuery.validator.addMethod("companyName", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z0-9\u4E00-\u9FA5*（） ]{1,30}$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "您输入的单位名称格式有误！");
//中英文校验
jQuery.validator.addMethod("limitCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var limitLower = $('#limitLower').val();
		var limitUpper = $('#limitUpper').val();
		if(limitLower!='' && limitLower!=null){
			limitLower = parseFloat(limitLower);
		}
		if(limitUpper!='' && limitUpper!=null){
			limitUpper = parseFloat(limitUpper);
		}
		if(parseFloat(value)<limitLower || parseFloat(value)>limitUpper){
			isOK = false;
		}
    }
	return isOK;
}, "申请额度不在有效的额度限制范围内");
// 公司名称中英文校验
jQuery.validator.addMethod("compNameCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z\u4e00-\u9fa5()（） *]{1,50}$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能输入中文、英文、空格跟括号，且总长度不能超过20位");
// 两位小数校验
jQuery.validator.addMethod("numCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex1=/^\d{1,10}\.\d{1,2}$/;
		var regex2=/^\d{1,10}$/;
		isOK = regex1.test(value)||regex2.test(value);
    }
	return isOK;
}, "数值不合法");
//两位小数校验
jQuery.validator.addMethod("positiveNumCheck", function(value, element) {
	var isOK = true;
	var num = parseFloat(value);
	if(num<0){
	
		isOK = false;
    }
	return this.optional(element) || (isOK);
}, "输入的数值不能小于0");
// 其它所得校验
jQuery.validator.addMethod("amountCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var intPart;
		if(value.indexOf(".")!=-1){
			intPart = value.split(".")[0];
		}else{
			intPart = value;
		}
	   if(intPart.length>10){
		 isOK = false;
       }
	   numPart = parseFloat(value); 
	   if(numPart<0){
		 isOK = false; 
	   }
	}
	return this.optional(element) || (isOK);
}, "输入的金额不能小于0,整数部分不能超过10位");
// 电话校验
jQuery.validator.addMethod("isTel", function(value, element) {
	var telRegx = /^0\d{2,3}-\d{7,8}$/;
    var result=true;
 	if(value!=null && value!=''){
 		 if(!telRegx.test(value)){
 			result = false;
         }
	}
    return this.optional(element) || (result);
}, "号码格式：XXX(X)-XXXXXXX(X)");
//手机或座机
jQuery.validator.addMethod("isPhone", function(value, element) {
	var telRegx = /^0\d{2,3}-\d{7,8}$/;
	var mobile = /^(13[0-9]{9})|(18[0-9]{9})|(14[0-9]{9})|(17[0-9]{9})|(15[0-9]{9})$/;
    var result=true;
 	if(value!=null && value!=''){
 		 if(!telRegx.test(value) || !mobile.test(value)){
 			result = false;
         }
	}
    return this.optional(element) || (result);
}, "请输入正确格式的座机号或手机号！");
// 证件号码检测（配偶）
jQuery.validator.addMethod("isCertificate", function(value, element) {
	var isOK= true;
	var cardType = $("#mateCardType option:selected").val();
	var cardTypeName= $("#mateCardType option:selected").text();
	
	if(cardTypeName == "身份证" || cardTypeName == "临时身份证"){ // 身份证检测
			isOK = checkIdcard(value);
	}else if(cardTypeName=="户口薄"){ // 户口检测
		    isOK = checkNodeIdNum(value);
	}else if(cardTypeName=="军官证" || cardTypeName=="士兵证"){ // 军官证检测
		    isOK = checkOfficersNum(value);
	}else if(cardTypeName=="护照"){ // 护照检测
		    isOK = checkPassport(value);
	}else if(cardTypeName=="港澳居民来往内地通行证" || cardTypeName=="台湾同胞来往内地通行证"){ // 港澳通行证检测
		    isOK = checkGatepassNum(value);
	}else if(cardTypeName=="外国人居留证"){ // 外国人居留证
		  //  isOK = checkCerpacNum(value);
	}else if(cardTypeName=="警官证"){
		    isOK = checkPoliceNum(value);
	}
   return  isOK;
}, "证件号码输入有误");
// 共借人手机号码检查（本页），只对第一个共借人加上该校验方法。
jQuery.validator.addMethod("coboMobileDiff1", function(value, element) {
	var isOK = true;
	$('#coborrowForm').removeAttr("novalidate");
	var targetArray = $(":input[name$='.coboMobile']");
	var targetArray2 = $(":input[name$='.coboMobile2']");
	var coboContactArray = $(":input[name$='.contactMobile']"); 
	var arrayLength = targetArray.length;
	var arrayLength2 = targetArray2.length;
	var same = false;
	var coboContactLength = coboContactArray.length;
	var curValue;
	var curName;
	var indexValue;
	var indexName;
	var indexContactName;
	var indexContactValue;
	var i = 0;
	curValue = value;
	curName = element.name;
	// 验证并加上提示信息
	for(;i<arrayLength;i++){
		indexValue = targetArray[i].value;
		indexName = targetArray[i].name;
		if(indexName!=curName && curValue!='' && indexValue!='' && curValue==indexValue){
			isOK = false;
			break;
		}
	}
	if(isOK){
		// 验证并加上提示信息
		for(i=0;i<arrayLength2;i++){
			indexValue = targetArray2[i].value;
			indexName = targetArray2[i].name;
			if(indexName!=curName && curValue!='' && indexValue!='' && curValue==indexValue){
				isOK = false;
				break;
			}
		}
	}
	if(isOK){
	    // 共借人联系人跟当前号码是否重复
		for(i = 0;i<coboContactLength;i++){
			indexContactName = coboContactArray[i].name;
			indexContactValue = coboContactArray[i].value;
			if(curName!=indexContactName && curValue!='' && indexContactValue!='' && curValue==indexContactValue){
				isOK = false;
				break;
			}
		}
	 }
	return isOK;
}, "手机号码重复");

// 证件号码检测（共借人 当前页面跟数据库中已存在的手机号码做比对）
jQuery.validator.addMethod("coboMobileDiff2", function(value, element) {
	if(value!='' && value!=null){
	  launch.phoneNumCheck(value,"coboBorrower");
	}else{
	  customError ="";
	  checkResult = true;
	}
	jQuery.validator.messages.coboMobileDiff2 = customError;
    return checkResult;
}, "手机号码重复");
// 证件号码检测（配偶）
jQuery.validator.addMethod("mateMobileDiff", function(value, element) {
	launch.phoneNumCheck(value,"mate");
    jQuery.validator.messages.mateMobileDiff = customError;
    return checkResult;
}, customError);
// 证件号码检测（主借人）
jQuery.validator.addMethod("mainBorrowMobileDiff1", function(value, element) {
	var isOK= true;
	if(value!='' && value!=null && value!=undefined){
		var curValue = $("#customerPhoneFirst").val();
		if(value == curValue){
			isOK = false;
		}
	}
    return isOK;
}, "跟当前页中的手机号码重复");


//手机号码检测（主借人）
jQuery.validator.addMethod("mainBorrowMobileDiff2", function(value, element) {
	if(value!='' && value!=null){
      launch.phoneNumCheck(value,"mainBorrower");
	}
	jQuery.validator.messages.mainBorrowMobileDiff2 = customError;
    return checkResult;
},customError);
//联系人手机号码检查（本页）
jQuery.validator.addMethod("contactMobileDiff1", function(value, element) {
	var isOK = true;
	var same = false;
	var targetArray = $(":input[name$='.contactMobile']");
	var arrayLength = targetArray.length;
	var curValue;
	var curName;
	var indexValue;
	var indexName;
	var i = 0;
	curValue = value;
	curName  = element.name;
	// 第二步验证并加上提示信息
	for(;i<arrayLength;i++){
		indexValue = targetArray[i].value;
		indexName = targetArray[i].name;
		if(curName!=indexName && curValue!='' && indexValue!='' && curValue==indexValue){
			isOK = false;
			break;
		}
	}
   return isOK;
}, "手机号码重复");
//证件号码检测（联系人）
jQuery.validator.addMethod("contactMobileDiff2", function(value, element) {
   	if(value!='' && value!=null){
	  launch.phoneNumCheck(value,"mainContact");
	}else{
	  customError ="";
	  checkResult = true;
	}
	jQuery.validator.messages.contactMobileDiff2 = customError;
	return checkResult;
}, "手机号码重复");
// 证件号码检测（共借人）
jQuery.validator.addMethod("coboCertCheck", function(value, element) {
	var isOK= true;
	var targName = element.name;
	var otherTargName = targName.split(".")[0]+".dictCertType";
	var cardType = $("select[name='"+otherTargName+"'] option:selected").val();
    var cardTypeName= $("select[name='"+otherTargName+"'] option:selected").text();
    
	if(cardTypeName == "身份证" || cardTypeName == "临时身份证"){ // 身份证检测
			isOK = checkIdcard(value);
	}else if(cardTypeName=="户口薄"){ // 户口检测
		    isOK = checkNodeIdNum(value);
	}else if(cardTypeName=="军官证" || cardTypeName=="士兵证"){ // 军官证检测
		    isOK = checkOfficersNum(value);
	}else if(cardTypeName=="护照"){ // 护照检测
		    isOK = checkPassport(value);
	}else if(cardTypeName=="港澳居民来往内地通行证" || cardTypeName=="台湾同胞来往内地通行证"){ // 港澳通行证检测
		    isOK = checkGatepassNum(value);
	}else if(cardTypeName=="外国人居留证"){ // 外国人居留证
		  //  isOK = checkCerpacNum(value);
	}else if(cardTypeName=="警官证"){
		    isOK = checkPoliceNum(value);
	}
    return isOK;
}, "证件号码输入有误");
jQuery.validator.addMethod("effectiveCertificate", function(value, element) {
	   var isOK= true;
	   var startDay = $('#idStartDay').val();
	   var endDay = $('#idEndDay').val();
	   var checked = $('#longTerm').prop('checked');
	   if(startDay=='' || startDay==null){
		   isOK = false;
		   jQuery.validator.messages.effectiveCertificate = "请填写证件有效期";
	   }else{
		   if((endDay==''|| endDay==null) && (checked==false || checked==undefined)){
	    	isOK = false;
	    	jQuery.validator.messages.effectiveCertificate = "证件有效期输入有误";
		   }
	   }
     return isOK;
	/*   var endDay = $('#idEndDay').val();
	   var checked = $('#longTerm').prop('checked');
       if((endDay==''|| endDay==null) && (checked==false || checked==undefined)){
	    	isOK = false;
	    }
    return this.optional(element) || (isOK);*/
}, "证件有效期输入有误");

//共借人证件有效期校验
jQuery.validator.addMethod("coboEffeCertificate", function(value, element) {
	   var isOK= true;
	   var curId = element.id;
	   var curIndex = curId.split("_")[1];
	   var startDayId = "idStartDay_"+curIndex;
	   var longTermId = "longTerm_"+curIndex;
	   var endDayId = "idEndDay_"+curIndex;
	   var startDay = $('#'+startDayId).val();
	   var endDay = $('#'+endDayId).val();
	   var checked = $('#'+longTermId).prop('checked');
	   if(startDay=='' || startDay==null){
		   isOK = false;
		   jQuery.validator.messages.coboEffeCertificate = "请填写证件有效期";
	   }else{
		   if((endDay==''|| endDay==null) && (checked==false || checked==undefined)){
	    	isOK = false;
	    	jQuery.validator.messages.coboEffeCertificate = "证件有效期输入有误";
		   }
	   }
 return isOK;
}, "证件有效期输入有误");
// 共借人证件号码查重
jQuery.validator.addMethod("coboCertNumCheck1", function(value, element) {
	   var isOK= true;
	   var curNumName = element.name;
	   var curTypeName = curNumName.split(".")[0]+".dictCertType";
	   var curTypeValue = $("select[name='"+curTypeName+"']")[0].value;
	   var typeArray = $("select[name$='.dictCertType']");
	   var numArray = $(":input[name$='.coboCertNum']");
	   var arrayLength = typeArray.length;
	   var indexTypeValue = null;
	   var indexTypeName = null;
	   var indexCertNumValue = null;
	   var indexCertNumName = null;
	   if(arrayLength>1){
		   for(var i = 0;i<arrayLength;i++){
			   indexTypeValue = typeArray[i].value;
			   indexTypeName = typeArray[i].name;
			   indexCertNumValue = numArray[i].value;
			   indexCertNumName = numArray[i].name;
			   if(curTypeName!=indexTypeName && indexCertNumName!=curNumName){
				   if(indexTypeValue==curTypeValue && value==indexCertNumValue){
					   isOK = false;
					   break;
				   }	
			   }
		   }
	   }
 return isOK;
}, "证件号码重复");
// 共借人证件号码远程查重 launch.certNumCheck = function(certType,certNum,inputType)
jQuery.validator.addMethod("coboCertNumCheck2", function(value, element) {
	var curNumName = element.name;
	var curTypeName = curNumName.split(".")[0]+".dictCertType";
	var curTypeValue = $("select[name='"+curTypeName+"']")[0].value;
	var oneedition =  $('#oneedition').val();

	if(oneedition==-1){
		launch.certNumCheck(curTypeValue,value,'coboBorrower');
	}
	else {
	}
	jQuery.validator.messages.coboCertNumCheck2 = customError;
	return checkResult;
}, "证件号码重复");
// 配偶证件号码查重
jQuery.validator.addMethod("mateNumCheck", function(value, element) {
  var curTypeValue = $("#mateCardType option:selected").val(); 
  launch.certNumCheck(curTypeValue,value,'mate'); 
  jQuery.validator.messages.mateNumCheck = customError;
  return checkResult;
}, "证件号码重复");
// onblur 通过证件号码更新证件中的x
launch.certifacteFormatByCertNum = function(value,element){
	
	if(value!=''&&value!=null){
		var certName = element.name;
		var prefix = certName.split(".")[0];
		var certTypeName = prefix + ".dictCertType";
		var certTypeValue = $("select[name='"+certTypeName+"']")[0].value;
		if("0"==certTypeValue){
			if(value.indexOf("x")!=-1){
				value = value.replace(/x/g,"X");
				$(":input[name='"+certName+"']")[0].value=value;
			}
		    if(value.length==15 || value.length==18){
		    	var sexName = prefix + ".coboSex";
		    	var num = value.charAt(value.length-2);
		    	var remainder = parseInt(num)%2;
		    	$("input[name='"+sexName+"']").each(function(){
		    		if($(this).val()=='1' && remainder==0){
		  			  $(this).attr('checked',true);
		  		    }
		  		  	if($(this).val()=='0' && remainder==1){
		  			  $(this).attr('checked',true); 
		  		    }
		    	});
		    }
		}
	}
	
	
	//校验身份证号码是否大于 22周岁 和50周岁
//===========

	//自然人保证人校验
	checkResult = true;
	customError = "";
  $.ajax({
	  url:ctx+"/apply/dataEntry/certNumCheck",
	  type:"post",
	  data:{
		  'certType':"0",
		  'certNum':value,
		  'inputType':"coboBorrowerPerson",
		  'loanCode':$('#loanCode').val(),
		  'customerCode':$('#customerCode').val(),
		  'consultId':$('#consultId').val()
	  },
	  dataType:'json',
	  success:function(data){
		  if(data.idNum==1){
			  top.$.jBox.tip( data.resultMsg);
		  }
	  },
	  async:false
  });	
	
	
//===========	
	
	
	
};
// onchange 通过证件类型更新证件号码
launch.certifacteFormatByCertType = function(value,element){
	if(value!='' && value!=null){
		var certTypeName= element.name;
		var certName = certTypeName.split(".")[0]+".coboCertNum";
		var certValue = $(":input[name='"+certName+"']")[0].value;
		if("0"==value){
			if(certValue.indexOf("x")!=-1){
				certValue = certValue.replace(/x/g,"X");
				$(":input[name='"+certName+"']")[0].value=certValue;
			}
		}
	}
};
launch.getRelationDict = function(parentValue,targetId){
  if(parentValue!='' && parentValue!=null){	
	  $.ajax({
		  type : "POST",
		  url : ctx + "/apply/dataEntry/getRelationDict",
		  data : {
			'parentValue' : parentValue
		  },
		  dataType:'json',
		  success : function(data) {
			  $("#" + targetId).empty();
			  $("#" + targetId).append("<option value=''>请选择</option>");
			  var i = 0;
			  if(data!=null){
				  for(;i<data.length;i++){
					  $("#" + targetId).append("<option value=" + data[i].value+ ">" + data[i].label + "</option>");
				  }
			  }
			  $("#" + targetId).trigger("change");
		  }
	  });
  }else{
	  $("#" + targetId).empty();
	  $("#" + targetId).append("<option value=''>请选择</option>"); 
	  $("#" + targetId).trigger("change");
  }
};
launch.initRelationDict = function(parentValue,targetId,targetValue){
	if(parentValue!='' && parentValue!=null){	
		$.ajax({
			type : "POST",
			url : ctx + "/apply/dataEntry/getRelationDict",
			data : {
				'parentValue' : parentValue
			},
			dataType:'json',
			success : function(data) {
				$("#" + targetId).empty();
				$("#" + targetId).append("<option value=''>请选择</option>");
				var i = 0;
				if(data!=null){
					for(;i<data.length;i++){
						if(targetValue==data[i].value){
							$("#" + targetId).append("<option selected=true value=" + data[i].value+ ">" + data[i].label + "</option>");	
						}else{
							$("#" + targetId).append("<option value=" + data[i].value+ ">" + data[i].label + "</option>");
						}
					}
				}
				$("#" + targetId).trigger("change");
				$("#" + targetId).attr("disabled", false);
			}
		});
	}else{
		  $("#" + targetId).empty();
		  $("#" + targetId).append("<option value=''>请选择</option>"); 
		  $("#" + targetId).trigger("change");
	  }
};
launch.phoneNumCheck = function(num,type){
	checkResult = true;
	customError = "";
  $.ajax({
	  url:ctx+"/apply/dataEntry/phoneNumCheck",
	  type:"post",
	  data:{
		  'currType':type,
		  'phoneNum':num,
		  'loanCode':$('#loanCode').val(),
		  'customerCode':$('#customerCode').val(),
		  'consultId':$('#consultId').val()
	  },
	  dataType:'json',
	  success:function(data){
		   checkResult = data.result;
		   customError = data.resultMsg;
	  },
	  async:false
  });	
};
launch.certNumCheck = function(certType,certNum,inputType){
	checkResult = true;
	customError = "";
  $.ajax({
	  url:ctx+"/apply/dataEntry/certNumCheck",
	  type:"post",
	  data:{
		  'certType':certType,
		  'certNum':certNum,
		  'inputType':inputType,
		  'loanCode':$('#loanCode').val(),
		  'customerCode':$('#customerCode').val(),
		  'consultId':$('#consultId').val()
	  },
	  dataType:'json',
	  success:function(data){
		   checkResult = data.result;
		   customError = data.resultMsg;
	  },
	  async:false
  });	
};



launch.saveApplyInfo =function(num,busiForm,btnId) {
	$("#"+busiForm).validate({
		onkeyup: false, 
		rules : {
			'loanCustomer.customerEmail' : {
				isEmail:true,
				email:true
			},
			'loanCustomer.customerPhoneSecond':{
				isMobile:true
			},
			'loanMate.mateCertNum':{
				isCertificate:true
			},
			'loanMate.mateTel':{
				isMobile:true
			},
			'loanInfo.loanApplyAmount':{
				required : true,
          	   	max:999999999,
          	   	number:true 
			},
     	   'customerLoanCompany.compWorkExperience' : {
    		   required : true,
        	   max:65,
        	   min:0.5,
        	   number:true,
        	   compWorkExperience : true    			 
    		},
    		'customerLoanCompany.compSalaryDay' :{
          	   max:31,
          	   digits:true,
          	   min:1
    		},
    		'customerLoanCompany.compSalary' :{
    		   max:999999999,
    		   min:100,
           	   number:true 
    		},
    		'customerLoanCompany.compTel':{
    		   isTel:true
    		},
    		'longTerm':{
    		   effectiveCertificate:true
    		},
    		'loanBank.bankAccount' : {
				equalTo:"#firstInputBankAccount",
				digits:true,
				min:0,
				maxlength:19
			},
			'loanRemark.remark':{
				maxlength:450,
			}
		},
		messages : {
			'loanCustomer.customerEmail':{
				email: "邮箱格式有误"	
			},
			'loanInfo.loanApplyAmount':{
				max:"输入金额有误",
				number : "金额只能是数字",
				min:"输入最小为{0}的数值"
			},
			'customerLoanCompany.compSalary' :{
				max:"收入金额有误",
				min:"输入最小{0}的数值"
			},
			'customerLoanCompany.compWorkExperience':{
				max:"工作年限有误",
				min:"输入最小为{0}的数值"
			},
			'customerLoanCompany.compSalaryDay' :{
          	   	max:"输入最大为{0}的数值",
          	   	min:"输入最小为{0}的数值"
    		},
    		'loanBank.bankAccount':{
				equalTo: "输入银行卡号不一致",
				digits:  "银行卡号必须为数字类型",
				min:"输入的数据不合法",
				maxlength:$.validator.format("请输入一个长度最多是 {0} 的数字字符串")
			},
			'loanRemark.remark':{
				maxlength:"输入的字符串长度最长为{0}字",
			}
		},
		showErrors:function(errorMap,errorList) {
			$('#'+btnId).removeAttr('disabled');
			this.defaultShowErrors();
		},
		submitHandler : function(form) {
			top.$.jBox.tip('正在保存数据...');
			$('#'+btnId).attr('disabled',true);
			var url = ctx + "/apply/dataEntry/saveApplyInfo?" + "flag=" + num;
			form.action = url;
			form.submit();
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio")
					|| element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
};
launch.idEffectiveDay = function(obj,targetId){
	var checked = obj.checked;
	if(checked || checked=='checked'){
		$("#"+targetId).val("");
		$('#'+targetId).attr('readOnly', true);
		$('#'+targetId).hide();
	} else {
		$('#'+targetId).removeAttr('readOnly');
		$('#'+targetId).show();
	}
};
launch.addMyValidateTip = function(t,val){
	
	  var fOffset = t.offset();
	  var top = t.position().top- t.height()-30;//fOffset.top ;//- t.height()-30;
	  var left =t.position().left;//+ t.width() - 30;// fOffset.left ;//+ t.width() - 10;
	  var html="<div  style='top: "+top+"px; display: block; left: "+left+"px;' class='ketchup-error-container'><ol><li>"+val+"</li></ol><span></span></div>";
	  //t.parent("td").append(html);
	
	  if(t.next(".ketchup-error-container").length==0){
		  
		  t.after(html);  
	  }else{
		  
		  t.next(".ketchup-error-container").css("display","")
	  }
	  
};
//护照编号检测
function checkPassport(num){
	var result = /^[a-zA-Z0-9]{3,21}$/.test(num);
	result = /^(P\d{7})|(G\d{8})$/.test(num)||result;
	return result;
}
// 军官证检测
function checkOfficersNum(num){
	var result = /^[a-zA-Z0-9]{7,21}$/.test(num);
	return result;
}
// 港澳通行证检测
function checkGatepassNum(num){
	var result = /^[a-zA-Z0-9]{5,21}$/.test(num);
	return result;
}
// 户口编号检测
function checkNodeIdNum(num){
	var result = /^[a-zA-Z0-9]{3,21}$/.test(num);
	return result;
}
// 外国人居留证检测
function checkCerpacNum(num){
	var result = /^[(J\-1)(DZXFLGC)]{1}[0-9]{14}$/.test(num);
	return result;
}
// 警官证检测
function checkPoliceNum(num){
	var result = /^[0-9]{6}$/.test(num);
	return result;
}


jQuery.validator.addMethod("comLegalMan", function(value, element) {
	var curNumName = '';
	var curTypeName =  '';
	var curTypeValue =  '';
	var oneedition =  $('#oneedition').val();
	if(oneedition==-1){
	}
	else {
		//自然人保证人校验
		launch.certNumCheck(curTypeValue,value,'comLegalMan'); 	
	//-=----------	
	}
	jQuery.validator.messages.comLegalMan = customError;
	return checkResult;
}, "必填信息");

jQuery.validator.addMethod("comLegalManNum", function(value, element) {
	var curNumName = '';
	var curTypeName =  '';
	var curTypeValue =  '';
	var oneedition =  $('#oneedition').val();
	if(oneedition==-1){
	}
	else {
		
		launch.certNumCheck(curTypeValue,value,'comLegalManNum'); 		
		
	//-=----------	
	}
	jQuery.validator.messages.comLegalManNum = customError;
	return checkResult;
}, "必填信息");
jQuery.validator.addMethod("comLegalManMoblie", function(value, element) {
	var curNumName = '';
	var curTypeName =  '';
	var curTypeValue =  '';
	var oneedition =  $('#oneedition').val();
	if(oneedition==-1){
	}
	else {
		launch.certNumCheck(curTypeValue,value,'comLegalManMoblie'); 	
	//-=----------	
	}
	jQuery.validator.messages.comLegalManMoblie = customError;
	return checkResult;
}, "必填信息");

jQuery.validator.addMethod("comLegalManComEmail", function(value, element) {
	var curNumName = '';
	var curTypeName =  '';
	var curTypeValue =  '';
	var oneedition =  $('#oneedition').val();
	if(oneedition==-1){
	}
	else {
		launch.certNumCheck(curTypeValue,value,'comLegalManComEmail'); 	
	//-=----------	
	}
	jQuery.validator.messages.comLegalManComEmail = customError;
	return checkResult;
}, "必填信息");

//法人身份证号 和共借人   配偶信息 关联
launch.certifacte = function(value,element){
	
     var isOK= true;
        isOK = checkIdcard(value)
     if(isOK==false){
    	top.$.jBox.tip('法人身份证号输入有误');
    	return false;
     }
// ---------------
   var  checkResult = true;
   var  customError = "";
   var  customerName ="";
   var iphoneOne ="";
   var     inputType ="egalperson";       	
      $.ajax({
    	  url:ctx+"/apply/dataEntry/certNumCheck",
    	  type:"post",
    	  data:{
    		  'inputType':inputType,
    		  'certNum':value,
    		  'loanCode':$('#loanCode').val(),
    		  'customerCode':$('#customerCode').val(),
    		  'consultId':$('#consultId').val()
    	  },
    	  dataType:'json',
    	  success:function(data){
    		   checkResult = data.result;
    		   customError = data.resultMsg;
    		   
    		   customerName=data.customerName;
    		   iphoneOne =data.iphoneOne;
    		if(checkResult==false){
    			$('#comLegalMan').val(customerName);
    			$('#comLegalMan').attr('readonly',true);
    			$('#comLegalManMoblie').val(iphoneOne);
    			$('#comLegalManMoblie').attr('readonly',true);
    			$('#comLegalManNum').attr('readonly',false);
    		}   
    	  },
    	  async:false
      });
//----------------        
	
};


//法人身份证号码校验
jQuery.validator.addMethod("coboCertCheckCopmany", function(value, element) {
	var isOK= true;
	isOK = checkIdcard(value);
    return isOK;
}, "证件号码输入有误");








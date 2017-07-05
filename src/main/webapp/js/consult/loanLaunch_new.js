var launch = {};
//新版申请表页签：个人基本信息，借款意愿，工作信息，联系人信息，自然人保证人信息，房产信息，经营信息，证件信息，银行卡信息
launch.viewName = new Array('_loanFlowCustomer_new','_loanFlowApplyInfo_new','_loanFlowCompany_new','_loanFlowContact_new','_loanFlowNaturalGuarantor_new','_loanFlowHouse_new','_loanFlowManager_new','_loanFlowCertificate_new','_loanFlowBank_new');
//门店复核页签
launch.viewName_storeView = new Array('loanFlowStoreReview_new','_loanFlowApplyInfo_new_storeView','_loanFlowCompanyStoreView','_loanFlowContactStoreView','_loanFlowNaturalGuarantorStoreView','_loanFlowHouseStoreView','_loanFlowManager_new_storeView','_loanFlowCertificateStoreView','_loanFlowBank_new_storeView');
var checkResult=true;
var customError = "";
//加载邮箱验证js
document.write('<script src="'+context+'/js/consult/emailvalidate.js"><\/script>');
$(document).ready(function(){
	
	var isBorrow=$("#isBorrow").val();
	//如果是借么的单子则按照借么APP的要求 字段或置灰或赋默认值或新增字段
	if(isBorrow == 1){
		/*个人基本信息页签*/
		isBorrowInit_loanFlowCustomer();
		/*借款意愿页签*/
		isBorrowInit_loanFlowApplyInfo();
		/*工作信息页签*/
		isBorrowInit_loanFlowCompany();
		/*自然人保证人页签*/
		isBorrowInit_loanFlowNaturalGuarantor();
		/*证件信息页签*/
		isBorrowInit_loanFlowCertificate();
	}
	updateEmailIfConfirm();
});

function isBorrowInit_loanFlowCustomer(){
	$("#customerChildrenCount").attr('disabled',true); //子女人数
	$("#customerFamilySupport").attr('disabled',true); //供养人数
	$("#firstArriveYear").attr('disabled',true); //初来本市时间
	$("#customerFirtLivingDay").attr('disabled',true); //现住宅起始居住日期
	$("input[name='loanCustomer.customerTel']").attr('disabled',true); //宅电
	$("select[name='loanCustomer.registerProperty']").attr('disabled',true); //户籍性质
	$("#dictCustomerDiff").val("1").trigger("change"); //申请人分类 授薪人群
	$("input[name='loanCustomer.dictCustomerSourceNewStr']").attr('disabled',true); //从何处了解到我公司
	$(".loanCustomer_issuingAuthority").css("display","block"); //主借人签发机关
}
function isBorrowInit_loanFlowApplyInfo(){
	$("#productId").val("A013").trigger("change"); //产品类别   信易借
	$("#dictLoanSource").attr('disabled',true); //主要还款来源
}
function isBorrowInit_loanFlowCompany(){
	$("#previousCompName").attr('disabled',true); //前单位名称
	$("#compUnitScale").attr('disabled',true); //员工数量
	$("#compPost").attr('disabled',true); //职务
}
function isBorrowInit_loanFlowNaturalGuarantor(){
	$("select[name^='loanCoborrower'][name$='dictEducation']").attr('disabled',true); //教育程度
	$("select[name^='loanCoborrower'][name$='dictMarry']").attr('disabled',true); //婚姻状况
	$("input[name^='loanCoborrower'][name$='childrenNum']").attr('disabled',true); //子女人数
	$("input[name^='loanCoborrower'][name$='supportNum']").attr('disabled',true); //供养人数
	$("input[name^='loanCoborrower'][name$='homeMonthIncome']").attr('disabled',true); //家庭月收入
	$("input[name^='loanCoborrower'][name$='homeMonthPay']").attr('disabled',true); //家庭月支出
	$("input[name^='loanCoborrower'][name$='homeTotalDebt']").attr('disabled',true); //家庭总负债
	$("input[name^='loanCoborrower'][name$='customerFirtArriveYear']").attr('disabled',true); //初来本市时间
	$("input[name^='loanCoborrower'][name$='customerFirstLivingDay']").attr('disabled',true); //现住宅起始居住日期
	$("select[name^='loanCoborrower'][name$='customerHouseHoldProperty']").attr('disabled',true); //住宅类别
	$("select[name^='loanCoborrower'][name$='registerProperty']").attr('disabled',true); //户籍性质
	$("input[name^='loanCoborrower'][name$='coboFamilyTel']").attr('disabled',true); //宅电
	$("input[name^='loanCoborrower'][name$='coboCompany.compTel']").attr('disabled',true); //单位电话
	$("input[name^='loanCoborrower'][name$='coboCompany.compTelExtension']").attr('disabled',true); //单位电话
	$("input[name^='loanCoborrower'][name$='coboCompany.compSalaryDay']").attr('disabled',true); //每月发薪日期
	$("select[name^='loanCoborrower'][name$='coboCompany.dictSalaryPay']").attr('disabled',true); //主要发薪方式
	$("select[name^='loanCoborrower'][name$='coboCompany.compProvince']").attr('disabled',true); //单位地址
	$("select[name^='loanCoborrower'][name$='coboCompany.compCity']").attr('disabled',true); //单位地址
	$("select[name^='loanCoborrower'][name$='coboCompany.compArer']").attr('disabled',true); //单位地址
	$("input[name^='loanCoborrower'][name$='coboCompany.compAddress']").attr('disabled',true); //单位地址
	$("input[name^='loanCoborrower'][name$='loanInfoCoborrower.loanApplyAmount']").attr('disabled',true); //申请额度
	$("select[name^='loanCoborrower'][name$='loanInfoCoborrower.productType']").val("A013").trigger("change"); //产品类别  信易借
	$("select[name^='loanCoborrower'][name$='loanInfoCoborrower.loanMonths']").attr('disabled',true); //申请期限 注意ajax的申请期限不在这设置
	$("select[name^='loanCoborrower'][name$='loanInfoCoborrower.borrowingPurposes']").attr('disabled',true); //主要借款用途
	$("select[name^='loanCoborrower'][name$='loanInfoCoborrower.mainPaybackResource']").attr('disabled',true); //主要还款来源
	$("input[name^='loanCoborrower'][name$='loanInfoCoborrower.highPaybackMonthMoney']").attr('disabled',true); //最高可承受月还
	$(".loanCoborrower_issuingAuthority").css("display","block"); //自然人保证人签发机关
}
function isBorrowInit_loanFlowCertificate(){
	$("#personalCertificate").attr('disabled',true); //申请人与户主关系
	$("#masterName").attr('disabled',true); //户主姓名
	$("#masterCertNum").attr('disabled',true); //身份证号码
	$("#masterAddressProvince").attr('disabled',true); //户主页地址
	$("#masterAddressCity").attr('disabled',true); //户主页地址
	$("#masterAddressArea").attr('disabled',true); //户主页地址
	$("#masterAddress").attr('disabled',true); //户主页地址
	$("#weddingTime").attr('disabled',true); //结婚日期
	$("#licenseIssuingAgency").attr('disabled',true); //发证机构
}

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
					$.ajax({
						url:ctx+"/borrow/borrowlist/asynCheckInput_new?loanCode="+loanCode,
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
											url:ctx+"/apply/dataEntry/delAdditionItem_new",
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
									var url = ctx + "/borrow/borrowlist/launchFlow_new?flag=" + num;
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
					form.action = ctx + "/apply/dataEntry/saveApplyInfo_new?"+"&flag=" + num;
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
/**门店复核提交*/
launch.storeViewSubmitFlow = function(form,btnId) {
	$('#'+btnId).attr('disabled',true);
	var loanCode = $("#loanCode").val();
	$.ajax({
		url:ctx+"/borrow/borrowlist/asynCheckInput_new?loanCode="+loanCode,
		type:'post',
		data:{},
		dataType:'json',
		success:function(data){
			if(!data.success){
				if(data.flag!='4'){
					art.dialog.alert(data.message);
					$('#'+btnId).removeAttr('disabled');
					return data.success;
				}else{
				    art.dialog.confirm(data.message,
					  function(){
				    	$.ajax({
							type:'post',
							url:ctx+"/apply/dataEntry/delAdditionItem_new",
							data:{
								'delType':data.itemType,
								'tagId':  data.tagId
							},
							dataType:'json',
							success:function(result){
								if(result){
									top.$.jBox.tip('删除成功');
									$('#'+btnId).removeAttr('disabled');
								}else{
									top.$.jBox.tip('删除失败');
									$('#'+btnId).removeAttr('disabled');
								}
							},
							error:function(){
								art.dialog.alert("服务器异常！");
							}
	  					});
		     		},function(){});
				}
			}else{
				$.jBox.tip("检查影像资料、个人信用体检报告费用，请稍候...", 'loading',{opacity:0.1,persistent:true});
				$.ajax({
					url:ctx+"/borrow/borrowlist/checkService?loanCode="+loanCode,
					type:'post',
					data:{},
					dataType:'json',
					success:function(dt){
						$.jBox.closeTip();
						if(!dt.success){
							art.dialog.alert(dt.message);
							$('#'+btnId).removeAttr('disabled');
						}else{
							art.dialog.confirm(data.message,
								function(){
									var url = ctx + "/borrow/borrowlist/dispatchFlow";
									$('#'+form).attr("action",url);
									$('#'+form).submit();
								},function(){
									$('#'+btnId).removeAttr('disabled');
								}
						    );
						}
					},
					async:true, 
					error:function(){
						$.jBox.closeTip();
						art.dialog.alert('服务器异常！');
						return false;
					}
				});
			}
		},
		async:false, 
		error:function(){
			art.dialog.alert('服务器异常！');
			return false;
		}
	});
}
/**此方法是新版申请表填写申请资料时调用*/
launch.getCustomerInfo = function(flag){
	var url="";
	if(flag=="1"){
	    url = ctx+"/bpm/flowController/openLaunchForm?dealType=1";
	    $('#custInfoForm').attr('action',url);
	    $('#custInfoForm').submit();
	}else{
		url = ctx+"/apply/dataEntry/getApplyInfo_new?flag="+flag+"&viewName="+launch.viewName[parseInt(flag)-1];
		$('#custInfoForm').attr('action',url);
	    $('#custInfoForm').submit();
	}
};
/**此方法门店复核时调用*/
launch.getCustomerInfo_storeView = function(flag){
	var url="";
	var teleFlag=$("#teleFlag").val();
	if(flag=="1"){
	    url = ctx+"/bpm/flowController/openForm?loanInfoOldOrNewFlag=1&teleFlag="+teleFlag;
	    $('#custInfoForm').attr('action',url);
	    $('#custInfoForm').submit();
	}else{
		url = ctx+"/apply/dataEntry/getApplyInfo_new_storeView?flag="+flag+"&viewName="+launch.viewName_storeView[parseInt(flag)-1]+"&teleFlag="+teleFlag;
		$('#custInfoForm').attr('action',url);
	    $('#custInfoForm').submit();
	}
};
launch.checkInput = function(num, bankForm,btnId){
	var loanCode = $("#loanCode").val();
	$.ajax({
		url:ctx+"/borrow/borrowlist/asynCheckInput_new?loanCode="+loanCode,
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
							url:ctx+"/apply/dataEntry/delAdditionItem_new",
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
	+ "&currIndex=" + currIndex+"&oneedition="+$('#oneedition').val()+"&isBorrow="+$("#isBorrow").val();
	
	if(param!=null && param!=''){
		url +="&"+param; 
	}
	
	$.ajax({
		type : "POST",
		url  : url,
		dataType : 'html',
		success : function(data) {
			if(tagetId=="contactArea" || tagetId.indexOf("table_")!=-1 || tagetId=="loanCreditArea"){
				$('#' + tagetId+" tbody").append(data);
			}else{
				$('#' + tagetId).append(data);
			}
			//添加自然人保证人
			if(tagetId.indexOf("contactPanel") != -1){
				//初始化自然人保证人 产品类别
				var productTypeValues = $("#productType_"+currIndex).html();
				var newProductTypeSelect = $("#productType_" + parentIndex);
				newProductTypeSelect.append(productTypeValues);
				newProductTypeSelect.val("");
				
				var page = $("#page").val();
				//自然人保证人初始化
				coborrower.naturalGuarantorInit(page);
				//如果是借么的单子则按照借么APP的要求 字段或置灰或赋默认值或新增字段
				var isBorrow=$("#isBorrow").val();
				if(isBorrow == 1){
					isBorrowInit_loanFlowNaturalGuarantor();
				}
			}
			
			//房产信息初始化
			if(tagetId.indexOf("loanHouseArea") != -1){
				house.init();
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
//联系人本页名字重复校验
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
//中文校验
jQuery.validator.addMethod("chineseCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[\u4e00-\u9fa5+]{2,30}$/; 
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能输入中文");
//中文校验
jQuery.validator.addMethod("chineseCheck2", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[\u4e00-\u9fa5+]{1,500}$/; 
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能输入中文");
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
//英文数字校验
jQuery.validator.addMethod("stringNum", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[0-9a-zA-Z]{1,100}$/; 
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能为字母、数字的组合");
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
	var isOK = launch.limitCheck(value, 'limitLower', 'limitUpper');
	return isOK;
}, "申请额度不在有效的额度限制范围内");
jQuery.validator.addMethod("limitCheckCobo", function(value, element) {
	
	var limitLowerId = $(element).siblings("input[id^='limitLower']").prop("id");
	var limitUpperId = $(element).siblings("input[id^='limitUpper']").prop("id");
	var isOK = launch.limitCheck(value, limitLowerId, limitUpperId);
	
	return isOK;
}, "申请额度不在有效的额度限制范围内");

launch.limitCheck = function(value, limitLowerId, limitUpperId){
	var isOK = true;
	if(value!=''){
		var limitLower = $('#'+limitLowerId).val();
		var limitUpper = $('#'+limitUpperId).val();
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
	return isOK
}

// 公司名称中英文校验
jQuery.validator.addMethod("compNameCheck", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[a-zA-Z0-9\u4e00-\u9fa5()（） *]{1,50}$/;
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能输入中文、英文、空格跟括号，且总长度不能超过20位");
//社保卡校验
jQuery.validator.addMethod("isSocial", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[0-9]*$/;
		var regex1=/[0-9]+/;
		var regex2=/[A-Za-z]+/;
		var regex3=/[^0-9A-Za-z]/;
		isOK = regex.test(value) || (regex1.test(value) && regex2.test(value) && !(regex3.test(value)));
    }
	return this.optional(element) || (isOK);
}, "只能为数字，或者数字与字母的组合");
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
//一位小数校验
jQuery.validator.addMethod("numCheck2", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex1=/^\d{1,10}\.\d{1}$/;
		var regex2=/^\d{1,10}$/;
		isOK = regex1.test(value)||regex2.test(value);
    }
	return isOK;
}, "数值不合法");
//非负整数
jQuery.validator.addMethod("positiveInteger", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex=/^[\+]?\d+(?:\.d+)?$/;
		isOK = regex.test(value);
    }
	return isOK;
}, "数值不合法");
//日期校验
jQuery.validator.addMethod("day", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex=/^[\+]?\d+(?:\.d+)?$/;
		isOK = regex.test(value);
    }
	return isOK && value > 0 && value < 32;
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
//QQ校验
jQuery.validator.addMethod("isQQ", function(value, element) {
	var regex=/^[1-9]\d{4,19}$/;
    var result=true;
 	if(value!=null && value!=''){
 		 if(!regex.test(value)){
 			result = false;
         }
	}
    return this.optional(element) || (result);
}, "请输入正确的QQ号");
// 证件号码检测（配偶）
jQuery.validator.addMethod("isCertificate", function(value, element) {
	var isOK= true;
	var cardType = $("#mateCardType option:selected").val();
	var cardTypeName= $("#mateCardType option:selected").text();
	
	if(cardTypeName == "身份证" || cardTypeName == "临时身份证"){ // 身份证检测
			isOK = checkIdcard(value);
	}
//	else if(cardTypeName=="户口薄"){ // 户口检测
//		    isOK = checkNodeIdNum(value);
//	}else if(cardTypeName=="军官证" || cardTypeName=="士兵证"){ // 军官证检测
//		    isOK = checkOfficersNum(value);
//	}else if(cardTypeName=="护照"){ // 护照检测
//		    isOK = checkPassport(value);
//	}else if(cardTypeName=="港澳居民来往内地通行证" || cardTypeName=="台湾同胞来往内地通行证"){ // 港澳通行证检测
//		    isOK = checkGatepassNum(value);
//	}else if(cardTypeName=="外国人居留证"){ // 外国人居留证
//		  //  isOK = checkCerpacNum(value);
//	}else if(cardTypeName=="警官证"){
//		    isOK = checkPoliceNum(value);
//	}
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

//手机号码和宅电必须填一个（联系人亲属）
jQuery.validator.addMethod("mobileAndTelNeedOne", function(value, element) {
	var isOK= false;
	$(element).parents("tr").find(".mobileAndTelNeedOne").each(function(){
		if($(this).val() != null && $(this).val() != '' && $(this).val() != undefined){
			isOK = true;
		}
	});
    return isOK;
},"手机号码和宅电必须填一个");

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
	var cardType = $("input[name='"+otherTargName+"']").val();
	var cardTypeName = cardType == '0' ? '身份证' : '';
	if(cardType != '0'){
		cardType = $("select[name='"+otherTargName+"'] option:selected").val();
		cardTypeName= $("select[name='"+otherTargName+"'] option:selected").text();
	}
    
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
	   
	   //var curTypeValue = $("select[name='"+curTypeName+"']")[0].value;
	   var curTypeValue = $("input[name='"+curTypeName+"']")[0].value;
	   //原来是select 
	   //var typeArray = $("select[name$='.dictCertType']");
	   //现在改为隐藏input
	   var typeArray = $("input[name$='.dictCertType']");
	   
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

//联系人证件号码查重
jQuery.validator.addMethod("currentPageDuplicateCertNumCheck", function(value, element) {
	   var isOK= true;
	   var curNumName = element.name;
	   var numArray = $(".currentPageDuplicateCertNumCheck");
	   var arrayLength = numArray.length;
	   if(arrayLength>1){
		   for(var i = 0;i<arrayLength;i++){
			   var indexCertNumValue = numArray[i].value;
			   var indexCertNumName = numArray[i].name;
			   if(indexCertNumName!=curNumName && value != '' && indexCertNumValue != '' && value==indexCertNumValue){
				   isOK = false;
				   break;
			   }
		   }
	   }
 return isOK;
}, "证件号码重复");

// 共借人证件号码远程查重 launch.certNumCheck = function(certType,certNum,inputType)
jQuery.validator.addMethod("coboCertNumCheck2", function(value, element) {
	var curNumName = element.name;
	var curTypeName = curNumName.split(".")[0]+".dictCertType";
	//var curTypeValue = $("select[name='"+curTypeName+"']")[0].value;
	var curTypeValue = $("input[name='"+curTypeName+"']")[0].value;
	launch.certNumCheck(curTypeValue,value,'coboBorrower');
	jQuery.validator.messages.coboCertNumCheck2 = customError;
	return checkResult;
}, "证件号码重复");
// 配偶证件号码查重
jQuery.validator.addMethod("mateNumCheck", function(value, element) {
  var curTypeValue = "0"; 
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
		var certTypeValue = $("input[name='"+certTypeName+"']")[0].value;
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
	  url:ctx+"/apply/dataEntry/phoneNumCheckNew",
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
	  url:ctx+"/apply/dataEntry/certNumCheck_new",
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


/**此方法是新版申请填写申请资料时调用*/
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
			var customerProductType = $("#customerProductType").val();
			if(num==1 && customerProductType != 'A021'){
				var sendEmailFlag = $("#sendEmailFlag").val();
				var email = $("input[name='loanCustomer.customerEmail']").val();
				var oldEmailFlag = $("#oldEmailFlag").val();
				//如果填写邮箱不是之前保存过的邮箱，并且不是验证过的邮箱则进行验证提示
				if(sendEmailFlag!=email && email!=oldEmailFlag){
					top.$.jBox.tip('请进行邮箱验证处理！');
					return;
				}
				$.ajax({
					  url:ctx+"/borrow/borrowlist/checkIfEmailConfirm",
					  type:"post",
					  data:{'customerCode':$("#bvCustomerCode").val(),'loanCode':$("#loanCode").val(),'customerEmail':email},
					  async:false,
					  dataType:'json',
					  success:function(data){
						  if(data=='1'){
							  top.$.jBox.tip('正在保存数据...');
						      $('#'+btnId).attr('disabled',true);
							  var url = ctx + "/apply/dataEntry/saveApplyInfo_new?" + "flag=" + num;
							  form.action = url;
							  form.submit();
						  }else{
							  top.$.jBox.tip('请进行邮箱验证处理！');
						  }
					  },error:function(data){
						  top.$.jBox.tip('业务处理错误');
					  }
			    });
			}else{
				top.$.jBox.tip('正在保存数据...');
			      $('#'+btnId).attr('disabled',true);
				  var url = ctx + "/apply/dataEntry/saveApplyInfo_new?" + "flag=" + num;
				  form.action = url;
				  form.submit();
			}
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
/**此方法是门店复核时调用*/
launch.updateApplyInfo =function(num,busiForm,btnId) {
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
			var customerProductType = $("#customerProductType").val();
			if(num==1 && customerProductType != 'A021'){
				var sendEmailFlag = $("#sendEmailFlag").val();
				var email = $("input[name='loanCustomer.customerEmail']").val();
				var oldEmailFlag = $("#oldEmailFlag").val();
				//如果填写邮箱不是之前保存过的邮箱，并且不是验证过的邮箱则进行验证提示
				if(sendEmailFlag!=email && email!=oldEmailFlag){
					top.$.jBox.tip('请进行邮箱验证处理！');
					return;
				}
				$.ajax({
					  url:ctx+"/borrow/borrowlist/checkIfEmailConfirm",
					  type:"post",
					  data:{'customerCode':$("#bvCustomerCode").val(),'loanCode':$("#loanCode").val(),'customerEmail':email},
					  async:false,
					  dataType:'json',
					  success:function(data){
						  if(data=='1'){
							  top.$.jBox.tip('正在保存数据...');
							  $('#'+btnId).attr('disabled',true);
							  var url = ctx + "/apply/storeReviewController/storeReviewUpdate_new?" + "flag=" + num+"&viewName="+launch.viewName_storeView[parseInt(num)-1];
							  form.action = url;
							  form.submit();
						  }else{
							  top.$.jBox.tip('请进行邮箱验证处理！');
						  }
					  },error:function(data){
						  top.$.jBox.tip('业务处理错误');
					  }
			    });
			}else{
				top.$.jBox.tip('正在保存数据...');
				$('#'+btnId).attr('disabled',true);
				var url = ctx + "/apply/storeReviewController/storeReviewUpdate_new?" + "flag=" + num+"&viewName="+launch.viewName_storeView[parseInt(num)-1];
				form.action = url;
				form.submit();
			}

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
	launch.certNumCheck(curTypeValue,value,'comLegalMan'); 	
	jQuery.validator.messages.comLegalMan = customError;
	return checkResult;
}, "必填信息");

jQuery.validator.addMethod("comLegalManNum", function(value, element) {
	var curNumName = '';
	var curTypeName =  '';
	var curTypeValue =  '';
	launch.certNumCheck(curTypeValue,value,'comLegalManNum'); 		
	jQuery.validator.messages.comLegalManNum = customError;
	return checkResult;
}, "必填信息");
jQuery.validator.addMethod("comLegalManMoblie", function(value, element) {
	var curNumName = '';
	var curTypeName =  '';
	var curTypeValue =  '';
	launch.certNumCheck(curTypeValue,value,'comLegalManMoblie'); 	
	jQuery.validator.messages.comLegalManMoblie = customError;
	return checkResult;
}, "必填信息");

jQuery.validator.addMethod("comLegalManComEmail", function(value, element) {
	var curNumName = '';
	var curTypeName =  '';
	var curTypeValue =  '';
	launch.certNumCheck(curTypeValue,value,'comLegalManComEmail'); 	
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
	isOK = checkIdcardNoAlert(value);
    return isOK || this.optional(element);
}, "证件号码输入有误");

//学历证书编号验证
jQuery.validator.addMethod("certificateNum", function(value, element) {
    var plateNumber = /^[0-9a-zA-Z]*$/g;
    return this.optional(element) || (plateNumber.test(value));
}, "学历证书编号输入有误");

//长度限制20
jQuery.validator.addMethod("maxLength20", function(value, element) {
    
	if(value != undefined && value != '' && value != null){
		return value.length < 21;
	}
	
    return true;
}, "最大字符不超过20");

//长度限制50
jQuery.validator.addMethod("maxLength50", function(value, element) {

	if(value != undefined && value != '' && value != null){
		return value.length < 51;
	}
	
	return true;
}, "最大字符不超过50");

//长度限制100
jQuery.validator.addMethod("maxLength100", function(value, element) {
	
	if(value != undefined && value != '' && value != null){
		return value.length < 101;
	}
	
	return true;
}, "最大字符不超过100");
//最大值校验
jQuery.validator.addMethod("max50", function(value, element) {
	
	if(value != undefined && value != '' && value != null){
		return value < 51;
	}
	
	return true;
}, "最大值不能超过50");

//子女姓名和身份证号若只填一个，则另一个也需要填写
jQuery.validator.addMethod("childrenNameNotEmptyAndCertNumEmpty", function(value, element) {
	
	var childrenName = $("#childrenName").val();
	var childrenCertNum = $("#childrenCertNum").val();
	if(childrenName != '' && childrenCertNum == ''){
		return false;
	}
	
	return true;
}, "必填信息");
//子女姓名和身份证号若只填一个，则另一个也需要填写
jQuery.validator.addMethod("childrenNameEmptyAndCertNumNotEmpty", function(value, element) {
	
	var childrenName = $("#childrenName").val();
	var childrenCertNum = $("#childrenCertNum").val();
	
	if(childrenName == '' &&  childrenCertNum != ''){
		return false;
	}
	return true;
}, "必填信息");
//同业在还借款总笔数和月还款总额 若一个为空或0则两一个也为空或0
jQuery.validator.addMethod("otherCompanyCompare", function(value, element) {
	
	var otherCompanyPaybackCount = $("#otherCompanyPaybackCount").val();
	var otherCompanyPaybackTotalmoney = $("#otherCompanyPaybackTotalmoney").val();
	
	if(((otherCompanyPaybackCount == '' || otherCompanyPaybackCount == 0) &&  (otherCompanyPaybackTotalmoney != '' && otherCompanyPaybackTotalmoney > 0)) 
			||	((otherCompanyPaybackCount != '' && otherCompanyPaybackCount > 0) &&  (otherCompanyPaybackTotalmoney == '' || otherCompanyPaybackTotalmoney == 0))){
		return false;
	}
	return true;
}, "请检查同业在还借款总笔数和月还款总额");

//法人身份证号 和主借人   配偶信息 关联
launch.corporateCertNum = function(value){
    var curtNum="";
    var customerName ="";
    var iphoneOne ="";
    var curCertNum="";
    var mateName="";
    var mateTel="";
   	$.ajax({
		  url:ctx+"/apply/dataEntry/corporateCertNumCheck",
		  type:"post",
		  data:{'corporateCertNum':value,'loanCode':$('#loanCode').val()},
		  async:false,
		  dataType:'json',
		  success:function(data){
			   if(null!=data.loanCustomer){
	    		   //主借人
	    		   curtNum=data.loanCustomer.customerCertNum;
	    		   customerName=data.loanCustomer.customerName;
	    		   iphoneOne =data.loanCustomer.customerPhoneFirst;
			   }
			   if(null!=data.loanMate){
	    		   //配偶
	    		   curCertNum=data.loanMate.mateCertNum;
	    		   mateName=data.loanMate.mateName;
	    		   mateTel=data.loanMate.mateTel;
			   }
			   if(value==curtNum){
	    			$('#corporateRepresent').val(customerName);
	    			$('#corporateRepresentMobile').val(iphoneOne);
	    			$('#corporateRepresent').attr('readonly',true);
	    			$('#corporateRepresentMobile').attr('readonly',true);
			   }else if(value==curCertNum){
	    			$('#corporateRepresent').val(mateName);
	    			$('#corporateRepresentMobile').val(mateTel);
	    			$('#corporateRepresent').attr('readonly',true);
	    			$('#corporateRepresentMobile').attr('readonly',true);
			   }else{
				   $('#corporateRepresent').attr('readonly',false);
				   $('#corporateRepresentMobile').attr('readonly',false);
			   }
		}
   });
};
//信用代码和组织机构不能同时为空
jQuery.validator.addMethod("creditCodeAndOrgCodeNotEmpty", function(value, element) {
	var creditCode=$("#creditCode").val();
	var orgCode=$("#orgCode").val();
	if (creditCode == '' && orgCode == '') {
		return false;
	}
	return true;
}, "信用代码和组织机构不能同时为空");
//长度为18
jQuery.validator.addMethod("Length18", function(value, element) {
    
	if(value.length ==18){
		return true;
	}
    return false;
}, "长度只能为18位");
//长度为18
jQuery.validator.addMethod("Length10", function(value, element) {
    
	if(value.length ==10){
		return true;
	}
    return false;
}, "长度只能为10位");
//只能填写数字和大小写字母组合校验
jQuery.validator.addMethod("stringNumber", function(value, element) {
	var isOK = true;
	if(value!=''){
		//var regex=/(?!^\d+$)(?!^[a-zA-Z]+$)[0-9a-zA-Z]{18}/; 
		var regex=/^[0-9a-zA-Z]{18}$/; 
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "只能为数字、大小写字母");
//前8位字符，包括大小写字母或者0到9的数字字符      9，10位字符是  中划线 后面跟1位大小写字母或数字
jQuery.validator.addMethod("stringNumberOther", function(value, element) {
	var isOK = true;
	if(value!=''){
		var regex= /^[0-9a-zA-Z]{8}-[0-9a-zA-Z]{1}$/; 
		isOK = regex.test(value);
    }
	return this.optional(element) || (isOK);
}, "输入格式错误(如：abCdefg1-a)");
/*
 * 银行卡信息页签银行户名校验：
 * 		不勾选生僻字即借款人姓名与银行户名应保持一致，如果不一致提交时将弹出错误提示框，提示内容为“借款人姓名与银行户名不一致，请修改”。
 */
jQuery.validator.addMethod("bankAccountNameCheckOne", function(value, element) {
	var bankAccountName=$("#bankAccountName").val();
	var loanCustomer_customerName=$("#loanCustomer_customerName").val();
	
	var bankIsRareword= $("input[id='bankIsRareword']:checked").val();
	if(!bankIsRareword){
		if(bankAccountName!=loanCustomer_customerName){
			return false;
		}
	}
	return true;
}, "借款人姓名与银行户名不一致，请修改");
/*
 * 银行卡信息页签银行户名校验：
 * 		勾选上生僻字即借款人姓名与银行户名应不一致，如果一致提交时将弹出错误提示框，提示内容为“为生僻字，借款人姓名与银行户名一致，请修改”。
 */
jQuery.validator.addMethod("bankAccountNameCheckTwo", function(value, element) {
	var bankAccountName=$("#bankAccountName").val();
	var loanCustomer_customerName=$("#loanCustomer_customerName").val();
	
	var bankIsRareword= $("input[id='bankIsRareword']:checked").val();
	if(bankIsRareword && bankIsRareword==1){
		if(bankAccountName==loanCustomer_customerName){
			return false;
		}
	}
	return true;
}, "为生僻字，借款人姓名与银行户名一致，请修改");



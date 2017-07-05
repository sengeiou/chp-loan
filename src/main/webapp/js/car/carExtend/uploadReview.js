//保存---展期复核联系人业务数据
$(document).ready(function() {
	
	    $("#contactSave").click(function(){
	    	if (!checkForm($("#qnm"))) {
	    		return false;
	    	}
	    	var flag = $("#qnm").validate({
	    		onclick: true, 
	    		rules:{
	    			customerCertNum:{
	    				card:true,
	    			}
	    		},
	    		messages : {
	    			contactName:{
	    				realName:"姓名只能为2-30个汉字"
	    			},
	    		}
	    	}).form();
	    	if (flag) {
		    	 var phoneArr = [];
			   	 var telepnone=[];//单位电话
				 var phone = $("input[mark='contactUnitTel']").each(function(){
					 if($(this).val() != '' && $(this).val() != null){
						phoneArr.push($(this).val());//验证手机号码是否重复
					 }
				 });
				 var phoneSort = phoneArr.sort();
				 for(var i = 0; i< phoneSort.length - 1; i ++){
					 if(phoneSort[i] == phoneSort[i+1]){
						 alert("您输入的手机号码："+phoneSort[i]+" 重复了，请重新输入！" );
						 return false;
					 }
				 }
				 $("input[mark='compTel']").each(function(){
					 if($(this).val() != '' && $(this).val() != null){
						 telepnone.push($(this).val());//验证单位电话是否重复
					 }
				 });
				 var telephoneSort = telepnone.sort();
				 for(var i = 0; i< telephoneSort.length - 1; i ++){
					 if(telephoneSort[i] == telephoneSort[i+1]){
						 alert("您输入的单位电话："+telephoneSort[i]+" 重复了，请重新输入！" );
						 return false;
					 }
				 }
				 var tabLength=$('#contactArea tbody>tr').length;
				 if(tabLength<3){
					 art.dialog.alert("联系人至少要3个");
					 return false;
				 }
		    	var param = makeData($(this));
		    	var loanCodeParam = $("#loanCodeParam").serialize();
		    	$.ajax({
		    		url:ctx+"/car/carExtend/carExtendUpload/contactSave",
		    		type:'post',
		    		data: param + "&" + loanCodeParam + "&delArray=" + delArray,
		    		dataType:'text',
		    		success:function(data){
		    			 $.jBox.tip(data);
		    		},
		    		error:function(){
		    			art.dialog.alert('服务器异常！');
		    			return false;
		    		}
		    	});
	    	}
	    });
  //保存共借人业务数据
    $("#coborrowerSave").click(function(){
    	var paramForm = "";
    	var divs = $("#coborroArea .contactPanel");
    	var myLoanCode = $("#myLoanCode").val();
    	for (var i = 0; i < divs.length; i++) {
    		var divdiv = divs[i];
    		var tables = $(divdiv).find("table.coborrowerTable");
			var coboName = $(tables[0]).find("input[mark='coboName']")[0].value;
			var dictCertType = $(tables[0]).find("select[mark='dictCertType']")[0].value;
			var certNum = $(tables[0]).find("input[mark='certNum']")[0].value;
			var dictSex = $(tables[0]).find("input[mark='dictSex']")[0].value;
			var mobile = $(tables[0]).find("input[mark='mobile']")[0].value;
			var dictMarryStatus = $(tables[0]).find("select[mark='dictMarryStatus']")[0].value;
			var haveChildFlag = $(tables[0]).find("input[mark='haveChildFlag']")[0].value;
			var familyTel = $(tables[0]).find("input[mark='familyTel']")[0].value;
			var otherIncome = $(tables[0]).find("input[mark='otherIncome']")[0].value;
			var houseRent = $(tables[0]).find("input[mark='houseRent']")[0].value;
			var dictHouseholdProvince = $(tables[0]).find("select[mark='dictHouseholdProvince']")[0].value;
			var dictHouseholdCity = $(tables[0]).find("select[mark='dictHouseholdCity']")[0].value;
			var dictHouseholdArea = $(tables[0]).find("select[mark='dictHouseholdArea']")[0].value;
			var householdAddress = $(tables[0]).find("input[mark='householdAddress']")[0].value;
			var dictLiveProvince = $(tables[0]).find("select[mark='dictLiveProvince']")[0].value;
			var dictLiveCity = $(tables[0]).find("select[mark='dictLiveCity']")[0].value;
			var dictLiveArea = $(tables[0]).find("select[mark='dictLiveArea']")[0].value;
			var nowAddress = $(tables[0]).find("input[mark='nowAddress']")[0].value;
			var loanCode = $(tables[0]).find("input[mark='loanCode']")[0].value;
			var companyName = $(tables[0]).find("input[mark='companyName']")[0].value;
			var companyAddress = $(tables[0]).find("input[mark='companyAddress']")[0].value;
			var companyPosition = $(tables[0]).find("input[mark='companyPosition']")[0].value;
			
			var id = $(tables[0]).find("input[mark='id']")[0].value;
				
			paramForm += '&carLoanCoborrower['+i+'].coboName=' + coboName;
			paramForm += '&carLoanCoborrower['+i+'].dictCertType=' + dictCertType;
			paramForm += '&carLoanCoborrower['+i+'].certNum=' + certNum;
			paramForm += '&carLoanCoborrower['+i+'].dictSex=' + dictSex;
			paramForm += '&carLoanCoborrower['+i+'].mobile=' + mobile;
			paramForm += '&carLoanCoborrower['+i+'].dictMarryStatus=' + dictMarryStatus;
			paramForm += '&carLoanCoborrower['+i+'].haveChildFlag=' + haveChildFlag;
			paramForm += '&carLoanCoborrower['+i+'].familyTel=' + familyTel;
			paramForm += '&carLoanCoborrower['+i+'].otherIncome=' + otherIncome;
			paramForm += '&carLoanCoborrower['+i+'].houseRent=' + houseRent;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdProvince=' + dictHouseholdProvince;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdCity=' + dictHouseholdCity;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdArea=' + dictHouseholdArea;
			paramForm += '&carLoanCoborrower['+i+'].householdAddress=' + householdAddress;
			paramForm += '&carLoanCoborrower['+i+'].dictLiveProvince=' + dictLiveProvince;
			paramForm += '&carLoanCoborrower['+i+'].dictLiveCity=' + dictLiveCity;
			paramForm += '&carLoanCoborrower['+i+'].dictLiveArea=' + dictLiveArea;
			paramForm += '&carLoanCoborrower['+i+'].nowAddress=' + nowAddress;
			paramForm += '&carLoanCoborrower['+i+'].loanCode=' + loanCode;
			paramForm += '&carLoanCoborrower['+i+'].companyName=' + companyName;
			paramForm += '&carLoanCoborrower['+i+'].companyAddress=' + companyAddress;
			paramForm += '&carLoanCoborrower['+i+'].companyPosition=' + companyPosition;
			paramForm += '&carLoanCoborrower['+i+'].id=' + id;
			
			var contactNames = $(tables[1]).find("input[mark='contactName']");
			var dictContactRelations = $(tables[1]).find("select[mark='dictContactRelation']");
			var contactUints = $(tables[1]).find("input[mark='contactUint']");
			var dictContactNowAddress = $(tables[1]).find("input[mark='dictContactNowAddress']");
			var compTels = $(tables[1]).find("input[mark='compTel']");
			var contactUnitTels = $(tables[1]).find("input[mark='contactUnitTel']");
			var loanCustomterTypes = $(tables[1]).find("input[mark='loanCustomterType']");
			//var contactId = $(tables[1]).find("input[mark='id']");
			for (j = 0; j < contactNames.length; j++) {
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactName=' + contactNames[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].dictContactRelation=' + dictContactRelations[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactUint=' + contactUints[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].dictContactNowAddress=' + dictContactNowAddress[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].compTel=' + compTels[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactUnitTel=' + contactUnitTels[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].loanCustomterType=' + loanCustomterTypes[j].value;
				//paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].id=' + contactId[j].value;
			}

    	}
		if (!checkForm($(this).parents("form"))) {
			return false;
		}
    	var workItemForm = $("#workItemForm").serialize();
    	$.ajax({
    		url:ctx+"/car/carExtend/carExtendUpload/coborrowerSave",
    		type:'post',
    		async: false,
    		data: paramForm + "&" + workItemForm+ "&delArray=" + delArray+ "&myLoanCode=" + myLoanCode,
    		dataType:'text',
    		success:function(data){
    			if(data == "已保存"){
    				art.dialog.confirm("已保存");
    				window.location.href = ctx+"/car/carExtend/carExtendUpload/toCarLoanFlowBank?loanCode=" + $("#ipt_loanCode").val();
    			}else{
    				art.dialog.confirm("失败");
    			}
    		},
    		error:function(data){
    			art.dialog.alert(data);
    			return false;
    			}
    		});
    });
});

function contactRecheckSave(){
	 var phoneArr = [];
  	 var telepnone=[];//单位电话
	 var phone = $("input[mark='contactUnitTel']").each(function(){
		 if($(this).val() != '' && $(this).val() != null){
			phoneArr.push($(this).val());//验证手机号码是否重复
		 }
	 });
	 var phoneSort = phoneArr.sort();
	 for(var i = 0; i< phoneSort.length - 1; i ++){
		 if(phoneSort[i] == phoneSort[i+1]){
			 alert("您输入的手机号码："+phoneSort[i]+" 重复了，请重新输入！" );
			 return false;
		 }
	 }
	 $("input[mark='compTel']").each(function(){
		 if($(this).val() != '' && $(this).val() != null){
			 telepnone.push($(this).val());//验证单位电话是否重复
		 }
	 });
	 var telephoneSort = telepnone.sort();
	 for(var i = 0; i< telephoneSort.length - 1; i ++){
		 if(telephoneSort[i] == telephoneSort[i+1]){
			 alert("您输入的单位电话："+telephoneSort[i]+" 重复了，请重新输入！" );
			 return false;
		 }
	 }
	 var tabLength=$('#contactArea tbody>tr').length;
	 if(tabLength<3){
		 art.dialog.alert("联系人至少要3个");
		 return false;
	 }
	var param = makeData($("#contactSave"));
	var loanCodeParam = $("#loanCodeParam").serialize();
	$.ajax({
		url:ctx+"/car/carExtend/carExtendUpload/contactSave",
		type:'post',
		data: param + "&" + loanCodeParam + "&delArray=" + delArray,
		dataType:'text',
		success:function(data){
			 $.jBox.tip(data);
		},
		error:function(){
			art.dialog.alert('服务器异常！');
			return false;
		}
	});
}

//生成联系人信息相关参数
function makeData(t){
	var form = $(t).parents("form");
	var contactN = form.find("input[mark='contactName']");
	var dictContactR = form.find("select[mark='dictContactRelation']");
	var contactU = form.find("input[mark='contactUint']");
	var NowAddressO = form.find("input[mark='dictContactNowAddress']");
	var compT = form.find("input[mark='compTel']");
	var contactUT = form.find("input[mark='contactUnitTel']");
	var loanC = form.find("input[mark='loanCode']");
	var loanCustomter = form.find("input[mark='loanCustomterType']");
	var id = form.find("input[mark='id']");
	var param = "";
	for(var i = 0; i < contactN.length; i++){
		param += '&carCustomerContactPerson['+i+'].contactName=' + encodeURI($(contactN[i]).val());
		param += '&carCustomerContactPerson['+i+'].dictContactRelation=' + $(dictContactR[i]).val();
		param += '&carCustomerContactPerson['+i+'].contactUint=' + encodeURI($(contactU[i]).val());
		param += '&carCustomerContactPerson['+i+'].dictContactNowAddress=' + encodeURI($(NowAddressO[i]).val());
		param += '&carCustomerContactPerson['+i+'].compTel=' + $(compT[i]).val();
		param += '&carCustomerContactPerson['+i+'].contactUnitTel=' + $(contactUT[i]).val();
		param += '&carCustomerContactPerson['+i+'].loanCode=' + $(loanC[i]).val();
		param += '&carCustomerContactPerson['+i+'].loanCustomterType=' + $(loanCustomter[i]).val();
		param += '&carCustomerContactPerson['+i+'].id=' + $(id[i]).val();
	}
	return param;
};

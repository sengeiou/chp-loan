var count = 10;
var contactNum = 3;
var delArray = [];
$(function(){
	
    //月租金显示与隐藏（）
    $("select[name='coboHouseHoldProperty']").change(function() {
        if($("select[name='coboHouseHoldProperty']").val() == '2' ||
                $("select[name='coboHouseHoldProperty']").val() == '0'){
            $(this).parent().children("span").children("label")[0].innerHTML="月供金额：";
            $(this).parent().children("span").children("input").addClass("required");
            $(this).parent().children("span").show();
        }else if($("select[name='coboHouseHoldProperty']").val() == '4'){
            $(this).parent().children("span").children("label")[0].innerHTML="月租金：";
            $(this).parent().children("span").children("input").addClass("required");
            $(this).parent().children("span").show();
        } else {
            $(this).parent().children("span").hide();
            $(this).parent().find("input[name='houseRent']").val("");
            $(this).parent().children("span").children("input").removeClass("required");
            }
    });
    $("input[name='coboHouseHoldProperty']").trigger("change");
    
    $(".contactPanel").each(function(i, element) {
        // 月租金收入显示与隐藏
        $(this).find("select[name='coboHouseHoldProperty" + i + "']").bind('change', function() {
            var haveOtherIncome = $("select[name='coboHouseHoldProperty" + i + "']").val();
            if(haveOtherIncome){
                if(haveOtherIncome == '0'|| haveOtherIncome == '2'){
                    $(this).parent().children("span").children("label")[0].innerHTML="月供金额：";
                    $(this).parent().children("span").children("input").addClass("required");
                    $(this).parent().children("span").show();
                }else if(haveOtherIncome == '4'){
                    $(this).parent().children("span").children("label")[0].innerHTML="月租金：";
                    $(this).parent().children("span").children("input").addClass("required");
                    $(this).parent().children("span").show();
                } else {
                    $(this).parent().children("span").hide();
                    $(this).parent().find("input[name='houseRent" + i + "']").val("");
                    $(this).parent().children("span").children("input").removeClass("required");
                    }
            }
        });
        $(this).find("select[name='coboHouseHoldProperty" + i + "']").trigger('change');
    });
	
	var coboCount = parseInt($("#coboCount").val());
	for (var i = 0; i < coboCount; i++) {
		loan.initCity("liveProvinceId"+i, "liveCityId"+i,"liveDistrictId"+i);
		loan.initCity("registerProvince"+i,"customerRegisterCity"+i, "customerRegisterArea"+i);
		
		areaselectCard.initCity("registerProvince" + i, "customerRegisterCity" + i, "customerRegisterArea" + i, 
				$("#houseCityId" + i).val(), $("#houseAreaId" + i).val());
		areaselectCard.initCity("liveProvinceId" + i, "liveCityId" + i, "liveDistrictId" + i, 
				$("#livingCityId" + i).val(), $("#livingAreaId" + i).val());
	}
	
    // 添加联系人
    $("#contactAdd").click(function(){
		$("#myConfirmCopy tr").cloneSelect2(true).appendTo($("#contactArea"));
		$("#contactArea tr:last").find("input[mark='contactName']").prop("name", "contactName" + contactNum);
		$("#contactArea tr:last").find("input[mark='compTel']").prop("name", "compTel" + contactNum);
		$("#contactArea tr:last").find("input[mark='dictContactNowAddress']").prop("name", "dictContactNowAddress" + contactNum);
		$("#contactArea tr:last").find("input[mark='dictContactNowAddress']").addClass("detailAddress");
		$("#contactArea tr:last").find("input[mark='contactUint']").prop("name", "contactUint" + contactNum);
		$("#contactArea tr:last").find("input[mark='contactUint']").addClass("companyName");
		$("#contactArea tr:last").find("input[mark='contactUnitTel']").prop("name", "contactUnitTel" + contactNum);
		contactNum++;
	});
    // 添加共借人
    $("#addCoborrowBtn").click(function(){
		$("#myCoborroCopy div").first().cloneSelect2(true).appendTo($("#coborroArea"));
		$("#coborroArea>div").last().first("table").find("input[mark='dictSex']").prop("name", "dictSexo" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='haveChildFlag']").prop("name", "haveChildFlago" + count);
		count++;
	});
    $.fn.extend({          
		cloneSelect2:function(withDataAndEvents,  deepWithDataAndEvents) {
		  var $oldSelects2 = this.is('select') ? this : this.find('select');
		  //$oldSelects2.select2('destroy');
		  $oldSelects2.each(function(){
		  $(this).select2('destroy');
		  });

		  var $clonedEl = this.clone(withDataAndEvents,  deepWithDataAndEvents);
		  $oldSelects2.select2();
		  $clonedEl.is('select') ? $clonedEl.select2() : $clonedEl.find('select').select2();
		  return $clonedEl;         
		}
		});

   //保存联系人业务数据
    $("#contactNextBtn").click(function(){
    	 var phoneArr = [];
    	 var phone = $("input[mark='contactUnitTel']").each(function(){
    		 if($(this).val() != '' && $(this).val() != null){
    			phoneArr.push($(this).val());//验证手机号码是否重复
    		 }
    	 });
    	 var phoneSort = phoneArr.sort();
    	 for(var i = 0; i< phoneSort.length - 1; i ++){
    		 if(phoneSort[i] == phoneSort[i+1]){
    			 alert("您输入的手机号码："+phoneSort[i]+" 重复了，请重新输入！" );
    		 }
    	 }
    	if (!checkForm($(this).parents("form"))) {
    		return false;
    	}
    	 var tabLength=$('#contactArea tbody>tr').length;
		 if(tabLength<3){
			 art.dialog.alert("联系人至少要3个");
			 return false;
		 }
    	var param = makeData($(this));
    	var workItemParam = $("#workItemParam").serialize();
    	var loanCodeParam = $("#loanCodeParam").serialize();
    	$.ajax({
    		url:ctx+"/car/carApplyTask/carLoanFlowContact",
    		type:'post',
    		data: param + "&" + workItemParam + "&" + loanCodeParam,
    		dataType:'json',
    		success:function(data){
    			if(data == true){
 //   				art.dialog.alert("成功");
    				window.location = ctx+"/car/carApplyTask/toCarLoanFlowBank?workItem="+workItemParam+
    				"&"+loanCodeParam;
    			}else{
    				art.dialog.confirm("失败");
    			}
    		},
    		error:function(){
    			art.dialog.alert('服务器异常！');
    			return false;
    		}
    	});
    });
    //保存历史展期数据
    $("#extendNextBtn").click(function(){
    	var paramExtend = makeDataExtend($(this));
    	$.ajax({
    		url:ctx+"/car/carExtendHistory/carExtendContractInfo",
    		type:'post',
    		data: paramExtend ,
    		dataType:'json',
    		success:function(data){
    			if(data.flag == "true"){
 //   				art.dialog.alert("成功");
    				window.location = ctx+"/car/carExtendHistory/toCarExtendInfo?loanCode=" + data.newLoanCode;
    			}else{
    				art.dialog.confirm("失败");
    			}
    		},
    		error:function(){
    			art.dialog.alert('服务器异常！');
    			return false;
    		}
    	});
    });
    
    //保存---展期联系人业务数据
    $("#contactExtendNextBtn").click(function(){
    //	$("#qnm").validate();
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
			if (!checkForm($(this).parents("form"))) {
				return false;
			}
			 var tabLength=$('#contactArea tbody>tr').length;
			 if(tabLength<3){
				 art.dialog.alert("联系人至少要3个");
				 return false;
			 }
    	
    	var param = makeData($(this));
    	var workItemParam = $("#workItemParam").serialize();
    	var loanCodeParam = $("#loanCodeParam").serialize();
    	$.ajax({
    		url:ctx+"/car/carExtendApply/carLoanFlowContact",
    		type:'post',
    		data: param + "&" + workItemParam + "&" + loanCodeParam,
    		dataType:'json',
    		success:function(data){
    			if(data == true){
 //   				art.dialog.alert("成功");
    				window.location = ctx+"/car/carExtendApply/toExtendCoborrower?workItem="+workItemParam+
    				"&"+loanCodeParam;
    			}else{
    				art.dialog.confirm("失败");
    			}
    		},
    		error:function(){
    			art.dialog.alert('服务器异常！');
    			return false;
    		}
    	});
    });
    //保存---展期历史补录联系人业务数据
    $("#contactExtendHisNextBtn").click(function(){
    	$("#qnm").validate();
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
			 
			 
			 
			if (!checkForm($(this).parents("form"))) {
				return false;
			}
			 var tabLength=$('#contactArea tbody>tr').length;
			 if(tabLength<3){
				 art.dialog.alert("联系人至少要3个");
				 return false;
			 }
    	
    	var param = makeData($(this));
    	var workItemParam = $("#workItemParam").serialize();
    	var loanCodeParam = $("#loanCodeParam").serialize();
    	$.ajax({
    		url:ctx+"/car/carExtendHistory/carLoanFlowContact",
    		type:'post',
    		data: param + "&" + workItemParam + "&" + loanCodeParam,
    		dataType:'json',
    		success:function(data){
    			if(data == true){
 //   				art.dialog.alert("成功");
    				window.location = ctx+"/car/carExtendHistory/toExtendCoborrower?workItem="+workItemParam+
    				"&"+loanCodeParam;
    			}else{
    				art.dialog.confirm("失败");
    			}
    		},
    		error:function(){
    			art.dialog.alert('服务器异常！');
    			return false;
    		}
    	});
    });
    //保存共借人业务数据
    $("#coborrowNextBtn").click(function(){
    	var flag = false; 
    	var paramForm = "";
    	var divs = $("#coborroArea .contactPanel");
    	for (var i = 0; i < divs.length; i++) {
    		var divdiv = divs[i];
    		var tables = $(divdiv).find("table.coborrowerTable");
			var coboName = $(tables[0]).find("input[mark='coboName']")[0].value;
			var dictCertType = $(tables[0]).find("select[mark='dictCertType']")[0].value;
			var certNum = $(tables[0]).find("input[mark='certNum']")[0].value;
			var dictSex = $(tables[0]).find("input:radio[mark='dictSex']:checked").val();
			if(dictSex == undefined){
				$(tables[0]).find("input:radio[mark='dictSex']").parent().next().show();
				flag = false;
				break;
			} else {
				flag = true;
			}
			var haveChildFlag = $(tables[0]).find("input:radio[mark='haveChildFlag']:checked").val();
			if(haveChildFlag == undefined){
				haveChildFlag = "";
			}
			var mobile = $(tables[0]).find("input[mark='mobile']")[0].value;
			var dictMarryStatus = $(tables[0]).find("select[mark='dictMarryStatus']")[0].value;
			var familyTel = $(tables[0]).find("input[mark='familyTel']")[0].value;
			var email = $(tables[0]).find("input[mark='email']")[0].value;
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
			var id = $(tables[0]).find("input[mark='id']")[0].value;
				
			paramForm += '&carLoanCoborrower['+i+'].coboName=' + encodeURI(coboName);
			paramForm += '&carLoanCoborrower['+i+'].dictCertType=' + dictCertType;
			paramForm += '&carLoanCoborrower['+i+'].certNum=' + certNum;
			paramForm += '&carLoanCoborrower['+i+'].dictSex=' + dictSex;
			paramForm += '&carLoanCoborrower['+i+'].mobile=' + mobile;
			paramForm += '&carLoanCoborrower['+i+'].dictMarryStatus=' + dictMarryStatus;
			paramForm += '&carLoanCoborrower['+i+'].haveChildFlag=' + haveChildFlag;
			paramForm += '&carLoanCoborrower['+i+'].familyTel=' + familyTel;
			paramForm += '&carLoanCoborrower['+i+'].email=' + email;

			paramForm += '&carLoanCoborrower['+i+'].otherIncome=' + otherIncome;
			paramForm += '&carLoanCoborrower['+i+'].houseRent=' + houseRent;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdProvince=' + dictHouseholdProvince;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdCity=' + dictHouseholdCity;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdArea=' + dictHouseholdArea;
			paramForm += '&carLoanCoborrower['+i+'].householdAddress=' + encodeURI(householdAddress);
			paramForm += '&carLoanCoborrower['+i+'].dictLiveProvince=' + dictLiveProvince;
			paramForm += '&carLoanCoborrower['+i+'].dictLiveCity=' + dictLiveCity;
			paramForm += '&carLoanCoborrower['+i+'].dictLiveArea=' + dictLiveArea;
			paramForm += '&carLoanCoborrower['+i+'].nowAddress=' + encodeURI(nowAddress);
			paramForm += '&carLoanCoborrower['+i+'].loanCode=' + loanCode;
			paramForm += '&carLoanCoborrower['+i+'].id=' + id;

			var contactNames = $(tables[1]).find("input[mark='contactName']");
			var dictContactRelations = $(tables[1]).find("select[mark='dictContactRelation']");
			var contactUints = $(tables[1]).find("input[mark='contactUint']");
			var dictContactNowAddress = $(tables[1]).find("input[mark='dictContactNowAddress']");
			var compTels = $(tables[1]).find("input[mark='compTel']");
			var contactUnitTels = $(tables[1]).find("input[mark='contactUnitTel']");
			var loanCustomterTypes = $(tables[1]).find("input[mark='loanCustomterType']");
			for (j = 0; j < contactNames.length; j++) {
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactName=' + encodeURI(contactNames[j].value);
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].dictContactRelation=' + dictContactRelations[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactUint=' + encodeURI(contactUints[j].value);
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].dictContactNowAddress=' + encodeURI(dictContactNowAddress[j].value);
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].compTel=' + compTels[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactUnitTel=' + contactUnitTels[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].loanCustomterType=' + loanCustomterTypes[j].value;
			}

    	}
    	if (!checkForm($(this).parents("form")) || !flag) {
    		return false;
    	}
    	var workItemForm = $("#workItemForm").serialize();
    	$.ajax({
    		url:ctx + "/car/carApplyTask/carLoanFlowCoborrower?iptLoanCode="+$("#ipt_loanCode").val(),
    		type:'post',
    		async: false,
    		data: paramForm + "&" + workItemForm,
    		dataType:'json',
    		contentType : "application/x-www-form-urlencoded; charset=utf-8", 
    		success:function(data){
    			if(data == true){
    				window.location.href = ctx+"/car/carApplyTask/toCarLoanFlowCompany?"+workItemForm+
    				"&loanCode=" + $("#ipt_loanCode").val();
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
    //保存---展期共借人业务数据
    $("#coborrowExtendNextBtn").click(function(){
    	var flag = false; 
    	var paramForm = "";
    	if (!checkForm($(this).parents("form"))) {
    		return false;
    	}
    	var divs = $("#coborroArea .contactPanel");
    	for (var i = 0; i < divs.length; i++) {
    		var divdiv = divs[i];
    		var tables = $(divdiv).find("table.coborrowerTable");
			var coboName = $(tables[0]).find("input[mark='coboName']")[0].value;
			//var dictCertType = $(tables[0]).find("input[mark='dictCertType']")[0].value;
			var certNum = $(tables[0]).find("input[mark='certNum']")[0].value;
			var mobile = $(tables[0]).find("input[mark='mobile']")[0].value;
			var istelephonemodify = $(tables[0]).find("input[mark='istelephonemodify']")[0].value;
			var dictMarryStatus = $(tables[0]).find("select[mark='dictMarryStatus']")[0].value;
			var dictSex = $(tables[0]).find("input:radio[mark='dictSex']:checked").val();
			if(dictSex == undefined){
				$(tables[0]).find("input:radio[mark='dictSex']").parent().next().show();
				flag = false;
				break;
			} else {
				flag = true;
			}
			var haveChildFlag = $(tables[0]).find("input:radio[mark='haveChildFlag']:checked").val();
			if(haveChildFlag == undefined){
				haveChildFlag = "";
			}
			var haveOtherIncome = $(tables[0]).find("input:radio[mark='haveOtherIncome']:checked").val();
			if(haveOtherIncome == undefined){
				haveOtherIncome = "0";
			}
			var familyTel = $(tables[0]).find("input[mark='familyTel']")[0].value;
			var email = $(tables[0]).find("input[mark='email']")[0].value;
			var isemailmodify = $(tables[0]).find("input[mark='isemailmodify']")[0].value;
			var otherIncome = $(tables[0]).find("input[mark='otherIncome']")[0].value;
			var coboHouseHoldProperty = $(tables[0]).find("select[mark='coboHouseHoldProperty']")[0].value;
			var houseRent = $(tables[0]).find("input[mark='houseRent']")[0].value;
			var dictHouseholdProvince = $(tables[0]).find("input[mark='dictHouseholdProvince']")[0].value;
			var dictHouseholdCity = $(tables[0]).find("input[mark='dictHouseholdCity']")[0].value;
			var dictHouseholdArea = $(tables[0]).find("input[mark='dictHouseholdArea']")[0].value;
			var householdAddress = $(tables[0]).find("input[mark='householdAddress']")[0].value;
			var dictLiveProvince = $(tables[0]).find("select[mark='dictLiveProvince']")[0].value;
			var dictLiveCity = $(tables[0]).find("select[mark='dictLiveCity']")[0].value;
			var dictLiveArea = $(tables[0]).find("select[mark='dictLiveArea']")[0].value;
			var nowAddress = $(tables[0]).find("input[mark='nowAddress']")[0].value;
			var loanCode = $(tables[0]).find("input[mark='loanCode']")[0].value;
			var id = $(tables[0]).find("input[mark='id']")[0].value;
			var newLoanCode = $(tables[0]).find("input[mark='newLoanCode']")[0].value;
				
			paramForm += '&carLoanCoborrower['+i+'].coboName=' + encodeURI(coboName);
			//paramForm += '&carLoanCoborrower['+i+'].dictCertType=' + dictCertType;
			paramForm += '&carLoanCoborrower['+i+'].certNum=' + certNum;
			paramForm += '&carLoanCoborrower['+i+'].dictSex=' + dictSex;
			paramForm += '&carLoanCoborrower['+i+'].mobile=' + mobile;
			paramForm += '&carLoanCoborrower['+i+'].istelephonemodify=' + istelephonemodify;
			paramForm += '&carLoanCoborrower['+i+'].dictMarryStatus=' + dictMarryStatus;
			paramForm += '&carLoanCoborrower['+i+'].haveChildFlag=' + haveChildFlag;
			paramForm += '&carLoanCoborrower['+i+'].familyTel=' + familyTel;
			paramForm += '&carLoanCoborrower['+i+'].email=' + email;
			paramForm += '&carLoanCoborrower['+i+'].isemailmodify=' + isemailmodify;
			paramForm += '&carLoanCoborrower['+i+'].otherIncome=' + otherIncome;
			paramForm += '&carLoanCoborrower['+i+'].coboHouseHoldProperty=' + coboHouseHoldProperty;
			paramForm += '&carLoanCoborrower['+i+'].houseRent=' + houseRent;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdProvince=' + dictHouseholdProvince;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdCity=' + dictHouseholdCity;
			paramForm += '&carLoanCoborrower['+i+'].dictHouseholdArea=' + dictHouseholdArea;
			paramForm += '&carLoanCoborrower['+i+'].householdAddress=' + encodeURI(householdAddress);
			paramForm += '&carLoanCoborrower['+i+'].dictLiveProvince=' + dictLiveProvince;
			paramForm += '&carLoanCoborrower['+i+'].dictLiveCity=' + dictLiveCity;
			paramForm += '&carLoanCoborrower['+i+'].dictLiveArea=' + dictLiveArea;
			paramForm += '&carLoanCoborrower['+i+'].nowAddress=' + encodeURI(nowAddress);
			paramForm += '&carLoanCoborrower['+i+'].loanCode=' + loanCode;
			paramForm += '&carLoanCoborrower['+i+'].id=' + id;
			paramForm += '&carLoanCoborrower['+i+'].newLoanCode=' + newLoanCode;
			paramForm += '&carLoanCoborrower['+i+'].haveOtherIncome=' + haveOtherIncome;
			
			var contactNames = $(tables[1]).find("input[mark='contactName']");
			var dictContactRelations = $(tables[1]).find("select[mark='dictContactRelation']");
			var contactUints = $(tables[1]).find("input[mark='contactUint']");
			var dictContactNowAddress = $(tables[1]).find("input[mark='dictContactNowAddress']");
			var compTels = $(tables[1]).find("input[mark='compTel']");
			var contactUnitTels = $(tables[1]).find("input[mark='contactUnitTel']");
			var loanCustomterTypes = $(tables[1]).find("input[mark='loanCustomterType']");
			for (j = 0; j < contactNames.length; j++) {
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactName=' + encodeURI(contactNames[j].value);
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].dictContactRelation=' + dictContactRelations[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactUint=' + encodeURI(contactUints[j].value);
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].dictContactNowAddress=' + encodeURI(dictContactNowAddress[j].value);
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].compTel=' + compTels[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactUnitTel=' + contactUnitTels[j].value;
				paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].loanCustomterType' + loanCustomterTypes[j].value;
			}
    	}
/*    	$(".contactPanel").each(function(i, element) {
    		$(this).find("select[mark='dictHouseholdProvince']").attr("disabled", false);
    		$(this).find("select[mark='dictHouseholdCity']").attr("disabled", false);
    		$(this).find("select[mark='dictHouseholdArea']").attr("disabled", false);
    		$(this).find("select[mark='dictCertType']").attr("disabled", false);
    	});*/
    	var workItemForm = $("#workItemForm").serialize();
    	$.ajax({
    		url:ctx + "/car/carExtendApply/carExtendCoborrower",
    		type:'post',
    		async: false,
    		data: paramForm + "&" + workItemForm + "&loanCode=" + $("#ipt_loanCode").val() + "&newLoanCode=" + $("#ipt_newLoanCode").val(),
    		dataType:'json',
    		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
    		success:function(data){
    			if(data == true){
    				window.location.href = ctx+"/car/carExtendApply/toCarLoanFlowBank?"+workItemForm+
    				"&loanCode=" + $("#ipt_loanCode").val() + "&newLoanCode=" + $("#ipt_newLoanCode").val();
    			}else{
    				art.dialog.confirm("失败");
    			}
    		},
    		error:function(data){
    			art.dialog.alert("您输入的信息有误！");
    			return false;
    			}
    		});
    });
});
// 生成联系人信息相关参数
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
//生成历史展期相关
function makeDataExtend(t){
	var form = $(t).parents("form");
	var contractCode = form.find("input[mark='contractCode']");
	var contractAmount = form.find("input[mark='contractAmount']");
	var contractMonths = form.find("input[mark='contractMonths']");
	var contractFactDay = form.find("input[mark='contractFactDay']");
	var contractEndDay = form.find("input[mark='contractEndDay']");
	var numCount = form.find("input[mark='numCount']");
	var derate = form.find("input[mark='derate']");
	var loanCode = form.find("input[mark='loanCode']");
	var paramExtend = "";
	for(var i = 0; i < contractCode.length; i++){
		paramExtend += '&carContract['+i+'].contractCode=' + $(contractCode[i]).val();
		paramExtend += '&carContract['+i+'].contractAmount=' + $(contractAmount[i]).val();
		paramExtend += '&carContract['+i+'].contractMonths=' + $(contractMonths[i]).val();
		paramExtend += '&carContract['+i+'].contractFactDay=' + $(contractFactDay[i]).val();
		paramExtend += '&carContract['+i+'].contractEndDay=' + $(contractEndDay[i]).val();
		paramExtend += '&carContract['+i+'].numCount=' + $(numCount[i]).val();
		paramExtend += '&carContract['+i+'].derate=' + $(derate[i]).val();
		paramExtend += '&carContract['+i+'].loanCode=' + $(loanCode[i]).val();
	}
	return paramExtend;
}
function coborrowExtendNextBtnSave(){
	var flag = false; 
	var paramForm = "";
	var divs = $("#coborroArea .contactPanel");
	for (var i = 0; i < divs.length; i++) {
		var divdiv = divs[i];
		var tables = $(divdiv).find(".coborrowerTable");
		var coboName = $(tables[0]).find("input[mark='coboName']")[0].value;
		var dictCertType = $(tables[0]).find("input[mark='dictCertType']")[0].value;
		var certNum = $(tables[0]).find("input[mark='certNum']")[0].value;
		var mobile = $(tables[0]).find("input[mark='mobile']")[0].value;
		var istelephonemodify = $(tables[0]).find("input[mark='istelephonemodify']")[0].value;
		var dictMarryStatus = $(tables[0]).find("select[mark='dictMarryStatus']")[0].value;
		var dictSex = $(tables[0]).find("input:radio[mark='dictSex']:checked").val();
		if(dictSex == undefined){
			$(tables[0]).find("input:radio[mark='dictSex']").parent().next().show();
			flag = false;
			break;
		} else {
			flag = true;
		}
		var haveChildFlag = $(tables[0]).find("input:radio[mark='haveChildFlag']:checked").val();
		if(haveChildFlag == undefined){
			haveChildFlag = "";
		}
		var familyTel = $(tables[0]).find("input[mark='familyTel']")[0].value;
		var email = $(tables[0]).find("input[mark='email']")[0].value;
		var isemailmodify = $(tables[0]).find("input[mark='isemailmodify']")[0].value;
		var haveOtherIncome = $(tables[0]).find("input:radio[mark='haveOtherIncome']:checked").val();
		if(haveOtherIncome == undefined){
			haveOtherIncome = "";
		}
		var otherIncome = $(tables[0]).find("input[mark='otherIncome']")[0].value;
		var houseRent = $(tables[0]).find("input[mark='houseRent']")[0].value;
		var dictLiveProvince = $(tables[0]).find("select[mark='dictLiveProvince']")[0].value;
		var dictLiveCity = $(tables[0]).find("select[mark='dictLiveCity']")[0].value;
		var dictLiveArea = $(tables[0]).find("select[mark='dictLiveArea']")[0].value;
		var nowAddress = $(tables[0]).find("input[mark='nowAddress']")[0].value;
		var loanCode = $(tables[0]).find("input[mark='loanCode']")[0].value;
		var id = $(tables[0]).find("input[mark='id']")[0].value;
		var newLoanCode = $(tables[0]).find("input[mark='newLoanCode']")[0].value;
		var coboHouseHoldProperty = $(tables[0]).find("select[mark='coboHouseHoldProperty']")[0].value;
		var houseRent = $(tables[0]).find("input[mark='houseRent']")[0].value;
			
		paramForm += '&carLoanCoborrower['+i+'].coboName=' + coboName;
		paramForm += '&carLoanCoborrower['+i+'].dictCertType=' + dictCertType;
		paramForm += '&carLoanCoborrower['+i+'].certNum=' + certNum;
		paramForm += '&carLoanCoborrower['+i+'].dictSex=' + dictSex;
		paramForm += '&carLoanCoborrower['+i+'].mobile=' + mobile;
		paramForm += '&carLoanCoborrower['+i+'].istelephonemodify=' + istelephonemodify;
		paramForm += '&carLoanCoborrower['+i+'].dictMarryStatus=' + dictMarryStatus;
		paramForm += '&carLoanCoborrower['+i+'].haveChildFlag=' + haveChildFlag;
		paramForm += '&carLoanCoborrower['+i+'].familyTel=' + familyTel;
		paramForm += '&carLoanCoborrower['+i+'].email=' + email;
		paramForm += '&carLoanCoborrower['+i+'].isemailmodify=' + isemailmodify;
		paramForm += '&carLoanCoborrower['+i+'].otherIncome=' + otherIncome;
		paramForm += '&carLoanCoborrower['+i+'].haveOtherIncome=' + haveOtherIncome;
		paramForm += '&carLoanCoborrower['+i+'].houseRent=' + houseRent;
		paramForm += '&carLoanCoborrower['+i+'].dictLiveProvince=' + dictLiveProvince;
		paramForm += '&carLoanCoborrower['+i+'].dictLiveCity=' + dictLiveCity;
		paramForm += '&carLoanCoborrower['+i+'].dictLiveArea=' + dictLiveArea;
		paramForm += '&carLoanCoborrower['+i+'].nowAddress=' + nowAddress;
		paramForm += '&carLoanCoborrower['+i+'].loanCode=' + loanCode;
		paramForm += '&carLoanCoborrower['+i+'].id=' + id;
		paramForm += '&carLoanCoborrower['+i+'].newLoanCode=' + newLoanCode;
		paramForm += '&carLoanCoborrower['+i+'].coboHouseHoldProperty=' + coboHouseHoldProperty;
		paramForm += '&carLoanCoborrower['+i+'].houseRent=' + houseRent;
		
		var contactIds = $(tables[1]).find("input[mark='id']");
		var contactNames = $(tables[1]).find("input[mark='contactName']");
		var dictContactRelations = $(tables[1]).find("select[mark='dictContactRelation']");
		var contactUints = $(tables[1]).find("input[mark='contactUint']");
		var dictContactNowAddress = $(tables[1]).find("input[mark='dictContactNowAddress']");
		var compTels = $(tables[1]).find("input[mark='compTel']");
		var contactUnitTels = $(tables[1]).find("input[mark='contactUnitTel']");
		var loanCustomterTypes = $(tables[1]).find("input[mark='loanCustomterType']");
		for (j = 0; j < contactNames.length; j++) {
			paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].id=' + contactIds[j].value;
			paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactName=' + contactNames[j].value;
			paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].dictContactRelation=' + dictContactRelations[j].value;
			paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactUint=' + contactUints[j].value;
			paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].dictContactNowAddress=' + dictContactNowAddress[j].value;
			paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].compTel=' + compTels[j].value;
			paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].contactUnitTel=' + contactUnitTels[j].value;
			paramForm += '&carLoanCoborrower['+i+'].carCustomerContactPerson['+j+'].loanCustomterType' + loanCustomterTypes[j].value;
		}
	}
	$(".contactPanel").each(function(i, element) {
		$(this).find("select[mark='dictHouseholdProvince']").attr("disabled", false);
		$(this).find("select[mark='dictHouseholdCity']").attr("disabled", false);
		$(this).find("select[mark='dictHouseholdArea']").attr("disabled", false);
		$(this).find("input[mark='dictCertType']").attr("disabled", false);
	});
	var workItemForm = $("#workItemForm").serialize();
	$.ajax({
		url:ctx + "/car/carExtend/carExtendUpload/coborrowerSave",
		type:'post',
		async: false,
		data: paramForm + "&" + workItemForm + "&loanCode=" + $("#ipt_loanCode").val() + "&newLoanCode=" + $("#ipt_newLoanCode").val(),
		success:function(data){
			$.jBox.tip(data);
		}
		});
}
//验证共借人性别
function checkRequired (){
	var sexOne =  $("input[id=sex_one]:checked").val();
	var sexTwo =  $("input[id=sex_two]:checked").val();
	var sexThree =  $("input[id=sex_three]:checked").val();
	if(typeof(sexOne) == 'undefined'){
		$("#sexSpan").show();
	} else {
		$("#sexSpan").hide();
	}
	if(typeof(sexTwo) == 'undefined'){
		$("#sex_twoSpan").show();
	} else {
		$("#sex_twoSpan").hide();
	}
	if(typeof(sexThree) == 'undefined'){
		$("#sex_threeSpan").show();
	} else {
		$("#sex_threeSpan").hide();
	}
}
// 添加共借人联系人
function addContractPersonClick(self) {
	$("#myConfirmCopyg tr").cloneSelect2(true).appendTo($(self).parent().next("table"));
}
//删除共借人
function delCoborrower(self){
	$(self).parents("div:first").remove();
}
//删除联系人
function delContact(self){
	delArray.push($($(self).parents("tr:first").find("input[mark='id']")[0]).attr("value"));
	$(self).parents("tr:first").remove();
}

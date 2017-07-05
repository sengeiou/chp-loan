var count = 0;
var contactNum = 3;
var creditorRightNum = 3;
var countAdd = 100;
$(function(){
	count = $("#coboLength").val();
	if (count == undefined) {
		count = 0;
	}
	
	$("input:radio[name='haveOtherIncome']").change(function() {
		if($("input:radio[name='haveOtherIncome']:checked").val() == '1'){
			$(this).parent().children("span").show();
		}else{
			$(this).parent().children("span").hide();
			$(this).parent().find("input[name='haveOtherIncomeo']").val("");
		}
	});
	$("input[name='haveOtherIncome']").trigger("change");

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
		// 其他收入显示与隐藏
		$(this).find("input:radio[name='haveOtherIncome" + i + "']").bind('change', function() {
			var haveOtherIncome = $("input:radio[name='haveOtherIncome" + i + "']:checked").val();
			if(haveOtherIncome){
				if(haveOtherIncome == '0'){
					$(this).parent().find("input[name='otherIncome" + i + "']").val("");
					$(this).parent().children("span").hide();
				}else{
					$(this).parent().children("span").show();
				}
			}
		});
		$(this).find("input:radio[name='haveOtherIncome" + i + "']").trigger('change');
	});
	
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

    
    // 车借债权录入增加共同借款人
    $("#coborrowAdd").click(function(){
		$("#mycoborrowCopy tr").cloneSelect2(true).appendTo($("#coborrowArea"));
		$("#coborrowArea tr:last").find("input[mark='coborrowName']").prop("name", "coborrowName" + creditorRightNum);
		$("#coborrowArea tr:last").find("input[mark='coborrowCertNum']").prop("name", "coborrowCertNum" + creditorRightNum);
		creditorRightNum++;
	});
    // 添加联系人
    $("#contactAdd").click(function(){
		$("#myConfirmCopy tr").cloneSelect2(true).appendTo($("#contactArea"));
		$("#contactArea tr:last").find("input[mark='contactName']").prop("name", "contactNameo" + contactNum);
		$("#contactArea tr:last").find("input[mark='compTel']").prop("name", "compTelo" + contactNum);
		$("#contactArea tr:last").find("input[mark='contactUnitTel']").prop("name", "contactUnitTelo" + contactNum);
		contactNum++;
	});
    // 添加共借人
    $("#addCoborrowBtn").click(function(){
		$("#myCoborroCopy div").first().cloneSelect2(true).appendTo($("#coborroArea"));
		$("#coborroArea>div").last().first("table").find("input[mark='dictSex']").prop("name", "dictSex" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='haveChildFlag']").prop("name", "haveChildFlag" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='email']").prop("name", "email" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='mobile']").prop("name", "mobile" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='familyTel']").prop("name", "familyTel" + count);
		//$("#coborroArea>div").last().first("table").find("input[mark='dictCertType']").prop("name", "dictCertType" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='certNum']").prop("name", "certNum" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='otherIncome']").prop("name", "otherIncome" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='houseRent']").prop("name", "houseRent" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='coboName']").prop("name", "coboName" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='companyName']").prop("name", "companyName" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='companyAddress']").prop("name", "companyAddress" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='companyPosition']").prop("name", "companyPosition" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='haveOtherIncome']").prop("name", "haveOtherIncomeo" + count);
		$("#coborroArea>div").last().first("table").find("select[mark='coboHouseHoldProperty']").prop("name", "coboHouseHoldPropertyo" + count);
		(function(count){
			$("input:radio[name='haveOtherIncomeo" + count + "']").change(function() {
				if($("input:radio[name='haveOtherIncomeo" + count + "']:checked").val() == '1'){
					$(this).parent().children("span").show();
				}else{
					$(this).parent().children("span").hide();
					$(this).parent().find("input[name='otherIncome" + count + "']").val("");
				}
			});
			$("input:radio[name='haveOtherIncomeo" + count + "']").trigger("change");
	        //月租金显示与隐藏
	        $("select[name='coboHouseHoldPropertyo" + count + "']").change(function() {
	            if($(this).val() == '0' || $(this).val() == '2'){
	            	$(this).parent().children("span").children("label")[0].innerHTML="月供金额：";
	            	$(this).parent().children("span").children("input").addClass("required");
	                $(this).parent().children("span").show();
	            }else if($(this).val() == '4'){
	                $(this).parent().children("span").children("label")[0].innerHTML="月租金：";
	                $(this).parent().children("span").children("input").addClass("required");
	                $(this).parent().children("span").show();
	            } else {
	                $(this).parent().children("span").hide();
	                $(this).parent().children("span").children("input").removeClass("required");
	                $(this).parent().find("input[name='houseRent" + count + "']").val("");
	            }
	        });
	        $("select[name='coboHouseHoldPropertyo" + count + "']").trigger("change");
		})(count);
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
    	 var telephone =[];
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
    	 
   /* 	//保存单位号码校验
    	  $("input[mark='compTel']").each(function(){
    		 if($(this).val() != '' && $(this).val() != null){
    			 telephone.push($(this).val());//验证电话号码是否重复
    		 }
    	 });
    	 var telephoneSort = telephone.sort();
    	 for(var i = 0; i< telephoneSort.length - 1; i ++){
    		 if(telephoneSort[i] == telephoneSort[i+1]){
    			 alert("您输入的电话号码："+telephoneSort[i]+" 重复了，请重新输入！" );
    			 return false;
    		 }
    	 }
    	 */
    	 
    	 
    	var flag = true;
    	if (!checkForm($(this).parents("form"))) {
    		flag = false;
    	}
    	if($("input[name='compTel']").hasClass("error") || $("input[name='contactName']").hasClass("error")){
    		flag = false;
    	}
    	 var tabLength=$('#contactArea tbody>tr').length;
		 if(tabLength<3){
			 art.dialog.alert("联系人至少要3个");
			 return false;
		 }
    	var param = makeData($(this));
    	var workItemParam = $("#workItemParam").serialize();
    	var loanCodeParam = $("#loanCodeParam").serialize();
    	if(flag){
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
    	}
    });
    
    // 车借债权录入增加共同借款人
    $("#creditorRightSubmit").click(function(){
    	
    	
    	var f=$("#creditorRightsForm").validate({
			onclick: true, 
			rules :{
				email:{
					email:"/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/"
				}
			}
			
        }).form();
    	
    	if(f==true){
    	
    	var param = creditorMakeData($(this));
    	var creditorRightsParam = $("#creditorRightsForm").serialize();
    	$.ajax({
    		url:ctx+"/car/creditorRight/addCoborrower",
    		beforeSend : function(){
				waitingDialog.show();
			},
    		type:'post',
    		data: param + "&" + creditorRightsParam,
    		dataType:'json',
    		success:function(data){
    			waitingDialog.hide();
    			if(data == true){
     				alert("成功");
    				window.location = ctx+"/car/creditorRight/list";
    			}else{
    				alert("失败");
    			}
    		},
    		error:function(){
    			waitingDialog.hide();
    			art.dialog.alert('服务器异常！');
    			return false;
    		}
    	});
    	
    }
    
    });    
    
   
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
    // 验证
    function isOdd(num) {
    	return (num % 2 == 0) ? false : true;
    };
    //保存共借人业务数据
    $("#coborrowNextBtn").click(function(){
    	
    	var f = $("#carCoborrow").validate({
			onclick: true, 
			rules :{
				email:{
					email:"/^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/"
				}
			}
    	}).form();
    	
    	if(f == true && checkForm($(this).parents("form")) && !$("input[mark='certNum']").hasClass("error")){
	    	
	    	var flag = false;
	    	var paramForm = "";
	    	var divs = $("#coborroArea .contactPanel");
	    	
	    	for (var i = 0; i < divs.length; i++) {
	    		var divdiv = divs[i];
	    		var tables = $(divdiv).find("table.coborrowerTable");
	    		var dictSex = $(tables[0]).find("input:radio[mark='dictSex']:checked").val();
		    	if(dictSex == undefined){
					$(tables[0]).find("input:radio[mark='dictSex']").parent().next().show();
					flag = false;
					break;
				} else {
					flag = true;
				}
	    	}
	    	if (!flag || $("input[mark='familyTel']").hasClass("error") || $("input[mark='contactName']").hasClass("error")) {
	    		return false;
    		} else {
    			var telArr = [];
	   			var tel = $("input[mark='compTel']").each(function(){
	   				if($(this).val() != '' && $(this).val() != null){
	   					 telArr.push($(this).val());//验证手机号码是否重复
	   				}
	   			});
	   			var telSort = telArr.sort();
	   			for(var i = 0; i< telSort.length - 1; i ++){
	   				if(telSort[i] == telSort[i+1]){
	   					alert("您输入的单位号码："+telSort[i]+" 重复了，请重新输入！" );
	   					return false;
	   				}
	   			}
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
	   					 return false;
	   				 }
	   			}
	   			
	   			var strSex = "";
	   			for (var i = 0; i < divs.length; i++) {
		    		var divdiv = divs[i];
		    		var tables = $(divdiv).find("table.coborrowerTable");
		    		var dictCertType = $(tables[0]).find("input[mark='dictCertType']")[0].value;
					var certNum = $(tables[0]).find("input[mark='certNum']")[0].value;
					var dictSex = $(tables[0]).find("input:radio[mark='dictSex']:checked").val();
			    	if (dictCertType == '0') { // 若为身份证
			    		var tSex = certNum.substr(certNum.length - 2, 1);
			    		if ((isOdd(tSex) && dictSex == '0') || (!isOdd(tSex) && dictSex == '1')) {
			    			continue;
			    		} else {
			    			strSex += "," + (i + 1);
			    		}
			    	} else {
			    		continue;
			    	}
		    	}
	   			if (strSex != "") {
	   				strSex = strSex.substring(1);
	   				alert("第" + strSex + "个共借人身份证和性别不匹配，请重新填写！");
	   				return false;
	   			} else {
	   				$("#coborrowNextBtn").attr("disabled", true);
	   				for (var i = 0; i < divs.length; i++) {
	   		    		var divdiv = divs[i];
	   		    		var tables = $(divdiv).find("table.coborrowerTable");
	   					var coboName = $(tables[0]).find("input[mark='coboName']")[0].value;
	   					var dictCertType = $(tables[0]).find("input[mark='dictCertType']")[0].value;
	   					var certNum = $(tables[0]).find("input[mark='certNum']")[0].value;
	   					var dictSex = $(tables[0]).find("input:radio[mark='dictSex']:checked").val();

	   					var haveChildFlag = $(tables[0]).find("input:radio[mark='haveChildFlag']:checked").val();
	   					if(haveChildFlag == undefined){
	   						haveChildFlag = "";
	   					}
	   					var mobile = $(tables[0]).find("input[mark='mobile']")[0].value;
	   					var dictMarryStatus = $(tables[0]).find("select[mark='dictMarryStatus']")[0].value;
	   					var familyTel = $(tables[0]).find("input[mark='familyTel']")[0].value;
	   					var email = $(tables[0]).find("input[mark='email']")[0].value;
	   					var haveOtherIncome = $(tables[0]).find("input:radio[mark='haveOtherIncome']:checked").val();
	   					if(haveOtherIncome == undefined){
	   						haveOtherIncome = "";
	   					}
	   					var otherIncome = $(tables[0]).find("input[mark='otherIncome']")[0].value;
	   					var coboHouseHoldProperty = $(tables[0]).find("select[mark='coboHouseHoldProperty']")[0].value;
	   					var houseRent = $(tables[0]).find("input[mark='jydzzmRentMonth']")[0].value;
	   					var dictHouseholdProvince = $(tables[0]).find("select[mark='dictHouseholdProvince']")[0].value;
	   					var dictHouseholdCity = $(tables[0]).find("select[mark='dictHouseholdCity']")[0].value;
	   					var dictHouseholdArea = $(tables[0]).find("select[mark='dictHouseholdArea']")[0].value;
	   					var householdAddress = $(tables[0]).find("input[mark='householdAddress']")[0].value;
	   					
	   					//14、共借人增加工作单位、地址、职务  // companyName companyAddress companyPosition
	   					var companyName = $(tables[0]).find("input[mark='companyName']")[0].value;
	   					var companyAddress = $(tables[0]).find("input[mark='companyAddress']")[0].value;
	   					var companyPosition = $(tables[0]).find("input[mark='companyPosition']")[0].value;
	   					
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
	   					paramForm += '&carLoanCoborrower['+i+'].haveOtherIncome=' + haveOtherIncome;
	   					paramForm += '&carLoanCoborrower['+i+'].otherIncome=' + otherIncome;
	   					paramForm += '&carLoanCoborrower['+i+'].coboHouseHoldProperty=' + coboHouseHoldProperty;
	   					paramForm += '&carLoanCoborrower['+i+'].houseRent=' + houseRent;
	   					paramForm += '&carLoanCoborrower['+i+'].dictHouseholdProvince=' + dictHouseholdProvince;
	   					paramForm += '&carLoanCoborrower['+i+'].dictHouseholdCity=' + dictHouseholdCity;
	   					paramForm += '&carLoanCoborrower['+i+'].dictHouseholdArea=' + dictHouseholdArea;
	   					paramForm += '&carLoanCoborrower['+i+'].householdAddress=' + encodeURI(householdAddress);
	   					
	   					//14、共借人增加工作单位、地址、职务  // companyName companyAddress companyPosition
	   					paramForm += '&carLoanCoborrower['+i+'].companyName=' + encodeURI(companyName);
	   					paramForm += '&carLoanCoborrower['+i+'].companyAddress=' + encodeURI(companyAddress);
	   					paramForm += '&carLoanCoborrower['+i+'].companyPosition=' + encodeURI(companyPosition);
	   					
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
	   			   	
	   				var workItemForm = $("#workItemForm").serialize();
	   		    	$.ajax({
	   		    		url : ctx + "/car/carApplyTask/carLoanFlowCoborrower?iptLoanCode=" + $("#ipt_loanCode").val(),
	   		    		type : 'post',
	   		    		/*async: false,*/
	   		    		data: paramForm + "&" + workItemForm,
	   		    		dataType : 'text',
	   		    		contentType: "application/x-www-form-urlencoded; charset=utf-8", 
	   		    		success : function(data){
	   		    			if(data == 't'){
	   		    				window.location.href = ctx + "/car/carApplyTask/toCarLoanFlowCompany?" + workItemForm + "&loanCode=" + $("#ipt_loanCode").val();
	   		    			}else if(data=='f'){
	   		    				art.dialog.alert("主借人的身份证不能和共借人的身份证相同");
	   		    			}else if(data=='x'){
	   		    				art.dialog.alert("共借人的身份证相同");
	   		    			}else{
	   		    				art.dialog.alert("失败");
	   		    			}
	   		    		},
	   		    		error : function(data){
	   		    			art.dialog.alert(data);
	   		    			return false;
	   	    			}
	   	    		});
	   			}
    		}
    	}
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
    
 // 车借债权录入增加共同借款人
    function creditorMakeData(t){
    	var form = $(t).parents("form");
    	
    	var coborrowN = form.find("input[mark='coborrowName']");
    	var coborrowCertN = form.find("input[mark='coborrowCertNum']");
    	var param = "";
    	for(var i = 0; i < coborrowN.length; i++){
    		param += '&coborrower['+i+'].loanCustomerName=' + encodeURI($(coborrowN[i]).val());
    		param += '&coborrower['+i+'].customerCertNum=' + $(coborrowCertN[i]).val();
    	}
    	return param;
    };
  
});
// 添加共借人联系人
function addContractPersonClick(self) {
	$("#myConfirmCopyg tr").cloneSelect2(true).appendTo($(self).parent().next("table"));
	$(self).parent().next("table").find("tr:last").find("input[mark='contactName']").prop("name", "contactNamep" + countAdd);
	$(self).parent().next("table").find("tr:last").find("input[mark='compTel']").prop("name", "compTelp" + countAdd);
	$(self).parent().next("table").find("tr:last").find("input[mark='contactUnitTel']").prop("name", "contactUnitTelp" + countAdd);
	countAdd++;
};
//删除共借人
function delCoborrower(self){
	$(self).parents("div:first").remove();
	count--;
};

function addEndDate(self){
	alert(0);
}

function changeContractDay(){
	if($("#monthId").val()!=""){
		setContractDate($("#monthId").val());
	}
}

function setContractDate(value){
	//如果申请借款期限没选择，设置日期为空值，否则会出现NaN类型错误
	if(value == null || value.trim() == ''){
	    //为首次还款日赋值
		$("input[name = 'downPaymentDay']").val("");
	    //为合同截止日期赋值
		$("input[name = 'contractEndDay']").val("");
		return ;
	}
	//判断合同签约日期是否为空，如果为空，提示
	var contractDay =  $("input[name = 'contractDay']").val();
	if(contractDay == null || contractDay.trim() == ''){
		art.artDialog.alert("请先选择合同签约日期！<br/>在选择期数和其他日期！");
		getLoanMonths($("#productType").val(),"");
		return;
	}
    //为首次还款日赋值
	$("input[name = 'downPaymentDay']").val(convertDate(contractDay,29));
    //为合同截止日期赋值
	$("input[name = 'contractEndDay']").val(convertDate(contractDay,(parseInt(value)-1)));
}

function convertDate(time,dateWeek){
	//对日期进行相加，计算出首期还款日和合同截止日期
	 var dateTemp = time.split("-");  
    var nDate = new Date(dateTemp[1] + '/' + dateTemp[2] + '/' + dateTemp[0]); //转换为MM-DD-YYYY格式    
    var millSeconds = Math.abs(nDate) + (dateWeek * 24 * 60 * 60 * 1000);  
    var rDate = new Date(millSeconds);  
    var year = rDate.getFullYear();  
    var month = rDate.getMonth() + 1;  
    if (month < 10) month = "0" + month;  
    var date = rDate.getDate();  
    if (date < 10) date = "0" + date;  
    return (year + "-" + month + "-" + date);
}

function addDate(date,days){ 
    var d=new Date(date); 
    d.setDate(d.getDate()+days); 
    var m=d.getMonth()+1; 
    return d.getFullYear()+'-'+m+'-'+d.getDate(); 
} 

//借款产品类型(dictProductType)
//申请借款期限(value)
function getLoanMonths(dictProductType,value){
	$.ajax({
		type : "POST",
		url : ctx + "/common/productInfo/asynLoadProductMonths",
		data : {
			productCode : dictProductType,
			productType:"products_type_car_credit"
		},
		success : function(data) {
			console.info(data);
			 var resultObj = data.split(",");
			$("#monthId").empty();
			$("#monthId").append("<option value=''>请选择</option>");
			$.each(resultObj, function(i, item) {
				if (value == item) {
					$("#monthId").append("<option selected=true value=" + item + ">" + item + "</option>");
				} else {
					$("#monthId").append("<option value=" + item + ">" + item + "</option>");
				}
			});
			$("#monthId").trigger("change"); 
		}
	});
}
//删除联系人
var delArray = [];
function delContact(self){
	delArray.push($($(self).parents("tr:first").find("input[mark='id']")[0]).attr("value"));
	$(self).parents("tr:first").remove();
}
//删除共借人联系人
function delCoboContact(self){
	$(self).parent("td").parent("tr").remove();
	countAdd--;
};
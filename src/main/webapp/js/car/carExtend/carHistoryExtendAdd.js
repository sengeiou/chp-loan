var extendCount = null; // 加上车借，共已展期次数
var sumCount = 0; // 共可展期次数
var delCoboArray = []; // 删除的共借人数组
var count = 0; // 共借人回显个数
var countAdd = 100; // 共借人联系人
var delHistoryExtendArray = []; // 存放删除数据的合同id的数组
$(function() {
	count = $("#coboLength").val();
	if (count == undefined) {
		count = 0;
	}
	sumCount = parseInt($("#sumCountId").val());
	extendCount = parseInt($("#extendCountId").val());
	function addDate(date,days){
		var d = new Date(date);
		d.setDate(d.getDate() + days);
		var month = d.getMonth() + 1;
		var day = d.getDate();
		if(month < 10){
			month = "0" + month;
		}
		if(day < 10){
			day = "0" + day;
		}
		var val = d.getFullYear() + "-" + month + "-" + day;
		return val;
	}
	// 添加历史展期
	$("#ExtendAdd").click(function() {
		if (extendCount <= sumCount - 1) {
			var lastValStr = $("#lastValStr").val();
			if (lastValStr < 30000 && lastValStr > 1) {
				alert("您只能申请一次展期");
				return;
			}
			if ($("#contactAreaExtend tr:eq(1)").length == 0) {
				$("#myExtendCopyFirst tr").cloneSelect2(true).appendTo($("#contactAreaExtend"));
			} else {
				$("#myExtendCopy tr").cloneSelect2(true).appendTo($("#contactAreaExtend"));
				var firstEndDay = $($("#contactAreaExtend tr:eq(1)")[0]).find("input[mark='contractEndDay']")[0].value;
				if (firstEndDay != '') {
					$("#contactAreaExtend tr:last>td").find("input[mark='contractFactDay']")[0].value = addDate(firstEndDay, 1 * extendCount + (29 * (extendCount - 1)));
					$("#contactAreaExtend tr:last>td").find("input[mark='contractEndDay']")[0].value = addDate(firstEndDay, 1 * extendCount + 29 * extendCount);
				}
			}
			$("#contactAreaExtend tr:last>td").find("input[mark='numCount']")[0].value = extendCount;
			$("#contactAreaExtend tr:last").find("input[mark='contractFactDay']").prop("name", "contractFactDay" + extendCount);
			$("#contactAreaExtend tr:last").find("input[mark='contractEndDay']").prop("name", "contractEndDay" + extendCount);
			extendCount++;
		} else {
			alert("已达展期上限，不可再展期！");
		}
	});
	// 删除最后一行历史展期
	$("#ExtendDelete").click(function() {
		if ($("#contactAreaExtend tr:last").attr("mark") == 'saveDataMark') {
			delHistoryExtendArray.push(($("#contactAreaExtend tr:last").find("input[mark='id']")[0]).value);
			$("#contactAreaExtend tr:last").remove();
			extendCount--;
		}
	});
	
	 //月租金显示与隐藏
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
    
    //其他收入
	$("input:radio[name='haveOtherIncome']").change(function() {
		if($("input:radio[name='haveOtherIncome']:checked").val() == '1'){
			$(this).parent().children("span").css("display","inline");
			$(this).parent().find("input[name='otherIncome']").addClass("required");
		}else{
			$(this).parent().children("span").css("display","none");
			$(this).parent().find("input[name='otherIncome']").val("");
			$(this).parent().find("input[name='otherIncome']").removeClass("required");
		}
	});
	$("input[name='haveOtherIncome']").trigger("change");
	
	$(".contactPanel").each(function(i, element) {
		// 其他收入显示与隐藏
		$(this).find("input:radio[name='haveOtherIncome" + i + "']").bind('change', function() {
			var haveOtherIncome = $("input:radio[name='haveOtherIncome" + i + "']:checked").val();
			if(haveOtherIncome){
				if(haveOtherIncome == '0'){
					$(this).parent().find("input[name='otherIncome" + i + "']").val("");
					$(this).parent().children("span").css("display","none");
					$(this).parent().find("input[name='otherIncome" + i + "']").removeClass("required");
				}else{
					$(this).parent().children("span").css("display","inline");
					$(this).parent().find("input[name='otherIncome" + i + "']").addClass("required");
				}
			}
		});
		$(this).find("input:radio[name='haveOtherIncome" + i + "']").trigger('change');
	});
	
	// 添加共借人
    $("#addCoborrowBtn").click(function(){
		$("#myCoborroCopy div").first().cloneSelect2(true).appendTo($("#coborroArea"));
		$("#coborroArea>div").last().first("table").find("input[mark='dictSex']").prop("name", "dictSexo" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='haveChildFlag']").prop("name", "haveChildFlago" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='email']").prop("name", "emailo" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='mobile']").prop("name", "mobileo" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='familyTel']").prop("name", "familyTelo" + count);
		//$("#coborroArea>div").last().first("table").find("input[mark='dictCertType']").prop("name", "dictCertTypeo" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='certNum']").prop("name", "certNumo" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='otherIncome']").prop("name", "otherIncomeo" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='houseRent']").prop("name", "houseRento" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='coboName']").prop("name", "coboNameo" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='companyName']").prop("name", "companyName" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='companyAddress']").prop("name", "companyAddress" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='companyPosition']").prop("name", "companyPosition" + count);
		$("#coborroArea>div").last().first("table").find("input[mark='haveOtherIncome']").prop("name", "haveOtherIncomeo" + count);
		$("#coborroArea>div").last().first("table").find("select[mark='coboHouseHoldProperty']").prop("name", "coboHouseHoldPropertyo" + count);
		(function(count){
			$("input:radio[name='haveOtherIncomeo" + count + "']").change(function() {
				if($("input:radio[name='haveOtherIncomeo" + count + "']:checked").val() == '1'){
					$(this).parent().children("span").css("display","inline");
					$(this).parent().find("input[name='otherIncomeo" + count + "']").addClass("required");
				}else{
					$(this).parent().children("span").css("display","none");
					$(this).parent().find("input[name='otherIncomeo" + count + "']").removeClass("required");
					$(this).parent().find("input[name='otherIncomeo" + count + "']").val("");
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
	                $(this).parent().find("input[name='houseRento" + count + "']").val("");
	            }
	        });
	        $("select[name='coboHouseHoldPropertyo" + count + "']").trigger("change");
		})(count);
		count++;
	});
	$.fn.extend({
		cloneSelect2 : function(withDataAndEvents, deepWithDataAndEvents) {
			var $oldSelects2 = this.is('select') ? this : this.find('select');
			$oldSelects2.each(function() {
				$(this).select2('destroy');
			});
			var $clonedEl = this.clone(withDataAndEvents, deepWithDataAndEvents);
			$oldSelects2.select2();
			$clonedEl.is('select') ? $clonedEl.select2() : $clonedEl.find('select').select2();
			return $clonedEl;
		}
	});
	// 保存历史展期数据
	$("#extendNextBtn").click(function() {
		if (!checkForm($(this).parents("form"))) {
			return;
		}
		var paramExtend = makeDataExtend($(this));
		if (paramExtend == "") {
			art.dialog.alert("请补录历史展期！");
			return false;
		} else {
			$.ajax({
				url : ctx + "/car/carExtendHistory/carExtendContractInfo",
				type : 'post',
				data : paramExtend + "&loanCode=" + $("#loanCodeId").val() + "&newLoanCode=" + $("#newLoanCodeId").val() 
					+ "&contractMonths=" + $("#monthId").val() + "&delArray=" + delHistoryExtendArray,
				dataType : 'json',
				success : function(data) {
					if (data.flag == "true") {
						window.location = ctx + "/car/carExtendHistory/toCarExtendInfo?loanCode=" + data.loanCode + "&newLoanCode=" + data.newLoanCode;
					} else {
						art.dialog.alert("失败");
					}
				},
				error : function() {
					art.dialog.alert('服务器异常！');
					return false;
				}
			});
		}
	});
	// 验证共借人性别
	function checkRequired() {
		var sexOne = $("input[id=sex_one]:checked").val();
		var sexTwo = $("input[id=sex_two]:checked").val();
		var sexThree = $("input[id=sex_three]:checked").val();
		if (typeof (sexOne) == 'undefined') {
			$("#sexSpan").show();
		} else {
			$("#sexSpan").hide();
		}
		if (typeof (sexTwo) == 'undefined') {
			$("#sex_twoSpan").show();
		} else {
			$("#sex_twoSpan").hide();
		}
		if (typeof (sexThree) == 'undefined') {
			$("#sex_threeSpan").show();
		} else {
			$("#sex_threeSpan").hide();
		}
	};
	// 验证
	function isOdd(num) {
		return (num % 2 == 0) ? false : true;
	};

	// 保存---历史展期补录共借人业务数据
	$("#coborrowExtendNextBtn").click(function() {
		if (checkForm($(this).parents("form"))) {
			var flag = false;
			var paramForm = "";
			var divs = $("#coborroArea .contactPanel");
			for (var i = 0; i < divs.length; i++) {
				var divdiv = divs[i];
				var tables = $(divdiv).find("table.coborrowerTable");
				var dictSex = $(tables[0]).find("input:radio[mark='dictSex']:checked").val();
				if (dictSex == undefined) {
					$(tables[0]).find("input:radio[mark='dictSex']").parent().next().show();
					flag = false;
					break;
				} else {
					flag = true;
				}
			}
			if (flag) {
				var telArr = [];
				var tel = $("input[mark='compTel']").each(function() {
					if ($(this).val() != '' && $(this).val() != null) {
						telArr.push($(this).val());// 验证手机号码是否重复
					}
				});
				var telSort = telArr.sort();
				for (var i = 0; i < telSort.length - 1; i++) {
					if (telSort[i] == telSort[i + 1]) {
						alert("您输入的单位号码：" + telSort[i] + " 重复了，请重新输入！");
						return false;
					}
				}
				var phoneArr = [];
				var phone = $("input[mark='contactUnitTel']").each(function() {
					if ($(this).val() != '' && $(this).val() != null) {
						phoneArr.push($(this).val());// 验证手机号码是否重复
					}
				});
				var phoneSort = phoneArr.sort();
				for (var i = 0; i < phoneSort.length - 1; i++) {
					if (phoneSort[i] == phoneSort[i + 1]) {
						alert("您输入的手机号码：" + phoneSort[i] + " 重复了，请重新输入！");
						return false;
					}
				}
				var customerCertNum = $("#customerCertNum").val();
				for(var i = 0; i < divs.length; i++){
					var divdiv = divs[i];
					var tables = $(divdiv).find("table.coborrowerTable");
					var certNum = $(tables[0]).find("input[mark='certNum']")[0].value;
					if(certNum == customerCertNum ){
						alert("共借人身份证号与主借人重复了！请重新输入！");
						return false;
					}else{
						continue;
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
					for (var i = 0; i < divs.length; i++) {
						var divdiv = divs[i];
						var tables = $(divdiv).find("table.coborrowerTable");
						var coboName = $(tables[0]).find("input[mark='coboName']")[0].value;
						var dictCertType = $(tables[0]).find("input[mark='dictCertType']")[0].value;
						var certNum = $(tables[0]).find("input[mark='certNum']")[0].value;
						var mobile = $(tables[0]).find("input[mark='mobile']")[0].value;
						var istelephonemodify = $(tables[0]).find("input[mark='istelephonemodify']")[0].value;
						var dictMarryStatus = $(tables[0]).find("select[mark='dictMarryStatus']")[0].value;
						var dictSex = $(tables[0]).find("input:radio[mark='dictSex']:checked").val();
						var haveChildFlag = $(tables[0]).find("input:radio[mark='haveChildFlag']:checked").val();
						if (haveChildFlag == undefined) {
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
	   					var coboHouseHoldProperty = $(tables[0]).find("select[mark='coboHouseHoldProperty']")[0].value;
						var houseRent = $(tables[0]).find("input[mark='houseRent']")[0].value;
						var dictHouseholdProvince = $(tables[0]).find("select[mark='dictHouseholdProvince']")[0];
						if (dictHouseholdProvince == undefined) {
							dictHouseholdProvince = $(tables[0]).find("input[mark='dictHouseholdProvince']")[0].value;
						} else {
							dictHouseholdProvince = dictHouseholdProvince.value;
						}
						var dictHouseholdCity = $(tables[0]).find("select[mark='dictHouseholdCity']")[0];
						if (dictHouseholdCity == undefined) {
							dictHouseholdCity = $(tables[0]).find("input[mark='dictHouseholdCity']")[0].value;
						} else {
							dictHouseholdCity = dictHouseholdCity.value;
						}
						var dictHouseholdArea = $(tables[0]).find("select[mark='dictHouseholdArea']")[0];
						if (dictHouseholdArea == undefined) {
							dictHouseholdArea = $(tables[0]).find("input[mark='dictHouseholdArea']")[0].value;
						} else {
							dictHouseholdArea = dictHouseholdCity.value;
						}
						var householdAddress = $(tables[0]).find("input[mark='householdAddress']")[0].value;

						var dictLiveProvince = $(tables[0]).find("select[mark='dictLiveProvince']")[0].value;
						var dictLiveCity = $(tables[0]).find("select[mark='dictLiveCity']")[0].value;
						var dictLiveArea = $(tables[0]).find("select[mark='dictLiveArea']")[0].value;
						var nowAddress = $(tables[0]).find("input[mark='nowAddress']")[0].value;
						var loanCode = $(tables[0]).find("input[mark='loanCode']")[0].value;
						var id = $(tables[0]).find("input[mark='id']")[0].value;

						paramForm += '&carLoanCoborrower[' + i + '].coboName=' + encodeURI(coboName);
						paramForm += '&carLoanCoborrower[' + i + '].dictCertType=' + dictCertType;
						paramForm += '&carLoanCoborrower[' + i + '].certNum=' + certNum;
						paramForm += '&carLoanCoborrower[' + i + '].dictSex=' + dictSex;
						paramForm += '&carLoanCoborrower[' + i + '].mobile=' + mobile;
						paramForm += '&carLoanCoborrower[' + i + '].istelephonemodify=' + istelephonemodify;
						paramForm += '&carLoanCoborrower[' + i + '].dictMarryStatus=' + dictMarryStatus;
						paramForm += '&carLoanCoborrower[' + i + '].haveChildFlag=' + haveChildFlag;
						paramForm += '&carLoanCoborrower[' + i + '].familyTel=' + familyTel;
						paramForm += '&carLoanCoborrower[' + i + '].email=' + email;
						paramForm += '&carLoanCoborrower[' + i + '].isemailmodify=' + isemailmodify;
						paramForm += '&carLoanCoborrower[' + i + '].haveOtherIncome=' + haveOtherIncome;
						paramForm += '&carLoanCoborrower[' + i + '].otherIncome=' + otherIncome;
	   					paramForm += '&carLoanCoborrower[' + i + '].coboHouseHoldProperty=' + coboHouseHoldProperty;
	   					paramForm += '&carLoanCoborrower[' + i + '].houseRent=' + houseRent;
						paramForm += '&carLoanCoborrower[' + i + '].dictHouseholdProvince=' + dictHouseholdProvince;
						paramForm += '&carLoanCoborrower[' + i + '].dictHouseholdCity=' + dictHouseholdCity;
						paramForm += '&carLoanCoborrower[' + i + '].dictHouseholdArea='　+ dictHouseholdArea;
						paramForm += '&carLoanCoborrower[' + i + '].householdAddress=' + encodeURI(householdAddress);

						paramForm += '&carLoanCoborrower[' + i + '].dictLiveProvince=' + dictLiveProvince;
						paramForm += '&carLoanCoborrower[' + i + '].dictLiveCity=' + dictLiveCity;
						paramForm += '&carLoanCoborrower[' + i + '].dictLiveArea=' + dictLiveArea;
						paramForm += '&carLoanCoborrower[' + i + '].nowAddress=' + encodeURI(nowAddress);
						paramForm += '&carLoanCoborrower[' + i + '].loanCode=' + loanCode;
						paramForm += '&carLoanCoborrower[' + i + '].id=' + id;

						var contactNames = $(tables[1]).find("input[mark='contactName']");
						var dictContactRelations = $(tables[1]).find("select[mark='dictContactRelation']");
						var contactUints = $(tables[1]).find("input[mark='contactUint']");
						var dictContactNowAddress = $(tables[1]).find("input[mark='dictContactNowAddress']");
						var compTels = $(tables[1]).find("input[mark='compTel']");
						var contactUnitTels = $(tables[1]).find("input[mark='contactUnitTel']");
						var loanCustomterTypes = $(tables[1]).find("input[mark='loanCustomterType']");
						for (j = 0; j < contactNames.length; j++) {
							paramForm += '&carLoanCoborrower[' + i + '].carCustomerContactPerson[' + j + '].contactName=' + contactNames[j].value;
							paramForm += '&carLoanCoborrower[' + i + '].carCustomerContactPerson[' + j + '].dictContactRelation=' + dictContactRelations[j].value;
							paramForm += '&carLoanCoborrower[' + i + '].carCustomerContactPerson[' + j + '].contactUint=' + contactUints[j].value;
							paramForm += '&carLoanCoborrower[' + i + '].carCustomerContactPerson[' + j + '].dictContactNowAddress=' + dictContactNowAddress[j].value;
							paramForm += '&carLoanCoborrower[' + i + '].carCustomerContactPerson[' + j + '].compTel=' + compTels[j].value;
							paramForm += '&carLoanCoborrower[' + i + '].carCustomerContactPerson[' + j + '].contactUnitTel=' + contactUnitTels[j].value;
							paramForm += '&carLoanCoborrower[' + i + '].carCustomerContactPerson[' + j + '].loanCustomterType' + loanCustomterTypes[j].value;
						}
					}
				}
				var workItemForm = $("#workItemForm").serialize();
				$.ajax({
					url : ctx + "/car/carExtendHistory/carExtendCoborrower",
					type : 'post',
					async : false,
					data : paramForm + "&" + workItemForm + "&delArray=" + delCoboArray,
					dataType : 'text',
					success : function(data) {
						if (data == 't') {
							window.location.href = ctx + "/car/carExtendHistory/toCarLoanFlowBank?" + workItemForm + "&loanCode=" + $("#ipt_loanCode").val() + "&newLoanCode=" + $("#ipt_newLoanCode").val();
						}else {
							art.dialog.alert("失败");
						}
					},
					error : function(data) {
						art.dialog.alert(data);
						return false;
					}
				});
			}
		}
	});

	// 生成历史展期相关
	function makeDataExtend(t) {
		var formTr = $(t).parents("form").find("tr[mark='saveDataMark']");
		var paramExtend = "";
		for (var i = 0; i < formTr.length; i++) {
			paramExtend += '&carContract[' + i + '].id=' + $(formTr[i]).find("input[mark='id']")[0].value;
			paramExtend += '&carContract[' + i + '].loanCode=' + $(formTr[i]).find("input[mark='loanCode']")[0].value;
			paramExtend += '&carContract[' + i + '].contractCode=' + $(formTr[i]).find("input[mark='contractCode']")[0].value;
			paramExtend += '&carContract[' + i + '].loanInfoId=' + $(formTr[i]).find("input[mark='loanInfoId']")[0].value;
			paramExtend += '&carContract[' + i + '].contractAmount=' + $(formTr[i]).find("input[mark='contractAmount']")[0].value;
			var contractMonth = $(formTr[i]).find("input[mark='contractMonths']")[0] == undefined ? $(formTr[i]).find("select[mark='contractMonths']")[0].value : $(formTr[i]).find("input[mark='contractMonths']")[0].value;
			paramExtend += '&carContract[' + i + '].contractMonths=' + contractMonth;
			paramExtend += '&carContract[' + i + '].contractFactDay=' + $(formTr[i]).find("input[mark='contractFactDay']")[0].value;
			paramExtend += '&carContract[' + i + '].contractEndDay=' + $(formTr[i]).find("input[mark='contractEndDay']")[0].value;
			paramExtend += '&carContract[' + i + '].numCount=' + $(formTr[i]).find("input[mark='numCount']")[0].value;
			paramExtend += '&carContract[' + i + '].derate=' + $(formTr[i]).find("input[mark='derate']")[0].value;
		}
		return paramExtend;
	};
	function commonContractDate() {
		var factDay = $("#carFactDayChange").val();
		var month = $("#carMonthChange option:selected").val();
		if (month == '' || month == '30') {
			sumCount = 5;
		} else if (month == '90') {
			sumCount = 3;
			$("#contactAreaExtend tr:gt(3)").each(function(i,e) {
				e.remove();
			});
			if (extendCount > 3) {
				extendCount = 3;
			}
		}
		if (factDay != '' && month != '') {
			month = parseInt(month);
			$($("#contactAreaExtend tr:eq(1)")[0]).find("input[mark='contractEndDay']")[0].value = addDate(factDay, month - 1);
			var firstEndDay = $($("#contactAreaExtend tr:eq(1)")[0]).find("input[mark='contractEndDay']")[0].value;
			$("#contactAreaExtend tr:gt(1)").each(function(i, e) {
				$("#contactAreaExtend tr:gt(" + (i + 1) + ")").find("input[mark='contractFactDay']")[0].value = addDate(firstEndDay, 1 * (i + 1) + (29 * i));
				$("#contactAreaExtend tr:gt(" + (i + 1) + ")").find("input[mark='contractEndDay']")[0].value = addDate(firstEndDay, 1 * (i + 1) + 29 * (i + 1));
			});
		} else {
			$($("#contactAreaExtend tr:eq(1)")[0]).find("input[mark='contractEndDay']")[0].value = null;
			$("#contactAreaExtend tr:gt(1)").each(function(i, e) {
				$("#contactAreaExtend tr:gt(" + (i + 1) + ")").find("input[mark='contractFactDay']")[0].value = null;
				$("#contactAreaExtend tr:gt(" + (i + 1) + ")").find("input[mark='contractEndDay']")[0].value = null;
			});
		}
	};
	$("#carMonthChange").change(function () {
		commonContractDate();
	});
	$("#carFactDayChange").blur(function () {
		commonContractDate();
	});
});
//添加共借人联系人
function addContractPersonClick(self) {
	$("#myConfirmCopyg tr").cloneSelect2(true).appendTo($(self).parent().next("table"));
	$(self).parent().next("table").find("tr:last").find("input[mark='contactName']").prop("name", "contactNamep" + countAdd);
	$(self).parent().next("table").find("tr:last").find("input[mark='compTel']").prop("name", "compTelp" + countAdd);
	$(self).parent().next("table").find("tr:last").find("input[mark='contactUnitTel']").prop("name", "contactUnitTelp" + countAdd);
	countAdd++;
};
// 删除历史展期补录共借人
function delHisCoborrower(self) {
	$(self).parents("div:first").remove();
	count--;
	delCoboArray.push($($(self).parents("div:first").find("input[mark='id']")[0]).attr("value"));
};
// 删除最后一行历史展期
function delContact(self) {
	$(self).parents("tr:first").remove();
};
// 合同金额blur事件
function changeTrDerate(self) {
	var preValObj = $(self).parent().parent().prev("tr").find("input[mark='contractAmount']")[0];
	var thisValStr = $(self).val();
	if (thisValStr != '' && isNaN(thisValStr)) {
		$(self).val('');
		thisValStr = '';
	}
	if (preValObj == undefined) { // 若前一条不存在
		var inde = $(self).parent().parent().index();
		if (thisValStr == '') { // 本次值为''，则把本次之后的合同金额和降额置为空 后，返回
			$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='derate']").each(function(i, e) {
				e.value = null;
			});
			$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='contractAmount']").each(function(i, e) {
				e.value = null;
			});
			return false;
		} else { // 本次值不为''
			var nextValObj = $("#contactAreaExtend tr:eq(" + (inde + 1) + ")").find("input[mark='contractAmount']")[0];
			if (nextValObj == undefined) { // 若下一条不存在，则直接返回
				return false;
			} else { // 若下一条存在
				var nextValStr = nextValObj.value;
				if (nextValStr != '' && parseFloat(nextValStr) <= parseFloat(thisValStr)) {
					// 递归改变降额
					changeTrDerate($("#contactAreaExtend tr:eq(" + (inde + 1) + ")").find("input[mark='contractAmount']")[0]);
				} else {
					$("#contactAreaExtend tr:gt(" + (inde + 1) + ")").find("input[mark='derate']").each(function(i, e) {
						e.value = null;
					});
					$("#contactAreaExtend tr:gt(" + (inde + 1) + ")").find("input[mark='contractAmount']").each(function(i, e) {
						e.value = null;
					});
					return false;
				}
			}
		}
	} else { // 若存在前一条
		if (thisValStr == '') {
			$(self).parent().siblings().find("input[mark='derate']")[0].value = null;
			var inde = $(self).parent().parent().index();
			$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='derate']").each(function(i, e) {
				e.value = null;
			});
			$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='contractAmount']").each(function(i, e) {
				e.value = null;
			});
			return false;
		} else {
			var preValStr = preValObj.value;
			if (preValStr == '') {
				alert("请先填写上次展期合同金额");
				var inde = $(self).parent().parent().index() - 1;
				$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='derate']").each(function(i, e) {
					e.value = null;
				});
				$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='contractAmount']").each(function(i, e) {
					e.value = null;
				});
				return false;
			} else {
				if (thisValStr != '') {
					var thisVal = parseFloat(thisValStr);
					var preVal = parseFloat(preValStr);
					var inde = $(self).parent().parent().index() - 1;
					if (thisVal > preVal) {
						alert("展期合同金额不应该大于前次合同金额");
						$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='derate']").each(function(i, e) {
							e.value = null;
						});
						$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='contractAmount']").each(function(i, e) {
							e.value = null;
						});
						return false;
					} else {
						$(self).parent().siblings().find("input[mark='derate']")[0].value = (preVal - thisVal).toFixed(2);
						// 递归改变降额
						changeTrDerate($("#contactAreaExtend tr:eq(" + (inde + 2) + ")").find("input[mark='contractAmount']")[0]);
					}
				} else {
					var inde = $(self).parent().parent().index() - 1;
					$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='derate']").each(function(i, e) {
						e.value = null;
					});
					$("#contactAreaExtend tr:gt(" + inde + ")").find("input[mark='contractAmount']").each(function(i, e) {
						e.value = null;
					});
					return false;
				}
			}
		}
	}
};
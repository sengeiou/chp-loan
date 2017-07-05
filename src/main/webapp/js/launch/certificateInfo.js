var personalCertificate = {}

personalCertificate.init = function(){
	personalCertificate.initCustomerRelMaster();
	personalCertificate.initEducationalCertificateType();
	personalCertificate.initPersonalCertificateChange();
	
	
}
	
personalCertificate.initPersonalCertificateChange = function(){
	//申请人与户主关系选择数据后，设置必填项
	$("#personalCertificate").bind("change", function(){
		var value = $("#personalCertificate").val();
		var dictmarry = $("#dictMarry").val();
		if(value == 0){
			$("#customerRelMasterRemark").removeClass("required");
			$("#customerRelMasterRemark").hide();
			$("#masterName").val($("#certCustomerName").val());
			$("#masterCertNum").val($("#customerCertNum").val());
			$("#masterCertNum").attr("readonly", "readonly");
		}else if(value == 4){
			$("#customerRelMasterRemark").addClass("required");
			$("#customerRelMasterRemark").show();
			$("#masterName").val("");
			$("#masterCertNum").val("");
			$("#masterCertNum").removeAttr("readonly");
		}else if(value == 5){
			$("#masterName").removeClass("required");
			$("#masterName").siblings(".lab").children(".red").hide();
			$("#masterCertNum").removeClass("required");
			$("#masterCertNum").siblings(".lab").children(".red").hide();
			$("select[name$='masterAddressProvince']").removeClass("required");
			$("select[name$='masterAddressCity']").removeClass("required");
			$("select[name$='masterAddressArea']").removeClass("required");
			$("input[name$='masterAddress']").removeClass("required");
			$("input[name$='masterAddress']").siblings(".lab").children(".red").hide();
			$("#customerRelMasterRemark").removeClass("required");
			$("#customerRelMasterRemark").hide();
		}else{
			$("#customerRelMasterRemark").removeClass("required");
			$("#customerRelMasterRemark").hide();
			$("#masterName").val("");
			$("#masterCertNum").val("");
			$("#masterCertNum").removeAttr("readonly");
		}
		
		if(value!="" && value != 5){
			$("#masterName").addClass("required");
			$("#masterName").siblings(".lab").children(".red").show();
			$("#masterCertNum").addClass("required");
			$("#masterCertNum").siblings(".lab").children(".red").show();
		}else if(value==""){
			$("#masterName").removeClass("required");
			$("#masterName").siblings(".lab").children(".red").hide();
			$("#masterCertNum").removeClass("required");
			$("#masterCertNum").siblings(".lab").children(".red").hide();
			$("#masterName").val("");
			$("#masterCertNum").val("");
		}
	});
	//学历证书类型选择数据后，设置必填项
	$("#personalCertificateType").bind("change", function(){
		personalCertificate.initEducationalCertificateType();
	});
}	
	
personalCertificate.initCustomerRelMaster = function(){
	var value = $("#personalCertificate").val();
	
	if(value == 0){
		$("#customerRelMasterRemark").removeClass("required");
		$("#customerRelMasterRemark").hide();
		$("#customerCertNum").attr("readonly", "readonly");
	}else if(value == 4){
		$("#customerRelMasterRemark").addClass("required");
		$("#customerRelMasterRemark").show();
		$("#customerCertNum").removeAttr("readonly");
	}else if(value == 5){
		$("#masterName").removeClass("required");
		$("#masterName").siblings(".lab").children(".red").hide();
		$("#masterCertNum").removeClass("required");
		$("#masterCertNum").siblings(".lab").children(".red").hide();
		$("select[name$='masterAddressProvince']").removeClass("required");
		$("select[name$='masterAddressCity']").removeClass("required");
		$("select[name$='masterAddressArea']").removeClass("required");
		$("input[name$='masterAddress']").removeClass("required");
		$("input[name$='masterAddress']").siblings(".lab").children(".red").hide();
		$("#customerRelMasterRemark").removeClass("required");
		$("#customerRelMasterRemark").hide();
	}else{
		$("#customerRelMasterRemark").removeClass("required");
		$("#customerRelMasterRemark").hide();
		$("#customerCertNum").removeAttr("readonly");
	}
	if(value!="" && value != 5){
		$("#masterName").addClass("required");
		$("#masterName").siblings(".lab").children(".red").show();
		$("#masterCertNum").addClass("required");
		$("#masterCertNum").siblings(".lab").children(".red").show();
	}else if(value==""){
		$("#masterName").removeClass("required");
		$("#masterName").siblings(".lab").children(".red").hide();
		$("#masterCertNum").removeClass("required");
		$("#masterCertNum").siblings(".lab").children(".red").hide();
		$("#masterName").val("");
		$("#masterCertNum").val("");
	}
}

personalCertificate.initEducationalCertificateType = function(){
	
	var value = $("#personalCertificateType").val();
	if(value != ""){
		$("#graduationSchool").addClass("required");
		$("#educationalCertificateNum").addClass("required");
		$("#educationalCertificateTime").addClass("required");
	}else{
		$("#graduationSchool").removeClass("required");
		$("#educationalCertificateNum").removeClass("required");
		$("#educationalCertificateTime").removeClass("required");
	}
}






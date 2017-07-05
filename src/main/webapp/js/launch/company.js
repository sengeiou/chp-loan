var company = {}

company.init = function(){
	
	loan.initCity("compProvince", "compCity", "compArer");
	areaselect.initCity("compProvince", "compCity", "compArer", $("#compCityHidden").val(),$("#compArerHidden").val());
	
	//主借人产品类型
	var customerProductType = $("#customerProductType").val();
	//薪水借，优房借，优卡借，精英借 月税后工资必填
	if('A003' == customerProductType || 'A008' == customerProductType || 'A007' == customerProductType || 'A014' == customerProductType){
		var compSalary = $("input[name$='compSalary']");
		compSalary.siblings("label").children("span").show();
		compSalary.removeClass("required");
		compSalary.addClass("required");
	}else{
		var compSalary = $("input[name$='compSalary']");
		compSalary.siblings("label").children("span").hide();
		compSalary.removeClass("required");
	}
}
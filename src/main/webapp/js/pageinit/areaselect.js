var areaselect = {};
var areaselectCard = {};

/**
 * 省市选择
 */
areaselectCard.initCity = function(provinceId, cityId, districtId, cityCode, distCode) {
	var provinceCode = $("#" + provinceId + " option:selected").val();
	if (provinceCode == "-1" || provinceCode == "") {
		$("#" + cityId).empty();
		$("#" + cityId).append("<option value=''>请选择</option>");
		$("#" + cityId).trigger("change");
		$("#" + districtId).empty();
		$("#" + districtId).append("<option value=''>请选择</option>");
		$("#" + districtId).trigger("change");
	} else {
		$.ajax({
			type : "POST",
			url : ctx + "/common/cityinfo/asynLoadCityCmb",
			data : {
				provinceId : provinceCode
			},
			async: false,
			success : function(data) {
				var resultObj = eval("(" + data + ")");
				$("#" + cityId).empty();
				$("#" + cityId).append("<option value=''>请选择</option>");
				$.each(resultObj, function(i, item) {
					if (cityCode == item.areaCode) {
						$("#" + cityId).append("<option selected='selected' value=" + item.areaCode+ ">" + item.areaName + "</option>");
					} else {
						$("#" + cityId).append("<option value=" + item.areaCode + ">" + item.areaName+ "</option>");
					}
				});
				$("#" + cityId).trigger("change");
				$("#" + cityId).attr("disabled", false);
			}
		});
	}
	if (districtId != null && districtId != '' && districtId != undefined) {
		/*var cityCode = $("#" + cityId + " option:selected").val();*/
		if (cityCode == "-1" || cityCode == ""|| cityCode == undefined) {
			$("#" + districtId).empty();
			$("#" + districtId).append("<option value=''>请选择</option>");
			$("#" + districtId).trigger("change");
		} else {
			$("#" + districtId).empty();
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadDistrict",
				data : {
					cityId : cityCode
				},
				async: false,
				success : function(data) {
					var resultObj = eval("(" + data+ ")");
					$("#" + districtId).empty();
					$("#" + districtId).append("<option value=''>请选择</option>");
					$.each(resultObj,function(i,item) {
						       	if (distCode == item.areaCode) {
							    	$("#"+ districtId).append("<option selected='selected' value="+ item.areaCode+ ">"+ item.areaName+ "</option>");
								} else {
									$("#"+ districtId).append("<option value="+ item.areaCode+ ">"+ item.areaName+ "</option>");
								}

							});
					$("#" + districtId).trigger("change");
					$("#" + districtId).attr("disabled", false);
				}
			});
		}		 
	}
};

/**
 * 金账户省市选择初始化
 */
areaselect.initKingBankCity = function(provinceId, cityId,areaType,cityCode) {
	var provinceCode = $("#" + provinceId + " option:selected").val();
	if (provinceCode == "-1" || provinceCode == "") {
		$("#" + cityId).empty();
		$("#" + cityId).append("<option value=''>请选择</option>");
		$("#" + cityId).trigger("change");
	} else {
		$.ajax({
			type : "POST",
			url : ctx + "/common/fyAreaCode/asynGetFyAreaCode",
			data : {
				'parentId':provinceCode,
				'areaType':areaType 
			},
			async: false,
	    	success : function(data) {
				var resultObj = eval("(" + data + ")");
				$("#" + cityId).empty();
				$("#" + cityId).append("<option value=''>请选择</option>");
				$.each(resultObj, function(i, item) {
					if (cityCode == item.areaCode) {
						$("#" + cityId).append("<option selected='selected' value=" + item.areaCode+ ">" + item.areaName + "</option>");
					} else {
						$("#" + cityId).append("<option value=" + item.areaCode + ">" + item.areaName+ "</option>");
					}
				});
				$("#" + cityId).trigger("change");
				$("#" + cityId).attr("disabled", false);
			}
		});
	}
};
/**
 * 省市选择
 */
areaselect.initCity = function(provinceId, cityId, districtId, cityCode, distCode) {
	var provinceCode = $("#" + provinceId + " option:selected").val();
	if (provinceCode == "-1" || provinceCode == "") {
		$("#" + cityId).empty();
		$("#" + cityId).append("<option value=''>请选择</option>");
		$("#" + cityId).trigger("change");
		$("#" + districtId).empty();
		$("#" + districtId).append("<option value=''>请选择</option>");
		$("#" + districtId).trigger("change");
	} else {
		$.ajax({
			type : "POST",
			url : ctx + "/common/cityinfo/asynLoadCity",
			data : {
				provinceId : provinceCode
			},
			async: false,
			success : function(data) {
				var resultObj = eval("(" + data + ")");
				$("#" + cityId).empty();
				$("#" + cityId).append("<option value=''>请选择</option>");
				$.each(resultObj, function(i, item) {
					if (cityCode == item.areaCode) {
						$("#" + cityId).append("<option selected='selected' value=" + item.areaCode+ ">" + item.areaName + "</option>");
					} else {
						$("#" + cityId).append("<option value=" + item.areaCode + ">" + item.areaName+ "</option>");
					}
				});
				$("#" + cityId).trigger("change");
				$("#" + cityId).attr("disabled", false);
			}
		});
	}
	if (districtId != null && districtId != '' && districtId != undefined) {
		/*var cityCode = $("#" + cityId + " option:selected").val();*/
		if (cityCode == "-1" || cityCode == ""|| cityCode == undefined) {
			$("#" + districtId).empty();
			$("#" + districtId).append("<option value=''>请选择</option>");
			$("#" + districtId).trigger("change");
		} else {
			$("#" + districtId).empty();
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadDistrict",
				data : {
					cityId : cityCode
				},
				async: false,
				success : function(data) {
					var resultObj = eval("(" + data+ ")");
					$("#" + districtId).empty();
					$("#" + districtId).append("<option value=''>请选择</option>");
					$.each(resultObj,function(i,item) {
						       	if (distCode == item.areaCode) {
							    	$("#"+ districtId).append("<option selected='selected' value="+ item.areaCode+ ">"+ item.areaName+ "</option>");
								} else {
									$("#"+ districtId).append("<option value="+ item.areaCode+ ">"+ item.areaName+ "</option>");
								}

							});
					$("#" + districtId).trigger("change");
					$("#" + districtId).attr("disabled", false);
				}
			});
		}		 
	}
};
areaselect.batchInitCity=function(provinceName,CityName,AreaName){
	if(AreaName!=null){
	  var houseProvs = $("select[name$='"+provinceName+"']");
	  var houseCities = $("select[name$='"+CityName+"']"); 
	  var houseAreas = $("select[name$='"+AreaName+"']");
	  var cityCode=null;
	  var distCode=null;
	  for(var i = 0;i<houseProvs.length;i++){
		  cityCode = $('#'+CityName+"Hidden_"+i).val();
		  distCode = $('#'+AreaName+"Hidden_"+i).val();
		  areaselect.initCity(houseProvs[i].id, houseCities[i].id,houseAreas[i].id, cityCode, distCode);
		 
	  }
	  return true;
	}else{
		 var houseProvs = $("select[name$='"+provinceName+"']");
		 var houseCities = $("select[name$='"+CityName+"']"); 
		 var cityCode=null;
		 for(var i = 0;i<houseProvs.length;i++){
			 cityCode = $('#'+CityName+"Hidden_"+i).val();
			 areaselect.initCity(houseProvs[i].id, houseCities[i].id,null,cityCode,null);
		  }
		 return true;
	}
};

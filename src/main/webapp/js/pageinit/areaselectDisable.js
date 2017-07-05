var areaselectCardDisabled = {};


/**
 * 省市选择
 */
areaselectCardDisabled.initCity = function(provinceId, cityId, districtId, cityCode, distCode) {
	var provinceCode = $("#" + provinceId + " option:selected").val();
	$("#" + provinceId).attr("disabled", true);
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
				$("#" + cityId).attr("disabled", true);
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
					$("#" + districtId).attr("disabled", true);
				}
			});
		}		 
	}
};

// 初始化方法

$(function(){
	
	// 返回按钮
	$("#backBtn").click(function(){
		
		window.history.go(-1);
	});	
	
	$("select[str='province']").change(function(){
		$("#select2-drop").css("style","bottom: 500px;!important;");
		var provinceId = $(this).find("option:selected").val();
		var element = $(this).parent("td").parent("tr").next("tr").find("td select");
		if( provinceId == "" ){
			element.empty();
			element.append("<option value='' selected=true>请选择</option>");
			element.trigger("change");
		}else{
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadCity",
				data: {provinceId: provinceId},	
				async: false,
				success : function(data) {
					//var cityName =  $("select[str='province']").data("cityName");
					var resultObj = eval("("+data+")");
					element.empty();
					element.append("<option value=''>请选择</option>");
					var message ="";
					$.each(resultObj,function(i,item){
						message += "<option value="+item.id+">"+item.areaName+"</option>";
					});
					
					element.append(message);
					element.trigger("change");
					element.attr("disabled", false);
				}
			});
		}
	});
	
	$("select[str='city']").change(function(){
		var cityId = $(this).find("option:selected").val();
		var element = $(this).parent("td").parent("tr").next("tr").find("td select");
		if( cityId == "" ){
			element.empty();
			element.append("<option value='' selected=true>请选择</option>");
			element.trigger("change");
		}else{
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadDistrict",
				data: {cityId: cityId},	
				async: false,
				success : function(data) {
					//var areaName =  $("select[str='city']").data("areaName");
					var resultObj = eval("("+data+")");
					element.empty();
					element.append("<option value=''>请选择</option>");
					var message ="";
					$.each(resultObj,function(i,item){
						/*if(areaName == item.id){
							message += "<option selected value="+item.id+">"+item.name+"</option>";
						}else{*/
						message += "<option value="+item.id+">"+item.areaName+"</option>";
						//}
					});
					element.append(message);
					element.trigger("change");
					element.attr("disabled", false);
				}
			});
		}
	});
	
	$("select[str='provinceTD']").change(function(){
		var provinceId = $(this).find("option:selected").val();
		var element = $(this).parent("td").next("td").find("select");
		if( provinceId == "" ){
			element.empty();
			element.append("<option value='' selected=true>请选择</option>");
			element.trigger("change");
		}else{
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadCity",
				data: {provinceId: provinceId},
				async: false,
				success : function(data) {
					var resultObj = eval("("+data+")");
					element.empty();
					element.append("<option value=''>请选择</option>");
					var message ="";
					$.each(resultObj,function(i,item){
						message += "<option value="+item.id+">"+item.areaName+"</option>";
					});
					element.append(message);
					element.trigger("change");
					element.attr("disabled", false);
				}
			});
		}
	});
	
	$("select[str='cityTD']").change(function(){
		var cityId = $(this).find("option:selected").val();
		var element = $(this).parent("td").next("td").find("select");
		if( cityId == "" ){
			element.empty();
			element.append("<option value='' selected=true>请选择</option>");
			element.trigger("change");
		}else{
			$.ajax({
				type : "POST",
				url : ctx + "/common/cityinfo/asynLoadDistrict",
				data: {cityId: cityId},
				async: false,
				success : function(data) {
					var resultObj = eval("("+data+")");
					element.empty();
					element.append("<option value=''>请选择</option>");
					var message ="";
					$.each(resultObj,function(i,item){
						message += "<option value="+item.id+">"+item.areaName+"</option>";
					});
					element.append(message);
					element.trigger("change");
					element.attr("disabled", false);
				}
			});
		}
	});
	
	// 初始化数据
	initData();
});

// 初始化数据
function initData(){
	$(window.parent.document).find("input[name='isSave']").val("0");
	var data = $(window.parent.document).find("form[id='param']").serialize();
	var applyCertNum = $(window.parent.document).find("form[id='param']").find("input[name='applyCertNum']").val();
	
	$("#detailedInfo").find("input[name='certNo']").val(applyCertNum);
	$("#detailedInfo").find("input[name='certNo']").attr("readonly","readonly");
	
	$.ajax({
		url:ctx+'/creditdetailed/info/showData',
		data:data,
		type: "post",
		dataType:'json',
		success:function(data){
			if(data != null){
				if(data.id != null && data.id != ""){
					$(window.parent.document).find("input[name='isSave']").val("1");
				}
				$("#detailedInfo").find("input[name='creditCode']").val(data.creditCode);// 报告编号
				$("#detailedInfo").find("input[name='queryTime']").val(formatTime(data.queryTime));// 查询时间
				$("#detailedInfo").find("input[name='reportTime']").val(formatTime(data.reportTime));// 报告时间
				$("#detailedInfo").find("input[name='id']").val(data.id);//隐藏项-ID
				$("#detailedInfo").find("input[name='name']").val(data.name);// 姓名
				$("#detailedInfo").find("select[name='sex']").select2("val",data.sex);// 性别
				if(data.certType == '' || data.certType == null){
					$("#detailedInfo").find("select[name='certType']").select2("val","0");// 证件类型
				}else{
					$("#detailedInfo").find("select[name='certType']").select2("val",data.certType);
				}
				//$("#detailedInfo").find("input[name='certNo']").val(data.certNo);// 证件号码
				$("#detailedInfo").find("input[name='birthday']").val(formatTime(data.birthday));// 出生日期
				$("#detailedInfo").find("select[name='highestEducation']").select2("val",data.highestEducation);// 最高学历
				$("#detailedInfo").find("select[name='contactAddProvince']").select2("val",data.contactAddProvince);// 通讯省 								
				$("#detailedInfo").find("select[name='contactAddProvince']").trigger("change");
				$("#detailedInfo").find("select[name='contactAddCity']").select2("val",data.contactAddCity);// 通讯市 								
				$("#detailedInfo").find("select[name='contactAddCity']").trigger("change");
				$("#detailedInfo").find("select[name='contactAddArea']").select2("val",data.contactAddArea);// 通讯省 								
				$("#detailedInfo").find("input[name='contactAddress']").val(data.contactAddress);// 通讯详细地址
				$("#detailedInfo").find("input[name='zipCode']").val(data.zipCode);// 邮政编码
				$("#detailedInfo").find("select[name='nativeAddProvince']").select2("val",data.nativeAddProvince);// 户籍省 								
				$("#detailedInfo").find("select[name='nativeAddProvince']").trigger("change");
				$("#detailedInfo").find("select[name='nativeAddCity']").select2("val",data.nativeAddCity);// 户籍市								
				$("#detailedInfo").find("select[name='nativeAddCity']").trigger("change");
				$("#detailedInfo").find("select[name='nativeAddArea']").select2("val",data.nativeAddArea);// 户籍区								
				$("#detailedInfo").find("input[name='nativeAddress']").val(data.nativeAddress);// 户籍详细地址
				$("#detailedInfo").find("input[name='homePhone']").val(data.homePhone);// 住宅电话
				$("#detailedInfo").find("input[name='unitPhone']").val(data.unitPhone);// 单位电话
				$("#detailedInfo").find("input[name='mobilePhone']").val(data.mobilePhone);// 手机号码
				$("#detailedInfo").find("select[name='marryStatus']").select2("val",data.marryStatus);//婚姻状态
				$("#detailedInfo").find("input[name='mateName']").val(data.mateName);// 配偶姓名
				$("#detailedInfo").find("input[name='matePhone']").val(data.matePhone);// 配偶联系电话
				$("#detailedInfo").find("select[name='mateCertType']").select2("val",data.mateCertType);// 配偶证件类型
				$("#detailedInfo").find("input[name='mateCertNo']").val(data.mateCertNo);// 配偶姓名
				showHouse(data.liveList)// 居住信息
				showWork(data.occupationList);// 显示职业信息
			}
		}
	});
}

// 显示居住信息
function showHouse(house){
	$("#houseInfo").find("tbody").html("");
	// 如果有数据
	if(house != null && typeof(house) != "undefined"){
		for( var i = 0; i < house.length; i++ ){
			var target = $("#houseInfo");
			$("#houseHid tbody tr").find("input[name='id']").parent("td").parent("tr").attr("id","");
			$("#houseHid tbody tr").find("input[name='id']").parent("td").parent("tr").attr("id",house[i].id);
			$("#houseHid tbody tr").find("a").attr("onClick","removeHouse('')");
			$("#houseHid tbody tr").find("a").attr("onClick","removeHouse('"+house[i].id+"')");
			var temp = $("#houseHid tbody tr");
			var htmInfo=temp.cloneSelect2(true,true);
			htmInfo.appendTo($(target));
			$("#houseInfo").find("#"+house[i].id).find("input[name='id']").val(house[i].id);
			$("#houseInfo").find("#"+house[i].id).find("select[name='liveProvince']").select2("val",house[i].liveProvince)
			$("#houseInfo").find("#"+house[i].id).find("select[name='liveProvince']").trigger("change");
			$("#houseInfo").find("#"+house[i].id).find("select[name='liveCity']").select2("val",house[i].liveCity);
			$("#houseInfo").find("#"+house[i].id).find("select[name='liveCity']").trigger("change");
			$("#houseInfo").find("#"+house[i].id).find("select[name='liveArea']").select2("val",house[i].liveArea);
			$("#houseInfo").find("#"+house[i].id).find("select[name='liveArea']").trigger("change");
			$("#houseInfo").find("#"+house[i].id).find("input[name='liveAddress']").val(house[i].liveAddress);
			$("#houseInfo").find("#"+house[i].id).find("input[name='liveSituation']").val(house[i].liveSituation);
			$("#houseInfo").find("#"+house[i].id).find("input[name='getinfoTime']").val(formatTime(house[i].getinfoTime));
		}
	}
}

// 显示职业信息
function showWork(work){
	$("#workOne").find("tbody").html("");
	$("#workTwo").find("tbody").html("");
	
	if( work != null ){
		for(var i = 0 ; i < work.length; i++ ){
			// 拼接职业表格1
			var targetOne = $("#workOne");
			$("#workOneHid tbody tr").find("input[name='id']").parent("td").parent("tr").attr("id","");
			$("#workOneHid tbody tr").find("input[name='id']").parent("td").parent("tr").attr("id","One"+work[i].id);
			$("#workOneHid tbody tr").find("a").attr("onClick","removeWork('')");
			$("#workOneHid tbody tr").find("a").attr("onClick","removeWork('"+work[i].id+"')");
			var tempOne = $("#workOneHid tbody tr");
			var htmInfoOne = tempOne.cloneSelect2(true,true);
			htmInfoOne.appendTo($(targetOne));
			$("#workOne").find("#One"+work[i].id).find("input[name='id']").val(work[i].id);
			$("#workOne").find("#One"+work[i].id).find("input[name='unitName']").val(work[i].unitName);
			$("#workOne").find("#One"+work[i].id).find("select[name='unitProvince']").select2("val",work[i].unitProvince);
			$("#workOne").find("#One"+work[i].id).find("select[name='unitProvince']").trigger("change");
			$("#workOne").find("#One"+work[i].id).find("select[name='unitCity']").select2("val",work[i].unitCity);
			$("#workOne").find("#One"+work[i].id).find("select[name='unitCity']").trigger("change");
			$("#workOne").find("#One"+work[i].id).find("select[name='unitArea']").select2("val",work[i].unitArea);
			$("#workOne").find("#One"+work[i].id).find("input[name='unitAddress']").val(work[i].unitAddress);
			$("#workOne").find("#One"+work[i].id).find("input[name='unitIndustry']").val(work[i].unitIndustry);
			
			// 拼接职业表格2
			var targetTwo = $("#workTwo");
			$("#workTwoHid tbody tr")
			$("#workTwoHid tbody tr").find("input[name='id']").parent("td").parent("tr").attr("id","");
			$("#workTwoHid tbody tr").find("input[name='id']").parent("td").parent("tr").attr("id","Two"+work[i].id);
			var tempTwo = $("#workTwoHid tbody tr");
			var htmInfoTwo = tempTwo.cloneSelect2(true,true);
			htmInfoTwo.appendTo($(targetTwo));
			$("#workTwo").find("#Two"+work[i].id).find("input[name='id']").val(work[i].id);
			$("#workTwo").find("#Two"+work[i].id).find("input[name='occupation']").val(work[i].occupation);
			$("#workTwo").find("#Two"+work[i].id).find("input[name='duties']").val(work[i].duties);
			$("#workTwo").find("#Two"+work[i].id).find("input[name='title']").val(work[i].title);
			$("#workTwo").find("#Two"+work[i].id).find("input[name='annualIncome']").val(work[i].annualIncome);
			$("#workTwo").find("#Two"+work[i].id).find("input[name='startingYear']").val(work[i].startingYear);
			$("#workTwo").find("#Two"+work[i].id).find("input[name='getinfoTime']").val(formatTime(work[i].getinfoTime));
		}
	}
	// 显示编号
	sort();
}

// 格式化时间为 YYYY-MM-DD
function formatTime( param ){
	var result = "";
	if( param != null && typeof(param) != "undefined"){
		var tim = new Date(param);
		var month = (tim.getMonth()+1);
		if(tim.getMonth() < 9){
			month="0"+(tim.getMonth()+1);
		}
		var day = tim.getDate();
		if(tim.getDate() < 10){
			day="0"+tim.getDate()
		}
		result = tim.getFullYear()+"-"+month+"-"+day;
	}
	return result;
}

// 添加居住信息
function addHouse(){
	var target = $("#houseInfo");
	var temp = $("#houseHid tbody tr");
	var htmInfo=temp.cloneSelect2(true,true);
	var randomNum = getRandomNum();
	htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id",randomNum);
	htmInfo.find("a").attr("onClick","removeHouse('"+randomNum+"')");
	htmInfo.appendTo($(target));
}

// 删除居住信息
function removeHouse(id){
	if(id != "" && id.length > 20){
		$.ajax({
			url:ctx+'/creditdetailed/info/removeReportHouse',
			data:"id="+id,
			type: "post",
			dataType:'json',
			success:function(data){
				if(data){
					$("#houseInfo").find("#"+id).remove();
					art.dialog.tips('删除成功!');
				}else{
					art.dialog.tips('删除失败!');
				}
			}
		});
	}else{
		$("#houseInfo").find("#"+id).remove();
	}
}

// 添加职业信息
function addWork(){
	// 拼接职业表格1
	var targetOne = $("#workOne");
	var tempOne = $("#workOneHid tbody tr");
	var htmInfoOne = tempOne.cloneSelect2(true,true);
	var randomNum = getRandomNum();
	htmInfoOne.find("input[name='id']").parent("td").parent("tr").attr("id","One"+randomNum);
	htmInfoOne.find("a").attr("onClick","removeWork('"+randomNum+"')");
	htmInfoOne.appendTo($(targetOne));
	// 拼接职业表格2
	var targetTwo = $("#workTwo");
	var tempTwo = $("#workTwoHid tbody tr");
	var htmInfoTwo = tempTwo.cloneSelect2(true,true);
	htmInfoTwo.find("input[name='id']").parent("td").parent("tr").attr("id","Two"+randomNum);
	htmInfoTwo.appendTo($(targetTwo));
	// 显示编号
	sort();
}

// 删除职位信息
function removeWork(id){
	if(id != "" && id.length > 20){
		$.ajax({
			url:ctx+'/creditdetailed/info/removeReportWork',
			data:"id="+id,
			type: "post",
			dataType:'json',
			success:function(data){
				if(data){
					$("#workOne").find("#One"+id).remove();
					$("#workTwo").find("#Two"+id).remove();
					// 显示编号
					sort();
					art.dialog.tips('删除成功!');
				}else{
					art.dialog.tips('删除失败!');
				}
			}
		});
	}else{
		$("#workOne").find("#One"+id).remove();
		$("#workTwo").find("#Two"+id).remove();
		sort();
	}
}

//显示编号
function sort(){
	// 循环公积金信息(进行排序)
	var allInfoNum = $("#workOne").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
	});
	// 循环期数(进行排序)
	var allPeriodsNum = $("#workTwo").find("tbody").find("input[name='num']");
	allPeriodsNum.each(function(n,item){
		$(this).val(n+1);
	});
}

// 保存数据
function saveData(){
	//验证表单
	if (!checkForm($("#formHouseWork"))) {
		return;
	}
	//验证表单
	if (!checkForm($("#detailedInfo"))) {
		return;
	}
	
	var timeFlg = true;
	// 时间限制当前时间之前
	$(".Wdate").each(function(){
		var start=new Date($(this).val()); 
		var now = new Date();
		var year = now.getFullYear(); //获取完整的年份(4位,1970-????)
		var month = now.getMonth()+1; //获取当前月份(0-11,0代表1月)
		if(month < 10){
			month = "0" + month;
		}
		var day = now.getDate(); // 获取日
		if(day < 10){
			day = "0" + day;
		}
		var end = new Date(year+"-"+month+"-"+day);
		if(end < start){
			addMyValidateTip( $(this),"时间要小于当前日期" )
			timeFlg = false;  
		}
	});
	if(!timeFlg){
		return;
	}
	// 基本信息
	var param = $("#detailedInfo").serialize();
	param = param + encodeURI(makeParam());
	var data = param +"&"+$(window.parent.document).find("form[id='param']").serialize();
	
	if($("#saveLock").val() != '1'){
		$("#saveLock").val("1");
		$.jBox.tip("正在保存...", 'loading',{opacity:0.1,persistent:true});
		$.ajax({
			url:ctx+'/creditdetailed/info/saveData',
			data:data,
			type: "post",
			dataType:'json',
			async: false,
			success:function(data){
				$.jBox.closeTip();
				if(data){
					initData();
					art.dialog.tips('保存成功!');
				}else{
					art.dialog.tips("保存失败！");
				}
				$("#saveLock").val("0");
			},
			error:function(){
				$.jBox.closeTip();
				art.dialog.alert('服务器异常！');
				return false;
			}
		});
		
	}
	
	
	
	
}

//做好保存参数
function makeParam(){
	var param = "";
	// 居住信息
	var houseId = $("#houseInfo").find("input[name='id']");
	var liveProvince = $("#houseInfo").find("select[name='liveProvince']");
	var liveCity = $("#houseInfo").find("select[name='liveCity']");
	var liveArea = $("#houseInfo").find("select[name='liveArea']");
	var liveAddress = $("#houseInfo").find("input[name='liveAddress']");
	var liveSituation = $("#houseInfo").find("input[name='liveSituation']");
	var getinfoTime = $("#houseInfo").find("input[name='getinfoTime']");
	
	for( var i = 0; i < houseId.length; i++){
		param+="&liveList["+i+"].id="+$(houseId[i]).val();
		param+="&liveList["+i+"].liveProvince="+$(liveProvince[i]).val();
		param+="&liveList["+i+"].liveCity="+$(liveCity[i]).val();
		param+="&liveList["+i+"].liveArea="+$(liveArea[i]).val();
		param+="&liveList["+i+"].liveAddress="+$(liveAddress[i]).val();
		param+="&liveList["+i+"].liveSituation="+$(liveSituation[i]).val();
		param+="&liveList["+i+"].getinfoTime="+$(getinfoTime[i]).val();
	}
	
	// 职业信息(第一行)
	var workId = $("#workOne").find("input[name='id']");
	var unitName = $("#workOne").find("input[name='unitName']");
	var unitProvince = $("#workOne").find("select[name='unitProvince']");
	var unitCity = $("#workOne").find("select[name='unitCity']");
	var unitArea = $("#workOne").find("select[name='unitArea']");
	var unitAddress = $("#workOne").find("input[name='unitAddress']");
	var unitIndustry = $("#workOne").find("input[name='unitIndustry']");
	// 职业信息（第二行）
	var occupation = $("#workTwo").find("input[name='occupation']");
	var duties = $("#workTwo").find("input[name='duties']");
	var title = $("#workTwo").find("input[name='title']");
	var annualIncome = $("#workTwo").find("input[name='annualIncome']");
	var startingYear = $("#workTwo").find("input[name='startingYear']");
	var getinfoTime = $("#workTwo").find("input[name='getinfoTime']");
	
	for( var w = 0; w < workId.length; w++ ){
		param+="&occupationList["+w+"].id="+$(workId[w]).val();
		param+="&occupationList["+w+"].unitName="+$(unitName[w]).val();
		param+="&occupationList["+w+"].unitProvince="+$(unitProvince[w]).val();
		param+="&occupationList["+w+"].unitCity="+$(unitCity[w]).val();
		param+="&occupationList["+w+"].unitArea="+$(unitArea[w]).val();
		param+="&occupationList["+w+"].unitAddress="+$(unitAddress[w]).val();
		param+="&occupationList["+w+"].unitIndustry="+$(unitIndustry[w]).val();
		param+="&occupationList["+w+"].occupation="+$(occupation[w]).val();
		param+="&occupationList["+w+"].duties="+$(duties[w]).val();
		param+="&occupationList["+w+"].title="+$(title[w]).val();
		param+="&occupationList["+w+"].annualIncome="+$(annualIncome[w]).val();
		param+="&occupationList["+w+"].startingYear="+$(startingYear[w]).val();
		param+="&occupationList["+w+"].getinfoTime="+$(getinfoTime[w]).val();
	}
	return param;
}
// 时间戳加上随机数(15位随机数)
function getRandomNum(){
	var data = new Date().getTime()+"";
	var num = Math.floor(Math.random()*100+1)+"";
	var randomNum = data+num;
	return randomNum;
}

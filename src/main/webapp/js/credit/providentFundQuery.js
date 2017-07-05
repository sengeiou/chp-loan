// 个人住房公积金信息相关操作
$(function(){
	
	// 初始化数据
	initData();
});

// 初始化数据
function initData(){
	$("#info").find("tbody").html("");
	$("#periods").find("tbody").html("");
	var data = $(window.parent.document).find("form[id='param']").serialize();
	$.ajax({
		url:ctx+'/creditdetailed/accumulation/showData',
		data:data,
		type: "post",
		dataType:'json',
		success:function(data){
			if( data != null ){
				// 个人住房公积金信息
				if(data.creditCpfDetailedList != null && data.creditCpfDetailedList[0].id != null){
					for( var i=0; i<data.creditCpfDetailedList.length; i++ ){
						if( data.creditCpfDetailedList[0].id != null ){
							var trInfo = $("#infoHide").find("tbody").html();
							var htmInfo = $(trInfo);
							htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id","infoTr"+data.creditCpfDetailedList[i].id);
							htmInfo.find("input[name='id']").val(data.creditCpfDetailedList[i].id);
							htmInfo.find("input[name='personAccount']").val(data.creditCpfDetailedList[i].personAccount);
							htmInfo.find("input[name='unitName']").val(data.creditCpfDetailedList[i].unitName);
							htmInfo.find("input[name='accountDay']").val(formatTime(data.creditCpfDetailedList[i].accountDay));
							htmInfo.find("input[name='payDay']").val(formatTime(data.creditCpfDetailedList[i].payDay));
							htmInfo.find("input[name='payToDay']").val(formatTime(data.creditCpfDetailedList[i].payToDay));
							htmInfo.find("input[name='payDayNear']").val(formatTime(data.creditCpfDetailedList[i].payDayNear));
							htmInfo.find("input[name='unitRation']").val(data.creditCpfDetailedList[i].unitRation);
							htmInfo.find("input[name='personRation']").val(data.creditCpfDetailedList[i].personRation);
							htmInfo.find("input[name='deposit']").val(data.creditCpfDetailedList[i].deposit);
							htmInfo.find("input[name='getinfoTime']").val(formatTime(data.creditCpfDetailedList[i].getinfoTime));
							htmInfo.find("input[name='deleteName']").attr("onClick","removeData(this,'"+data.creditCpfDetailedList[i].id+"')");
							htmInfo.find("tr").attr("id","infoTr"+data.creditCpfDetailedList[i].id);
							$("#info").find("tbody").append(htmInfo);
							// 显示期数
							showQs(data.creditCycleRecordExList,data.creditCpfDetailedList[i].id);
						}
					}
					// 显示编号
					sort();
				}
			}
		}
	});
}

// 显示期数
function showQs(QSData , id){
	if(QSData != null && QSData.length > 0 && QSData[0].relationId != null){
		for( var s = 0; s < QSData.length; s++  ){
			if(QSData[s].relationId == id){
				// 最近24个月缴交状态
				var trPeriods = $("#periodsHide").find("tbody").html();
				var htmPeriods = $(trPeriods);
				htmPeriods.find("input[name='num']").parent("td").parent("tr").attr("id","periodsTr"+id);
				htmPeriods.find("input[name='qs1']").val(QSData[s].qs1);
				htmPeriods.find("input[name='qs2']").val(QSData[s].qs2);
				htmPeriods.find("input[name='qs3']").val(QSData[s].qs3);
				htmPeriods.find("input[name='qs4']").val(QSData[s].qs4);
				htmPeriods.find("input[name='qs5']").val(QSData[s].qs5);
				htmPeriods.find("input[name='qs6']").val(QSData[s].qs6);
				htmPeriods.find("input[name='qs7']").val(QSData[s].qs7);
				htmPeriods.find("input[name='qs8']").val(QSData[s].qs8);
				htmPeriods.find("input[name='qs9']").val(QSData[s].qs9);
				htmPeriods.find("input[name='qs10']").val(QSData[s].qs10);
				htmPeriods.find("input[name='qs11']").val(QSData[s].qs11);
				htmPeriods.find("input[name='qs12']").val(QSData[s].qs12);
				htmPeriods.find("input[name='qs13']").val(QSData[s].qs13);
				htmPeriods.find("input[name='qs14']").val(QSData[s].qs14);
				htmPeriods.find("input[name='qs15']").val(QSData[s].qs15);
				htmPeriods.find("input[name='qs16']").val(QSData[s].qs16);
				htmPeriods.find("input[name='qs17']").val(QSData[s].qs17);
				htmPeriods.find("input[name='qs18']").val(QSData[s].qs18);
				htmPeriods.find("input[name='qs19']").val(QSData[s].qs19);
				htmPeriods.find("input[name='qs20']").val(QSData[s].qs20);
				htmPeriods.find("input[name='qs21']").val(QSData[s].qs21);
				htmPeriods.find("input[name='qs22']").val(QSData[s].qs22);
				htmPeriods.find("input[name='qs23']").val(QSData[s].qs23);
				htmPeriods.find("input[name='qs24']").val(QSData[s].qs24);
				$("#periods").find("tbody").append(htmPeriods);
			}
		}
	}
}

// 添加公积金信息
function addData(){
	var data = new Date().getTime()+"";
	var num = Math.floor(Math.random()*100+1)+"";
	var randomNum = data+num;
	// 个人住房公积金信息
	var trInfo = $("#infoHide").find("tbody").html();
	var htmInfo = $(trInfo);
	htmInfo.find("input[name='deleteName']").attr("onClick","removeData(this,'"+randomNum+"')");
	$("#info").find("tbody").append(htmInfo);
	// 最近24个月缴交状态
	var trPeriods = $("#periodsHide").find("tbody").html();
	var htmPeriods = $(trPeriods);
	htmPeriods.find("input[name='num']").parent("td").parent("tr").attr("id","periodsTr"+randomNum);
	$("#periods").find("tbody").append(htmPeriods);
	// 显示编号
	sort();
}

// 删除公积金信息
function removeData(t,id){
	if(id != "" && id.length > 20){
		$.ajax({
			url:ctx+'/creditdetailed/accumulation/deleteData',
			data:"id="+id,
			type: "post",
			dataType:'json',
			success:function(data){
				if(data != "0"){
					//alert("删除成功");
					//$("#msg").html("<font size='3px' color='red'>删除成功!</font>").show(300).delay(2000).hide(300);
					art.dialog.tips('删除成功!');
					$(t).parent("td").parent("tr").remove();
					$("#periodsTr"+id).remove();
					// 显示编号
					sort();
				}else{
					//alert("删除失败");
					//$("#msg").html("<font size='3px' color='red'>删除失败!</font>").show(300).delay(2000).hide(300);
					art.dialog.tips('删除成功!');
				}
			}
		});
	}else{
		$(t).parent("td").parent("tr").remove();
		$("#periodsTr"+id).remove();
		// 显示编号
		sort();
	}
}

// 显示编号
function sort(){
	// 循环公积金信息(进行排序)
	var allInfoNum = $("#info").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
	});
	// 循环期数(进行排序)
	var allPeriodsNum = $("#periods").find("tbody").find("input[name='num']");
	allPeriodsNum.each(function(n,item){
		$(this).val(n+1);
	});
}

//格式化时间为 YYYY-MM-DD
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


// 保存数据
function saveData(){
	//验证表单
	if (!checkForm($("#infoForm"))) {
		return;
	}
	//验证表单
	if (!checkForm($("#periodsForm"))) {
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
	
	var param = makeParam();
	var loanCode = $(window.parent.document).find("form[id='param']").find("input[name='loanCode']").val();
	var rCustomerCoborrowerId = $(window.parent.document).find("form[id='param']").find("input[name='rCustomerCoborrowerId']").val();
	var dictCustomerType = $(window.parent.document).find("form[id='param']").find("input[name='dictCustomerType']").val();
	var data = param+"&loanCode="+loanCode+"&type="+dictCustomerType+"&relId="+rCustomerCoborrowerId;
	// 公积金
	$.ajax({
		url:ctx+'/creditdetailed/accumulation/saveData',
		data:encodeURI(data),
		type: "post",
		dataType:'json',
		async: false,
		success:function(data){
			if(data){
				art.dialog.tips('保存成功!');
				initData();
			}else{
				art.dialog.tips('保存失败!');
			}
		}
	});
}

// 做好保存参数
function makeParam(){
	// 个人住房公积金信息
	var personAccount = $("#infoForm").find("input[name='personAccount']"); // 个人账号
	var unitName = $("#infoForm").find("input[name='unitName']"); // 单位名称
	var accountDay = $("#infoForm").find("input[name='accountDay']"); // 开户日期
	var payDay = $("#infoForm").find("input[name='payDay']"); // 初交年月
	var payToDay = $("#infoForm").find("input[name='payToDay']"); // 缴至年月
	var payDayNear = $("#infoForm").find("input[name='payDayNear']"); // 最后一次缴交日期
	var unitRation = $("#infoForm").find("input[name='unitRation']"); // 单位缴存比例
	var personRation = $("#infoForm").find("input[name='personRation']"); // 个人缴存比例
	var deposit = $("#infoForm").find("input[name='deposit']"); // 月缴存额
	var getinfoTime = $("#infoForm").find("input[name='getinfoTime']"); // 信息获取时间
	var id = $("#infoForm").find("input[name='id']"); // 单位名称
	var param = "";
	for(var i = 0; i < personAccount.length; i++ ){
		param+="&creditCpfDetailedList["+i+"].personAccount="+$(personAccount[i]).val();
		param+="&creditCpfDetailedList["+i+"].unitName="+$(unitName[i]).val();
		param+="&creditCpfDetailedList["+i+"].accountDay="+$(accountDay[i]).val();
		param+="&creditCpfDetailedList["+i+"].payDay="+$(payDay[i]).val();
		param+="&creditCpfDetailedList["+i+"].payToDay="+$(payToDay[i]).val();
		param+="&creditCpfDetailedList["+i+"].payDayNear="+$(payDayNear[i]).val();
		param+="&creditCpfDetailedList["+i+"].unitRation="+$(unitRation[i]).val();
		param+="&creditCpfDetailedList["+i+"].personRation="+$(personRation[i]).val();
		param+="&creditCpfDetailedList["+i+"].deposit="+$(deposit[i]).val();
		param+="&creditCpfDetailedList["+i+"].getinfoTime="+$(getinfoTime[i]).val();
		param+="&creditCpfDetailedList["+i+"].id="+$(id[i]).val();
	}
	
	// 拼接期数
	var tArray = new Array();  
	var relationId = $("#periodsForm").find("input[name='relationId']");
	for( var f = 0; f < 24; f++ ){
		tArray[f+1]=new Array();
		for( var g = 0; g < relationId.length; g++ ){
		tArray[f+1][g] = $("#periodsForm").find("input[name=qs"+(f+1)+"]")[g];
		param+= "&creditCycleRecordExList["+g+"].qs"+(f+1)+"="+$(tArray[f+1][g]).val();
		}
	}
	return param;
}
//获得焦点，清除msg
function clearMsg(t){
	$(t).css("border","");
	$(t).css("border-radius","");
	$(t).next(".ketchup-error-container").remove();//.css("display","none")	;
}

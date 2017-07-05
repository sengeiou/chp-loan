// 初始化方法

$(function(){
	// 初始化数据
	initData();
});

// 初始化数据
function initData(){
	$("#queryInfoId").find("tbody").html("");
	var data = $(window.parent.document).find("form[id='param']").serialize();
	// 公积金
	$.ajax({
		url:ctx+'/credit/creditpaybackdetail/showData',
		data:data,
		type: "post",
		dataType:'json',
		success:function(data){
			if(data != null){
				for( var i = 0; i < data.length; i++ ){
					if(data[i].id != null){
						var target = $("#queryInfoId");
						var temp = $("#model tbody tr");
						var htmInfo=temp.cloneSelect2(true,true);
						htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id",data[i].id);
						htmInfo.find("input[name='recentlyPaybackTime']").val(formatTime(data[i].recentlyPaybackTime));
						htmInfo.find("input[name='paybackOrg']").val(data[i].paybackOrg);
						htmInfo.find("input[name='totalPaybackAmount']").val(data[i].totalPaybackAmount);
						htmInfo.find("input[name='lastPaybackDate']").val(formatTime(data[i].lastPaybackDate));
						htmInfo.find("input[name='residualAmount']").val(data[i].residualAmount);
						htmInfo.find("input[name='id']").val(data[i].id);
						htmInfo.find("a").attr("onClick","removeData('"+data[i].id+"')");
						htmInfo.appendTo($(target));
					}
				}
			}
		}
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

// 添加数据
function addData(){
	var target = $("#queryInfoId");
	var temp = $("#model tbody tr");
	var htmInfo=temp.cloneSelect2(true,true);
	var data = new Date().getTime()+"";
	var num = Math.floor(Math.random()*100+1)+"";
	var randomNum = data+num;
	htmInfo.find("input[name='id']").parent("td").parent("tr").attr("id",randomNum);
	htmInfo.find("a").attr("onClick","removeData('"+randomNum+"')");
	htmInfo.appendTo($(target));
}

// 删除数据
function removeData( id ){
	
	if(id != "" && id.length > 20){
		$.ajax({
			url:ctx+'/credit/creditpaybackdetail/deleteData',
			data:"id="+id,
			type: "post",
			dataType:'json',
			success:function(data){
				if(data){
					art.dialog.tips('删除成功!');
					//initData();
					$("#"+id).remove();
				}else{
					art.dialog.tips('删除失败!');
				}
			}
		});
	}else{
		$("#"+id).remove();
	}
}

//保存数据
function saveData(){
	var param = makeParam();
	var loanCode = $(window.parent.document).find("form[id='param']").find("input[name='loanCode']").val();
	var rCustomerCoborrowerId = $(window.parent.document).find("form[id='param']").find("input[name='rCustomerCoborrowerId']").val();
	var dictCustomerType = $(window.parent.document).find("form[id='param']").find("input[name='dictCustomerType']").val();
	var data = encodeURI(param)+"&loanCode="+loanCode+"&type="+dictCustomerType+"&relId="+rCustomerCoborrowerId;
	
	//验证表单
	if (!checkForm( $("#bzrdcxxID") ) ) {
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
	
	// 保存数据
	$.ajax({
		url:ctx+'/credit/creditpaybackdetail/saveData',
		data:data,
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

function makeParam(){
	var param = "";
	var id =  $("#queryInfoId").find("input[name='id']");
	var recentlyPaybackTime = $("#queryInfoId").find("input[name='recentlyPaybackTime']");
	var paybackOrg = $("#queryInfoId").find("input[name='paybackOrg']");
	var totalPaybackAmount = $("#queryInfoId").find("input[name='totalPaybackAmount']");
	var lastPaybackDate = $("#queryInfoId").find("input[name='lastPaybackDate']");
	var residualAmount = $("#queryInfoId").find("input[name='residualAmount']");
	
	for(var i = 0; i < id.length; i++ ){
		param+="&creditPaybackInfoList["+i+"].id="+$(id[i]).val();
		param+="&creditPaybackInfoList["+i+"].recentlyPaybackTime="+$(recentlyPaybackTime[i]).val();
		param+="&creditPaybackInfoList["+i+"].paybackOrg="+$(paybackOrg[i]).val();
		param+="&creditPaybackInfoList["+i+"].totalPaybackAmount="+$(totalPaybackAmount[i]).val();
		param+="&creditPaybackInfoList["+i+"].lastPaybackDate="+$(lastPaybackDate[i]).val();
		param+="&creditPaybackInfoList["+i+"].residualAmount="+$(residualAmount[i]).val();
	}
	return param;
}
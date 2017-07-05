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
		url:ctx+'/creditdetailed/queryrecord/showData',
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
						htmInfo.find("select[name='queryType']").select2("val",data[i].queryType);
						htmInfo.find("input[name='queryDay']").val(formatTime(data[i].queryDay))
						htmInfo.find("a").attr("onClick","removeData('"+data[i].id+"')");
						htmInfo.find("input[name='id']").val(data[i].id);
						htmInfo.appendTo($(target));
						sort();
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
	sort();
}

//显示编号
function sort(){
	// 循环公积金信息(进行排序)
	var allInfoNum = $("#queryInfoId").find("tbody").find("input[name='num']");
	allInfoNum.each(function(i,item){
		$(this).val(i+1);
	});
}

// 删除数据
function removeData( id ){
	if(id != "" && id.length > 20){
		$.ajax({
			url:ctx+'/creditdetailed/queryrecord/deleteData',
			data:"id="+id,
			type: "post",
			dataType:'json',
			success:function(data){
				if(data){
					//$("#msg").html("<font size='3px' color='red'>删除成功!</font>").show(300).delay(2000).hide(300);
					//alert("删除成功");
					art.dialog.tips('删除成功!');
					initData();
				}else{
					//alert("删除失败");
					//$("#msg").html("<font size='3px' color='red'>删除失败!</font>").show(300).delay(2000).hide(300);
					art.dialog.tips('删除失败!');
				}
			}
		});
	}else{
		$("#"+id).remove();
		sort();
	}
}

//保存数据
function saveData(){
	
	var param = makeParam();
	var loanCode = $(window.parent.document).find("form[id='param']").find("input[name='loanCode']").val();
	var rCustomerCoborrowerId = $(window.parent.document).find("form[id='param']").find("input[name='rCustomerCoborrowerId']").val();
	var dictCustomerType = $(window.parent.document).find("form[id='param']").find("input[name='dictCustomerType']").val();
	var data = param+"&loanCode="+loanCode+"&type="+dictCustomerType+"&relId="+rCustomerCoborrowerId;
	
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

	$("#queryInfoId").find("select[name='queryType']").each(function(){
		if($(this).val() == undefined || $(this).val() == ""){
			addMyValidateTip( $(this).siblings(".required"),"保存失败，请填写查询原因" )
			timeFlg = false;  
		}
	});
	
	if(!timeFlg){
		return;
	}
	// 公积金
	$.ajax({
		url:ctx+'/creditdetailed/queryrecord/saveData',
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

function makeParam(){
	var param = "";
	var id =  $("#queryInfoId").find("input[name='id']");
	var queryDay = $("#queryInfoId").find("input[name='queryDay']");
	var queryType = $("#queryInfoId").find("select[name='queryType']");
	for(var i = 0; i < id.length; i++ ){
		param+="&creditQueryList["+i+"].id="+$(id[i]).val();
		param+="&creditQueryList["+i+"].queryDay="+$(queryDay[i]).val();
		param+="&creditQueryList["+i+"].queryType="+$(queryType[i]).val();
	}
	return param;
}

$(function(){
$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src='../../../../static/images/down.png';
			document.getElementById("T1").style.display='';
		}else{
			document.getElementById("showMore").src='../../../../static/images/up.png';
			document.getElementById("T1").style.display='none';
		}
	});

})
//查询条件中图标的收缩和显示
function show() {
		if (document.getElementById("T1").style.display == 'none') {
			$("#showMore").attr({
				"src" : "images/down.png"
			});
			$("#T1").removeAttr("style");
			$("#T2").removeAttr("style");
			$("#T3").removeAttr("style");
		} else {
			$("#showMore").attr({
				"src" : "images/up.png"
			});
			$("#T1").attr("style", "display:none;");
			$("#T2").attr("style", "display:none;");
			$("#T3").attr("style", "display:none;");
		}
	}
/**
 * 退回
 */
function doOpencheck(id,sendStatus,fromId){
	var fileType = $("#fileType").val();
	art.dialog.confirm("是否确定退回？", function(){
		$.ajax({
			type : "POST",
			url : ctx + "/borrow/serve/customerServe/doOpencheck",
			data:{
				   id:id,
				   sendStatus:sendStatus,
				   fileType:fileType
			  },
			success : function(data) {
				if (data == "1"){
					art.dialog.alert("退回成功");
					$("#"+fromId).submit();
				} else {
					art.dialog.alert("退回失败");
				}
			}
		});
	});
}


/**
 * 查看历史操作
 * @param applyId
 */
function doOpenhis(loanCode,fileType) {
	var url = ctx + "/borrow/serve/customerServe/historyList?loanCode=" + loanCode + "&fileType=" + fileType;
	art.dialog.open(url, {  
        id: 'his',
        title: '历史列表',
        lock:true,
        width:700,
     	height:350
    }, false);  
}

/**
 * 确认删除
 */
function confirmDel(){
	var remarks = $("#delReason").val();
	if (remarks == null || remarks == ""){
		art.dialog.alert("请输入删除原因");
		return;
	}
	
	$.ajax({
		cache : false,
		type : "POST",
		url : ctx + "/borrow/serve/customerServe/deleteItem",
		dataType : "json",
		data : $("#delForm").serialize(),
		async : false,
		success : function(data) {
			if (data == "1"){
				art.dialog.alert("删除成功");
				var formId = $("#formId").val();
				$("#" + formId).submit();
			} else {
				art.dialog.alert("删除失败");
			}
		}
	});
}

/**
 * 删除弹出框
 * @param id
 * @param applyId
 * @param customerName
 * @param operateStep
 * @param formid
 */
function deleteItem(id, applyId, loanCode, operateStep, formId){
	$("#delReason").val('');
	$("#id").val(id);
	$("#applyId").val(applyId);
	$("#loanCode").val(loanCode);
	$("#operateStep").val(operateStep + "-删除");
	$("#formId").val(formId);
	$('#deleteItem').modal('toggle').css({
		width : '60%',   
		"margin-left" : (($(document).width() -  $("#inputExpressNumber").css("width").replace("px", "")) / 2),
		"margin-top" : (($(document).height() -  $("#inputExpressNumber").css("height").replace("px", "")) / 2)
	});
	
}
var infos = {};
var CustomerManagementList = {};
function doOpenhs(num) {
	var url = ctx + '/consult/customerManagement/findsimpleHis?id=' + num;
	$.colorbox({
		href : url,
		iframe : true,
		width : "900px",
		height : "400px"
	});
}	

function doOpenhis(num) {
	var url = ctx + '/consult/customerManagement/findHistory?consultID='
			+ num;
     art.dialog.open(url, {  
         id: 'his',
         title: '历史沟通记录',
         lock:true,
         width:700,
     	 height:350
     },  
     false);  
}
function doOpenss(consultId, customerCode, customerName, mateCertNum,
		customerMobilePhone) {
	var url = ctx + '/consult/customerManagement/findConsult?consultId='
			+ consultId + "&customerCode=" + customerCode + "&customerName="
			+ customerName + "&mateCertNum=" + mateCertNum
			+ "&customerMobilePhone=" + customerMobilePhone;
	art.dialog.open(url, {
		id:"consult",
		title : '客户咨询',
		width : 800,
		lock:true,
		height : 250,
		close:function()
		{
			$("form:first").submit();
		}
	});
}
/**
 * 更新咨询信息
 * 
 * @param formId
 * 
 */
function updateConsult(formId) {
	$.ajax({
		url : ctx + '/consult/customerManagement/updateConsult',
		type : 'post',
		data : $('#' + formId).serialize(),
		dataType : 'text',
		success : function(data) {
			if (data) {
 				// 如果父页面重载或者关闭其子对话框全部会关闭
				var win = art.dialog.open.origin;//来源页面
				art.dialog.confirm("客户状态修改成功,点击确定关闭对话框",function(){
				  art.dialog.close();
				});
				
			} else {
				art.dialog.alert("数据更新失败,请重试！");
			}
		},
		error : function() {
			art.dialog.alert("服务器异常,请重试！");
		},
		async : false

	});
}

function giveUpConsult(formId, consultId, customerCode, customerName,
		mateCertNum, customerMobilePhone) {
		art.dialog.confirm('确认要放弃吗？', function() {
			$('#consultId').val(consultId);
			$('#customerCode').val(customerCode);
			$('#customerMobilePhone').val(customerMobilePhone);
			updateConsult(formId);
			$('#inputForm')[0].submit();
	});
}

infos.datavalidate = function() {
	$("#inputForm").validate({
		onkeyup : false,
		submitHandler : function(form) {
			updateConsult("inputForm");
		},
		errorContainer : "#messageBox",
		errorPlacement : function(error, element) {
			$("#messageBox").text("输入有误，请先更正。");
			if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
				error.appendTo(element.parent().parent());
			} else {
				error.insertAfter(element);
			}
		}
	});
};

CustomerManagementList.allot = function(){
	$(".allot").click(function(){
		var id = $(this).siblings("input[name='id']").val();
		var url = ctx + '/consult/customerManagement/findConsultList/allot?id='+id;
		art.dialog.open(url, {
			id : "allot",
			title : '分配',
			width : 500,
			lock : true,
			height : 300,
			close : function() {
			}
		});
	});
}

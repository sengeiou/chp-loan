$(function(){
	// 全选反选
	$("#checkAll").click(function(){
		$("input:checkbox").attr("checked", this.checked);	
});

// 批量申请弹出层
	$("#transate").click(function(){
		var valArr = new Array;
		$(":input[name='applyLoanCodes']:checked").each(function(i) {
			valArr[i] = $(this).val();	
		});	
		if (valArr != null && "" != valArr) {
			art.dialog({
				title: '批量申请',
				padding: 0,
				content: document.getElementById('boath'),
				lock: true,
				button:[{
					name: '确认交割',
					callback: function() {
						// 提交时非空验证
						var org = $("#totalOrg").val();
						var team = $("#totalTeam").val();
						var manager = $("#totalManager").val();
						if ('' == org) {
							art.dialog.alert("请选择门店"); 
							return false;
						}else {
							if ('' == team) {
								art.dialog.alert("请选择团队经理");
								return false;
							}else {
								if ('' == manager) {
									art.dialog.alert("请选择客户经理");
									return false;
								}else {
									var valArr = new Array;
									$(":input[name='applyLoanCodes']:checked").each(function(i) {
										valArr[i] = $(this).val();	
									});	
									var vals = valArr.join(',');
									var url = ctx + "/borrow/apply/applyBatchTransact?loanCodes="+vals;	
									$("#confirmApplyForm").prop("action", url);	
									$("#confirmApplyForm").submit();
								}	
							}	
						}
					}
				},{
					name: '关闭'
				}]
			},false);
		} else {
			art.dialog.alert('请选择办理项');
		}	
	});

// 批量驳回原因输入框
$("#regectedAll").click(function(){
		var valArr = new Array;
		$(":input[name='check']:checked").each(function(i) {
			valArr[i] = $(this).val();	
		});	
		if (valArr != null && "" != valArr) {
			art.dialog({
				title: '驳回原因',
				padding: 0,
				content: document.getElementById('reason'),
				lock: true,
				button:[{
					name:'确认全部驳回',
					callback:function(){
						// 批量驳回
						var valArr = new Array;
						var reason = $("#regectedReason").val();
						$(":input[name='check']:checked").each(function(i) {
							valArr[i] = $(this).val();	
						});	
						var vals = valArr.join(',');
						if (reason != null && "" != reason ) {
							var url = ctx + "/borrow/delivery/regectedAll?loanCodes="+vals+"&reason="+ reason;
							window.location = url;							
						}else{
							art.dialog.alert("请填写驳回原因!");	
							return false;
						}						
					}
				},{
					name:'关闭'
				}],
			},false);
		} else {
			art.dialog.alert("请选择办理项")
		}
	});


//批量通过
$("#passAll").click(function() {	
	var valArr = new Array;
	$(":input[name='check']:checked").each(function(i) {
		valArr[i] = $(this).val();	
	});
	var vals = valArr.join(',');
	if (vals != null && "" != vals) {
		var url = ctx + "/borrow/delivery/passAll?loanCodes=" + vals;
		window.location = url;
	} else {
		art.dialog.alert("请选择处理项");
	}
	});
});
// 交割申请回显数据清除
$(document).ready(function() {	
	$("#appRemoveBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除checkbox	
		$(":checkbox").attr("checked", false);
		// 清除radio	
		$(":radio").attr("checked", false);
		// 清除select	
		$("select").val("");
		//$("select").trigger("change");	
		$("#applyForm").submit();
	});	
});

// 交割待办回显数据清除
$(document).ready(function() {	
	$("#wRemoveBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除checkbox	
		$(":checkbox").attr("checked", false);
		// 清除radio	
		$(":radio").attr("checked", false);
		// 清除select	
		$("select").val("");
		$("select").trigger("change");	
		$("#waitForm").submit();
	});	
});

// 交割已办回显数据清除
$(document).ready(function() {	
	$("#removeBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除checkbox	
		$(":checkbox").attr("checked", false);
		// 清除radio	
		$(":radio").attr("checked", false);
		// 清除select	
		$("select").val("");
		$("select").trigger("change");	
		$("#delForm").submit();
	});	
});

// 交割申请办理异步请求数据
function traBtn(obj){
	var loanCode = obj.id;
	$.ajax({
		url : ctx + "/borrow/apply/applyTransact",
		type : "post",	
		cache : false,
		data : {'loanCode':loanCode},
		error : function () {
			alert("Error!");
		},
		success : function(dv) {
			$("#orgName").html(dv.orgName);
			$("#teamManagerName").html(dv.teamManagerName);
			$("#managerName").html(dv.managerName);
			$("#customerServiceName").html(dv.customerServiceName);
			$("#outbountByName").html(dv.outbountByName);
			$("#loanCodeHid").val(dv.loanCode);
			$("#orgCode").val(dv.orgCode);
			$("#teamManagerCode").val(dv.teamManagerCode);
			$("#managerCode").val(dv.managerCode);
			$("#serviceCode").val(dv.customerServiceCode);
			$("#outBoundCode").val(dv.outbountByCode);
			$("#dictLoanStatus").val(dv.dictLoanStatus);
			$("#contractCode").val(dv.contractCode);
			$("#imageUrl").attr('src',dv.imageUrl);
			// 调出办理弹出层
			art.dialog({
				title: '办理申请',
				padding: 0,
				content: document.getElementById('single'),
				lock: true,
				button:[{
					name: '确认交割',
					callback: function() {
						var org = $("#org").val();
						var team = $("#team").val();
						var manager = $("#newManager").val();
						if ('' == org) {
							art.dialog.alert("请选择门店"); 
							return false;
						}else {
							if ('' == team) {
								art.dialog.alert("请选择团队经理");
								return false;
							}else {
								if ('' == manager) {
									art.dialog.alert("请选择客户经理");
									return false;
								}else {
									var url= ctx + "/borrow/apply/updateDelivery";
									$("#singleForm").attr("action",url);
									$("#singleForm").submit();	
								}	
							}	
						}
					}
				},{
					name: '关闭'
				}]
			},false);
		}
	});	
};

// 门店,团队经理,客户经理,客服人员,外访人员联动
$(function(){
	// 根据门店异步查询团队经理
	$("#org").change(function(){
		var orgCode = $("#org").val();
		$("#team").empty();
		$("#newManager").empty();
		$("#service").empty();
		$("#outBound").empty();
		$.ajax({
			url:ctx + "/borrow/apply/getTeam",
			type : "post",	
			cache : false,
			data : {'orgCode':orgCode},
			error : function () {
				alert("Error!");
			},
			success : function(maps) {	
				// 团队经理
				$("#team").append("<option value='' selected>请选择</option>")
				for(var i=0;i<maps.teams.length;i++){
					$("#team").append("<option value='"+maps.teams[i].userCode+"'>"+maps.teams[i].userName+"</option>");
				}
				$("#team").trigger("change");
				// 客服
				$("#service").append("<option value='' selected>请选择</option>")
				for(var i=0;i<maps.services.length;i++){
					$("#service").append("<option value='"+maps.services[i].userCode+"'>"+maps.services[i].userName+"</option>");
				}
				$("#service").trigger("change");
				// 外访
				$("#outBound").append("<option value='' selected>请选择</option>")
				for(var i=0;i<maps.outBounds.length;i++){
					$("#outBound").append("<option value='"+maps.outBounds[i].userCode+"'>"+maps.outBounds[i].userName+"</option>");
				}
				$("#outBound").trigger("change");	
			}
		});
	});	

// 根据团队经理异步查询所有客户经理
$("#team").change(function(){
	var userCode = $("#team").val();	
	$("#newManager").empty();
	$.ajax({
		url:ctx + "/borrow/apply/getManager",
		type : "post",	
		cache : false,
		data : {'userCode':userCode},
		error : function () {
			art.dialog.alert("Error!");
		},
		success : function(managers) {	
			$("#newManager").append("<option selected value=''>请选择</option>");
			for(var i=0;i<managers.length;i++){
				$("#newManager").append("<option value='"+managers[i].userCode+"'>"+ managers[i].userName+"</option>");
			}	
			$("#newManager").trigger("change");
		}
	});
	});	
});

// 批量操作门店,团队经理,客户经理,客服人员,外访人员联动
$(function(){
	// 根据门店异步查询团队经理
	$("#totalOrg").change(function(){
		var orgCode = $("#totalOrg").val();
		$("#totalTeam").empty();
		$("#totalManager").empty();
		$("#totalService").empty();
		$("#totalOutBound").empty();
		$.ajax({
			url:ctx + "/borrow/apply/getTeam",
			type : "post",	
			cache : false,
			data : {'orgCode':orgCode},
			error : function () {
				art.dialog.alert("Error!");
			},
			success : function(maps) {	
				// 团队经理
				$("#totalTeam").append("<option value='' selected>请选择</option>")
				for(var i=0;i<maps.teams.length;i++){
					$("#totalTeam").append("<option value='"+maps.teams[i].userCode+"'>"+maps.teams[i].userName+"</option>");
				}
				$("#totalTeam").trigger("change");
				// 客服
				$("#totalService").append("<option value='' selected>请选择</option>")
				for(var i=0;i<maps.services.length;i++){
					$("#totalService").append("<option value='"+maps.services[i].userCode+"'>"+maps.services[i].userName+"</option>");
				}
				$("#totalService").trigger("change");
				// 外访
				$("#totalOutBound").append("<option value='' selected>请选择</option>")
				for(var i=0;i<maps.outBounds.length;i++){
					$("#totalOutBound").append("<option value='"+maps.outBounds[i].userCode+"'>"+maps.outBounds[i].userName+"</option>");
				}
				$("#totalOutBound").trigger("change");	
			}
		});
	});	

	// 根据团队经理异步查询所有客户经理
	$("#totalTeam").change(function(){
		var userCode = $("#totalTeam").val();	
		$("#totalManager").empty();
		$.ajax({
			url:ctx + "/borrow/apply/getManager",
			type : "post",	
			cache : false,
			data : {'userCode':userCode},
			error : function () {
				art.dialog.alert("Error!");
			},
			success : function(managers) {	
				$("#totalManager").append("<option selected value=''>请选择</option>");
				for(var i=0;i<managers.length;i++){
					$("#totalManager").append("<option value='"+managers[i].userCode+"'>"+ managers[i].userName+"</option>");
				}	
				$("#totalManager").trigger("change");
			}
		});
	});	
});

// 单条记录交割申请非空验证
$(function(){
	// 验证部门是否选择,选择则*消失
	$("#org").change(function(){
		var org = $("#org").val();
		if (org != '') {
			$("#orgFont").hide();
		}else{
			$("#orgFont").show();
		}	
});

// 验证团队经理是否选择,选择则*消失
$("#team").change(function(){
	var team = $("#team").val();
	if (team != '') {
		$("#teamFont").hide();
	}else{
		$("#teamFont").show();
	}	
});

// 验证客户经理是否选择,选择则*消失 
$("#newManager").change(function(){
	var manager = $("#newManager").val();
	if (manager != '') {
		$("#managerFont").hide();
	}else{
		$("#managerFont").show();
	}	
	});	
});

/*// 批量交割申请非空验证
$(function(){
	// 验证部门是否选择,选择则*消失
	$("#totalOrg").change(function(){
		var org = $("#totalOrg").val();
		if (org != '') {
			$("#totalOrgFont").hide();
		}else{
			$("#totalOrgFont").show();
		}	
	});

	// 验证团队经理是否选择,选择则*消失
	$("#totalTeam").change(function(){
		var team = $("#totalTeam").val();
		if (team != '') {
			$("#totalTeamFont").hide();
		}else{
			$("#totalTeamFont").show();
		}	
	});

	// 验证客户经理是否选择,选择则*消失 
	$("#totalManager").change(function(){
		var manager = $("#totalManager").val();
		if (manager != '') {
			$("#totalManagerFont").hide();
		}else{
			$("#totalManagerFont").show();
		}	
	});	
});*/

// 时间搜索起止时间校验
function checkDate() {
	var startTime = $("#startTime").val();
	var start = new Date(startTime.replace("-", "/").replace("-", "/"));  
	var endTime = $("#endTime").val();
	var end = new Date(endTime.replace("-", "/").replace("-", "/"));
	if (start > end) {
		art.dialog.alert("起始时间不能大于终止时间!");
	}
};

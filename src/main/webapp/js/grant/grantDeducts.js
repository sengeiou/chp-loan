var checkVal = "";
//保存系统设定
function saveSysSetting(sysFlag,sysName,sysValue,sysId){
	$.ajax({
		url:ctx+"/common/systemSetMater/save",
		type:"post",
		dataType:'json',
		data:{
		  'sysFlag':sysFlag,
		  'sysName':sysName,
		  'sysValue':sysValue,
		  'isNewRecord':false,
		  'id':sysId 
		},
		cache: false,
		async: false,
		success:function(data){
			
		},
		error:function(){
			
		}
	});
}
$(document).ready(function() {
	onload = show();
	// 点击清除，清除搜索页面中的数据
	$("#clearBtn").click(function() {
		$(':input', '#deductsForm')
		.not(':button,:submit,:reset').val('').removeAttr('checked').removeAttr('selected');
		$("#bankId").val('');
	});

	// 查看单子操作历史
	$(":input[name='history']").each(function() {
		$(this).click(function() {
			var urgeId = $(this).attr("sid");
			var url = ctx + '/common/history/showFeekHis?payBackApplyId=' + urgeId;
			art.dialog.open(url, {
				title: "操作历史",
				lock: true,
				width: 700,
				height: 350
			},
			false);
		});
	});

	// 1.点击停止自动划扣按钮,需要将setting表中的标识更新;2.如果有选中的，将选中的停止，修改单子的自动划扣标识;
	$("#stopDeducts").click(function() {
		var stop = "停止自动划扣";
		var start = "开始自动划扣";
		var op = $(this).val();
		var cid = "";   
		// 获得选中单子的催收主表id
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (cid != "") {
					cid += "," + $(this).attr("cid");
				} else {
					cid = $(this).attr("cid");
				}
			});
		}
		if (op == stop) {
			var flag = confirm("确定停止自动划扣么？");
			if (flag) {
				saveSysSetting($('#autoDeductsFlag').val(),$('#autoDeductsName').val(),"0",$('#autoDeductsID').val());
				// 修改选中的单子的自动划扣规则
				updateAutoFlag(cid,'0');
			}
		} else { // 开启自动划扣
			var flag = confirm("确定开启自动划扣么？");
			if (flag) {
				saveSysSetting($('#autoDeductsFlag').val(),$('#autoDeductsName').val(),"1",$('#autoDeductsID').val());
				updateAutoFlag(cid,'1');
			}
		}
	});
	
	// 伸缩
	$("#showMore").click(function() {
		if (document.getElementById("T1").style.display == 'none') {
			document.getElementById("showMore").src = '../../../../static/images/down.png';
			document.getElementById("T1").style.display = '';
			document.getElementById("T2").style.display = '';
			document.getElementById("T3").style.display = '';
		} else {
			document.getElementById("showMore").src = '../../../../static/images/up.png';
			document.getElementById("T1").style.display = 'none';
			document.getElementById("T2").style.display = 'none';
			document.getElementById("T3").style.display = 'none';
		}
	});

	// 计算金额,
	$(":input[name='checkBoxItem']").click(function() {
		// 记录总金额，当length为0时，进行总金额的处理
		var totalDeduct = $("#deduct").val();
		var totalNum = $("#num").val();
		// 获得单个单子的金额
		var deductAmount = parseFloat($(this).attr("deductAmount"));
		var num = 1;
		if ($(this).attr('checked') == 'checked' || $(this).attr('checked')) {
			var hiddenNum = parseFloat($("#hiddenNum").val()) + num;
			var hiddenDeduct = parseFloat($("#hiddenDeduct").val()) + deductAmount;
			$('#totalNum').text(hiddenNum);
			$("#hiddenNum").val(hiddenNum);
			$("#hiddenDeduct").val(hiddenDeduct);
			$('#deductAmount').text(fmoney(hiddenDeduct, 2));
		} else {
			$('#checkAll').removeAttr("checked");
			if ($(":input[name='checkBoxItem']:checked").length == 0) {
				$('#totalNum').text(totalNum);
				$("#deductAmount").text(fmoney(totalDeduct, 2));
				$("#hiddenNum").val(0);
				$("#hiddenDeduct").val(0.00);
			} else {
				$('#totalNum').text(parseFloat($("#hiddenNum").val()) - num);
				$("#hiddenNum").val(parseFloat($("#hiddenNum").val()) - num);

				var hiddenDeduct = parseFloat($("#hiddenDeduct").val()) - deductAmount;
				$("#deductAmount").text(fmoney(hiddenDeduct, 2));
				$("#hiddenDeduct").val(hiddenDeduct);
			}
		}
	});

	// 导出富友，如果有选中，获得选中单子的拆分id，如果没有，默认为当前条件下的所有划扣平台为富友的单子
	$("#daoFBtn").click(function() {
		var url = "deductsFyExl";
		excelDao(url);
	});

	// 导出好易联
	$("#daoHBtn").click(function() {
		var url = "deductsHylExl";
		excelDao(url);
	});

	// 导出通联
	$("#daoTLBtn").click(function() {
		var url = "deductsTLExl";
		excelDao(url);
	});

	// 导出中金
	$("#daoZBtn").click(function() {
		var url = "deductsZJExl";
		excelDao(url);
	});
	
	// 放款以往待划扣导出excel
	$("#daoBef").click(function(){
		var cid = "";
		var urgeMoney = $("#deductsForm").serialize();
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (cid != "") {
					cid += "," + $(this).attr("cid");
				} else {
					cid = $(this).attr("cid");
				}
			});
		}
		window.location.href = ctx + "/borrow/grant/grantDeducts/daoExcelBef?cid=" + cid + "&" + urgeMoney;
	})

	// 划扣已办列表导出
	$("#dao").click(function() {
		var cid = "";
		var urgeMoney = $("#deductsForm").serialize();
		if ($(":input[name='checkBoxItem']:checked").length > 0) {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (cid != "") {
					cid += "," + $(this).attr("cid");
				} else {
					cid = $(this).attr("cid");
				}
			});
		}
		window.location.href = ctx + "/borrow/grant/grantDeductsDone/exportDeductsDone?cid=" + cid + "&" + urgeMoney;
	})

	// 上传富友，控制弹出框的
	$("#shangF").click(function() {
		var returnUrl = $("#returnUrl").val();
		var result = $("#result").val();
		$("#uploadAuditForm").attr("action", ctx + "/borrow/grant/grantDeducts/fyImportResult?returnUrl=" + returnUrl + "&result=" + result);
	});

	// 上传好易联，控制弹出框的
	$("#shangH").click(function() {
		var result = $("#result").val();
		var returnUrl = $("#returnUrl").val();
		$("#uploadAuditForm").attr("action", ctx + "/borrow/grant/grantDeducts/hylImportResult?returnUrl=" + returnUrl + "&result=" + result);
	});

	// 上传通联，控制弹出框的
	$("#shangT").click(function() {
		var result = $("#result").val();
		var returnUrl = $("#returnUrl").val();
		$("#uploadAuditForm").attr("action", ctx + "/borrow/grant/grantDeducts/tlImportResult?returnUrl=" + returnUrl + "&result=" + result);
	});

	// 上传中金，控制弹出框的
	$("#shangZ").click(function() {
		var result = $("#result").val();
		var returnUrl = $("#returnUrl").val();
		$("#uploadAuditForm").attr("action", ctx + "/borrow/grant/grantDeducts/zjImportResult?returnUrl=" + returnUrl + "&result=" + result);
	});

	// 发送富友划扣，默认将查询条件下划扣失败的数据更改状态
	$("#sendF").click(function() {
		var type = "0";
		var deductName = "富友";
		var rule = "0:0";
		$("#rule").val(rule);
		send(type,deductName, rule);
	});

	// 发送中金划扣
	$("#sendZ").click(function() {
		var type = "2";
		var deductName = "中金";
		var rule = "2:0";
		$("#rule").val(rule);
		send(type,deductName, rule);
	});

	// 发送好易联划扣
	$("#sendH").click(function() {
		var type = "1";
		var deductName = "好易联";
		var rule = "1:0";
		$("#rule").val(rule);
		send(type,deductName, rule);
	});

	// 发送通联划扣
	$("#sendT").click(function() {
		var type = "3";
		var deductName = "通联";
		var rule = "3:0";
		$("#rule").val(rule);
		send(type,deductName, rule);
	});

	// 点击追回,可以批量进行追回,根据拆分表的id进行更改回盘结果，回盘时间，手动追回的应该拆分和划扣表都需要进行同步更新
	$("#backBtn").click(function() {
		var checkVal = "";
		if($(":input[name='checkBoxItem']").length > 0){
		if ($(":input[name='checkBoxItem']:checked").length == 0) {
			var flag = confirm("确认将列表中所有单子追回么？");
			if (!flag) {
				return;
			}
		} else {
			$(":input[name='checkBoxItem']:checked").each(function() {
				if (checkVal == "") {
					checkVal = $(this).attr("cid");
				} else {
					checkVal += "," + $(this).attr("cid");
				}
			});
		}
		$("#checkVal").val(checkVal);
		$(this).attr("data-target", "#backModal");
		}else{
			 art.dialog.alert("列表数据为空！");
		}
		
	});
	
	// 追回确认
	$("#backSure").click(function() {
		confirmQuit();
	});

});

// 全选
function allOrNo(){
	var totalNum = $("#num").val();
	var deductAmount = $("#deduct").val();
	if ($('#checkAll').attr('checked') == 'checked' || $('#checkAll').attr('checked')) {
		var totalNum = 0;
		var deductAmount = 0.00;
		$(":input[name='checkBoxItem']").each(function() {
			$(this).attr("checked", 'true');
			totalNum = totalNum + 1;
			deductAmount = deductAmount + parseFloat($(this).attr("deductAmount"));
		});
		$('#totalNum').text(totalNum);
		$("#deductAmount").text(fmoney(deductAmount, 2));
		$("#hiddenNum").val(totalNum);
		$("#hiddenDeduct").val(deductAmount);
	} else {
		$(":input[name='checkBoxItem']").each(function() {
			$(this).removeAttr("checked");
		});
		$('#totalNum').text(totalNum);
		$("#deductAmount").text(fmoney(deductAmount, 2));
		$("#hiddenNum").val(0);
		$("#hiddenDeduct").val(0.00);
	}
}

// 发送划扣，有选中，将选中的回盘结果为失败的单子进行划扣，如果没有选中，默认将查询条件下的回盘结果为失败的单子进行划扣
function send(type,deductName, rule) {
	var reason = null;
	var cid = "";
	var result = $("#result").val();
	if ($(":input[name='checkBoxItem']:checked").length == 0) {
		art.dialog.confirm("您确定所有划扣失败的单子发送" + deductName + "划扣么？", 
				function(){
				sendTo(cid, type, result, rule);
			    },
        	    function () {
        	    art.dialog.tips('处理取消');
        	    return;
    	    });	
	} else {
		art.dialog.confirm("确定发送" + deductName + "划扣么？", 
				function(){
					$(":input[name='checkBoxItem']:checked").each(function() {
						reason = $(this).attr('reason');
						if (cid != "") {
							cid += ";" + $(this).attr("cid");
						} else {
							cid = $(this).attr("cid");
						}
					});
					sendTo(cid, type, result, rule);
			    },
        	    function () {
        	    art.dialog.tips('处理取消');
        	    return;
    	    });
	}
}

// 关闭模式框
function closeBtn(id) {
	$("#" + id + "").modal('hide');
}
// 划扣追回确认方法
function confirmQuit() {
	var checkVal = $("#checkVal").val();
	var backPlat = $('#backPlat option:selected').val();
	back(checkVal, backPlat);
	$("#backModal").modal('hide');
}

// 追回处理，
function back(checkVal, backPlat) {
	var returnUrl = $("#returnUrl").val();
	var result = $("#result").val();
	var urgeMoney = $("#deductsForm").serialize();
	urgeMoney +="&backPlat="+backPlat+"&checkVal="+checkVal+"&result="+result;
	$.ajax({
		type: 'post',
		url: ctx + '/borrow/grant/grantDeducts/deductsBackSure',
		data: urgeMoney,
		dataType: 'text',
		cache: false,
		async: false,
		success: function(result) {
			art.dialog.alert(result);
			window.location.href = ctx + "/borrow/grant/grantDeducts/deductsInfo?result=" + result + "&returnUrl=" + returnUrl;
		},
		error: function() {
			art.dialog.alert('请求异常！');
		}
	});
}
// 发送划扣
function sendTo(cid, type, result, rule) {
	var returnUrl = $("#returnUrl").val();
	var urgeMoney = $("#deductsForm").serialize();
	urgeMoney+="&type="+type+"&cid="+cid+"&returnUrl="+returnUrl+"&rule="+rule+"&result="+result;
	$.ajax({
		type: 'post',
		url: ctx + '/borrow/grant/grantDeducts/sendDeduct',
		data: urgeMoney,
		dataType: 'text',
		cache: false,
		async: false,
		success: function(data) {
			art.dialog.alert(data);
			window.location.href = ctx + "/borrow/grant/grantDeducts/deductsInfo?result=" + result + "&returnUrl=" + returnUrl;
		},
		error: function() {
			art.dialog.alert('请求异常！');
		}
	});
}

// excel导出
function excelDao(url) {
	var cid = "";
	var returnUrl = $("#returnUrl").val();
	var result = $("#result").val();
	var urgeMoney = $("#deductsForm").serialize();
	if ($(":input[name='checkBoxItem']:checked").length > 0) {
		$(":input[name='checkBoxItem']:checked").each(function() {
			if (cid != "") {
				cid += "," + $(this).attr("cid");
			} else {
				cid = $(this).attr("cid");
			}
		});
	}
	window.location.href = ctx + "/borrow/grant/grantDeducts/" + url + "?cid=" + cid + "&resultString=" + result + "&returnUrl=" +returnUrl+ "&" + urgeMoney;
}

/**
 * 停止或者开启自动划扣处理方法
 * @param cid
 * @param autoFlag
 */
function updateAutoFlag(cid,autoFlag){
	var returnUrl = $("#returnUrl").val();
	var result = $("#result").val();
	var urgeMoney = $("#deductsForm").serialize();
	urgeMoney += "&cid="+cid+"&autoFlag="+autoFlag;
	$.ajax({
		type: 'post',
		url: ctx + '/borrow/grant/grantDeducts/updateAutoFlag',
		data: urgeMoney,
		dataType: 'text',
		cache: false,
		async: false,
		success: function(result) {
			if(result = "success"){
				art.dialog.alert("执行成功");
				if(autoFlag == '0'){
					btnCol('button', 'start');
					$("#startFlag").val('start');
				}else{
					$("#startFlag").val('stop');
					btnCol('hidden', 'stop');
				}
			}else{
				art.dialog.alert("执行失败"+result);
			}
			window.location.href = ctx + "/borrow/grant/grantDeducts/deductsInfo?result=" + result + "&returnUrl=" + returnUrl;
		},
		error: function() {
			art.dialog.alert('请求异常！');
		}
	});
}
// 格式化，保留两个小数点
function fmoney(s, n) {
	n = n > 0 && n <= 20 ? n: 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(),
	r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? ",": "");
	}
	return t.split("").reverse().join("") + "." + r;
}

// 控制页面上发送划扣和线上线下按钮的隐藏和显示
function btnCol(showType, startFlag) {
	var stop = "停止自动划扣";
	var start = "开始自动划扣";
	if (startFlag == 'start') {
		$("#stopDeducts").val(start);
	} else {
		$("#stopDeducts").val(stop);
	}
	$("#daoHBtn").prop("type", showType);
	$("#daoFBtn").prop("type", showType);
	$("#daoZBtn").prop("type", showType);
	$("#daoTLBtn").prop("type", showType);
	$("#shangF").prop("type", showType);
	$("#shangH").prop("type", showType);
	$("#shangT").prop("type", showType);
	$("#shangZ").prop("type", showType);
	$("#sendF").prop("type", showType);
	$("#sendZ").prop("type", showType);
	$("#sendH").prop("type", showType);
	$("#sendT").prop("type", showType);
}

function show() {
	var startFlag = $("#startFlag").val();
	if (startFlag == 'start') {
		btnCol('button', 'start');
	} else {
		btnCol('hidden', 'stop');
	}
}
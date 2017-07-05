$(function() {
	$.popuplayer(".edit");
	$("#showMore").click(function(){
		if(document.getElementById("T1").style.display=='none'){
			document.getElementById("showMore").src=ctxStatic + '/images/down.png';
			document.getElementById("T1").style.display='';
			document.getElementById("T2").style.display='';
			document.getElementById("T3").style.display='';
			document.getElementById("T4").style.display='';
		}else{
			document.getElementById("showMore").src=ctxStatic + '/images/up.png';
			document.getElementById("T1").style.display='none';
			document.getElementById("T2").style.display='none';
			document.getElementById("T3").style.display='none';
			document.getElementById("T4").style.display='none';
		}
	});
		loan.getstorelsit("storesName", "storeOrgId");
		if (message) {
			art.dialog.alert(message);
		}

		// 点击清除，清除搜索页面中的数据，DOM
		$("#clearBt").click(
				function() {
					$("#customerName").val('');
					$("#contractCode").val('');
					$("#storesName").val('');
					$("#storeOrgId").val('');
					$("#identityCode").val('');

					$("#replyProductCode").val('');
					$("#replyProductCode").trigger("change");
					$("#urgentFlag").val('');
					$("#urgentFlag").trigger("change");
					$("#additionalFlag").val('');
					$("#additionalFlag").trigger("change");
					$("#telesalesFlag").val('');
					$("#telesalesFlag").trigger("change");
					$("#loanStatusCode").val('');
					$("#loanStatusCode").trigger("change");
					$("#paperLessFlag").val('');
					$("#paperLessFlag").trigger("change");
					$("#contractVersion").val('');
					$("#contractVersion").trigger("change");
					$("#ensureManFlag").val('');
					$("#ensureManFlag").trigger("change");
					$("#registFlag").val('');
					$("#registFlag").trigger("change");
					$("#signUpFlag").val('');
					$("#signUpFlag").trigger("change");
					$("#riskLevel").val('');
					$("#riskLevel").trigger("change");
					$("#revisitStatus").val('');
					$("#revisitStatusName").val('');
					$("#zcjForm").submit();
				});
		
		// 点击全选
		$("#checkAll").click(function(){
			var count = 0;
			var contract = 0.00;
			var grant = 0.00;
			if($('#checkAll').attr('checked')=='checked'|| $('#checkAll').attr('checked'))
			{
				$(":input[name='checkBoxItem']").each(function() {
					$(this).attr("checked",'true');
					count += 1;
					contract += parseFloat($.isBlank($(this).attr("contractMoney")),10);
					grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
				});
			}else{
				$(":input[name='checkBoxItem']").each(function() {
					$(this).removeAttr("checked");
					count += 1;
					contract += parseFloat($.isBlank($(this).attr("contractMoney")),10);
					grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
				});
			}
			setValue(count,contract,grant);
		});
		var totalGrantMoney = 0.00;
		var contractMoney = 0.00;
		var num = 0;
		$(":input[name='checkBoxItem']").each(function(){
			totalGrantMoney += parseFloat($.isBlank($(this).attr("grantAmount")));
			contractMoney += parseFloat($.isBlank($(this).attr("contractMoney")));
			num += 1;
		});
		setValue(num,contractMoney,totalGrantMoney);
		/** 计算默认金额 */
		$(":input[name='checkBoxItem']").click(function(){
			var count = 0;
			var contract = 0.00;
			var grant = 0.00;
			var t = true;
			$(":input[name='checkBoxItem']").each(function() {
				if($(this).attr('checked')=='checked'|| $(this).attr('checked'))
				{
					count += 1;
					contract += parseFloat($.isBlank($(this).attr("contractMoney")),10);
					grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
					t = false;
				}
			});
			if(t){
				$(":input[name='checkBoxItem']").each(function(){
					grant += parseFloat($.isBlank($(this).attr("grantAmount")));
					contract += parseFloat($.isBlank($(this).attr("contractMoney")));
					count += 1;
				});
			}
			setValue(count,contract,grant);
		});
		
		
		// 导出客户信息表
		$("#btnExportCustomer")
				.click(
						function() {
							var idVal = "";
							var loanFlowQueryParam = $("#zcjForm")
									.serialize();
							if ($(":input[name='checkBoxItem']:checked").length > 0) {
								$(":input[name='checkBoxItem']:checked").each(
										function() {
											if (idVal != "") {
												idVal += ","
														+ $(this).attr(
																"loanCode");
											} else {
												idVal = $(this)
														.attr("loanCode");
											}
										});
							}
								window.location.href = ctx
								+ "/borrow/zcj/exportCustomer?loanCodes="
								+ idVal + "&" + loanFlowQueryParam;
						});
		// 导出打款表
		$("#btnExportRemit")
				.click(
						function() {
							var idVal = "";
							var loanGrant = $("#zcjForm").serialize();
							if ($(":input[name='checkBoxItem']:checked").length > 0) {
								$(":input[name='checkBoxItem']:checked").each(
										function() {
											if (idVal != "") {
												idVal += ","
														+ $(this).attr(
																"loanCode");
											} else {
												idVal = $(this)
														.attr("loanCode");
											}
										});
							}
								window.location.href = ctx
								+ "/borrow/zcj/exportRemit?loanCodes="
								+ idVal + "&" + loanGrant;
						});

		// 汇总表导出
		$("#btnExportSummary")
				.click(
						function() {
							var idVal = "";
							var loanGrant = $("#zcjForm").serialize();
							if ($(":input[name='checkBoxItem']:checked").length > 0) {
								$(":input[name='checkBoxItem']:checked").each(
										function() {
											if (idVal != "") {
												idVal += ","
														+ $(this).attr(
																"loanCode");
											} else {
												idVal = $(this)
														.attr("loanCode");
											}
										});
							}

								window.location.href = ctx
								+ "/borrow/zcj/exportSummary?loanCodes="
								+ idVal + "&" + loanGrant;
						});
		
		// 点击放款确认
		$(".zcj_grantSureBtn1").click(function(){
			//判断是不是冻结是数据信息
			var $input = $(this).closest("tr").find("td:first").find("input");
			var frozenFlag =  $input.attr("frozenFlag");
			var urgentFlag = $input.attr("urgentFlag");
			var revisitStatus = $input.attr("revisitStatus");
			if(frozenFlag == '1') {
				art.dialog.alert("当前数据信息已经申请了门店冻结,请先取消门店冻结后方可操作!");
				return false;
			}
			//判断是否待回访
			if(revisitStatus != ''&& revisitStatus != null){
				if(revisitStatus == 0 || revisitStatus == -1){
					art.dialog.alert("不允许确认待回访的数据");
					return;
				}
			}
			var param=analyticalData($(this));
			if(param!=null && param!=undefined){
				param+='&urgentFlag='+urgentFlag;
			}
			art.dialog.confirm("确定对单子进行放款确认？",function (){
				// 放款确认，更改单子的借款状态以及对表进行查询，同时对历史表进行插入，
				$.ajax({
	    			type : 'post',
	    			url : ctx + '/borrow/zcj/dispatchFlowStatus',
	    			data :param,
	    			cache : false,
	    			async : false,
	    			success : function(result) {
		    		   if(result == 'true'){
			    			art.dialog.alert("放款确认成功！",function () {
			    				$("#zcjForm").submit();
		    		    		//window.location.reload();
		    		    	});
		    		    }else{
			    			art.dialog.alert(result);
		    		    }
	    			},
	    			error : function() {
	    				 art.dialog.alert('请求异常，放款确认失败！');
	    			}
	    			 
	    		});
			});
		});
		
		// 点击退回
		$(".zcj_BackBtn").click(function(){
			$("#hiddenValue").val(analyticalData($(this)));
			$("#sel").val('6');
			$("#sel").trigger("change");
			$("#textArea").val('');
			$("#sel").change();
		    $(this).attr("data-target","#sureBack");
		});
		
		// 点击确认退回原因,需要指定退回的节点
		$("#GCgrantSureBackBtn").click(function(){
			// 获得填写的退回原因，调用流程退回的处理方法，更新单子的状态以及合同表的待放款确认退回原因
			var grantSureBackReasonVal= $("#sel").find("option:selected").val();
			var grantSureBackReason= $("#sel").find("option:selected").text();
			var param=$("#hiddenValue").val()+"&grantSureBackReasonCode="+grantSureBackReasonVal;
			if(grantSureBackReason=="其他"){
				grantSureBackReason=$.trim($("#textArea").val());
				if(grantSureBackReason==null || grantSureBackReason == '请填写其他退回原因！'||grantSureBackReason.length == 0){
					art.dialog.alert("退回原因不能为空！");
					return;
				}
			}
			grantSureBack(param,grantSureBackReason);
		});
		
		//驳回申请
		$(".zcj_bh").click(function () {
			var obj = this;
			art.dialog({
				content: document.getElementById("rejectFrozen"),
				title:'驳回申请',
				fixed: true,
				lock:true,
				okVal: '确认',
				ok: function () {
					var remark = $("#rejectReason").val();
					if (remark == null || remark=='') {
						art.dialog.alert("请输入驳回原因!");
					}else{		
						realRejectApply(remark,obj);
					}
				},
				cancel: true
			});
			
			
		});	
		
});

function analyticalData($this) {
	var $input = $this.closest("tr").find("td:first").find("input");
	var inputVal =  $input.val();
	inputVal = inputVal.split(",");
	return encodeURI("applyId="+inputVal[0]+"&contractCode="+inputVal[1]+"&loanCode="+inputVal[2]);
}

//退回方法声明
function grantSureBack(param,grantSureBackReason){
	if(param!=null && param!=undefined){
		param+='&BackReason='+grantSureBackReason;
	}
	$.ajax({
		type : 'post',
		url : ctx+'/borrow/zcj/grantSureBack',
		data :encodeURI(param),
		cache : false,
		dataType : 'json',
		async : false,
		success : function(result) {
			if(result){
				art.dialog.alert("单子退回成功",function () {
					// 跳转到列表页面
					window.location.reload();
				});
			}else{
				art.dialog.alert("单子退回失败");
			}
		},
		error : function() {
			art.dialog.alert('请求异常');
		}
	});
}

function realRejectApply(remark,obj){
	var $param = analyticalData($(obj));
	art.dialog({
		title : '消息',
		content : '确认驳回申请？',
		opacity : .1,
		lock : true,
		ok : function() {
			$.ajax({
				type : 'post',
				url : ctx+'/borrow/zcj/grantSureRejectBack',
				data :$param+"&flagStatus="+encodeURI("驳回门店冻结申请")+"&autoGrantResult="+encodeURI(remark),
				cache : false,
				dataType : 'json',
				async : false,
				success : function(result) {
					if(result){
						art.dialog.alert("驳回申请成功",function (){
							window.location.reload();
						});
						// 跳转到列表页面
					}else{
						art.dialog.alert("驳回申请失败");
						// 跳转到列表页面
					}
				},
				error : function() {
					art.dialog.alert('请求异常');
				}
			});
		},
		cancel : true
	});
}

(function($){
	  $.isBlank = function(obj){
	    return(!obj || $.trim(obj) === "") ? 0 : obj;
	  };
})(jQuery);

function setValue(count,contract,grant) {
	$("#totalNum").text(count);
	$("#totalContractMoney").text(fmoney(contract,2));
	$("#totalGrantMoney").text(fmoney(grant,2));
}

//选择其他的时候，填写退回原因
function SelectChange() {
    var selectText = $("#sel").find("option:selected").text();
    if (selectText != "其他") {
       $("#other").hide();
    } else {
	   $("#other").show(); 
    }
}

//格式化，保留两个小数点
function fmoney(s, n) {  
    n = n > 0 && n <= 20 ? n : 2;  
    s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
    var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];  
    t = "";  
    for (i = 0; i < l.length; i++) {  
        t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");  
    }  
    return t.split("").reverse().join("") + "." + r;  
}
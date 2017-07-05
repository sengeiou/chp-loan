<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript"
	src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/channel/goldcredit/GCGrantSure.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<script src="${context}/js/grant/grantSureDeal.js" type="text/javascript"></script>
<script src="${context}/js/grant/revisitStatusDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>放款确认明细</title>
<style type="text/css">
	.reqRed{
		color:red;
	}
</style>
<script type="text/javascript">
	$(function() {
		//获取table数据集
		var tlen = $("#tableList tbody>tr");
		//如果只有一条记录,则需要判断
		if(tlen.length==1){
			//获取tr的第二列
			var $td = $("#tableList tbody>tr").find("td:eq(1)");
			//第二列的文本内容
			var tdtext = $td.text();
			//如果=='-',则表示没有数据,则需要做以下操作,此行的第一个列增加colspan属性,删除之后的数据
			if(tdtext=='-'){
				//获取th的列数
				var theadtr = $("#tableList thead>tr").find("th");
				//
				var len = theadtr.length;
				$("#tableList tbody>tr").find("td:eq(0)").attr("colspan",len).css("text-align","center");
				tlen.children("td:gt(0)").remove();
			}
		}
		
		//全选checkAll
		/* $("#checkAll").click(function (){
			var $checkBox = $(":input[name='checkBoxItem']"),count = 0,contract = 0.00,grant = 0.00, audit = 0.00;
			$checkBox.prop("checked",this.checked);
			if ($(this).is(":checked")) {
				$checkBox.each(function(){
					count += 1;
					contract += parseFloat($.isBlank($(this).attr("contractAmount")),10);
					grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
					audit += parseFloat($.isBlank($(this).attr("loanAmount")),10);
				});
			}
			setValue(count,contract,grant,audit);
		}); */ 
		/* $(":input[name='checkBoxItem']").click(function (){
			var $checkBox = $(":input[name='checkBoxItem']:checked"),$box = $(":input[name='checkBoxItem']");
			var contract = parseFloat($("#hiddenContract").val(),10),
			audit = parseFloat($("#hiddenAudit").val(),10),
			grant = parseFloat($("#deductGrant").val(),10),
			count = parseInt($("#hiddenCount").val(),10);
			
			$("#checkAll").prop("checked", $checkBox.length == $box.length ? true : false);
			if ($(this).is(":checked")) {
				count += 1;
				grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
				contract += parseFloat($.isBlank($(this).attr("contractAmount")),10);
				audit += parseFloat($.isBlank($(this).attr("loanAmount")),10);
			} else {
				if ($checkBox.length == 0) {
					count = 0,
					grant = contract = audit = 0.00;
				} else {
					count -= 1;
					grant -= parseFloat($.isBlank($(this).attr("grantAmount")),10);
					contract -= parseFloat($.isBlank($(this).attr("contractAmount")),10);
					audit -= parseFloat($.isBlank($(this).attr("loanAmount")),10);
				}
			}
			setValue(count,contract,grant,audit);
		});  */
		loan.getstorelsit("storesName", "storeOrgId");
		revisitStatusObj.getRevisitStatusList("revisitStatusId","revisitStatus","revisitStatusName","");
		var message = '${message}';
		if (message) {
			art.dialog.alert(message);
		}
		// 批量取消金信标识
		//$("#btnCancelGoldCredit").click(function() {
		//	var borrowFlag = $(this).val();
		//	GCflagCancle(borrowFlag);
		//});
		// 点击清除，清除搜索页面中的数据，DOM
		$("#clearBt").click(
				function() {
					$(':input', '#grantSureForm')
							.not(':button,:submit, :reset').val('').removeAttr(
									'checked').removeAttr('selected');
				});
		// 导出客户信息表
		$("#btnExportCustomer")
				.click(
						function() {
							var idVal = "";
							var loanFlowQueryParam = $("#grantSureForm")
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
									+ "/channel/goldcredit/grantSure/exportCustomer?loanCodes="
									+ idVal + "&" + loanFlowQueryParam;
						});
		// 导出打款表
		$("#btnExportRemit")
				.click(
						function() {
							var idVal = "";
							var loanGrant = $("#grantSureForm").serialize();
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
									+ "/channel/goldcredit/grantSure/exportRemit?loanCodes="
									+ idVal + "&" + loanGrant;
						});

		// 汇总表导出
		$("#btnExportSummary")
				.click(
						function() {
							var idVal = "";
							var loanGrant = $("#grantSureForm").serialize();
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
									+ "/channel/goldcredit/grantSure/exportSummary?loanCodes="
									+ idVal + "&" + loanGrant;
						});
	});

	// 点击批量取消P2P,金信标识处理
	function GCflagCancle(borrowFlag) {
		var checkVal = null;
		var flagSet = "false";
		if ($(":input[name='checkBoxItem']:checked").length == 0) {
			art.dialog.alert("请选择要进行操作的数据!");
			return;
		} else {
			art.dialog.confirm("确定取消选中单子的" + borrowFlag + "标识么？",function (){
				$(":input[name='checkBoxItem']:checked").each(function() {
					// 获取要进行处理的单子
					var flag = $(this).attr('borrowTrusteeFlag');
					if (flag == borrowFlag) {
						if (checkVal == null) {
							checkVal = $(this).val();
						} else {
							// 将checkBox的值以';'分割传送到controller中
							checkVal += ";" + $(this).val();
						}
					} else {
						flagSet = "true";
					}
				});
				if (flagSet == "true") {
					art.dialog.alert("您选中的单子中，有不为" + borrowFlag + "的单子！请重新选择");
					return;
				} else {
					GCupdFlag(' ', checkVal);
				}
			});
		}
	}
	//修改单子的标识，同时返回列表
	function GCupdFlag(borrowFlag, checkVal) {
		$.ajax({
			type : 'post',
			url : ctx + '/channel/goldcredit/grantSure/asycUpdateFlowAttr',
			data : {
				'checkVal' : checkVal, //传递流程需要的参数
				'borrowFlag' : borrowFlag,
				'flagStatus':'取消金信标识',
				'grantSureBackReason':'取消金信标识'
			//单子标识
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if (result) {
					updateContractLoanInfo(checkVal,borrowFlag);
				}else{
					art.dialog.alert('修改失败！');
				}
			},
			error : function() {
				art.dialog.alert('请求异常！');
			}
		});
	}
	function updateContractLoanInfo(checkVal,borrowFlag){
		$.ajax({
			type : 'post',
			url : ctx + '/channel/goldcredit/grantSure/updateContractLoanInfo',
			data : {
				'checkVal' : checkVal, //传递流程需要的参数
				'borrowFlag' : borrowFlag,
				'flagStatus':'取消金信标识',
				'grantSureBackReason':'取消金信标识'
			//单子标识
			},
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if (result) {
					art.dialog.alert("修改成功",function (){
						window.location.reload();
					});
				}
			},
			error : function() {
				art.dialog.alert('请求异常！');
			}
		});
	}
	//单条取消金信标识
	function updateContractLoanInfoForOnly(param){
		$.ajax({
			type : 'post',
			url : ctx + '/channel/goldcredit/grantSure/updateContractLoanInfoForOnly',
			data : param,
			cache : false,
			dataType : 'json',
			async : false,
			success : function(result) {
				if (result) {
					art.dialog.alert("修改成功");
				}
			},
			error : function() {
				art.dialog.alert('请求异常！');
			}
		});
	}
	//点击办理
	function grantSureDeal(val) {
		window.location.href = ctx
				+ "/channel/goldcredit/grantSure/grantSureDeal?checkVal=" + val;
	}
	$(function (){
		function analyticalData($this) {
			var $input = $this.closest("tr").find("td:first").find("input");
			var inputVal =  $input.val();
			inputVal = inputVal.split(",");
			return encodeURI("applyId="+inputVal[0]+"&wobNum="+inputVal[1]+"&flowName="+inputVal[2]
			+"&token="+inputVal[3]+"&stepName="+inputVal[4]+"&contractCode="+inputVal[5]+"&loanCode="+inputVal[6]);
		}
		// 点击金信取消
	/* 	$(".cancleJxBtn1").click(function(){
			var $this = $(this);
			art.dialog.confirm("确定对单子进行金信取消么？",function (){
				 updFlag(encodeURI('财富'),$this);
		         $(this).prop("type",'hidden');
		         window.location.reload();
			});
		});	 */
	//驳回申请
		$(".btnRefuseApply").click(function () {
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
						url : ctx+'/channel/goldcredit/grantSure/grantSureRejectBack',
						data :$param+"&flagStatus="+encodeURI("驳回金信门店冻结申请")+"&autoGrantResult="+encodeURI(remark),
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
		// 点击退回
		$(".grantBackBtn").click(function(){
			$("#hiddenValue").val(analyticalData($(this)));
		    $(this).attr("data-target","#sureBack");
		});
		// 点击放款确认
		$(".grantSureBtn1").click(function(){
			var response="TO_DELIVERY_CARD";
			//判断是不是冻结是数据信息
			var $input = $(this).closest("tr").find("td:first").find("input");
			var frozenFlag =  $input.attr("frozenFlag");
			//回访状态
			var revisitStatus = $input.attr("revisitStatus");
			if(frozenFlag == '1') {
				art.dialog.alert("当前数据信息已经申请了门店冻结,请先取消门店冻结后方可操作!");
				return false;
			}
			//判断是否待回访
			if(revisitStatus != ''&& revisitStatus != null){
				if(revisitStatus == 0 || revisitStatus== -1){
					art.dialog.alert("不允许确认待回访的数据");
					return;
				}
			}
			var param=analyticalData($(this));
			art.dialog.confirm("确定对单子进行放款确认？",function (){
				if(param!=null && param!=undefined){
					param+='&response='+response;
				}
				// 放款确认，更改单子的借款状态以及对表进行查询，同时对历史表进行插入，
				$.ajax({
	    			type : 'post',
	    			url : ctx + '/channel/goldcredit/grantSure/dispatchFlowStatus',
	    			data :param,
	    			cache : false,
	    			async : false,
	    			success : function(result) {
	    				/* if (result)
	    					art.dialog.alert("放款确认成功！");
	    				else 
	    					art.dialog.alert("放款确认失败！"); */
		    		   if(result == false || result == 'false'){
			    			art.dialog.alert("确认放款失败!");
		    		    }else{
		    		    	art.dialog.alert("放款确认成功！",function () {
		    		    		window.location.reload();
		    		    	});
		    		    }
	    			},
	    			error : function() {
	    				 art.dialog.alert('请求异常，放款确认失败！');
	    			}
	    			 
	    		});
			});
		});
		// 点击确认退回原因,需要指定退回的节点
		$("#GCgrantSureBackBtn").click(function(){
			// 获得填写的退回原因，调用流程退回的处理方法，更新单子的状态以及合同表的待放款确认退回原因
			var grantSureBackReasonVal= $("#sel").find("option:selected").val();
			var grantSureBackReason= $("#sel").find("option:selected").text();
			// 要退回的流程节点,合同审核
			var response="TO_CONTRACT_CHECK";
			var param=$("#hiddenValue").val()+"&grantSureBackReasonCode="+grantSureBackReasonVal;
			if(grantSureBackReason=="其他"){
				grantSureBackReason=$.trim($("#textArea").val());
				if(grantSureBackReason==null || grantSureBackReason == '请填写其他退回原因！'||grantSureBackReason.length == 0){
					art.dialog.alert("退回原因不能为空！");
					return;
				}
			}
			if(param!=null && param!=undefined){
				param+='&response='+response;
			}
			grantSureBack(param,grantSureBackReason);
		});
		/* $("#textArea").blur(function (){
			var reasonVal = $.trim($(this).val());
			if (reasonVal.length == 0 || reasonVal == '')
				$(this).val("请填写其他退回原因！");
			if (reasonVal != null && reasonVal == '请填写其他退回原因！'){
				$(this).addClass('muted');
			}
		}); */
		$("#textArea").focus(function (){
			var reasonVal = $.trim($(this).val());
			if (reasonVal == '请填写其他退回原因！')
				$(this).val("");
			if (reasonVal != null && reasonVal == '请填写其他退回原因！'){
				$(this).removeClass('muted');
			}
		}); 
		 /* var frozenFlag = '${workItem.bv.frozenFlag}';
		 if (!frozenFlag) {
			 $("#btnRefuseApply").hide();
		 } */
		 // 更新单子标识方法
		    function updFlag(loanMarking,$this){
		    	var param = analyticalData($this);
		    	param+='&loanMarking='+loanMarking+"&flagStatus="+encodeURI("取消金信标识")+"&grantSureBackReason="+encodeURI('取消金信标识')+"&operateType=1&backFlag=0"+
		    	'&grantSureBackReason='+encodeURI("取消金信标识");
		    	$.ajax({
					type : 'post',
					url : ctx + '/channel/goldcredit/grantSure/grantUpdFlag',
					data :param,
					cache : false,
					dataType : 'json',
					async : false,
					success : function(result) {
						if(result){
							updateContractLoanInfoForOnly(param);
						}else{
							art.dialog.alert("修改失败");
						}
					},
					error : function() {
						art.dialog.alert('请求异常');
					}
				});
		    }
		
		$.popuplayer(".edit");
	});
	//退回方法声明
	function grantSureBack(param,grantSureBackReason){
		if(param!=null && param!=undefined){
			param+='&grantSureBackReason='+grantSureBackReason;
		}
		$.ajax({
			type : 'post',
			url : ctx+'/channel/goldcredit/grantSure/grantSureBack',
			data :encodeURI(param),
			cache : false,
			dataType : 'text',
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
	function checkAll(thi){
		var $checkBox = $(":input[name='checkBoxItem']"),count = 0,contract = 0.00,grant = 0.00, audit = 0.00;
		$checkBox.prop("checked",thi.checked);
		if (thi.checked==true) {
			$checkBox.each(function(){
				//alert($(this).attr("contractAmount"));
				count += 1;
				contract += parseFloat($.isBlank($(this).attr("contractMoney")),10);
				grant += parseFloat($.isBlank($(this).attr("grantAmount")),10);
				audit += parseFloat($.isBlank($(this).attr("loanAmount")),10);
			});
		}
		setValue(count,contract,grant,audit);
	}
	function setValue(count,contract,grant,audit) {
		$("#totalNum").text(count);
		$("#totalContractMoney").text(fmoney(contract,2));
		$("#totalGrantMoney").text(fmoney(grant,2));
		
		
		$("#totalNumber").val(count);
		$("#hiddenContract").val(contract);
		$("#hiddenGrant").val(grant);
	}
function page(n, s) {
   	if (n)
   		$("#pageNo").val(n);
   	if (s)
   		$("#pageSize").val(s);
   	$("#grantSureForm").attr("action","${ctx }/channel/goldcredit/grantSure/getGCGrantInfo");
   	$("#grantSureForm").submit();
   	return false;
}	
</script>

</head>
<body>
	<div class="control-group">
	<!-- 冻结驳回弹出框 -->
		<div id="rejectFrozen" style="display:none">
			<form method="post" action="">
				<table class="table1" cellpadding="0" cellspacing="0" border="0"
					width="100%">
					<tr valign="top">
						<td><label ><span style="color:red;">*</span>驳回理由：</label>
							<textarea id="rejectReason" maxlength="450" cols="55" class="textarea_refuse" ></textarea>
						</td>
					</tr>
				</table>		
			</form>
		</div>
		<form:form id="grantSureForm"
			action="${ctx }/channel/goldcredit/grantSure/getGCGrantInfo"
			modelAttribute="LoanFlowQueryParam" method="post">
			
	   		<input type="hidden" name="menuId" value="${param.menuId }">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
	   				<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
					<td><label class="lab">客户名称：</label>
					<form:input type="text" class="input_text178" path="customerName"></form:input></td>
					<td><label class="lab">合同编号：</label>
					<form:input type="text" class="input_text178" path="contractCode"></form:input></td>
					<td><label class="lab">门店：</label> <input type="text"
						class="input_text178" name="storeName" id="storesName"
						readonly="readonly" value="${LoanFlowQueryParam.storeName }" /> <i
						id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<form:input type="hidden" path="storeOrgIds"
						 id="storeOrgId" ></form:input></td>
				</tr>
				<tr>
					<td><label class="lab">证件号码：</label>
					<form:input type="text" class="input_text178" path="identityCode"></form:input></td>
					<td><label class="lab">产品：</label>
					<form:select class="select180" path="replyProductCode">
							<form:option value="">全部</form:option>
							<c:forEach items="${productList}" var="card_type">
								<form:option value="${card_type.productCode}">${card_type.productName}</form:option>
							</c:forEach>
						</form:select></td>
					<td><label class="lab">是否加急：</label>
					<form:select class="select180" path="urgentFlag">
							<form:option value="">全部</form:option>
							<c:forEach items="${fns:getDictList('jk_urgent_flag')}"
								var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
							</c:forEach>
						</form:select></td>

				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">是否追加借：</label>
					<form:select class="select180" path="additionalFlag">
							<form:option value="">全部</form:option>
							<form:option value="0">否</form:option>
							<form:option value="1">是</form:option>
						</form:select></td>


					<td><label class="lab">是否电销：</label>
					<form:select class="select180" path="telesalesFlag">
							<form:option value="">全部</form:option>
							<c:forEach items="${fns:getDictList('jk_telemarketing')}"
								var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
							</c:forEach>
						</form:select></td>
					<td><label class="lab">借款状态：</label>
					<form:select class="select180" path="loanStatusCode">
							<form:option value="">全部</form:option>
							<form:option value="1">门店申请冻结</form:option>
							<form:option value="65">待款项确认</form:option>
						</form:select></td>
				</tr>
				 <tr id="T2" style="display:none">
			  		<td><label class="lab">是否无纸化：</label> <form:select class="select180" path="paperLessFlag">
						<form:option value="">全部</form:option>
						<c:forEach items="${fns:getDictList('yes_no')}"
							var="yes_no">
							<form:option value="${yes_no.value}">${yes_no.label}</form:option>
						</c:forEach>
					</form:select></td>
					 <td><label class="lab">合同版本号：</label>
                   <form:select class="select180" path="contractVersion">
						<form:option value="">全部</form:option>
						     <c:forEach items="${fns:getDictList('jk_contract_ver')}"
									var="contract_vesion">
									<form:option value="${contract_vesion.value}">${contract_vesion.label}</form:option>
							 </c:forEach>			
						</form:select>
                	</td>
					<td><label class="lab">是否有保证人：</label> <form:select class="select180" path="ensureManFlag">
						<form:option value="">全部</form:option>
						<c:forEach items="${fns:getDictList('yes_no')}"
							var="yes_no">
							<form:option value="${yes_no.value}">${yes_no.label}</form:option>
						</c:forEach>
					</form:select></td>
			  </tr>
			   <tr id="T3" style="display:none">
			  		<td><label class="lab">是否登记失败：</label> <form:select class="select180" path="registFlag">
						<form:option value="">全部</form:option>
						<form:option value="1">成功</form:option>
						<form:option value="0">失败</form:option>
					</form:select></td>
					<td><label class="lab">是否加盖失败：</label> <form:select class="select180" path="signUpFlag">
						<form:option value="">全部</form:option>
						<form:option value="1">成功</form:option>
						<form:option value="0">失败</form:option>
					</form:select></td>
					<%-- <td><label class="lab">是否有保证人：</label><form:select class="select180" path="ensureManFlag">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('yes_no')}"
							var="yes_no">
							<form:option value="${yes_no.value}">${yes_no.label}</form:option>
						</c:forEach>
					</form:select></td> --%>
					<td><label class="lab">风险等级：</label>
					      <select name="riskLevel" class="select180">
					          <option value=''>全部</option>
			    	        <c:forEach items="${fns:getDictList('jk_loan_risk_level')}" var="item">
					           <option value="${item.value}"
									 <c:if test="${LoanFlowQueryParam.riskLevel==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
					         </c:forEach>
					     </select>
					     </td>
			  </tr>
			   <tr id="T4" style="display:none">
			  		<td><label class="lab">回访状态：</label> 
					     <form:input path="revisitStatusName" id="revisitStatusName" class="input_text178" readonly="true"/>
					     <form:hidden path="revisitStatus" id="revisitStatus"/>
						 <i id="revisitStatusId"  class="icon-search" style="cursor: pointer;"></i>
					</td>
					<td><label class="lab">审核日期：</label>
               		<input  name="checkTime"  id="checkTime"  
                 	value="<fmt:formatDate value='${LoanFlowQueryParam.checkTime}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
					</td>
			  </tr>
			</table>
			<div class="tright pr30 pb5">
				<input class="btn btn-primary" type="submit" value="搜索"></input>
				<button class="btn btn-primary" id="clearBt">清除</button>
				<div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="../../../../static/images/up.png" id="showMore"></img>
				</div>
			</div>
		</form:form>
	</div>

	<p class="mb5">
		<c:if test="${notContractAudit!='1'}">
		<button class="btn btn-small jkhj_grantgc_csexport" id="btnExportCustomer">客户信息表导出</button>
		</c:if>
		&nbsp;
		<button class="btn btn-small jkhj_grantgc_dkexport" id="btnExportRemit">打款表导出</button>
		&nbsp;
		<button class="btn btn-small jkhj_grantgc_hzexport" id="btnExportSummary">汇总表导出</button>
		&nbsp;
		<!-- <button class="btn btn-small jkhj_grantsure_gccancel" id="btnCancelGoldCredit" value="金信">批量金信取消</button>
		&nbsp; -->
		<button id="btnUpload" role="button" class="btn btn-small jkhj_grantgc_upload"
			data-target="#uploadAuditedModal" data-toggle="modal">上传回执结果</button>
		&nbsp;
		<button id="btnBackload" role="button" class="btn btn-small jkhj_grantgc_back_upload"
			data-target="#backUploadAuditedModal" data-toggle="modal">上传退回结果</button>
		放款总笔数：<label class="red" id="totalNum">0 </label>笔&nbsp; <input
			type="hidden" id="totalNumber" value="0" /> 放款总金额：<label class="red"
			id="totalGrantMoney">0.00 </label>元&nbsp; <input type="hidden"
			id="hiddenGrant" value="0.00" /> 合同总金额：<label class="red"
			id="totalContractMoney">0.00 </label>元 <input type="hidden"
			id="hiddenContract" value="0.00" />

	</p>
		<div>
    	 <sys:columnCtrl pageToken="grantSureList"></sys:columnCtrl>
    	</div>
  <div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;">
	   <table id="tableList"
		class="table  table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th>全选<input type = "checkbox" id="checkAll" onclick="javaScript:checkAll(this);"/></th>
				<th>合同编号</th>
				<th>门店</th>
				<th>共借人</th>
				<th>自然人保证人</th>
				<th>产品</th>
				<th>客户姓名</th>
				<th>证件号码</th>
				<th>期数</th>
				<th>合同金额</th>
				<th>放款金额</th>
				<th>开户行</th>
				<th>支行名称</th>
				<th>账号</th>
				<th>借款状态</th>
				<th>加急标识</th>
				<th>合同版本号</th>
				<th>是否无纸化</th>
				<th>是否有保证人</th>
				<th>是否登记失败</th>
				<th>是否加盖失败</th>
				<th>借款利率</th>
				<th>催收服务费</th>
				<th>是否电销</th>
				<th>渠道</th>
				<th>模式</th>
				<th>风险等级</th>
				<th>回访状态</th>
				<th>审核日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${ workItems!=null && fn:length(workItems)>0}">
				<c:set var="index" value="0" />
				<c:forEach items="${workItems}" var="item" varStatus="status">
					<c:set var="index" value="${index+1}" />
					<tr
						<c:if test="${fn:trim(item.backFlag) eq '1'}">
                   			class='reqRed'
             			</c:if>
             		>
						<td><input type="checkbox" name="checkBoxItem"
							borrowTrusteeFlag='${item.channelName}'
							loanCode='${item.loanCode }'
							contractMoney='<fmt:formatNumber value="${item.contractMoney}" pattern="#00.0#"/>'
							grantAmount='${item.lendingMoney}' frozenFlag = "${item.frozenFlag}" revisitStatus='${item.revisitStatus }'
							value='${item.applyId},${item.wobNum},${item.flowName},${item.token},${item.stepName},${item.contractCode},${item.loanCode}' />
							${status.count}
						</td>
						<td>${item.contractCode}</td>
						<td>${item.storeName}</td>
						<td>
							<c:if test="${item.loanInfoOldOrNewFlag eq '' || item.loanInfoOldOrNewFlag eq '0'}">
								${empty item.coborrowerName || item.coborrowerName == 'null' ? "" : item.coborrowerName}
							</c:if>
						</td>
						<td>${item.newCoboName}</td>
						<td>${item.replyProductName}</td>
						<td>${item.customerName}</td>
						<td>${item.identityCode}</td>
						<td>${item.replyMonth}</td>
						<td><fmt:formatNumber value='${item.contractMoney}'
								pattern="#,#00.00" /></td>
						<td><fmt:formatNumber value='${item.lendingMoney}'
								pattern="#,#00.00" /></td>
						<td>${item.depositBank}</td>
						<td>${item.bankBranchName}</td>
						<td>${item.bankAccountNumber}</td>
						<td>${item.loanStatusName}<c:if
								test="${item.frozenFlag == '1' }">
	             	(门店申请冻结)
	             </c:if>
						</td>
						<td><c:if test="${item.urgentFlag=='1'}">
								<span style="color: red">加急</span>
							</c:if></td>
						<td>${item.contractVersion }</td>
						<td>
							<c:if test="${item.paperLessFlag eq '1'}" var = 'paperLessFlag'>
								是
							</c:if>
							<c:if test="${!paperLessFlag}">
								否
							</c:if>
						</td>
						<td>
							<c:if test="${item.ensureManFlag eq '1'}" var = 'ensureManFlag'>
								是
							</c:if>
							<c:if test="${!ensureManFlag}">
								否
							</c:if>
						</td>
						<td>
							<c:if test="${item.registFlag eq '1'}">
								成功
							</c:if>
							<c:if test="${item.registFlag eq '0'}">
								<span style="color: red">失败</span>
							</c:if>
							<c:if test="${empty item.registFlag}"></c:if>
						</td>
						<td>
							<c:if test="${item.signUpFlag eq '1'}">
								成功
							</c:if>
							<c:if test="${item.signUpFlag eq '0'}">
								<span style="color: red">失败</span>
							</c:if>
							<c:if test="${empty item.signUpFlag}"></c:if>
						</td>
						<td><fmt:formatNumber value='${item.monthRate }'
								pattern="#,##0.000" /></td>
						<td><fmt:formatNumber value='${item.urgeServiceFee }'
								pattern="#,##0.00" /></td>
						<td>${item.telesalesFlag}</td>
						<td>${item.channelName}</td>
						<td></td>
						<td>${item.riskLevel}</td>
						<td>
			             	<c:choose>
			             	  <c:when test="${item.revisitStatus == '' || item.revisitStatus == null}">
                 			  </c:when>
			                  <c:when test="${item.revisitStatus == -1 }">
			                                                     失败
			                  </c:when>
			                  <c:when test="${item.revisitStatus == 0 }">
			                                                     待回访	
			                  </c:when>
			                  <c:when test="${item.revisitStatus == 1 }">
			                                                      成功
			                  </c:when>
			                </c:choose>
			             </td>
						 <td><fmt:formatDate value="${item.checkTime }"
							pattern="yyyy-MM-dd" /></td>
						<td style="position:relative">
						    <input type="button" class="btn btn_edit edit" value="操作"/>
						    <div class="alertset" style="display:none">
								   <c:if test="${item.frozenFlag == '1'}">
									 <button class=" btn_edit  btnRefuseApply" id="btnRefuseApply">驳回申请</button>
								   </c:if>
						           <button  class=" btn_edit  grantSureBtn1 jkhj_grantSureBtn1" id="grantSureBtn1">确认</button>   
						         <!--   <button class=" btn_edit  cancleJxBtn1 jkhj_cancleJxBtn1" id="cancleJxBtn1">金信取消</button><br /> -->
						        <input type="button" class=" btn_edit " onclick="showAllHisByLoanCode('${item.loanCode}')"  value="历史"></input> 
						        <c:if test="${notContractAudit=='1' && item.issplit == '0'}">
						          <button id="grantBackBtn" role="button" class=" btn_edit  grantBackBtn"  data-toggle="modal">退回</button>
						        </c:if> 
					        </div>
						</td>
						
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ workItems==null || fn:length(workItems)==0}">
				<tr>
					<td colspan="26" style="text-align: center;">没有待办数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	<c:if test="${workItems!=null || fn:length(workItems)>0}">
		<div class="page">${gcGrantSureList}</div>
	</c:if>
	</div>
	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="uploadAuditForm" role="form" enctype="multipart/form-data"
					method="post"
					action="${ctx}/channel/goldcredit/grantSure/importResult">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
						<input type="file" name="file" id="fileid" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
					</div>
					<div class="modal-footer">
						<input class="btn btn-primary" type="submit" value="确认">
						<button class="btn btn-primary" class="close" data-dismiss="modal">取消</button>
					</div>
			</div>
		</div>
		</form>
	</div>
	<div class="modal fade" id="backUploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="backUploadAuditForm" role="form" enctype="multipart/form-data"
					method="post"
					action="${ctx}/channel/goldcredit/grantSure/backImportResult">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">上传退回结果</h4>
					</div>
					<div class="modal-body">
						<input type="file" name="file" id="fileid" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
					</div>
					<div class="modal-footer">
						<input class="btn btn-primary" type="submit" value="确认">
						<button class="btn btn-primary" class="close" data-dismiss="modal">取消</button>
					</div>
			</div>
		</div>
		</form>
	</div>
	 <div class='modal fade'  id="sureBack" style="width:90%;height:90%">
       <div class="modal-dialog" role="document">
	       <div class="modal-content">
           <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="grantSureBack">待放款确认退回</h4>
		    </div>
       <div class="modal-body">
       	<input type = "hidden" id = "hiddenValue"/>
			<table class="table table-striped table-bordered table-condensed">
                <tr>
                    <td align="left"><label>退回原因</label></td>
                    <td align="left">
                      <select id="sel" class  = "select180">
                        <option value = "6">风险客户</option>
                        <option value = "7">客户原因放弃</option>
                        <option value = "9">其他</option>
                      </select>
                    </td>
                </tr>
                <tr id="other" style="display:none">
                    <td align="left"><label>其他退回原因</label></td>
                    <td align="left"><textarea id = "textArea" class="muted"   rows="" cols="" style='font-family:"Microsoft YaHei";' >请填写其他退回原因！</textarea></td>
                </tr>
            </table>
		</div>
            <div class="modal-footer"><button class="btn btn-primary" id="GCgrantSureBackBtn">确认</button>
            <button class="btn btn-primary" class="close" data-dismiss="modal" onclick="closeModal('sureBack')">取消</button></div>
		</div>
		</div>
		</div>
		 <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-400,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" type="text/javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/grant/grantAudit.js?version=2" type="text/javascript"></script>
<script type="text/javascript"
	src="${context}/js/payback/ajaxfileupload.js"></script>
<script type="text/javascript"
	src="${context}/js/pageinit/areaselect.js"></script>
<c:if test="${listFlag=='TG' }">
	<script type="text/javascript"
		src="${context}/js/trusteeship/trusteeGrantAudit.js"></script>
</c:if>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				loan.initCity("addrProvice", "addrCity", null);
				areaselect.initCity("addrProvice", "addrCity", null, $(
						"#addrCity").val(), null);
				loan.getstorelsit("storeName", "storeOrgId");
				loan.getOpenBank('中间人开户行','cautionerDepositBank',null);
			});
			var msg = "${message}";
			if (msg != "" && msg != null) {
				art.dialog.alert(msg);
			};	
			function page(n, s) {
				 var listFlag = $("#listFlag").val();
					if (n)
						$("#pageNo").val(n);
					if (s)
						$("#pageSize").val(s);
					$("#grantAuditForm").attr("action", "${ctx}/borrow/grant/grantAudit/grantAuditItem?listFlag="+listFlag);
					$("#grantAuditForm").submit();
					return false;
			}

</script>
<title>放款确认</title>
</head>
<body>
	<div class="control-group">
		<form:form
			action="${ctx }/borrow/grant/grantAudit/grantAuditItem?listFlag=${listFlag }"
			modelAttribute="LoanFlowQueryParam" id="grantAuditForm">
			<table class="table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<input id="pageNo" name="pageNo" type="hidden"　value="${workItems.pageNo}" />
				<input id="pageSize" name="pageSize" type="hidden"　value="${workItems.pageSize}" />
				<input type="hidden" id="listFlag" value="${listFlag }" />
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input type="text"
							class="input_text178" path="customerName"></form:input></td>
					<td><label class="lab">证件号码：</label> <form:input type="text"
							class="input_text178" path="identityCode"></form:input></td>
					<td><label class="lab">合同编号：</label> <form:input type="text"
							class="input_text178" path="contractCode"></form:input></td>
				</tr>
				<tr>
					<td><label class="lab">管辖城市：</label> <form:select
							class="select180" style="width:110px" path="provinceCode"
							id="addrProvice">
							<form:option value="">请选择</form:option>
							<c:forEach var="item" items="${provinceList}" varStatus="status">
								<form:option value="${item.areaCode }">${item.areaName}</form:option>
							</c:forEach>
						</form:select>-<form:select class="select180" style="width:110px"
							path="cityCode" id="addrCity">
							<form:option value="">请选择</form:option>
						</form:select></td>
					<td><label class="lab">门店：</label> <form:input type="text"
							id="storeName" class="input_text178" path="storeName"
							readonly="true"></form:input> <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i> <form:hidden
							path="storeOrgIds" id="storeOrgId" /></td>
					<c:if test="${listFlag!='TG' }">
						<td><label class="lab">渠道：</label> <form:select
								class="select180" path="channelCode">
								<form:option value="">请选择</form:option>
								<c:forEach items="${fns:getDictList('jk_channel_flag')}"
									var="card_type">
									<c:if
										test="${card_type.label=='P2P'|| card_type.label=='财富'|| card_type.label=='XT01' || card_type.label=='XT02'|| card_type.label==' '}">
										<form:option value="${card_type.value}">${card_type.label}</form:option>
									</c:if>
								</c:forEach>
							</form:select></td>
					</c:if>
				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">是否追加借：</label> <form:select
							class="select180" path="additionalFlag">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_add_flag')}"
								var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
							</c:forEach>
						</form:select></td>
					<td><label class="lab">放款时间：</label> <input
						id="lendingTimeStart" name="lendingTimeStart" type="text"
						class="Wdate input_text70" size="10"
						value="<fmt:formatDate value='${LoanFlowQueryParam.lendingTimeStart}' pattern='yyyy-MM-dd'/>"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'lendingTimeEnd\')}'})"
						style="cursor: pointer"></input>-<input id="lendingTimeEnd"
						name="lendingTimeEnd" type="text" class="Wdate input_text70"
						size="10"
						value="<fmt:formatDate value='${LoanFlowQueryParam.lendingTimeEnd}' pattern='yyyy-MM-dd'/>"
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'lendingTimeStart\')}'})"
						style="cursor: pointer"></input></td>
					<td><label class="lab">放款批次：</label> <form:select
							class="select180" path="grantBatchCode">
							<form:option value="">请选择</form:option>
							<c:forEach items="${grantPchList}" var="card_type">
								<form:option value="${card_type.grantBatch}">${card_type.grantBatch}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr id="T2" style="display: none">
					<td><label class="lab">放款途径：</label> <form:select
							class="select180" path="lendingWayCode">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_payment_way')}"
								var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
							</c:forEach>
						</form:select></td>

					<td>
						<label class="lab">放款银行：</label> <form:input type="text" id="cautionerDepositBank" readonly="true" class="input_text178" path="cautionerDepositBanks"></form:input>
				    	<i id="chooseOpenBankBtn"
						class="icon-search" style="cursor: pointer;"></i></td>
					<td><label class="lab">是否电销：</label> <form:select
							class="select180" path="telesalesFlag">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_telemarketing')}"
								var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr id="T3" style="display: none">
					<td><label class="lab">提交时间：</label> 
					<input  name="submissionDateStart"  id="submissionDateStart"  
                  value="<fmt:formatDate value='${LoanFlowQueryParam.submissionDateStart}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'submissionDateEnd\')}'})" style="cursor: pointer" ></input>-<input  name="submissionDateEnd" 
                  value="<fmt:formatDate value='${LoanFlowQueryParam.submissionDateEnd}' pattern='yyyy-MM-dd'/>"
                     type="text" class="Wdate input_text70" size="10" id="submissionDateEnd"  
                                        onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'submissionDateStart\')}'})" style="cursor: pointer" ></input></td>
					<td><label class="lab">提交批次：</label> <form:input type="text"
							class="input_text178" path="submissionBatch"></form:input></td>
					<td><label class="lab">回盘结果：</label> <form:select
							class="select180" path="receiptResult">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_loansend_result')}"
								var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
							</c:forEach>
						</form:select></td>
				</tr>
				<tr id="T4" style="display: none">
					<td><label class="lab">是否加急：</label> <form:select
							class="select180" path="urgentFlag">
							<form:option value="">请选择</form:option>
							<c:forEach items="${fns:getDictList('jk_urgent_flag')}"
								var="card_type">
								<form:option value="${card_type.value}">${card_type.label}</form:option>
							</c:forEach>
						</form:select></td>
				<c:if test="${listFlag=='TG' }">
  					<td>
  						<label class="lab">委托提现：</label>
                        <form:select class="select180" path="trustCash">
                			<option value="">请选择</option>
                			<c:forEach items="${fns:getDictList('jk_trust_status')}" var="card_type">
    							<option value="${card_type.value}" <c:if test="${LoanFlowQueryParam.trustCash == card_type.value}">selected=true</c:if>>${card_type.label}</option>
			                </c:forEach>
			          </form:select>
       				</td>
        		</c:if>
        		<td>
			    <label class="lab">借款产品：</label><form:select class="select180" path="replyProductName"><option value="">请选择</option>
			    <c:forEach items="${productList}" var="card_type">
								<form:option value="${card_type.productCode}">${card_type.productName}</form:option>
				 </c:forEach>
			    </form:select>
			    </td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input class="btn btn-primary" type="submit" value="搜索"
					id="searchBtn"></input>
				<button class="btn btn-primary" id="clearBtn">清除</button>
				<div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="../../../../static/images/up.png" id="showMore"></img>
				</div>
			</div>
		</form:form>
	</div>
	<p class="mb5">
		<button class="btn btn-small" id="auditBtn">批量审核</button>
		<button class="btn btn-small" id="daoBtn">导出excel</button>
		<c:if test="${listFlag!='TG' }">
			<button class="btn btn-small" id="updGrantYh">修改放款行</button>
			<button id="importAuditedModal2" role="button" class="btn btn-small"
				data-target="#uploadAuditedModal2" data-toggle="modal">导入平台回执</button>
		</c:if>
		<c:if test="${listFlag=='TG' }">
			<button class="btn btn-small" id="export1">导出委托提现excel</button>
			<button class="btn btn-small" id="export2">上传回盘结果（委托提现）</button>
			<button class="btn btn-small" id="trustcashOnline"
				style="display: none">线上委托提现</button>
		</c:if>
		<input type="hidden" id="hiddenTotalGrant" value="${totalGrantMoney}">
		<input type="hidden" id="hiddenNum" value="${totalItem}">
		&nbsp;&nbsp;
		 <label  style="color:red;">放款总金额：
		    <span id="grantMoneyText">
		  	 <fmt:formatNumber value='${totalGrantMoney}' pattern="#,##0.00" />
		  	</span>
		  	
		  	 <input type="hidden" id="numHid" value="0"/>
		  	 <input type="hidden" id="grantMoneyHid" value="0.00"/>
		  	 
		 </label>
		 <label class="lab" style="color:red;" >放款总笔数：
		   <span id="totalItem">
		     ${totalItem}
		     </span>
		   &nbsp;笔
		 </label>
	</p>
	<sys:columnCtrl pageToken="gauditList"></sys:columnCtrl>
	<div class="box5" style="overflow: auto; width: 100%;">
		<table id="tableList"
			class="table  table-bordered table-condensed table-hover ">
			<thead>
				<tr>
					<th><input type="checkbox" id="checkAll" /></th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>共借人</th>
					<th>证件号码</th>
					<th>门店名称</th>
					<th>账户名字</th>
					<th>借款产品</th>
					<th>合同金额</th>
					<th>放款金额</th>
					<th>放款失败金额</th>
					<th>催收服务费</th>
					<th>征信费</th>
					<th>信访费</th>
					<th>费用总计</th>
					<!-- <th>未划金额</th> -->
					<th>批复期限(月)</th>
					<c:if test="${listFlag!='TG' }">
					<th>放款账号</th>
					<th>开户行</th>
					<th>账户姓名</th>
					<th>放款途径</th>
					</c:if>
					<th>回盘时间</th>
					<th>回盘结果</th>
					<th>失败原因</th>
					<th>渠道</th>
					<th>加急标识</th>
					<th>是否电销</th>
					<th>放款批次</th>
					<th>放款日期</th>
					<th>提交批次</th>
					<th>提交日期</th>
					<c:if test="${listFlag=='TG' }">
						<th>委托提现状态</th>
						<th>委托提现结果</th>
						<th>委托提现失败原因</th>
					</c:if>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:if test="${ workItems!=null && fn:length(workItems.list)>0}">
					<c:set var="index" value="0" />
					<c:forEach items="${workItems.list}" var="item">
						<c:set var="index" value="${index+1}" />
						<tr>
							<td><input type="checkbox" name="checkBoxItem" 
								contractCode="${item.contractCode }" value='${item.applyId}' lendingMoney='${item.lendingMoney}'/>
							</td>
							<td>${item.contractCode}</td>
							<td>${item.customerName}</td>
							<td><c:if
									test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}">
                    			  ${item.coborrowerName}
            				</c:if></td>
							<td>${item.identityCode}</td>
							<td>${item.storeName}</td>
							<td>${item.custBankAccountName}</td>
							<td>${item.replyProductName}</td>
							<td><fmt:formatNumber value='${item.contractMoney}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.lendingMoney}'
									pattern="#,#00.00" /></td>
							<td><fmt:formatNumber value='${item.grantFailAmount}'
									pattern="#,#00.00" /></td>		
							<td><fmt:formatNumber value='${item.urgeServiceFee}'
									pattern="#,#00.00" /></td>
							<td><c:if test="${item.replyProductName=='农信借'}"><fmt:formatNumber value='${item.feeCredit}'
									pattern="#,##0.00"/></c:if></td>
							<td><c:if test="${item.replyProductName=='农信借'}"><fmt:formatNumber value='${item.feePetition}'
									pattern="#,##0.00"/></c:if></td>
							<td><fmt:formatNumber value='${item.feeSum}'
									pattern="#,##0.00"/></td>
							<%-- <td><fmt:formatNumber value='${item.unDeductMoney}'
									pattern="#,#00.00" /></td> --%>
							<td>${item.replyMonth}</td>
							<c:if test="${listFlag!='TG' }">
							<td>${item.lendingAccount}</td>
							<td>${item.cautionerDepositBank}</td>
							<td>${item.bankAccountName}</td>
							<td>${item.lendingWayName}</td>
							</c:if>
							<td><fmt:formatDate value="${item.grantBackDate}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${item.receiptResult}</td>
							<td>${item.failReason}</td>
							<td>${item.channelName}</td>
							<td><c:if test="${item.urgentFlag=='1'}">
									<span style="color: red">加急</span>
								</c:if></td>
							<td><c:if test="${item.telesalesFlag=='1'}">
									<span>是</span>
								</c:if></td>
							<td>${item.grantBatchCode}</td>
							<td><fmt:formatDate value="${item.lendingTime}"
							pattern="yyyy-MM-dd HH:mm:ss" /></td>
							<td>${item.submissionBatch}</td>
							<td>${item.submissionDate}</td>

							<c:if test="${listFlag=='TG' }">
								<td>${item.trustCash}</td>
								<td>${item.trustCashRtn}</td>
								<td>${item.trustCashFailure}</td>
							</c:if>

							<td><button class="btn_edit" value='${item.applyId}'
									name="jumpTo">办理</button>
								<button class="btn_edit"
									onclick="showHisByLoanCode('${item.loanCode}')">历史</button>
								<button class="btn_edit"
									onclick="showGrantHis('${item.contractCode}')">划扣历史</button></td>
						</tr>

					</c:forEach>

				</c:if>

				<c:if test="${ workItems==null || fn:length(workItems.list)==0}">
					<tr>
						<td colspan="18" style="text-align: center;">没有待办数据</td>
					</tr>
				</c:if>
			</tbody>
		</table>
	</div>

	<div id="trustRechargeDiv" style="display: none">
		<table class=" table4" cellpadding="0" cellspacing="0" border="0"
			id="tpTable" width="380px">
			<tr style="display: none;">
				<td width="380px"><label class="lab"></label> <input
					type="radio" name="trustRd" value="1"
					onclick="javascript:diahide();">导出&nbsp;&nbsp;&nbsp; <input
					type="radio" name="trustRd" value="2" checked="checked"
					onclick="javascript:diashow();">上传&nbsp;&nbsp;&nbsp;</td>
			</tr>
			<tr>
				<td width="380px"><label class="lab">文件格式：</label><select
					class="select78"><option>Excel</option></td>
			</tr>
			<form id="fileForm" enctype="multipart/form-data">
				<tr id="DT">
					<td width="380px"><label class="lab"></label><input
						type='file' name="file" id="fileid"></td>
				</tr>
			</form>
		</table>
	</div>
	
	<!-- 上传回执结果，中金通联 -->
	<div class="modal fade" id="uploadAuditedModal2" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="uploadAuditForm2" role="form"
					enctype="multipart/form-data" method="post"
					action="${ctx}/borrow/grant/grantAudit/importGrantAudit">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
						<table class=" table1" cellpadding="0" cellspacing="0" border="0"
							width="100%">
							<tr>
								<td><label class="lab"><span style="color: red">*</span>放款途径：</label>
									<select class="select180 required" id="lendingWay"
									name="lendingWay">
										<option value="">请选择</option>
										<c:forEach items="${fns:getDictList('jk_payment_way')}"
											var="card_type">
											<c:if
												test="${card_type.label=='中金' || card_type.label=='通联'}">
												<option value="${card_type.value}">${card_type.label}</option>
											</c:if>
										</c:forEach>
								</select></td>
							</tr>
							<tr id="templateTypeTr" style="display: none;">
								<td><label class="lab"><span id="templateTypeId"
										style="color: red">*</span>模板类型：</label> <span> <input
										type="radio" name="templateType" value="TL01" />模板1 <input
										type="radio" name="templateType" value="TL02" />模板2
								</span></td>
							</tr>
							<tr>
								<td><span style="color: red">*</span> <input type='file'
									class="required" name="file" id="fileid2"></td>
							</tr>
						</table>
					</div>
					<div class="modal-footer">
						<input type="submit" class="btn btn-primary" class="close"
							value="确认" id="uploadAuditConfirmBtn"></input>
						<button class="btn btn-primary" class="close" data-dismiss="modal">取消</button>
					</div>
				</form>
			</div>
		</div>
</div>
	
	<div class="pagination">${workItems}</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>
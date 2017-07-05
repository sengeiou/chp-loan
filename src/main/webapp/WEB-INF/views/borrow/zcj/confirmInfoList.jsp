<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript"
	src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/zcj/confirmInfoList.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/channel/goldcredit/popuplayer.js"></script>
<script src="${context}/js/grant/revisitStatusDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>资产家大金融待款项确认列表</title>
<style type="text/css">
	.reqRed{
		color:red;
	}
</style>
<script type="text/javascript">
	$(function() {
		revisitStatusObj.getRevisitStatusList("revisitStatusId","revisitStatus","revisitStatusName","");
	})
	var message = '${message}';
	function page(n, s) {
	   	if (n)
	   		$("#pageNo").val(n);
	   	if (s)
	   		$("#pageSize").val(s);
	   	$("#zcjForm").attr("action","${ctx }/borrow/zcj/fetchTaskItems");
	   	$("#zcjForm").submit();
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
		<form:form id="zcjForm"
			action="${ctx }/borrow/zcj/fetchTaskItems"
			modelAttribute="LoanFlowQueryParam" method="post">
			<input type="hidden" id="pageNo" name="pageNo" value="${page.pageNo}" />
	   		<input type="hidden" id="pageSize" name="pageSize" value="${page.pageSize}" />
	   		<input type="hidden" name="menuId" value="${param.menuId }">
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">客户名称：</label>
					<input type="text" class="input_text178" id="customerName" name="customerName" value="${LoanFlowQueryParam.customerName}"></input></td>
					<td><label class="lab">合同编号：</label>
					<input type="text" class="input_text178" id="contractCode" name="contractCode" value="${LoanFlowQueryParam.contractCode}"></input></td>
					<td><label class="lab">门店：</label> <input type="text"
						class="input_text178" name="storeName" id="storesName"
						readonly="readonly" value="${LoanFlowQueryParam.storeName }" /> <i
						id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
						<input type="hidden" name="storeOrgIds"
						 id="storeOrgId"  value="${LoanFlowQueryParam.storeOrgId}"></input></td>
				</tr>
				<tr>
					<td><label class="lab">证件号码：</label>
					<input type="text" class="input_text178" id="identityCode" name="identityCode" value="${LoanFlowQueryParam.identityCode}"></input></td>
					<td><label class="lab">产品：</label>
					<select class="select180" id="replyProductCode" name="replyProductCode">
							<option value="">全部</option>
							<c:forEach items="${productList}" var="card_type">
								<option value="${card_type.productCode}" <c:if test="${card_type.productCode eq LoanFlowQueryParam.replyProductCode}">selected</c:if>>${card_type.productName}</option>
							</c:forEach>
						</select></td>
					<td><label class="lab">是否加急：</label>
					<select class="select180" id="urgentFlag" name="urgentFlag">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_urgent_flag')}"
								var="urgent_flag">
								<option value="${urgent_flag.value}" <c:if test="${urgent_flag.value eq LoanFlowQueryParam.urgentFlag}">selected</c:if>>${urgent_flag.label}</option>
							</c:forEach>
						</select></td>

				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">是否追加借：</label>
					<select class="select180" id="additionalFlag" name="additionalFlag">
							<option value="">全部</option>
							<option value="0" <c:if test="${'0' eq LoanFlowQueryParam.additionalFlag}">selected</c:if>>否</option>
							<option value="1" <c:if test="${1 eq LoanFlowQueryParam.additionalFlag}">selected</c:if>>是</option>
						</select></td>


					<td><label class="lab">是否电销：</label>
					<select class="select180" id="telesalesFlag" name="telesalesFlag">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_telemarketing')}"
								var="telesalesFlag">
								<option value="${telesalesFlag.value}" <c:if test="${telesalesFlag.value eq LoanFlowQueryParam.telesalesFlag}">selected</c:if>>${telesalesFlag.label}</option>
							</c:forEach>
						</select></td>
					<td><label class="lab">借款状态：</label>
					<select class="select180" id="loanStatusCode" name="loanStatusCode">
							<option value="">全部</option>
							<option value="1" <c:if test="${1 eq LoanFlowQueryParam.loanStatusCode}">selected</c:if>>门店申请冻结</option>
							<option value="65" <c:if test="${65 eq LoanFlowQueryParam.loanStatusCode}">selected</c:if>>待款项确认</option>
						</select></td>
				</tr>
				 <tr id="T2" style="display:none">
			  		<td><label class="lab">是否无纸化：</label> <select class="select180" id="paperLessFlag" name="paperLessFlag">
						<option value="">全部</option>
						<c:forEach items="${fns:getDictList('yes_no')}"
							var="yes_no">
							<option value="${yes_no.value}" <c:if test="${yes_no.value eq LoanFlowQueryParam.paperLessFlag}">selected</c:if>>${yes_no.label}</option>
						</c:forEach>
					</select></td>
					 <td><label class="lab">合同版本号：</label>
                   <select class="select180" id="contractVersion" name="contractVersion">
						<option value="">全部</option>
						     <c:forEach items="${fns:getDictList('jk_contract_ver')}"
									var="contract_vesion">
									<option value="${contract_vesion.value}" <c:if test="${contract_vesion.value eq LoanFlowQueryParam.contractVersion}">selected</c:if>>${contract_vesion.label}</option>
							 </c:forEach>			
						</select>
                	</td>
					<td><label class="lab">是否有保证人：</label> <select class="select180" id="ensureManFlag" name="ensureManFlag">
						<option value="">全部</option>
						<c:forEach items="${fns:getDictList('yes_no')}"
							var="yes_no">
							<option value="${yes_no.value}" <c:if test="${yes_no.value eq LoanFlowQueryParam.ensureManFlag}">selected</c:if>>${yes_no.label}</option>
						</c:forEach>
					</select></td>
			  </tr>
			   <tr id="T3" style="display:none">
			  		<td><label class="lab">是否登记失败：</label> <select class="select180" id="registFlag" name="registFlag">
						<option value="">全部</option>
						<option value="1" <c:if test="${'1' eq LoanFlowQueryParam.registFlag}">selected</c:if>>成功</option>
						<option value="0" <c:if test="${'0' eq LoanFlowQueryParam.registFlag}">selected</c:if>>失败</option>
					</select></td>
					<td><label class="lab">是否加盖失败：</label> <select class="select180" id="signUpFlag" name="signUpFlag">
						<option value="">全部</option>
						<option value="1" <c:if test="${'1' eq LoanFlowQueryParam.signUpFlag}">selected</c:if>>成功</option>
						<option value="0" <c:if test="${'0' eq LoanFlowQueryParam.signUpFlag}">selected</c:if>>失败</option>
					</select></td>
					<%-- <td><label class="lab">是否有保证人：</label><form:select class="select180" path="ensureManFlag">
						<form:option value="">请选择</form:option>
						<c:forEach items="${fns:getDictList('yes_no')}"
							var="yes_no">
							<form:option value="${yes_no.value}">${yes_no.label}</form:option>
						</c:forEach>
					</form:select></td> --%>
					<td><label class="lab">风险等级：</label>
					      <select id="riskLevel" name="riskLevel" class="select180">
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
				<input class="btn btn-primary" type="button" id="clearBt" value="清除"></button>
				<div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="../../../static/images/up.png" id="showMore"></img>
				</div>
			</div>
		</form:form>
	</div>

	<p class="mb5">
		 
		<button class="btn btn-small zcj_csexport" id="btnExportCustomer">客户信息表导出</button>
		&nbsp;
		<button class="btn btn-small zcj_dkexport" id="btnExportRemit">打款表导出</button>
		&nbsp;
		<button class="btn btn-small zcj_hzexport" id="btnExportSummary">汇总表导出</button>
		&nbsp;
		<button id="btnUpload" role="button" class="btn btn-small zcj_upload"
			data-target="#uploadAuditedModal" data-toggle="modal">上传回执结果</button>
      
		放款总笔数：<label class="red" id="totalNum">0 </label>笔&nbsp; <input
			type="hidden" id="totalNumber" value="0" /> 放款总金额：<label class="red"
			id="totalGrantMoney">0.00 </label>元&nbsp; <input type="hidden"
			id="hiddenGrant" value="0.00" /> 合同总金额：<label class="red"
			id="totalContractMoney">0.00 </label>元 <input type="hidden"
			id="hiddenContract" value="0.00" />

	</p>
    	 <sys:columnCtrl pageToken="grantSureList"></sys:columnCtrl>
  <div class="box5">
	   <table id="tableList"
		class="table  table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th><input type = "checkbox" id="checkAll"/>全选</th>
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
				
				<!-- <th>借款利率</th>
				<th>催收服务费</th>
				
				<th>渠道</th>
				<th>模式</th> -->
				<th>是否电销</th>
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
					<tr <%-- || item.frozenFlag == '1' --%>
						<c:if test="${fn:trim(item.backFlag) eq '1'}">
                   class="reqRed"
             </c:if>>
						<td><input type="checkbox" name="checkBoxItem" 
							borrowTrusteeFlag='${item.channelName}'
							loanCode='${item.loanCode }'
							contractMoney='<fmt:formatNumber value="${item.contractMoney}" pattern="#00.0#"/>'
							grantAmount='${item.lendingMoney}' frozenFlag = "${item.frozenFlag}" revisitStatus='${item.revisitStatus }'
							value='${item.applyId},${item.contractCode},${item.loanCode}' urgentFlag = '${item.urgentFlag }' />
							${status.count}
						</td>
						<td>${item.contractCode}</td>
						<td>${item.storeName}</td>
						<td><c:if test="${item.loanInfoOldOrNewFlag ne '1'}">
								${empty item.coborrowerName || item.coborrowerName == 'null' ? "" : item.coborrowerName}
							</c:if></td>
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
						
						<%-- <td><fmt:formatNumber value='${item.monthRate }'
								pattern="#,##0.000" /></td>
						<td><fmt:formatNumber value='${item.urgeServiceFee }'
								pattern="#,##0.00" /></td>
						
						<td>${item.channelName}</td>
						<td></td> --%>
						<td>${item.telesalesFlag}</td>
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

								<%----  样式控制按钮权限 不能修改  --%>
								<c:if test="${item.frozenFlag == '1'}">
									<button class="btn btn_edit zcj_bh" id="btnRefuseApply">驳回申请</button>
								</c:if>
								<c:if test="${item.frozenFlag != '1'}">
						        	<button  class="btn btn_edit zcj_grantSureBtn1" id="zcjSureBtn1">确认</button>     
						        </c:if>
						        <button  class="btn btn_edit zcj_history" onclick="showAllHisByLoanCode('${item.loanCode}')"   >历史</button>
						        <c:if test="${item.issplit == '0'}"> 
						        <button id="zcjBackBtn"   class="btn btn_edit zcj_BackBtn"  data-toggle="modal">退回</button>
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
	</div>
	<c:if test="${workItems!=null || fn:length(workItems)>0}">
		<div class="page">${zcjPage}</div>
	</c:if>
	
	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<form id="uploadAuditForm" role="form" enctype="multipart/form-data"
					method="post"
					action="${ctx}/borrow/zcj/importResult">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
					</div>
					<div class="modal-body">
						<input type='file' name="file" id="fileid">
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
                      <select id="sel" class  = "select180" onchange="javaScript:SelectChange();">
                        <option value = "6">风险客户</option>
                        <option value = "7">客户原因放弃</option>
                        <option value = "9">其他</option>
                      </select>
                    </td>
                </tr>
                <tr id="other" style="display:none">
                    <td align="left"><label>其他退回原因</label></td>
                    <td align="left"><textarea id = "textArea" class="muted"   rows="" cols="" style='font-family:"Microsoft YaHei";' ></textarea></td>
                </tr>
            </table>
		</div>
            <div class="modal-footer"><button class="btn btn-primary" id="GCgrantSureBackBtn">确认</button>
            <button class="btn btn-primary" class="close" data-dismiss="modal" onclick="closeModal('sureBack')">取消</button></div>
		</div>
		</div>
		</div>
		 <!-- <script type="text/javascript">

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
	</script> -->
</body>
</html>
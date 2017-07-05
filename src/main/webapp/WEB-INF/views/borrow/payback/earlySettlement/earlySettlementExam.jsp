<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/moment.js" type="text/javascript"></script>
<script src="${ctxStatic}/bootstrap/3.3.5/js/daterangepicker.js" type="text/javascript"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<script type="text/javascript" src="${context}/js/payback/earlySettlementExam.js"></script>
<link href="${ctxStatic}/bootstrap/3.3.5/awesome/daterangepicker-bs3.css" type="text/css" rel="stylesheet" />
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
</head>
<body>
 <div class="body_r">
    <div class="control-group">
    <form id="auditForm" method="post" action="${ctx}/borrow/payback/earlySettlement/list">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
        		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
				<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
            <tr>
                <td><label class="lab">客户名称：</label>
                	<input type="text" class="input_text178" name="loanCustomer.customerName" value="${paybackCharge.loanCustomer.customerName }"></td>
                <td><label class="lab">合同编号：</label>
                	<input type="text" class="input_text178" name="contractCode" value="${paybackCharge.contractCode }"></td>
                <td><label class="lab">门店：</label>
	                <input id="txtStore" name="store" type="text" maxlength="20" class="txt date input_text178" value="${paybackCharge.store }" /> 
					<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore" name='storeyc' value="${paybackCharge.storeyc}">
                </td>
            </tr>
            <tr>
                <td><label class="lab">来源系统：</label>
                	<select class="select180" name="loanInfo.dictSourceType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${paybackCharge.loanInfo.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                              </c:forEach>
                	</select> 
                </td>
                <td><label class="lab">渠道：</label>
                <select class="select180" name="loanInfo.loanFlag">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${paybackCharge.loanInfo.loanFlag == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                	</select>
                </td>
				<td><label class="lab">还款日：</label> 
				<select name="payback.paybackDay" class="select180">
						        <option value="">请选择</option>
						<c:forEach var="day" items="${dayList}">
								<option value="${day.billDay}"
								<c:if test="${paybackCharge.payback.paybackDay==day.billDay }">selected</c:if>>${day.billDay}</option>
						</c:forEach>
				</select>
			   </td>
            </tr>
            
             <tr id="T1"  style="display: none">
                <td><label class="lab">合同版本号：</label>
                 <select class="select180" name="contract.contractVersion">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="contractVersion">
                                   <option value="${contractVersion.value }" <c:if test="${paybackCharge.contract.contractVersion == contractVersion.value }">selected</c:if>>${contractVersion.label }</option>
                              </c:forEach>
                	</select> 
                </td>
                
                  <td>
					      <label class="lab">模式：</label>
					      	<select class="select180" name="loanInfo.model">
		                     <option value="">请选择</option>
				              <c:forEach var="flag" items="${fns:getDictList('jk_loan_model')}">
							    <option value="${flag.value }"
								<c:if test="${paybackCharge.loanInfo.model==flag.value }">selected</c:if>>
								<c:if test="${flag.value=='1' }">${flag.label}</c:if>
								<c:if test="${flag.value!='1' }">非TG</c:if>
							</option>
						   </c:forEach>
		                    </select>
		             </td>
            </tr>
        </table>
        </form>
       	<div class="tright pr30 pb5">
            <button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
        	<button class="btn btn-primary" id="clearBtn">清除</button>
      	    <div style="float: left; margin-left: 45%; padding-top: 10px">
		     <img src="${context }/static/images/up.png"
			id="showMore"  onclick ="show()"></img>
	    </div>
        </div>
    
    </div>
    <div>
    <table id="tableList">
      <thead>
        <tr>
			<th>序号</th> 
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>合同到期日</th>
            <th>合同版本号</th>	   
            <th>门店名称</th>
            <th>借款状态</th>
            <th>催收服务费</th>
            <th>违约金(滞纳金)及罚息总额</th>
            <th>应还款金额</th>
            <th>申请还款金额</th>
            <th>还款日</th>
            <th>还款类型</th>
            <th>还款状态</th>
            <th>渠道</th>
            <th>模式</th>
            <th>操作</th>
        </tr>
        </thead>
        <c:forEach items="${waitPage.list }" var="paybackCharge" varStatus="status">
        <tr>
			<td>${status.count }</td>
            <td>${paybackCharge.contractCode }</td>
            <td>${paybackCharge.loanCustomer.customerName }</td>
            <td>
                <fmt:formatDate value="${paybackCharge.contract.contractEndDay }" type="date" />
		    </td>
		    <td>${paybackCharge.contract.contractVersionLabel }</td>
            <td>${paybackCharge.loanInfo.loanStoreOrgName }</td>
            <td>${paybackCharge.loanInfo.dictLoanStatusLabel}</td>
            <td>
               <fmt:formatNumber value='${paybackCharge.urgeServicesMoney.urgeMoeny }' pattern="#,##0.00" /> 
            </td>
            <td>
                <fmt:formatNumber value='${paybackCharge.penaltyTotalAmount}' pattern="#,##0.00" /> 
            </td>
            <td>
                 <fmt:formatNumber value='${paybackCharge.settleTotalAmount}' pattern="#,##0.00" /> 
            </td>
			<td>
			   <fmt:formatNumber value='${paybackCharge.applyBuleAmount}' pattern="#,##0.00" /> 
			</td>
			<td>
			  ${paybackCharge.payback.paybackDay }
			</td>   
			<td>${paybackCharge.dictOffsetTypeLabel}</td>
            <td>${paybackCharge.payback.dictPayStatusLabel}</td>
            <td>${paybackCharge.loanInfo.loanFlagLabel}</td>
             <td>${paybackCharge.loanInfo.modelLabel}</td>
            <td class="tcenter">
            <input type="button" class="btn_edit"
             onclick="JavaScript:window.location='${ctx}/borrow/payback/earlySettlement/getEarlyBackApply?id=${paybackCharge.id }&contractCode=${paybackCharge.contractCode }'"
             value="办理" />
            <input type="hidden" value="${paybackCharge.id }"/>
            <!--  <button type="button" class="btn_edit" data-toggle="modal" data-target="#examBackDiv" >退回</button> -->
             
			<input type="hidden" value="${paybackCharge.payback.id }"/>
			  <input type="button" class="btn_edit" value="历史"  onclick="showNoDeductPaybackHistory('','${paybackCharge.id }','');" />
			</td>
        </tr>
        </c:forEach>
    </table>
    <div class="pagination">${waitPage}</div>
	</div>
	
<div class="modal fade" id="examBackDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">  
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">批量退回</h4>
				</div>
				<div class="box1 mb10">  
					<table id="backTB" class="table4" cellpadding="0"
						cellspacing="0" border="0" width="100%">
						<tr>
							<td>
							<input type="hidden"  id="id" />
							<input type="hidden"  id="pId" />
							<lable class="lab"><span style="color: red;">*</span>退回原因：</lable> 
								 <textarea class="form-control" rows="3" id="returnReason"></textarea>
								 <span style="color: red;margin-top:20px">最多输入1500字符</span>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal" id="backConfirmBtn">提交更改</a>
					<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-200,
			striped: true,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>
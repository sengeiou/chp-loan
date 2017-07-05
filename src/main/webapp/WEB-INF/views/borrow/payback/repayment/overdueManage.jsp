<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>逾期管理</title>
<script type="text/javascript" src="${context}/js/payback/overdueManage.js"></script>
<script type="text/javascript" src="${context}/js/payback/overdueManagePage.js"></script>
<script src='${context}/js/common.js' type="text/javascript"></script>
<script src="${context}/js/payback/historicalRecords.js" type="text/javascript"></script>
<script>

$(document).ready(function() {
	overduemanage.overduemanage();
});

</script>
</head>
<body>
<div >
    <div class="control-group">
    <form id="auditForm" modelAttribute="OverdueManageEx" method="post" action="${ctx}/borrow/payback/overduemanage/allOverdueManageList">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" > </input>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" ></input>
			<input id="msg" type="hidden" value="${message}" ></input>
			<input id="ids" name="id" type="hidden"></input>
          <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
             <tr>
                <td><label class="lab">客户姓名：</label>
                	<input value="${OverdueManageEx.customerName }" type="text" class="input_text178" name="customerName"></input></td>
                <td><label class="lab">合同编号：</label>
                	<input value="${OverdueManageEx.contractCode }" type="text" class="input_text178" name="contractCode"></input></td>
				<td><label class="lab">门店： </label>
					<input id="txtStore" name="orgName" type="text" maxlength="20" class="txt date input_text178" value="${OverdueManageEx.orgName }" /> 
					<i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
					<input type="hidden" id="hdStore"  name='orgIdyc' value="${OverdueManageEx.orgIdyc}">
				</td>
            </tr>
            <tr> 
			    <td><label class="lab">期供状态：</label>
			    	<select class="select180" name="dictMonthStatus">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_period_status')}" var="dictMonthStatus">
                                   <option value="${dictMonthStatus.value }" <c:if test="${OverdueManageEx.dictMonthStatus == dictMonthStatus.value }">selected</c:if>>${dictMonthStatus.label }</option>
                              </c:forEach>
                	</select>
			    </td>
				<td ><label class="lab">来源系统：</label>
					<select class="select180" name="dictSourceType">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                   <option value="${dictSourceType.value }" <c:if test="${OverdueManageEx.dictSourceType == dictSourceType.value }">selected</c:if>>${dictSourceType.label }</option>
                              </c:forEach>
                	</select>
				</td>
				<td>
				<label class="lab">银行：</label>
				<sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
                <input id="search_applyBankName" type="text" class="input_text178" name="bankName" value='${OverdueManageEx.bankName}'>&nbsp;<i id="selectBankBtn"
						class="icon-search" style="cursor: pointer;"></i> 
						<input type="hidden" id="bankId" name='bankyc' value='${OverdueManageEx.bankyc}'>
                </td>
                
            </tr>
            <tr id="T1" style="display: none;">
            	<td><label class="lab">模式：</label>
            		<select name="model" class="select180">
								<option value=''>请选择</option>
								<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                   <option value="${loanmodel.value }" <c:if test="${OverdueManageEx.model == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                   	非TG
	                                   </c:if>
	                                   <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                   </option>
	                            </c:forEach>
                       </select>
                </td>
			   <td ><label class="lab">渠道：</label>
			   		<select class="select180" name="loanMark">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="loanMark">
                                   <option value="${loanMark.value }" <c:if test="${OverdueManageEx.loanMark == loanMark.value }">selected</c:if>>${loanMark.label }</option>
                              </c:forEach>
                	</select>
			   </td>
				<td><label class="lab">逾期天数：</label>
					<input  value="${OverdueManageEx.monthOverdueDays }" type="number" name="monthOverdueDays" id="monthOverdueDays" class="input_text178">
				</td>
            </tr>
            <tr id="searchHidden" style="display: none;">
			   	<td><label class="lab">逾期日期：</label>
			   			<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${OverdueManageEx.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
						</label>-<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${OverdueManageEx.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
					
			   	</td>
            </tr>
        </table>
          </form>
     
         <div class="tright pr30 pb5">
             <button onclick="document.forms[0].submit();" class="btn btn-primary" >搜索</button>
             <button id="clearBtn" class="btn btn-primary" />清除</button>
             <div style="float: left; margin-left: 45%; padding-top: 10px">
				<img src="${context}/static/images/up.png" id="showMore"></img>
			</div>
        </div>
   
     </div> 
	<p class="mb5">
			<!--  <button class="btn_sc" id="checkAll1">全选</button>-->
			<button class="btn btn-small"   onclick="selectAll(this)">全选</button>
			<button class="btn btn-small" id="centerApply">批量减免</button>
			<button class="btn btn-small" name = "exportExcel">导出Excel</button>
	</p>
 <div  class="box5 ">
    <table id="overDueTab">
         <thead>
        <tr>
             <th></th>
            <th>序号</th>
            <th>门店名称</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>银行</th>
            <th>逾期日期</th>
            <th>期供金额</th>
            <th>逾期天数</th>
            <th>违约金(滞纳金)及罚息总额</th>
            <th>实还期供金额</th>
            <th>已还违约金(滞纳金)及罚息金额</th>
            <th>未还违约金(滞纳金)及罚息金额</th>
            <th>蓝补金额</th>
            <th>借款状态</th>
            <th>期供状态</th>
            <th>减免人</th>
            <th>减免天数</th>
            <th>减免金额</th>
            <th>是否电销</th>
            <th>渠道</th>
			<th>模式</th>
            <th>操作</th>
        </tr>
        </thead>
       <c:forEach items="${waitPage.list}" var="item" varStatus="num">
					<tr>
					     <td>
				            <input type="checkbox" name="checkBox"   value="${item.id}" />
				            <input type="hidden" name="monthOverdueDays" value="${item.monthOverdueDays}"/>
				         </td>
						<td>${num.index + 1 }</td>
						<td>${item.orgName}</td>
						<td>${item.contractCode}</td>
					    <td>${item.customerName}</td>
						<td>${item.bankNameLabel}</td>
						<td><fmt:formatDate value="${item.monthPayDay}" type="date"/></td>
						<td><fmt:formatNumber value='${item.contractMonthRepayAmount}'  pattern="#,##0.00"/></td>
						<td>${item.monthOverdueDays-item.monthReductionDay}</td>
						<td><fmt:formatNumber value='${item.penaltyAndShould}'  pattern="#,##0.00"/></td>
						<td><fmt:formatNumber value='${item.alsocontractMonthRepay}'  pattern="#,##0.00"/></td>
						<td><fmt:formatNumber value='${item.alsoPenaltyInterest}'  pattern="#,##0.00"/></td>
						<td><fmt:formatNumber value='${item.noPenaltyInterest}'  pattern="#,##0.00"/></td>
						<td><fmt:formatNumber value='${item.paybackBuleAmount}'  pattern="#,##0.00"/></td>
						<td>${item.dictLoanStatusLabel}</td>
						<td>${item.dictMonthStatusLabel}</td>
						<td>${item.reductionBy}</td>
						<td>${item.monthReductionDay}</td>
						<td><fmt:formatNumber value='${item.reductionAmount}'  pattern="#,##0.00"/></td>
						<td>${item.customerTelesalesFlagLabel}</td>
						<td>${item.loanMarkLabel}</td>
						<td>${item.modelLabel}</td>
						<td>
						<c:if test="${item.dictMonthStatus== 2}">
						   <button class="btn_edit" name="reductionAmount" value="${item.id }" role="button" data-toggle="modal">减免</button>
						</c:if>
						<c:if test="${item.dictMonthStatus == 2 || item.dictMonthStatus == 1 }">
						   <button class="btn_edit" name="monthReductionDayss" value="${item.id }" role="button" data-toggle="modal">调整</button>
						</c:if>
						<input type="button" class="btn_edit" onclick="showNoDeductPaybackHistory('${item.rPaybackId}','','4');" value="历史" />
						</td>
					</tr>
		</c:forEach>
    </table>
	</div>
  <div class="pagination">${waitPage}</div>
 </div>
  <!-- 减免弹出框 -->
 <div id="myModals" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true">
        <div style="width: 400px;" class="modal-dialog" role="document">
        <div class="modal-content">
         <div class="modal-header">
         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4>设置减免金额</h4>
        	</div>
        <table class="f14 table2" cellpadding="0" cellspacing="0" border="0">
	   </table>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" >
            <tr>
                <td><label class="lab" style="width:132px">减免人：</label><input  class="input_text178" id="reductionBys" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:128px"><span class='red'>*</span>减免金额：</label>
              
                <input  class="input_text178"  id="reductionAmounts" value="" /></td>
            </tr>
            <tr>
              <td><label class="lab" style="width:128px"><span class='red'>*</span>减免原因：</label>
                  <textarea rows="" cols="" class="input_text178" id="reductionReson" style="input_text178"></textarea>
              </td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">银行名称：</label><input  class="input_text178" id="bankNames" readOnly="true" value=" " /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">借款人姓名：</label><input  class="input_text178" id="customerNames" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">合同编号：</label><input  class="input_text178" id="contractCode" readOnly="true" value="" /></td>
            </tr>
             <tr>
                <td><label class="lab" style="width:132px">逾期时间：</label><input  class="input_text178" id="dateStrings" readOnly="true" value="" /></td>
            </tr>
             <tr>
                <td><label class="lab" style="width:132px">逾期天数：</label><input  class="input_text178" id="monthOverdueDayss" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">逾期期供金额：</label><input type="text" class="input_text178" id="contractMonthRepayAmountLate" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">违约金(滞纳金)及罚息：</label><input  class="input_text178" id="penaltyAndShoulds" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">未还违约金(滞纳金)及罚息：</label><input  class="input_text178" id="noPenaltyInterest" readOnly="true" value="" /></td>
            </tr>
            <input type="hidden" id="rPaybackIds" value="" /><input type="hidden" id="ids" value="" />
           		<!--  <input type="hidden" id="monthPenaltyShould" value="" /><input type="hidden" id="monthInterestPunishshould" value="" />
           		 <input type="hidden" id="monthPenaltyReduction" value="" /><input type="hidden" id="monthPunishReduction" value="" />
			 --><!-- 应还滞纳金 -->
			  	<input type="hidden" id="monthLateFee" value="" />
			  	<!-- 应还罚息 -->
			  	<input type="hidden" id="monthInterestPunishshould" value="" />
           		 <!--减免滞纳金  -->
           		 <input type="hidden" id="monthLateFeeReduction" value="" />
           		 <!-- 减免罚息 -->
           		 <input type="hidden" id="monthPunishReduction" value="" />
           		 <!-- 实还滞纳金 -->
           		 <input type="hidden" id="actualMonthLateFee" value="" />
           		 <!-- 实还利息 -->
           		 <!-- <input type="hidden" id="monthInterestPayactual" value="" /> -->
           		 <!-- 实还罚息 -->
           		 <input type="hidden" id="monthInterestPunishactual" value="" />
			     <!-- 应还违约金 -->
			     <input type="hidden" id="monthPenaltyShould" value="" />
			     <!-- 实还违约金 -->
			     <input type="hidden" id="monthPenaltyActual" value="" />
           		 <!-- 减免违约金 -->
           		 <input type="hidden" id="monthPenaltyReduction" value="" /><input type="hidden" id="contractVersion" value="" />
           		 <input type="hidden" id="months" value=""/>
			<br/>
        </table><br/>
         <div class="modal-footer">
        <button style="margin-left: 220px;" class="btn btn-primary" id="confirms">确定</button><button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">关闭</button>
        </div>
 </div>
 </div>
 </div>
 
 <!-- 调整弹出框 -->
 <div id="myModal" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabe1l" aria-hidden="true" data-url="data">
        <div style="width: 400px;" class="modal-dialog" role="document">
        <div class="modal-content">
        <div class="modal-header">
         <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        	<h4>调整逾期天数</h4>
        </div>
        <div class="modal-body">
        <table class="table1" cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td><label class="lab" style="width:132px"><span class='red'>*</span>减免天数：</label>
                <input type="text" class="input_text178" id="reductionDay"/>
                <input type="hidden" class="input_text178" id="monthReductionDay" />
                </td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">银行名称：</label><input type="text" class="input_text178" id="bankName" readOnly="true" value=" " /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">银行账号：</label><input type="text" class="input_text178" id="bankAccount" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">银行开户行：</label><input type="text" class="input_text178" id="bankBranchs" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">借款人身份证号：</label><input type="text" class="input_text178" id="customerCertNum" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">借款人姓名：</label><input type="text" class="input_text178" id="customerName" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">借款编号：</label><input type="text" class="input_text178" id="loanCode" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">逾期时间：</label><input type="text" class="input_text178" id="dateString" readOnly="true" value="" /></td>
            </tr>
            <tr>                                                                                                             
                <td><label class="lab" style="width:132px">逾期期供金额：</label><input type="text" class="input_text178" id="contractMonthRepayAmountLates" readOnly="true" value="" /></td>
            </tr>
            <tr>
                <td><label class="lab" style="width:132px">违约金(滞纳金)及罚息：</label><input type="text" class="input_text178" id="penaltyAndShould" readOnly="true" value="" /></td>
            </tr><input type="hidden" id="rPaybackId" value="" /><input type="hidden" id="id" value="" /><input id="monthOverdueDayss" type="hidden" />
        </table>
        </div>
        <div class="modal-footer">
        <button  class="btn btn-primary" id="confirm">确定</button>
          <button class="btn btn-primary" data-dismiss="modal" aria-hidden="true">关闭</button>
          </div>
    
 </div>
  </div>
   </div>
 <!-- 批量减免-->   
	<div id="backDiv" style="display: none">
				 <p><span class="red">是否为所有逾期天数少于3天的客户进行批量减免？</span></p>
					<textarea rows="" cols="" class="textarea_refuse" id="batchReson" style="margin-left:60px"></textarea>
					<span class="red">最多输入1500字符</span>
			     </div>
 </div>
</body>
</html>
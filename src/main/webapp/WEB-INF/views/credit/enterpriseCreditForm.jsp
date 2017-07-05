<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/head.jsp"%>
<html>
<head>
<title>征信报告</title>
<script type="text/javascript" src="${ctxStatic}/tableCommon.js"></script>
<script type="text/javascript" src="${context}/js/consult/loanLaunch.js"></script>
<script type="text/javascript" src="${context}/js/certificate.js"></script>
<script type="text/javascript" src="${context}/js/validateCredit.js"></script>
<script type="text/javascript" src="${context}/js/credit/common.js"></script>
<script type="text/javascript" src="${context}/js/credit/enterpriseCreditForm.js"></script>
<meta name="decorator" content="default" />

</head>
<body>

<div class="control-group pb10  form-search ">
	<form id="enterpriseCreditForm">
	<table class="table1" width="100%" cellspacing="0" cellpadding="0" border="0">
		<tbody>
			<tr>
				<td>
					<lable class="lab" ><font color="red">*</font>客户唯一码：</lable>
					<input maxlength="20" required id="enterpriseCreditLoanCode" name="loanCode" value="${enterpriseCredit.loanCode}" class="input_text178 " type="text" readonly="readonly"/>
					<input name="enterpriseCreditExists" type="hidden" value="${enterpriseCredit.creditVersion}"/>
				</td>
				<td>
					<lable class="lab" ><font color="red">*</font>信用报告版本：</lable>
					<select required name="creditVersion" class="select180 "  >
						<option value="" >请选择</option>
						<c:forEach items="${fns:getDictList('jk_enterprise_risk_type')}" var="item">
							<option value="${item.value}" <c:if test="${enterpriseCredit.creditVersion == item.value}">selected</c:if> >${item.label}</option>
						</c:forEach>
					</select>
				</td>
				<td>
					<lable class="lab" >贷款卡编号：</lable>
					<input maxlength="20" name="loanCardCode" value="${enterpriseCredit.loanCardCode}" class="input_text178 " type="text" />
				</td>
			</tr>
			<tr>
				<td>
					<lable class="lab" ><font color="red">*</font>报告日期：</lable>
					<input required name="reportDate" id="reportDate" value='<fmt:formatDate value="${enterpriseCredit.reportDate}" pattern="yyyy-MM-dd"/>' type="text" 
						class="input_text178  Wdate" onclick="WdatePicker({maxDate:'%y-%M-%d'})" />
				</td>
			</tr>

		</tbody>
	</table>
	</form>
</div>

<h2 class="f14 pl10 mt20">基础信息</h2>

<%@ include file="/WEB-INF/views/credit/creditBasicInfoForm.jsp"%>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">出资人信息</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增出资人信息" onclick="addTr('creditInvestorInfo');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存出资人信息" onclick="saveTr('creditInvestorInfo');"/>
			</td>
		</tr>
	</table>
</div>

<%@ include file="/WEB-INF/views/credit/creditInvestorInfoForm.jsp"%>

<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">高管人员信息</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增高管人员信息" onclick="addTr('creditExecutiveInfo');"/>
				&nbsp;
				<input type="button"  class="btn btn-small" value="保存高管人员信息" onclick="saveTr('creditExecutiveInfo');"/>
			</td>
		</tr>
	</table>
</div>

<%@ include file="/WEB-INF/views/credit/creditExecutiveInfoForm.jsp"%>
<br>

<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">有直接关联的其他企业</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增有直接关联的其他企业" onclick="addTr('creditAffiliatedEnterprise');"/>
				&nbsp;
				<input type="button"  class="btn btn-small" value="保存有直接关联的其他企业" onclick="saveTr('creditAffiliatedEnterprise');"/>
			</td>
		</tr>
	</table>
</div>

<%@ include file="/WEB-INF/views/credit/creditAffiliatedEnterpriseForm.jsp"%>
<br>
<br>
<h4 class="f16 pl10" style="color:#337ab7;">未结清信贷信息概要</h4>
<h4 class="f14 pl10">当前负债信息概要</h4>
<%@ include file="/WEB-INF/views/credit/creditCurrentLiabilityInfoForm.jsp"%>

<br>
<%@ include file="/WEB-INF/views/credit/creditCurrentLiabilityDetailForm.jsp"%>

<h4 class="f14 pl10">对外担保信息概要</h4>
<%@ include file="/WEB-INF/views/credit/creditExternalSecurityInfoForm.jsp"%>
<h4 class="f14 pl10">已结清信贷信息概要</h4>
<%@ include file="/WEB-INF/views/credit/creditCreditClearedInfoForm.jsp"%>
<br>
<%@ include file="/WEB-INF/views/credit/creditCreditClearedDetailForm.jsp"%>
<br>
<br>

<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">负债历史变化</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增负债历史变化" onclick="addTr('creditLiabilityHis');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存负债历史变化" onclick="saveTr('creditLiabilityHis');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditLiabilityHisForm.jsp"%>
<br>
<br>

<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">信贷记录明细</h3>
<h3 style="font-size:15px;color:#333;padding-left:10px;line-height:26px;">未结清业务:不良、关注类</h3>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">贷款</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button"  class="btn btn-small" value="新增贷款信息" onclick="addTr('unclearLoanLoan');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存贷款信息" onclick="saveTr('unclearLoanLoan');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearLoanLoanForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">贸易融资</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增贸易融资" onclick="addTr('unclearLoanTrade');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存贸易融资" onclick="saveTr('unclearLoanTrade');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearLoanTradeForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">保理</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增保理" onclick="addTr('unclearLoanFactor');"/>
				&nbsp;
				<input type="button"  class="btn btn-small"value="保存保理" onclick="saveTr('unclearLoanFactor');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearLoanFactorForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">票据贴现</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增票据贴现" onclick="addTr('unclearLoanNotes');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存票据贴现" onclick="saveTr('unclearLoanNotes');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearLoanNotesForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">银行承兑汇票</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增银行承兑汇票" onclick="addTr('unclearLoanBank');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存银行承兑汇票" onclick="saveTr('unclearLoanBank');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearLoanBankForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">信用证</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增信用证" onclick="addTr('unclearLoanGuarantee');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存信用证" onclick="saveTr('unclearLoanGuarantee');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearLoanGuaranteeForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">保函</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增保函" onclick="addTr('unclearLoanLetter');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存保函" onclick="saveTr('unclearLoanLetter');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearLoanLetterForm.jsp"%>
<br>
<br>
<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">未结清业务:正常</h3>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">贷款</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增贷款" onclick="addTr('creditUnclearedLoan');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存贷款" onclick="saveTr('creditUnclearedLoan');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearedLoanForm.jsp"%>
<br>

<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">贸易融资</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增贸易融资" onclick="addTr('creditUnclearedTradeFinancing');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存贸易融资" onclick="saveTr('creditUnclearedTradeFinancing');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearedTradeFinancingForm.jsp"%>
<br>

<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">保理</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增保理" onclick="addTr('creditUnclearedFactoring');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存保理" onclick="saveTr('creditUnclearedFactoring');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearedFactoringForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">票据贴现</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增票据贴现" onclick="addTr('creditUnclearedNotesDiscounted');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存票据贴现" onclick="saveTr('creditUnclearedNotesDiscounted');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearedNotesDiscountedForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">银行承兑汇票</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增银行承兑汇票" onclick="addTr('creditUnclearedBankAcceptance');"/>
				&nbsp;
				<input type="button"  class="btn btn-small" value="保存银行承兑汇票" onclick="saveTr('creditUnclearedBankAcceptance');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearedBankAcceptanceForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">信用证</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增信用证" onclick="addTr('creditUnclearedLetterCredit');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存信用证" onclick="saveTr('creditUnclearedLetterCredit');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearedLetterCreditForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">保函</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增保函" onclick="addTr('creditUnclearedLetterGuarantee');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存保函" onclick="saveTr('creditUnclearedLetterGuarantee');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditUnclearedLetterGuaranteeForm.jsp"%>
<br>

<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">已还清债务</h3>

<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">贷款</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增贷款信息" onclick="addTr('paidLoanLoan');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存贷款信息" onclick="saveTr('paidLoanLoan');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditPaidLoanLoanForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">贸易融资</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增贸易融资" onclick="addTr('paidLoanTrade');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存贸易融资" onclick="saveTr('paidLoanTrade');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditPaidLoanTradeForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">保理</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增保理信息" onclick="addTr('paidLoanFactor');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存保理信息" onclick="saveTr('paidLoanFactor');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditPaidLoanFactorForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">票据贴现</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增票据贴现信息" onclick="addTr('paidLoanNotes');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存票据贴现信息" onclick="saveTr('paidLoanNotes');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditPaidLoanNotesForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">银行承兑汇票</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button"  class="btn btn-small" value="新增银行承兑汇票" onclick="addTr('paidLoanBank');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存银行承兑汇票" onclick="saveTr('paidLoanBank');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditPaidLoanBankForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">信用证</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增信用证信息" onclick="addTr('paidLoanCredit');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存信用证信息" onclick="saveTr('paidLoanCredit');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditPaidLoanCreditForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">保函</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增保函信息" onclick="addTr('paidLoanGuarantee');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存保函信息" onclick="saveTr('paidLoanGuarantee');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditPaidLoanGuaranteeForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">对外担保记录</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增对外担保记录" onclick="addTr('externalGuarantee');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存对外担保记录" onclick="saveTr('externalGuarantee');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditExternalGuaranteeForm.jsp"%>
<br>
<br>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">公共记录明细</h3>

<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">民事判决记录</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button"  class="btn btn-small" value="新增判决记录" onclick="addTr('civilJudgment');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存判决记录" onclick="saveTr('civilJudgment');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditCivilJudgmentForm.jsp"%>
<br>
<br>
<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">声明信息明细</h3>
<h3 style="font-size:15px;color:#666;padding-left:10px;line-height:26px;">征信中心标注</h3>


<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">贷款卡</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增贷款卡" onclick="addTr('creditLoanCard');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存贷款卡信息" onclick="saveTr('creditLoanCard');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditLoanCardForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">评级</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增评级" onclick="addTr('creditGrade');"/>
				&nbsp;
				<input type="button" class="btn btn-small" value="保存评级信息" onclick="saveTr('creditGrade');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditGradeForm.jsp"%>
<br>
<br>
<div>
	<table cellpadding="0" cellspacing="0" border="0"  width="100%" class="table1">
		<tr>
			<td class="tleft" >
				<h3 style="font-size:16px;color:#337ab7;padding-left:10px;line-height:26px;">处罚</h3>
			</td>
			
			<td class="tright pr10" >
				<input type="button" class="btn btn-small" value="新增处罚信息" onclick="addTr('creditPunish');"/>
				&nbsp;
				<input type="button"  class="btn btn-small" value="保存处罚信息" onclick="saveTr('creditPunish');"/>
			</td>
		</tr>
	</table>
</div>
<%@ include file="/WEB-INF/views/credit/creditPunishForm.jsp"%>

<br>
<div class="tright pr30 pt10 mb30">
	<input type="button" class="btn btn-primary" value="保存" onclick="saveEnterpriseCredit();"/>
</div>

</body>
</html>
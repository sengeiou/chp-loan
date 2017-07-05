<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>资料上传</title>
<meta name="decorator" content="default" />
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script> 
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" /> 
<script type="text/javascript" src="${context}/js/storereview/uploadInformation.js"></script>
<script type="text/javascript">
$(function(){
	var teleFlag='${param.teleFlag}';
	//返回
	$("#uploadInformationBack").bind('click',function(){
		if(teleFlag=='0'){
			window.location=ctx+"/borrow/borrowlist/fetchTaskItems";
		}else if(teleFlag=='1'){
			window.location=ctx+"/borrow/borrowlist/fetchTaskTelesales";
		}
	});
});
</script>
</head>
<body>
<div style="position:absolute;top:0;left:0;width:100%;height:95%" >
	<c:set var="bview" value="${workItem.bv}" />

		<div class="tright pb5 pt5">			
			<input id="subBtn" type="button" class="btn btn-small" value="客户资料上传确认" />
		<%-- 	<c:if test="${bview.isStoreAssistant=='1'}"> --%>
				<input id="giveUpBtn" type="button" class="btn btn-small" value="客户放弃" />
	<%-- 		</c:if> --%>
			<input id="${bview.loanCustomer.applyId }" class="btn btn-small" onclick="showLoanHis(this.id)" type="button" value="历史" />
			<input id="uploadInformationBack" type="button" class="btn btn-small" value="返回" />
		</div>
		<div class="control-group r" style="width:39.6%; height:97%">
		<table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td class="f14 pl10">客户信息:</td>
					<td></td>
				
				</tr>
				<tr>
					<td><label class="lab">客户编号：</label>${bview.loanCustomer.customerCode}</td>
					<td><label class="lab">客户姓名：</label>${bview.loanCustomer.customerName}</td>
			    </tr>	
			    <tr>	
					<td><label class="lab">性别：</label>${bview.loanCustomer.customerSexLabel}</td>
					<td><label class="lab">证件类型：</label>${bview.loanCustomer.dictCertTypeLabel}</td>
			   	</tr>	
					<td><label class="lab">证件号码：</label>${bview.loanCustomer.customerCertNum}</td>
					<td>
						<label class="lab">证件有效期：</label>
						<c:if test="${bview.loanCustomer.idEndDay == null}">
							长期
						</c:if>
						<c:if test="${bview.loanCustomer.idStartDay !=null && bview.loanCustomer.idEndDay != null}">
							<fmt:formatDate value="${bview.loanCustomer.idStartDay}" pattern="yyyy-MM-dd"/>-
							<fmt:formatDate value="${bview.loanCustomer.idEndDay}" pattern="yyyy-MM-dd"/>					
						</c:if>
					</td>	
				</tr>
				<tr>
					<td><label class="lab">学历：</label>${bview.loanCustomer.dictEducationLabel}</td>
					<td><label class="lab">婚姻状况：</label>${bview.loanCustomer.dictMarryLabel}</td>					
				</tr>						
					<td >
						<label class="lab">
							<c:if test="${bview.loanInfo.loanInfoOldOrNewFlag eq 0}">
								共借人姓名：
							</c:if>
							<c:if test="${bview.loanInfo.loanInfoOldOrNewFlag eq 1}">
								自然人保证人姓名：
							</c:if>
						</label>
						<c:forEach var="cobos" items="${bview.coborrowers }">
							${cobos.coboName }&nbsp;&nbsp;&nbsp;&nbsp;
						</c:forEach>
					</td>
				</tr>				
			</table>
		</div>

	<!-- 影像插件 -->
	<div class="l" style="width:60%;height:99%">
		<iframe src="${bview.imageUrl }"
			width="100%" height="100%">
		</iframe>
	</div>
	<!-- 工作流提交表单 -->
	<form id="loanApplyForm" method="post">
		<input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
		<input type="hidden" value="${workItem.flowName}" name="flowName"></input>
		<input type="hidden" value="${workItem.stepName}" name="stepName"></input>
		<input type="hidden" value="${workItem.token}" name="token"></input>
		<input type="hidden" value="${workItem.flowId}" name="flowId"></input>
		<input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
		<input type="hidden" value="${bview.applyId}" name="applyId"></input>
		<input type="hidden" value="${bview.loanCustomer.loanCode }" name="loanCustomer.loanCode"></input>
		<input type="hidden" name="queue" value="HJ_CUSTOMER_AGENT"/>
      	<input type="hidden" name="viewName" value="loanflow_borrowlist_workItems"/>
     	<input type="hidden" name='response' id="response" />
     	<input type="hidden" name="teleFlag" value="${param.teleFlag}" />
	</form>
	</div>
</body>
</html>
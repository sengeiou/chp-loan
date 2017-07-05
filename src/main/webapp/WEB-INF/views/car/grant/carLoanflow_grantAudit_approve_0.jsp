<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>
<script src="${context}/js/car/grant/carGrantAuditDeal.js"
	type="text/javascript"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
$(document).ready(function(){
	
	 
	 // 当退回原因为其他时，备注前增加  红星
	  $('#reason').bind('change',function(){
		 var dictBackMestype=$("#reason").attr("selected","selected").val();
		 if(dictBackMestype == "9")
		 {
			 $('#remarkSpan').show();
		 }else{
			 $('#remarkSpan').hide();
		 }
	 }); 
	 

});
</script>
</head>
<body>

	<div >
		<h4 class="f14 pl10">放款审核</h4>
					
				</div>
				<table class="table  table-bordered table-condensed table-hover ">
				<thead>
					<tr>
						<th>借款人</th>
						<th>合同编号</th>
						<th>放款金额</th>
						<th>放款账户</th>
						<th>放款日期</th>
						<th>渠道</th>
					</tr>
				</thead>
				<tbody>
          			<form id="param">
	            		<c:forEach items="${list}" var="item" varStatus="stat">
	            		<tr>
	                		<td>${item.customerName}</td>
	                		<td>${item.contractCode}</td>
	                		<td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
	                		<td>${item.midBankCardName}</td>
	                		<td><fmt:formatDate value="${item.lendingTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	                		<td>${item.loanFlag}</td>
	               			<input type="hidden" name="list[${stat.index}].applyId" value='${item.applyId}'></input>
	               			<input type="hidden" name="list[${stat.index}].workItemView.flowId" value='${item.applyId}'></input>
	               			<input type="hidden" name="list[${stat.index}].workItemView.wobNum" value='${item.wobNum}'></input>
	                		<input type="hidden" name="list[${stat.index}].workItemView.flowName" value='${item.flowName}'></input>
	                		<input type="hidden" name="list[${stat.index}].workItemView.token" value='${item.token}'></input>
	                		<input type="hidden" name="list[${stat.index}].workItemView.stepName" value='${item.stepName}'></input>
	                		<input type="hidden" name="list[${stat.index}].contractCode" value='${item.contractCode}'></input>
	               			<input type="hidden" name="list[${stat.index}].loanCode" value='${item.loanCode}'></input>
	            		</tr>
	            		</c:forEach>
         		 	</form> 
         		 </tbody>					
				</table>
				<div class="control-group pb10">
					<table class="table1" cellpadding="0" cellspacing="0"
						border="0" width="100%">
						<tr>
							<td style="text-align: left"><label class="lab">筛选结果：</label><input
								type="radio" class="mr10" name="sort" checked="checked"
								 value="通过">通过 <input type="radio" 
								class="mr10" name="sort"  value="退回">退回</td>
						</tr>
						<tr>
							<td style="text-align: left">
								<div style="display: inline" id="sort_div">
									<label class="lab">确认放款日期：</label><input id="grantDate" name="bv.lendingTime" value="<fmt:formatDate value='${bv.lendingTime}' pattern="yyyy-MM-dd"/>" type="text" class="Wdate input_text178" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" />
								</div>
								<div style="display: none" id="back_div">
								<form id="reasonValidate" >
										<label class="lab"><span style="color: red;">*</span>退回原因：</label>
					    		 <select id="reason" class="select180 required " >
				    				 <option value="">请选择</option>
									<c:forEach items="${fns:getDictLists('6,7,9', 'jk_chk_back_reason')}" var="item">
										<option value="${item.value}">${item.label}</option>
									</c:forEach>
				 				</select>
									<p>
									<label class="lab"><span id="remarkSpan" style="color: red;display:none;" >*</span>备注：</label>
									
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								<textarea rows="" cols="" class="textarea_refuse"
									id="remark" name="consLoanRemarks" ></textarea>
									</p>
								</form>
							
								</div>
							</td>
						</tr>
					</table>
			</div>
				<div class="tright mt10 pr30">
					<button class="btn btn-primary" id="auditSure">确认</button>
					
					<button class="btn btn-primary" id="closeBtn">取消</button>
				</div>
					
			</div>

</body>
</html>
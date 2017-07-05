<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>退款列表</title>
<script type="text/javascript" language="javascript"
	src='${context}/js/common.js'></script>
<script src="${context}/js/refund/refund.js" type="text/javascript"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#refundForm").attr("action", "${ctx}/borrow/refund/longRefund/dataRefundInfo?returnUrl=dataRefundList");
	$("#refundForm").submit();
	return false;
}
$(document).ready(
		function() {
			loan.getstorelsit("name","storeId");
		});
</script>
<meta name="decorator" content="default"/>
</head>
<body>
<input type="hidden" value="${refund.ifCanExport }" id="canExport"/>
	<div class="control-group">
       <form:form  method="post" modelAttribute="refund" id="refundForm">
         <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input  name="menuId" type="hidden" value="${param.menuId}" />
		 <input name="channelFlag" type="hidden" value="5" />
		 <input name="ifCanExport" type="hidden" value="" />
            <tr>
               <td><label class="lab">客户姓名：</label>
               <form:input type="text" class="input_text178" path="customerName"></form:input></td>
               <td><label class="lab">合同编号：</label>
               <form:input type="text" class="input_text178" path="contractCode"></form:input></td>
               <td><label class="lab">门店：</label>
				    <form:input type="text" id="name" class="input_text178" path="name" readonly="true"></form:input>
                	  <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <form:hidden path="storeId" id="storeId"/>
				</td>
            </tr>
            <tr>
				<td>
               	 	<label class="lab">借款状态：</label>
                	<select class="select180" id="loanStatus" name="loanStatus">
                	<option value="-1">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_loan_apply_status')}" var="status">
							<option value="${status.value}" <c:if test="${status.value eq refund.loanStatus}">selected</c:if>>${status.label}</option>
					 	</c:forEach>
				    </select>
                </td>
                <td>
               	 	<label class="lab">申请状态：</label>
                	<select class="select180" id="appStatus" name="appStatus">
                	<option value="-1">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_app_status')}" var="status">
							<option value="${status.value}" <c:if test="${status.value eq refund.appStatus}">selected</c:if>>${status.label}</option>
					 	</c:forEach>
				    </select>
				</td>
				<td>
					<label class="lab">申请日期：</label>
                   <input id="startTime" name="startTime"  readonly="true"
                 value="${refund.startTime}"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>-
                   <input id="endTime" name="endTime"  readonly="true"
                 value="${refund.endTime}"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
               </td>
            </tr>
			<tr >
                <td>
               	 	<label class="lab">退款类别：</label>
                	<select class="select180" id="appType" name="appType">
                		<option value="-1">请选择</option>
					    <c:forEach items="${fns:getDictList('jk_app_type')}" var="status">
							<option value="${status.value}" <c:if test="${status.value eq refund.appType}">selected</c:if>>${status.label}</option>
					 	</c:forEach>
				    </select>
                </td>
                <td>
					<label class="lab">退款日期：</label>
                   <input id="backStartDate" name="backStartDate"  readonly="true"
                 value="${refund.backStartDate}"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>-
                   <input id="backEndDate" name="backEndDate"  readonly="true"
                 value="${refund.backEndDate}"
                     type="text" class="Wdate input_text70" size="10"  
                                        onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" ></input>
               </td>
            </tr>
        </table>
        	
        <div class="tright pr30 pb5">
              <input class="btn btn-primary" type="submit" value="搜索"></input>
              <button class="btn btn-primary" id="clearBtn">清除</button>
		</div>
	</form:form>
	   </div>
		<p class="mb5">

			<input class="btn btn-small jkhj_lbzhgl_tklb_plth" id="batchReturn" type="button" value="批量退回"/>
			<input class="btn btn-small jkhj_lbzhgl_tklb_dcexcel" id="daoExcel" type="button" value="导出Excel"/>
			<input class="btn btn-small jkhj_lbzhgl_tklb_alreadyRefund" id="alreadyRefundDataExcel" type="button" value="已退款导出"/>
			<input class="btn btn-small jkhj_lbzhgl_tklb_schzjg" role="button"  data-target="#uploadAuditedModal" data-toggle="modal" id="importExcel" type="button" value="上传回执结果"/>
			<input class="btn btn-small jkhj_lbzhgl_tklb_tkcg" id="returnSuccess" type="button" value="退款成功"/>
			<span class="red">退款总金额:</span>
			<span class="red" id="total_money"><fmt:formatNumber value='${totalMony}' pattern="#,##0.00"/></span> </input>&nbsp;
			<span class="red">退款总笔数:</span>
			<span class="red" id="total_num">${totalNum}</span>

		</p>
		<sys:columnCtrl pageToken="grantdeductlist"></sys:columnCtrl>
		<div class="box5" >
        <table id="tableList" class="table  table-bordered table-condensed table-hover ">
        <thead>
         <tr>
            <th><input type="checkbox" id="checkAll"/></th>
            <th>门店</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>合同金额</th>
            <th>借款状态</th>
            <th>申请退款金额</th>
            <th>申请日期</th>
			<th>退款类别</th>
			<th>开户行</th>
			<th>退款银行</th>
			<th>退款日期</th>
			<th>申请状态</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ urgeList!=null && fn:length(urgeList.list)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${urgeList.list}" var="item">
          <c:set var="index" value="${index+1}" />
             <tr>
             <td><input type="checkbox" name="checkBoxItem" cid='${item.id }' value="${item.refundMoney}"/></td>
             <td>${item.mendianName }</td>
             <td>${item.contractCode}</td>
             <td>${item.customerName }</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,##0.00"/></td>
             <td>${item.loanStatusName}</td>
             <td><fmt:formatNumber value='${item.refundMoney}' pattern="#,##0.00"/></td>
             <td><fmt:formatDate value="${item.createTimes}" type="date" /></td>
             <td>${item.appTypeName}</td>
             <td>${item.bankName}</td>
             <td>${item.refundBank}</td>
             <td><fmt:formatDate value="${item.backDate}" type="date" /></td>
             <td>${item.appStatusName}</td>
             <td>
             	<input style= "background-color:transparent;width:1px;border:none;">
             	<c:if test="${jkhj_lbzhgl_tklb_ck==null }">
             		<input class="btn_edit jkhj_lbzhgl_tklb_ck" onclick="openView('${item.id}','${item.contractCode}','${item.appType}')" value="查看" type="button">
            	</c:if>
            	<c:if test="${item.appStatus eq '5'}">
            		<c:if test="${jkhj_lbzhgl_tklb_tp==null }">
           				<input class="btn_edit jkhj_lbzhgl_tklb_tp" sid='${item.id}' onclick="refundTicket('${item.id}','${item.appType}','${item.contractCode}','${item.refundMoney}')" value="退票" type="button">
            		</c:if>
            	</c:if>
            	<c:if test="${jkhj_lbzhgl_tklb_ls==null }">
             		<input class="btn_edit jkhj_lbzhgl_tklb_ls" onclick="showPaybackHistory('${item.contractCode}');" value="历史" type="button">
             	</c:if>	
             </td>
         </tr> 
         </c:forEach>  
         </c:if>
         <c:if test="${ urgeList==null || fn:length(urgeList.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
     </tbody>
    </table>
	</div> 
	<div class="pagination">${urgeList}</div> 
	<div id="batchReturnDiv"  class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="batchReturnForm" class="validate" action="${ctx}/borrow/refund/longRefund/batchReturn" method="post">
			<input type="hidden" id="ids" name="ids"/>
			<textarea class="input-xlarge" id="remarkA" name="remarkA" rows="3" 
					maxlength="200" style="width: 320px; height: 75px;"></textarea>
			<font id="showMsgA" color="red"></font>
			<input type="hidden" name="channelFlag" value="5"/>
		</form>
	</div>
	
	<div id="refundTicketDiv"  class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="refundTicketForm" class="validate" action="${ctx}/borrow/refund/longRefund/editRefundTicket" method="post">
			<input type="hidden" id="id" name="id"/>
			<input type="hidden" id="appTypeA" name="appTypeA"/>
			<input type="hidden" id="refundMoney" name="refundMoney"/>
			<input type="hidden" id="contractCodeA" name="contractCodeA"/>
			<input type="hidden" id="appStatus" name="appStatus" value="6"/>
			<textarea class="input-xlarge" id="remark" name="remark" rows="3" 
					maxlength="200" style="width: 320px; height: 75px;"></textarea>
			<font id="showMsg" color="red"></font>
		</form>
	</div>
	
	<div id="returnSuccessDiv"  class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="returnSuccessForm" class="validate" action="${ctx}/borrow/refund/longRefund/saveRefund" method="post">
			<input type="hidden" id="idsA" name="idsA"/>
			<table id="tableList" class="table  table-bordered table-condensed table-hover ">
		        <thead>
			         <tr>
			         	<td>退款银行：</td>
			         	<td>
				            <select id="refundBank" name="refundBank" class="select78">
							</select>
			         	</td>
			         </tr>
			         <tr>
			         	<td>退款日期：</td>
			         	<td>
				             <input id="backDate" name="backDate" 
				           		value="${search.startTime}"
				               	type="text" class="Wdate input_text70" size="10"  
				                 onClick="WdatePicker({skin:'whyGreen'})" style="cursor: pointer" >
				             </input>
			         	</td>
			         </tr>
		         </thead>
	         </table>
		</form>
	</div>
	
	<div class="modal fade" id="uploadAuditedModal" tabindex="-1"
		role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<form id="uploadAuditForm" role="form" enctype= "multipart/form-data" method="post">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title" id="myModalLabel">上传回执结果</h4>
						<input type='file' name="file" id="fileid">
					</div>
					 <div class="modal-footer">
       					 <input class="btn btn-primary" type="submit" value="确认"/>
      					 <button class="btn btn-primary"  class="close" data-dismiss="modal" >取消</button>
   					 </div>
			</div>
			</div>
		</form>
	</div>
	
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
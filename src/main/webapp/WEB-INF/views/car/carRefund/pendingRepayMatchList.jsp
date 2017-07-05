<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" language="javascript" src='${context}/js/common.js'></script>
<script type="text/javascript" src='${context}/js/bootstrap.autocomplete.js' ></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script>
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#inputForm").attr("action", "${ctx}/car/refund/carPendingRepayMatch/pendingMatchJump");
	$("#inputForm").submit();
	return false;
}
$(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 $("#inputForm").validate({
			onkeyup: false, 
			rules : {
				'applyRepayAmountUp':{
					required : false,
	          	   	max:999999999,
	          	   	number:true 
				},
				'applyRepayAmountDown':{
					required : false,
	          	   	max:999999999,
	          	   	number:true 
				}
			},
			messages : {
				'applyRepayAmountUp':{
					max:"输入申请金额起始值有误",
					number : "申请金额起始值只能是数字"
				},
				'applyRepayAmountDown':{
					max:"输入申请金额结束值有误",
					number : "申请金额结束值只能是数字"
				}
			},
			submitHandler : function(form) {
				$('#inputForm').submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
				//error.insertAfter(element);
				error.appendTo($("#errorSpan"));
			}
		});
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');  
		 $("select").trigger("change");
	 });
	 $('#searchBtn').bind('click',function(){
			$('#inputForm').submit(); 
		 });
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 // 初始化tab
	$('#tableList').bootstrapTable('destroy');
	$('#tableList').bootstrapTable({
		method: 'get',
		cache: true,
		height: 320,
		pageSize: 20,
		checkboxHeader: true,
		clickToSelect: true,
		pageNumber:1
	});
	/**
	 * 页面匹配按钮事件
	 */
	$(":input[name='matchBtn']").each(function(){
		$(this).bind('click',function(){
			$('#matchId').val($(this).val());
			$("#matchingInfoForm").submit();
		});
	});
	
	/**
	 * @function 批量匹配
	 */
	$('#batchMatchingBtn').click(function(){
		var data = $('#tableList').bootstrapTable('getSelections');
		var matchingIds;
		if(data.length > 0){
			for(var i=0;i<data.length;i++){
				if(typeof(matchingIds)!='undefined'){
					matchingIds +="," + data[i].id;
				}else{
					matchingIds = data[i].id;
				}
	    	}
			if(matchingIds){
				$.ajax({  
					   type : "POST",
					   async:true,
					   data:{
						   matchingIds:matchingIds,
					   },
						url : ctx+"/car/refund/carPendingRepayMatch/repayBatchMatch",
						datatype : "json",
						success : function(msg){
							artDialog.alert(msg,function(){
								window.location.href=ctx+"/car/refund/carPendingRepayMatch/pendingMatchJump";
							});
						},
						error: function(){
								artDialog.alert('服务器没有返回数据，可能服务器忙，请重试!','warning');
							}
					});
			}else{
				artDialog.alert('请选择匹配数据！');
			}
		}else{
			artDialog.alert('请选择匹配数据！');
		}
	});
	
	/**
	 * @function 批量退回
	 */
	$('#backConfirm').click(function() {
		var data = $('#tableList').bootstrapTable('getSelections');
		var backIds;
		if(data.length > 0){
			for(var i=0;i<data.length;i++){
				if(typeof(backIds)!='undefined'){
					backIds +="," + data[i].id;
				}else{
					backIds = data[i].id;
				}
	    	}
			if(backIds){
				$.ajax({  
					   type : "POST",
					   async:true,
					   data:{
						   backIds:backIds,
						   backMsg:$('#backMsg').val()
					   },
						url : ctx+"/car/refund/carPendingRepayMatch/repayBatchBack",
						datatype : "json",
						success : function(msg){
							artDialog.alert(msg,function(){
								window.location.href=ctx+"/car/refund/carPendingRepayMatch/pendingMatchJump";
							});
						},
						error: function(){
								artDialog.alert('服务器没有返回数据，可能服务器忙，请重试!','warning');
							}
					});
			}else{
				artDialog.alert('请选择匹配退回数据！');
			}
		}else{
			artDialog.alert('请选择匹配退回数据！');
		}
	});

	/**
	 * @function 弹出退回框
	 */
	$('#batchBackMathcingBtn').bind('click', function(){
		var data = $('#tableList').bootstrapTable('getSelections');
		if(data.length > 0){
			$('#matchingBackDiv').modal();  
		}else{
			artDialog.alert('请选择匹配退回数据！');
		}
	});
});	
</script>
<meta name="decorator" content="default"/>
</head>
<body>
	<div class="control-group">
       <form:form  method="post" id="inputForm" modelAttribute="carPendingRepayMatchInfo">
         <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
		 <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
		 <input id="rule" type="hidden" path="rule">
        <table id = "searchTable" class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
		 <span id="errHid" style="display:none"></span>
		 <div>	 
            <tr>
               <td><label class="lab">客户姓名：</label>
               <form:input type="text" class="input_text178" path="loanCustomerName"></form:input></td>
               <td><label class="lab">合同编号：</label>
               <form:input type="text" class="input_text178" path="contractCode"></form:input></td>
               
               <td><label class="lab">申请金额：</label>
               <form:input type="text" class="input_text70" path="applyRepayAmountUp"></form:input> - 
               <form:input type="text" class="input_text70" path="applyRepayAmountDown"></form:input>
               <span id="errorSpan"></span>
               </td>
            </tr>
            <tr>
				<td><label class="lab">门店：</label> 
					<input id="txtStore" name="storeName"
						type="text" maxlength="20" class="txt date input_text178"
						value="${secret.store }" readonly="true"/> 
					<i id="selectStoresBtn"
					class="icon-search" style="cursor: pointer;"></i>
               </td>		
				<td><label class="lab">来源系统：</label>
					<form:select path="dictSourceType" class="select180">
						<form:option value=''>请选择</form:option>
						<c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="dictSourceType">
                                  <option value="${dictSourceType.value}" >${dictSourceType.label}</option>
                           </c:forEach>
					</form:select>	
				</td>
				<td><label class="lab">渠道：</label>
				<form:select class="select180" path="mark">
           			<form:option value="">请选择</form:option>
                         <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
	                   			<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
	                     </c:forEach>
           		</form:select>
			    </td>
            </tr>
         </div>
         <div >
            <tr id="T1" Style="display:none">
				<td><label class="lab">还款日：</label> 
					<form:input type="number" class="input-medium" id="paybackDay" path="paybackDay"></form:input>
				</td>
				<td><label class="lab">存入账户：</label>
					<form:select class="select180" id="storesInAccount" path="storesInAccount" >
						<form:option value="">请选择</form:option>
						<c:forEach var="item" items="${middlePersonList}">
							<option value="${item.bankCardNo}" >${item.midBankName}</option>
						</c:forEach>
					</form:select>
            </tr>
        </div>    
        </table>
        <div style="float:left;margin-left:45%;padding-top:10px">
			<img src="${context }/static/images/up.png" id="showMore" onclick="show();"></img>
		</div>	
        <div class="tright pr30 pb5">
			<input type="hidden" id="hdStore">
            <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
            <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
		</div>
	</form:form>
	   </div>
	   <div>
	   <p class="mb5">
	    &nbsp;&nbsp;
		<button class="btn btn-small" id="batchMatchingBtn">批量匹配</button>
		<button class="btn btn-small" id="batchBackMathcingBtn">批量匹配退回</button>
		</p>
		</div>
		<div>
		<form id="matchingInfoForm" action="${ctx}/car/refund/carPendingRepayMatch/pendingMatchProcess" method="post">
		<input type="hidden" id = "matchId" name="id">
		 <div  style="overflow-y:auto; width:100%;height:400px">
		 
		<sys:columnCtrl pageToken="carPendMatchList"></sys:columnCtrl>
        <table id="tableList" >
        <thead>
         <tr height="37px">
            <th data-field="matchingResult" data-checkbox="true"></th>
            <th data-field="id" data-visible="false" data-switchable="false" class="hidden">ID</th>
            <th>合同编号</th>
            <th>客户姓名</th>
            <th>门店名称</th>
            <th>存入账户</th>
            <th>批借期数</th>
            <th>首期还款日</th>
			<th>申请查账日期</th>
			<th>还款状态</th>
            <th>合同金额</th>
			<th>期供金额</th>
			<th>申请还款金额</th>
			<th>追回金额</th>
			<th>还款日</th>
			<th>借款状态</th>
			<th>蓝补金额</th>
			<th>查账金额</th>
			<th>渠道</th>
            <th>操作</th>
        </tr>
		</thead>
        <tbody>
         <c:if test="${ pendingList!=null && fn:length(pendingList.list)>0}">
          <c:forEach items="${pendingList.list}" var="item">
             <tr>
             <td></td>
             <td>${item.id}</td>
             <td>${item.contractCode}</td>
             <td>${item.loanCustomerName}</td>
             <td>${item.storeName}</td>
             <td>${item.storesInAccount}</td>
             <td>-</td>
             <td>-</td>
             <td>-</td>
             <td>-</td>
             <td><fmt:formatNumber value='${item.contractAmount}' pattern="#,#00.00"/></td>
             <td>-</td>
             <td><fmt:formatNumber value='${item.applyRepayAmount}' pattern="#,#00.00"/></td>
             <td><fmt:formatNumber value='${item.reallyAmount}' pattern="#,#00.00"/></td>
             <td>-</td>
             <td>${item.dictLoanStatus}</td>
             <td>${item.blueAmount}</td>
             <td>${item.checkType}</td>   
             <td>-</td>     
             <td>
             	<button class="btn_edit" name="matchBtn" value='${item.id}' >匹配</button>
             </td>
         </tr> 
         </c:forEach>  
         </c:if>
     </tbody>
    </table>
    </div>
    </form>
	</div>
     <div class="pagination">${pendingList}</div> 
     
</div>  

<div class="modal fade" id="matchingBackDiv" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
					<h4 class="modal-title">批量退回</h4>
				</div>
				<div class="box1 mb10">
					<table id="backTB" class="table4" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="lab">退回原因：</label> 
								 <textarea class="input-xlarge" rows="3" maxlength="200" id="backMsg" style="width: 292px; height: 75px;"></textarea>
							</td>
						</tr>
					</table>
				</div>
				<div class="modal-footer">
					<a type="button" class="btn btn-primary" data-dismiss="modal">关闭</a>
					<a type="button" class="btn btn-primary" data-dismiss="modal" id="backConfirm">提交更改</a>
				</div>
			</div>
		</div>
	</div>   
</body>
</html>
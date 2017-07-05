<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/credit/creditReportCheck.js?version=1"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		$('#inputForm').submit(); 
	 });
	   var msg = "${message}";
		if (msg != "" && msg != null) {
			top.$.jBox.tip(msg);
		};
	 $("#clearBtn").bind('click',function(){
		// 清除text	
			$(":text").val('');
			// 清除checkbox	
			$(":checkbox").attr("checked", false);
			// 清除radio	
			$(":radio").attr("checked", false);
			// 清除select	
			$("select").val("");
			$("select").trigger("change");
			$('#inputForm').submit();
	 });
	 $('#showMore').bind('click',function(){
		show(); 
	 });
	 loan.getstorelsit("storeName","storeCode");
	 $("input[name='openFormBtn']").each(function(){
		 $(this).bind('click',function(){
			 $('#applyId').val($(this).attr('applyId'));
			 $('#wobNum').val($(this).attr('wobNum'));
			 $('#token').val($(this).attr('token'));
			 $('#loanInfoOldOrNewFlag').val($(this).attr('loanInfoOldOrNewFlag'));
			 $('#teleFlag').val($(this).attr('teleFlag'));
			 //$('#loanStatusCode').val($(this).attr('loanStatusCode'));
			 $('#openFlowForm').submit();
		 });
	 });
	 autoMatch("teamManagerName","teamManagerCode");
	 autoMatch("customerManagerName","customerManagerCode");
	
 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/newBorrow/borrowlist/fetchTaskItems");
		$("#inputForm").submit();
		return false;
}
document.onkeydown = function(e) {
	   //捕捉回车事件
	   var ev = (typeof event!= 'undefined') ? window.event : e;
		if(ev.keyCode == 13) {
			 $('#searchBtn').click();
		 }
	 }
	 function openFrom(url){
		 window.location = url;
	 }
	 
</script>
<title>信借待办</title>
<meta name="decorator" content="default" />
</head>
<body>

    <div class="control-group">
    <form:form id="inputForm" modelAttribute="loanFlowQueryParam" action="${ctx}/newBorrow/borrowlist/fetchTaskItems" method="post" class="form-horizontal">
       <input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
        <table class=" table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label>
                <input type="hidden" name="flowFlag" value=""/><form:input id="customerName" path="customerName" class="input_text178"/></td>
                <td><label class="lab">申请产品：</label>
                <form:select id="applyCardType" path="applyProductCode" class="select180">
                    <option value="">全部</option>
					<form:options items="${productList}" itemLabel="productName" itemValue="productCode" htmlEscape="false"/>
				</form:select>
                </td>
                <td><label class="lab">批借产品：</label>
                <form:select id="cardType" path="replyProductCode" class="select180">
                    <option value="">全部</option>
					<form:options items="${productList}" itemLabel="productName" itemValue="productCode" htmlEscape="false"/>
				</form:select>
                </td>
            </tr>
            <tr>
                <td><label class="lab">团队经理：</label>
                    <form:input path="teamManagerName" id="teamManagerName" class="input_text178"/>
                    <form:hidden  path="teamManagerCode" id="teamManagerCode"/> 
                </td>
                <td><label class="lab">客户经理：</label>
                  <form:input path="customerManagerName" id="customerManagerName" class="input_text178"/>
                  <form:hidden path="customerManagerCode" id="customerManagerCode"/> 
                </td>
					<td><label class="lab">是否追加借：</label>
               		  <form:radiobuttons path="additionalFlag" items="${fns:getDictList('jk_urgent_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
					</td>
            </tr>
			<tr id="T1" style="display:none">
					<td><label class="lab">身份证号：</label>
						<form:input path="identityCode" class="input_text178"/>
					</td>
					<td><label class="lab">模式：</label>
						<select id="model" name="model" class="select180">
                          <option value="">全部</option>
                          <c:forEach items="${fns:getDictList('jk_loan_model')}" var="item">
                         	  <option value="${item.value}"
					      		 <c:if test="${item.value==queryParam.model}">
					      		   selected=true
					      		 </c:if>
					      		>${item.label}</option>
					       </c:forEach>
				        </select>	
					</td>
            		<td>
					 <label class="lab">渠道：</label>
                        <select id="cardType" name="channelCode" class="select180">
                          <option value="">全部</option>
                          <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="item">
                         	 <c:if test="${item.label!='TG'}">
					      		<option value="${item.value}"
					      		 <c:if test="${item.value==queryParam.channelCode}">
					      		   selected=true
					      		 </c:if>
					      		>${item.label}</option>
					         </c:if>
					       </c:forEach>
				        </select>			
                    </td>
			 </tr>
			 <tr  id="T2" style="display:none">
			    <td><label class="lab">是否电销：</label>
					 <form:radiobuttons path="telesalesFlag" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>			
                </td>
                <td><label class="lab">外访人员：</label>
                    <form:input path="visitUserName" class="input_text178"/>
				</td>
	            <td><label class="lab">是否加急：</label>
             		 <form:radiobuttons path="urgentFlag" items="${fns:getDictList('jk_urgent_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</td>
			 </tr>
		</table>
        <div  class="tright pr30 pb5">
           <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
           <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
        <div class="xiala" style="text-align:center;">
				  <img src="${context}/static/images/up.png" id="showMore"></img>
       </div>
    </div>
    </div>
   </form:form> 
   
   <div class="box5"> 
      <form id="openFlowForm" action="${ctx}/bpm/flowController/openForm" method="post">
         <input type="hidden" name="applyId" id="applyId"/>
         <input type="hidden" name="wobNum" id="wobNum"/>
         <input type="hidden" name="dealType" value="0"/>
         <input type="hidden" name="token" id="token"/>
         <input type="hidden" name="loanInfoOldOrNewFlag" id="loanInfoOldOrNewFlag"/>
         <input type="hidden" name="teleFlag" id="teleFlag"/>
         <!-- <input type="hidden" name="loanStatusCode" id="loanStatusCode"/> -->
      </form>
      <table  class=" table  table-bordered table-condensed table-hover " >
      <thead>
         <tr>
          <th>序号</th>
          <th>合同编号</th>
          <th>客户姓名</th>
          <th>共借人</th>
          <th>自然人保证人</th>
          <th>门店</th>
          <th>申请产品</th>
          <th>批借产品</th>
          <th>状态</th>
          <th>批复金额</th>
          <th>批复期数</th>
          <th>加急标识</th>
          <th>是否电销</th>
          <th>团队经理</th>
          <th>销售人员</th>
          <th>进件时间</th>
          <th>模式</th>
          <th>渠道</th>
          <th>退回原因</th>
          <th>操作</th>
          <th>征信</th>
         </tr>
         </thead>
         <c:if test="${ itemList!=null && fn:length(itemList)>0}">
         <c:set var="index" value="0"/>
          <c:forEach items="${itemList}" var="item"> 
           <c:choose>
              <c:when test="${item.backFlag=='1'}">
            	  <tr class="red">
                 	<td><c:set var="index" value="${index+1}"/>${index}</td>
            	    <td>${item.contractCode}</td>
             	    <td>${item.customerName}</td>
            		<td>
            			<c:if test="${item.loanInfoOldOrNewFlag eq '' || item.loanInfoOldOrNewFlag eq '0'}">
	                		<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
	                 			${item.coborrowerName}
	               			</c:if> 
            			</c:if>
                    </td>
                    <td>
            			<c:if test="${item.loanInfoOldOrNewFlag eq '1'}">
	                		<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
	                 			${item.coborrowerName}
	               			</c:if> 
            			</c:if>
                    </td>
                    <td>${item.storeName}</td>
                    <td>${item.applyProductName}</td>
             		<td>${item.replyProductName}</td>
             		<td>${item.loanStatusName}</td>
             		<td><fmt:formatNumber value='${item.replyMoney}' pattern="#,##0.00"/></td>
            		<td>${item.replyMonth}</td>
             		<td><c:if test="${item.urgentFlag=='1'}">
                   			 <span style="color:red">加急</span>
               	 		 </c:if>
             		</td>
            		<td>
              			<c:if test="${item.telesalesFlag=='1'}">
                  			 <span>是</span>
              			</c:if>
             	    </td>
             		<td>${item.teamManagerName}</td>
             		<td>${item.customerManagerName}</td>
            		<td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
            		<td>${item.modelLabel}</td>
             		<td>${item.channelName}</td>
             		<td>${item.backReason}</td>
             		<td>
             		<c:if test="${item.loanStatusCode != '62'}">
	             		<c:if test="${item.issplit == '0'}">
	               		 <input type="button" value="办理" class="btn_edit" name="openFormBtn"   applyId="${item.applyId}" wobNum="${item.wobNum}" token="${item.token}" loanInfoOldOrNewFlag="${item.loanInfoOldOrNewFlag}" teleFlag="${teleFlag}"></input>
	             		</c:if>
	             		<c:if test="${item.issplit == '1'}">
	               		 <input type="button" value="办理" class="btn_edit" onclick="javascript:openFrom('${ctx}/newBorrow/borrowlist/openFrom?applyId=${item.applyId}');" ></input>
	             		</c:if>
             		</c:if>
            		 <input type="button" value="历史" class="btn_edit" onclick="javascript:showHisByLoanCode('${item.loanCode}');"></input>
            		</td> 
            		<td>
            			<c:if test="${item.loanStatusCode < 58 || item.loanStatusCode == 82}">
            				<input type="button" value="个人" class="btn_edit"  onclick="checkLoanMan('${item.applyId}', this);" loanInfoOldOrNewFlag = "${item.loanInfoOldOrNewFlag}"></input>&nbsp;&nbsp;
            				<%-- <input type="button" value="企业" class="btn_edit"  onclick="window.location='${ctx}/credit/enterpriseCredit/form?applyId=${item.applyId}'"></input> --%>
            			</c:if>
            		</td>          
         			</tr>  
             </c:when>
             <c:otherwise>
              <tr>
            	 <td><c:set var="index" value="${index+1}"/>${index}</td>
             	 <td>${item.contractCode}</td>
             	 <td>${item.customerName}</td>
             	 <td>
           			<c:if test="${item.loanInfoOldOrNewFlag eq '' || item.loanInfoOldOrNewFlag eq '0'}">
                		<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                 			${item.coborrowerName}
               			</c:if> 
           			</c:if>
                 </td>
                 <td>
           			<c:if test="${item.loanInfoOldOrNewFlag eq '1'}">
                		<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                 			${item.coborrowerName}
               			</c:if> 
           			</c:if>
                 </td>
             	 <td>${item.storeName}</td>
             	 <td>${item.applyProductName}</td>
             	 <td>${item.replyProductName}</td>
             	 <td>${item.loanStatusName}</td>
             	 <td><fmt:formatNumber value='${item.replyMoney}' pattern="#,##0.00"/></td>
             	 <td>${item.replyMonth}</td>
             	 <td><c:if test="${item.urgentFlag=='1'}">
                       <span style="color:red">加急</span>
                     </c:if>
            	 </td>
             	 <td>
              		<c:if test="${item.telesalesFlag=='1'}">
                   		<span>是</span>
              		</c:if>
             	 </td>
            	 <td>${item.teamManagerName}</td>
            	 <td>${item.customerManagerName}</td>
             	 <td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
             	 <td>${item.modelLabel}</td>
             	 <td>${item.channelName}</td>
             	 <td>${item.backReason}</td>
             	 <td> 
             	 	<c:if test="${item.issplit == '0'}">
               		 <input type="button" value="办理" class="btn_edit" name="openFormBtn"   applyId="${item.applyId}" wobNum="${item.wobNum}" token="${item.token}" loanInfoOldOrNewFlag="${item.loanInfoOldOrNewFlag}" teleFlag="${teleFlag}"></input>
             		</c:if>
             		<c:if test="${item.issplit == '1'}">
               		 <input type="button" value="办理" class="btn_edit" onclick="javascript:openFrom('${ctx}/newBorrow/borrowlist/openFrom?applyId=${item.applyId}');" ></input>
             		</c:if>
                   <input type="button" value="历史" class="btn_edit" onclick="javascript:showHisByLoanCode('${item.loanCode}');"></input>
                 </td> 
                 <td>
                 	<c:if test="${item.loanStatusCode < 58 || item.loanStatusCode == 82}">
           				<input type="button" value="个人" class="btn_edit"  onclick="checkLoanMan('${item.applyId}', this);" loanInfoOldOrNewFlag = "${item.loanInfoOldOrNewFlag}"></input>&nbsp;&nbsp;
            			<%-- <input type="button" value="企业" class="btn_edit"  onclick="window.location='${ctx}/credit/enterpriseCredit/form?applyId=${item.applyId}'"></input> --%>
            		</c:if>
           		 </td>
           	 </tr>  
           </c:otherwise>
          </c:choose>
         </c:forEach>       
         </c:if>
         <c:if test="${ itemList==null || fn:length(itemList)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
         </c:if>
      </table>
    </div>
 </div>
 <div class="pagination">${page}</div>
</body>


</html>
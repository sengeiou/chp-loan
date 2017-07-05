<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<title>预约银行及时间维护列表</title>

<script type="text/javascript" src="${context}/js/payback/deductsDueMaintain.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	bankmain.batchChuli();
});
</script>
</head>
<body>
<div class="control-group">
    <form id="auditForm" method="post">
    <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
    		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
            <tr>
                <td><label class="lab">预约银行：</label>
                 <sys:multipleBank bankClick="selectBankBtn" bankName="search_applyBankName" bankId="bankId"></sys:multipleBank>
				    <input id="search_applyBankName" type="text" class="input_text178" name="bankName"  value='${pbean.bankName}' readonly>&nbsp;<i id="selectBankBtn"
				    class="icon-search" style="cursor: pointer;"></i> 
				<input type="hidden" id="bankId" name='bankCode' value="${pbean.bankCode}">
               <td><label class="lab">划扣平台：</label>
	              <select name="platCode" class="select180">
					<option value=''>请选择</option>
					 <c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="dictDealType">
	                   <option value="${dictDealType.value }" 
	                   <c:if test="${pbean.platCode == dictDealType.value }">selected</c:if>>${dictDealType.label }</option>
	                 </c:forEach>
				  </select>
                </td>
                <td><label class="lab">划扣方式：</label>
                <select  name="deductTime" class="select180">
                    <option value="">请选择</option>
                      <c:forEach items="${fns:getDictList('jk_deduct_time')}" var="deductTime">
                                   <option value="${deductTime.value }" 
                                   <c:if test="${pbean.deductTime == deductTime.value }">selected</c:if>>
                                   		${deductTime.label }
                                   </option>
                      </c:forEach>
                </td>
            </tr>
    </table>
        <div class="tright pr30 pb5"><button class="btn btn-primary" onclick="document.forms[0].submit();">搜索</button>
        <button class="btn btn-primary" id="reset"> 清除</button></div>
    </div>
     </form>
     <p class="mb5">
   <button class="btn btn-small" id="batchmodify">批量修改</button>
   </p>
   <div class="box5"> 
        <table  class="table  table-bordered table-condensed" >
        <thead>
            <tr>
                <th><input type="checkbox" class="checkbox" id="checkAll"/></th>
               	<th>序号</th>
                <th>预约银行</th>
                <th>划扣平台</th>
                <th>批量或实时</th>
				<th>操作</th>
            </tr>
         </thead>   
        <c:if test="${ waitPage.list!=null && fn:length(waitPage.list)>0}">
         <c:forEach items="${waitPage.list }" var="packbackdeductsdue" varStatus="status">
            <tr>
                 <td><input type="checkbox" name="checkBox" value="${packbackdeductsdue.id }"/>
                 </td>
                 <td>${status.count}</td>
                 <td>${packbackdeductsdue.bankName}</td>
                 <td>
                    ${packbackdeductsdue.platName}
                 </td>
                 <td>
                      ${packbackdeductsdue.deductTimeLabel}
                 </td>
                 <td>
                  <input type="button" class="btn_edit" value="修改">
                  <input type="hidden" value="${packbackdeductsdue.id }"/>
                  <input type="hidden" value="${packbackdeductsdue.deductTime}"/>
                </td>
            </tr>
			</c:forEach>
		</c:if>
        <c:if test="${ waitPage.list==null || fn:length(waitPage.list)==0}">
           <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
        </c:if>
      </table>
    </div>
    <div class="pagination">${waitPage}</div>
</div>
	<!-- 线上划扣 -->
	<div  id="batchmodifyDiv" style="display: none">
					<table id="onlineDeductTB" class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td><label class="lab">是否批量：</label> 
							<input type="radio" name="back" value="0" checked="checked" />实时&nbsp;&nbsp;&nbsp;
							<input type="radio" name="back" value="1" />批量&nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
	</div>
</body>
</html>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:if test="${ workItems!=null && fn:length(workItems)>0}">
   <c:set var="index" value="0" />
	 <c:forEach items="${workItems}" var="item">
	   <c:set var="index" value="${index+1}" />
		<tr>
		  <td><input type="checkbox" name="prepareCheckEle"
									value='${item.applyId},${item.wobNum},${item.flowName},${item.token}${item.stepName}' />${index}</td>
		  <td>${item.contractCode}</td>
		  <td>${item.customerName}</td>
		  <td>共借人(具体字段暂缺)</td>
          <td>${item.addrProvince}</td>
          <td>${item.addrCity}</td>
          <td>${item.contStoresId}</td>
          <td>${item.borrowProduct}</td>
          <td>${item.dictStatus}</td>
          <td>${item.auditAmount}</td>
          <td>${item.hisAmountMonths}</td>
          <td>${item.signPlatform}</td>
          <td>${item.customerIntoTime}</td>
          <td>${item.loanRaise}</td>
          <td>${item.loanUrgentFlag}</td>
          <td>${item.loanFlag}</td>
          <td>${item.contractVersion}</td>
          <td><input type="button" class="btn_edit" applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0' 
				token='${item.token}' name="dealctrAudit" value="办理" />
              <input type="button" class="btn_edit" applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
									token='${item.token}' name="backBtn" value="退回"/>
              <input type="button" class="btn_edit" applyId='${item.applyId}' name="history" value="历史" /> 
          </td>
      </tr>
    </c:forEach>
</c:if>
<c:if test="${ workItems==null || fn:length(workItems)==0}"> 
   <tr>
     <td colspan="18" style="text-align: center;">没有待办数据</td>
   </tr>
</c:if>
<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="productType" type="java.lang.String" required="true" description="产品类型"%>
<%@ attribute name="dictSettleRelend" type="java.lang.String" required="true" description="结清再借"%>
<%@ attribute name="dictGpsRemaining" type="java.lang.String" required="true" description="GPS是否拆除"%>
<%@ attribute name="dictIsGatherFlowFee" type="java.lang.String" required="true" description="是否收取平台及流量费"%>
<%@ attribute name="flowFee" type="java.lang.String" required="true" description="平台及流量费"%>
<%@ attribute name="parkingFee" type="java.lang.String" required="true" description="停车费"%>
<%@ attribute name="facilityCharge" type="java.lang.String" required="true" description="设备费"%>
<%@ attribute name="deviceUsedFee" type="java.lang.String" required="true" description="gps设备使用费" %>
<%@ attribute name="contractVersion" type="java.lang.String" required="false" description="合同版本" %>
<c:set var="dictSettleRelendLabel" value="${(dictSettleRelend=='1'||dictSettleRelend=='是')?'是':'否'}"></c:set>
<c:set var="dictGpsRemainingLabel" value="${(dictGpsRemaining=='1'||dictGpsRemaining=='是')?'是':'否'}"></c:set>
<c:set var="dictIsGatherFlowFeeLabel" value="${(dictIsGatherFlowFee=='1'||dictIsGatherFlowFee=='是')?'是':'否'}"></c:set>
<tr>
<td><label class="labl">批借产品：</label>${productType}</td>
<td><c:choose>
	<c:when test="${productType=='GPS'}"><label class="labl">设备费：</label><fmt:formatNumber value="${facilityCharge == '' ? 0 : facilityCharge }" pattern="#,##0.00" />元</c:when>
	<c:otherwise><label class="labl">停车费：</label><fmt:formatNumber value="${parkingFee == '' ? 0 : parkingFee }" pattern="#,##0.00" />元</c:otherwise>
</c:choose></td>
<td><c:if test="${productType=='GPS' && (contractVersion=='1.5' || contractVersion=='1.6')}"><label class="labl">设备使用费：</label><span id="deviceUsedFee"><fmt:formatNumber value="${deviceUsedFee == '' ? 0 : deviceUsedFee }" pattern="#,##0.00" /></span>元</c:if></td>
</tr>
<tr>
<td><label class="labl">结清再借：</label>${dictSettleRelendLabel}</td>
<c:if test="${productType=='GPS'&& dictSettleRelendLabel=='是'}">
	 <td><label class="labl">GPS是否拆除：</label>${dictGpsRemainingLabel }</td></tr><tr>
	 <td><c:if test="${dictGpsRemainingLabel=='否'}"><label class="labl">是否收取平台流量费：</label>${dictIsGatherFlowFeeLabel }</c:if></td>
	 <td><c:if test="${dictGpsRemainingLabel=='否' && dictIsGatherFlowFeeLabel=='是'}"><label class="labl">平台及流量费：</label><fmt:formatNumber value="${flowFee == '' ? 0 : flowFee }" pattern="#,##0.00" />元</c:if></td>
</c:if>
</tr>
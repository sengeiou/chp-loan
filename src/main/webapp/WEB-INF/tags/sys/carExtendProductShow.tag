<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="productType" type="java.lang.String" required="true" description="产品类型"%>
<%@ attribute name="dictSettleRelend" type="java.lang.String" required="true" description="结清再借"%>
<%@ attribute name="dictGpsRemaining" type="java.lang.String" required="true" description="GPS是否拆除"%>
<%@ attribute name="dictIsGatherFlowFee" type="java.lang.String" required="true" description="是否收取平台及流量费"%>
<%@ attribute name="flowFee" type="java.lang.String" required="true" description="平台及流量费"%>
<%@ attribute name="parkingFee" type="java.lang.String" required="true" description="停车费"%>
<%@ attribute name="facilityCharge" type="java.lang.String" required="true" description="设备费"%>
<c:set var="dictSettleRelendLabel" value="${(dictSettleRelend=='1'||dictSettleRelend=='是')?'是':'否'}"></c:set>
<c:set var="dictGpsRemainingLabel" value="${(dictGpsRemaining=='1'||dictGpsRemaining=='是')?'是':'否'}"></c:set>
<c:set var="dictIsGatherFlowFeeLabel" value="${(dictIsGatherFlowFee=='1'||dictIsGatherFlowFee=='是')?'是':'否'}"></c:set>

<td><label class="lab">产品类型：</label>${productType}</td>
<c:choose>
	<c:when test="${productType=='GPS'}">
		<td><label class="lab">平台及流量费：</label><fmt:formatNumber value="${flowFee == '' ? 0 : flowFee }" pattern="#,##0.00" />元</td>
	</c:when>
	<c:otherwise>
		<td><label class="lab">停车费：</label><fmt:formatNumber value="${parkingFee == '' ? 0 : parkingFee }" pattern="#,##0.00" />元</td>
	</c:otherwise>
</c:choose>
<input type="hidden" name="dictSettleRelend" value="${dictSettleRelend}"/>
<input type="hidden" name="dictGpsRemaining" value="${dictGpsRemaining}"/>
<input type="hidden" name="dictIsGatherFlowFee" value="${dictIsGatherFlowFee}"/>
<input type="hidden" name="parkingFee" value="${parkingFee}"/>
<input type="hidden" name="facilityCharge" value="${facilityCharge}"/>
<input type="hidden" name="flowFee" value="${flowFee}"/>


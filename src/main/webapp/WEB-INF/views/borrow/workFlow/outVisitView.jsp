<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>外访清单</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/outside/outVisitView.js"></script>
</head>
<body>
	<div>
	<ul class="nav nav-tabs" style="margin-bottom: 8px;">
	<c:forEach items="${checkInfos}" var="checkInfo" varStatus="state">
	 	<li class="miss<c:if test="${state.index eq 0}"> active</c:if>"><a href="#">
	 	${loanManType[checkInfo.dictCustomerType]}(${checkInfo.customerName})</a></li>
	 </c:forEach>
	</ul>
	 <c:forEach items="${checkInfos}" var="checkInfo" varStatus="main">
		<div class="body <c:if test='${main.index eq 1}'>hide</c:if>" mark="body">
		<input type="hidden" value="${checkInfo.checkJson }" mark="json">
		<form>
		<input type="hidden" value="${main.index}" name="dictCustomerType"/>
		<div class="diabox01" mark="checkBox" style="width:970px;border:none;">
		    <h3 class="table-top" style="border:none;border-bottom:1px solid #ccc;">
		        <c:forEach items="${dicts}" var="firstList" varStatus="firstStatu">
			    	<span mark="tab" <c:if test="${firstStatu.index eq 0}">class="click"</c:if>>
			    	<input disabled="disabled" type="checkbox" mark="${firstList.value }"
			    	style="display: block;float: left;margin: 3px 3px 0;"/>&nbsp;${firstList.label}
			    	</span>
			    </c:forEach>
		    </h3>
		    <c:forEach items="${dicts}" var="firstList" varStatus="firstStatu">
				<div style="border:1px solid #ccc;border-top:none;" mark="content" class="content <c:if test="${firstStatu.index ne 0}">hide</c:if>">
		            <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="100%">
					    <c:forEach items="${firstList.dictExs}" var="secondList">
					    	 <c:if test="${fn:length(firstList.value) eq 2}">
				            	<tr>
					                <td class="pl10">
					                <input value="${secondList.value}" mark="${secondList.value}" disabled="disabled" 
					                style="display: block;float: left;margin: 10px 3px 0;" type="checkbox"/>
					                &nbsp;${secondList.label}
					                </td>
					            </tr>
				            </c:if>
				            <c:if test="${fn:length(firstList.value) eq 3}">
				            	<c:set var="minList" value="${firstList.type}_${firstList.value}_${secondList.value}"></c:set>
				            	<tr>
				                    <td>
				                    	<p class="listbg01 pl10" style="line-height:28px;"><input
				                    	class="hide" type="checkbox" mark="${secondList.value}"/>${secondList.label}</p>
				                	</td>
				                </tr>
				                <tr>
					              <td class="pl10">
					              	<c:forEach items="${secondList.dictExs}" var="thirdList">
					              	   <input type="checkbox" disabled="disabled" mark="${thirdList.value}"
					              	   style="display: block;float: left;margin: 10px 3px 0;"/>
					              	  <label style="float: left;">&nbsp;${thirdList.label}&nbsp;&nbsp;&nbsp;&nbsp;</label>
					              	</c:forEach>
					              </td>
					            </tr>
				            </c:if>
					    </c:forEach>
		            </table>
		         </div>
		    </c:forEach>   
		</div>
	    <div lass="textarea_big"style="padding-left:20px;">
	            <table class="f14 table1" cellpadding="0" cellspacing="0" border="0" width="100%">
	                <tr>
	                    <td class="pl10">
	                     	居住地址：${checkInfo.liveProvince}${checkInfo.liveCity}${checkInfo.liveArea}
	                     	${checkInfo.liveAddress}
	                    </td>
	                </tr>
	                <tr>
	                    <td class="pl10">
	                    	房产地址：${checkInfo.houseProvince}${checkInfo.houseCity}${checkInfo.houseArea}
	                    	${checkInfo.houseAddress}   
	                    </td>
	                </tr>
	                <tr>
	                    <td class="pl10">
	                  	  工作单位地址：${checkInfo.workUnitProvince}${checkInfo.workUnitCity}${checkInfo.workUnitArea}
	                    ${checkInfo.workUnitAddress}
	                    </td>
	                </tr>
	            	</tr>
	            	<tr>
	                    <td class="pl10">
	                    	经营/生产地址：${checkInfo.operationProvince}${checkInfo.operationCity}${checkInfo.operationArea}
	                    	${checkInfo.operationAddress}
	                    </td>
	                </tr>
	        </table>
	    <span>备注框：</span>
	    <textarea class="textarea_big" disabled="disabled">${checkInfo.remark }</textarea><br/><br/>
	     </div>
	    </form>
	</div>
	</c:forEach>
	</div>
</body>
</html>
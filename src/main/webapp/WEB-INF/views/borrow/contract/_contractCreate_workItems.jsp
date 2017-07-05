<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<c:if test="${ workItems!=null && fn:length(workItems)>0}">
						<c:set var="index" value="0" />
						<c:forEach items="${workItems}" var="item">
							<c:set var="index" value="${index+1}" />
							<tr>
								<td>${index}</td>
								<td>${item.contractVersion}</td>
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
								<td>${item.customerIntoTime}</td>
								<td>${item.loanUrgentFlag}</td>
								<td>${item.loanFlag}</td>
								<td>${item.loanIsPhone}</td>
								<td><input class="btn_edit" type="button"
									applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
									token='${item.token}' name="dealctrCreate" value="办理" />
								</td>
							</tr>
						</c:forEach>
					</c:if>
					<c:if test="${ workItems==null || fn:length(workItems)==0}">
						<tr>
							<td colspan="18" style="text-align: center;">没有待办数据</td>
						</tr>
					</c:if>
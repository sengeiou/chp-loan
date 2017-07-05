<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>已制作合同详情</title>
<meta name="decorator" content="default" />

<!-- 组内共通js,历史 -->
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script> 
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" /> 
<!-- 我自己的js,影像/查看协议 -->
<script src="${context}/js/transate/transate.js"></script>

<script type="text/javascript">
  $(function(){
	  $("#xyckBtn").click(function(){
		  var loanCode=$('#load').val();
		  var docId=$('#docId').val();
		  var contractCode=$('#contractCode').val();
		  var url='${ctx}/apply/contractAudit/xieYiList?docId='+docId+'&loanCode='+loanCode+'&contractCode='+contractCode;
		  art.dialog.open(url, {  
			   id: 'protcl',
			   title: '协议查看',
			   lock:false,
			   width:1000,
			   height:450
			},false); 
		  //window.location=url;
		   /*art.dialog.open(url, {
				title: "协议查看",	
				top: 80,
				lock: true,
			    width: 1000,
			    height: 450,
			},false);	 */
		});
	  $("#preViewCtrBtn").click(function(){
		  var url='${url}';
			art.dialog.open(url, {
				title: "预览合同资料",	
				top: 80,
				lock: true,
			    width: 1000,
			    height: 450,
			},false);	
		});
	$("#xzBtn").click(function(){
         var loanCode=$('#load').val();
         window.location='${ctx}/apply/contractAudit/downLoadContractByLoanCode?loanCode='+loanCode;
	});
  })
</script>
</head>
<body>	
	<div class=" pt5 pr30 mb5">
		<div class="tright">
		    <input type="hidden" id="load" value="${ld}"/>
		    <input type="hidden" id="docId" value="${docId}"/>	
		    <input type="hidden" id="contractCode"  value="${contractCode}"/>			
			<%-- <button class="btn btn-small" id="${ld}"  onclick="showHisByLoanCode(this.id)">历史</button> --%>
			<button class="btn btn-small" id="xyckBtn">协议查看</button>
			 <c:if test="${isRateLeader=='1'}">
	          <button class="btn btn-small" id="preViewCtrBtn">预览合同资料</button>
    		  <!-- <button class="btn btn-small" id="xzBtn" >下载</button>	 -->
			</c:if>	
			<button class="btn btn-small"  onclick="location.href='${ctx }/apply/contractAudit/findContract'" >返回</button>
		</div>
	</div>
	<div class="box2 mb10">
		   <input type="hidden" id="dictLoanStatus" value="${dictLoanStatus}"/>
		   <c:if test="${lm.new_flag=='0'}">
			<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td ><label class="lab">借款合同编号：</label>${lm.contractCode }</td>
					<td><label class="lab">门店名称：</label> ${lm.dictLoanUse }</td>
					<td><label class="lab">模式：</label> ${lm.model }</td>
				</tr>
				<tr>
					<td colspan="3"><p class="bar"></p></td>
				</tr>
				<tr>
					<td><label class="lab">借款人姓名：</label>${lm.customerName }</td>
					<td><label class="lab">证件类型：</label>${lm.customerCertTypeLabel}</td>
					<td><label class="lab">证件号码：</label>${lm.customerCertNum }</td>
				</tr>

				<c:forEach var="i" items="${lb}">
					<tr>
						<td><label class="lab">
							<c:if test="${lm.loanInfoOldOrNewFlag eq '' || lm.loanInfoOldOrNewFlag eq '0'}">
							共借人姓名：
							</c:if>
							<c:if test="${lm.loanInfoOldOrNewFlag eq '1'}">
								自然人保证人姓名：
							</c:if>
						</label>${i.coboname }</td>
						<td><label class="lab">证件类型：</label>${i.dictcerttype }</td>
						<td><label class="lab">证件号码：</label>${i.cobocertnum }</td>
					</tr>
				</c:forEach>
				<c:if test="${lm.companyname != null && lm.companyname != '' 
							&& lm.legalman != null && lm.legalman != '' 
							&& 	lm.maddress != null && lm.maddress != ''
							}">
					<tr>
						<c:if test="${lm.companyname != null && lm.companyname != '' }">
							<td><label class="lab">
							<c:if test="${lm.loanInfoOldOrNewFlag eq '' || lm.loanInfoOldOrNewFlag eq '0'}">
							保证人：
							</c:if>
							<c:if test="${lm.loanInfoOldOrNewFlag eq '1'}">
							保证公司名称：
							</c:if>
							
							</label>${lm.companyname }</td>
						</c:if>
						<c:if test="${lm.legalman != null && lm.legalman != '' }">
							<td><label class="lab">法定代表人：</label>${lm.legalman }</td>
						</c:if>
						<c:if test="${lm.maddress != null && lm.maddress != '' }">
							<td><label class="lab">经营场所：</label>${lm.maddressName }<td>
						</c:if>
					</tr>					
				</c:if>
					<tr><td>
						<c:if test="${lm.middleName != null && lm.middleName != '' }">
								<label class="lab">中间人：</label>${lm.middleName }
						</c:if></td>
						<td><label class="lab">风险等级：</label>${lm.riskLevel }</td>
					</tr>
				<tr>
					<td colspan="3"><p class="bar"></p></td>
				</tr>				
				<tr>
					<td><label class="lab">产品种类：</label>${lm.productName }</td>
					<td><label class="lab">批复期数：</label>${lm.contractMonths }</td>
					<td><label class="lab">还款期数：</label>${lm.contractMonths }</td>
				</tr>
				<tr>
					<td>
						<label class="lab">批借金额：</label>
						<fmt:formatNumber value="${lm.money == null ? 0 : lm.money }" pattern="#,##0.00#" />
					</td>
					<td>
						<label class="lab">产品总费率%：</label>
						<fmt:formatNumber value="${lm.feeAllRaio == null ? 0 : lm.feeAllRaio }" pattern="#0.0000" />
					</td>
					<td><label class="lab">合同签订日：</label>
					    <fmt:formatDate value="${lm.contractFactDate }" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">外访费：</label>
						<fmt:formatNumber value="${lm.feePetitionFee == null ? 0 : lm.feePetitionFee }" pattern="#,##0.00#" />
					</td>		
					<td>
						<label class="lab">借款利率%：</label>
						<fmt:formatNumber value="${lm.feeLoanRate == null ? 0 : lm.feeLoanRate }" pattern="#0.000" />
					</td>
					<td><label class="lab">首期还款日：</label>
					<fmt:formatDate value="${lm.contractReplayDate }" pattern="yyyy-MM-dd" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">外访距离：</label> ${lm.item_distance}
					</td>
					<td>
						<c:if test="${lm.outside_flag eq ''  || lm.outside_flag eq '0' || lm.outside_flag ==null }">
							<span style="position: relative;color:red;"><label class="lab">免外访&nbsp;&nbsp;&nbsp;</label>
								<input type="checkbox"   disabled="disabled" checked="checked" />
							</span>
						</c:if>
					</td>
					<td></td>
				</tr>
				<tr>
					<td colspan="3"><p class="bar"></p></td>
				</tr>
				<tr>
					<td>
						<label class="lab">实放金额：</label>
						<fmt:formatNumber value="${lm.feeRealput == null ? 0 : lm.feeRealput }" pattern="#,##0.00#" />
					</td>		
					<td>
						<label class="lab">前期咨询费：</label>
						<fmt:formatNumber value="${lm.feeConsult == null ? 0 : lm.feeConsult }" pattern="#,##0.00#" />
					</td>
					<td>
						<label class="lab">分期咨询费：</label>
						<fmt:formatNumber value="${lm.monthFeeConsult == null ? 0 : lm.monthFeeConsult }" pattern="#,##0.00#" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">合同金额：</label>
						<fmt:formatNumber value="${lm.contractMoney == null ? 0 : lm.contractMoney }" pattern="#,##0.00#" />
					</td>		
					<td>
						<label class="lab">前期审核费：</label>
						<fmt:formatNumber value="${lm.feeCredit == null ? 0 : lm.feeCredit }" pattern="#,##0.00#" />
					</td>
					<td>
						<label class="lab">分期居间服务费：</label>
						<fmt:formatNumber value="${lm.monthMidFeeService == null ? 0 : lm.monthMidFeeService }" pattern="#,##0.00#" />						
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">应还本息：</label>
						<fmt:formatNumber value="${lm.contractBackMonthMoney == null ? 0 : lm.contractBackMonthMoney }" pattern="#,##0.00#" />
					</td>		
					<td>
						<label class="lab">前期居间服务费：</label>
						<fmt:formatNumber value="${lm.feeService == null ? 0 : lm.feeService }" pattern="#,##0.00#" />						
					</td>
					<td>
						<label class="lab">分期服务费：</label>
						<fmt:formatNumber value="${lm.monthFeeService == null ? 0 : lm.monthFeeService }" pattern="#,##0.00#" />						
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">分期服务费：</label>
						<fmt:formatNumber value="${lm.monthFeeService == null ? 0 : lm.monthFeeService }" pattern="#,##0.00#" />
					</td>		
					<td>
						<label class="lab">前期信息服务费：</label>
						<fmt:formatNumber value="${lm.feeInfoService == null ? 0 : lm.feeInfoService }" pattern="#,##0.00#" />
					</td>
					<td>
						<label class="lab">催收服务费：</label>
						<fmt:formatNumber value="${lm.feeUrgedService == null ? 0 : lm.feeUrgedService }" pattern="#,##0.00#" />
					</td>
				</tr>
				<tr>
					<td>
						<label class="lab">月还款额：</label>
						<fmt:formatNumber value="${lm.contractMonthRepayTotal == null ? 0 : lm.contractMonthRepayTotal }" pattern="#,##0.00#" />
					</td>		
					<td>
						<label class="lab">前期综合服务费：</label>
						<fmt:formatNumber value="${lm.feeCount == null ? 0 : lm.feeCount }" pattern="#,##0.00#" />
					</td>
					<td>
						<label class="lab">加急费：</label>
						<fmt:formatNumber value="${lm.feeExpeditedFee == null ? 0 : lm.feeExpeditedFee }" pattern="#,##0.00#" />
					</td>
				</tr>
				<tr>
					<td>	
						<label class="lab">预计还款总额：</label>
						<fmt:formatNumber value="${lm.contractExpectCount == null ? 0 : lm.contractExpectCount }" pattern="#,##0.00#" />
					</td>
					<td><label class="lab">还款付息方式：</label>${lm.dictRepayMethodLabel }</td>
				</tr>
			</table>
			</c:if>
			<c:if test="${lm.new_flag=='1'}">
					<table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
						<tr>
							<td ><label class="lab">借款合同编号：</label>${lm.contractCode }</td>
							<td><label class="lab">门店名称：</label> ${lm.dictLoanUse }</td>
							
						</tr>
						<tr>
							<td colspan="3"><p class="bar"></p></td>
						</tr>
						<tr>
							<td><label class="lab">借款人姓名：</label>${lm.customerName }</td>
							<td><label class="lab">证件类型：</label>${lm.customerCertTypeLabel}</td>
							<td><label class="lab">证件号码：</label>${lm.customerCertNum }</td>
						</tr>
		
						<c:forEach var="i" items="${lb}">
							<tr>
								<td><label class="lab">
									<c:if test="${lm.loanInfoOldOrNewFlag eq '' || lm.loanInfoOldOrNewFlag eq '0'}">
									共借人姓名：
									</c:if>
									<c:if test="${lm.loanInfoOldOrNewFlag eq '1'}">
										自然人保证人姓名：
									</c:if>
								</label>${i.coboname }</td>
								<td><label class="lab">证件类型：</label>${i.dictcerttype }</td>
								<td><label class="lab">证件号码：</label>${i.cobocertnum }</td>
							</tr>
						</c:forEach>
						<c:if test="${lm.companyname != null && lm.companyname != '' 
									&& lm.legalman != null && lm.legalman != '' 
									&& 	lm.maddress != null && lm.maddress != ''
									}">
							<tr>
								<c:if test="${lm.companyname != null && lm.companyname != '' }">
									<td><label class="lab">
									<c:if test="${lm.loanInfoOldOrNewFlag eq '' || lm.loanInfoOldOrNewFlag eq '0'}">
									保证人：
									</c:if>
									<c:if test="${lm.loanInfoOldOrNewFlag eq '1'}">
									保证公司名称：
									</c:if>
									
									</label>${lm.companyname }</td>
								</c:if>
								<c:if test="${lm.legalman != null && lm.legalman != '' }">
									<td><label class="lab">法定代表人：</label>${lm.legalman }</td>
								</c:if>
								<c:if test="${lm.maddress != null && lm.maddress != '' }">
									<td><label class="lab">经营场所：</label>${lm.maddressName }<td>
								</c:if>
							</tr>					
						</c:if>
							<tr><td><label class="lab">模式：</label> ${lm.model }</td>
								<td><label class="lab">风险等级：</label>${lm.riskLevel }</td>
							</tr>
						<tr>
							<td colspan="3"><p class="bar"></p></td>
						</tr>				
						<tr>
							<td><label class="lab">产品种类：</label>${lm.productName }</td>
							<td><label class="lab">批复期数：</label>${lm.contractMonths }</td>
							<td><label class="lab">还款期数：</label>${lm.contractMonths }</td>
						</tr>
						<tr>
							<td>
								<label class="lab">批借金额：</label>
								<fmt:formatNumber value="${lm.money == null ? 0 : lm.money }" pattern="#,##0.00#" />
							</td>
							<td>
								<label class="lab">产品总费率%：</label>
								<fmt:formatNumber value="${lm.feeAllRaio == null ? 0 : lm.feeAllRaio }" pattern="#0.0000" />
							</td>
							<td><label class="lab">合同签订日：</label>
							    <fmt:formatDate value="${lm.contractFactDate }" pattern="yyyy-MM-dd" />
							</td>
						</tr>
						<tr>
							<td>
								<label class="lab">外访费：</label>
								<fmt:formatNumber value="${lm.feePetitionFee == null ? 0 : lm.feePetitionFee }" pattern="#,##0.00#" />
							</td>		
							<td>
								<label class="lab">借款利率%：</label>
								<fmt:formatNumber value="${lm.feeLoanRate == null ? 0 : lm.feeLoanRate }" pattern="#0.000" />
							</td>
							<td><label class="lab">首期还款日：</label>
							<fmt:formatDate value="${lm.contractReplayDate }" pattern="yyyy-MM-dd" />
							</td>
						</tr>
						<tr>
							<td>
								<label class="lab">外访距离：</label> ${lm.item_distance}
							</td>
							<td>
								<c:if test="${lm.outside_flag eq ''  || lm.outside_flag eq '0' || lm.outside_flag ==null }">
									<span style="position: relative;color:red;"><label class="lab">免外访&nbsp;&nbsp;&nbsp;</label>
										<input type="checkbox"   disabled="disabled" checked="checked" />
									</span>
								</c:if>
							</td>
							<td></td>
						</tr>
						<tr>
							<td colspan="3"><p class="bar"></p></td>
						</tr>
						<tr>
							<td>
								<label class="lab">实放金额：</label>
								<fmt:formatNumber value="${lm.feeRealput == null ? 0 : lm.feeRealput }" pattern="#,##0.00#" />
							</td>		
							<td>
								<label class="lab">前期综合服务费：</label>
								<fmt:formatNumber value="${lm.feeCount == null ? 0 : lm.feeCount }" pattern="#,##0.00#" />
							</td>
							<td>
								<c:if test="${lm.productType eq 'A021'}">
									<label class="lab">月利息：</label>
									<fmt:formatNumber value="${lm.monthFee == null ? 0 : lm.monthFee }" pattern="#,##0.00#" />
								</c:if>
							</td>
						</tr>
						<tr>
							<td>
								<label class="lab">合同金额：</label>
								<fmt:formatNumber value="${lm.contractMoney == null ? 0 : lm.contractMoney }" pattern="#,##0.00#" />
							</td>		
							<td>
								<label class="lab">分期服务费总额：</label>
								<fmt:formatNumber value="${lm.monthFeeService*lm.contractMonths}" pattern="#,##0.00#" />						
							</td>
							<td>
							</td>
						</tr>
						<tr>
							<td>
								<label class="lab">应还本息：</label>
								<fmt:formatNumber value="${lm.contractBackMonthMoney == null ? 0 : lm.contractBackMonthMoney }" pattern="#,##0.00#" />
							</td>		
							<td>
								<label class="lab">催收服务费：</label>
								<fmt:formatNumber value="${lm.feeUrgedService == null ? 0 : lm.feeUrgedService }" pattern="#,##0.00#" />
							</td>
							
						</tr>
						<tr>
							<td>
								<label class="lab">分期服务费：</label>
								<fmt:formatNumber value="${lm.monthFeeService == null ? 0 : lm.monthFeeService }" pattern="#,##0.00#" />
							</td>		
							<td>
								<label class="lab">加急费：</label>
								<fmt:formatNumber value="${lm.feeExpeditedFee == null ? 0 : lm.feeExpeditedFee }" pattern="#,##0.00#" />
							</td>
							
						</tr>
						<tr>
							<td>
								<label class="lab">月还款额：</label>
								<fmt:formatNumber value="${lm.contractMonthRepayTotal == null ? 0 : lm.contractMonthRepayTotal }" pattern="#,##0.00#" />
							</td>		
							<td><label class="lab">还款付息方式：</label>${lm.dictRepayMethodLabel }</td>
						</tr>
						<tr>
							<td>	
								<label class="lab">预计还款总额：</label>
								<fmt:formatNumber value="${lm.contractExpectCount == null ? 0 : lm.contractExpectCount }" pattern="#,##0.00#" />
							</td>
							
						</tr>
					</table>
			
			</c:if>
		</div>
	</div>
</body>
</html>
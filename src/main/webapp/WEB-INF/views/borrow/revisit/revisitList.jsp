<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script src="${context}/js/revisit/revisit.js" type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/contract/rateList.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>回访失败列表</title>
<script language="javascript">

function page(n, s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#revisitForm").attr("action", "${ctx}/revisit/revisitfail/findContract");
	$("#revisitForm").submit();
	return false;
}
$(document).ready(
		function() {
			$('* a').css('cursor', 'pointer');
			//操作按钮绑定
			$('.cz').click(function() {

			})

			//全选绑定
			$('#checkAll').bind(
					'click',
					function() {
						var checked = false;
						if ($('#checkAll').attr('checked') == 'checked'
								|| $('#checkAll').attr('checked')) {
							checked = true;
						}
						selectAll(checked);
					});
			//搜索绑定
			$('#searchBtn').bind('click', function() {

			});
			//隐藏/显示绑定
			$('#showMore').bind('click', function() {
				show();
			});
			//清除按钮绑定
			$('#clearBtn').bind('click', function() {
				queryFormClear('revisitForm');
				$('#revisitForm').attr('action','${ctx}/revisit/revisitfail/findContract?menuId=${param.menuId }');
				$('#revisitForm').submit();
			});
			//导出客户信息表
			$("#revisitCustomer").bind('click', function() {
				var idVal = "";
				var revPersonInfo = $("#revisitForm").serialize();
				revPersonInfo = decodeURIComponent(revPersonInfo,true); 
				revPersonInfo = encodeURI(encodeURI(revPersonInfo));
				if($(":input[name='checkBoxItem']").length<=0)
				{
					art.dialog.alert("无可导出的客户信息!");
					return;
				}
				if($(":input[name='checkBoxItem']:checked").length>0){
					$(":input[name='checkBoxItem']:checked").each(function(){
		        		if(idVal!="")
		        		{
		        			idVal+=","+$(this).attr("applyId");
		        		}else{
		        			idVal=$(this).attr("applyId");
		        		}
		        	});
				}
				window.location.href=ctx+"/revisit/revisitfail/exportCustomerExcel?idVal="+idVal+"&"+revPersonInfo;
			});
			//导出Excel 
			$("#revisitExport").bind('click', function() {
				var idVal = "";
				var revPersonInfo = $("#revisitForm").serialize();
				revPersonInfo = decodeURIComponent(revPersonInfo,true); 
				revPersonInfo = encodeURI(encodeURI(revPersonInfo));
				if($(":input[name='checkBoxItem']").length<=0)
				{
					art.dialog.alert("无可导出的客户信息!");
					return;
				}
				if($(":input[name='checkBoxItem']:checked").length>0){
					$(":input[name='checkBoxItem']:checked").each(function(){
		        		if(idVal!="")
		        		{
		        			idVal+=","+$(this).attr("loanCode");
		        		}else{
		        			idVal=$(this).attr("loanCode");
		        		}
		        	});
				}
				window.location.href=ctx+"/revisit/revisitfail/exportExcel?idVal="+idVal+"&"+revPersonInfo;
			});
			
			loan.getstorelsit("storeName", "storeOrgId");
			rateObj.getRateQueryList('monthRateBtn', 'monthRateAll');
		});
</script>
</head>
<body >
      <div class="control-group">
		<form:form id="revisitForm" modelAttribute="revPersonInfo" action="${ctx}/revisit/revisitfail/findContract">
		 <input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
			<input type="hidden" name="menuId" value="${param.menuId }">
			<table class=" table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户名称：</label>
					    <form:input type="text" path="customerName"  class="input_text178" />
					</td>
					<td><label class="lab">门店：</label>
					  <form:input type="text" path="storeName" id="storeName" class="input_text178" readonly="true"/> 
					  <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
				    <form:hidden   path="storeOrgId" id="storeOrgId" />
					</td>
					<td><label class="lab">产品：</label>
				    <form:select path="replyProductName"  class="select180">
						<form:option value="">全部</form:option>
						 <c:forEach items="${productList}" var="product">
						   <form:option value="${product.productCode}" 
						 
								>${product.productName}</form:option>
						  </c:forEach> 
					 </form:select>
				 </td>
					
				</tr>
				<tr>
				 <td><label class="lab">合同编号：</label>
				     <form:input type="text"  path="contractCode"  class="input_text178" />
				 </td>
				 <td><label class="lab">身份证号：</label>
				      <form:input type="text"  path="identityCode"  class="input_text178" />
				  </td>
				  <td>
    			      <label class="lab">是否加急：</label>
			          <form:select path="urgentFlag" class="select180">
						    <form:option value="">全部</form:option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="urgentFlag">
						    	<form:option value="${urgentFlag.value}"
									>${urgentFlag.label}
								</form:option>
		     				</c:forEach> 
					 </form:select>
               	  </td>
				</tr>
				<tr id="T1" style="display: none">
				   <td><label class="lab">是否无纸化：</label>
				      <form:select class="select180" path="paperLessFlag">
                	    <form:option value="">请选择</form:option>
            			<form:option 
            			   
            			 value="1">是</form:option>
           			    <form:option 
           			       
           			     value="0">否</form:option>
           			</form:select>
					 </td>
				  <td><label class="lab">模式：</label> 
						<select name="modelLabel" class="select180">
							<option value=''>请选择</option>
							 <c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                  <option value="${loanmodel.value }"  <c:if test="${revPersonInfo.modelLabel == loanmodel.value }">selected</c:if>>
	                                   <c:if test="${loanmodel.value=='0'}">
	                                       
	                                  </c:if>
	                                  <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                  </option>
	                           </c:forEach>
	                      </select>
	             	</td>
    				<td><label class="lab">渠道：</label>
					    <form:select path="channelName" class="select180">
						  <form:option value=''>全部</form:option>
						   <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark">
							<c:if test="${mark.label!='TG' && mark.label!='附条件'}">
							  <form:option value="${mark.value}"
							 >${mark.label}
							  </form:option>
							</c:if>
						  </c:forEach> 
						</form:select>
					</td>
			    	</tr>
			    	<tr id="T2" style="display: none">
				       <td colspan="1"> <label class="lab">合同版本号：</label>
					      <form:select path="contractVersion" class="select180">
								<form:option value=''>全部</form:option>
								 <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="mark">
									<form:option value="${mark.value}"
									 
									>${mark.label}</form:option>
								</c:forEach> 
						  </form:select> 
					    </td>
						<td>
						<label class="lab">是否冻结：</label>
				 			<form:select class="select180" path="frozenFlag">
				               <form:option value="-1">请选择</form:option>
				           	   <form:option value="1">是</form:option>
				               <form:option value="0">否</form:option>
				            </form:select>
						</td>
						<td> <label class="lab">借款利率：</label>
			 		   	    <form:input type="text" path="monthRateAll"  class="input_text178" readonly="true"/> 
					  		<i id="monthRateBtn" class="icon-search" style="cursor: pointer;"></i>  
						</td>
					</tr>
					<tr id="T3" style="display: none">
					 <td><label class="lab">是否有保证人：</label>
				      <form:select class="select180" path="ensureManFlag">
                	    <form:option value="">请选择</form:option>
            			<form:option  value="1">有</form:option>
           			    <form:option  value="0">无</form:option>
           			</form:select>
				     </td> 
				 
					 <td><label class="lab">是否登记失败：</label>
					      <form:select class="select180" path="registFlag">
	                	    <form:option value="">请选择</form:option>
	            			<form:option value="1">成功</form:option>
	           			    <form:option value="0">失败</form:option>
	           			</form:select>
					 </td>
					<td >
					  <label class="lab">是否电销：</label>
			           <form:select path="telesalesFlag" class="select180">
						   <form:option value="">全部</form:option>
						   <c:forEach items="${fns:getDictList('yes_no')}" var="rsSrc">
						         <form:option value="${rsSrc.value}"
									
									>${rsSrc.label}
								  </form:option>
						    </c:forEach>
						 </form:select>
					</td>
					</tr>
					<tr id="T4" style="display: none">
					<td><label class="lab">风险等级：</label>
					      <form:select path="riskLevel" class="select180">
					          <form:option value=''>全部</form:option>
			    	         <c:forEach items="${fns:getDictList('jk_loan_risk_level')}" var="item">
					           <form:option value="${item.value}">
									${item.label}
								  </form:option>
					         </c:forEach> 
					     </form:select>
					     </td>
					     <td></td>
					     <td></td>
					</tr>
					
			</table>
			</form:form> 
			<div class="tright pr30 pb5">
				<button class="btn btn-primary" id="searchBtn" onclick="return page();">搜索</button>
				<button class="btn btn-primary" id="clearBtn">清除</button>
			
			<div style="float: left; margin-left: 45%; padding-top: 10px">
				<img src="../../../static/images/up.png" id="showMore"></img>
			</div>
		</div>
		</div>
		<p class="mb5">  
		 <c:if test="${jkhj_grantsure_csexport==null }">
		  <button class="btn btn-small jkhj_grantsure_csexport" id="revisitExport">导出Excel</button>
		 </c:if> 
		 <c:if test="${jkhj_grantsure_csexport_customer==null }">
		  <button class="btn btn-small jkhj_grantsure_csexport_customer" id="revisitCustomer">客户信息表导出</button>
		 </c:if>
		  </p>
		   
		    <sys:columnCtrl pageToken="outside"></sys:columnCtrl>
           <table  id="tableList"class="table  table-bordered table-condensed table-hover " style="overflow:scroll;">
                <thead>
                    <tr>
                    	<th><input  id="checkAll"   type="checkbox"/></th>
                        <th>序号</th>
						<th>合同编号</th>
						<th>门店</th>
                        <th>客户姓名</th>
                        <th>产品</th>
                        <th>共借人</th>
                        <th>自然人保证人</th>
                        <th>证件号码</th>
                        <th>期数</th>
                        <th>借款利率</th>
                        <th>合同金额</th>
                        <th>放款金额</th>
                        <th>催收服务费</th>
                        <th>开户行</th>
                        <th>支行名称</th>
                        <th>账号</th>
                        <th>回访状态</th>
                        <th>借款状态</th>
						<th>加急标识</th>
						<th>模式</th>
						<th>渠道</th>
						<th>合同版本号</th>
						<th>回访失败原因</th>
						<th>是否无纸化</th>
						<th>是否电销</th>
						<th>风险等级</th>
                        <th>操作</th>
                        <th></th>
                    </tr>
                   </thead>
                   <tbody id="prepareListBody">
                    <c:set var="index" value="0"/>
                   <c:forEach items="${page.list }" var="ps" varStatus="xh">
                  <c:set var="index" value="${index+1}" />
                      <tr  >
                        <td><input type="checkbox" name="checkBoxItem" loanCode='${ps.loanCode }' applyId='${ps.applyId}'/>
				        </td>
				        <td>${index}</td>
                        <td>${ps.contractCode}</td>
                        <td>${ps.storeName}</td>
                        <td>${ps.customerName}</td>
                        <td>${ps.replyProductName}</td>
                        <td>                     
                        	<c:if test="${ps.loanInfoOldOrNewFlag eq '0' || ps.loanInfoOldOrNewFlag eq ''}">
                        		${ps.coborrowerName}
                        	</c:if>
                        </td>
                        <td>
                        	<c:if test="${ps.loanInfoOldOrNewFlag eq '1'}">
                        		${ps.bestCoborrower}
                        	</c:if>
                        </td>
                        <td>${ps.identityCode}</td>
                        <td>${ps.replyMonth}</td>
                        <td>${ps.monthRate}</td>
                        <td><fmt:formatNumber value="${ps.contractMoney}" pattern=".00"/> </td>
                        <td><fmt:formatNumber value="${ps.lendingMoney}" pattern=".00"/></td>
                        <td><fmt:formatNumber value="${ps.urgeServiceFee}" pattern=".00"/> </td>
                        <td>${ps.depositBank}</td>
                        <td>${ps.bankBranchName}</td>
                        <td>${ps.bankAccountNumber}</td>
                        <td>失败</td>
                        <td>客户放弃</td>
                        <td><c:if test="${ps.urgentFlag=='1'}">加急</c:if></td>
                        <td><c:if test="${ps.modelLabel=='TG'}">${ps.modelLabel}</c:if></td>
                        <td>${ps.channelName}</td>
                        <td>${ps.contractVersion}</td>
                        <td>${ps.revisitReason}</td>
                        <td>
                        	<c:choose>
			                 <c:when test="${ps.paperLessFlag=='1'}">
			                   	是
			                 </c:when>
			                 <c:otherwise>
			                                                    否
			                 </c:otherwise>
			               </c:choose>
                        </td>
                        <td>
                        	<c:if test="${ps.telesalesFlag=='0'}">否</c:if>
                       		<c:if test="${ps.telesalesFlag=='1'}">是</c:if>
                        </td>
                        <td>${ps.riskLevel}</td>
                        <td>
                         <a class="jkhj_contracthis_view" href="#" onclick="showAllHisByLoanCode('${ps.loanCode}')">历史</a>
                        </td>
                        <td></td>
                      </tr>
                   </c:forEach>
                   </tbody>
                </table>
                <div class="modal-footer" style="margin-bottom: 12px;"></div>
			  <div class="pagination">${page}</div> 
			  <script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-220,
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
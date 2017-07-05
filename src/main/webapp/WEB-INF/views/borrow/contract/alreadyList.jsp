<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script src="${context}/js/contract/contractAudit.js"
	type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>我的已办列表</title>
<script language="javascript">

function page(n, s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#ctrCreatetForm").attr("action", "${ctx}/apply/contractAudit/findContractAndalready");
	$("#ctrCreatetForm").submit();
	return false;
}
taskParam={}    
$(document)
.ready(
		function() {
			$('* a').css('cursor','pointer');
			//操作按钮绑定
			$('.cz').click(function(){
			
			})
			
			//全选绑定
			$('#checkAll')
					.bind(
							'click',
							function() {
								var checked = false;
								if ($('#checkAll').attr('checked') == 'checked'
										|| $('#checkAll').attr(
												'checked')) {
									checked = true;
								}
								selectAll(checked);
							});
			//搜索绑定
			$('#searchBtn').bind(
					'click',
					function() {
						
					});
			//隐藏/显示绑定
			$('#showMore').bind('click', function() {
				show();
			});
			//清除按钮绑定
			$('#clearBtn').bind('click', function() {
				queryFormClear('ctrCreatetForm');
				$('#ctrCreatetForm').submit();
			});
			//历史绑定
			$(":input[name='history']").each(function() {

				$(this).bind('click', function() {

					viewAuditHistory($(this).attr('applyId'));
				});
			});
			//办理绑定
			$(":input[name='"+taskParam.taskBtnName+"']")
					.each(
							function() {
								$(this)
										.bind(
												'click',
												function() {
													dealTask(
															$(this)
																	.attr(
																			'applyId'),
															$(this)
																	.attr(
																			'wobNum'),
															$(this)
																	.attr(
																			'dealType'),
															$(this)
																	.attr(
																			'token'),
																			taskParam.taskStep,
																			taskParam.taskView);
												});
							});
			//批量取消TG绑定
			$("#cancelTGBtn").bind('click', function() {
				batchCancelFlag(kvParam,reloadParam,taskParam,null,null);
			});
			loan.getstorelsit("storeName","storeOrgId");
		});
</script>
</head>
<body >
      <div class="control-group">       
		<form:form id="ctrCreatetForm" modelAttribute="contractAndAlready" action="${ctx}/apply/contractAudit/findContractAndalready">
		<input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
			<input type="hidden" name="menuId" value="${param.menuId }">
			<table class=" table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户名称：</label>
					    <form:input path="loanCustomerName" name="customerName" value="${contractAndAlready.loanCustomerName}" class="input_text178" />
					</td>
					<td><label class="lab">门店：</label>
					  <form:input path="storeName" id="storeName" class="input_text178" readonly="true"/> 
					  <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
				    <form:hidden path="storeOrgId" id="storeOrgId" />
					</td>
					<td><label class="lab">产品：</label>
				    <form:select path="productType" value="${contractAndAlready.productType}" class="select180">
						<option value='${contractAndAlready.productType}'>全部</option>
						<c:forEach items="${productList}" var="product">
						   <option value="${product.productCode}" 
						     <c:if test="${product.productCode == contractAndAlready.productType}">selected=true</c:if>
								>${product.productName}</option>
						  </c:forEach>
					 </form:select>
				 </td>
					
				</tr>
				<tr>
				 <td><label class="lab">合同编号：</label>
				     <form:input path="contractCode" name="contractCode" value="${contractAndAlready.contractCode}" class="input_text178" />
				 </td>
				 <td><label class="lab">借款状态：</label>
				    <form:select path="dictLoanStatus" value="${contractAndAlready.dictLoanStatus}" class="select180">
						<option value=''>全部</option>
						<c:forEach items="${queryList}" var="lab">
						   <option value="${lab.name}" 
						   <c:if test="${lab.name==contractAndAlready.dictLoanStatus }">selected=true</c:if>
								>${lab.name}</option>
						  </c:forEach>
					 </form:select>
				  </td>
				  <td>
    			      <label class="lab">是否加急：</label>
			          <select name="loanUrgentFlag" class="select180">
						   <option value="">全部</option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="urgentFlag">
						    	<option value="${urgentFlag.value}"
									 <c:if test="${contractAndAlready.loanUrgentFlag==urgentFlag.value}">
									    selected=true
									  </c:if>
									>${urgentFlag.label}
								</option>
		     				</c:forEach>
					 </select>
               	  </td>
				</tr>
				<tr id="T1" style="display: none">
				  <td><label class="lab">身份证号：</label>
				      <form:input path="customerCertNum" name="customerCertNum" value="${contractAndAlready.customerCertNum}" class="input_text178" />
				  </td>
				  <td><label class="lab">模式：</label> 
						<select name="model" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                  <option value="${loanmodel.value }"
	                                   <c:if test="${contractAndAlready.model == loanmodel.value }">selected</c:if>>
	                                  <c:if test="${loanmodel.value=='0'}">
	                                    
	                                  </c:if>
	                                  <c:if test="${loanmodel.value!='0'}">${loanmodel.label}</c:if>
	                                  </option>
	                           </c:forEach>
	                      </select>
	             	</td>
    				<td><label class="lab">渠道：</label>
					    <form:select path="loanFlag" class="select180">
						  <option value=''>全部</option>
						  <c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark">
							<c:if test="${mark.label!='TG' && mark.label!='附条件'}">
							  <option value="${mark.value}"
							  <c:if test="${contractAndAlready.loanFlag == mark.value}">
								     selected=true
							  </c:if>>${mark.label}
							  </option>
							</c:if>
						  </c:forEach>
						</form:select>
					</td>
			    	</tr>
			    	<tr id="T2" style="display: none">
				       <td colspan="1"> <label class="lab">合同版本号：</label>
					      <select name="contractVersion" class="select180">
								<option value=''>全部</option>
								<c:forEach items="${fns:getDictList('jk_contract_ver')}" var="mark">
									<option value="${mark.value}"
									 <c:if test="${contractAndAlready.contractVersion==mark.value}">
									    selected=true
									  </c:if>
									>${mark.label}</option>
								</c:forEach>
						  </select> 
					    </td>
						<td>
						<label class="lab">是否追加借：</label>
				 			<select name="dictIsAdditional" class="select180">
						   		<option value="">全部</option>
								<c:forEach items="${fns:getDictList('yes_no')}" var="item">
							 	  <option value="${item.value}"
									 <c:if test="${contractAndAlready.dictIsAdditional==item.value}">
									    selected=true
									  </c:if>
									>${item.label}
								  </option>
				        	    </c:forEach>
               	            </select>
						</td>
						<td> <label class="lab">来源系统：</label>
					   	 <select class="select180" name="dictSourceType">
					   	    <option value=''>全部</option>
					   	    <c:forEach items="${fns:getDictList('jk_new_old_sys_flag')}" var="item">
							 	  <option value="${item.value}"
									 <c:if test="${contractAndAlready.dictSourceType==item.value}">
									    selected=true
									  </c:if>
									>${item.label}
								  </option>
				        	</c:forEach>
			 		   	 </select> 
						</td>
					</tr>
					<tr id="T3" style="display: none">
					 <td><label class="lab">团队经理：</label>
				       <form:input path="loanTeamManagerName" name="loanTeamManagerName" value="${contractAndAlready.loanTeamManagerName}" class="input_text178" />
				     </td> 
				 
					 <td><label class="lab">客户经理：</label>
				      <form:input path="loanManagerName" name="loanManagerName" value="${contractAndAlready.loanManagerName}" class="input_text178" />
					 </td>
					<td >
					  <label class="lab">是否电销：</label>
			           <select name="loanIsPhone" class="select180">
						   <option value="">全部</option>
						   <c:forEach items="${fns:getDictList('yes_no')}" var="rsSrc">
						         <option value="${rsSrc.value}"
									 <c:if test="${contractAndAlready.loanIsPhone==rsSrc.value}">
									    selected=true
									  </c:if>
									>${rsSrc.label}
								  </option>
						    </c:forEach>
						 </select>
					</td>
					</tr>
					<tr id="T4" style="display: none">
					<td><label class="lab">风险等级：</label>
					      <select name="riskLevel" class="select180">
					          <option value=''>全部</option>
			    	        <c:forEach items="${fns:getDictList('jk_loan_risk_level')}" var="item">
					           <option value="${item.value}"
									 <c:if test="${contractAndAlready.riskLevel==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
					         </c:forEach>
					     </select>
					     </td>
					     <td>
    			      <label class="lab">最后审核时间：</label>
			         <input id="auditingTime" name="auditingTimeStart" value="${contractAndAlready.auditingTimeStart }" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH'})" style="cursor: pointer" />
			         -
			         <input id="auditingTime" name="auditingTimeEnd" value="${contractAndAlready.auditingTimeEnd }" type="text" class="Wdate input_text178 required" size="10" onClick="WdatePicker({skin:'whyGreen',maxDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH'})" style="cursor: pointer" />
               	  </td>
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
		    <input type="hidden" id="contractCode" value="" />
		    <sys:columnCtrl pageToken="outside"></sys:columnCtrl>
		<div class="box5">
           <table  id="tableList"class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
                <thead>
                    <tr>
                        <th>序号</th>
						<th>合同编号</th>
                        <th>客户姓名</th>
                        <th>共借人</th>
                        <th>省份</th>
                        <th>城市</th>
                        <th>门店</th>
                        <th>产品</th>
                        <th>状态</th>
                        <th>退回原因</th>                       
                        <th>批复金额</th>
                        <th>批复期数</th>
                        <th>签约平台</th>
                        <th>进件时间</th>
                        <th>门店上传时间</th>
                        <th>最后审核时间</th>
                        <th>汇诚审批时间</th>
                        <th>上调标识</th>
                        <th>加急标识</th>
                        <th>模式</th>
                        <th>渠道</th>
                        <th>无纸化标识</th>
                        <th>版本号</th>
                        <th>是否登记失败</th>
						<th>风险等级</th>
                        <th>操作</th>
                    </tr>
                   </thead>
                   <tbody id="prepareListBody">
                   <c:forEach items="${page.list }" var="ps" varStatus="xh">
                      <tr  >
                        <td>${xh.count+(page.pageSize*(page.pageNo-1))}</td>
                        <td>${ps.contractCode}</td>
                        <td>${ps.loanCustomerName}</td>
                        <td>${ps.coboName }</td>
                        <td>${ps.storeProviceName }</td>
                        <td>${ps.storeCityName }</td>
                        <td>${ps.storeCode }</td>
                        <td>${ps.productType }</td>
                        <td>${ps.dictLoanStatus }</td>
                        <td>${ps.contractBackResult }</td>
                        <td><fmt:formatNumber value="${ps.loanAuditAmount}" pattern=".00"/></td>
                        <td>${ps.loanAuditMonth }</td>
                        <td>
                         	<c:forEach items="${fns:getDictList('jk_deduct_plat')}" var="item">
                              <c:if test="${item.value==ps.bankSigningPlatform }">
                                 ${item.label}
                              </c:if>					           
					         </c:forEach>                        
                        </td>
                        <td><fmt:formatDate value="${ps.customerIntoTime}" pattern="yyyy-MM-dd"/></td>
                        <td>${ps.operateTime}</td>
                        <td>${ps.auditingTime}</td>
                        <td><fmt:formatDate value="${ps.loanAuditTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td><c:if test="${ps.loanRaiseFlag=='1'}">是</c:if></td>
                        <td> <c:if test="${ps.loanUrgentFlag=='1'}">加急</c:if></td>
                        <td><c:if test="${ps.model=='1'}">TG</c:if></td>
                        <td>${ps.loanFlag }</td>
                        <td><c:if test="${ps.paperlessFlag=='1'}">是</c:if><c:if test="${ps.paperlessFlag=='0'}">否</c:if></td>
                        <td>${ps.contractVersionLabel }</td>
                        <td><c:if test="${ps.isRegister=='1'}">成功</c:if><c:if test="${ps.isRegister=='0'}">失败</c:if></td>
                        <td>${ps.riskLevel }</td>
                        <td >
                        <a class="jkhj_contracthis_view" href="#" onclick="showAllHisByLoanCode('${ps.loanCode}')">历史</a>
                        </td>
                      </tr>
                   </c:forEach>
                   </tbody>
                </table>
			  <div class="pagination">${page}</div>
			  </div>
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

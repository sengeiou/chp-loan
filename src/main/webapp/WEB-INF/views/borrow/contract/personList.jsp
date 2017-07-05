<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script src="${context}/js/contract/contractAudit.js"
	type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/grant/revisitStatusDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>已制作合同列表</title>
<script language="javascript">
$(function() {
	revisitStatusObj.getRevisitStatusList("revisitStatusId","revisitStatuss","revisitStatusName","isbtn");
})

function page(n, s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#ctrCreatetForm").attr("action", "${ctx}/apply/contractAudit/findContract");
	$("#ctrCreatetForm").submit();
	return false;
}

taskParam={
		taskBtnName:'dealctrCreate',
		taskStep:'合同制作',
		taskView:'loanflow_contractCreate_approve_0'
   } 
   reloadParam={
	 curForm:'ctrCreatetForm',
	 queueName:'HJ_CONTRACT_COMMISSIONER',
	 childPage:'loanflow_contractCreate_workItems'   
   }
   kvParam={
	key:'loanFlag',
	value:' '
   }
   cancelFlagRetVal = '0';
 function getCode(x,y,z){
	 var a=$("#lc").val();
	 var docId=$('#docId').val();
	 var conCode=$('#contractCode').val();
	 docId=y;
	 a=x;
	 conCode=z;
	 window.location='${ctx}/apply/contractAudit/contractAndPersonDetails?loanCode='+a+'&docId='+docId+'&contractCode='+conCode;
 }

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
							$('#ctrCreatetForm').attr('action','${ctx}/apply/contractAudit/findContract?menuId=${param.menuId }');
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
       
		<form:form id="ctrCreatetForm" modelAttribute="contractAndPersonInfo" action="${ctx}/apply/contractAudit/findContract">
		<input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
			<input type="hidden" name="menuId" value="${param.menuId }">
			<table class=" table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户名称：</label>
					    <form:input path="loanCustomerName" name="customerName" value="${contractAndPersonInfo.loanCustomerName}" class="input_text178" />
					</td>
					<td><label class="lab">门店：</label>
					  <form:input path="storeName" id="storeName" class="input_text178" readonly="true"/> 
					  <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
				    <form:hidden path="storeOrgId" id="storeOrgId" />
					</td>
					<td><label class="lab">产品：</label>
				    <form:select path="productType" value="${contractAndPersonInfo.productType}" class="select180">
						<option value='${contractAndPersonInfo.productType}'>全部</option>
						<c:forEach items="${productList}" var="product">
						   <option value="${product.productCode}" 
						     <c:if test="${product.productCode == contractAndPersonInfo.productType}">selected=true</c:if>
								>${product.productName}</option>
						  </c:forEach>
					 </form:select>
				 </td>
					
				</tr>
				<tr>
				 <td><label class="lab">合同编号：</label>
				     <form:input path="contractCode" name="contractCode" value="${contractAndPersonInfo.contractCode}" class="input_text178" />
				 </td>
				 <td><label class="lab">借款状态：</label>
				    <form:select path="dictLoanStatus" value="${contractAndPersonInfo.dictLoanStatus}" class="select180">
						<option value=''>全部</option>
						<c:forEach items="${queryList}" var="lab">
						   <option value="${lab.name}" 
						   <c:if test="${lab.name==contractAndPersonInfo.dictLoanStatus }">selected=true</c:if>
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
									 <c:if test="${contractAndPersonInfo.loanUrgentFlag==urgentFlag.value}">
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
				      <form:input path="customerCertNum" name="customerCertNum" value="${contractAndPersonInfo.customerCertNum}" class="input_text178" />
				  </td>
				  <td><label class="lab">模式：</label> 
						<select name="model" class="select180">
							<option value=''>请选择</option>
							<c:forEach items="${fns:getDictList('jk_loan_model')}" var="loanmodel">
	                                  <option value="${loanmodel.value }"
	                                   <c:if test="${contractAndPersonInfo.model == loanmodel.value }">selected</c:if>>
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
							  <c:if test="${contractAndPersonInfo.loanFlag == mark.value}">
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
									 <c:if test="${contractAndPersonInfo.contractVersion==mark.value}">
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
									 <c:if test="${contractAndPersonInfo.dictIsAdditional==item.value}">
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
									 <c:if test="${contractAndPersonInfo.dictSourceType==item.value}">
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
				       <form:input path="loanTeamManagerName" name="loanTeamManagerName" value="${contractAndPersonInfo.loanTeamManagerName}" class="input_text178" />
				     </td> 
				 
					 <td><label class="lab">客户经理：</label>
				      <form:input path="loanManagerName" name="loanManagerName" value="${contractAndPersonInfo.loanManagerName}" class="input_text178" />
					 </td>
					<td >
					  <label class="lab">是否电销：</label>
			           <select name="loanIsPhone" class="select180">
						   <option value="">全部</option>
						   <c:forEach items="${fns:getDictList('yes_no')}" var="rsSrc">
						         <option value="${rsSrc.value}"
									 <c:if test="${contractAndPersonInfo.loanIsPhone==rsSrc.value}">
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
									 <c:if test="${ctrQryParam.riskLevel==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
					         </c:forEach>
					     </select>
					     </td>
					     <td>
    			      <label class="lab">是否冻结：</label>
			          <select name="loanFrozenFlag" class="select180">
						   <option value="">全部</option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="frozenFlag">
						    	<option value="${frozenFlag.value}"
									 <c:if test="${contractAndPersonInfo.loanFrozenFlag==frozenFlag.value}">
									    selected=true
									  </c:if>
									>${frozenFlag.label}
								</option>
		     				</c:forEach>
					 </select>
               	  </td>
				  	<td><label class="lab">回访状态：</label>
						<form:input path="revisitStatusName" id="revisitStatusName" class="input_text178" readonly="true"/>
					    <form:hidden path="revisitStatuss" id="revisitStatuss"/>
						<i id="revisitStatusId"  class="icon-search" style="cursor: pointer;"></i>
					</td>
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
		   <input type="hidden" value="" id="lc" />
		   <input type="hidden" id="docId" value="" />
		    <input type="hidden" id="contractCode" value="" />
		    <sys:columnCtrl pageToken="outside"></sys:columnCtrl>
           <table  id="tableList"class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
                <thead>
                    <tr>
                        <th>序号</th>
						<th>合同编号</th>
						<th>合同版本号</th>
                        <th>客户姓名</th>
                        <th>共借人</th>
                        <th>自然人保证人</th>
                        <th>省份</th>
                        <th>城市</th>
                        <th>门店</th>
                        <th>状态</th>
                        <th>产品</th>
                        <th>批复金额</th>
                        <th>批复期数</th>
                        <th>加急标识</th>
                        <th>团队经理</th>
                        <th>销售人员</th>
                        <th>进件时间</th>
                        <th>放款时间</th>
						<th>渠道</th>
						<th>模式</th>
						<th>是否电销</th>
						<th>风险等级</th>
						<th>回访状态</th>
                        <th>操作</th>
                    </tr>
                   </thead>
                   <tbody id="prepareListBody">
                   <c:forEach items="${page.list }" var="ps" varStatus="xh">
                      <tr  >
                        <td>${xh.count+(page.pageSize*(page.pageNo-1))}</td>
                        <td>${ps.contractCode}</td>
                        <td>${ps.contractVersionLabel}</td>
                        <td>${ps.loanCustomerName}</td>
                        <td>                     
                        	<c:if test="${ps.loanInfoOldOrNewFlag eq '0' || ps.loanInfoOldOrNewFlag eq ''}">
                        		${ps.coboName}
                        	</c:if>
                        </td>
                        <td>
                        	<c:if test="${ps.loanInfoOldOrNewFlag eq '1'}">
                        		${ps.bestCoborrower}
                        	</c:if>
                        </td>
                        <td>${ps.storeProviceName}</td>
                        <td>${ps.storeCityName}</td>
                        <td>${ps.storeCode}</td>
                        <td>
                         <c:choose>
                           <c:when test="${ps.frozenCode != null && ps.frozenCode!=' ' && ps.frozenCode!=''}">
                             ${ps.dictLoanStatus}(门店申请冻结)
                           </c:when>
                           <c:otherwise>
                           
                           ${ps.dictLoanStatus}
                           </c:otherwise>
                         </c:choose>
                        </td>
                        <td>${ps.productType}</td>
                        <td><fmt:formatNumber value="${ps.loanAuditAmount}" pattern="0.00"/> </td>
                        <td>${ps.loanAuditMonth}</td>
                        <td>
                        <c:if test="${ps.loanUrgentFlag=='1'}">加急</c:if>
                        </td>
                        <td>${ps.loanTeamManagerName}</td>
                        <td>${ps.loanManagerName}</td>

                        <td><fmt:formatDate value="${ps.customerIntoTime}" pattern="yyyy-MM-dd"/></td>
                         <td><fmt:formatDate value="${ps.lendingTime}" pattern="yyyy-MM-dd"/></td>
                        <td>${ps.loanFlag }</td>
                        <td><c:if test="${ps.modelLabel=='TG'}">${ps.modelLabel}</c:if></td>
                        <td>
                        <c:if test="${ps.loanIsPhone=='0'}">否</c:if>
                       <c:if test="${ps.loanIsPhone=='1'}">是</c:if>
                        </td>
                        <td>${ps.riskLevel }</td>
                        <td>
			             	<c:choose>
			             	  <c:when test="${ps.revisitStatus == '' || ps.revisitStatus == null}">
                 			  </c:when>
			                  <c:when test="${ps.revisitStatus == -1 }">
			                                                     失败
			                  </c:when>
			                  <c:when test="${ps.revisitStatus == 0 }">
			                                                     待回访	
			                  </c:when>
			                  <c:when test="${ps.revisitStatus == 1 }">
			                                                      成功
			                  </c:when>
			                </c:choose>
			             </td>
                        <td >
                       <c:if test="${jkhj_createddoc_elecview==null }">
                        <c:if test="${ps.signCount!='0'}">
                            <a class="cz jkhj_createddoc_elecview" onclick="javascript:signDocShow('','${ps.loanCode}','${ps.contractCode}');">
                                                                            电子协议查看
                            </a>
                        </c:if>
                        </c:if>
                         <c:if test="${jkhj_createddoc_view==null }">
                         <a class="cz jkhj_createddoc_view" onclick="getCode('${ps.loanCode}','${ps.docId}','${ps.contractCode}');">查看</a>
                         </c:if>
                          &nbsp;&nbsp;
                          <c:if test="${jkhj_createddoc_history==null }">
                          <a class="jkhj_createddoc_history" href="#" onclick="showAllHisByLoanCode('${ps.loanCode}')">历史</a>
                          </c:if>
                        </td>
                      </tr>
                   </c:forEach>
                   </tbody>
                </table>
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

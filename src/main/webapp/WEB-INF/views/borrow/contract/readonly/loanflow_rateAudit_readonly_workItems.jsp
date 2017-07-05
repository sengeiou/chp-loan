<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head><!--  -->
<script src="${context}/js/contract/readonly/contractAuditReadOnly.js" type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script src="${context}/js/contract/rateList.js" type="text/javascript"></script>
<script src="${context}/js/contract/paperLessFlag.js" type="text/javascript"></script>
<script src="${context}/js/contract/channelHandler.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>利率审核只读待办列表</title>
<script language="javascript">
REDIRECT_URL='/apply/contractAudit/fetchTaskItems4ReadOnly';
   taskParam={
		taskBtnName:'dealRateAudit',
		taskStep:'利率审核',
		taskView:'loanflow_rateAudit_approve_0'
   };
   reloadParam={
	  curForm:'rateAuditForm',
	  flagForm:'flagForm'
   };
   kvParam={
	  key:'loanFlag',
	  value:' '
   };
   tgParam = {
      key:'loanFlag', 
      value:'${tgCode}',
      label:'${tgName}'
   };
   cancelFlagRetVal = '0';
$(document).ready(
  function() {
    // 全选绑定
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
		// 搜索绑定
		$('#searchBtn').bind(
		  'click',
		  function() {
			fetchTaskItems('rateAuditForm','flagForm');
					 });
		// 隐藏/显示绑定
		$('#showMore').bind('click', function() {
				show();
		 	});
		// 清除按钮绑定
		$('#rateAuditFormClrBtn').bind('click', function() {
			queryFormClear('rateAuditForm');
			fetchTaskItems('rateAuditForm','flagForm');
		});
		// 历史绑定
		$(":input[name='history']").each(function() {
            $(this).bind('click', function() {
                viewAuditHistory($(this).attr('loanCode'));
			});
		});
		// 办理绑定
		$(":input[name='dealRateAudit']").each(
			function() {
				$(this).bind(
		    		'click',
						function() {
							dealTask($(this).attr('applyId'),
												$(this).attr('wobNum'), "1", $(
														this).attr('token'),$(this).attr("stepName"));
									});
		});
		//批量取消标识按钮绑定
	    $('#cancelFlagBtn').bind('click',function(){
	       batchCancelFlag(kvParam,reloadParam,null);
	    });
		// 批量取消TG绑定
		$("#cancelTGBtn").bind('click',function(){
			batchCancelFlag(kvParam,reloadParam,'TG');
		});
	    $('#addTGBtn').bind('click',function(){
			batchAddTGFlag(tgParam,reloadParam);
		});
		loan.getstorelsit("storeName","storeOrgId");
		rateObj.getRateList('taskDispatchBtn');
	/* 	// 添加无纸化标识
		$('#addPaperLessBtn').bind('click',function(){
		paperLessFlag.add("paperLessFlag","1",reloadParam);
		});
	    // 取消无纸化标识
		$('#cancelPaperLessBtn').bind('click',function(){
			paperLessFlag.cancel("paperLessFlag","0",reloadParam);
		}); */
	    // 占比查看
	    $('#percentViewBtn').bind('click',function(){
	    	openContractSummary();
	    });
	    $('#addChannelBtn').bind('click',function(){
	    	channel.openDialog('loanFlag',$('#flowFlag').val(),REDIRECT_URL);
	    });
	    var msg = "${message}";
		if (msg != "" && msg != null) {
			top.$.jBox.tip(msg);
		};
	});
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	fetchTaskItems('rateAuditForm',
	'flagForm');
	return false;
}
document.onkeydown = function(e) {
	 //捕捉回车事件
	 var ev = (typeof event!= 'undefined') ? window.event : e;
	 if(ev.keyCode == 13) {
		 $('#searchBtn').click();
	 }
	}
</script>
</head>
<body>
	<div>
    	<div class="control-group">
    	  <form id="flagForm">
    	    <input type="hidden" value="${flowFlag}" id="flowFlag" name="flowFlag"/>
    	  </form>
    	  <input type="hidden" id="batchColl"/>
		  <form id="rateAuditForm">
		  <input type="hidden" name="menuId" value="${param.menuId }">
		    <input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
			<table class=" table1" cellpadding="0" cellspacing="0" border="0"width="100%">
			  <tr>
				<td><label class="lab">客户姓名：</label> 
				   <input type="text" name="customerName" value="${ctrQryParam.customerName}" class="input_text178" /></td>
				 <td><label class="lab" >门店：</label>
					<input type="text" id="storeName" name="storeName" value="${ctrQryParam.storeName}" readonly="readonly" class="input_text178" /> 
					 <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
				    <input type="hidden" name="storeOrgId" value="${ctrQryParam.storeOrgId}" id="storeOrgId" />
		       	  </td>
				   <td><label class="lab" >产品：</label>
						<select name="replyProductCode" value="${ctrQryParam.replyProductCode}" class="select180">
						    	<option value=''>全部</option>
						    	<c:forEach items="${productList}" var="product">
									<option value="${product.productCode}"
									  <c:if test="${ctrQryParam.replyProductCode==product.productCode}">
									    selected=true
									  </c:if>
									>${product.productName}</option>
								</c:forEach>
						</select>
					</td>
					</tr>
					<tr>
					   <td><label class="lab">模式：</label>
						 <select id="model" name="model" class="select180">
                          <option value="">全部</option>
                          <c:forEach items="${fns:getDictList('jk_loan_model')}" var="item">
                         	  <option value="${item.value}"
					      		 <c:if test="${item.value==ctrQryParam.model}">
					      		   selected=true
					      		 </c:if>
					      		>${item.label}</option>
					       </c:forEach>
				        </select>	
						</td>
						<td><label class="lab" >渠道：</label>
							<select name="channelCode" class="select180">
								<option value=''>全部</option>
								<c:forEach items="${fns:getDictList('jk_channel_flag')}" var="mark">
								 <c:if test="${mark.label!='TG' && mark.label!='XT01' && mark.label!='附条件'}">
									<option value="${mark.value}"
									 <c:if test="${ctrQryParam.channelCode==mark.value}">
									    selected=true
									  </c:if>
									>${mark.label}</option>
								 </c:if>
								</c:forEach>
						</select>
						</td>
						<td><label class="lab">是否加急：</label>
						 <select name="urgentFlag" class="select180">
						        <option value=''>全部</option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="urgentFlag">
						    	<option value="${urgentFlag.value}"
									 <c:if test="${ctrQryParam.urgentFlag==urgentFlag.value}">
									    selected=true
									  </c:if>
									>${urgentFlag.label}
								</option>
		     				</c:forEach>
						 </select>
					</tr>
					<tr id="T1" style="display: none;" >
					    <td><label class="lab">身份证号：</label> <input
							type="text" name="identityCode" value="${ctrQryParam.identityCode}" class="input_text178" />
						</td>
						<td><label class="lab">是否追加借：</label> 
						 <select name="additionalFlag" class="select180">
						 		 <option value=''>全部</option>
			    				<c:forEach items="${fns:getDictList('yes_no')}" var="item">
							 	  <option value="${item.value}"
									 <c:if test="${ctrQryParam.additionalFlag==item.value}">
									    selected=true
									  </c:if>
									>${item.label}
								  </option>
				        	    </c:forEach>
               	          </select>
						</td>
						<td><label class="lab">是否电销：</label> 
						 <select name="telesalesFlag" class="select180">
						 		 <option value=''>全部</option>
						   <c:forEach items="${fns:getDictList('yes_no')}" var="rsSrc">
						         <option value="${rsSrc.value}"
									 <c:if test="${ctrQryParam.telesalesFlag==rsSrc.value}">
									    selected=true
									  </c:if>
									>${rsSrc.label}
								  </option>
						    </c:forEach>
						  </select>
					    </td>
					 </tr>
					 <tr id="T2" style="display: none;" >
					    <td>
					     <label class="lab">版本号：</label> 
					     <select name="contractVersion" class="select180">
					          <option value=''>全部</option>
			    	        <c:forEach items="${fns:getDictList('jk_contract_ver')}" var="item">
			    	          <c:if test="${item.value=='5'}">
					           <option value="${item.value}"
									 <c:if test="${ctrQryParam.contractVersion==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
							   </c:if>
					         </c:forEach>
					     </select>
					    </td>
					    <td><label class="lab">风险等级：</label>
					      <select name="riskLevel" class="select180">
					          <option value=''>全部</option>
			    	        <c:forEach items="${riskLevels}" var="item">
					          <option value="${item}"
									 <c:if test="${ctrQryParam.riskLevel==item}">
									    selected=true
									  </c:if>
									>
									${item}
								  </option>
					         </c:forEach>
					     </select>
					     </td>
					    <td></td>
					 </tr>
				</table>
			</form>
			<div class="tright pr30 pb5">
				<input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
				<input type="button" id="rateAuditFormClrBtn" class="btn btn-primary" value="清除"></input>
			<div style="float: left; margin-left: 45%; padding-top: 10px">
				<img src="${context}/static/images/up.png" id="showMore"></img>
			</div>
		</div>
		</div>
		<div  id="auditList">
	    <!-- 
			<p class="mb5">
			    <c:if test="${isRateLeader=='1'}">
			    	<button class="btn btn-small jkhj_rateaudit_taskdispatch" id="taskDispatchBtn">任务分配设置</button>
			    </c:if>
			    <button class="btn btn-small jkhj_rateaudit_channel" id="addChannelBtn">渠道</button>
			    <button class="btn btn-small jkhj_rateaudit_channel_cancel" id="cancelFlagBtn">批量取消渠道标识</button>
				<c:if test="${isRateLeader=='1'}">
					<button class="btn btn-small jkhj_rateaudit_percent_view" id="percentViewBtn">占比查看</button>
				</c:if>
				<span><label>当前版本号：</label>
					<c:if test="${curVersion=='4'}">
					    1.4
					 </c:if>
					 <c:if test="${curVersion!='4'}">
						1.${curVersion}
					 </c:if>
				</span>
			</p>
		 -->
			 <sys:columnCtrl pageToken="rateAuditlist"></sys:columnCtrl>
			<div class="box5">
			<table id="tableList" class="table  table-bordered table-condensed table-hover ">
				<thead>
					<tr>
						<th><input type="checkbox" id="checkAll"></input>序号</th>
						<th>合同编号</th>
						<th>版本号</th>
						<th>客户姓名</th>
						<th>共借人</th>
						<th>省份</th>
						<th>城市</th>
						<th>门店</th>
						<th>产品</th>
						<th>状态</th>
						<th>批复金额</th>
						<th>批复期数</th>
						<th>是否电销</th>
						<th>进件时间</th>
						<th>汇诚审批时间</th>
						<th>上调标识</th>
						<th>加急标识</th>
						<th>模式</th>
						<th>渠道</th>
						<th>风险等级</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody id="prepareListBody">
					<c:if test="${ workItems!=null && fn:length(workItems)>0}">
						<c:set var="index" value="0" />
						<c:forEach items="${workItems}" var="item">
							<c:set var="index" value="${index+1}" />
							<c:choose>
							  <c:when test="${item.backFlag=='1'}">
							    <tr class="red">
								 <td><input type="checkbox" name="prepareCheckEle" paperLessFlag='${item.paperLessFlag}' flag='${item.channelName}' 
								     model="${item.model}" 
									value='${item.applyId},${item.wobNum},${item.flowName},${item.token},${item.stepName},${item.loanCode}' />${index}</td>
								 <td>${item.contractCode}</td>
								 <td>
								     <c:if test="${item.contractVersion=='4'}">
								          1.4
								     </c:if>
								      <c:if test="${item.contractVersion!='4'}">
								          1.${item.contractVersion}
								     </c:if>
								 </td>
								 <td>${item.customerName}</td>
								 <td>
								   <c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                                     ${item.coborrowerName}
                                   </c:if> 
								 </td>
								 <td>${item.provinceName}</td>
								 <td>${item.cityName}</td>
								 <td>${item.storeName}</td>
								 <td>${item.replyProductName}</td>
								 <td>${item.loanStatusName}</td>
								 <td><fmt:formatNumber value='${item.replyMoney}' pattern="###00.00"/></td>
								 <td>${item.replyMonth}</td>
								 <td>
								   <c:if test="${item.telesalesFlag=='1'}">
                                      <span>是</span>
                                   </c:if>
								 </td>
								 <td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
								 <td>${item.outApproveTime}</td>
								 <td>
								    <c:if test="${item.raiseFlag=='1'}">
                   					 	<span>是</span>
               					 	</c:if>
								 </td>
								 <td>
								   <c:if test="${item.urgentFlag=='1'}">
                                      <span style="color:red">加急</span>
                                   </c:if>
                                 </td>
                                 <td>${item.modelLabel}</td>
								 <td>${item.channelName}</td>
								 <td>${item.riskLevel}</td>
								 <td>
								 <input type="button" class="btn_edit"
									applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
									token='${item.token}' stepName="${item.stepName}" name="dealRateAudit" value="查看" />
									<input type="button" class="btn_edit" loanCode='${item.loanCode}'
									name="history" value="历史" />
								</td>
							  </tr>
							</c:when>
							<c:otherwise>
							   <tr>
								 <td><input type="checkbox" name="prepareCheckEle" paperLessFlag='${item.paperLessFlag}' flag='${item.channelName}' 
								  model="${item.model}" 
								  value='${item.applyId},${item.wobNum},${item.flowName},${item.token},${item.stepName},${item.loanCode}' />${index}</td>
								 <td>${item.contractCode}</td>
								 <td>
								     <c:if test="${item.contractVersion=='4'}">
								          1.4
								     </c:if>
								     <c:if test="${item.contractVersion!='4'}">
								          1.${item.contractVersion}
								     </c:if>
								 </td>
								 <td>${item.customerName}</td>
								 <td>
								   <c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                                     ${item.coborrowerName}
                                   </c:if> 
								 </td>
								 <td>${item.provinceName}</td>
								 <td>${item.cityName}</td>
								 <td>${item.storeName}</td>
								 <td>${item.replyProductName}</td>
								 <td>${item.loanStatusName}</td>
								 <td><fmt:formatNumber value='${item.replyMoney}' pattern="###00.00"/></td>
								 <td>${item.replyMonth}</td>
								 <td>
								   <c:if test="${item.telesalesFlag=='1'}">
                                      <span>是</span>
                                   </c:if>
								 </td>
								 <td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
								 <td>${item.outApproveTime}</td>
								 <td>
								    <c:if test="${item.raiseFlag=='1'}">
                   					 	<span>是</span>
               					 	</c:if>
								 </td>
								 <td>
								   <c:if test="${item.urgentFlag=='1'}">
                                      <span style="color:red">加急</span>
                                   </c:if>
                                 </td>
                                 <td>${item.modelLabel}</td>
								 <td>${item.channelName}</td>
								 <td>${item.riskLevel}</td>
								 <td>
								   <input type="button" class="btn_edit"
									applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='1'
									token='${item.token}' stepName="${item.stepName}" name="dealRateAudit" value="查看" />
									<input type="button" class="btn_edit" loanCode='${item.loanCode}'
									name="history" value="历史" />
								 </td>
							    </tr>
							</c:otherwise>
							</c:choose>
						</c:forEach>
					</c:if>
					<c:if test="${ workItems==null || fn:length(workItems)==0}">
						<tr>
							<td colspan="20" style="text-align: center;">没有待办数据</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<c:if test="${workItems!=null || fn:length(workItems)>0}">
			  <div class="page">${page}</div>
			</c:if>
		</div>
	</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-260,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>
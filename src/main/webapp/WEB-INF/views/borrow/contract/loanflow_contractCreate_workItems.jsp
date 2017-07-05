<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<!--  -->
<script src="${context}/js/contract/contractAudit.js"
	type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<title>合同制作</title>
<script language="javascript">
REDIRECT_URL="/apply/contractAudit/fetchTaskItems";
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
   $(document).ready(function() {
	//全选绑定
	$('#checkAll').bind('click',function() {
		var checked = false;
		if ($('#checkAll').attr('checked') == 'checked'
			 || $('#checkAll').attr('checked')){
			checked = true;
		 }
		 selectAll(checked);
	});
	//搜索绑定
	$('#searchBtn').bind('click',function() {
	  fetchTaskItems('ctrCreatetForm','flagForm');
	});
	//隐藏/显示绑定
	$('#showMore').bind('click', function() {
	   show();
	});
	//清除按钮绑定
	$('#clearBtn').bind('click', function() {
	   queryFormClear('ctrCreatetForm');
	   fetchTaskItems('ctrCreatetForm','flagForm');
	});
    //历史绑定
	$(":input[name='history']").each(function() {
	   $(this).bind('click', function() {
		  viewAuditHistory($(this).attr('loanCode'));
	   });
	});
	//办理绑定
	$(":input[name='"+taskParam.taskBtnName+"']").each(function() {
	   $(this).bind('click',function() {
			dealTaskNew($(this).attr('applyId'),
					$(this).attr('wobNum'), $(this)
							.attr('dealType'), $(
							this).attr('token'),taskParam.taskStep,'${param.menuId}');
	    });
     });
	//批量取消TG绑定
	$("#cancelTGBtn").bind('click', function() {
		 batchCancelFlag(kvParam,reloadParam,taskParam,null,null);
	});
	loan.getstorelsit("storeName","storeOrgId");
	var msg = "${message}";
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	 }
	   $('#backListBtn').bind("click",function(){
	    	var param = "";
	  		$("input[name='prepareCheckEle']:checked").each(function(){
	  			if(param!=""){
	  			    param+=";"+$(this).val();
	  			}else{
	  				param=$(this).val();	
	  			}
	  		});
	  		var fromSubmit = "";
	  		if(param == ""){
	  			fromSubmit = fetchTaskItemsSubmit('ctrCreatetForm','flagForm');
	  		}
		    	backListFlow('backList',$('#flowFlag').val(),REDIRECT_URL,$('#menuId1').val(),param,fromSubmit);  //传递退回窗口的视图名称
	      });
});
   function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		fetchTaskItems('ctrCreatetForm',
		'flagForm');
		return false;
}
   function timeCheck(obj){
		var outApproveTimeEnd = $("#lastDealTimeEnd").val();
		var outApproveTimeStart= $("#lastDealTimeStart").val();
		if(outApproveTimeEnd!='' && outApproveTimeStart!=''){
			if(Date.parse(new Date(outApproveTimeStart))>Date.parse(new Date(outApproveTimeEnd))){
				art.dialog.alert('开始时间不能小于结束时间！');
				$(obj).val('');
			}

		}
	}
   function timeCheck1(obj){
		var outApproveTimeEnd = $("#confirmSignDateEnd").val();
		var outApproveTimeStart= $("#confirmSignDateStart").val();
		if(outApproveTimeEnd!='' && outApproveTimeStart!=''){
			if(Date.parse(new Date(outApproveTimeStart))>Date.parse(new Date(outApproveTimeEnd))){
				art.dialog.alert('开始时间不能小于结束时间！');
				$(obj).val('');
			}

		}
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
    	    <input id="menuId1" name="menuId" value='${menuId}' type="hidden">
    	</form>
		<form id="ctrCreatetForm">
		    <input id="pageNo" name="pageNo" type="hidden"
				value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden"
				value="${page.pageSize}" />
			<table class=" table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label>
					    <input type="text" name="customerName" value="${ctrQryParam.customerName}" class="input_text178">
					</td>
					<td><label class="lab">门店：</label>
					  <input type="text" id="storeName" name="storeName" value="${ctrQryParam.storeName}" class="input_text178" readonly="readonly"/> 
					  <i id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
				    <input type="hidden" name="storeOrgId" value="${ctrQryParam.storeOrgId}" id="storeOrgId" />
					</td>
					 <td><label class="lab">身份证号：</label>
				   	 	 <input type="text" name="identityCode" value="${ctrQryParam.identityCode}" class="input_text178">
					 </td>
				</tr>
				<tr>
				<td><label class="lab">合同编号：</label> 
				   <input type="text" name="contractCode" value="${ctrQryParam.contractCode}" class="input_text178" /></td>
				
				<td><label class="lab">渠道：</label>
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
						   <option value="">全部</option>
							<c:forEach items="${fns:getDictList('yes_no')}" var="urgentFlag">
						    	<option value="${urgentFlag.value}"
									 <c:if test="${ctrQryParam.urgentFlag==urgentFlag.value}">
									    selected=true
									  </c:if>
									>${urgentFlag.label}
								</option>
		     				</c:forEach>
					 </select>
				  </td>
				</tr>
				<tr id="T1" style="display: none">
				  <td><label class="lab">产品：</label>
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
		      		 <td><label class="lab">是否追加借：</label>
					    <select name="additionalFlag" class="select180">
						   		<option value="">全部</option>
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
					<td colspan="1">
					    <label class="lab">是否电销：</label>
					      <select name="telesalesFlag" class="select180">
						   <option value="">全部</option>
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
   				<tr id="T2" style="display: none">
   				   <td>
					     <label class="lab">版本号：</label> 
					     <select name="contractVersion" class="select180">
					        <option value="">全部</option>
					        <c:forEach items="${fns:getDictList('jk_versions_search')}" var="item">
					              <option value="${item.value}"
									 <c:if test="${ctrQryParam.contractVersion==item.value}">
									    selected=true
									  </c:if>
									>
									${item.label}
								  </option>
					         </c:forEach>
					     </select>
					</td>
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
   				</tr>
   				<tr id="T3" style="display: none">
   					<td><label class="lab">门店提交时间：</label>
				               <input type="text" id="lastDealTimeStart" name="lastDealTimeStart" readonly="readonly" value="${ctrQryParam.lastDealTimeStart}" onchange="timeCheck(this)" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate input_text180"/>-
				               <input type="text" id="lastDealTimeEnd" name="lastDealTimeEnd" readonly="readonly" value="${ctrQryParam.lastDealTimeEnd}" onchange="timeCheck(this)" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate input_text180"/>
              			 </td>
              		<td><label class="lab">确认签署时间：</label>
				               <input type="text" id="confirmSignDateStart" name="confirmSignDateStart" readonly="readonly" value="${ctrQryParam.confirmSignDateStart}" onchange="timeCheck1(this)" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate input_text70"/>-
				               <input type="text" id="confirmSignDateEnd"  name="confirmSignDateEnd" readonly="readonly" value="${ctrQryParam.confirmSignDateEnd}" onchange="timeCheck1(this)" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate input_text70"/>
              			 </td>
              		<td></td>
   				</tr>
			</table>
			</form>
			<div class="tright pr30 pb5">
				<button class="btn btn-primary" onkeydown="KeyDown('searchBtn');" id="searchBtn">搜索</button>
				<button class="btn btn-primary" id="clearBtn">清除</button>
			
			<div style="float: left; margin-left: 45%; padding-top: 10px">
				<img src="../../../static/images/up.png" id="showMore"></img>
			</div>
		</div>
		</div>
		    <p>
		     <c:if test="${contract_create_backList==null }">
		       <button class="btn btn-small contract_create_backList" id="backListBtn">批量退回</button>
		       </c:if>
		       <span><label>当前版本号：</label>
						 ${curVersion}
				</span>
			</p>
		 <sys:columnCtrl pageToken="contractCreateList"></sys:columnCtrl>
		 <div class="box5">
           <table id="tableList" class="table  table-bordered table-condensed table-hover ">
                <thead>
                    <tr>
                        <th><input type="checkbox" id="checkAll"></input>序号</th>
						<th>合同编号</th>
						<th>合同版本</th>
                        <th>客户姓名</th>
                        <th>共借人</th>
                        <th>自然人保证人</th>
                        <th>省份</th>
                        <th>城市</th>
                        <th>门店</th>
                        <th>产品</th>
                        <th>状态</th>
                        <th>批复金额</th>
                        <th>批复期数</th>
                        <th>进件时间</th>
                        <th>门店提交时间</th>
                        <th>确认签署时间</th>
                        <th>汇诚审批时间</th>
                        <th>加急标识</th>
                        <th>模式</th>
                        <th>渠道</th>
                		<th>是否电销</th>
						<th>是否生僻字</th>
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
							  <c:when test="${item.backFlag=='1' || item.nodeFlag=='1'}">
							    <tr class="red">
									<td><input type="checkbox" name="prepareCheckEle"  backFlag="1" paperLessFlag='${item.paperLessFlag}' flag='${item.channelName}' 
								     model="${item.model}" replyProductName='${item.replyProductName}'
									value='${item.applyId},${item.wobNum},${item.flowName},${item.token},${item.stepName},${item.loanCode}' />${index}</td>
								    <td>${item.contractCode}</td>
								    <td>
								     <c:forEach var="dict" items="${dictlist }">
								            <c:if test="${item.contractVersion==dict.value}">
								               ${dict.label }
								            </c:if>
								         </c:forEach>
								    </td>
									<td>${item.customerName}</td>
									<td>
								 		<c:if test="${item.loanInfoOldOrNewFlag eq '' || item.loanInfoOldOrNewFlag eq '0'}">
								 			<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                                				${item.coborrowerName}
                                 			</c:if> 
                                   		</c:if>
								   	</td>
								   	<td>
								 		<c:if test="${item.loanInfoOldOrNewFlag eq '1'}">  
								    		<c:choose>
									 			<c:when test="${item.bestCoborrowerFlag eq '0'}">
									 				<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
			                                			 ${item.coborrowerName}
			                                 		</c:if> 
									 			</c:when>
									 			<c:otherwise>
									 				<c:if test="${item.bestCoborrowerName!=null && fn:contains(item.bestCoborrowerName,'null')==false}" >
			                                    		${item.bestCoborrowerName}
			                                   		</c:if>
									 			</c:otherwise>
									 		</c:choose>
                                   		</c:if> 
								 	</td>
									<td>${item.provinceName}</td>
									<td>${item.cityName}</td>
									<td>${item.storeName}</td>
									<td>${item.replyProductName}</td>
									<td>${item.loanStatusName}</td>
									<td><fmt:formatNumber value='${item.replyMoney}' pattern="#,#00.00"/></td>
									<td>${item.replyMonth}</td>
									<td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
									<td>${item.lastDealTime}</td>
									<td>${item.confirmSignDate}</td>
									<td>${item.outApproveTime}</td>
									<td>
								  		<c:if test="${item.urgentFlag=='1'}">
                                      		<span style="color:red">加急</span>
                                  		</c:if>
                               		 </td>
                               		 <td>${item.modelLabel}</td>
									 <td>${item.channelName}</td>
									 <td>
									    <c:if test="${item.telesalesFlag=='1'}">
                                          <span>是</span>
                                        </c:if>
                                     </td>
                                     <td>
                                      <c:if test="${item.bankIsRareword=='1'}">
                                          <span>是</span>
                                      </c:if>
                                     </td>
                                     <td>${item.riskLevel}</td>
								     <td><input  type="button" class="btn_edit"
										applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
										token='${item.token}' name="dealctrCreate" value="办理" />
										<input type="button" class="btn_edit" loanCode='${item.loanCode}' 
										name="history" value="历史" /> 
									 </td>
								</tr>
							  </c:when>
							  <c:otherwise>
							    <tr>
									<td><input type="checkbox" name="prepareCheckEle"  backFlag="1" paperLessFlag='${item.paperLessFlag}' flag='${item.channelName}' 
								     model="${item.model}" replyProductName='${item.replyProductName}'
									value='${item.applyId},${item.wobNum},${item.flowName},${item.token},${item.stepName},${item.loanCode}' />${index}</td>
							    	<td>${item.contractCode}</td>
									<td>
									 <c:forEach var="dict" items="${dictlist }">
								            <c:if test="${item.contractVersion==dict.value}">
								               ${dict.label }
								            </c:if>
								         </c:forEach>
								    </td>
									<td>${item.customerName}</td>
									<td>
								 		<c:if test="${item.loanInfoOldOrNewFlag eq '' || item.loanInfoOldOrNewFlag eq '0'}">
								 			<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
                                				${item.coborrowerName}
                                 			</c:if> 
                                   		</c:if>
								   	</td>
								   	<td>
								 		<c:if test="${item.loanInfoOldOrNewFlag eq '1'}">  
								    		<c:choose>
									 			<c:when test="${item.bestCoborrowerFlag eq '0'}">
									 				<c:if test="${item.coborrowerName!=null && fn:contains(item.coborrowerName,'null')==false}" >
			                                			 ${item.coborrowerName}
			                                 		</c:if> 
									 			</c:when>
									 			<c:otherwise>
									 				<c:if test="${item.bestCoborrowerName!=null && fn:contains(item.bestCoborrowerName,'null')==false}" >
			                                    		${item.bestCoborrowerName}
			                                   		</c:if>
									 			</c:otherwise>
									 		</c:choose>
                                   		</c:if> 
								 	</td>
									<td>${item.provinceName}</td>
									<td>${item.cityName}</td>
									<td>${item.storeName}</td>
									<td>${item.replyProductName}</td>
									<td>${item.loanStatusName}</td>
									<td><fmt:formatNumber value='${item.replyMoney}' pattern="#,#00.00"/></td>
									<td>${item.replyMonth}</td>
									<td><fmt:formatDate value="${item.intoLoanTime}" pattern="yyyy-MM-dd"/></td>
									<td>${item.lastDealTime}</td>
									<td>${item.confirmSignDate}</td>
									<td>${item.outApproveTime}</td>
									<td>
								  		<c:if test="${item.urgentFlag=='1'}">
                                      		<span style="color:red">加急</span>
                                  		</c:if>
                               		 </td>
                               		 <td>${item.modelLabel}</td>
									 <td>${item.channelName}</td>
									 <td>
									    <c:if test="${item.telesalesFlag=='1'}">
                                          <span>是</span>
                                        </c:if>
                                     </td>
                                     <td>
                                      <c:if test="${item.bankIsRareword=='1'}">
                                          <span>是</span>
                                      </c:if>
                                     </td>
                                     <td>${item.riskLevel}</td>
								     <td><input  type="button" class="btn_edit"
										applyId='${item.applyId}' wobNum='${item.wobNum}' dealType='0'
										token='${item.token}' name="dealctrCreate" value="办理" />
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
            <c:if test="${ workItems!=null || fn:length(workItems)>0}">
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
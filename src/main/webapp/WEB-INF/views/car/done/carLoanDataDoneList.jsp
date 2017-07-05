<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车借数据列表</title>
<meta name="decorator" content="default" />
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/enter_select.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 page(1,${page.pageSize});
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input','#searchTable')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		 $("#hdStore").val('');
		 $("select").trigger("change");
	 });
	 $('#showMore').bind('click',function(){
		show();
	 });
	 //选择门店
	 loan.getstorelsit("txtStore","hdStore");
	 

	//申请电子协议 验证
	$("#applyAgreementMessage").validate({
		rules : {
			"urgentDegree" : {
				required : true
			},
			"applyReason" : {
				required : true
			}
		},
		messages : {
			"urgentDegree" : {
				required : "<font color='red'>请选择紧急程度</font>"
			},
			"applyReason" : {
				required : "<font color='red'>请输入申请理由</font>"
			}
		}
	});
		
		
 });
  function page(n,s){
      if(n) $("#pageNo").val(n);
      if(s) $("#pageSize").val(s);
      $("#inputForm").attr("action", "${ctx}/common/carHistory/carLoanDataDoneList");
      $("#inputForm").submit();
      return false;
  }
  
  function applyAgreement(contractCode){
		var url = "${ctx}/common/carHistory/showCutomerMsg";
		$.ajax({
			type : "POST",
			url : url,
			data:{"contractCode":contractCode},
			success : function(data){
				$("#actId").val(data.id);
    			$("#applyLoanCustomerName").val(data.loanCustomerName);
    			$("#applyContractCode").val(data.contractCode);
    			$("#applyCustomerCertNum").val(data.customerCertNum);
    			$("#applyCustomerEmail").val(data.customerEmail);
    			art.dialog({
    			    content: document.getElementById("applyAgreement"),
    			    title:'申请电子协议',
    			    fixed: true,
    			    lock:true,
    			    width:450,
    			    height:200,
    			    id: 'confirm',
    			    okVal: '确认',
    			    ok: function () {
    			    	var srcFormParam = $('#applyAgreementMessage');
    					srcFormParam.submit();
    					return false;
    			    },
    			    cancel: true
    			});
			}
		});
  }
</script>
</head>
<body>
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="carLoanStatusHisEx"
			method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input name="menuId" type="hidden" value="${param.menuId}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table id = "searchTable" class="table1 " cellpadding="0" cellspacing="0" border="0"
				width="100%">
				<tr>
					<td><label class="lab">客户姓名：</label> <form:input
							id="customerName" path="loanCustomerName" class="input_text178" />
					</td>
					<td><label class="lab">产品类型：</label> <form:select
							id="productType" path="productType" class="select180">
							<option value="">全部</option>
							<c:forEach items="${fncjk:getPrd('products_type_car_credit')}"
								var="product_type">
								<form:option value="${product_type.productCode}">${product_type.productName}</form:option>
							</c:forEach>
						</form:select></td>
					<td><label class="lab">身份证号：</label> <form:input
							path="certNum" class="input_text178" /></td>
				</tr>
				<tr>
					<td><label class="lab">团队经理：</label> <form:input
							path="teamManagerName" class="input_text178" /></td>
					<td><label class="lab">客户经理：</label> <form:input
							path="costumerManagerName" class="input_text178" /></td>
					<td><label class="lab">门店：</label> <form:input id="txtStore"
						path="storeName" maxlength="20"
						class="txt date input_text178" value="${secret.store }" readonly="true"/> <i
						id="selectStoresBtn" class="icon-search" style="cursor: pointer;"></i>
				</tr>
				<tr id="T1" style="display: none">
					<td><label class="lab">借款状态：</label> <form:select
							id="loanMonths" path="loanStatusCode" class="select180">
							<option value="">全部</option>
							<form:options items="${fns:getDictList('jk_car_loan_status')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select></td>
					<td><label class="lab">是否电销：</label> <form:radiobuttons
							path="telesalesFlag"
							items="${fns:getDictList('jk_telemarketing')}" itemLabel="label"
							itemValue="value" htmlEscape="false" /></td>
				      <td><label class="lab">渠道：</label>
		             	<form:select id="loanFlag" path="loanFlag" class="select180">
		                   <option value="">请选择</option>
			                    <c:forEach items="${fns:getDictList('jk_car_throuth_flag')}" var="loan_Flag">
			                   		<form:option value="${loan_Flag.value}">${loan_Flag.label}</form:option>
			              </c:forEach>
				         </form:select>
		             </td>
				</tr>
				<tr id="T2" style="display: none">
					<td>
		              <label class="lab">系统来源：</label>
	                   <form:select id="dictSourceType" path="dictSourceType" class="select180">
							<option value="">请选择</option>
			                <option value="2">2.0</option> 
			                <option value="3">3.0</option>
						</form:select>
		            </td>
		            <td><label class="lab">回访状态：</label> 
		            	<form:select id="visitState" path="visitState" class="select180">
		                    <option value="">请选择</option>
		                    <c:forEach items="${fns:getDictList('jk_car_revisit_type')}" var="visit">
		                   		<form:option value="${visit.value}">${visit.label}</form:option>
		              		</c:forEach>
				         </form:select>
					</td>
		         </tr>
			</table>
			<div class="tright pr30 pb5">
				<form:input type="hidden" id="hdStore" path="storeCode"/></td>
				<input type="button" class="btn btn-primary" id="searchBtn"
					value="搜索"></input> <input type="button" class="btn btn-primary"
					id="clearBtn" value="清除"></input>
				<div class="xiala" style="text-align: center;">
					<img src="${context}/static/images/up.png" id="showMore"></img>
				</div>
			</div>
	</div>
	</form:form>
	<sys:columnCtrl pageToken="carLoanDataDoneList"></sys:columnCtrl>
	<div class="box5" style="position:relative;width:100%;overflow:hidden;height:60%;margin-bottom: 20px">
	 
		<table id="tableList" class="table table-bordered table-condensed table-hover"
			style="margin-bottom: 200px;">
			<thead>
				<tr>
					<th>序号</th>
					<th>合同编号</th>
					<th>客户姓名</th>
					<th>共借人</th>
					<th>申请金额（元）</th>
					<th>借款期限(天)</th>
					<th>产品类型</th>
					<th>评估金额</th>
					<th>批借金额</th>
					<th>申请日期</th>
					<th>所属省份</th>
					<th>门店名称</th>
					<th>团队经理</th>
					<th>客户经理</th>
					<th>面审人员</th>
					<th>借款状态</th>
					<th>合同到期提醒</th>
					<th>车牌号码</th>
					<th>是否电销</th>
					<th>来源系统</th>
					<th>渠道</th>
					<th>回访状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<c:if test="${ page.list != null && fn:length(page.list)>0}">
				<c:forEach items="${page.list}" var="item" varStatus="status">
					<tr>
						<td>${status.count}</td>
						<td>${item.contractCode}</td>
						<td>${item.loanCustomerName}</td>
						<td>${item.coborrowerName}</td>
						<td><fmt:formatNumber value="${item.loanApplyAmount}" pattern="#,##0.00"/></td>
						<c:choose>
							<c:when test="${item.finalAuditMonths != null }">
								<td>${item.finalAuditMonths}</td>
							</c:when>
							<c:when test="${item.secondAuditMonths != null }">
								<td>${item.secondAuditMonths}</td>
							</c:when>
							<c:otherwise>
								<td>${item.loanMonths}</td>
							</c:otherwise>
						</c:choose>
						<c:choose>
							<c:when test="${item.finalProductType != null }">
								<td>${item.finalProductType}</td>
							</c:when>
							<c:when test="${item.secondProductType != null }">
								<td>${item.secondProductType}</td>
							</c:when>
							<c:otherwise>
								<td>${item.firstProductType}</td>
							</c:otherwise>
						</c:choose>
						<td><fmt:formatNumber value="${item.storeAssessAmount}" pattern="#,##0.00"/></td>
						<c:choose>
							<c:when test="${item.finalAuditAmount != null }">
								<td>${item.finalAuditAmount}</td>
							</c:when>
							<c:when test="${item.secondAuditAmount != null }">
								<td>${item.secondAuditAmount}</td>
							</c:when>
							<c:otherwise>
								<td>${item.firstAuditAmount}</td>
							</c:otherwise>
						</c:choose>
						<td><fmt:formatDate value="${item.applyTime}"
								pattern="yyyy-MM-dd" /></td>
						<td>${item.provinceName}</td>
						<td>${item.storeName}</td>
						<td>${item.teamManagerName}</td>
						<td>${item.costumerManagerName}</td>
						<td>${item.reviewMeetName}</td>
						<td>${item.loanStatusCode}</td>
						<td><fmt:formatDate value="${item.rateCheckDate}"
								pattern="yyyy-MM-dd" /></td>
						<td>${item.plateNumbers}</td>
						<td>${item.telesalesFlag}</td>
						<td>${item.sourceType}</td>
						<td>${item.loanFlag}</td>
						<td>${item.visitState }</td>
						<td class="tcenter">
							<c:if test="${item.paperLessFlag eq '1' and item.agrDictLoanStatus eq '33' and (item.agreementType eq null or item.agreementType eq '0')}">
								<input class="btn_edit jkcj_lendCarApply_apply" type="button" onclick="applyAgreement('${item.contractCode}')" value="申请电子协议"  />
							</c:if>
							<c:choose>
								<c:when test="${item.operateStep == 4}">
						 			<input class="btn_edit jkcj_lendCarApply_check" type="button" onclick="carConsultDone('${item.loanCode}')" value="查看" />
								</c:when>
								<c:when test="${item.operateStep == 5}">
									<input class="btn_edit jkcj_lendCarApply_check" type="button" onclick="appraiserEntryDone('${item.loanCode}')"  value="查看" />
								</c:when>
								<c:otherwise>
						 			<input class="btn_edit jkcj_lendCarApply_check" type="button" onclick="showCarLoanInfo('${item.loanCode}')" value="查看"  />
								</c:otherwise>
							</c:choose>
							 <input class="btn_edit jkcj_lendCarApply_history" type="button"  onclick="showCarLoanHis('${item.loanCode}')" value="历史"  />
					 	</td>
					</tr>
				</c:forEach>
			</c:if>
			<c:if test="${ page.list==null || fn:length(page.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有已办数据</td>
				</tr>
			</c:if>
		</table>
	</div>
	<div class="pagination">${page}</div>
	
	
	<!-- 申请电子协议 -->
	<div id="applyAgreement" class=" pb5 pt10 pr30 title ohide" style="display:none">
		<form id="applyAgreementMessage" class="validate" action="${ctx}/common/carHistory/updateContractArgType" method="post" enctype="multipart/form-data">
			<input type="hidden" id="actId" name="id"/>
			<input type="hidden" name="dictOperStatus" value="1"/>
			<table class="table table-bordered table-condensed table-hover ">
				<tbody>
					<tr>
						<td><label class="lab">客户姓名：</label></td>
						<td><input type="text" class="input-medium" id="applyLoanCustomerName" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">合同编号：</label></td>
						<td><input type="text" class="input-medium" id="applyContractCode" name="contractCode" style="width: 270px;" readonly="readonly"/></td>
					</tr>
					<tr>
						<td><label class="lab">身份证号码：</label></td>
						<td>
							<input type="text" class="input-medium isEmailModify" id="applyCustomerCertNum" readonly="readonly"/></td>
						</td>
					</tr>
					<tr>
						<td><label class="lab">邮箱地址：</label></td>
						<td>
							<input type="text" class="input-medium isEmailModify" id="applyCustomerEmail" readonly="readonly"/></td>
						</td>
					</tr>
					<tr>
						<td><label class="lab">紧急程度：</label></td>
						<td>
							<select id="urgentDegree" name="urgentDegree" style="width:100px">
								<option value="">请选择</option>
								<option value="1">紧急</option>
								<option value="2">正常</option>
							</select>
						</td>
					</tr>
					<tr>
						<td><label class="lab">申请理由：</label></td>
						<td>
							<textarea rows="20" cols="10"  id="applyReason" name="applyReason" style="margin: 3px 0px; width: 305px; height: 68px;"></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</form>
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
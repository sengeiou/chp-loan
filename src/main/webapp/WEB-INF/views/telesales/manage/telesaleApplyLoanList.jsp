<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/telesales/telesalesApplyLoan.js?version=1"></script>
<script type="text/javascript">
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		    $("#inputForm").attr("action", "${ctx}/telesales/customerManagement/findTelesaleApplyLoanList?menuId=${param.menuId}");
		    $("#inputForm").submit();
		    return false;
	}
  
  
  function openConsult(id){
	  //查看
		  window.location.href="${ctx}/borrow/transate/loanMinute?loanCode="+id;
		                         
  }
  var dayList = "${dayList}";
  var current = new Date();
</script>
<title>电销借款申请管理</title>
<meta name="decorator" content="default" />
</head>
<body>
    <div class="control-group">
    <form:form id="inputForm" modelAttribute="consultView" action="${ctx}/telesales/customerManagement/findTelesaleApplyLoanList?menuId=${param.menuId}" method="post"  class="form-horizontal">
       	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input path="customerName" class="input_text178"/></td>
                <td><label class="lab">身份证号：</label><form:input path="mateCertNum" class="input_text178"/></td>
                <td><label class="lab">产品：</label><form:select id="productCode" path="productCode" class="select180">
						  <option value="">全部</option>
						   <c:forEach items="${products}" var="item">
						      <option value="${item.productCode}" <c:if test="${item.productCode eq consultView.productCode}">selected</c:if>>${item.productName}</option>
						  </c:forEach>
						</form:select>
				</td>
            </tr>
            <tr>
                <td><label class="lab">标示：</label><form:select id="loanFlagCode" path="loanFlagCode" class="select180">
                        <option value="">全部</option>
					    <form:options items="${fns:getDictList('jk_channel_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				     </form:select>
                </td>
                <td><label class="lab">借款状态：</label><form:select id="dictLoanStatusCode" path="dictLoanStatusCode" class="select180">
                        <option value="">全部</option>
					    <form:options items="${fns:getDictList('jk_loan_apply_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				     </form:select>
                </td>
                <td><label class="lab">门店：</label><form:input id="storeName" path="storeName" class="input_text178" readonly="true" value="${secret.store}"/>	                  
					 <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
					<form:hidden path="storeCode"  id="storeCode"/>
                </td>
            </tr>
            <tr>
                <td><label class="lab">是否循环借：</label><form:select id="dictIsCycleCode" path="dictIsCycleCode" class="select180">
                        <option value="">全部</option>
					    <form:options items="${fns:getDictList('jk_circle_loan_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				     </form:select>
                </td>
                <td><label class="lab">追加借：</label><form:select id="dictIsAdditional" path="dictIsAdditional" class="select180">
                        <option value="">全部</option>
					    <form:options items="${fns:getDictList('jk_add_flag')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				     </form:select>
                </td>
                <td><label class="lab">录单人员：</label><form:input path="creater" class="input_text178"/></td>
            </tr>
            <tr id="T1" style="display:none">
            	<td><label class="lab">电销来源：</label><form:select id="consTelesalesSource" path="consTelesalesSource" class="select180">
                    	<option value="">全部</option>
						<form:options items="${fns:getDictList('jk_rs_src')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				   		</form:select>
				</td>
				<td><label class="lab">电销专员：</label><form:input path="telesaleManName"  class="input_text178"/></td>	
				<td><label class="lab">电销团队主管：</label><form:input path="telesaleTeamLeaderName"  class="input_text178"/></td>              	
            </tr>
            <tr id="T2" style="display:none">
               <td><label class="lab">电销现场经理：</label><form:input id="telesaleSiteManagerName" path="telesaleSiteManagerName" class="input_text178"/>
               <td colspan="2"><label class="lab">进件时间：</label><input id="firstConsultDateStart"
						name="firstConsultDateStart" type="text"
						class="input_text70 Wdate" size="10"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'firstConsultDateStart\')}'})"
						style="cursor: pointer" readonly />-<input id="firstConsultDateEnd"
						name="firstConsultDateEnd" type="text" class="input_text70 Wdate"
						size="10"
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'firstConsultDateEnd\')}'})"
						style="cursor: pointer" readonly /> 
                </td>
                <td></td>
            </tr>
        </table>
         </form:form> 
        <div  class="tright pr30 pb5">
                 <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                 <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
      
		       	<div class="xiala" style="text-align:center;">
						  <img src="${context}/static/images/up.png" id="showMore"></img>
		       	</div>
        </div>
    </div>
  
   <p class="mb5">
   		<c:if test="${isSaleDateCommissioner}">
			 <button class="btn btn-small jkhj_dxgl_dxjksqgl_dcexcel" id="excelBtn">导出Excel</button>
		     <button class="btn btn-small jkhj_dxgl_dxjksqgl_jqzy" id="jqSourceBtn">结清资源</button>
			 <button class="btn btn-small jkhj_dxgl_dxjksqgl_bqyzy" id="bqSourceBtn">不签约资源</button>
			 <button class="btn btn-small jkhj_dxgl_dxjksqgl_jszdzy" id="zdSourceBtn">结算再贷资源</button>
			 <button class="btn btn-small jkhj_dxgl_dxjksqgl_spjjzy" id="spSourceBtn">审批拒绝资源</button>
   		</c:if>
	</p>
	<sys:columnCtrl pageToken="telesales"></sys:columnCtrl>
   <div class="box5"> 
      <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
      <thead>
         <tr>
                <th><input type="checkbox" id="checkAll"/></th>
                <th>序号</th>
                <th>借款编号</th>
                <th>合同编号</th>
                <th>客户姓名</th>
                <th>共借人</th>
                <th>自然人保证人</th>
                <th>省份</th>
                <th>城市</th>
                <th>门店</th>
                <th>状态</th>
                <th>产品</th>
                <th>是否加急</th>
                <th>申请金额</th>
                <th>批复金额</th>
                <th>放款金额</th>
                <th>产品期限</th>
                <th>电销来源</th>
                <th>电销专员</th>
                <th>电销专员编号</th>
                <th>电销团队主管</th>
                <th>电销现场经理</th>
                <th>录单人员</th>
                <th>外访人员</th>
				<th>进件时间</th>
			    <th>渠道</th>
				<th>是否循环借</th>
				<th>操作</th>	
         </tr>
        </thead>
        <tbody>
          <c:if test="${page.list!=null && fn:length(page.list)>0}">
        	<c:forEach items="${page.list}" var="item" varStatus="st">       	 
				<tr>
					<td><input type="checkbox" name="checkBoxItem" value="${item.loanCode}"/></td>
					<td><c:out value="${st.count}" /></td>
				    <td>${item.loanCode}</td>
				    <td>${item.contractCode}</td>
				    <td>${item.customerName}</td>
				    <td>
				    	<c:if test="${item.loanInfoOldOrNewFlag eq '0'}">	
							${item.coboName}
						</c:if>
				    </td>
				    <td>
				    	<c:if test="${item.loanInfoOldOrNewFlag eq '1'}">	
							${item.bestCoborrower}
						</c:if>
				    </td>
				    <td>${item.storeProviceName}</td>
				    <td>${item.storeCityName}</td>				   
				    <td>${item.storeName}</td>	
				    <td>${item.dictLoanStatusName}</td>
				    <td>${item.productName}</td>
				    <td>${item.loanUrgentFlag}</td>
				    <td><fmt:formatNumber value='${item.loanApplyAmount}' pattern="#,#00.00"/></td>
				    <td><fmt:formatNumber value='${item.loanAuditAmount}' pattern="#,#00.00"/></td>
				    <td><fmt:formatNumber value='${item.grantAmount}' pattern="#,#00.00"/></td>				   
				    <td>${item.loanMoths}</td>
				    <td>${item.consTelesalesSourceName}</td>
				    <td>${item.telesaleManName}</td>
				    <td>${item.telesaleManCode}</td>
				    <td>${item.telesaleTeamLeaderName}</td>
				    <td>${item.telesaleSiteManagerName}</td>
				    <td>${item.creater}</td>
				    <td>${item.loanSurveyEmpId}</td>
				    <td><fmt:formatDate value="${item.customerIntoTime}" pattern="yyyy/MM/dd" /></td>			
				    <td>${item.loanFlagName}</td>
				    <td>${item.dictIsCycleName}</td>				   
				    <td><input type="button" class="btn_edit" value="查看" onclick="openConsult('${item.loanCode}')"></td>
			</c:forEach>
		   </c:if>
		   <c:if test="${page.list==null || fn:length(page.list)==0}">
             <tr><td colspan="27" style="text-align:center;">暂无数据!</td></tr>
           </c:if>
		</tbody>
      </table>
     </div>
  </div>
 <div class="pagination">${page}</div>
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
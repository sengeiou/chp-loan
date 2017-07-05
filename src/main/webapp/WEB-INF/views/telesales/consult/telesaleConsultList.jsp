<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${context}/js/telesales/telesalesApplyLoan.js"></script>
<script type="text/javascript">
  $(document).ready(function(){
	 $('#searchBtn').bind('click',function(){
		 var startDate=$('#firstConsultDateStart').val();
		 var endDate=$('#firstConsultDateEnd').val();
		 if(getDate(startDate)-getDate(endDate)>0){
			 art.dialog.alert("开始日期不能大于结束日期!");
			 return false;
		 }
		$('#inputForm').submit(); 
	 });
	 $('#clearBtn').bind('click',function(){
		 $(':input', '#inputForm').not(':button, :submit, :reset')
			.val('').removeAttr('checked').removeAttr('selected');
			$('#inputForm')[0].submit();
	 });
 });
  function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/telesales/customerManagement/findTelesaleCustomerList?menuId=${param.menuId}");
		$("#inputForm").submit();
		return false;
	}
  
  function updateConsult(id,customerCode,status){
	  //门店退回或继续跟踪的话则可以进行修改操作，否则只能查看
	  if(status =="8" || status =="0" ){
		  window.location.href="${ctx}/telesales/customerManagement/updateTelesalesConsultInfo?id="+id+"&customerCode="+customerCode+"&dictOperStatus="+status;
	  }else{
		  window.location.href="${ctx}/telesales/customerManagement/viewTelesalesConsultInfo?id="+id+"&customerCode="+customerCode;
	  }
  }
  
//日期转换
  function getDate(date){
     var dates = date.split("-");
      var dateReturn = '';
      for(var i=0; i<dates.length; i++){
         dateReturn+=dates[i];
       }
     return dateReturn;
  }
  
  function doOpenhis(num) {
		var url = ctx + '/consult/customerManagement/TMfindHistory?consultID='
				+ num;  
	     art.dialog.open(url, {  
	         id: 'his',
	         title: '历史沟通记录',
	         lock:true,   
	         width:700,
	     	 height:350
	     },  
	     false);  
	}
  
  </script>
</script>
<title>信借电销客户咨询列表</title>
<meta name="decorator" content="default" />
</head>
<body>
    <div class="control-group">
    <form:form id="inputForm" modelAttribute="consultView" action="${ctx}/telesales/customerManagement/findTelesaleCustomerList?menuId=${param.menuId}" method="post" class="form-horizontal">
       	<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
        <table class="table1 " cellpadding="0" cellspacing="0" border="0"width="100%">
            <tr>
                <td><label class="lab">客户姓名：</label><form:input id="customerName" path="customerName" class="input_text178"/></td>
                <td><label class="lab">身份证号：</label><form:input path="mateCertNum" class="input_text178"/></td>
                <td><label class="lab">咨询状态：</label><form:select
							id="dictOperStatus" path="dictOperStatus"
							class="select180">
							<option value="">全部</option>
							<c:forEach items="${fns:getDictList('jk_rs_status')}" var="item">
								<c:if test="${item.value!='5' }">
									<option value="${item.value}" <c:if test='${consultView.dictOperStatus eq item.value}'>selected</c:if>>${item.label}</option>
								</c:if>
							</c:forEach>
					</form:select>               
                </td>
            </tr>
            <tr>
                <td><label class="lab">门店：</label><form:input id="storeName" path="storeName" class="input_text178" readonly="true" value="${secret.store}"/>	                  
					 <i id="selectStoresBtn"
						class="icon-search" style="cursor: pointer;"></i>
					<form:hidden path="storeCode"  id="storeCode"/>
                </td>
                <td><label class="lab">电销专员：</label><form:input path="telesaleManName"  class="input_text178"/></td>
                <td><label class="lab">电销团队主管：</label><form:input path="telesaleTeamLeaderName"  class="input_text178"/></td>              
            </tr>
			<tr id="T1" style="display:none">
				<td><label class="lab">电销来源：</label><form:select id="consTelesalesSource" path="consTelesalesSource" class="select180">
                    <option value="">全部</option>
					<form:options items="${fns:getDictList('jk_rs_src')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				    </form:select>
				</td>				
				<td><label class="lab">电销现场经理：</label><form:input id="telesaleSiteManagerName" path="telesaleSiteManagerName" class="input_text178"/>
				</td>
				<td></td> 
			 </tr>
			 <tr id="T2" style="display:none">
                <td colspan="2"><label class="lab">首次咨询时间：</label><input id="firstConsultDateStart"
						name="firstConsultDateStart" type="text"
						class="input_text70 Wdate" size="10" value="<fmt:formatDate value='${consultView.firstConsultDateStart }' pattern='yyyy-MM-dd' />"
						onClick="WdatePicker({skin:'whyGreen',maxDate:'#F{$dp.$D(\'firstConsultDateStart\')}'})"
						style="cursor: pointer" readonly />-<input id="firstConsultDateEnd"
						name="firstConsultDateEnd" type="text" class="input_text70 Wdate"
						size="10" value="<fmt:formatDate value='${consultView.firstConsultDateEnd }' pattern='yyyy-MM-dd' />"
						onClick="WdatePicker({skin:'whyGreen',minDate:'#F{$dp.$D(\'firstConsultDateEnd\')}'})"
						style="cursor: pointer" readonly /> 
                </td>
                <td></td>
            </tr>
        </table>
        <div  class="tright pr30 pb5">
                 <input type="button" class="btn btn-primary" id="searchBtn" value="搜索"></input>
                 <input type="button" class="btn btn-primary" id="clearBtn" value="清除"></input>
        <div class="xiala" style="text-align:center;">
				  <img src="${context}/static/images/up.png" id="showMore"></img>
        </div>
    </div>
    </div>
   </form:form> 
   <sys:message content="${message}" />
    <p class="mb5">
    	<c:if test="${isSaleDateCommissioner}">
	 		<button class="btn btn-small jkhj_dxgl_xjdxkhzxlb_dcexcel" id="telesaleConsultListexcelBtn">导出Excel</button>
    	</c:if>
	</p>
   <div class="box5"> 
      <table id="tableList" class="table  table-bordered table-condensed table-hover " style="margin-bottom:100px;">
      <thead>
         <tr>
               <th>序号</th>
               <th>电销来源</th>
               <th>客户编号</th>
               <th>客户姓名</th>
               <!-- <th>手机号</th> -->
               <th>身份证号码</th>
               <th>门店名称</th>
               <th>首次咨询时间</th>
               <th>咨询时间</th>
               <th>沟通记录</th>
               <th>客服人员</th>
               <th>电销专员</th>
               <th>电销编号</th>
               <th>电销团队主管</th>
               <th>电销现场经理</th>
               <th>咨询状态</th>
               <th>是否首次咨询</th>
               <th>操作</th>
         </tr>
         </thead>
       <tbody>
           <c:if test="${page.list!=null && fn:length(page.list)>0}">
			<c:forEach items="${page.list}" var="item" varStatus="st">
				<tr>
					<td><c:out value="${st.count }" /></td>
					<td>${item.consTelesalesSourceName}</td>
				    <td>${item.customerCode}</td>
				    <td>${item.customerName}</td>
				    <%-- <td>${item.customerMobilePhone}</td> --%>
				    <td>${item.mateCertNum}</td>
				    <td>${item.storeName}</td>
				    <td><fmt:formatDate value="${item.createTime}"
								pattern="yyyy/MM/dd" /></td>
					<td><fmt:formatDate value="${item.lastTimeConsCommunicateDate}" pattern="yyyy/MM/dd" /></td>
				    <td>${item.consLoanRecord}</td>
				    <td>${item.consServiceUserName}</td>
				    <td>${item.telesaleManName}</td>
				    <td>${item.telesaleManCode}</td>
				    <td>${item.telesaleTeamLeaderName}</td>
				    <td>${item.telesaleSiteManagerName}</td>
				    <td>${item.dictOperStatusName}</td>
				    <td>${item.isFirstConsult}</td>
				    <td class="tcenter">				    
				       <c:choose>   
						   <c:when test="${item.dictOperStatus== '0' || item.dictOperStatus=='8'}">  
						        <button class="btn_edit" onclick="updateConsult('${item.id}','${item.customerCode}','${item.dictOperStatus}')">修改</button>
						   </c:when>
						   <c:otherwise> 
						        <button class="btn_edit" onclick="updateConsult('${item.id}','${item.customerCode}','${item.dictOperStatus}')">查看</button>
						   </c:otherwise> 
					  </c:choose>         
						    <input type="button" class="btn_edit" onclick="doOpenhis('${item.id}')" value="历史" />            
				    </td>
				</tr>
			 </c:forEach>
			</c:if>
			<c:if test="${page.list==null || fn:length(page.list)==0}">
             <tr><td colspan="17" style="text-align:center;">暂无数据!</td></tr>
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
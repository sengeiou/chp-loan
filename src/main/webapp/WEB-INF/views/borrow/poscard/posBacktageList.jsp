<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script src="${context}/js/poscard/posBacktage.js" type="text/javascript"></script>
<script type="text/javascript" src="${context}/js/payback/ajaxfileupload.js"></script>


<%-- <script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script> --%>
<script
	src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet"
	href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />

<meta name="decorator" content="default" />
<script language="javascript">
	function page(n, s) {
		if (n)
			$("#pageNo").val(n);
		if (s)
			$("#pageSize").val(s);
		$("#backForm").attr("action",
				"${ctx}/borrow/poscard/posBacktageList/posBacktageInfo");
		$("#backForm").submit();
		return false;
	}

		function backReason() {
	 $('#diabox01').modal('toggle').css({
	 width : '90%',
	 'margin-left' : function() {
	 return -($(this).width() / 2);
	 }
	 });
	 } 
</script>
</head>
<body>
	<div id="messageBox" class="alert alert-success ${empty message ? 'hide' : ''}">
		<button data-dismiss="alert" class="close">×</button>
		<label id="loginSuccess" class="success">${message}</label>
	</div>
	<div class="control-group">
		   <form:form  action="${ctx }/borrow/poscard/posBacktageList/posBacktageInfo" method="post" id="backForm" modelAttribute="PosBacktage">
   			<input id="pageNo" name="pageNo" type="hidden" value="${posBackListpage.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${posBackListpage.pageSize}" />	
			<input id="ids" name="id" type="hidden"></input>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
          <tr>
		       <td><label class="lab">查账日期：</label><input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${posBacktage.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>-<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${posBacktage.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               </td>
               <td><label class="lab">匹配状态：</label><select class="select180" name="matchingState">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_matching')}" var="matchingState">
                                   <option value="${matchingState.value }" <c:if test="${posBacktage.matchingState == matchingState.value }">selected</c:if>>${matchingState.label }</option>
                              </c:forEach>
                	</select>
               </td>
			   <td><label class="lab">参考号：</label><input value="${posBacktage.referCode}" type="text" name="referCode" class="input_text178"></input></td>
          </tr> 
          <tr>
		       <td><label class="lab">到账日期：</label><input id="paybackBeginDate" name="paybackBeginDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${posBacktage.paybackBeginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>-<input id="paybackEndDate" name="paybackEndDate" type="text" readonly="readonly" maxlength="20" class="input_text70 Wdate"
								value="<fmt:formatDate value="${posBacktage.paybackEndDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'});"/>
               </td>
			   <td><label class="lab">订单号：</label><input value="${posBacktage.posOrderNumber}" type="text" name="posOrderNumber" class="input_text178"></input></td>
			   <td><label class="lab"> 金额 ：</label><input value="${posBacktage.applyReallyAmount}" type="text" name="applyReallyAmount" class="input_text178"></input></td>
          </tr>
          <tr id="T1" style="display: none;">
			   <td><label class="lab">合同编号：</label><input value="${posBacktage.contractCode}" type="text" name="contractCode" class="input_text178"></input></td>
          </tr>
        </table>
        <div class="tright pr30 pb5">
          		<input type="button" id="searchBtn" class="btn btn-primary"
						value="搜索"></input> <input type="button" id="clearBtn"
						class="btn btn-primary" value="清除"></input>
        <div style="float: left; margin-left: 45%; padding-top: 10px">
					<img src="${context}/static/images/up.png" id="showMore"></img>
		</div>
        </div>
      </form:form>
	</div>
	<p class="mb5">
		<button class="btn btn-small" id="daoBtnList">导出excel</button>
		&nbsp;&nbsp;<span class="red">导出条数：</span><span class="red" id="totalNum">${totalNum }</span>
		&nbsp;&nbsp;<span class="red">导出金额：</span><span class="red" id="tmoney"></span>
	</p>
	<div class="box5">
	<table id="tableList" class="table  table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th><input type="checkbox"   class="checkbox" id="checkAll" /></th>
				<th>参考号</th>
				<th>POS机订单号</th>
				<th>到账日期</th>
				<th>存入账户</th>
				<th>金额</th>
				<th>匹配状态</th>
				<th>合同编号</th>
				<th>查账日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:if
				test="${posBackListpage.list!=null && fn:length(posBackListpage.list)>0}">
				<c:set var="index" value="0" />
				<c:forEach items="${posBackListpage.list}" var="item">
					<c:set var="index" value="${index+1}" />
					<tr>
						<td><input type="checkbox" name="checkBoxItem"  id="checkBoxItem"
							val=${item.id } tmoney="${item.applyReallyAmount}" /></td>
						<td>${item.referCode}</td>            <!-- 参考号 --> 
						<td>${item.posOrderNumber}</td>       <!-- 订单号 -->
						<td><fmt:formatDate value="${item.paybackDate}"  pattern="yyyy-MM-dd" type="date" /> </td><!-- 到账日期 -->
						<td>${item.depositedAccount}</td>     <!-- 存入账户 -->
						<td><fmt:formatNumber value='${item.applyReallyAmount}' pattern="0.00"/></td>    <!-- 金额-->
						<td>${item.matchingStateLabel}</td>  <!-- 匹配状态-->
						<td>${item.contractCode}</td>         <!-- 合同编号-->
						<td><fmt:formatDate value="${item.auditDate}" pattern="yyyy-MM-dd" type="date" /></td>   <!-- 查账日期-->
						<td><input type="button" class="btn_edit" onclick="doOpenss('${item.id}','${item.matchingState}','${item.auditDate}','${item.contractCode}');" value="修改" /></td>
						
					   </tr>
				</c:forEach>
			</c:if>
			<c:if
				test="${ posBackListpage.list==null || fn:length(posBackListpage.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有待办数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>
	</div>
	<div class="pagination">${posBackListpage }</div>
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
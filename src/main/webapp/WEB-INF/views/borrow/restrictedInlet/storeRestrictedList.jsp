<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>

<script src="${context}/js/poscard/storeRestriced.js" type="text/javascript"></script>
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
				"${ctx}/borrow/restrictedInlet/storeRestrictedList");
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
		   <form:form  action="${ctx }/borrow/restrictedInlet/storeRestrictedList" method="post" id="backForm" modelAttribute="PosBacktage">
   			<input id="pageNo" name="pageNo" type="hidden" value="${restrictedInletListpage.pageNo}" /> 
			<input id="pageSize" name="pageSize" type="hidden" value="${restrictedInletListpage.pageSize}" />	
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
          <tr>
		       <td><label class="lab">门店编号：</label><input value="${restrictedInlet.orgCode}" type="text" name="orgCode" class="input_text178"></input></td>
     	       <td><label class="lab">门店名称：</label><input value="${restrictedInlet.orgName}" type="text" name="orgName" class="input_text178"></input></td>
			   <td><label class="lab">省分公司名称：</label><input value="${restrictedInlet.provinceName}" type="text" name="provinceName" class="input_text178"></input></td>
          </tr> 
          <tr>
          	   <td><label class="lab">业务大区名称：</label><input value="${restrictedInlet.lgareName}" type="text" name="lgareName" class="input_text178"></input></td>
			   
			   
			   <td><label class="lab">高危线标准：</label><select class="select180" name="highStandard">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_denger_line')}" var="matchingState">
                                   <option value="${matchingState.value }" <c:if test="${restrictedInlet.highStandard == matchingState.value }">selected</c:if>>${matchingState.label }</option>
                              </c:forEach>
                	</select>
               </td>
			   
			   <td><label class="lab">是否被限制进件：</label><select class="select180" name="sfJj">
                		<option value="">请选择</option>
                              <c:forEach items="${fns:getDictList('jk_input_limit')}" var="matchingState">
                                   <option value="${matchingState.value }" <c:if test="${restrictedInlet.sfJj == matchingState.value }">selected</c:if>>${matchingState.label }</option>
                              </c:forEach>
                	</select>
               </td>
          </tr>
        </table>
        <div class="tright pr30 pb5">
          		<input type="button" id="searchBtn" class="btn btn-primary"
						value="搜索"></input> <input type="button" id="clearBtn"
						class="btn btn-primary" value="清除"></input>
        </div>
      </form:form>
	</div>
	
	<p class="mb5">
		<button class="btn btn-small" id="checkBtnList">设置高危线标准</button>
		&nbsp;&nbsp;<span class="red">选择修改条数：</span><span class="red" id="totalNum">${totalNum }</span>
	</p>
	<table id="tableList" class="table  table-bordered table-condensed table-hover ">
		<thead>
			<tr>
				<th><input type="checkbox"   class="checkbox" id="checkAll" /></th>
				<th>门店编号</th>
				<th>门店名称</th>
				<th>省分公司名称</th>
				<th>业务大区名称</th>
				<th>当前M1逾期率</th>
				<th>高危线标准</th>
				<th>M1逾期率高危线</th>
				<th>是否被限制进件</th>
			</tr>
		</thead>
		<tbody>
			<c:if
				test="${restrictedInletListpage.list!=null && fn:length(restrictedInletListpage.list)>0}">
				<c:set var="index" value="0" />
				<c:forEach items="${restrictedInletListpage.list}" var="item">
					<c:set var="index" value="${index+1}" />
					<tr>
						<td><input type="checkbox" name="checkBoxItem"  id="checkBoxItem"
							value=${item.id } /></td>
						<td>${item.orgCode}</td>            <!-- 门店编号--> 
						<td>${item.orgName}</td>           <!-- 门店名称 -->
						<td>${item.provinceName}</td><!-- s省名称 -->
						<td>${item.lgareName}</td>     <!-- 业务大区名称 -->
						
						<c:if test="${item.highStandard!=1}">
						<td>${item.m1YqlCurrentLable}%</td>    <!--当前M1逾期率省份-->
						</c:if>
						<c:if test="${item.highStandard==1}">
						<td>${item.m1YqlLable}%</td>    <!--当前M1逾期率自定义-->
						</c:if>
						<td>${item.highStandardLable}</td>  <!-- 高危线标准-->
						<td>${item.storeNumLable}%</td>         <!-- 门店M1逾期率高危险-->
						<td>${item.sfJjLable}</td>   <!-- 是否被限制进件-->
					   </tr>
				</c:forEach>
			</c:if>
			<c:if
				test="${ restrictedInletListpage.list==null || fn:length(restrictedInletListpage.list)==0}">
				<tr>
					<td colspan="18" style="text-align: center;">没有待办数据</td>
				</tr>
			</c:if>
		</tbody>
	</table>

	<div class="pagination">${restrictedInletListpage }</div>
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
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<script type="text/javascript" src="${ctxStatic}/tableCommon.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script type="text/javascript">

  $(document).ready(function(){
	  
	$('#searchBtn').bind('click',function(){
		$('#inputForm')[0].submit();
	});
	
	$('#clearBtn').bind('click',function(){  
		$(':input', '#inputForm').not(':button, :submit, :reset')
		.val('').removeAttr('checked').removeAttr('selected');
		$('#inputForm')[0].submit();
	});
	
	$('#mateCertNum').bind('blur',
			function(){
		var certValue = $(this).val();
		if(certValue.indexOf("x")!=-1){
			certValue = certValue.replace(/x/g,"X");
			$(this).val(certValue);
		}
	});
  });
 
  function page(n,s) {
		if (n) $("#pageNo").val(n);
		if (s) $("#pageSize").val(s);
		$("#inputForm").attr("action", "${ctx}/apply/consultapp/getConsultList");
		$("#inputForm").submit();
		return false;
	}
  
  function appOpenForm(id) {
  	var url = ctx + '/apply/consultapp/getConsultForm?id='+ id;
  	window.location.href = url;
  }
</script>
<title>app咨询管理</title>
<meta name="decorator" content="default" />
</head>
<body>
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="appCustomerInfo" action="${ctx}/apply/consultapp/getConsultList"
			method="post" class="form-horizontal">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}" />
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}" />
			<table class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">客户名称：</label> <form:input path="customerName" class="input_text178" /></td>
					<td><label class="lab">客户身份证号：</label> <form:input path="certNum" id="mateCertNum" class="input_text178" /></td>
				</tr>
				<tr>
					<td><label class="lab">客户经理：</label> <form:input path="financingName" class="input_text178" /></td>
				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" id="searchBtn" class="btn btn-primary"
					value="搜索"></input> 
				<input type="button" id="clearBtn"
					class="btn btn-primary" value="清除"></input>
			</div>
	</div>
	</form:form>
	<sys:message content="${message}" />
		<table id="tableList" >
		  <thead >
			<tr height="37px">
				<th >客户姓名</th>
				<th >身份证号</th>
				<th >APP咨询创建时间</th>
				<th >计划贷款金额</th>
				<th >客户经理</th>	
				<th >团队经理</th>
				<th >操作</th>
			</tr>
			
			</thead>
			<c:forEach items="${appCustomerInfoPage.list}" var="item">
				<tr>
					<td>${item.customerName}</td>
					<td>${item.certNum}</td>
					<td><fmt:formatDate
							value="${item.firstCreateTime}" pattern="yyyy-MM-dd" /></td>
					<td>${item.loanPosition}</td>
					<td>${item.financingId}</td>
					<td>${item.teamEmpcode}</td>
					<td> 
					<input type="button" class="btn_edit"
						onclick="appOpenForm('${item.id}')" value="办理" /></td>
				</tr>
			</c:forEach>
		</table>
		<div class="pagination">${appCustomerInfoPage}</div> 
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
	</div>
</body>
</html>
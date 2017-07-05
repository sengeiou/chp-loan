<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<script type="text/javascript" src="${context}/js/common.js"></script>
<meta name="decorator" content="default" />
<script type="text/javascript">
  $(document).ready(function(){
	$('#validStatusSub').bind('click',function(){
		$(this).attr("disabled",true);
		var ParamEx=$("#validStatusForm").serialize();
		var updateType=$("#updateType").val();
		var contractCode=$("#contractCode").val();
		var certNum=$("#certNum").val();
		if(updateType=='-1'){
			art.dialog.alert("请选择借款人类型");
			$("#validStatusSub").removeAttr("disabled");
		  	return false;
		}
		if(certNum==''||certNum==null){
			art.dialog.alert("请填写身份证号");
			$("#validStatusSub").removeAttr("disabled");
		  	return false;
		}
		if(contractCode==''||contractCode==null){
			art.dialog.alert("请填写合同编号");
			$("#validStatusSub").removeAttr("disabled");
		  	return false;
		}
		$.ajax({
			type : 'post',
			url : ctx+'/valid/validStatus/toUpdateStatus',
			data :ParamEx,
			cache : false,
			dataType: "json", 
			success : function(result) {
				$("#validStatusSub").removeAttr("disabled");
				art.dialog.alert(result.message,function(){
					window.location.href=ctx+"/valid/validStatus/findValidHisList";
			  	}); 
			},
			error : function() {
				art.dialog.alert('请求异常！');
				$("#validStatusSub").removeAttr("disabled");
			}
		});
	});  
	//提交搜索框
	$('#searchBtn').bind('click',function(){
		$('#inputForm').submit();
	}); 
	//提交清除框
	$('#clearBtn').bind('click',function(){  
		$(':input', '#inputForm').not(':button, :submit, :reset')
		.val('').removeAttr('checked').removeAttr('selected');
		$('#inputForm').submit();
	}); 
	//点击回车触发搜索事件
	 $("body").keyup(function () {  
         if (event.which == 13){  
        	 $('#inputForm').submit();
         }  
     });   
	
  });
 
  
</script>
<title>身份验证管理</title>
<meta name="decorator" content="default" />

</head>
<body>
	<h2 class="f14 pl10">修改（主借人/共借人）身份验证信息</h2>
	<div class="control-group">
		<form:form id="validStatusForm" modelAttribute="validHistory"
			action="#"  method="post" class="form-horizontal">
			<table class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">借款人类型：</label> 
						<select name="updateType" class="input_text178" id="updateType">
							<option value="-1">请选择</option>
							<option value="0">主借人</option>
							<option value="1">共借人</option>
						</select>
					</td>
					<td><label class="lab">合同编号：</label> <input type="text"
							name="contractCode" class="input_text178" id="contractCode"/></td>
					<td><label class="lab">身份证号：</label> <input type="text"
							name="certNum" id="certNum" class="input_text178" id="certNum"/></td>

				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" id="validStatusSub" class="btn btn-primary" value="提交"></input> 
			</div>
	  </form:form>
	</div>
	
	<h2 class="f14 pl10">查询身份验证历史信息</h2>
	<div class="control-group">
		<form:form id="inputForm" modelAttribute="validHistory"
			action="${ctx}/valid/validStatus/findValidHisList"  method="post" class="form-horizontal">
			<table class="table1 " cellpadding="0" cellspacing="0" border="0" width="100%">
				<tr>
					<td><label class="lab">借款人类型：</label> 
						<form:select path="updateType" class="select180 required">
							<option value="-1">请选择</option>
							<option value="0" <c:if test="${validHistory.updateType==0 }">selected</c:if>>主借人</option>
							<option value="1" <c:if test="${validHistory.updateType==1 }">selected</c:if>>共借人</option>
						</form:select>
					</td>
					<td><label class="lab">合同编号：</label> <form:input
							path="contractCode" class="input_text178" /></td>
					<td><label class="lab">身份证号：</label> <form:input
							path="certNum" id="mateCertNum" class="input_text178" /></td>

				</tr>
			</table>
			<div class="tright pr30 pb5">
				<input type="button" id="clearBtn" class="btn btn-primary" value="清除"></input> 
				<input type="button" id="searchBtn" class="btn btn-primary" value="搜索"></input> 
			</div>
	  </form:form>
	</div>
	<%-- <sys:message content="${message}" /> --%>
		<table id="tableList" >
		  <thead >
			<tr height="37px">
				<th >修改类型</th>
				<th >合同编号</th>
				<th >姓名</th>
				<th >身份证号</th>
				<th >操作人</th>
				<th >操作时间</th>
				
			</tr>
			</thead>
			 <c:forEach items="${page.list}" var="item"> 
				<tr>
					<td>${item.updateType }</td>
					<td>${item.contractCode }</td>
					<td>${item.loanName }</td>
					<td>${item.certNum }</td>
					<td>${item.createBy }</td>
					<td>
						<fmt:formatDate value="${item.createTime }" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
				</tr>
				
			 </c:forEach>
		</table>
		<div class="pagination">${page}</div>
	<script type="text/javascript">

	  $("#tableList").wrap('<div id="content-body"><div id="reportTableDiv"></div></div>');
		$('#tableList').bootstrapTable({
			method: 'get',
			cache: false,
			height: $(window).height()-200,
			
			pageSize: 20,
			pageNumber:1
		});
		$(window).resize(function () {
			$('#tableList').bootstrapTable('resetView');
		});
	</script>
</body>
</html>

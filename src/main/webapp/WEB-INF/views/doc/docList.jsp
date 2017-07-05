<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>	
<html>
<head>
<title>${fns:getConfig('productName')}</title>
<style type="text/css">
.wrapper_navbar_navbar .navbar.navbar-inverse {
	background: rgba(51, 51, 51, 0.2);
}
body {
	height: 223px;
	padding: 0px;
	position: relative;
	z-index: 99;
}
.footer {
	text-align: center;
	width: 100%;
	background: rgba(147, 161, 185, 0.5);
	color: #fff;
	padding-top: 14px;
	position: fixed;
	bottom: 0;
}
.table{color:#666;}
.table a{color:#666;}
.table a:hover{color:#000;}
</style>
<script type="text/javascript">

function page(n,s){
    if(n) $("#pageNo").val(n);
    if(s) $("#pageSize").val(s);
    $("#searchForm").attr("action","${context}/a/documentManage/queryDocs");
    $("#searchForm").submit();
    return false;
} 

$(document).ready(function(){
	if("${maxUploadSize}" == "yes"){
		top.$.jBox.tip("文件大小不能超过100M，请重新上传！");
	}
	
	$("#uploadFile").click(function(){
		var files = $('#file').val();
		if(files == "" || file == null ){
			top.$.jBox.tip("请选择文件！");
			return false;
		}
		$("#frmUpload").submit();
	});
	$("#file").change(function(){
		$("#lblFile").html($('#file').val());
	});
});
</script>

</head>
<body>
	<div class="wrapper wrapper_navbar_navbar at-top" style="text-align:left;width:1170px;margin:0 auto;margin-top:10px;">
	</div>
	<div class="container">
		<form id="frmUpload" action="${ctx}/documentManage/upload" method="POST" enctype="multipart/form-data" >
			<legend>上传文件</legend>
			<input id="file" name="file" type="file" style="display: none">
			<label id="lblFile"><font color="red">请选择100M以内的文件</font></label>
			<a class="btn btn-primary" onclick="$('input[id=file]').click();">选择文件</a>
			<a type="button" class="btn btn-primary" data-dismiss="modal" id="uploadFile">上传</a>
		</form>
		<form id="searchForm" style="width:100%;">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
	        <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
	        
			<div class="row">
				<table id="contentTable" class="table table-striped table-bordered table-condensed" style="color:3c3c3c; margin-bottom: 22px">
				<tr>
					<th>序号</th>
					<th>文件名称</th>
					<th>上传人</th>
					<th>上传时间</th>
				</tr>
				<c:forEach items="${page.list }" var="doc" varStatus="status">
					<tr>
						<td width="10%">${status.index + 1 }</td>
						<td style="text-align: left;"><a style="color:3c3c3c;" href="${pageContext.request.contextPath}/a/documentManage/download/${doc.docId}" target="_blank"><i class="icon-arrow-down"></i> ${doc.docTitle }</a></td>
						<td>${doc.docCreator}</td>
						<td><fmt:formatDate value='${doc.createdDate}' pattern="yyyy-MM-dd"/></td>
					</tr>
				</c:forEach>
				</table>
				<div class="pagination">${page}</div>
			</div>
		</form>
	</div>
</body>
</html>


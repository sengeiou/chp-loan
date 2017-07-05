<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>放款确认</title>

<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.min.js"></script>
<script src="${context}/static/ckfinder/plugins/gallery/colorbox/jquery.colorbox-min.js"></script>
<link media="screen" rel="stylesheet" href="${context}/static/ckfinder/plugins/gallery/colorbox/colorbox.css" />
<script src="${context}/js/car/grant/carDisCardDeal.js" type="text/javascript"></script>
<meta name="decorator" content="default" />
<script language="javascript">

</script>
</head>
<body>
	<!--分配外访弹出框表-->
	<div class="dialogbox">
		<div class="diabox_cell">
			<div class="diabox">
			<div class="mb10">
				<h3 class="tcenter mb30">放款人员</h3>
				<form action="${ctx }/car/grant/disCard/showDisPerson" >
					
					<label class="lab">编号：</label>
					<input type="text" class="input_text140 mr10" id="userCode" name="userCode" value="${userInfo.userCode }">
					<label class="lab">姓名：</label>
					<input type="text" class="input_text140 mr10" id="name" name="name" value="${userInfo.name }">
					
					<input type="submit" class="btn btn-small" value="查询"/>
				</form>
				</div>
					<table class="table  table-bordered table-condensed table-hover ">
						<thead>
						<tr>
							<th>序号</th>
							<th>员工编号</th>
							<th>姓名</th>
							<th>选择</th>
						</tr>
						</thead>
						   <tbody>
					         <c:if test="${ user!=null && fn:length(user)>0}">
					          <c:set var="index" value="0" />
						      <c:forEach items="${user}" var="item">
							  <c:set var="index" value="${index+1}" />
					            <tr>
					             <td>${index}</td>
					             <td>${item.userCode}</td>
					             <td>${item.name}</td>
					             <td><input type="checkbox" name="SelectEl" value="${item.id}"></td>
					             
				
					            </tr> 
					         </c:forEach>            
					         </c:if>
					         <c:if test="${ user==null || fn:length(user)==0}">
					         <tr><td colspan="18" style="text-align:center;">没有待办数据</td></tr>
					         </c:if>
					       </tbody>
			        </table>
				
			</div>
		</div>
	</div>

</body>
</html>
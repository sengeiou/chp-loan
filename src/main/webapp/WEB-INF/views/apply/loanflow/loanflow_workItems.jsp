<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>流程待办页面</title>
<script>
 
</script>
</head>
<body>
   <table>
     <tbody>
       <tr>
         <td colspan="4">待办列表</td>
       </tr>
       <tr>
		 <td>客户姓名</td> 
		 <td>任务ID</td>
		 <td>步骤名称</td>
		 <td>操作</td>
		</tr>
		<c:forEach items="${itemList}" var="item">
			<tr>
				<td>${item.customerName}</td> 
				<td>${item.wobNum}</td>
				<td>${item.stepName}</td>
				
				<!--
				     进入办理页面，直接调用flowController的openForm方法即可，传递的参数如下所示，
				     同时需要在taskDispatch.xml相关的steps下配置办理页面所在位置
				 -->
				<td><input type="button" value="办理" name="办理" onclick="window.location='${ctx}/bpm/flowController/openForm?applyId=${item.applyId}&wobNum=${item.wobNum}&dealType=0&token=${item.token}'"></input>
				    <input type="button" value="更改姓名" name="更改姓名" onclick="window.location='${ctx}/apply/loanFlow/updateFlowAttr?flowName=${item.flowName}&stepName=${item.stepName}&wobNum=${item.wobNum}&token=${item.token}&customerName=更改姓名不改流程'">
				    <input type="button" value="属性绑定" name="属性绑定" onclick="window.location='${ctx}/apply/loanFlow/attrBinder?applyId=${item.applyId}&wobNum=${item.wobNum}&qryParam[0]=qryParam'"></input>
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
   </table>
</body>
</html>
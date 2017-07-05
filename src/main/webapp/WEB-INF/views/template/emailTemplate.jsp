<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta name="decorator" content="default"/>
<script type="text/javascript">
     /* 跳转到添加页面 */
    function saveBtn(){
    	 location.href = ctx +"/borrow/template/email/saveForm";
    }
     /*跳转到修改页面  */
     function updateBtn(id){
          location.href = ctx +"/borrow/template/email/updateForm?id="+id;  
     }
</script>
</head>
<body>
<div class="body_r">
     
     <p class="mb5 mt5">
     <input type="button" class="btn btn-small" value="新增" onclick="saveBtn()"/></p>
    <form action="${ctx }/borrow/template/email/findAllList">
    <table cellspacing="0" cellpadding="0" border="1"  class="table2" width="100%">
				<tr>
					<th>模板名称</th>
					<th>描述</th>
					<th>模板内容</th>
					<th>模板类型</th>
					<th>创建时间</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${list }" var="ls">
					<input type="hidden" name="id" value="${ls.id }"/>
				<tr>
					<td>${ls.templateName }</td>
					<td>${ls.templateDescription }</td>
					<td>${ls.templateContent }</td>
					<td>${ls.templateType }</td>
					<td>${ls.createTime }</td>
				     <td><input type="button" class="btn_edit" onclick="updateBtn('${ls.id }')" value="办理"/></td>
				</tr>
				</c:forEach>
			</table>
			</form>
    </div>
</body>
</html>

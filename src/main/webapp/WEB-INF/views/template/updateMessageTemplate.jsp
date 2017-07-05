<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta name="decorator" content="default"/>
</head>
<body>
<div class="body_r">
    
    <div class="control-group">
       <form id="updateFrom" action="${ctx }/borrow/template/sms/updateMessageTemplate" method="post" >
        <input type="hidden" name="id" value="${messageTemplate.id  }">
		<sys:message content="${message}"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab"><span class='red'>*</span>模板名称：</label><input type="text" class="input_text178" name="templateName" value='${messageTemplate.templateName }'/></td>
                <td><label class="lab"><span class='red'>*</span>描述：</label><input type="text" class="input_text178" name="templateDescription"value='${messageTemplate.templateDescription }'/></td>
            </tr>
             <tr>
                <td><label class="lab"><span class='red'>*</span>模板内容：</label><input type="textarea" class='textarea_big2' name="templateContent" value="${messageTemplate.templateContent }"/></td>
                <td><label class="lab"><span class='red'>*</span>模板code：</label><input type="text" class="input_text178" name="templateCode" value='${messageTemplate.templateCode }'/></td>
            </tr>
			 <tr>
                <td><label class="lab"><span class='red'>*</span>模板状态：</label><select class="input_text178" name="templateStatus" value="${messageTemplate.templateStatus }"><option value="">请选择</option><option value="0">停用</option><option value="1">可用</option></select></td>
                <td></td>
            </tr>
        </table>
        <div class="tright pr30 pb5"> 
			<input type="submit" value="修改" name="update" class="btn btn-primary" />
			<input type="button" name="go" value="返回"  class="btn btn-primary" onclick="javascript:history.go(-1)"/>
		</div>
		 </form>
</body>
</html>

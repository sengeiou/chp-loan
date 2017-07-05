<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta name="decorator" content="default"/>
<title></title>

</head>
<body>
<div class="body_r">
     <p class="bar"><span class="color_b97226">首页>交互管理></span>邮件模板修改管理</p>
    <div class="box1 mb10">
       <form action="${ctx }/borrow/template/email/updateEmailTemplate" method="post">
       <input type="hidden" name="id" value="${emailTemplate.id  }">
		<sys:message content="${message}"/>
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab"><span class='red'>*</span>模板名称：</label><input type="text" class="input_text178" name="templateName" value="${emailTemplate.templateName }"/></td>
                <td><label class="lab"><span class='red'>*</span>描述：</label><input type="text" class="input_text178" name="templateDescription" value="${emailTemplate.templateDescription}"/></td>
            </tr>
             <tr>
                <td><label class="lab"><span class='red'>*</span>模板内容：</label><input type="textarea" class='textarea_big2' name="templateContent" value="${emailTemplate.templateContent }"/></td>
                <td><label class="lab"><span class='red'>*</span>模板类型：</label><select class="input_text178" name="templateType" value="${emailTemplate.templateType }"><option>请选择</option><option value="0">首期</option><option value="1">非首期</option></select></td>
            </tr>
        </table>
        <div class="tright pr30">
            <input type="submit" value="修改" name="update" />
            <input type="button" name="go" value="返回" onclick="javascript:history.go(-1)"/>
		</div>
		</form>
    </div>
    </div> 
</body>
</html>

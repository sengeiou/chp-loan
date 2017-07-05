<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<meta name="decorator" content="default"/>
<html xmlns="http://www.w3.org/1999/xhtml"><head>
</head>
<body>
<div class="body_r">
     
     <form method="post" action="${ctx }/borrow/template/sms/saveMessageTemplate">
    <div class=" control-group">
        <table class="table1" cellpadding="0" cellspacing="0" border="0" width="100%">
            <tr>
                <td><label class="lab"><span class='red'>*</span>模板名称：</label><input type="text" class="input_text178" name="templateName" value='${templateName }'/></td>
                <td><label class="lab"><span class='red'>*</span>描述：</label><input type="text" class="input_text178" name="templateDescription" value='${templateDescription }'/></td>
            </tr>
             <tr>
                <td><label class="lab"><span class='red'>*</span>模板内容：</label><input type="textarea" class='textarea_big2' name="templateContent" value="${templateContent }"/></td>
                <td><label class="lab"><span class='red'>*</span>模板code：</label><input type="text" class="input_text178" name="templateCode" value="${templateCode }"/></td>
            </tr>
			 <tr>
                <td><label class="lab"><span class='red'>*</span>模板状态：</label><select class="select180" name="templateStatus" value="${templateStatus }"><option value="">请选择</option><option value="0">停用</option><option value="1">可用</option></select></td>
                 <td><label class="lab"><span class='red'>*</span>模板类型：</label><select class="select180" name="templateType" value="${templateType }"><option value="">请选择</option><option value="0">汇金汇诚</option><option value="1">财富</option></select></td>
            </tr>
        </table>
        <div id="result"></div>
        <div class="tright pr30"> 
            <input type="submit" name="submit" class="btn btn-primary" value="新增"/>
			<input type="button" name="go" value="返回" class="btn btn-primary" onclick="javascript:history.go(-1)"/>
		</div>
    </div>
    </form>
    </div> 
</body>
</html>

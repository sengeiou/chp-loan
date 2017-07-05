<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>催收服务费退款初审</title>

<script type="text/javascript" >

function updateRefund(formId) {
	if($('#remark').val()==''){
		art.dialog.alert("请添加审批意见！");
		return;
	}
	$.ajax({
		url : ctx + '/borrow/refund/longRefund/operate',
		type : 'post',
		data : $('#' + formId).serialize(),
		dataType : 'text',
		success : function(data) {
			if (data) {
				// 如果父页面重载或者关闭其子对话框全部会关闭
				var win = art.dialog.open.origin;//来源页面
				art.dialog.close();
				art.dialog.alert("审核成功！");
			} else {
				art.dialog.alert("审核失败,请重试！");
			}
		},
		error : function() {
			art.dialog.alert("服务器异常,请重试！");
		},
		async : false

	});
}

</script>
<meta name="decorator" content="default"/>

</head>
<body>
 <div class="box2 mb10">
  <form id="inputForm" >
  		<input type="hidden" id="type" name="type" value="${type }">
        <table class=" table1" cellpadding="0" cellspacing="0" border="0" width="95%">
        	<tr>
                <td><label class="lab">退款原因：</label>
               <input type="radio" class="input_text18" id="fkReason" name="fkReason" value="1" checked/>系统原因
               <input type="radio" class="input_text18" id="fkReason" name="fkReason" value="2" />门店操作错误
               <input type="radio" class="input_text18" id="fkReason" name="fkReason" value="3" />客户自身原因
                </td>
             </tr>
			  <tr>
                <td><label class="lab">审批意见：</label>
                <input type="hidden" id="id" name="id" value="${refund.id}">
                <input type="hidden" id="mt" name="mt" value="${refund.mt}">
                <input type="hidden" id="fkResult" name="fkResult" value="${refund.fkResult}">
            <textarea rows="5" cols="6" maxlength="150" style="width:200px;height: 80px;" class="required" id="remark" name="remark" ></textarea>
                </td>
             </tr> 

        </table>
        
        <div class="tright pr30">
       
        <input type="button" id="submitBtn" value="提交"  class="btn btn-primary" onclick="updateRefund('inputForm');"/> 
    
        </div>
 </form>  
</div>
</body>
</html>

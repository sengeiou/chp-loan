<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="../../../js/contract/_flowGrantBack.js" type="text/javascript"></script>
<script type="text/javascript">
 $(document).ready(function(){
	$('#ConfirmBtn').bind('click',function(){
		var response = $('#response option:selected').val();
		var responseText = $('#response option:selected').text();
		if(response=='' || response==null){
			$('#responseMessage').html("请选择需要退回的节点");
			return false;
		 }else{
			 $('#responseMessage').html(""); 
		 }
		if(responseText!='待签订上传合同'){
			if($('#remarks').val()==''){
				$('#remarksMessage').html("请输入退回原因");
				return false;
			}else{
				$('#remarksMessage').html("");
				$('#chooseRemarksMessage').html('');
			}
		}else{
			if($('#chooseRemark').val()==''){
			    $('#chooseRemarksMessage').html('请选择退回原因');	
			    return false;
			}else{
				$('#chooseRemarksMessage').html('');	
				$('#remarksMessage').html("");
			}
			
		}
		if($('#remarks').val().length>500){
			$('#remarksMessage').html("退回原因不能超过500字");
			return false;
		}else{
			$('#remarksMessage').html("");
		}
		// 如果父页面重载或者关闭其子对话框全部会关闭
		var win = art.dialog.open.origin;//来源页面
		$.ajax({
			type : 'post',
			url : ctx+"/apply/contractAudit/backFlow",
			data :$('#backForm').serialize(),
			cache : false,
			async : false,
			success : function(result) {
		      if('success'==result){
		    	 win.location = ctx+$('#redirectUrl').val()+"?flowFlag="+$('#flowFlag').val()+"&menuId="+$("#menuId").val();
		 		 art.dialog.close();  
		      }
			},
			error : function() {
				art.dialog.alert('请求异常！', '提示信息');
			}
		});
	}); 
   $('#CancelBtn').bind('click',function(){
	   art.dialog.close();
	}); 
   $('#response').bind('change',function(){
	   var resText = $('#response option:selected').text();
	   if(resText=='待签订上传合同'){
		   $('#remarkChooseItem').css('display','');  
		   $('#remarkLabel').html('');
		   $('#chooseRemarksMessage').html('');	
		   $('#remarksMessage').html("");
	   }else{
		   $('#remarkChooseItem').css('display','none'); 
		   $('#remarkLabel').html('*');
		   $('#chooseRemarksMessage').html('');	
		   $('#remarksMessage').html("");
	   }
   });	 
 });
 
</script>
</head>
<body>
<div id="auditRateBackWindow">
 <div class="control-group pb10">
 <form id="backForm" method="post">
 <p class="pl10 f14">提示</p>
  <table>
     <thead>
       
     </thead>
     <tbody>
        <tr>
           <td><label class="lab"><span style="color:red">*</span>退回至流程节点：</label></td>
           <td>
               <select name="response" id="response" class="input-medium">
                 <c:forEach items="${backResponses}" var="response">
				   <option value='${response.backResponse}'>${response.backMsg}</option>
				 </c:forEach>
				</select>
				 <span id="responseMessage" style="color:red"></span>
		    	 <input type="hidden" value="${workItem.flowCode}" name="flowCode"></input>
     			 <input type="hidden" value="${workItem.flowName}" name="flowName"></input>
     			 <input type="hidden" value="${workItem.wobNum}" name="wobNum"></input>
    			 <input type="hidden" id="stepName" value="${workItem.stepName}" name="stepName"></input>
    			 <input type="hidden" value="${workItem.token}" name="token"></input>
      			 <input type="hidden" value="${workItem.flowId}" name="flowId"></input>
      			 <input type="hidden" value="${applyId}" name="applyId"/>
      			 <input type="hidden" value="${flowFlag}" id="flowFlag" name="flowFlag"/>
      			 <input type="hidden" value="${redirectUrl}" id="redirectUrl" name="redirectUrl"/>
      			 <input type="hidden" value="${contractView.contract.contractCode}" name="contract.contractCode"/>
                 <input type="hidden" value="${contractView.contract.loanCode}" name="contract.loanCode"/>
                 <input type="hidden" value="0" name="dictOperateResult"/>
                 <input id="menuId" name="menuId" value='${menuId} ' type="hidden">
		   </td>
         </tr>
          <tr id="remarkChooseItem" style="display:none">
            <td><label class="lab"><span style="color:red" id="backReasonL">*</span>退回原因：</label>
            </td>
            <td>
              <select name="remarks" id="chooseRemark" class="input-medium">
               	<option value="">请选择</option>
               	 <c:forEach items="${fns:getDictList('jk_contract_verify_return')}" var="item">
                	 <option value="${item.label}">${item.label}</option>
                 </c:forEach>  
              </select>
               <span id="chooseRemarksMessage" style="color:red"></span>
            </td>
          </tr>  
          <tr>
           <td><label class="lab"><span style="color:red" id="remarkLabel">*</span>备注：</label></td>
            <td>
              <textarea rows="" cols="" id="remarks" name="remarks" class="textarea_refuse"></textarea>
              <span id="remarksMessage" style="color:red"></span>
            </td>
        </tr>
  
     </tbody>
  </table>
 </form>
  </div>
  <div class="tright pt10 pr30"> <input type="button" class="btn btn-primary" value="确认" id="ConfirmBtn"></input>
              <input type="button" class="btn btn-primary" value="取消" id="CancelBtn"></input></div>
</div>
</body>
</html>

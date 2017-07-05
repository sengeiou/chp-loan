<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<meta name="decorator" content="default" />
<script src="../../../js/contract/_flowGrantBack.js" type="text/javascript"></script>
<script src="${context}/js/common.js" type="text/javascript"></script>
<script type="text/javascript">
 $(document).ready(function(){
	$('#ConfirmBtn').bind('click',function(){
		var response = $('#response option:selected').val();
		var responseText = $('#response option:selected').text();
		var flowFlag= $('#flowFlag').val();
		if(flowFlag!='RATE_AUDIT'){
			if(response=='' || response==null){
				$('#responseMessage').html("请选择需要退回的节点");
				return false;
			 }else{
				 $('#responseMessage').html(""); 
			 }
		}
		if(responseText=='待确认签署'){
			$('#remarksMessage').html("");
			$('#chooseRemarksMessage1').html('');
			var remark = "";
			if($("#signReturnCheck").attr("checked")){
				if($('#remarksTemp').val()==''){
					$('#remarksMessage').html("请输入退回原因");
					return false;
				}else{
					$('#remarksMessage').html("");
					$('#chooseRemarksMessage1').html('');
				}
			}else{
				if($('#signReturn').val()==''){
				    $('#chooseRemarksMessage1').html('请选择退回原因');	
				    return false;
				}else{
					$('#chooseRemarksMessage1').html('');	
					$('#remarksMessage1').html("");
				}
			}
			 
			var  selectValue= $('#signReturn').val();
			var  textValue = $('#remarksTemp').val();
			selectValue = selectValue.replace(/,/g,"；")
			if(selectValue != null && selectValue !=""){
				remark = remark+","+selectValue;
			}
			if(textValue != null && textValue !=""){
				remark = remark+"；其他："+textValue;
			}
			remark = remark.substring(1,remark.length);
			
			$("#remarks").html(remark);
		}else if(responseText=='待上传合同'){
			$('#remarksMessage2').html("");
			$('#chooseRemarksMessage2').html('');
			var remark = "";
			if($("#uploadReturnCheck").attr("checked")){
				if($('#remarksTemp').val()==''){
					$('#remarksMessage').html("请输入退回原因");
					return false;
				}else{
					$('#remarksMessage').html("");
					$('#chooseRemarksMessage2').html('');
				}
			}else{
				if($('#uploadReturn').val()==''){
				    $('#chooseRemarksMessage2').html('请选择退回原因');	
				    return false;
				}else{
					$('#chooseRemarksMessage2').html('');	
					$('#remarksMessage2').html("");
				}
			}
			
			var  selectValue= $('#uploadReturn').val();
			var  textValue = $('#remarksTemp').val();
			selectValue = selectValue.replace(/,/g,"；")
			if(selectValue != null && selectValue !=""){
				remark = remark+","+selectValue;
			}
			if(textValue != null && textValue !=""){
				remark = remark+"；其他："+textValue;
			}
			remark = remark.substring(1,remark.length);
			
			$("#remarks").html(remark);
		}else{
			if($('#chooseRemark').val()==''){
			    $('#chooseRemarksMessage').html('请选择退回原因');	
			    return false;
			}else{
				$('#chooseRemarksMessage').html('');	
				$('#remarksMessage').html("");
			}
			
		}
		if($('#remarksTemp').val().length>500){
			$('#remarksMessage').html("退回原因不能超过500字");
			return false;
		}else{
			$('#remarksMessage').html("");
		}
		$('#ConfirmBtn').attr('disabled',true);
		// 如果父页面重载或者关闭其子对话框全部会关闭
		var win = art.dialog.open.origin;//来源页面
		$.ajax({
			type : 'post',
			url : ctx+"/apply/contractAudit/backListFlow",
			data :$('#backForm').serialize(),
			cache : false,
			async : false,
			success : function(result) {
	 		    art.dialog.close();  
	 			art.dialog.alert(result,function (){
	 		    win.location = ctx+$('#redirectUrl').val()+"?flowFlag="+$('#flowFlag').val()+"&menuId="+$("#menuId").val();
	 			});
			},
			error : function() {
				art.dialog.alert('请求异常！', '提示信息');
			}
		});
	}); 
   $('#CancelBtn').bind('click',function(){
	   art.dialog.close();
	}); 
   ////退回节点发生变更时
   $('#response').bind('change',function(){
	   var resText = $('#response option:selected').text();
	   if(resText=='待签订上传合同'){
		   $('#remarkChooseItem').css('display','');  
		   $('#remarkTR').css('display','');  
		   $('#remarkLabel').html('');
		   $('#chooseRemarksMessage').html('');	
		   $('#remarksMessage').html("");
	   }else if(resText=='待确认签署'){
		   $('#remarkTR').css('display','none');
		   $('#remarkChooseItem2').css('display','none');
		   $('#remarkChooseItem1').css('display','');
		   $('#remarkChooseItem1').val("");
		   $('#chooseRemarksMessage').html('');	
		   $('#remarksMessage').html("");
		   $("#remarksTemp").val("");//选择时清空 备注框内容
	   }else if(resText=='待上传合同'){
		   $('#remarkTR').css('display','none');
		   $('#remarkChooseItem1').css('display','none'); 
		   $('#remarkChooseItem2').css('display','');
		//   $('#remarkChooseItem2').attr("che","");
		   $('#chooseRemarksMessage').html('');	
		   $('#remarksMessage').html("");
		   $("#remarksTemp").val("");//选择时清空 备注框内容
	   }
   });	 
   
   
   
   
  /*  //退回原因发生变更时 退回待确认签署
   $('#chooseRemark1').bind('change',function(){
	   var resText = $('#chooseRemark1  option:selected').text();
	   var resText1 = $('#chooseRemark1').val();
	   alert(resText.indexOf('其他'));
	   if(resText.indexOf('其他')>0){
		   $('#remarkTR').css('display',''); 
		   $('#chooseRemarksMessage').html('');	
		   $('#remarkLabel').html('*');//选择其他的时候要有 **
		   $('#remarksMessage').html("");//每次重新选择时候清空
		   
	   }else{
		   $('#remarkTR').css('display','none');  
		   $('#remarkChooseItem2').css('display','none');
		   $('#remarkChooseItem1').css('display',''); 
		   $('#remarksMessage').html("");
		   $("#remarksTemp").val("");//选择时清空 备注框内容
	   } 
   });	  */
 
   
  /*  //退回原因发生变更时 退回待上传合同
   $('#uploadReturn').bind('change',function(){
	   var resText = $('#uploadReturn').val();
	   if(resText.index('其他')>0){
		   $('#remarkTR').css('display','');  //选择其他的时候 出现备注框 
		   $('#remarkLabel').html('*');//选择其他的时候要有 **
		   $('#remarksMessage').html("");
	   }else{
		   $('#remarkTR').css('display','none');  
		   $('#remarkLabel').html('');
		   $('#remarksMessage').html("");
		   $("#remarksTemp").val("");//选择时清空 备注框内容
	   } 
   });	 */
   $('#remarkTR').css('display','none');  //加载的时候 不出现备注框 
   var resText = $('#response option:selected').text();
 	if(resText=='待确认签署'){
	   $('#remarkChooseItem2').css('display','none');
	   $('#remarkChooseItem1').css('display',''); 
	   $('#chooseRemarksMessage').html('');	
	   $('#remarksMessage').html("");
   }else if(resText=='待上传合同'){
	   $('#remarkChooseItem1').css('display','none'); 
	   $('#remarkChooseItem2').css('display',''); 
	   $('#chooseRemarksMessage').html('');	
	   $('#remarksMessage').html("");
   }
 });
 $(function(){
	 var flag='${flag}';
	 if(flag==1){
		 art.dialog.close();  
		 art.dialog.alert('没有符合退回的数据！', '提示信息');
	 }
	 var flowFlag= $('#flowFlag').val();
	 if(flowFlag=='RATE_AUDIT'){
	 	$('#backNode').hide(); 
	 }
 });
 
 function changeCheckBoxsignReturn(){
	 if($("#signReturnCheck").attr("checked")){
		 $('#remarkTR').css('display',''); 
	 }else{
		 $('#remarkTR').css('display','none'); 
		 $("#remarksTemp").val("");
	 }
 }
 function changeCheckBoxuploadReturn(){
	 if($("#uploadReturnCheck").attr("checked")){
		 $('#remarkTR').css('display',''); 
	 }else{
		 $('#remarkTR').css('display','none'); 
		 $("#remarksTemp").val("");
	 }
 }
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
        <tr id="backNode">
           <td ><label class="lab"><span style="color:red">*</span>退回至流程节点：</label></td>
           <td >
               <select name="response" id="response" class="input-medium">
                 <c:forEach items="${backResponses}" var="response">
				   <option value='${response.backResponse}'>${response.backMsg}</option>
				 </c:forEach>
				</select>
				 <span id="responseMessage" style="color:red"></span>
                
                <c:forEach items="${list}" var="list">
	     			 <input type="hidden" value="${list.flowName}" name="flowName"></input>
	     			 <input type="hidden" value="${list.wobNum}" name="wobNum"></input>
	    			 <input type="hidden" id="stepName" value="${list.stepName}" name="stepName"></input>
	    			 <input type="hidden" value="${list.token}" name="token"></input>
	      			 <input type="hidden" value="${list.applyId}" name="applyId"/>
	      			 <input type="hidden" value="${list.contract.contractCode}" name="contract.contractCode"/>
	                 <input type="hidden" value="${list.contract.loanCode}" name="contract.loanCode"/>
                </c:forEach>
                 <input type="hidden" value="0" name="dictOperateResult"/>
      			 <input type="hidden" value="${redirectUrl}" id="redirectUrl" name="redirectUrl"/>
      			 <input type="hidden" value="${flowFlag}" id="flowFlag" name="flowFlag"/>
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
          <!--  退回至待确认签署   -->
          <tr id="remarkChooseItem1" style="display:none">
            <td><label class="lab"><span style="color:red" id="backReasonL1">*</span>退回原因：</label>
            </td>
            <td>
            <%--   <select name="remarks1" id="chooseRemark1" class="input-medium"  multiple="multiple">
               	 <option value="">请选择</option>
               	 <c:forEach items="${fns:getDictList('auditContract_sign')}" var="item">
                	 <option value="${item.label}">${item.label}</option>
                 </c:forEach>  
              </select> --%>
               <input type="text" id="signReturn" name="signReturn" class="input_text178" readonly="readonly" oninput="alert(123)"/> 
					  <i id="selectsignReturnBtn" class="icon-search" style="cursor: pointer;" onClick="getDict('auditContract_sign','退回原因','signReturn','signReturnId');"></i>
				 <input type="hidden" name="signReturnId"   id="signReturnId" />
               <span id="chooseRemarksMessage1" style="color:red"></span>
                <input type="checkbox" id="signReturnCheck" onclick="changeCheckBoxsignReturn();">  其他
            </td>
        
          </tr>  
           <!--  退回至待上传合同    -->
          <tr id="remarkChooseItem2" style="display:none">
            <td><label class="lab"><span style="color:red" id="backReasonL2">*</span>退回原因：</label>
            </td>
            <td>
            <%--   <select name="remarks2" id="chooseRemark2" class="input-medium">
               	<option value="">请选择</option>
               	 <c:forEach items="${fns:getDictList('auditContract_upload')}" var="item">
                	 <option value="${item.label}">${item.label}</option>
                 </c:forEach>  
              </select> --%>
            	 <input type="text" id="uploadReturn" name="uploadReturn" class="input_text178" readonly="readonly"/> 
					  <i id="selectuploadReturnBtn" class="icon-search" style="cursor: pointer;" onClick="getDict('auditContract_upload','退回原因','uploadReturn','uploadReturnId');"></i>
				 <input type="hidden" name="uploadReturnId"   id="uploadReturnId" />
               <span id="chooseRemarksMessage2" style="color:red"></span>
               <input type="checkbox" id="uploadReturnCheck" onclick="changeCheckBoxuploadReturn();"> 其他
            </td>
          </tr>    
          <tr id="remarkTR" >
           <td><label class="lab"><span style="color:red" id="remarkLabel">*</span>备注：</label></td>
            <td>
             <textarea rows="" cols="" id="remarksTemp" name="remarksTemp" class="textarea_refuse"  ></textarea>
              <span id="remarksMessage" style="color:red"></span>
            </td>
        </tr>
  		<tr>
  			<td> <textarea rows="" cols="" id="remarks" name="remarks" class="textarea_refuse" hidden="true"></textarea></td>
  		</tr>
     </tbody>
  </table>
 </form>
  </div>
  <div class="tright pt10 pr30"> <input type="button" class="btn btn-primary" value="确认1" id="ConfirmBtn"></input>
              <input type="button" class="btn btn-primary" value="取消" id="CancelBtn"></input></div>
</div>
</body>
</html>

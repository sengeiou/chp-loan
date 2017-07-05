var ctr={};

ctr.search = function(formId){
	$('#'+formId)[0].submit();	
};
ctr.clearForm = function(formId){
	$(':input','#'+formId).not(':button,:submit,:reset,:hidden').val('')
	.removeAttr('checked').removeAttr('selected');
};
ctr.preAddTemplate=function(){
 window.open(ctx+'/template/contract/preAddContractTemplate','window','width=1000px,height=350px, top=200px,left=300px, modal=yes,status=no');
};
ctr.addTemplate=function(formId){
	$('#'+formId).submit();
};
ctr.viewTemplate=function(templateId,viewName){
 window.open(ctx+'/template/contract/loadContractTemplateSingle?id='+templateId+"&viewName="+viewName,'_blank','width=1000px,height=350px, top=200px,left=300px, modal=yes,status=no');
};
ctr.histroy = function(templateType,viewName,tagObj){
 
 $.ajax({
	 url:ctx+"/template/contract/asynLoadContractTemplateList",
	 type:'post',
	 data:{
		'templateType':templateType,
		'viewName':viewName
	 },
	success:function(result){
		$('#'+tagObj).html(result);
		$('#'+tagObj).css('display','block');
	 },
	 error:function(){
		  alert('请求异常！'); 
	 },
	 dataType:'html'
  });
};
ctr.subContractTemplateList=function(templateType){
	window.open(ctx+'/template/contract/loadSubContractTemplateList?templateType='+templateType,'window','width=1000px,height=350px, top=200px,left=300px, modal=yes,status=no');
};
ctr.preAddAttach=function(){
  window.open(ctx+'/template/contract/attachment/preAddContractAttachment','window','width=1000px,height=350px, top=200px,left=300px, modal=yes,status=no');
};
ctr.listAttach = function(templetId){
  
  window.open(ctx+'/template/contract/attachment/queryAttachmentList?templetId='+templetId,'window','width=1000px,height=350px, top=200px,left=300px, modal=yes,status=no');

};
ctr.enableUsed=function(tempId,tagStatus,curStatus,flag,tempType,viewName){
	var message = null;
	var scope =null ;//操作范围 0：主合同、子合同及相关附件 1：主合同及附件或 子合同及附件 2：当前合同 3：不执行任何操作
	/**flag 0 主合同 、1子合同
	 * tagStatus 0 停用
	 */
	if(flag=='0' && tagStatus=='0'){
		message = '选择【确定】停用跟当前主合同相关的子合同以及子合同附件，选择【取消】停用当前主合同及其附件';
	}else if(flag=='1' && tagStatus=='0'){
		message = '选择【确定】停用当前子合同及其附件，选择【取消】放弃执行当前操作'; 
	}else if(flag=='0' && tagStatus=='1'){
		message = '选择【确定】执行启用主合同模板操作，选择【取消】放弃执行当前操作';
	}else if(flag=='1' && tagStatus=='1'){
	    message = '选择【确定】执行启用子合同模板操作，选择【取消】放弃执行当前操作';
	}
	var confirm = window.confirm(message);
	if(flag=='0' && tagStatus=='0'){
		if(confirm){
		    scope='0';
		}else{
			scope='1';
		}
	}else if(flag=='1' && tagStatus=='0'){
		if(confirm){
		    scope='1';
		}else{
			scope='3';
		}
	}else{
		if(confirm){
		    scope='2';
		}else{
			scope='3';
		}
	}
	if(scope!='3'){
	window.location=ctx+'/template/contract/updateContractTemplateStatus?curStatus='+curStatus+'&tagStatus='+tagStatus+'&templateId='+tempId+'&flag='+flag+'&tempType='+tempType+'&scope='+scope+"&viewName="+viewName;	
	}
};

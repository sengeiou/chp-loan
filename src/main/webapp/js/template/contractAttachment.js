var contractAttach={};

contractAttach.InsertAttachment=function(formId){
	$('#'+formId).submit();
};
contractAttach.clearForm = function(formId){
	$(':input','#'+formId).not(':button,:submit,:reset,:hidden').val('')
	.removeAttr('checked').removeAttr('selected');
};
contractAttach.updateStatus = function(templetId,id,tagStatus){
	window.location=ctx+"/template/contract/attachment/updateAttachmentStatus?templetId="+templetId+"&id="+id+"&tagStatus="+tagStatus;
	
};
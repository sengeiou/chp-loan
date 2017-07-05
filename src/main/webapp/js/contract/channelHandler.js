var channel={};
channel.openDialog = function(attributeName,flowFlag,redirectUrl){
	var targetModel = "";
	var hasDifModel= false;
	var hasNoChannel = false;
	var hasChannel = false;
	var errorCount=0;
	var sucessCount=0;
	
	var sucessCountZCJ=0;
	var errorCountZCJ=0;
	var param = "";
	var paramZCJ="";
	var replyProductName = false;
	var BackCount=0;
	var NXD =false;
	$("input[name='prepareCheckEle']:checked").each(function(){
		if($(this).attr("replyProductName")=="农信借"){
			NXD = true;
			return false;
		}
		if(flowFlag=='CTR_AUDIT' && $(this).attr("replyProductName")=="信易借"){
			replyProductName=true;
			return false;
		}
		if(flowFlag=='CTR_AUDIT' && $(this).attr("flag")=="ZCJ"){
	    	 	errorCount++;
			    errorCountZCJ++;
	    }else if($(this).attr("flag")=="联合"){
	    	errorCount++;
	    }else{
	    	sucessCount++;
			if(targetModel ==""){
				targetModel = $(this).attr('model');
			}else{
				if(targetModel!=$(this).attr('model')){
					hasDifModel = true;	
				}
			}
			if($(this).attr('flag')!=null && $(this).attr('flag')!=''&& $(this).attr('flag').trim()!=''){
				hasNoChannel = true;
			}else{
				hasChannel = true;
			}
    		if(param!=""){
			    param+=";"+$(this).val();
			}else{
				param=$(this).val();	
			}
    		if(flowFlag!='RATE_AUDIT' && $(this).attr("backFlag")!="1"){
    			sucessCountZCJ++;
    			if(paramZCJ!=""){
    				paramZCJ+=";"+$(this).val();
    			}else{
    				paramZCJ=$(this).val();	
    			}
    		}else{
    			errorCountZCJ++;
    		}
			
	    }
	});
	if(flowFlag=='RATE_AUDIT'){
		sucessCountZCJ=sucessCount;
		errorCountZCJ=errorCount;
		paramZCJ=param;
	}
	if(NXD){
		art.dialog.alert('农信借的产品不能修改渠道,请重新选择!');
		return false;
	}
	if(replyProductName){
		art.dialog.alert('信易借的产品不能修改渠道,请重新选择!');
		return false;
	}
	if(param=='' && errorCount>0){
		param='a';
	}
	if(hasDifModel){
		art.dialog.alert('选中的数据中存在不同模式的数据,请重新选择!');
		return false;
	}
	if(param==''){
		art.dialog.alert('请选择需要添加渠道的数据!');
		return false;
	}
	 $('#batchColl').val(param);
	 $('#batchCollBack').val(paramZCJ);
	 $('#errorCountZCJ').val(errorCountZCJ);
	 $('#sucessCountZCJ').val(sucessCountZCJ);
	 var url=ctx + '/apply/contractUtil/openAddFlagDialog?attributeName='+attributeName+"&flowFlag="+flowFlag+
	 "&redirectUrl="+redirectUrl+"&targetModel="+targetModel+"&errorCount="+errorCount+"&sucessCount="+sucessCount;
	 art.dialog.open(url, {  
         id: 'flag',
         title: '标识!',
         lock:true,
         width:400,
     	 height:300
     },  
     false);
};
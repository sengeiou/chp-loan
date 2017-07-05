var paperLessFlag={};
paperLessFlag.add = function(attrName,tarVal,reloadObj){
	 var added = false;
	  var param = "";
	  if($("input[name='prepareCheckEle']:checked").length==0){
		 
		  art.dialog.alert("请选择需要添加标识的数据!");
		  return false;
	  }
	$("input[name='prepareCheckEle']:checked").each(function(){
		
		var paperLessFlag = $(this).attr("paperLessFlag");
		if(paperLessFlag=="1"){
			added = true;
		}
		if(param!=""){
		    param+=";"+$(this).val();
		}else{
			param=$(this).val();	
		}
	});
	if(added){
		art.dialog.alert("选中的数据中存在已添加无纸化标识的数据，请重新选择!");
		return false;
	}else{
		ajaxHandleFlag(param,attrName,tarVal," ");
		 if(cancelFlagRetVal=='1'){
				fetchTaskItems(reloadObj.curForm,
						reloadObj.flagForm);  
			  }else if('0'==cancelFlagRetVal){
				  art.dialog.alert("请求的数据异常!");
			  }
	} 
 };
 
paperLessFlag.cancel = function(attrName,tarVal,reloadObj){
	 var added = false;
	  var param = "";
	  if($("input[name='prepareCheckEle']:checked").length==0){
		 
		  art.dialog.alert("请选择需要取消标识的数据!");
		  return false;
	  }
	$("input[name='prepareCheckEle']:checked").each(function(){
		
		var paperLessFlag = $(this).attr("paperLessFlag");
		if(paperLessFlag=="0" || paperLessFlag==""){
			added = true;
		}
		if(param!=""){
		    param+=";"+$(this).val();
		}else{
			param=$(this).val();	
		}
	});
	if(added){
		art.dialog.alert("选中的数据中存在非无纸化标识的数据，请重新选择!");
		return false;
	}else{
		ajaxHandleFlag(param,attrName,tarVal," ");
		 if(cancelFlagRetVal=='1'){
				fetchTaskItems(reloadObj.curForm,
						reloadObj.flagForm);  
			  }else if('0'==cancelFlagRetVal){
				  art.dialog.alert("请求的数据异常!");
			  }
	}  
	 
 };
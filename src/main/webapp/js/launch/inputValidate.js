var validate={};

validate.telFormat=function(inputId,fieldName){
	var telNum = $('#'+inputId).val();
	if(telNum!=null && telNum!=''){
		if(telNum.indexOf("-")==-1){
			art.dialog.alert(fieldName+"格式应为XXX-XXXXXXXX");
			return false;
		}else{
		    var telNums = telNum.indexOf("-");
		    if(telNums.length>2){
		    	art.dialog.alert(fieldName+"格式应为XXX-XXXXXXXX");
		    	return false;
		    }else{
		    	if(telNums[0].length!=3){
		    		art.dialog.alert(fieldName+"区号位数不对！");
		    		return false;
		    	}else if(isNaN(telNums[0])){
		    		alert(fieldName+"区号只能是数字！");
		    		return false;
		    	}
		    	if(telNums[1].length<8){
		    		art.dialog.alert(fieldName+"号码位数不对！");
		    		return false;
		    	}else if(isNaN(telNums[1])){
		    		art.dialog.alert(fieldName+"固定电话号码只能是数字！");
		    		return false;
		    	}
		    }
		}
	}
	return true;	
}
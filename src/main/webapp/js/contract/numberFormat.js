var MathUtils={}
MathUtils.numberFormat=function(srcNumber,precision){
	var num = new Number(srcNumber);
	return num.toFixed(precision);
	
}
MathUtils.moneyFormat=function(srcNumber,precision){
	   precision = precision> 0 && precision <= 20 ?precision: 2;  
	   srcNumber= parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";  
	    var nums = srcNumber.split(".");
	    var intPart = nums[0].reverse();
	    var floatPart = "00";
	    if(nums.length>1){
	    	floatPart = srcNumber.split(".")[1];  
	    }
	    targetNum = "";  
	    for (i = 0; i < intPart.length; i++) {  
	    	targetNum += intPart[i] + ((i + 1) % 3 == 0 && (i + 1) != intPart.length ? "," : "");  
	    }  
	    
	    return targetNum + "." + floatPart;  
}
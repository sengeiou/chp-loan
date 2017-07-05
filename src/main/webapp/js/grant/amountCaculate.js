var calculate = {
	srcListIndex:-1,
	srcListLength:0,
	curMaxAmount:0,
	maxAmountLocation:0,
	topLimitAmount:0,
	canBreak:false,
	amountObjList:new Array()	
};
function amountBean(){
	var amountBean = new Object();
	amountBean.totalAmount = 0;
	amountBean.itemList=new Array();
	return amountBean;
};
/*var amountObj={
	index:0,
	totalAmount:0,
	itemList:new Array()
		
};*/
calculate.amountTopLimit = function(srcList,calculateObj,moneyItem){
	var curIndex = calculateObj.srcListIndex;
	var curObj = srcList[curIndex];
	var amountObjList = calculateObj.amountObjList;
	var curAmount = parseFloat($(curObj).attr(moneyItem));
	var newAmountObjList = new Array();
	var indexAmount = 0;
	var newAmountObj = null;
	var amountObj = null;
	if(amountObjList.length!=0){
		for(var i = 0;i < amountObjList.length;i++){
			amountObj = amountObjList[i];
			indexAmount = amountObj.totalAmount;
			if(curAmount<calculateObj.topLimitAmount){
				newAmountObj = amountBean();
				newAmountObj.totalAmount = curAmount;
				newAmountObj.itemList = new Array();
				newAmountObj.itemList.push(curIndex);
				if(calculateObj.curMaxAmount < curAmount){
					calculateObj.curMaxAmount = curAmount;
					calculateObj.maxAmountLocation = newAmountObjList.push(newAmountObj)-1;
				}else{
					newAmountObjList.push(newAmountObj);
				}
				
			}
			if(indexAmount+curAmount<calculateObj.topLimitAmount){
				// 新生成一个保存当前总数的obj对象
				newAmountObj = amountBean();
				newAmountObj.totalAmount = amountObj.totalAmount;
				newAmountObj.itemList = amountObj.itemList.concat();
				newAmountObjList.push(newAmountObj); 
				// 金额相加
				amountObj.totalAmount += curAmount;
				amountObj.itemList.push(curIndex);
				if(calculateObj.curMaxAmount <= amountObj.totalAmount){
				   
					calculateObj.curMaxAmount = amountObj.totalAmount;
					calculateObj.maxAmountLocation =newAmountObjList.push(amountObj)-1;
				}else{
					newAmountObjList.push(amountObj);
				}
			}else if(indexAmount+curAmount==calculateObj.topLimitAmount){
				// 如果当前对象的总金额+当前循环金额=金额上限时。直接金额相加并退出循环
				amountObj.totalAmount += curAmount;
				amountObj.itemList.push(curIndex);
				calculateObj.curMaxAmount = amountObj.totalAmount;
				calculateObj.maxAmountLocation = newAmountObjList.push(amountObj)-1;
				calculateObj.canBreak = true;
				break;
			}else{
				if(calculateObj.curMaxAmount <= amountObj.totalAmount){
					
					calculateObj.curMaxAmount = amountObj.totalAmount;
					calculateObj.maxAmountLocation =newAmountObjList.push(amountObj)-1;
				}
				//newAmountObjList.push(amountObj);
			}
		}
	}else{
		// 初始值为0的时候的操作
		if(curAmount<calculate.topLimitAmount){
			newAmountObj = amountBean();
			newAmountObj.totalAmount = curAmount;
			newAmountObj.itemList.push(curIndex);
			amountObj = amountBean();
			calculateObj.curMaxAmount = curAmount;
			calculateObj.maxAmountLocation = newAmountObjList.push(newAmountObj)-1;
		    newAmountObjList.push(amountObj);
		}else if(curAmount==calculateObj.topLimitAmount){
			newAmountObj = amountBean();
			newAmountObj.totalAmount = curAmount;
			newAmountObj.itemList.push(curIndex);
			calculateObj.curMaxAmount = newAmountObj.totalAmount;
			calculateObj.maxAmountLocation = newAmountObjList.push(newAmountObj)-1;
		    calculateObj.canBreak = true;
		}
	}
	calculateObj.amountObjList = newAmountObjList;
	
	return calculateObj;
};
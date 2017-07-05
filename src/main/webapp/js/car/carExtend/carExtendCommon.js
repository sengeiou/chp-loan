function validateContractCode(contractCode) {
	var flag = false;
	var length = contractCode.length;
	if (length == 22) {
		var regex = /^[车|G|质][(|（][)|）]借字[(|（][\d]{4}[)|）]第[\d]{8}号$/;
		var str1 = new Array("京", "津", "冀", "晋", "蒙", "辽", "吉", "黑", "沪", "苏", "浙", "皖", "闽", "赣", "鲁", "豫", "鄂", "湘", "粤", "桂", "琼", "渝", "川", "贵", "云", "藏", "陕", "甘", "青", "宁", "新");
		for (var i = 0; i < str1.length; i++) {
			if (contractCode.substring(2, 3) == str1[i]) {
				contractCode = contractCode.substring(0, 2) + contractCode.substring(3, length);
				flag = regex.test(contractCode);
				break;
			} else {
				continue;
			}
		}
	}
	return flag;
};
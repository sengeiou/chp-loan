var loanCreditOpr={};
loanCreditOpr.rowStr="<tr><td><input type='radio' name='loanCreditInfoList.dictMortgageType'" +
		"value=''></input></td>" +
		"<td><input name='loanCreditInfoList.creditMortgageGoods' type='text' class='input_text178'/></td>" +
		" <td><input name='loanCreditInfoList.orgCode' type='text' class='input_text178'/></td>" +
		"<td><input name='loanCreditInfoList.creditLoanLimit' type='text' class='input_text70'/></td>" +
		"<td><input name='loanCreditInfoList.creditMonthsAmount' type='text' class='input_text70'/></td>" +
		"<td><input name='loanCreditInfoList.creditLoanBlance' type='text' class='input_text70'/></td>" +
		"<td><input name='loanCreditInfoList.creditCardNum' type='text' class='input_text70'/></td>" +
		"<td class='tcenter'><input type='button' onclick='loanCreditOpr.delRow(this,\'loanCreditArea\')' class='btn_delete' value='删除'/></td>";

loanCreditOpr.addRow=function(tableId){
	$('#'+tableId).append(loanCreditOpr.rowStr);
};
loanCreditOpr.delRow=function(obj,tableId){
	if($("#"+tableId).children("tr").length==2){
		art.dialog.alert("当前行禁止删除！");
		return;
	}
	 $(obj).parent().parent().remove();
};
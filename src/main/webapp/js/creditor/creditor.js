function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#creditorForm").attr("action", ctx + "/borrow/creditor/getCreditorlist");
	$("#creditorForm").submit();
	return false;
}
function clear1(){
	$('#loanCode').val('');
	$('#loanName').val('');
	$('#cerNum').val('');
	$('#type').val('');
	$("#type").trigger("change");
}

function addPage(){
	$("#creditorForm").attr("action", ctx + "/borrow/creditor/toAddPage");
	$("#creditorForm").submit();
}
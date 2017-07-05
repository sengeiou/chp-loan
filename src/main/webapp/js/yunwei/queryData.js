/**
 * @function 页面分页
 * @param n
 * @param s
 * @returns {Boolean}
 */
function page(n, s) {
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#queryDataForm").attr("action",  ctx + "/yunwei/query/queryDataBySql");
	$("#queryDataForm").submit();
	return false;
}

$(function(){  
	var errMsg = $("#errMsg").val();
	if(errMsg != '') {
		if(errMsg == 'isNull') {
			art.dialog.alert("请输入查询SQL！");
		} else if(errMsg == 'sqlError') {
			art.dialog.alert("请输入以SELECT 开头的语句！");
		} else if(errMsg == 'sqlQueryError') {
			art.dialog.alert("查询SQL过程出现异常！");
		} else if(errMsg == 'norights') {
			art.dialog.alert("无权限操作该功能！");
		}
	}
});
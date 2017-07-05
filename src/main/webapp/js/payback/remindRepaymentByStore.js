// 分页
function page(n, s) {
	if (n) $("#pageNo").val(n);
	if (s) $("#pageSize").val(s);
	$("#auditForm").attr("action",  ctx + "/borrow/payback/remindAgency/reremindRepayByStore");
	$("#auditForm").submit();
	return false;
};

// 添加备注按钮
function addRemark(obj){
	var remark = obj.id;
	var remarkid = obj.name; 
	$('#reamrkVal').val(remark);
	$('#remarkSubId').attr('name',remarkid);
	art.dialog({
		title: '添加备注信息',
		padding: 0,
		content: document.getElementById('remarkByStore'),
		lock: true,
	},false);
};	

// 提交备注
function remarkSub(obj){
	var remarkId = obj.name;
	var url = ctx + "/borrow/payback/remindAgency/addRemark?remarkId="+remarkId;
	$("#remarkForm").attr('action',url);
	$("#remarkForm").submit();
};

// 备注取消按钮
function remarkBack(){
	$('#remarkByStore').modal('toggle');
};

// 还款日验证
$(document).ready(function() {	
	$("#repaymentDate").blur(function(){
		var da = $("#repaymentDate").val();
		if (da != null && "" != da) {
			var dar = eval(da);
			if (dar>31 || dar<1 ) {
				alert("请输入1~31之间的数字!");
			}
		}
	});
});

// 回显数据清除
$(document).ready(function() {		
	$("#clearBtn").click(function(){	
		// 清除text	
		$(":text").val('');
		// 清除number
		$("#repaymentDate").val('');
		// 清除checkbox	
		$(":checkbox").attr("checked", false);
		// 清除radio			
		$(":radio").attr("checked", false);
		// 清除select			
		$("select").val("");
		$("select").trigger("change");	
		$("#auditForm").submit();
	});	
});

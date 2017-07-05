$(function() {
	$("#clearBtn").click(
			function() {
				$(':input', '#searchForm').not(':button, :reset,:hidden').val(
						'').removeAttr('checked').removeAttr('selected');
			});
	// 点击全选
	$("#checkAll").click(function() {
		var totalNum = $("#num").val();
		var deductAmount = $("#deduct").val();
		$('input[name="checkBoxItem"]').attr("checked", this.checked);
		if(this.checked == true){
			$('#hiddenNum').val(totalNum);
			$("#hiddenAmount").val(deductAmount);
		}else{
			$("#hiddenNum").val(0);
			$("#hiddenAmount").val(0.00);
		}	
		$('#totalNum').text(totalNum);
		$("#deductAmount").text(fmoney(deductAmount, 2));
	});
	// 计算金额,
	var $subBox = $(":input[name='checkBoxItem']");
	$subBox
			.click(function() {
				// 记录总金额，当length为0时，进行总金额的处理
				var totalCreditor = $("#deduct").val();
				var totalNum = $("#num").val();
				// 获得单个单子的金额
				var creditorAmount = parseFloat($(this).attr("creditorAmount"));
				var num = 1;
				if ($(this).is(':checked')) {
					var hiddenNum = parseFloat($("#hiddenNum").val()) + num;
					var hiddenDeduct = parseFloat($("#hiddenAmount").val())
							+ creditorAmount;
					$('#totalNum').text(hiddenNum);
					$("#hiddenNum").val(hiddenNum);
					$("#hiddenAmount").val(hiddenDeduct);
					$('#deductAmount').text(fmoney(hiddenDeduct, 2));
				} else {
					if ($(":input[name='checkBoxItem']:checked").length == 0) {
						$('#totalNum').text(totalNum);
						$("#deductAmount").text(fmoney(totalCreditor, 2));
						$("#hiddenNum").val(0);
						$("#hiddenAmount").val(0.00);
					} else {
						$('#totalNum').text(
								parseFloat($("#hiddenNum").val()) - num);
						$("#hiddenNum").val(
								parseFloat($("#hiddenNum").val()) - num);

						var hiddenDeduct = parseFloat($("#creditorAmount")
								.val())
								- creditorAmount;
						$("#deductAmount").text(fmoney(hiddenDeduct, 2));
						$("#hiddenAmount").val(hiddenDeduct);
					}
				}
				$("#checkAll")
						.attr(
								"checked",
								$subBox.length == $(":input[name='checkBoxItem']:checked").length ? true
										: false);
			});
	// 债务结清列表导出
	$("#dao,#confirm")
			.click(
					function() {
						var elementId = $(this).attr("id");

						var cid = "";
						if ($(":input[name='checkBoxItem']:checked").length > 0) {
							$(":input[name='checkBoxItem']:checked").each(
									function() {
										if (cid != "") {
											cid += ",'" + $(this).attr("cid")+"'";
										} else {
											cid = "'"+$(this).attr("cid")+"'";
										}
									});
						} else {
							if (elementId == "confirm") {
								alert("请选择要进行确认的数据信息");
								return false;
							}
						}
						if (elementId == "dao") {
							art.dialog.confirm("确定要导出选中的数据吗",function (){
								window.location.href = ctx
										+ "/channel/financial/carSettlement/impSettlementOfClaimsList?"
										+ "cid=" + cid + "&loanCode="
										+ $("#loanCode").val()
										+ "&settleStartDate="
										+ $("#settleStartDate").val()
										+ "&settleEndDate="
										+ $("#settleEndDate").val();
							});
						} else
							{
							art.dialog.confirm("确定要确认选中的数据吗",function (){
								$.ajax({
									type : 'post',
								    url:ctx +"/channel/financial/carSettlement/confirmSettlementOfClaimsList?cid="
									+ cid,
									cache: false,
									processData: false,
									contentType: false,
									dataType : 'text',
									success : function(data) {
										   alert(data);
										   window.location.href = ctx + "/channel/financial/carSettlement/init";
									}
								});
								});
							}
					})
});
// 格式化，保留两个小数点
function fmoney(s, n) {
	n = n > 0 && n <= 20 ? n : 2;
	s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
	var l = s.split(".")[0].split("").reverse(), r = s.split(".")[1];
	t = "";
	for (i = 0; i < l.length; i++) {
		t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
	}
	return t.split("").reverse().join("") + "." + r;
}
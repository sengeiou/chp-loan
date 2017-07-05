$(function() {
	$("#clearBtn").click(
			function() {
				$(":input", "#confirmForm").not(":button,:reset,:hidden").val(
						"").removeAttr("checked").removeAttr("selected");
			});
	// 点击全选
	$("#checkAll").click(function() {
		var totalNum = $("#num").val();
		var deductAmount = $("#deduct").val();
		$('input[name="checkBoxItem"]').attr("checked", this.checked);
		if(this.checked == true){
			$('#hiddenNum').val(totalNum);
			$("#hiddenDeduct").val(deductAmount);
		}else{
			$("#hiddenNum").val(0);
			$("#hiddenDeduct").val(0.00);
		}	
		$('#count').text(totalNum);
		$("#amount").text(fmoney(deductAmount, 2));
		/**/
	});
	// 计算金额,
	var $subBox = $(":input[name='checkBoxItem']");
	$subBox
			.click(function() {
				// 记录总金额，当length为0时，进行总金额的处理
				var totalDeduct = $("#deduct").val();
				var totalNum = $("#num").val();
				// 获得单个单子的金额
				var deductAmount = parseFloat($(this).attr("deductAmount"));
				var num = 1;
				if ($(this).is(':checked')) {
					var hiddenNum = parseFloat($("#hiddenNum").val()) + num;
					var hiddenDeduct = parseFloat($("#hiddenDeduct").val())
							+ deductAmount;
					$("#hiddenNum").val(hiddenNum);
					$("#count").text(hiddenNum);
					$("#hiddenDeduct").val(hiddenDeduct);
					var amount = fmoney(hiddenDeduct, 2);
					$("#amount").text(amount);
				} else {
					if ($(":input[name='checkBoxItem']:checked").length == 0) {
						
						$("#hiddenNum").val(0);
						$("#hiddenDeduct").val(0.00);
						$("#amount").text(fmoney(totalDeduct, 2));
						$("#count").text(totalNum);
					} else {
						var count = parseFloat($("#hiddenNum").val()) - num;
						$('#count').text(count);
						$("#hiddenNum").val(
								parseFloat($("#hiddenNum").val()) - num);

						var hiddenDeduct = parseFloat($("#hiddenDeduct").val())
								- deductAmount;
						var amount = fmoney(hiddenDeduct, 2);
						$("#amount").text(amount);
						$("#hiddenDeduct").val(hiddenDeduct);
					}
				}
				$("#checkAll")
						.attr(
								"checked",
								$subBox.length == $(":input[name='checkBoxItem']:checked").length ? true
										: false);
			});

	// 债权确认列表导出
	$("#dao,#confirm").click(function() {
						var elementId = $(this).attr("id");
						var cid = "";
						var message="选中的这批数据吗？";
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
								message="所有的数据吗？";
							}
						}
						if (elementId == "dao") {
								$("#confirmForm").attr("action",ctx + "/channel/financial/carConfirm/impCarCreditorConfirmList?cid="+cid);
								$("#confirmForm").submit();
						} else {
							art.dialog.confirm("确定要确认"+message,function (){
								$.ajax({
									type : 'post',
									url : ctx + "/channel/financial/carConfirm/confirmCarCreditorConfirmList?cid="+cid,
									data : $("#confirmForm").serialize(),
									cache : false,
									dataType : 'json',
									async : false,
									success : function(result) {
											art.dialog.alert("操作成功",function (){
												page(0,30);
											});
									},
									error : function() {
										art.dialog.alert('请求异常！');
									}
								});

							});
						}
			});
});
//格式化，保留两个小数点
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
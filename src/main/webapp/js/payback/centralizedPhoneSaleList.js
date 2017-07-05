/**
 * @function 页面分页
 * @param n
 * @param s
 * @returns {Boolean}
 */
function page(n, s) {
	var menuId = $("#menuId").val();
	if (n)
		$("#pageNo").val(n);
	if (s)
		$("#pageSize").val(s);
	$("#auditForm").attr("action",
			ctx + "/borrow/centralized/deduct/collectDeductionPhoneSaleList?menuId="+menuId);
	$("#auditForm").submit();
	return false;
}

$(document)
		.ready(
				function() {

					// 初始化门店下拉框
					loan.getstorelsit("txtStore", "hdStore");

					// 全选 多选
					$("[name='checkBox']")
							.each(
									function() {
										$(this)
												.bind(
														'click',
														function() {
															if ($(this).attr(
																	'checked') == 'checked'
																	|| $(this)
																			.attr(
																					'checked')) {
															} else {
																$("#checkAll")
																		.removeAttr(
																				"checked");
															}

														});
									});
					$('#checkAll').bind(
							'click',
							function() {
								if ($('#checkAll').attr('checked') == 'checked'
										|| $('#checkAll').attr('checked')) {
									$(":input[name='checkBox']").attr(
											"checked", 'true');
								} else {
									$(":input[name='checkBox']").removeAttr(
											"checked");
								}
							});
					// 导出Excel数据表
					$("button[name='exportExcel']")
							.click(
									function() {
										var idVal = "";
										var checkList = $(
												"input:checkbox[name='checkBox']:checked")
												.val();
										$(
												"input:checkbox[name='checkBox']:checked")
												.each(
														function() {
															if (checkList != null) {
																var value = $(
																		this)
																		.val();
																values = value
																		.split(",");
																idVal += ","
																		+ values[0];// 期供ID
															}
														});
										$("#ids").val(idVal);
										$("#auditForm")
												.attr(
														"action",
														ctx
																+ "/borrow/centralized/deduct/exportPhoneSaleExcel");
										$("#auditForm").submit();
									});

					// 还款日验证
					$("#repaymentDate").blur(function() {
						var da = $("#repaymentDate").val();
						if (da != null && "" != da) {
							var dar = eval(da);
							if (dar > 31 || dar < 1) {
								artDialog.alert('请输入1~31之间的数字!');
								$("#repaymentDate").focus();
								return;
							}
						}
					});

					// 清除按钮
					$("#clearBtn").click(
						function() {
							var menuId = $("#menuId").val();
							window.location.href =ctx+ "/borrow/centralized/deduct/collectDeductionPhoneSaleList?menuId"+menuId;
						}
					);
					// 搜索按钮
					$("#searchBtn").click(
						function() {
							$("#auditForm")
									.attr("action",ctx+ "/borrow/centralized/deduct/collectDeductionPhoneSaleList");
							$("#auditForm").submit();
							
						}
					);
				});

$(function() {
	// 全选按钮显示划扣金额，划扣笔数
	$("#checkAll").click(
			function() {
				$("input[name='checkBox']").attr("checked", this.checked);
//				var totalNum = 0;
//				var totalMoney = 0;
//				if (this.checked) {
//					$("[name = 'checkBox']").each(
//							function() {
//								totalNum = totalNum + 1;
//								totalMoney = totalMoney
//										+ parseFloat($(this).next().val() ? $(
//												this).next().val() : 0);
//							})
//					$("#totalNum").val(totalNum);
//					$("#totalMoney").val(totalMoney);
//				} else {
//					$("#totalNum").val(0);
//					$("#totalMoney").val(0);
//				}
			});

	// 为每一个复选框绑定事件
	$("input[name='checkBox']").click(
			function() {
				var totalNum = 0;
				var totalMoney = 0;
				$("[name='checkBox']").each(
						function() {
							if (this.checked) {
								totalMoney = totalMoney
										+ parseFloat($(this).next().val());
								totalNum = totalNum + 1;
							}
							$("#totalMoney").text(totalMoney.toFixed(2));
							$("#totalNum").text(totalNum);
						});
				var checkBox = $("input:checkbox[name='checkBox']").length();
				var checkBoxLength = $(
						"input:checkbox[name='checkBox']:checked").length();
				if (checkBox == checkBoxLength) {
					$("#checkAll").attr("checked", true);
				} else {
					$("#checkAll").attr("checked", false);
				}
			});
})

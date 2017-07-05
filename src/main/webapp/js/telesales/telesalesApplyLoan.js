$(document)
		.ready(
				function() {
					$('#searchBtn').bind('click', function() {
						var startDate = $('#firstConsultDateStart').val();
						var endDate = $('#firstConsultDateEnd').val();
						if (getDate(startDate) - getDate(endDate) > 0) {
							art.dialog.alert("开始日期不能大于结束日期!");
							return false;
						}
						$('#inputForm').submit();
					});
					$('#clearBtn').bind(
							'click',
							function() {
								$(':input', '#inputForm').not(
										':button, :submit, :reset,:hidden').val('')
										.removeAttr('checked').removeAttr(
												'selected');
								$("#storeCode").val('');
								$('#inputForm')[0].submit();
							});
					$('#showMore').bind('click', function() {
						show();
					});
					
					
					
					$('#excelBtn').bind(
									'click',
									function() {
										var loancode = "";
										var consultView = $("#inputForm").serialize();
										var checkList=$("input:checkbox[name='checkBoxItem']:checked").val();
										$("input:checkbox[name='checkBoxItem']:checked").each(function(){
											 if(checkList != null){
												 var value=$(this).val();
												 loancode +=";"+ value;
											 }
											});
										window.location.href = ctx
												+ "/telesales/export/exportApplyInfoExcel?loancode="
												+ loancode + "&" + consultView;

									});
					
					
					//---------------------------
					
					
					
					//----------------------------
					
					

					$('#jqSourceBtn').bind('click', function() {
						var flag = false;
						str=dayList.split("|");      
					    for (i=0;i<str.length ;i++ )   
					    {   
					        if(str[i]==current.getDate()){
					        	flag = true;
					        }
					    }   
					    if(flag){
					    	exportExcel('exportLoanCleanExcel'); // 结清资源
					    }else{
					    	art.dialog.alert('当前不是还款日，不能导出。');
					    }
						
					});

					$('#bqSourceBtn').bind('click', function() {
						exportExcel("exportLoanNoSignExcel"); // 不签约资源
					});

					$('#zdSourceBtn').bind('click', function() {
						exportExcel("exportLoanAgainExcel"); // 结清再贷资源
					});

					$('#spSourceBtn').bind('click', function() {
						exportExcel("exportLoanNoAuditExcel"); // 审批拒绝资源
					});
					
					$('#telesaleConsultListexcelBtn').bind('click', function() {
						exportExcel("exportTelesaleCustomerListExcel");
					});
					// 全选绑定
					$('#checkAll').bind(
							'click',
							function() {
								var checked = false;
								if ($('#checkAll').attr('checked') == 'checked'
										|| $('#checkAll').attr('checked')) {
									checked = true;
								}
								selectAll(checked);
							});

					loan.getstorelsit("storeName", "storeCode", "1");
				});

/**
 * 日期转换
 * 
 * @param date
 * @returns {String}
 */
function getDate(date) {
	var dates = date.split("-");
	var dateReturn = '';
	for (var i = 0; i < dates.length; i++) {
		dateReturn += dates[i];
	}
	return dateReturn;
}

/**
 * 全选
 * 
 * @param checked
 */
function selectAll(checked) {
	$("input[name='checkBoxItem']").each(function() {
		$(this).attr('checked', checked);
	});
}

/**
 * 导出Excel
 * @param url
 */
function exportExcel(url) {
	var consultView = $("#inputForm").serialize();
	window.location.href = ctx + "/telesales/export/" + url + "?" + consultView;
}

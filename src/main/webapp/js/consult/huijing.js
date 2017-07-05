function queryBlackLog(mateCertType, mateCertNum) {
	if (mateCertType != null && mateCertType != '' && mateCertNum != null && mateCertNum != '') {
		$.ajax({
			url : ctx + '/apply/black/asynQueryBlack',
			type : 'post',
			data : {
				'dictBlackType' : mateCertType,
				'blackMsg' : mateCertNum
			},
			dataType : 'json',
			success : function(data) {
				if ($('#flag').length > 0) {
					$('#flag').val(data.isWhite);
					$('#message').val(data.message);
				}
				$('#blackTip').html(data.message);
			},
			error : function() {
				art.dialog.alert('请求异常');
			},
			async : false
		});
	}
}
function queryCustomerBaseByCertNum(idType, certNum) {
	$.ajax({
		url : ctx + "/apply/consult/asynFindByCertNum",
		type : 'post',
		data : {
			'certNum' : certNum,
			'certType' : idType
		},
		dataType : 'json',
		cache : false,
		success : function(data) {
			if (data != null) {
				if (data.code !== '201') {
					art.dialog.alert(data.msg);
					$('#flag').val('0');
					$('#message').val(data.msg);
				} else {
					$('#flag').val('1');
					$('#message').val(data.msg);
					$('#customerCode').val(data.customerCode);
					if ($('#customerName').val() == '' || $('#customerName').val() == null) {
						$('#customerName').val(data.customerName);
					}
					if ($('#areaNo').val() == '' || $('#areaNo').val() == null) {
						$('#areaNo').val(data.areaNo);
					}
					if ($('#idStartDay').val() == '' || $('#idStartDay').val() == null) {
						$('#idStartDay').val(data.idStartDayStr);
					}
					if ($('#idEndDay').val() == '' || $('#idEndDay').val() == null) {
						if (data.idEndDayStr != null) {
							$('#idEndDay').val(data.idEndDayStr);
						} else {
							if ($('#idStartDay').val() != '' && $('#idStartDay').val() != null) {
								$('#longTerm').attr('checked', true);
								$('#idEndDay').attr('readOnly', true);
								$('#idEndDay').hide();
							}
						}
					}
					var compIndustry = $('#dictCompIndustry option:selected').val();
					if (compIndustry == '' || compIndustry == null) {
						$('#dictCompIndustry').val(data.dictCompIndustry).trigger("change");
					}
					var boolCheck = $('input:radio[name="customerBaseInfo.customerSex"]').is(":checked");
					if (!boolCheck) {
						$('.customerSex').each(function() {
							if ($(this).val() == data.customerSex) {
								$(this).attr('checked', true);
							}
						});
					}
				}
			}
		},
		error : function() {
			art.dialog.alert('服务器异常');
		},
		async : false
	});
}
function saveApplyInfo(num) {
	$.ajax({
		type : "POST",
		url : ctx + "/apply/dataEntry/saveApplyInfo?" + "flag=" + num,
		data : $('#inputForm').serialize(),
		success : function(data) {
			var spps = data.flag;
			$('#loanCode').val(data.loanInfo.loanCode);
		}
	});
}

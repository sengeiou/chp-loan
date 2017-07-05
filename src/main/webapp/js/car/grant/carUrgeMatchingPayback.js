$(document).ready(function(){
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	// 初始化tab
	$('#urgeMatchingTab').bootstrapTable('destroy');
	$('#urgeMatchingTab').bootstrapTable({
		method: 'get',
		cache: true,
		height: 320,
		pageSize: 20,
		checkboxHeader: true,
		clickToSelect: true,
		pageNumber:1
	});
	
	/**
	 * 页面办理按钮事件
	 */
	$(":input[name='matchingInfoBtn']").each(function(){
		$(this).bind('click',function(){
			$('#applyId').val($(this).attr('matchingId'));
			$('#matchingContractCode').val($(this).attr('contractCode'));
			$("#matchingInfoForm").submit();
		});
	});
	
	/**
	 * @function 批量匹配
	 */
	$('#urgeBatchMatchingBtn').click(function(){
		art.dialog.confirm("是否确定批量匹配？", function(){
			top.$.jBox.tip("正在匹配数据。。", 'loading');
			var urgeMatchingDate = $("#urgeMatchingForm").serialize();
			var data = $('#urgeMatchingTab').bootstrapTable('getSelections');
			var matchingIds,contractCodes,blueAmounts,urgeIds;
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(typeof(matchingIds)!='undefined'){
						matchingIds +="," + data[i].id;
						contractCodes +="," + data[i].contractCode;
						urgeIds +=","+data[i].urgeId;
					}else{
						matchingIds = data[i].id;
						contractCodes = data[i].contractCode; 
						urgeIds = data[i].urgeId;
					}
		    	}
			}
			$.ajax({  
				   type : "POST",
				   async:true,
				   data:{
					   matchingIds:matchingIds,
					   contractCodes:contractCodes,
					   urgeIds:urgeIds,
					   urgeServicesMoneyEx:urgeMatchingDate
				   },
					url : ctx+"/car/grant/urgeCheckMatch/matchingPayback?" + urgeMatchingDate,
					datatype : "json",
					success : function(msg){
						window.setTimeout(function () { 
							top.$.jBox.tip(msg, 'success'); 
							window.location.href=ctx+"/car/grant/urgeCheckMatch/urgeCheckMatchInfo";
						}, 2000);
					},
					error: function(){
							top.$.jBox.closeTip()
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
				});
		});
	})
	
	/**
	 * @function 批量退回
	 */
	$('#batchBackMathcingBtn').click(function() {
		art.dialog.confirm("是否确定批量退回？", function(){
			var data = $('#urgeMatchingTab').bootstrapTable('getSelections');
			var applyIds = "";
			var contractCodes,blueAmounts,urgeIds;
			var urgeServicesMoneyEx = $('#urgeMatchingForm').serialize();
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(typeof(matchingIds)!='undefined'){
						applyIds +="," + data[i].applyIds;
						contractCodes +="," + data[i].contractCode; 
						urgeIds +="," + data[i].urgeId;
					}else{
						applyIds = data[i].applyIds;
						contractCodes = data[i].contractCode; 
						urgeIds = data[i].urgeId;
					}
		    	}
			}
			urgeServicesMoneyEx+="&applyIds="+applyIds+"&contractCodes="+contractCodes+"&urgeIds="+urgeIds;
			$.ajax({  
				   type : "POST",
				   async:true,
				   data:urgeServicesMoneyEx,
					url : ctx+"/car/grant/urgeCheckMatch/urgeMatchingBatchBack",
					datatype : "json",
					success : function(msg){
						artDialog.alert(msg);
						window.location.href=ctx+"/car/grant/urgeCheckMatch/urgeCheckMatchInfo";
					},
					error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});
		});
	});

	/**
	 * @function 清除按钮绑定
	 */
	$('#urgeMatchingClearBtn').click(function() {
		$(':input','#urgeMatchingForm')
		  .not(':button, :reset')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$('select').val();
		$("select").trigger("change");
		$("#urgeMatchingForm").submit();
	});

	// 表单提交
	$("#urgeMatchingSerachBtn").click(function(){
		var urgeApplyAmount = $("#urgeApplyAmount").val();
		if(urgeApplyAmount != ''){
			if(!(/^(([1-9]\d*)(\.\d{1,2})?)$|(0\.0?([1-9]\d?))$/).test(urgeApplyAmount)){
	    		top.$.jBox.tip("金额只能输入数字且保留二位小数！"); 
	    	 	return false;
			}
		}
		$('#urgeMatchingForm').submit();
	})
});

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
	$("#urgeMatchingForm").attr("action",  ctx + "/car/grant/urgeCheckMatch/urgeCheckMatchInfo");
	$("#urgeMatchingForm").submit();
	return false;
}
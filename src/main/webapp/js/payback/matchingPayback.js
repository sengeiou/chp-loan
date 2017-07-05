$(document).ready(function(){
	// 初始化门店下拉框
	loan.getstorelsit("txtStore","hdStore");
	if($("#zhcz").val()==null || $("#zhcz").val()==''){
		if($('#sysValue').val() == 1){
			$("#endAutoMatching").css('display','inline-block'); 
			$('#startAutoMatching').css('display', 'none');
			$('#batchMatchingBtn').css('display', 'none');
			$('#batchBackMathcingBtn').css('display', 'none');
		}else{
			$("#endAutoMatching").css('display','none'); 
		}
	}
	// 初始化tab
	$('#matchingTab').bootstrapTable('destroy');
	$('#matchingTab').bootstrapTable({
		method: 'get',
		cache: true,
		height: $(window).height() - 230,
		pageSize: 20,
		striped: true,
		pageNumber:1
	});
	/**
	 * @function 加载弹出框
	 */
	var msg = $("#msg").val();
	if (msg != "" && msg != null) {
		top.$.jBox.tip(msg);
	}
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
	$('#batchMatchingBtn').click(function(){
		art.dialog.confirm("是否确定批量匹配？", function(){
			top.$.jBox.tip("正在匹配数据。。", 'loading');
			var matchingDate = $("#matchingForm").serialize();
			var data = $('#matchingTab').bootstrapTable('getSelections');
			var matchingIds,contractCodes,blueAmounts,paybackIds,models;
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(typeof(matchingIds)!='undefined'){
						matchingIds +="," + data[i].id;
						paybackIds +="," + data[i].paybackId;
						contractCodes +="," + data[i].contractCode
						models += "," + data[i].model; 
					}else{
						matchingIds = data[i].id;
						paybackIds = data[i].paybackId;
						contractCodes = data[i].contractCode; 
						models = data[i].model; 
					}
		    	}
			}
			$.ajax({  
				   type : "POST",
				   async:true,
				   data:{
					   matchingIds:matchingIds,
					   paybackIds:paybackIds,
					   contractCodes:contractCodes,
					   models:models
				   },
					url : ctx+"/borrow/payback/dealPayback/matchingPayback?" + matchingDate,
					datatype : "json",
					success: function(data){
						window.setTimeout(function () {
								top.$.jBox.tip(data, 'success');
								$('#startAutoMatching').css('display', 'inline-block');
								$('#batchMatchingBtn').css('display', 'inline-block');
								$('#batchBackMathcingBtn').css('display', 'inline-block');
								var zhcz=$("#zhcz").val();
								if(zhcz!=null && zhcz!="")
									window.location.href=ctx+"/borrow/payback/matching/list?zhcz=1";
								else
									window.location.href=ctx+"/borrow/payback/matching/list";
							}, 2000);
					},
					error: function(){
							top.$.jBox.closeTip();
							$('#startAutoMatching').css('display', 'inline-block');
							$('#batchMatchingBtn').css('display', 'inline-block');
							$('#batchBackMathcingBtn').css('display', 'inline-block');
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
				});
		});
	})
	
	/**
	 * @function 开启自动匹配
	 */
	$('#startAutoMatching').click(function(){
		art.dialog.confirm("是否开启自动匹配？", function(){
			$.ajax({  
				   type : "POST",
				   url : ctx+"/borrow/payback/dealPayback/startAutoMatching",
				   datatype : "json",
				   success: function(data){
					   if(data == 0){
						   top.$.jBox.tip("开始自动匹配");
							$('#startAutoMatching').css('display', 'none');
							$('#batchMatchingBtn').css('display', 'none');
							$('#batchBackMathcingBtn').css('display', 'none');
							$('#endAutoMatching').css('display', 'inline-block');
					   }else if(data == 1){
						   top.$.jBox.tip("数据正在自动匹配中，请稍后再试！");
					   }
				   },
				   error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});
		});
	})
	
	/**
	 * @function 结束自动匹配
	 */
	$('#endAutoMatching').click(function(){
		$.ajax({  
			   type : "POST",
			   async:true,
			   url : ctx+"/borrow/payback/dealPayback/endAutoMatching",
			   datatype : "json",
			   success: function(data){
				   if(data == 1){
					   	top.$.jBox.tip("结束自动匹配！");
						$('#startAutoMatching').css('display', 'inline-block');
						$('#endAutoMatching').css('display', 'none');
						$('#batchMatchingBtn').css('display', 'inline-block');
						$('#batchBackMathcingBtn').css('display', 'inline-block');
				   }else{
					   top.$.jBox.tip("自动匹配已经结束！");
				   }
			   },
			   error: function(){
						top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
					}
			});
	})
	
	/**
	 * @function 批量退回
	 */
	$('#batchBackMathcingBtn').click(function() {
		art.dialog.confirm("是否确定批量匹配退回？", function(){
			var matchingDate = $("#matchingForm").serialize();
			var data = $('#matchingTab').bootstrapTable('getSelections');
			var applyIds;
			if(data.length > 0){
				for(var i=0;i<data.length;i++){
					if(typeof(applyIds)!='undefined'){
						applyIds +="," + data[i].id;
					}else{
						applyIds = data[i].id;
					}
		    	}
			}
			$.ajax({  
				   type : "POST",
				   async:true,
				   data:{
					   applyIds:applyIds
				   },
					url : ctx+"/borrow/payback/matching/matchingBatchBack?" + matchingDate,
					datatype : "json",
					success : function(msg){
						top.$.jBox.tip(msg);
						var zhcz=$("#zhcz").val();
						if(zhcz!=null && zhcz!="")
							window.location.href=ctx+"/borrow/payback/matching/list?zhcz=1";
						else
							window.location.href=ctx+"/borrow/payback/matching/list";
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
	$('#matchingClearBtn').click(function() {
		$(':input','#matchingForm')
		  .not(':button, :reset,:hidden')
		  .val('')
		  .removeAttr('checked')
		  .removeAttr('selected');
		$("#repaymentDate").val('');
		$("#dictDepositAccount").val('');
		$("#hdStore").val('');
		$('select').val();
		$("select").trigger("change");
		$("#hdStore").val("");
		$("#matchingForm").submit();
	});
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
	$("#matchingForm").attr("action",  ctx + "/borrow/payback/matching/list");
	$("#matchingForm").submit();
	return false;
}
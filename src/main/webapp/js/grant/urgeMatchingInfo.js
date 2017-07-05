$(document).ready(function(){
	
	/**
	 * @function 手动匹配弹出框
	 */
	var idx;
	$("[name='matchingPayback']").each(function(){
		$(this).bind('click',function(){
			$('#infoId').val($(this).attr('infoId'));
			idx =  $(this).attr('id');
			var data = {outDepositTime: $(this).attr("tranDepositTime"),
						outReallyAmount:typeof($(this).attr("reallyAmount"))=="undefined"?'':$(this).attr("reallyAmount"),
						outEnterBankAccount:$('#storesInAccount').val()};
			$.ajax({  
				   type : "POST",
				   async:false,
				   url : ctx+"/borrow/grant/urgeCheckMatch/getMatchingList",
				   data:data,
				   datatype : "json",
					success : function(data){
						var jsonData = eval("("+data+")");
						for(var i=0;i<jsonData.length;i++){
							$.extend(jsonData[i], jsonData[i].middlePerson);
							delete jsonData[i].middlePerson;
						}
						$('#urgeMatchingTable').bootstrapTable('destroy');
						$('#urgeMatchingTable').bootstrapTable({
							data:jsonData,
							dataType: "json",    
							pageSize: 10,
							pageNumber:1
						});
					},
					error: function(){
							top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
						}
				});
		});
	});
	
	/**
	 * @function 图片预览下载
	 */
	$("[name='previewPngBtn']").each(function(){
		$(this).bind('click',function(){
			var url=ctx + "//borrow/payback/dealPayback/previewPng?docId="+$(this).attr('docId');
			art.dialog.open(url, {  
		         id: 'previewPngBtn',
		         title: '图片预览',
		         lock:true,
		         width:800,
		     	 height:500
		     },  
		     false);
		});
	});
	
	$("[name='downPng']").each(function(){
		$(this).bind('click',function(){
			window.location.href=ctx+"/borrow/grant/urgeCheckMatch/downPng?docId="+$(this).attr('docId')+"&fileName="+$(this).attr('fileName');
		});
	});

	/**
	 * @function 手动匹配
	 */
	$('#matchingSemiautomatic').click(function(){	
		var checkData = $('#urgeMatchingTable').bootstrapTable('getSelections')[0],
			contractCode = $('[name="contractCode"]').val(),
			blueAmount = $('#paybackBuleAmount').val(),
			applyReallyAmount = $('#applyReallyAmount').val(),
			id = $('[name="id"]').val();
		$.ajax({  
			   type : "POST",
			   async:false,
			   data:{
				   infoId:$('#infoId').val(),
				   outId:checkData.id,
				   contractCode:contractCode,
				   outReallyAmount:checkData.outReallyAmount,
				   blueAmount:blueAmount,
				   applyReallyAmount:applyReallyAmount,
				   applyId:id
			   },
				url : ctx+"/borrow/grant/urgeCheckMatch/matchingSingle",
				success : function(result){
					var obj = eval("("+ result +")");
					if(obj.success=="true"){
						var amount = obj.amount;
						$('#'+idx).val('已匹配');
						$('#'+idx).attr('disabled',true);
						$('#applyReallyAmount').val(amount.toFixed(2));
						$('#applyReallyAmountFormat').val(amount.toFixed(2));
					}else{
						top.$.jBox.tip(obj.message,'warning');
					}
				},
				error: function(){
					top.$.jBox.tip('服务器没有返回数据，可能服务器忙，请重试!','warning');
				}
			});
	})
	
	$('#applyPaySaveBtn').click(function(){
		// 实际到账金额
		var applyReallyAmount = $('#applyReallyAmount').val();
		var applyAmount = $('#applyAmount').val();
		if($('[name="dictPayResult"]:checked').val()==0){
			if(applyReallyAmount == applyAmount){
				$("#paybackInfoForm").validator();
			}else{
				artDialog.alert('汇款转账未完全匹配,不允许提交!');
				return false;
			}
		}else if($('[name="dictPayResult"]:checked').val()==1){
		  if($('#applyBackMes').val()==null || $('#applyBackMes').val()=='' ){
				artDialog.alert('请填写审核意见!');
				return false;
		  }
		}else{
			$("#paybackInfoForm").validator();
		}
	})
	
	// 退回显示退回原因
	$('#urgeDictPayResultGo').click(function(){
		  $('#shhy').css('display','none');
	})
	// 批量成功隐藏退回原因
    $('#urgeDictPayResultBack').click(function(){
		  $('#shhy').css('display','block');

	})
});
/*
 * @function 日期格式化
 */
function dataFormatter(value, row) {
	var d = new Date(value);
    return DateUtils.format(d, 'yyyy-MM-dd');
}

//金额格式化
function numberFormatter(value, row) {
	if (value && typeof(value)!="undefined" && value!=0)
	{
	  return value.toFixed(2);
	}
}
/**
 * js from表单set方法
 * @param $
 */
(function ($) {
    $.fn.setForm = function (jsonValue) {
         var obj=this;
        $.each(jsonValue, function (id, ival) {obj.find("#" + id).val(ival); })
        }
})(jQuery)